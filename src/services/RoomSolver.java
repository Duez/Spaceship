package services;

import global.Ship;

import java.util.Map;

import rooms.Room;

import com.httpSimpleRest.services.Service;

public class RoomSolver implements Service {

	@Override
	public StringBuffer getAnswer(Map<String, String> args) {
		if (args.containsKey("room")) {
			Room r = null;
			switch (args.get("room")) {
			case "computer":
				r = Ship.ship.getComputer();
				break;
			case "regulation":
				r = Ship.ship.getRegulation();
				break;
			case "weapons":
				r = Ship.ship.getWeapons();
				break;
			case "life":
				r = Ship.ship.getLife();
				break;
			case "engine":
				r = Ship.ship.getEngine();
				break;

			default:
				break;
			}
			
			if (r != null && r.getEvent() != null)
				r.solveEvent();
		}
		return new StringBuffer();
	}

}
