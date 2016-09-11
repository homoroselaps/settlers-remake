package jsettlers.logic.movable.stackedStateMachine;

import java.util.HashMap;
import java.util.Stack;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Semaphore;

public class StackedStateMachine {
	private class Key {

		private final Class<? extends State> stateType;
		private final Class<? extends Event> eventType;

		public Key(Class<? extends State> stateType, Class<? extends Event> eventType) { this.stateType = stateType; this.eventType = eventType; }

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (!(o instanceof Key)) return false;
			Key key = (Key) o;
			return stateType.equals(key.stateType) && eventType.equals(key.eventType);
		}

		@Override
		public int hashCode() {
			return (stateType.toString() + "#" +eventType.toString()).hashCode();
		}
	}
	
    private class Transition {
        State state;
        boolean clearStack;
        public Transition(State state, boolean clearStack) {
            this.state = state;
            this.clearStack = clearStack;
        }
    }

	
	private Stack<State> stateStack = new Stack<State>();
	private Stack<StateContext> contextStack = new Stack<StateContext>();
	private HashMap<Key, Transition> transitions = new HashMap<>();
	private StateContext context;
	private ConcurrentLinkedQueue<Event> events = new ConcurrentLinkedQueue<Event>();
	private Semaphore processEvents = new Semaphore(1);
	
	public StackedStateMachine(RootState stateStart, StateContext context) {
		this.context = context;
		stateStack.push(stateStart);
		contextStack.push(stateStart.buildContext(context));
		stateStart.activateState(null, contextStack.peek());
	}
	
	public State getState() { 
		return stateStack.peek(); 
	}
	
	public void addTransition(Class<? extends State> state1, Class<? extends Event> e, State state) {
		transitions.put(new Key(state1, e), new Transition(state, false));
	}
	
    public void addRootTransition(Class<? extends State> state1, Class<? extends Event> e, State state) {
        transitions.put(new Key(state1, e), new Transition(state, true));
    }
	
	private Event handleEvent(Event e) {
		Transition trans;
		State state = stateStack.peek();
		if (state == null)
			//The state machine has no active state anymore
			return null;
		Class<? extends State> stateType = state.getClass();
		Class<? extends Event> eventType = e.getClass();
		
		if (e instanceof AbortEvent || e instanceof DoneEvent) {
			if (state != null)
				state.deactivateState(e, contextStack.peek());
			stateStack.pop();
			contextStack.pop();
			State newState = stateStack.peek();
			if (newState != null)
				return newState.activateState(e, contextStack.peek());
		}
		else if ((trans = transitions.get(new Key(stateType, eventType))) != null) {
			if (state != null)
				state.deactivateState(e, contextStack.peek());
			
			if (trans.clearStack) {
				stateStack.clear();
				contextStack.clear();
			}
			State newState = trans.state;
			if (newState != null) {
				StateContext newContext = newState.buildContext(context);
				stateStack.push(newState);
				contextStack.push(newContext);
				return newState.activateState(e, contextStack.peek());
			}
		}
		else {
			return state.receive(e, contextStack.peek());
		}
		// an event occured with no valid transition
		return null;
	}
	public void raiseEvent(Event e) {
		events.add(e);
		// ensure sequential execution
		if (processEvents.tryAcquire()) {
			try {
				while (!events.isEmpty()) {
					Event ev = events.remove();
					while (ev != null) {
						ev = handleEvent(ev);
					}
				}
			}
			finally {
				processEvents.release();
			}
		}
	}
	
	public boolean existsValidTransition(Class<? extends Event> eventType) {
		State state = stateStack.peek();
		return existsValidTransition(state != null ? state.getClass(): null, eventType);
	}
	
	public boolean existsValidTransition(Class<? extends State> stateType, Class<? extends Event> eventType) {
		return transitions.containsKey(new Key(stateType, eventType));
	}
}
