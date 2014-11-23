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
	
	// Oxygen
	private boolean increase;
	private long since;
	private double rate;
	private double currentOxygen;
	private int maxOxygen;
	
	// Rooms
	protected CommandCenter command;
	protected LifeSupport life;
	protected EngineRoom engine;
	protected ComputerCenter computer;
	protected WeaponsRoom weapons;
	protected RegulationRoom regulation;
	
	protected List<Room> allRooms;

	public State state;
	
	public Ship() {
		this.increase = true;
		this.since = 1;
		this.rate = 1;
		this.maxOxygen = 100;
		this.currentOxygen = 100;
		
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
	
	// ----------------------- State modification ----------------------- 
	
	public void start () {
		for (Room r : this.allRooms)
			r.init();
		this.state = State.STARTED;
		
		this.since = System.currentTimeMillis();
		
		this.setChanged();
		this.notifyObservers("start");
		this.setChanged();
		this.notifyObservers("oxygen");
	}
	
	public void stop () {
		this.currentOxygen = this.getOxygenLevel();
		this.state = State.ENDED;
		
		this.setChanged();
		this.notifyObservers("stop");
		this.setChanged();
		this.notifyObservers("oxygen");
	}
	
	public enum State {
		READY, STARTED, ENDED, KILLED;
	}
	
	// ----------------------- Oxygen -----------------------
	
	public int getOxygenLevel () {
		if (this.state == State.ENDED)
			return new Double(this.currentOxygen).intValue();
		
		double level = this.currentOxygen;
		long duration = (System.currentTimeMillis() - this.since) / 1000;
		
		if (this.increase)
			level = Math.min(this.maxOxygen, new Double(level + duration*this.rate).intValue());
		else
			level = Math.max(0, new Double(level - duration*this.rate).intValue());
		
		return new Double(level).intValue();
	}
	
	public void setOxygenRate(double rate) {
		this.currentOxygen = this.getOxygenLevel();
		this.since = System.currentTimeMillis();
		
		this.rate = rate;
		
		if (this.state == State.STARTED) {
			this.setChanged();
			this.notifyObservers("oxygen");
		}
	}
	
	public void increaseOxygen () {
		this.currentOxygen = this.getOxygenLevel();
		this.since = System.currentTimeMillis();
		
		this.increase = true;
		
		this.setChanged();
		this.notifyObservers("oxygen");
	}
	
	public void decreaseOxygen () {
		this.currentOxygen = this.getOxygenLevel();
		this.since = System.currentTimeMillis();
		
		this.increase = false;
		
		this.setChanged();
		this.notifyObservers("oxygen");
	}
	
	public int getMaxOxygen() {
		return maxOxygen;
	}
	
	public synchronized void setMaxOxygen(int maxOxygen) {
		if (this.state != State.STARTED)
			this.currentOxygen = maxOxygen;
		this.maxOxygen = maxOxygen;
	}
	
	public double getOxygenRate() {
		return rate;
	}
	
	// ----------------------- Rooms -----------------------
	
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
}
