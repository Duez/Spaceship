package global;

import rooms.CommandCenter;
import rooms.LifeSupport;

public class Ship {

	protected CommandCenter command;
	protected LifeSupport life;
	
	public Ship() {
		this.command = new CommandCenter();
		this.life = new LifeSupport();
	}
	
}
