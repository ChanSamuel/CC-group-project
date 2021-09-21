package nz.ac.vuw.ecs.swen225.gp21.domain.terrain;

import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Chip;

/**
 * The information tile contains a help message. When chip enters the info tile,
 * it attempts to fire an event to the world object.
 *
 * @author Benjamin
 *
 */
public final class Info implements Terrain {
  /**
   * Store the only instance of info tile here. Save memory. Currently this means
   * levels can only have one Info tile :\
   */
  private static Info instance = new Info("No message");

  /**
   * Get an instance of the info tile.
   *
   * @return instance of info tile
   */
  public static Info getInstance() {
    return instance;
  }

  /**
   * make an info tile.
   *
   * @param message message this tile provides to the player.
   */
  private Info(String message) {
    if (message == null) {
      throw new IllegalArgumentException("Message must not be null");
    }
    this.message = message;
  }

  /**
   * The message that is displayed by this tile.
   */
  private final String message;

  /**
   * Change the Info terrain instance.
   *
   * @param message message displayed to player
   */
  public static void setInfoText(String message) {
    instance = new Info(message);
  }

  @Override
  public Terrain nextType(GameObject o) {
    return this;
  }

  @Override
  public Terrain entityEntered(GameObject o) {
    o.getTile().board.getWorld().enteredInfo(this.message);
    return this;
  }

  @Override
  public void entityExited(GameObject o) {
    o.getTile().board.getWorld().leftInfo();
  }

  @Override
  public void undoEntityActions(GameObject o) {
  }

  @Override
  public boolean canEntityGoOn(GameObject o) {
    // Only Chip can enter the info tiles
    return (o instanceof Chip);
  }

  @Override
  public char boardChar() {
    return '?';
  }

  @Override
  public String toString() {
    return "Info tile: " + message;
  }

}
