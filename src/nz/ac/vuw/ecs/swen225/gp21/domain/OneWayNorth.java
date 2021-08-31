package nz.ac.vuw.ecs.swen225.gp21.domain;
/**
 * One way tiles can only be traversed in a single direction
 * So they can only be entered of the object is facing the right direction
 * and they can only be left if facing the right direction
 * @author Benjamin
 *
 */
public final class OneWayNorth extends OneWay {
	/**
	 * Create a new One way North terrain
	 */
	public OneWayNorth() {
		super(Direction.NORTH);
	}

	@Override
	public char boardChar() {
		return '^';
	}
}
