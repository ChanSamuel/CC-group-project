package nz.ac.vuw.ecs.swen225.gp21.domain.commands;

import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;

/**
 * Encapsulates an instruction to move an object down one tile
 * @author Benjamin
 *
 */
public class MoveDown extends GameObjectMove{
	/**
	 * Create a new move down command
	 * @param o the object being moved by this command
	 */
	public MoveDown(GameObject o){ super(o); }
	
	@Override
	public void execute(World w) { w.moveDown(moved); }

	@Override
	public void undo(World w) { w.moveUp(moved); }
	
	@Override
	public String toString() { return super.toString()+"DOWN"; }

}
