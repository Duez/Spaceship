package rooms;

public class LifeSupport extends Room {
	
	private Snapshot snapshot;
	private double rate;
	
	public LifeSupport() {
		this.rate = 1.0;
		this.snapshot = new Snapshot();
		this.snapshot.isIncrising = true;
		this.snapshot.oxygenLevel = 100;
		this.snapshot.time = System.currentTimeMillis();
	}

	@Override
	protected void eventAppears() {
		Snapshot snap = new Snapshot();
		snap.isIncrising = false;
		snap.time = System.currentTimeMillis();
		
		int level = this.snapshot.oxygenLevel;
		long duration = System.currentTimeMillis() - this.snapshot.time;
		snap.oxygenLevel = Math.max(0, level + new Double(duration*this.rate).intValue());
		
		this.snapshot = snap;
	}
	
	@Override
	protected void eventDesappears() {
		Snapshot snap = new Snapshot();
		snap.isIncrising = true;
		snap.time = System.currentTimeMillis();
		
		int level = this.snapshot.oxygenLevel;
		long duration = System.currentTimeMillis() - this.snapshot.time;
		snap.oxygenLevel = Math.max(0, level - new Double(duration*this.rate).intValue());
		
		this.snapshot = snap;
	}

	public int getOxygenLevel () {
		int level = this.snapshot.oxygenLevel;
		long duration = System.currentTimeMillis() - this.snapshot.time;
		if (this.snapshot.isIncrising)
			level = Math.min(100, level + new Double(duration*this.rate).intValue());
		else
			level = Math.max(0, level - new Double(duration*this.rate).intValue());
		
		return level;
	}
	
	public void setRate(double rate) {
		this.rate = rate;
	}
	
	private class Snapshot {
		public boolean isIncrising;
		public long time;
		public int oxygenLevel;
	}
}
