package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * Encapsulates an instruction to move Chip down one tile
 * @author Benjamin
 *
 */
class MoveDown implements Command {
	
	@Override
	public void execute(World w) { w.moveDown(); }

	@Override
	public void undo(World w) { w.moveUp(); }

}
