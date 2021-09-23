package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.items.KeyItem;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Chip;

/**
 * Copper door is opened by keys that have the Copper color attribute.
 *
 * @author Benjamin
 *
 */
public final class CopperDoor extends Door {
  /**
   * Store the instance of Copper door terrain here.
   */
  private static CopperDoor instance = new CopperDoor();

  /**
   * Get an instance of Copper door terrain.
   *
   * @return instance of Copper door terrain
   */
  public static CopperDoor getInstance() {
    return instance;
  }

  /**
   * Create Copper door terrain.
   */
  private CopperDoor() {
  }

  @Override
  public Terrain entityEntered(GameObject o) {
    if (o instanceof Chip) {
      ((Chip) o).removeItem(new KeyItem("Copper"));
    } else {
      throw new RuntimeException("Non Chip object entered locked door! ->" + this + " & " + o);
    }
    return this;
  }

  @Override
  public void undoEntityActions(GameObject o) {
    if (o instanceof Chip) {
      ((Chip) o).addItem(new KeyItem("Copper"));
    } else {
      throw new RuntimeException("Non Chip object was on locked door! ->" + this + " & " + o);
    }
  }

  @Override
  public boolean canEntityGoOn(GameObject o) {
    if (o instanceof Chip) {
      // return ((Chip) o).hasItem(new KeyItem("Copper"));
      return ((Chip) o).hasItem(new KeyItem("Copper"));
    }
    return false;
  }

  @Override
  public String toString() {
    return super.toString() + " Copper";
  }
}
