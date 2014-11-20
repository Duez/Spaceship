package stats;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class NamesSaver {
	
	private JSONArray loadFile (File file) throws FileNotFoundException, IOException, ParseException {
		JSONArray jsa = null;
		if (!file.exists())
			return new JSONArray();
		
		JSONParser jsp = new JSONParser();
		jsa = (JSONArray) jsp.parse(new FileReader(file));
		return jsa;
	}
	
	private void saveFile (JSONArray scores, File file) {
		try {
			FileWriter fw = new FileWriter(file);
			fw.write(scores.toJSONString());
			fw.close();
		} catch (IOException e) {
			System.err.println("Impossible to save score file");
		}
	}
	
	private JSONObject findScore (int id, JSONArray array) {
		if (id == -1)
			id = array.size();
		if (id > array.size()) {
			System.err.println(id + " is out of bound");
			return new JSONObject();
		}
		
		for (Object o : array) {
			JSONObject jso = (JSONObject)o;
			int current = ((Number)jso.get("id")).intValue();
			if (id == current)
				return jso;
		}
		
		System.err.println(id + " id not found");
		return new JSONObject();
	}

	/**
	 * Add names to the last score registered.
	 * @param file
	 * @param names
	 */
	public void addNames(File file, JSONArray names) {
		this.addNames(file, -1, names);
	}
	
	/**
	 * Add names to the score with ref id
	 * @param file
	 * @param id
	 * @param names
	 */
	@SuppressWarnings("unchecked")
	public void addNames(File file, int id, JSONArray names) {
		JSONArray scores = null;
		try {
			scores = this.loadFile(file);
		} catch (IOException | ParseException e) {
			System.err.println("Impossible to load score file " + file.getPath());
			return;
		}
		
		JSONObject jso = this.findScore(id, scores);
		jso.put("names", names);
		
		this.saveFile(scores, file);
	}
	
}
