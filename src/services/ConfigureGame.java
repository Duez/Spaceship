package services;

import global.EventsGenerator;
import global.Ship;

import java.util.Map;

import org.json.simple.JSONObject;

import com.httpSimpleRest.services.Service;

public class ConfigureGame implements Service {

	@SuppressWarnings("unchecked")
	@Override
	public StringBuffer getAnswer(Map<String, String> args) {
		JSONObject jso = new JSONObject();
		
		// Set probability of apparition of an event each second.
		if (args.containsKey("proba")) {
			String value = args.get("proba");
			try {
				Double d = Double.parseDouble(value);
				EventsGenerator.generator.setProba(d);
				jso.put("proba", true);
			} catch (NumberFormatException e) {
				jso.put("proba", false);
			}
		}
		
		// Set base time to goal (in seconds)
		if (args.containsKey("time")) {
			String value = args.get("time");
			try {
				Integer i = Integer.parseInt(value);
				Ship.ship.getEngine().setBaseTimeToGoal(i);
				jso.put("time", true);
			} catch (NumberFormatException e) {
				jso.put("time", false);
			}
		}
		
		// Set the decrease/increase of oxygen each second.
		if (args.containsKey("oxygen")) {
			String value = args.get("oxygen");
			try {
				Double d = Double.parseDouble(value);
				Ship.ship.getLife().setRate(d);
				jso.put("oxygen", true);
			} catch (NumberFormatException e) {
				jso.put("oxygen", false);
			}
		}
		
		return new StringBuffer(jso.toJSONString());
	}

}
