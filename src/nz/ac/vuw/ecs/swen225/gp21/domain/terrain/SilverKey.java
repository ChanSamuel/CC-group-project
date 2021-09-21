package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.items.KeyItem;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Chip;

/**
 * Gives Chip a Silver key when entered.
 *
 * @author Benjamin
 *
 */
public final class SilverKey extends KeyTile {
  /**
   * Store the instance of Silver key terrain here.
   */
  private static SilverKey instance = new SilverKey();

  /**
   * Get an instance of Silver key terrain.
   *
   * @return instance of Silver key terrain
   */
  public static SilverKey getInstance() {
    return instance;
  }

  /**
   * Create Silver key terrain.
   */
  private SilverKey() {
  }

  @Override
  public Terrain entityEntered(GameObject o) {
    if (o instanceof Chip) {
      ((Chip) o).addItem(new KeyItem("Silver"));
    }
    return this;
  }

  @Override
  public void undoEntityActions(GameObject o) {
    if (o instanceof Chip) {
      ((Chip) o).removeItem(new KeyItem("Silver"));
    }
  }

  @Override
  public String toString() {
    return super.toString() + " Silver";
  }
}
