package jsettlers.logic.movable.strategies.events;

import jsettlers.logic.movable.stackedStateMachine.ExternalEvent;
import jsettlers.logic.movable.stackedStateMachine.Out;

public class PathSingleStepE extends ExternalEvent {
	public Out<Boolean> continuePath = new Out<Boolean>(true);
	public int stepIndex;
	public PathSingleStepE(int stepIndex) {
		this.stepIndex = stepIndex;
	}
}
