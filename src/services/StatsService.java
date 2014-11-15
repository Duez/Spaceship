package services;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.httpSimpleRest.services.Service;

public class StatsService implements Service {

	@Override
	public StringBuffer getAnswer(Map<String, String> arg0) {
		JSONArray answer = new JSONArray();
		JSONParser jsp = new JSONParser();
		
		File stats = new File("data/stats.json");
		if (stats.exists()) {
			try {
				answer = (JSONArray)jsp.parse(new FileReader(stats));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			if (arg0.containsKey("names")) {
				try {
					JSONArray names = (JSONArray) jsp.parse(arg0.get("names"));
					this.addAndSaveNames (answer, names);
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
		
		return new StringBuffer(answer.toJSONString());
	}

	@SuppressWarnings("unchecked")
	private void addAndSaveNames(JSONArray stats, JSONArray names) {
		JSONObject higher = new JSONObject();
		higher.put("id", -1);
		
		for (Object obj : stats) {
			JSONObject current = (JSONObject)obj;
			Number idCurrent = (Number)current.get("id");
			Number idHigher = (Number)higher.get("id");
			
			if (idCurrent.intValue() > idHigher.intValue())
				higher = current;
		}
		
		if (((Number)higher.get("id")).intValue() > 0) {
			higher.put("names", names);
			
			File statsFile = new File("data/stats.json");
			statsFile.delete();
			try {
				FileWriter fw = new FileWriter(statsFile);
				fw.write(stats.toJSONString());
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
