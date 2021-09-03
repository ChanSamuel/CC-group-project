package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import java.util.HashMap;
import java.util.Map;

import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
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
public class Teleporter implements Terrain {
	/**
	 * Record of one-way teleporter links
	 */
	public static Map<Coord, Coord> links = new HashMap<>();
	
	private static Teleporter instance = new Teleporter();
	
	public static Teleporter getInstance() { return instance; }
	
	private Teleporter() {}
	
	@Override
	public Terrain nextType(GameObject o) { return this; }

	@Override
	public void entityEntered(GameObject o) {
		Coord destination = getDestinationCoord(o.getTile().location, o.dir);
		o.w.moveObject(o, destination);
	}

	@Override
	public boolean canEntityGoOn(GameObject o) { 
		Coord locationOfTeleport = o.dir.next(o.getTile().location); //we are one tile away from the teleporter at this point
		Coord destination = getDestinationCoord(locationOfTeleport, o.dir);
		if(!o.w.isCoordValid(destination)) return false;
		return o.w.getTileAt(destination).canEntityGoOnTile(o);
	}

	@Override
	public char boardChar() { return 'O'; }
	/**
	 * Gets the coordinate of the tile the teleporter will try to move the GameObject to
	 * Based on the direction the GameObject is facing
	 * @param c - the location of the teleporter being moved into
	 * @param dir - the direction the teleporter is being moved through
	 * @return the coordinate of the tile the teleporter will move the object to
	 */
	private Coord getDestinationCoord(Coord c, Direction dir) {
		Coord destination = links.get(c); //if the teleport tile has no link the level was not initialized properly
		if(destination == null) throw new IllegalStateException("Teleporter at "+c+" has no link!");
		return dir.next(destination);
	}
	
	@Override
	public String toString() { return super.toString()+"Teleporter"; }

}
