package jsettlers.logic.movable.strategies.events;

import jsettlers.common.position.ShortPoint2D;
import jsettlers.logic.movable.stackedStateMachine.ExternalEvent;

public class MoveToPositionE extends ExternalEvent {
	public ShortPoint2D targetPosition;
	public MoveToPositionE(ShortPoint2D targetPosition) {
		this.targetPosition = targetPosition;
	}

}
