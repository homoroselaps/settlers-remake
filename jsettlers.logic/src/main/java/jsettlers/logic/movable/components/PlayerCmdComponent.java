package jsettlers.logic.movable.components;

import jsettlers.common.position.ShortPoint2D;
import jsettlers.logic.movable.Notification;

/**
 * @author homoroselaps
 */

public class PlayerCmdComponent extends Component {
	private static final long serialVersionUID = -3188445864619388414L;

	public static class LeftClickCommand extends Notification {
		public final ShortPoint2D pos;

		LeftClickCommand(ShortPoint2D pos) {
			this.pos = pos;
		}
	}

	public static class MoveToCommand extends Notification {
		public final ShortPoint2D pos;

		MoveToCommand(ShortPoint2D pos) {
			this.pos = pos;
		}
	}

	static class StartWorkCommand extends Notification {}

	public void sendLeftClick(ShortPoint2D pos) {
		entity.raiseNotification(new LeftClickCommand(pos));
	}

	public void moveTo(ShortPoint2D pos) {
		entity.raiseNotification(new MoveToCommand(pos));
	}

	public void sendStartWorkCommand() {
		entity.raiseNotification(new StartWorkCommand());
	}
}