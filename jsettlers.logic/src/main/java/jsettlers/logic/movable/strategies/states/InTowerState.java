package jsettlers.logic.movable.strategies.states;

import jsettlers.logic.movable.stackedStateMachine.Event;
import jsettlers.logic.movable.stackedStateMachine.RootState;
import jsettlers.logic.movable.stackedStateMachine.StateContext;
import jsettlers.logic.movable.strategies.events.DefendAtE;
import jsettlers.logic.movable.strategies.events.LeaveBuildingE;

public class InTowerState extends RootState {
	public InTowerState() {
		addOnDeactivateHandler(DefendAtE.class);
		addOnDeactivateHandler(LeaveBuildingE.class);
	}
	
	public static Event onDeactivate(DefendAtE e, StateContext context) {
		return null;
	}

	public static Event onDeactivate(LeaveBuildingE e, StateContext context) {
		return null;
	}
}
