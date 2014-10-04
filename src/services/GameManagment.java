package services;

import global.EventsGenerator;
import global.Game;
import global.Ship;

import java.util.Map;

import org.json.simple.JSONObject;

import com.httpSimpleRest.serveur.Serveur;
import com.httpSimpleRest.services.Service;

public class GameManagment implements Service {
	
	private Game game;
	private Serveur server;

	public GameManagment(Game g, Serveur serv) {
		this.game = g;
		this.server = serv;
	}

	@SuppressWarnings("unchecked")
	@Override
	public StringBuffer getAnswer(Map<String, String> args) {
		JSONObject jso = new JSONObject();
		
		// Modify game status
		if (args.containsKey("status")) {
			String value = args.get("status");
			switch (value) {
			case "start":
				this.game.start();
				break;
			case "stop":
				this.game.stop();
				break;
			case "reset":
				this.game.reset();
				break;
			case "shutdown":
				this.server.stopServer();

			default:
				break;
			}
		}
		jso.put("status", this.game.getStatus().name());
		
		// Set probability of apparition of an event each second.
		if (args.containsKey("proba")) {
			String value = args.get("proba");
			try {
				Double d = Double.parseDouble(value);
				if (d > 0)
					EventsGenerator.generator.setProba(d);
			} catch (NumberFormatException e) {
				
			}
		}
		jso.put("proba", EventsGenerator.generator.getProba());
		
		// Set base time to goal (in seconds)
		if (args.containsKey("baseTime")) {
			String value = args.get("baseTime");
			try {
				Integer i = Integer.parseInt(value);
				if (i > 0)
					Ship.ship.getEngine().setBaseTimeToGoal(i);
			} catch (NumberFormatException e) {
				
			}
		}
		jso.put("baseTime", Ship.ship.getEngine().getBaseTimeToGoal());
		
		// Set the decrease/increase of oxygen each second.
		if (args.containsKey("oxygenRate")) {
			String value = args.get("oxygenRate");
			try {
				Double d = Double.parseDouble(value);
				if (d > 0)
					Ship.ship.getLife().setRate(d);
			} catch (NumberFormatException e) {
				
			}
		}
		jso.put("oxygenRate", Ship.ship.getLife().getRate());
			
		return new StringBuffer(jso.toJSONString());
	}

}
