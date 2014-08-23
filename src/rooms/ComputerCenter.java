package rooms;

public class ComputerCenter extends Room {

	@Override
	protected void init() {}

	@Override
	protected void eventAppears() {}

	@Override
	protected void eventDesappears() {}

	public double getHackProbaIdx () {
		return this.currentEvent == null ? 0.5 : 1.0;
	}
	
}
