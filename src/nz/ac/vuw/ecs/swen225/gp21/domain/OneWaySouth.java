package nz.ac.vuw.ecs.swen225.gp21.domain;
/**
 * The one way south terrain can only be travesed in a downwards
 * direction of travel.
 * @author Benjamin
 *
 */
public final class OneWaySouth extends OneWay {
	/**
	 * Create a new one way south terrain
	 */
	public OneWaySouth() {
		super(Direction.SOUTH);
	}

	@Override
	public char boardChar() {
		return 'v';
	}

}
