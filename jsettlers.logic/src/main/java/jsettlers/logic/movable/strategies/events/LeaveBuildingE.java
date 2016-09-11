package jsettlers.logic.movable.strategies.events;

import jsettlers.common.position.ShortPoint2D;
import jsettlers.logic.movable.stackedStateMachine.ExternalEvent;

public class LeaveBuildingE extends ExternalEvent {
	public ShortPoint2D newPosition;
	public LeaveBuildingE(ShortPoint2D newPosition) {
		this.newPosition = newPosition;
	}

}
