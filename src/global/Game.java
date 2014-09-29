package global;

import java.io.File;

import com.httpSimpleRest.serveur.ClientThread;
import com.httpSimpleRest.serveur.Serveur;
import com.httpSimpleRest.services.ServiciesIndex;

import services.ConfigureGame;
import services.GameStatus;
import services.ShipState;

public class Game {

	public Game() {
		Ship.ship = new Ship();
		EventsGenerator.generator = new EventsGenerator();
	}
	
	public boolean start () {
		if (EventsGenerator.generator.isEnded())
			return false;
		
		Ship.ship.start();
		EventsGenerator.generator.start();
		
		return true;
	}
	
	public boolean stop () {
		EventsGenerator.generator.setFinished(true);
		return true;
	}
	
	public boolean reset () {
		Ship.ship = new Ship();
		EventsGenerator.generator = new EventsGenerator();
		return true;
	}
	
	
	
	
	public static void main(String[] args) {
		int port = args.length > 0 ? new Integer(args[0]) : 4242;
		Serveur serveur = new Serveur(new File("Clients"), port);
		ClientThread.verbose = true;
		
		ServiciesIndex index = serveur.getIndex();
		index.put("ship", new ShipState());
		index.put("config", new ConfigureGame());
		
		Game g = new Game();
		index.put("status", new GameStatus(g, serveur));
		
		serveur.start();
	}
	
}
