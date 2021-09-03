package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Chip;

/**
 * The information tile contains a help message.
 * When chip enters the info tile, it attempts to fire an event to the
 * world object.
 * @author Benjamin
 *
 */
public final class Info implements Terrain {
	
	private static Info instance = new Info("No message");
	
	public static Info getInstance() { return instance; }
	
	private Info(String message) {
		if(message == null) throw new IllegalArgumentException("Message must not be null");
		this.message = message;
	}
	
	/**
	 * The message that is displayed by this tile.
	 */
	private final String message;
	/**
	 * Create a new Info terrain type
	 * @param message
	 */
	public static void setInfoText(String message){
		instance = new Info(message);
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
	
	@Override
	public String toString() { return super.toString()+"Info tile: "+message; }

}
