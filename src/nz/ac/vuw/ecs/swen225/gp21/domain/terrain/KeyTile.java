package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Chip;

/**
 * Base class for the various key tiles to derive from.
 *
 * @author sansonbenj 300482847
 *
 */
public abstract class KeyTile implements Terrain {

  @Override
  public Terrain nextType(GameObject o) {
    if (o instanceof Chip) {
      return Free.getInstance();
    } else {
      return this;
    }
  }

  @Override
  public abstract Terrain entityEntered(GameObject o);

  @Override
  public boolean canEntityGoOn(GameObject o) {
    return true;
  } // anyone can go on this tile

  @Override
  public char boardChar() {
    return 'k';
  }

  @Override
  public String toString() {
    return "Key tile";
  }

}
