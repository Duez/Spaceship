package game;

import game.events.EventsGenerator;
import game.rooms.CommandRoom;
import game.rooms.DefenseRoom;
import game.rooms.EngineRoom;
import game.rooms.LifeSupport;
import game.rooms.PowerSupply;
import game.rooms.Room;
import game.rooms.WeaponRoom;


public class Game {
	
	/* Rooms :
	 * 0 - Command center
	 * 1 - Life Support
	 * 2 - Defense room
	 * 3 - Power supply
	 * 4 - Engine room
	 * 5 - Weapons room
	 */

	public static final Game g = new Game();
	public static final GameInterface gi = new GameInterface(g);
	
	public Room[] rooms;
	public CommandRoom command;
	public LifeSupport life;
	public DefenseRoom defense;
	public PowerSupply power;
	public EngineRoom engine;
	public WeaponRoom weapon;
	
	private EventsGenerator generator;

	
	private Game () {
		this.initGame();
	}
	
	/**
	 * Init variables to start the game.
	 */
	public void initGame() {
		this.generator = new EventsGenerator();
		this.initRooms();
		this.engine.startTravel = 0;
		for (Room r : this.rooms)
			r.setEvent(null);
	}

	private void initRooms() {
		this.rooms = new Room[6];
		this.command = new CommandRoom();
		this.rooms[0] = this.command;
		this.life = new LifeSupport();
		this.rooms[1] = this.life;
		this.defense = new DefenseRoom();
		this.rooms[2] = this.defense;
		this.power = new PowerSupply();
		this.rooms[3] = this.power;
		this.engine = new EngineRoom();
		this.rooms[4] = this.engine;
		this.weapon = new WeaponRoom();
		this.rooms[5] = this.weapon;
	}

	/**
	 * Start the game;
	 */
	public void startGame() {
		this.engine.startTravel = new Long(System.currentTimeMillis()).intValue();
		this.generator.start();
	}
	
	/**
	 * Stop the game and re-init.
	 */
	public void stopGame() {
		this.generator.setFinished(true);
		this.initGame();
	}

	/**
	 * Get all infos about the spaceship.
	 * @return All infos separated by ';'
	 */
	public String getInfos() {
		String infos = "" + this.engine.timeToGoal() + ";" + this.life.getOxygenLevel() + ";" + this.generator.getProba();
		
		for (Room r : this.rooms)
			infos += ";" + r.getEvent();
		
		return infos;
	}
	
	public EventsGenerator getGenerator() {
		return generator;
	}
	
}
