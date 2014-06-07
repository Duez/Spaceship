package game.events;

import game.Game;
import game.rooms.Room;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventsGenerator extends Thread {

	private double proba;
	private boolean finished;
	
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
			
			double rd = Math.random();
			
			if (rd <= this.proba) {
				List<Room> empty = this.getEmptyRooms();
				if (empty.size() > 0) {
					Collections.shuffle(empty);
					Event e = Event.values()[
					   new Double(Math.floor(Math.random() * Event.values().length)).intValue()
					];
					empty.get(0).setEvent(e);
				}
			}
		}
	}

	private List<Room> getEmptyRooms() {
		List<Room> empty = new ArrayList<>();
		
		for (int i=1 ; i<Game.g.rooms.length ; i++) {
			Room r = Game.g.rooms[i];
			if (r.getEvent() == null)
				empty.add(r);
		}

		return empty;
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
