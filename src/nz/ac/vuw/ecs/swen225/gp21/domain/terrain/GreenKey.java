package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.items.KeyItem;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Chip;

/**
 * Gives Chip a green key when entered.
 *
 * @author Benjamin
 *
 */
public final class GreenKey extends KeyTile {
  /**
   * Store the instance of green key terrain here.
   */
  private static GreenKey instance = new GreenKey();

  /**
   * Get an instance of green key terrain.
   *
   * @return instance of green key terrain
   */
  public static GreenKey getInstance() {
    return instance;
  }

  /**
   * Create green key terrain.
   */
  private GreenKey() {
  }

  @Override
  public Terrain entityEntered(GameObject o) {
    if (o instanceof Chip) {
      ((Chip) o).addItem(new KeyItem("Green"));
    }
    return this;
  }

  @Override
  public void undoEntityActions(GameObject o) {
    if (o instanceof Chip) {
      ((Chip) o).removeItem(new KeyItem("Green"));
    }
  }

  @Override
  public String toString() {
    return super.toString() + " Green";
  }
}
