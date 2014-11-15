package stats;

import org.json.simple.JSONObject;

public interface JsonTranslater {

	/**
	 * Translate the object to the corresponding json
	 * @return the object translate json
	 */
	public JSONObject toJson ();
	
}
