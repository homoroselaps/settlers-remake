package jsettlers.logic.movable.stackedStateMachine;

public abstract class DoneEvent extends Event { }

class SuccessEvent extends DoneEvent { }
class FailureEvent extends DoneEvent { }
