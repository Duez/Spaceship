package stats;

import global.Ship;

import java.util.Observable;
import java.util.Observer;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class TravelObserver implements Observer {

	private JSONArray saves;

	public TravelObserver() {
		this.saves = new JSONArray();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void update(Observable o, Object arg) {
		if ("travel".equals(arg)) {
			JSONObject save = new JSONObject();
			save.put("timestamp", System.currentTimeMillis());
			save.put("value", Ship.ship.getTimeToGoal());
			this.saves.add(save);
		}
	}
	
	public JSONArray toJson() {
		return this.saves;
	}

}
