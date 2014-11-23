package services;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import stats.NamesSaver;

import com.httpSimpleRest.services.Service;

public class StatsService implements Service {

	/**
	 * All in json format
	 * 
	 * - No arguments : return all plays in short mode.
	 * 
	 * - id : return details for the game corresponding
	 * 
	 * - names : add names to the last game
	 * 
	 * - id + names : add names to the game with correct id.
	 */
	
	@Override
	public StringBuffer getAnswer(Map<String, String> arg0) {
		JSONParser jsp = new JSONParser();
		File scores = new File("data/stats.json");
		
		if (arg0.containsKey("names")) {
			NamesSaver ns = new NamesSaver();
			boolean correct = true;
			
			int id = new Integer(arg0.get("id"));
			JSONArray names = null;
			try {
				System.out.println(arg0.get("names"));
				names = (JSONArray)jsp.parse(arg0.get("names"));
			} catch (ParseException e) {
				System.err.println("Parameter names is not a correct json");
				correct = false;
			}
			
			if (correct)
				if (arg0.containsKey("id"))
					ns.addNames(scores, id, names);
				else
					ns.addNames(scores, names);
		}
		
		try {
			if (arg0.containsKey("id")) {
				int id = new Integer(arg0.get("id"));
				File idFile = new File("data/" + id + ".json");
				if (idFile.exists())
					return new StringBuffer(((JSONObject)jsp.parse(new FileReader(idFile))).toJSONString());
			} else
				return new StringBuffer(((JSONArray)jsp.parse(new FileReader(scores))).toJSONString());
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return new StringBuffer("{}");
	}

}
