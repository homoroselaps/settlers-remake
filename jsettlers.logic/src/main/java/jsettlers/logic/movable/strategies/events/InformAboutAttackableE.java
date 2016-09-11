package jsettlers.logic.movable.strategies.events;

import jsettlers.logic.movable.interfaces.IAttackable;
import jsettlers.logic.movable.stackedStateMachine.ExternalEvent;

public class InformAboutAttackableE extends ExternalEvent {
	public IAttackable enemy;
	public InformAboutAttackableE(IAttackable enemy) {
		this.enemy = enemy;
	}

}
