package services;

import global.Ship;

import java.util.Map;

import com.httpSimpleRest.services.Service;

public class RoomSolver implements Service {

	@Override
	public StringBuffer getAnswer(Map<String, String> args) {
		if (args.containsKey("room"))
			switch (args.get("room")) {
			case "computer":
				Ship.ship.getComputer().solveEvent();
				break;
			case "regulation":
				Ship.ship.getRegulation().solveEvent();
				break;
			case "weapons":
				Ship.ship.getWeapons().solveEvent();
				break;
			case "life":
				Ship.ship.getLife().solveEvent();
				break;
			case "engine":
				Ship.ship.getEngine().solveEvent();
				break;

			default:
				break;
			}
		return null;
	}

}
