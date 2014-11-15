package global;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import rooms.CommandCenter;
import rooms.ComputerCenter;
import rooms.EngineRoom;
import rooms.LifeSupport;
import rooms.RegulationRoom;
import rooms.Room;
import rooms.WeaponsRoom;

public class Ship extends Observable {

	public static Ship ship;
	
	protected CommandCenter command;
	protected LifeSupport life;
	protected EngineRoom engine;
	protected ComputerCenter computer;
	protected WeaponsRoom weapons;
	protected RegulationRoom regulation;
	
	protected List<Room> allRooms;

	public State state;
	
	public Ship() {
		this.allRooms = new ArrayList<>();
		
		this.command = new CommandCenter();
		this.allRooms.add(this.command);
		
		this.life = new LifeSupport();
		this.allRooms.add(this.life);
		
		this.engine = new EngineRoom();
		this.allRooms.add(this.engine);
		
		this.computer = new ComputerCenter();
		this.allRooms.add(this.computer);
		
		this.weapons = new WeaponsRoom();
		this.allRooms.add(this.weapons);
		
		this.regulation = new RegulationRoom();
		this.allRooms.add(this.regulation);
		
		this.state = State.READY;
	}
	
	public void start () {
		for (Room r : this.allRooms)
			r.init();
		this.state = State.STARTED;
		
		this.setChanged();
		this.notifyObservers("start");
	}
	
	public void stop () {
		this.state = State.ENDED;
		this.setChanged();
		this.notifyObservers("stop");
	}
	
	public List<Room> getAllRooms() {
		return allRooms;
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
	
	public enum State {
		READY, STARTED, ENDED, KILLED;
	}
	
}
