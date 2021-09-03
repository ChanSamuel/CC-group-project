package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.Direction;

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
