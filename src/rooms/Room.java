package rooms;

import java.util.Observable;

import events.Event;

public abstract class Room extends Observable {

	protected Event currentEvent;
	protected boolean ended = true;
	
	public Event getEvent () {
		return this.currentEvent;
	}
	
	public void setEvent(Event event) {
		this.currentEvent = event;
		this.eventAppears();
		this.currentEvent.apply();
		
		this.setChanged();
		this.notifyObservers("event");
		//System.out.println(this.getClass().toString() + " : " + this.getEvent().getClass().toString() + " added");
	}
	
	public abstract void init();
	protected abstract void eventAppears();
	protected abstract void eventDesappears();
	
	public void end () {
		this.save();
		this.ended = true;
	}
	protected abstract void save();

	public void solveEvent (long time) {
		//System.out.println("Room " + this.getClass().getName() + "   Time : " + time + "  /  " + this.getEvent().getStartTime());
		if (time != this.currentEvent.getStartTime())
			return;
		
		this.setChanged();
		this.notifyObservers("solved");
		
		//System.out.println(this.getClass().toString() + " : " + this.getEvent().getClass().toString() + " solved");
		this.eventDesappears();
		Event e = this.currentEvent;
		this.currentEvent = null;
		
		e.solve();
	}
	
}
