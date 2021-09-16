package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.items.KeyItem;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Chip;

/**
 * Silver door is opened by keys that have the Silver color attribute.
 *
 * @author Benjamin
 *
 */
public final class SilverDoor extends Door {
  /**
   * Store the instance of Silver door terrain here.
   */
  private static SilverDoor instance = new SilverDoor();

  /**
   * Get an instance of Silver door terrain.
   *
   * @return instance of Silver door terrain
   */
  public static SilverDoor getInstance() {
    return instance;
  }

  /**
   * Create Silver door terrain.
   */
  private SilverDoor() {
  }

  @Override
  public void entityEntered(GameObject o) {
    if (o instanceof Chip) {
      ((Chip) o).removeItem(new KeyItem("Silver"));
    } else {
      throw new RuntimeException("Non Chip object entered locked door! ->" + this + " & " + o);
    }
  }

  @Override
  public void undoEntityActions(GameObject o) {
    if (o instanceof Chip) {
      ((Chip) o).addItem(new KeyItem("Silver"));
    } else {
      throw new RuntimeException("Non Chip object was on locked door! ->" + this + " & " + o);
    }
  }

  @Override
  public boolean canEntityGoOn(GameObject o) {
    if (o instanceof Chip) {
      return ((Chip) o).hasItem(new KeyItem("Silver"));
    }
    return false;
  }

  @Override
  public String toString() {
    return super.toString() + " Silver";
  }
}
