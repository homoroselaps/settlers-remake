package jsettlers.logic.movable.strategies.states;

import jsettlers.logic.movable.stackedStateMachine.Event;
import jsettlers.logic.movable.stackedStateMachine.RootState;
import jsettlers.logic.movable.stackedStateMachine.StateContext;
import jsettlers.logic.movable.strategies.events.InformAboutAttackableE;
import jsettlers.logic.movable.strategies.events.MoveToPositionE;
import jsettlers.logic.movable.strategies.events.OccupyBuildingE;

public class AggressiveState extends RootState {
	public AggressiveState() {
		addOnDeactivateHandler(MoveToPositionE.class);
		addOnDeactivateHandler(InformAboutAttackableE.class);
		addOnDeactivateHandler(OccupyBuildingE.class);
	}
	
	public static Event onDeactivate(MoveToPositionE e, StateContext context) {
		return null;
	}
	
	public static Event onDeactivate(InformAboutAttackableE e, StateContext context) {
		return null;
	}
	
	public static Event onDeactivate(OccupyBuildingE e, StateContext context) {
		return null;
	}
}
