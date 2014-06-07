package game.rooms;

import game.Game;
import game.events.Event;

public abstract class Room {

	//Command, Life, Defense, Power, Engine, Weapon;
	public final static int rooms = 6;
	
	protected Event event;
	protected long startEvent;

	/**
	 * Set an event in a room.
	 * @param r Room
	 * @param e Event
	 */
	public void setEvent (Event e) {
		synchronized (this) {
			this.event = e;
			this.startEvent = e == null ? 0 : System.currentTimeMillis();
		}
	}
	
	public void apply(Event e, Game game) {
		e.apply (this, game);
	}
	
	/**
	 * Get current event in a room and apply effects.
	 * @param r Room
	 * @return Current event in the room.
	 */
	public Event getEvent () {
		Event e = null;
		synchronized (this) {
			e = this.event;
		}
		
		if (e != null)
			this.apply (e, Game.g);
		
		return e;
	}

	/**
	 * Set null
	 * @param value
	 */
	public void solveEvent() {
		this.setEvent(null);
	}
	
	public int getEventDuration () {
		long currentTime = System.currentTimeMillis();
		long startEvent = this.startEvent == 0 ? currentTime : this.startEvent;
		return new Long(currentTime - startEvent).intValue();
	}
	
}
