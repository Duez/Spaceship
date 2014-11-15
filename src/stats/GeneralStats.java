package stats;

import java.util.Observable;
import java.util.Observer;

import org.json.simple.JSONObject;


public class GeneralStats implements JsonTranslater, Observer {

	private long start;
	private long stop;

	@SuppressWarnings("unchecked")
	@Override
	public JSONObject toJson() {
		JSONObject jso = new JSONObject();
		jso.put("start", this.start);
		jso.put("stop", this.stop);
		
		return jso;
	}

	@Override
	public void update(Observable o, Object arg) {
		String command = (String)arg;
		switch (command) {
		case "start":
			this.start = System.currentTimeMillis();
			break;
		case "stop":
			this.stop = System.currentTimeMillis();
			break;

		default:
			break;
		}
	}
	
}
