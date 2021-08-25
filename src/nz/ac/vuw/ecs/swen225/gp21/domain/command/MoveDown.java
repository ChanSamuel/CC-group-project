package nz.ac.vuw.ecs.swen225.gp21.domain.command;

import nz.ac.vuw.ecs.swen225.gp21.domain.World;
/**
 * Encapsulates an instruction to move Chip down one tile
 * @author Benjamin
 *
 */
public class MoveDown implements Command {

	@Override
	public void execute(World w) { w.moveDown(); }

	@Override
	public void undo(World w) { w.moveUp(); }

}
