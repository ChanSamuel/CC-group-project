package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * Encapsulates an instruction to move Chip left one tile
 * @author Benjamin
 *
 */
class MoveLeft implements Command {

	@Override
	public void execute(World w) { w.moveLeft(); }

	@Override
	public void undo(World w) { w.moveRight();	}

}
