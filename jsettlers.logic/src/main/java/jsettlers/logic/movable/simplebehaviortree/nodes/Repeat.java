package jsettlers.logic.movable.simplebehaviortree.nodes;

import jsettlers.logic.movable.simplebehaviortree.Decorator;
import jsettlers.logic.movable.simplebehaviortree.IBooleanConditionFunction;
import jsettlers.logic.movable.simplebehaviortree.Node;
import jsettlers.logic.movable.simplebehaviortree.NodeStatus;
import jsettlers.logic.movable.simplebehaviortree.Tick;

public class Repeat<T> extends Decorator<T> {
    private static final long serialVersionUID = -661870259301299858L;
    private IBooleanConditionFunction<T> condition;

    public Repeat(IBooleanConditionFunction<T> condition, Node child) {
        super(child);
        this.condition = condition;
    }

    @Override
    protected NodeStatus onTick(Tick<T> tick) {
        while (condition.apply(tick.Target)) {
            NodeStatus result = child.execute(tick);
            if (result != NodeStatus.Success) return result;
        }
        return NodeStatus.Success;
    }
}
