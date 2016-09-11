package jsettlers.logic.movable.stackedStateMachine;

import jsettlers.logic.movable.Movable;
import jsettlers.logic.movable.MovableStrategy;
import jsettlers.logic.movable.strategies.soldiers.SoldierStrategy;

public class StateContext {
	// register here the fields, all states need
	// eg Reference to a Movable
	public Movable m;
	public MovableStrategy s;
	
	public SoldierStrategy getSoldierStrategy() {
		return (SoldierStrategy) s;
	}
	
	public StateContext(StateContext stateContext) {
		if (stateContext != null) {
			this.m = stateContext.m;
		}
	}
}
