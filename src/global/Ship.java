package global;

import rooms.CommandCenter;
import rooms.ComputerCenter;
import rooms.EngineRoom;
import rooms.LifeSupport;
import rooms.RegulationRoom;
import rooms.WeaponsRoom;

public class Ship {

	protected CommandCenter command;
	protected LifeSupport life;
	protected EngineRoom engine;
	protected ComputerCenter computer;
	protected WeaponsRoom weapons;
	protected RegulationRoom regulation;
	
	public Ship() {
		this.command = new CommandCenter();
		this.life = new LifeSupport();
		this.engine = new EngineRoom();
	}
	
}
