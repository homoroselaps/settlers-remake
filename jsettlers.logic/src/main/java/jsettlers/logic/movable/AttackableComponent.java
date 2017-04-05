package jsettlers.logic.movable;

import jsettlers.common.position.ShortPoint2D;
import jsettlers.logic.movable.interfaces.ILogicMovable;

/**
 * @author homoroselaps
 */

public class AttackableComponent extends Component {
    private static final long serialVersionUID = -5453513130369184993L;
    private float health;
    private boolean isAttackable;

    public AttackableComponent(boolean isAttackable) {
        this.isAttackable = isAttackable;
    }

    public void receiveHit(float strength, ShortPoint2D attackerPos, byte attackingPlayer) {
        health -= strength;
    }

    public float getHealth() {
        return health;
    }

    public boolean isAttackable() {
        return isAttackable;
    }

    public void setAttackable(boolean isAttackable) {
        this.isAttackable = isAttackable;
    }

    public void informAboutAttackable(ILogicMovable other) {
        assert false: "Not implemented";
    }

    @Override
    public void onLateUpdate() {

    }

    @Override
    public void onDestroy() {
        health = -200;
    }
}
