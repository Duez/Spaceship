package rooms;

import events.Event;

public abstract class Room {

	protected Event currentEvent;
	
	public Event getEvent () {
		return this.currentEvent;
	}
	
	public void setEvent(Event event) {
		this.currentEvent = event;
		this.eventAppears();
	}
	
	protected abstract void eventAppears();
	protected abstract void eventDesappears();

	public void solveEvent () {
		this.currentEvent.solve();
		this.eventDesappears();
		this.currentEvent = null;
	}
	
}
