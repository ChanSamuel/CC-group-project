package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.items.KeyItem;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Chip;

/**
 * Gold door can be opened by chip. Chip must have a gold key to open the gold
 * door.
 *
 * @author sansonbenj 300482847
 *
 */
public final class GoldDoor extends Door {
  /**
   * Store the instance of Gold door terrain here. I think this helps save memory
   */
  private static GoldDoor instance = new GoldDoor();

  /**
   * Get an instance of Gold Door terrain.
   *
   * @return instance of gold door terrain
   */
  public static GoldDoor getInstance() {
    return instance;
  }

  /**
   * Create terrain of the Gold door type.
   */
  private GoldDoor() {
  }

  @Override
  public Terrain entityEntered(GameObject o) {
    if (o instanceof Chip) {
      ((Chip) o).removeItem(new KeyItem("Gold"));
    } else {
      throw new RuntimeException("Non Chip object entered locked door! ->" + this + " & " + o);
    }
    return this;
  }

  @Override
  public void undoEntityActions(GameObject o) {
    if (o instanceof Chip) {
      ((Chip) o).addItem(new KeyItem("Gold"));
    } else {
      throw new RuntimeException("Non Chip object was on locked door! ->" + this + " & " + o);
    }
  }

  @Override
  public boolean canEntityGoOn(GameObject o) {
    if (o instanceof Chip) {
      return ((Chip) o).hasItem(new KeyItem("Gold"));
    }
    return false;
  }
}
