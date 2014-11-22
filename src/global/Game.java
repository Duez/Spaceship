package global;

import global.Ship.State;

import java.io.File;

import rooms.Room;
import services.GameManagment;
import services.RoomSolver;
import services.ShipState;
import services.StatsService;
import stats.StatsManager;

import com.httpSimpleRest.serveur.ClientThread;
import com.httpSimpleRest.serveur.Serveur;
import com.httpSimpleRest.services.ServiciesIndex;

public class Game {
	
	private Status status;
	private StatsManager sm;

	public Game() {
		this.status = Status.READY;
		Ship.ship = new Ship();
		EventsGenerator.generator = new EventsGenerator();
		this.sm = new StatsManager();
	}
	
	public boolean start () {
		if (EventsGenerator.generator.isEnded())
			return false;
		
		Ship.ship.start();
		EventsGenerator.generator.start();
		this.status = Status.STARTED;
		
		return true;
	}
	
	public boolean stop () {
		EventsGenerator.generator.setFinished(true);
		for (Room r : Ship.ship.allRooms)
			r.end();
		
		if (Ship.ship.state == State.ENDED) {
			File directory = new File("data");
			if (!directory.exists())
				directory.mkdir();
			this.sm.save(new File("data/stats.json"));
		} else
			Ship.ship.state = State.KILLED;

		this.status = Status.STOPED;
		System.out.println(Ship.ship.state);
		
		return true;
	}
	
	public boolean reset () {
		if (this.status != Status.STOPED)
			this.stop();
		Ship.ship = new Ship();
		EventsGenerator.generator = new EventsGenerator();
		this.sm = new StatsManager();
		this.status = Status.READY;
		
		return true;
	}
	
	public Status getStatus() {
		return status;
	}
	
	
	public enum Status {
		STOPED, STARTED, READY;
	}
	
	
	
	
	public static void main(String[] args) {
		int port = args.length > 0 ? new Integer(args[0]) : 4242;
		Serveur server = new Serveur(new File("Clients"), port);
		ClientThread.verbose = true;

		Game g = new Game();
		
		ServiciesIndex index = server.getIndex();
		index.put("solve", new RoomSolver());
		index.put("ship", new ShipState(g));
		index.put("managment", new GameManagment(g, server));
		index.put("stats", new StatsService());
		
		ClientThread.verbose = false;
		server.start();
	}
	
}
