package rooms;

public class LifeSupport extends Room {
	
	private Snapshot snapshot;
	private double rate;
	
	private int maxOxygen;
	
	public LifeSupport() {
		this.rate = 1.0;
		this.snapshot = new Snapshot();
		this.snapshot.isIncrising = true;
		this.snapshot.oxygenLevel = 100;
		this.maxOxygen = 100;
	}

	@Override
	protected synchronized void eventAppears() {
		Snapshot snap = new Snapshot();
		snap.isIncrising = false;
		snap.time = System.currentTimeMillis();
		
		int level = this.snapshot.oxygenLevel;
		long duration = System.currentTimeMillis() - this.snapshot.time;
		snap.oxygenLevel = Math.max(0, level + new Double(duration*this.rate).intValue());
		
		this.snapshot = snap;
	}
	
	@Override
	protected synchronized void eventDesappears() {
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
	
	@Override
	protected void init() {
		this.snapshot.time = System.currentTimeMillis();
	}
	
	public int getMaxOxygen() {
		return maxOxygen;
	}
	
	public synchronized void setMaxOxygen(int maxOxygen) {
		this.snap();
		this.maxOxygen = maxOxygen;
	}
	
	
	private void snap() {
		Snapshot snap = new Snapshot();
		snap.isIncrising = this.snapshot.isIncrising;
		snap.oxygenLevel = this.getOxygenLevel();
		snap.time = System.currentTimeMillis();
		this.snapshot = snap;
	}

	private class Snapshot {
		public boolean isIncrising;
		public long time;
		public int oxygenLevel;
	}
}
