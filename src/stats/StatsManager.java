package stats;

import global.Ship;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import rooms.Room;

public class StatsManager implements JsonTranslater{
	
	private GeneralStats general;
	private List<RoomStat> rooms;
	
	public StatsManager() {
		this.general = new GeneralStats();
		Ship.ship.addObserver(this.general);
		
		this.rooms = new ArrayList<RoomStat>(Ship.ship.getAllRooms().size());
		for (Room r : Ship.ship.getAllRooms()) {
			RoomStat rs = new RoomStat(r);
			r.addObserver(rs);
			this.rooms.add(rs);
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJson() {
		JSONObject jso = new JSONObject();
		
		JSONArray array = new JSONArray();
		for (RoomStat rs : this.rooms)
			array.add(rs.toJson());
		jso.put("rooms", array);
		System.out.println(array.toJSONString());
		
		jso.put("general", this.general.toJson());
		
		return jso;
	}
	
	@SuppressWarnings("unchecked")
	public void save (File file) {
		JSONParser jsp = new JSONParser();
		JSONArray array = new JSONArray();
		
		if (file.exists())
			try {
				array = (JSONArray) jsp.parse(new FileReader(file));
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {}
		
		JSONObject currentStat = this.toJson();
		currentStat.put("id", array.size()+1);
		array.add(currentStat);
		
		try {
			file.delete();
			FileWriter fw = new FileWriter(file);
			fw.write(array.toJSONString());
			fw.close();
		} catch (IOException e) {
			System.err.println("Impossible to save stats in " + file.getPath());
		}
	}

}
