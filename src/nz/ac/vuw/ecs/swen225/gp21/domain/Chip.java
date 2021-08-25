package nz.ac.vuw.ecs.swen225.gp21.domain;

class Chip extends GameObject {
	/**
	 * @param w
	 */
	protected Chip(World w) {
		super(w);
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
		c.update(elapsedTime);
	}

	@Override
	protected String getName() {
		return getClass().getName();
	}
	
	

}
