package nz.ac.vuw.ecs.swen225.gp21.domain;
/**
 * TODO
 * @author Benjamin
 *
 */
public final class OneWayWest extends OneWay {
	/**
	 * TODO
	 */
	public OneWayWest() {
		super(Direction.WEST);
	}
	
	@Override
	public char boardChar() {
		return '<';
	}

}
