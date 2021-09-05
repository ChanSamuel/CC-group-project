package nz.ac.vuw.ecs.swen225.gp21.domain.commands;

import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;

/**
 * Encapsulates an instruction to move an object left one tile
 * @author Benjamin
 *
 */
public class MoveLeft extends GameObjectMove {
	/**
	 * Create a new move left command
	 * @param o the object being moved by this command
	 */
	public MoveLeft(GameObject o){ super(o); }
	
	@Override
	public void execute(World w) { w.moveLeft(moved); }

	@Override
	public void undo(World w) { w.moveRight(moved);	}

	@Override
	public String toString() { return super.toString()+"LEFT"; }
}
