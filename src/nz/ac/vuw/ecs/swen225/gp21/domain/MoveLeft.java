package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * Encapsulates an instruction to move an object left one tile
 * @author Benjamin
 *
 */
class MoveLeft extends GameObjectMove {
	/**
	 * Create a new move left command
	 * @param o the object being moved by this command
	 */
	MoveLeft(GameObject o){ super(o); }
	
	@Override
	public void execute(World w) { w.moveLeft(moved); }

	@Override
	public void undo(World w) { w.moveRight(moved);	}

}
