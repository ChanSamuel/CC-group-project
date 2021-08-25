package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * Encapsulates an instruction to move Chip up one tile
 * @author Benjamin
 *
 */
class MoveUp implements Command {
	/**
	 * Create a new move up command
	 */
	public MoveUp() {}
	//TODO 	should the command store a reference to the world which it receives from the app?
	//		Or should it get the reference when the World executes the command?
	@Override
	public void execute(World w) {
		w.moveUp();
	}

	@Override
	public void undo(World w) {
		w.moveDown();
	}

}
