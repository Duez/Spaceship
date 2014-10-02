package global;

import java.io.File;

import services.GameManagment;
import services.ShipState;

import com.httpSimpleRest.serveur.ClientThread;
import com.httpSimpleRest.serveur.Serveur;
import com.httpSimpleRest.services.ServiciesIndex;

public class Game {
	
	private Status status;

	public Game() {
		this.status = Status.STOPED;
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
		this.status = Status.STOPED;
		
		return true;
	}
	
	public boolean reset () {
		this.stop();
		Ship.ship = new Ship();
		EventsGenerator.generator = new EventsGenerator();
		
		return true;
	}
	
	public Status getStatus() {
		return status;
	}
	
	
	public enum Status {
		STOPED, STARTED, PAUSED;
	}
	
	
	
	
	public static void main(String[] args) {
		int port = args.length > 0 ? new Integer(args[0]) : 4242;
		Serveur serveur = new Serveur(new File("Clients"), port);
		ClientThread.verbose = true;
		
		ServiciesIndex index = serveur.getIndex();
		index.put("ship", new ShipState());
		
		Game g = new Game();
		index.put("managment", new GameManagment(g, serveur));
		
		serveur.start();
	}
	
}
