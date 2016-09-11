package jsettlers.logic.movable.strategies.states;

import jsettlers.logic.movable.stackedStateMachine.Event;
import jsettlers.logic.movable.stackedStateMachine.StateContext;
import jsettlers.logic.movable.strategies.events.PathSingleStepE;

public class OccupyPathingState extends PathingState {
	public OccupyPathingState() {
		addOnRecieveHandler(PathSingleStepE.class);
	}
	
	public static Event onRecieve(PathSingleStepE e, StateContext context) {
		e.continuePath.v = true;
		return null;
	}
}
