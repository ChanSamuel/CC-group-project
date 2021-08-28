package nz.ac.vuw.ecs.swen225.gp21.domain;
/**
 * The information tile contains a help message.
 * When chip enters the info tile, it attempts to fire an event to the
 * world object.
 * @author Benjamin
 *
 */
public final class Info extends Terrain {
	/**
	 * The message that is displayed by this tile.
	 */
	private String message;
	/**
	 * Create a new Info terrain type
	 * @param message
	 */
	Info(String message){
		this.message = message;
	}
	
	@Override
	public Terrain nextType(GameObject o) {return this;}

	@Override
	public void entityEntered(GameObject o) {
		o.w.enteredInfo(this.message);
	}

	@Override
	public boolean canEntityGoOn(GameObject o) {
		//Only Chip can enter the info tiles
		return (o instanceof Chip);
	}

	@Override
	public char boardChar() {return '?';}

}
