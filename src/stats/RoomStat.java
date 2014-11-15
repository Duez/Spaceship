package stats;

import java.util.Observable;
import java.util.Observer;
import java.util.Stack;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import events.Event;
import rooms.Room;

public class RoomStat implements Observer, JsonTranslater {
	
	private String name;
	private Stack<JSONObject> events;
	
	public RoomStat(Room r) {
		this.name = r.getClass().getSimpleName();
		this.events = new Stack<JSONObject>();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object arg) {
		if (!(o instanceof Room))
			return;
		Room room = (Room)o;
		
		if (!name.equals(room.getClass().getSimpleName())) {
			System.err.println("Invalid room name. It was " + name + " and now it's " + room.getClass().getSimpleName());
			System.exit(10);
		}
		
		String action = (String)arg;
		switch (action) {
		case "event":
			Event event = room.getEvent();
			JSONObject jsonEvent = new JSONObject();
			jsonEvent.put("name", event.getClass().getName());
			jsonEvent.put("start", event.getStartTime());
			this.events.push(jsonEvent);
			break;
			
		case "solved":
			JSONObject jso = this.events.pop();
			jso.put("stop", System.currentTimeMillis());
			this.events.push(jso);
			break;

		default:
			break;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJson() {
		JSONObject jso = new JSONObject();
		
		jso.put("name", this.name);
		JSONArray array = new JSONArray();
		for (JSONObject event : this.events)
			array.add(event);
		jso.put("events", array);
		
		return jso;
	}

}
