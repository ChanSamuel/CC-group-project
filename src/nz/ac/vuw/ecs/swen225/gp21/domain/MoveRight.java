package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * Encapsulates an instruction to move Chip right one tile
 * @author Benjamin
 *
 */
class MoveRight implements Command {

	@Override
	public void execute(World w) { w.moveRight(); }

	@Override
	public void undo(World w) { w.moveLeft(); }

}
