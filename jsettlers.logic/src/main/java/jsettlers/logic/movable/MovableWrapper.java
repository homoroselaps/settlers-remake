package jsettlers.logic.movable;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import jsettlers.algorithms.path.Path;
import jsettlers.common.material.EMaterialType;
import jsettlers.common.movable.EDirection;
import jsettlers.common.movable.EMovableAction;
import jsettlers.common.movable.EMovableType;
import jsettlers.common.position.ShortPoint2D;
import jsettlers.common.selectable.ESelectionType;
import jsettlers.logic.buildings.military.IBuildingOccupyableMovable;
import jsettlers.logic.buildings.military.occupying.IOccupyableBuilding;
import jsettlers.logic.movable.components.AnimationComponent;
import jsettlers.logic.movable.components.AttackableComponent;
import jsettlers.logic.movable.components.GameFieldComponent;
import jsettlers.logic.movable.components.MaterialComponent;
import jsettlers.logic.movable.components.MovableComponent;
import jsettlers.logic.movable.components.PlayerCmdComponent;
import jsettlers.logic.movable.components.SelectableComponent;
import jsettlers.logic.movable.components.SpecialistComponent;
import jsettlers.logic.movable.components.SteeringComponent;
import jsettlers.logic.movable.interfaces.IAttackable;
import jsettlers.logic.movable.interfaces.ILogicMovable;
import jsettlers.logic.player.Player;

/**
 * @author homoroselaps
 */

public final class MovableWrapper implements ILogicMovable, Serializable {
    private static final long serialVersionUID = -2853861825853788354L;
    private final Entity entity;

    public MovableWrapper(Entity entity) {
        this.entity = entity;
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        ois.defaultReadObject();
        entity.get(GameFieldComponent.class).getMovableMap().put(entity.getID(), this);
        entity.get(GameFieldComponent.class).getAllMovables().add(this);
    }

    //region Interface Implementations
    @Override
    public short getViewDistance() {
        if (!entity.containsComponent(MovableComponent.class)) return 0;
        return entity.get(MovableComponent.class).getViewDistance();
    }

    @Override
    public boolean needsPlayersGround() {
        if (!entity.containsComponent(MovableComponent.class)) return false;
        return entity.get(MovableComponent.class).needsPlayersGround();
    }

    @Override
    public EDirection getDirection() {
        if (!entity.containsComponent(MovableComponent.class)) return EDirection.NORTH_EAST;
        return entity.get(MovableComponent.class).getViewDirection();
    }

    @Override
    public EMovableAction getAction() {
        if (!entity.containsComponent(AnimationComponent.class)) return EMovableAction.NO_ACTION;
        return entity.get(AnimationComponent.class).getAnimation();
    }

    @Override
    public float getMoveProgress() {
        if (!entity.containsComponent(AnimationComponent.class)) return 0;
        return entity.get(AnimationComponent.class).getAnimationProgress();
    }

    @Override
    public EMaterialType getMaterial() {
        if (!entity.containsComponent(MaterialComponent.class)) return EMaterialType.NO_MATERIAL;
        return entity.get(MaterialComponent.class).getMaterial();
    }

    @Override
    public void receiveHit(float strength, ShortPoint2D attackerPos, byte attackingPlayer) {
        if (!entity.containsComponent(AttackableComponent.class)) return;
        entity.get(AttackableComponent.class).receiveHit(strength, attackerPos, attackingPlayer);
    }

    @Override
    public float getHealth() {
        return entity.get(AttackableComponent.class).getHealth();
    }

    @Override
    public boolean isRightstep() {
        return entity.get(AnimationComponent.class).isRightStep();
    }

    @Override
    public void stopOrStartWorking(boolean stop) {
        if (!entity.containsComponent(SpecialistComponent.class)) return;
        entity.get(SpecialistComponent.class).setIsWorking(!stop);
    }

    @Override
    public ShortPoint2D getPos() {
        return entity.get(MovableComponent.class).getPos();
    }

    @Override
    public boolean isSelected() {
        if (entity.get(SelectableComponent.class) == null) return false;
        return entity.get(SelectableComponent.class).isSelected();
    }

    @Override
    public void setSelected(boolean selected) {
        if (entity.get(SelectableComponent.class) == null) return;
        entity.get(SelectableComponent.class).setSelected(selected);
    }

    @Override
    public ESelectionType getSelectionType() {
        if (!entity.containsComponent(PlayerCmdComponent.class)) return ESelectionType.PEOPLE;
        return entity.get(SelectableComponent.class).getSelectionType();
    }

    @Override
    public boolean isAttackable() {
        if (!entity.containsComponent(AttackableComponent.class)) return false;
        return entity.get(AttackableComponent.class).isAttackable();
    }

    @Override
    public void setSoundPlayed() {
        entity.get(AnimationComponent.class).setSoundPlayed();
    }

    @Override
    public boolean isSoundPlayed() {
        return entity.get(AnimationComponent.class).isSoundPlayed();
    }

    @Override
    public EMovableType getMovableType() {
        return entity.get(MovableComponent.class).getMovableType();
    }

    @Override
    public boolean isTower() {
        return false;
    }

    @Override
    public void debug() {
        System.out.println("debug: " + entity);
    }

    @Override
    public void informAboutAttackable(IAttackable attackable) {
        entity.get(AttackableComponent.class).informAboutAttackable((ILogicMovable) attackable);
    }

    @Override
    public boolean push(ILogicMovable pushingMovable) {
        entity.raiseNotification(new SteeringComponent.LeavePositionRequest());
        return false;
    }

    @Override
    public Path getPath() {
        return null;
    }

    @Override
    public void goSinglePathStep() {
        assert false: "not implemented";
    }

    @Override
    public ShortPoint2D getPosition() {
        return entity.movC().getPos();
    }

    @Override
    public ILogicMovable getPushedFrom() {
        assert false: "not implemented";
        return null;
    }

    @Override
    public boolean isProbablyPushable(ILogicMovable pushingMovable) {
        //assert false: "not implemented";
        return false;
    }

    @Override
    public void leavePosition() {
        // The same as push - request to leave the place
        //TODO: call to new implementation of push
    }

    @Override
    public boolean canOccupyBuilding() {
        //TODO: this method has no right to exist, refactor together with @setOccupyableBuilding
        return false;
        //return entity.get(SelectableComponent.class).getSelectionType() == ESelectionType.SOLDIERS;
    }

    @Override
    public IBuildingOccupyableMovable setOccupyableBuilding(IOccupyableBuilding building) {
        //TODO: rename to occupyBuilding
        return null;
    }

    @Override
    public void checkPlayerOfPosition(Player playerOfPosition) {
        //TODO: rename to: player of current position changed
        //TODO: implement event
    }

    @Override
    public void convertTo(EMovableType newMovableType) {
        //TODO: support switching between Movable types
    }

    @Override
    public Player getPlayer() {
        //TODO: switch to playerID or player everywhere
        return entity.get(MovableComponent.class).getPlayer();
    }

    @Override
    public void moveTo(ShortPoint2D targetPosition) {
        if (!entity.containsComponent(PlayerCmdComponent.class)) return;
        entity.get(PlayerCmdComponent.class).send_AltLeftClick(targetPosition);
    }

    @Override
    public int getID() {
        return entity.getID();
    }

    @Override
    public int timerEvent() {
        return entity.timerEvent();
    }

    @Override
    public void kill() {
        entity.kill();
    }

    //endregion
}
