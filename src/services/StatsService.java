package services;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import stats.NamesSaver;

import com.httpSimpleRest.services.Service;

public class StatsService implements Service {

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
		
		JSONArray scoresArray = new JSONArray();
		try {
			scoresArray = (JSONArray)jsp.parse(new FileReader(scores));
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		return new StringBuffer(scoresArray.toJSONString());
	}

}
