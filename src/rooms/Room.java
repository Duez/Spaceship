package rooms;

import events.Event;
import global.Ship;

public abstract class Room {

	protected Event currentEvent;
	
	public Event getEvent () {
		return this.currentEvent;
	}
	
	public void setEvent(Event event) {
		this.currentEvent = event;
		Ship.ship.getFreeRooms().remove(this);
		this.eventAppears();
		this.currentEvent.apply();
	}
	
	public abstract void init();
	protected abstract void eventAppears();
	protected abstract void eventDesappears();

	public void solveEvent () {
		this.eventDesappears();
		Event e = this.currentEvent;
		this.currentEvent = null;
		Ship.ship.getFreeRooms().add(this);
		
		e.solve();
	}
	
}
