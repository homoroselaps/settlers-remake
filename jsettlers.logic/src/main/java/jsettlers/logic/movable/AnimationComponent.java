package jsettlers.logic.movable;

import jsettlers.common.movable.EMovableAction;
import jsettlers.logic.constants.MatchConstants;

/**
 * @author homoroselaps
 */

public class AnimationComponent extends Component {
    private static final long serialVersionUID = 8064683552580286008L;
    private EMovableAction anmiation = EMovableAction.NO_ACTION;
    private int animationStartTime;
    private short animationDuration;
    private boolean isSoundPlayed = false;
    private boolean isRightStep = false;

    public static class AnimationFinishedTrigger extends Notification {}

    public AnimationComponent() { }

    @Override
    public void onLateUpdate() {
        if (isAnimating())
            entity.setInvokationDelay(getRemainingTime());
    }

    public EMovableAction getAnimation() {
        return anmiation;
    }

    public float getAnimationProgress() {
        return ((float) (MatchConstants.clock().getTime() - animationStartTime)) / animationDuration;
    }

    public boolean isAnimating() {
        return animationStartTime + animationDuration < MatchConstants.clock().getTime();
    }

    public void startAnimation(EMovableAction animation, float duration) {
        this.animationStartTime = MatchConstants.clock().getTime();
        this.animationDuration = (short)(duration*1000);
        this.anmiation = animation;
        isSoundPlayed = false;
    }

    public void stopAnimation() {
        this.anmiation = EMovableAction.NO_ACTION;
    }

    public boolean isRightstep() {
        return isRightStep;
    }

    public void switchStep() {
        isRightStep = !isRightStep;
    }

    public void setSoundPlayed() {
        isSoundPlayed = true;
    }

    public boolean isSoundPlayed() {
        return isSoundPlayed;
    }

    public int getRemainingTime() {
        return animationStartTime + animationDuration - MatchConstants.clock().getTime();
    }
}
