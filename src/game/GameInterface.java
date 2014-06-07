package game;

import serveur.Message;

public class GameInterface {

	private Game g;
	
	public GameInterface(Game g) {
		this.g = g;
	}

	public synchronized Message getMessage(String addr) {
		String msg = "";
		
		if (addr.startsWith("infos/")) {
			msg = this.g.getInfos();
		} else if (addr.startsWith("start/")) {
			this.g.startGame();
			msg = "";
		} else if (addr.startsWith("reset/")) {
			this.g.stopGame();
			msg = "";
		} else if (addr.startsWith("solve/")) {
			String cut = addr.substring(6);
			String sValue = "";
			while (cut.charAt(0) != '/') {
				sValue += cut.charAt(0);
				cut = cut.substring(1);
			}
			this.g.rooms[new Integer(sValue)].solveEvent();
		} else if (addr.startsWith("proba/")) {
			String[] split = addr.split("\\?")[1].split(",");
			double value = new Double(split[0]);
			this.g.getGenerator().setProba(value);
		}
		
		return new Message(msg, "text/plain", 200);
	}

}
