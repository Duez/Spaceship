package game.events;

import game.Game;
import game.rooms.Room;

public enum Event {

	Asteroid, Bomb, Alien, Fire, Hack;

	public void apply(Room room, Game game) {
		//System.err.println("No effect for the moment");
	}
	
}
