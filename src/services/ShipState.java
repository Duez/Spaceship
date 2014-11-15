package services;

import events.Event;
import global.Game;
import global.Ship;
import global.Ship.State;

import java.util.Map;

import org.json.simple.JSONObject;

import rooms.Room;

import com.httpSimpleRest.services.Service;

public class ShipState implements Service {
	
	private Game game;
	
	public ShipState(Game g) {
		this.game = g;
	}

	@SuppressWarnings("unchecked")
	@Override
	public StringBuffer getAnswer(Map<String, String> arg0) {
		Ship s = Ship.ship;
		
		if (s.state == State.ENDED || s.state == State.KILLED)
			return new StringBuffer("{}");
		
		if (s.getEngine().timeToGoal() == 0 || s.getLife().getOxygenLevel() == 0) {
			Ship.ship.stop();
			this.game.stop();
		}
		
		JSONObject answer = new JSONObject();
		answer.put("oxygen", s.getLife().getOxygenLevel());
		answer.put("time", s.getEngine().timeToGoal());
		
		JSONObject rooms = new JSONObject();
		for (Room room : s.getAllRooms()) {
			JSONObject roomJso = new JSONObject();
			
			rooms.put(room.getClass().getName(), roomJso);
			
			if (room.getEvent() != null) {
				JSONObject eventJso = new JSONObject();
				Event e = room.getEvent();
				eventJso.put("name", e.getClass().getName());
				eventJso.put("duration", e.getDuration());
				eventJso.put("start", e.getStartTime());
				roomJso.put("event", eventJso);
			}
		}
		answer.put("rooms", rooms);
		
		return new StringBuffer(answer.toJSONString());
	}

}
