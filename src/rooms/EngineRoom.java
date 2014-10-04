package rooms;

public class EngineRoom extends Room {
	
	private long startTime;
	private long switchTime;
	
	private long timeToGoal;
	
	private long saveTime;
	
	public EngineRoom() {
		this.ended = false;
		this.switchTime = 0;
		this.timeToGoal = 60*4;
	}

	@Override
	protected void eventAppears() {
		// Nothing appends
	}

	@Override
	protected void eventDesappears() {
		long duration = this.currentEvent.getDuration();
		this.switchTime += duration;
	}

	@Override
	public void init() {
		this.startTime = System.currentTimeMillis();
	}
	
	public long timeToGoal () {
		if (this.ended)
			return this.saveTime;
		
		long duration = 0;
		if (this.currentEvent != null)
			duration = this.currentEvent.getDuration();
		
		long passedTime = 0;
		if (this.startTime != 0)
			passedTime = System.currentTimeMillis() - (this.startTime + this.switchTime + duration);
		
		return timeToGoal - (passedTime / 1000);
	}
	
	public void setBaseTimeToGoal (long duration) {
		this.timeToGoal = duration;
	}
	
	public long getBaseTimeToGoal() {
		return timeToGoal;
	}
	
	@Override
	protected void save() {
		this.saveTime = this.timeToGoal();
	}

}
