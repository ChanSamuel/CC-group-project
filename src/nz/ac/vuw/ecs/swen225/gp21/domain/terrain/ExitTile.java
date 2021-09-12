package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Chip;

/**
 * The exit tile is the final goal tile
 * If Chip enters a tile of this type, they win!
 * @author Benjamin
 *
 */
public class ExitTile implements Terrain {
	/**
	 * Store instance of exit tile here
	 */
	private static ExitTile instance = new ExitTile();
	/**
	 * Get an instance of exit tile terrain
	 * @return instance of exit tile terrain
	 */
	public static ExitTile getInstance() { return instance; }
	/**
	 * Create exit tile terrain type
	 */
	private ExitTile() {}

	@Override
	public Terrain nextType(GameObject o) { return this; }

	@Override
	public void entityEntered(GameObject o) {
		o.w.enteredExit();//TODO might need to defer this state?
	}
	
	@Override
	public void undoEntityActions(GameObject o) {
		// TODO not sure what the true consequences of entering the exit are at this time
	}

	@Override
	public boolean canEntityGoOn(GameObject o) {
		if(o instanceof Chip) return true;
		return false;
	}

	@Override
	public char boardChar() { return 'e'; }
	
	@Override
	public String toString() { return "Exit"; }
	
}
