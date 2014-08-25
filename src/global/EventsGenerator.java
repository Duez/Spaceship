package global;

import java.util.Collections;
import java.util.List;

import rooms.Room;
import events.Aliens;
import events.ElectricFailure;
import events.Event;
import events.Fire;
import events.Hack;
import events.Meteor;
import events.NoSignal;

public class EventsGenerator  extends Thread {

	private double proba;
	private boolean finished;
	
	private static final Class<?>[] eventsClass = {Aliens.class, Fire.class, Hack.class,
		ElectricFailure.class, Meteor.class, NoSignal.class}; 
	
	public EventsGenerator() {
		this.proba = 0.1;
		this.finished = false;
	}
	
	@Override
	public void run() {
		while (!this.finished) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (this.finished)
				break;
			
			double rand = Math.random();
			
			if (rand <= this.proba) {
				List<Room> empty = Ship.ship.getFreeRooms();
				if (empty.size() > 0) {
					Collections.shuffle(empty);
					Event e = this.generateEvent();
					empty.get(0).setEvent(e);
				}
			}
		}
	}
	
	public Event generateEvent() {
		@SuppressWarnings("unchecked")
		Class<? extends Event> c = (Class<? extends Event>) EventsGenerator.eventsClass[
		   new Double(Math.floor(Math.random() * EventsGenerator.eventsClass.length)).intValue()
		];
		Event e = null;
		try {
			e = c.newInstance();
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		}
		return e;
	}
	
	public void setFinished(boolean finished) {
		this.finished = finished;
	}
	
	public double getProba() {
		return proba;
	}
	
	public void setProba(double proba) {
		this.proba = proba;
	}

}
