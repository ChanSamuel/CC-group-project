package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * The no move command represents no movement
 * @author Benjamin
 *
 */
class NoMove implements Command {

	@Override
	public void execute(World w) {
		return;
	}

	@Override
	public void undo(World w) {
		return;
	}

}
