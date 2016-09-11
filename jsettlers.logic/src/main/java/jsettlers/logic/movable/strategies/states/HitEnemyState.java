package jsettlers.logic.movable.strategies.states;

import jsettlers.logic.movable.interfaces.IAttackable;
import jsettlers.logic.movable.stackedStateMachine.InitialEvent;
import jsettlers.logic.movable.stackedStateMachine.LeafState;

public class HitEnemyState extends LeafState {
	static class HitEnemyE extends InitialEvent
	{
		public IAttackable enemy;
		public HitEnemyE(IAttackable enemy) {
			this.enemy = enemy;
		}
	}
	public HitEnemyState() {
		
	}
}
