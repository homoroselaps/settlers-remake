package jsettlers.logic.movable.components;

import jsettlers.algorithms.path.Path;
import jsettlers.common.material.ESearchType;
import jsettlers.common.movable.EDirection;
import jsettlers.common.movable.EMovableAction;
import jsettlers.common.position.ShortPoint2D;
import jsettlers.logic.movable.Notification;
import jsettlers.logic.movable.Requires;
import jsettlers.logic.movable.interfaces.ILogicMovable;

/**
 * @author homoroselaps
 */
@Requires({GameFieldComponent.class, MovableComponent.class, AnimationComponent.class})
public class SteeringComponent extends Component {
    private static final long serialVersionUID = 8281773945922792414L;
    private Path path;
    private GameFieldComponent gameC;
    private MovableComponent movC;
    private AnimationComponent aniC;

    public static class TargetReachedTrigger extends Notification {}
    public static class TargetNotReachedTrigger extends Notification {}
    public static class LeavePositionRequest extends Notification {}

    @Override
    protected void onAwake() {
        gameC = entity.get(GameFieldComponent.class);
        movC = entity.get(MovableComponent.class);
        aniC = entity.get(AnimationComponent.class);
    }

    public boolean setTarget(ShortPoint2D targetPos) {
        if (movC.getPos().equals(targetPos)) {
            entity.raiseNotification(new TargetReachedTrigger());
            return true;
        }
        path = gameC.getMovableGrid().calculatePathTo(movC, targetPos);
        return path != null;
    }

    public void resetTarget() {
        path = null;
    }

    public void followPath(Path path) {
        assert path != null: "path mustn't be null";
        this.path = path;
    }

    public Path preSearchPath(boolean dijkstra, short centerX, short centerY, short radius, ESearchType searchType) {
        if (dijkstra) {
            return gameC.getMovableGrid().searchDijkstra(movC, centerX, centerY, radius, searchType);
        } else {
            return gameC.getMovableGrid().searchInArea(movC, centerX, centerY, radius, searchType);
        }
    }

    @Override
    protected void onUpdate() {
        if (path == null) return;
        aniC.stopAnimation();
        // if path is finished
        if (!path.hasNextStep()) {

            path = null;
            entity.raiseNotification(new TargetReachedTrigger());
            return;
        }

        ILogicMovable blockingMovable = gameC.getMovableGrid().getMovableAt(path.nextX(), path.nextY());
        if (blockingMovable == null) { // if we can go on to the next step
            if (gameC.getMovableGrid().isValidNextPathPosition(movC, path.getNextPos(), path.getTargetPos())) { // next position is valid
                goSinglePathStep();

            } else { // next position is invalid

                Path newPath = gameC.getMovableGrid().calculatePathTo(movC, path.getTargetPos()); // try to find a new path

                if (newPath == null) { // no path found
                    path = null;

                    entity.raiseNotification(new TargetNotReachedTrigger());
                } else {
                    this.path = newPath; // continue with new path
                    if (gameC.getMovableGrid().hasNoMovableAt(path.nextX(), path.nextY())) { // path is valid, but maybe blocked (leaving blocked area)
                        goSinglePathStep();
                    }
                }
            }

        } else { // step not possible, so try it next time (push not supported)

        }
    }

    private void goSinglePathStep() {
        movC.setViewDirection(EDirection.getDirection(movC.getPos(), path.getNextPos()));
        movC.setPos(path.getNextPos());
        aniC.startAnimation(EMovableAction.WALKING, movC.getMovableType().getStepDurationMs());
        aniC.switchStep();
        path.goToNextStep();
    }
}
