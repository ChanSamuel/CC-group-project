package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.items.KeyItem;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Chip;

/**
 * Gives Chip a Copper key when entered.
 *
 * @author sansonbenj 300482847
 *
 */
public final class CopperKey extends KeyTile {
  /**
   * Store the instance of Copper key terrain here.
   */
  private static CopperKey instance = new CopperKey();

  /**
   * Get an instance of Copper key terrain.
   *
   * @return instance of Copper key terrain
   */
  public static CopperKey getInstance() {
    return instance;
  }

  /**
   * Create Copper key terrain.
   */
  private CopperKey() {
  }

  @Override
  public Terrain entityEntered(GameObject o) {
    if (o instanceof Chip) {
      ((Chip) o).addItem(new KeyItem("Copper"));
    }
    return this;
  }

  @Override
  public void undoEntityActions(GameObject o) {
    if (o instanceof Chip) {
      ((Chip) o).removeItem(new KeyItem("Copper"));
    }
  }

  @Override
  public String toString() {
    return super.toString() + " Copper";
  }
}
