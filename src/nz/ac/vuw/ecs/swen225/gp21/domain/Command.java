package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * Command encapsulates an instruction that can be sent from an outside source
 * to the world, to be executed
 * @author Benjamin
 *
 */
public interface Command {
	/**
	 * Complete this action
	 * @param w
	 */
	void execute(World w);
	/**
	 * Undo the action performed by this command
	 * @param w
	 */
	void undo(World w);
}
