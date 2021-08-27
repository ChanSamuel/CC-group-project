package nz.ac.vuw.ecs.swen225.gp21.domain;
/**
 * The exit lock terrain type blocks the player from reaching the exit until all treasure has been collected
 * @author Benjamin
 *
 */
public class ExitLock extends Terrain {

	@Override
	public Terrain nextType(GameObject o) { return new Free(); }

	@Override
	public void entityEntered(GameObject o) { throw new RuntimeException("Entity "+o+" entered the exit lock tile");	}

	@Override
	public boolean canEntityGoOn(GameObject o) { return false; } //no objects can enter this type of tile

	@Override
	public char boardChar() { return 'X'; }

}
