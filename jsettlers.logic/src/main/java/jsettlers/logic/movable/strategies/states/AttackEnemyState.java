package jsettlers.logic.movable.strategies.states;

import jsettlers.logic.movable.stackedStateMachine.Event;
import jsettlers.logic.movable.stackedStateMachine.LeafState;
import jsettlers.logic.movable.stackedStateMachine.StateContext;
import jsettlers.logic.movable.stackedStateMachine.TimerEvent;
import jsettlers.logic.movable.strategies.events.PathSingleStepE;

public class AttackEnemyState extends LeafState {
	public AttackEnemyState() {
		addOnRecieveHandler(PathSingleStepE.class);
		addOnRecieveHandler(TimerEvent.class);
	}
	
	public static Event onRecieve(PathSingleStepE e, StateContext context) {
		e.continuePath.v = true;
		return null;
	}
	
	public static Event onRecieve(TimerEvent e, StateContext context) {
		return null;
	}
}
