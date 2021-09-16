package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.items.KeyItem;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Chip;

/**
 * Green door is opened by keys that have the Green color attribute.
 *
 * @author Benjamin
 *
 */
public final class GreenDoor extends Door {
  /**
   * Store the instance of Green door terrain here.
   */
  private static GreenDoor instance = new GreenDoor();

  /**
   * Get an instance of Green door terrain.
   *
   * @return instance of Green door terrain
   */
  public static GreenDoor getInstance() {
    return instance;
  }

  /**
   * Create Green door terrain.
   */
  private GreenDoor() {
  }

  @Override
  public void entityEntered(GameObject o) {
    if (o instanceof Chip) {
      ((Chip) o).removeItem(new KeyItem("Green"));
    } else {
      throw new RuntimeException("Non Chip object entered locked door! ->" + this + " & " + o);
    }
  }

  @Override
  public void undoEntityActions(GameObject o) {
    if (o instanceof Chip) {
      ((Chip) o).addItem(new KeyItem("Green"));
    } else {
      throw new RuntimeException("Non Chip object was on locked door! ->" + this + " & " + o);
    }
  }

  @Override
  public boolean canEntityGoOn(GameObject o) {
    if (o instanceof Chip) {
      return ((Chip) o).hasItem(new KeyItem("Green"));
    }
    return false;
  }

  @Override
  public String toString() {
    return super.toString() + " Green";
  }
}
