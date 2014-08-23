package global;

import java.util.ArrayList;
import java.util.List;

import rooms.CommandCenter;
import rooms.ComputerCenter;
import rooms.EngineRoom;
import rooms.LifeSupport;
import rooms.RegulationRoom;
import rooms.Room;
import rooms.WeaponsRoom;

public class Ship {

	public static Ship ship = new Ship();
	
	protected CommandCenter command;
	protected LifeSupport life;
	protected EngineRoom engine;
	protected ComputerCenter computer;
	protected WeaponsRoom weapons;
	protected RegulationRoom regulation;
	
	protected List<Room> allRooms;
	protected List<Room> freeRooms;
	
	public Ship() {
		this.allRooms = new ArrayList<>();
		this.freeRooms = new ArrayList<>();
		
		this.command = new CommandCenter();
		this.allRooms.add(this.command);
		
		this.life = new LifeSupport();
		this.allRooms.add(this.life);
		this.freeRooms.add(this.life);
		
		this.engine = new EngineRoom();
		this.allRooms.add(this.engine);
		this.freeRooms.add(this.engine);
		
		this.computer = new ComputerCenter();
		this.allRooms.add(this.computer);
		this.freeRooms.add(this.computer);
		
		this.weapons = new WeaponsRoom();
		this.allRooms.add(this.weapons);
		this.freeRooms.add(this.weapons);
		
		this.regulation = new RegulationRoom();
		this.allRooms.add(this.regulation);
		this.freeRooms.add(this.regulation);
	}
	
	public List<Room> getAllRooms() {
		return allRooms;
	}
	
	public List<Room> getFreeRooms() {
		return freeRooms;
	}
	
	public CommandCenter getCommand() {
		return command;
	}
	
	public ComputerCenter getComputer() {
		return computer;
	}
	
	public EngineRoom getEngine() {
		return engine;
	}
	
	public LifeSupport getLife() {
		return life;
	}
	
	public RegulationRoom getRegulation() {
		return regulation;
	}
	
	public WeaponsRoom getWeapons() {
		return weapons;
	}
	
}
