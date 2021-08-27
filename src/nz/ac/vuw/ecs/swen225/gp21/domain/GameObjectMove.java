package nz.ac.vuw.ecs.swen225.gp21.domain;
/**
 * This type defines the fields that a command that moves a game object needs
 * I believe it has package-private visibility
 * @author Benjamin
 *
 */
abstract class GameObjectMove implements Command {
	/**
	 * The object being moved by this command
	 */
	protected GameObject moved;
	/**
	 * Create a new game
	 * @param o
	 */
	protected GameObjectMove(GameObject o){
		this.moved = o;
	}
	
	@Override
	public abstract void execute(World w);

	@Override
	public abstract void undo(World w);
	
	@Override
	public String toString() {
		return "MOVE: ["+moved+"] ";
	}

}
