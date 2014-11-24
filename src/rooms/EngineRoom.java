package rooms;

import global.Ship;

public class EngineRoom extends Room {
	
	public EngineRoom() {
	}

	@Override
	protected void eventAppears() {
		Ship.ship.speedDown(1);
	}

	@Override
	protected void eventDesappears() {
		Ship.ship.speedUp(1);
	}

	@Override
	public void init() {
		
	}
	
	@Override
	protected void save() {
		
	}

}
