package rooms;

public class RegulationRoom extends Room {

	@Override
	protected void init() {}

	@Override
	protected void eventAppears() {}

	@Override
	protected void eventDesappears() {}

	public double getFireProbaIdx () {
		return this.currentEvent == null ? 0.5 : 1.0;
	}

}
