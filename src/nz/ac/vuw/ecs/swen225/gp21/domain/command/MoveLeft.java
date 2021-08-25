package nz.ac.vuw.ecs.swen225.gp21.domain.command;

import nz.ac.vuw.ecs.swen225.gp21.domain.World;
/**
 * Encapsulates an instruction to move Chip left one tile
 * @author Benjamin
 *
 */
public class MoveLeft implements Command {

	@Override
	public void execute(World w) { w.moveLeft(); }

	@Override
	public void undo(World w) { w.moveRight();	}

}
