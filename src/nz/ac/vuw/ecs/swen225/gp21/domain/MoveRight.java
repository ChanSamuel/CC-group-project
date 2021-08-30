package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * Encapsulates an instruction to move an object right one tile
 * @author Benjamin
 *
 */
class MoveRight extends GameObjectMove{
	/**
	 * Create a new move right command
	 * @param o the object being moved by this command
	 */
	MoveRight(GameObject o){ super(o); }
	
	@Override
	public void execute(World w) { w.moveRight(moved); }

	@Override
	public void undo(World w) { w.moveLeft(moved); }

	@Override
	public String toString() { return super.toString()+"RIGHT"; }
}
