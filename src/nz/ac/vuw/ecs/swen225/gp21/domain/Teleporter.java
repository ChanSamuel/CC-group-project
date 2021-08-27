package nz.ac.vuw.ecs.swen225.gp21.domain;

import java.util.HashMap;
import java.util.Map;
/**
 * The teleporter is a special tile that will move an entity to a linked tile 
 * when it is entered. The board loader must ensure the teleporter links (map entries) have been created
 * @author Benjamin
 *
 */
public class Teleporter extends Terrain {
	/**
	 * Record one-way teleporter links
	 */
	public static Map<Coord, Coord> links = new HashMap<>();
	
	@Override
	public Terrain nextType(GameObject o) { return this; }

	@Override
	public void entityEntered(GameObject o) {
		//if the teleport tile has not link the level was not initialized properly
		Coord destination = links.get(o.currentTile.location);
		if(destination == null) throw new IllegalStateException("Teleporter at "+o.currentTile.location+" has no link!");
		o.w.moveObject(o, destination);	
	}

	@Override
	public boolean canEntityGoOn(GameObject o) { return true; }//any entity can go through the teleporter
	

}
