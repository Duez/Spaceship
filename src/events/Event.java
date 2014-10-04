package events;

public abstract class Event {

	protected long start;
	
	public Event() {
		this.start = System.currentTimeMillis();
	}

	public abstract void apply();
	public abstract void solve();
	
	public long getStartTime() {
		return this.start;
	}
	
	public long getDuration () {
		return System.currentTimeMillis() - this.start;
	}

}
