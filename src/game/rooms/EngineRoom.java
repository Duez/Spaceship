package game.rooms;


public class EngineRoom extends Room {
	
	private static int baseTimeToGoal;
	public int startTravel;
	public int shift;
	
	public EngineRoom() {
		baseTimeToGoal = 4 * 60 * 1000;
		this.startTravel = 0;
		this.shift = 0;
	}

	@Override
	public void solveEvent() {
		this.shift += this.getEventDuration();
		super.solveEvent();
	}
	
	public int timeToGoal () {
		// Actual time
		int currentTime = new Long(System.currentTimeMillis()).intValue();
		// Time of the beginning of the travel.
		int travelBeginning = this.startTravel == 0 ? currentTime : this.startTravel;
		// Total shift
		int shift = this.getEventDuration() + this.shift;
		
		int total = Math.max(0, EngineRoom.baseTimeToGoal + shift - (currentTime - travelBeginning));
		
		return (total / 1000);
	}
	
}
