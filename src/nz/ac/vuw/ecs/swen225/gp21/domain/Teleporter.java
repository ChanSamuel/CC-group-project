package nz.ac.vuw.ecs.swen225.gp21.domain;

import java.util.HashMap;
import java.util.Map;
/**
 * The teleporter is a special tile that will move an entity to the tile one after the link tile in the direction the GameObject was moving. 
 * when it is entered. The board loader must ensure the teleporter links (map entries) have been created
 * Example teleport behavior:
 * Before:
 * |_|_|_|
 * |_|O|_|
 * |#|#|#|
 * |_|O|_|
 * |^|C|_|
 * After:
 * |_|C|_|
 * |_|O|_|
 * |#|#|#|
 * |_|O|_|
 * |_|_|_|
 * @author Benjamin
 *
 */
public class Teleporter extends Terrain {
	/**
	 * Record of one-way teleporter links
	 */
	public static Map<Coord, Coord> links = new HashMap<>();
	
	@Override
	public Terrain nextType(GameObject o) { return this; }

	@Override
	public void entityEntered(GameObject o) {
		Coord destination = getDestinationCoord(o);
		o.w.moveObject(o, destination);
	}

	@Override
	public boolean canEntityGoOn(GameObject o) { 
		Coord destination = getDestinationCoord(o);
		if(!o.w.isCoordValid(destination)) return false;
		return o.w.getTileAt(destination).canEntityGoOnTile(o);
	}

	@Override
	public char boardChar() { return 'O'; }
	/**
	 * Gets the coordinate of the tile the teleporter will try to move the GameObject to
	 * Based on the direction the GameObject is facing
	 * @param o The Object moving onto the teleporter
	 * @return the coordinate of the tile the teleporter will move the object to
	 */
	private Coord getDestinationCoord(GameObject o) {
		Coord destination = links.get(o.currentTile.location); //if the teleport tile has no link the level was not initialized properly
		if(destination == null) throw new IllegalStateException("Teleporter at "+o.currentTile.location+" has no link!");
		switch(o.dir) {
		case EAST:
			return destination.right();
		case NORTH:
			return destination.up();
		case SOUTH:
			return destination.down();
		case WEST:
			return destination.left();
		default: 
			throw new RuntimeException("Objects moving into teleporter must have a direction to signal where they are going! ->"+o.toString());
		}
	}
	

}
