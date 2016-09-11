package jsettlers.logic.movable.strategies.events;

import jsettlers.logic.buildings.military.IOccupyableBuilding;
import jsettlers.logic.movable.stackedStateMachine.ExternalEvent;

public class OccupyBuildingE extends ExternalEvent {
	public IOccupyableBuilding building;
	public OccupyBuildingE(IOccupyableBuilding building) {
		this.building = building;
	}
}
