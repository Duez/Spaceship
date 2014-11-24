package global;

import java.util.ArrayList;
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

	public static EventsGenerator generator; 
	
	private static double proba = 0.1;
	private boolean ended;
	
	private static final Class<?>[] eventsClass = {Aliens.class, Fire.class, Hack.class,
		ElectricFailure.class, Meteor.class, NoSignal.class}; 
	
	public EventsGenerator() {
		this.ended = false;
	}
	
	@Override
	public void run() {
		while (!this.ended) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			if (this.ended)
				break;
			
			double rand = Math.random();
			
			if (rand <= EventsGenerator.proba) {
				List<Room> rooms = new ArrayList<Room>(Ship.ship.getAllRooms());
				rooms.remove(Ship.ship.getCommand());
				Collections.shuffle(rooms);
				Room r = rooms.get(0);
				Event e = this.generateEvent();
				
				if (r.getEvent() == null)
					r.setEvent(e);
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
	
	public void setFinished(boolean ended) {
		this.ended = ended;
	}
	
	public boolean isEnded() {
		return ended;
	}
	
	public double getProba() {
		return proba;
	}
	
	public void setProba(double proba) {
		EventsGenerator.proba = proba;
	}

}
