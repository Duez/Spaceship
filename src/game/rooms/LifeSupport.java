package game.rooms;

import game.events.Event;

public class LifeSupport extends Room {
	
	private long lastPing;
	private int oxygenLevel;
	
	public LifeSupport() {
		super();
		this.oxygenLevel = 100;
	}
	
	@Override
	public void setEvent(Event e) {
		super.setEvent(e);
		if (e != null)
			this.lastPing = this.startEvent;
	}

	public int getOxygenLevel() {
		if (this.oxygenLevel == 0)
			return 0;
		
		long current = System.currentTimeMillis();
		long last = this.lastPing == 0 ? current : this.lastPing;
		long durationMilli = current - last;
		if (durationMilli > 100000) durationMilli = 0;
		int durationSec = new Double(Math.round(new Double(durationMilli) / 1000.0)).intValue();
		
		if (this.event == null)
			this.oxygenLevel = Math.min(this.getMaxOxygen(), this.oxygenLevel+durationSec);
		else
			this.oxygenLevel = Math.max(0, Math.min(this.getMaxOxygen(), this.oxygenLevel-durationSec));
		if (durationSec > 0)
			this.lastPing += durationMilli;

		return this.oxygenLevel;
	}

	private int getMaxOxygen() {
		return 100;
	}

}
