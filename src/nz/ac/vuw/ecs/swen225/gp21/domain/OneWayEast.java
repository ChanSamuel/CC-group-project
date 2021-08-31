package nz.ac.vuw.ecs.swen225.gp21.domain;
/**
 * TODO
 * @author Benjamin
 *
 */
public final class OneWayEast extends OneWay {
	/**
	 * TODO
	 */
	public OneWayEast() {
		super(Direction.EAST);
	}
	
	@Override
	public char boardChar() {
		return '>';
	}

}
