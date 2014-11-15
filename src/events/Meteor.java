package events;

import rooms.LifeSupport;
import global.Ship;

public class Meteor extends Event {

	@Override
	public void apply() {
		LifeSupport life = Ship.ship.getLife();
		life.setMaxOxygen(life.getMaxOxygen()-10);
	}

	@Override
	public void solve() {
		LifeSupport life = Ship.ship.getLife();
		life.setMaxOxygen(life.getMaxOxygen()+10);
	}

}
