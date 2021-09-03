package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Chip;

/**
 * The treasure tile terrain type represents a tile with treasure on it
 * chip must collect all the treasure to unlock the exit lock (step on all tiles with this terrain type)
 * Only Chip can enter this type of tile
 * @author Benjamin
 *
 */
public class Treasure implements Terrain {
	
	private static Treasure instance = new Treasure();
	
	public static Treasure getInstance() { return instance; }
	
	private Treasure() {}

	@Override
	public Terrain nextType(GameObject o) {
		//may need to call something here to signify one less treasure
		return Free.getInstance();
	}

	@Override
	public boolean canEntityGoOn(GameObject o) {
		//only chip is allowed on these tiles
		if(o instanceof Chip) return true;
		return false;
	}
	
	@Override
	public void entityEntered(GameObject o) {
		if(!(o instanceof Chip)) throw new RuntimeException("Non-chip entity entered treasure tile! ->"+o);
		Chip player = (Chip) o;
		player.collectedChip();
	}

	@Override
	public char boardChar() { return 'c'; }
	
	@Override
	public String toString() { return super.toString()+"Treasure"; }

}
