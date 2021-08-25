package nz.ac.vuw.ecs.swen225.gp21.domain;
/**
 * Chip is the GameObject that the player will control
 * He can collect items in his inventory
 * @author Benjamin
 *
 */
class Chip extends GameObject {
	/**
	 * Create a new Chip
	 * @param w the game world that chip exists in.
	 */
	protected Chip(World w) {
		super(w, new PlayerController(w));
	}
	
	@Override
	protected boolean canEntityGoOnTile(GameObject entity) {
		//monsters are allowed to enter the square that chip is on TODO
		// if (entity instanceof monster) { return true } else { return false }
		return false;
	}

	@Override
	protected void entityEnteredTile(GameObject entity) {
		// a monster stepped on the same square as chip, so the player lost
		//if(entity instanceof monster) w.gameOver()  TODO
		// else doNothing.
	}

	@Override
	protected void update(double elapsedTime) {
		//I'm not sure if we should be getting the lower level objects to move themselves?
		//or {currently} send a command back up to the top level class to move the object for us?
		c.update(elapsedTime).execute(w);
	}

	@Override
	protected String getName() {
		return getClass().getName();
	}
	
	

}
