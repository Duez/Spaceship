package rooms;

import global.Ship;

public class LifeSupport extends Room {
	
	public LifeSupport() {}

	@Override
	protected synchronized void eventAppears() {
		Ship.ship.decreaseOxygen();
	}
	
	@Override
	protected synchronized void eventDesappears() {
		Ship.ship.increaseOxygen();
	}

	@Override
	public void init() {
		
	}

	@Override
	protected void save() {
		
	}
}
