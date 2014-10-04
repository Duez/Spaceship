package rooms;

public class ComputerCenter extends Room {

	@Override
	public void init() {}

	@Override
	protected void eventAppears() {}

	@Override
	protected void eventDesappears() {}

	public double getHackProbaIdx () {
		return this.currentEvent == null ? 0.5 : 1.0;
	}

	@Override
	protected void save() {}
	
}
