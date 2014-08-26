package services;

import global.Game;

import java.util.Map;

import serveur.Serveur;

public class GameStatus implements Service {
	
	private Game game;
	private Serveur server;

	public GameStatus(Game g, Serveur serv) {
		this.game = g;
		this.server = serv;
	}

	@Override
	public StringBuffer getAnswer(Map<String, String> args) {
		StringBuffer sb = new StringBuffer();
		
		if (args.containsKey("status")) {
			String value = args.get("status");
			boolean b;
			switch (value) {
			case "start":
				b = this.game.start();
				sb.append(b);
				break;
			case "stop":
				b = this.game.stop();
				sb.append(b);
				break;
			case "reset":
				b = this.game.reset();
				sb.append(b);
				break;
			case "shutdown":
				this.server.stopServer();
				sb.append(true);

			default:
				sb.append(false);
				break;
			}
		} else {
			sb.append(false);
		}
			
		return sb;
	}

}
