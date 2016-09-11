package jsettlers.logic.movable.strategies.events;

import jsettlers.common.position.ShortPoint2D;
import jsettlers.logic.movable.stackedStateMachine.ExternalEvent;

public class DefendAtE extends ExternalEvent {
	public ShortPoint2D pos;
	public DefendAtE(ShortPoint2D pos) {
		this.pos = pos;
	}

}
