package rooms;

public class WeaponsRoom  extends Room {

	@Override
	protected void init() {}

	@Override
	protected void eventAppears() {}

	@Override
	protected void eventDesappears() {}

	public double getAliensProbaIdx () {
		return this.currentEvent == null ? 0.5 : 1.0;
	}
	
}
