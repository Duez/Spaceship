package rooms;

public class EngineRoom extends Room {
	
	private long startTime;
	private long switchTime;
	
	private long timeToGoal;
	
	public EngineRoom() {
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
		long duration = 0;
		if (this.currentEvent != null)
			duration = this.currentEvent.getDuration();
		
		long passedTime = this.startTime + this.switchTime + duration - System.currentTimeMillis();
		return timeToGoal - passedTime;
	}
	
	public void setBaseTimeToGoal (long duration) {
		this.timeToGoal = duration;
	}

}
