package global;

import java.io.File;

import rooms.Room;
import services.GameManagment;
import services.RoomSolver;
import services.ShipState;

import com.httpSimpleRest.serveur.ClientThread;
import com.httpSimpleRest.serveur.Serveur;
import com.httpSimpleRest.services.ServiciesIndex;

public class Game {
	
	private Status status;

	public Game() {
		this.status = Status.READY;
		Ship.ship = new Ship();
		EventsGenerator.generator = new EventsGenerator();
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
		this.status = Status.STOPED;
		
		return true;
	}
	
	public boolean reset () {
		this.stop();
		Ship.ship = new Ship();
		EventsGenerator.generator = new EventsGenerator();
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
		
		ClientThread.verbose = false;
		server.start();
	}
	
}
