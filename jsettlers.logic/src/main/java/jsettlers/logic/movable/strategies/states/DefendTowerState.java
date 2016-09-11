package jsettlers.logic.movable.strategies.states;

import jsettlers.logic.movable.stackedStateMachine.Event;
import jsettlers.logic.movable.stackedStateMachine.LeafState;
import jsettlers.logic.movable.stackedStateMachine.StateContext;
import jsettlers.logic.movable.strategies.events.DefendAtE;
import jsettlers.logic.movable.strategies.events.StrategyKilledE;

public class DefendTowerState extends LeafState {
	public DefendTowerState() {
		addOnDeactivateHandler(StrategyKilledE.class);
	}
	
	public static Event onDeactivate(DefendAtE e, StateContext context) {
		return null;
	}
}
