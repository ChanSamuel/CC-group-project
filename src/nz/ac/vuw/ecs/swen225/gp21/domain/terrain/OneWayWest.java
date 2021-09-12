package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.Direction;

/**
 * TODO
 * @author Benjamin
 *
 */
public final class OneWayWest extends OneWay {
	
	private static OneWayWest instance = new OneWayWest();
	
	public static OneWayWest getInstance() { return instance; }

	/**
	 * TODO
	 */
	private OneWayWest() {
		super(Direction.WEST);
	}
	
	@Override
	public char boardChar() {
		return '<';
	}

}
