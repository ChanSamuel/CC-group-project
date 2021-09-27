package nz.ac.vuw.ecs.swen225.gp21.domain.objects;

import java.util.ArrayList;
import java.util.List;
import nz.ac.vuw.ecs.swen225.gp21.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.Item;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;
import nz.ac.vuw.ecs.swen225.gp21.domain.controllers.PlayerController;

/**
 * Chip is the GameObject that the player will control He can collect items in
 * his inventory.
 *
 * @author sansonbenj 300482847
 *
 */
public class Chip extends GameObject {
  /**
   * The number of items chip can fit in their inventory.
   */
  public static int INVETORY_SIZE = 8;
  /**
   * The number of treasure Chip has collected.
   */
  public int treasureCollected;
  /**
   * The items in Chips inventory.
   */
  List<Item> invetory;

  /**
   * Create a new Chip.
   */
  public Chip() {
    // I don't know how to make the chip input stream
    super(new PlayerController(), Direction.NORTH, null, null);
    treasureCollected = 0;
    invetory = new ArrayList<Item>(INVETORY_SIZE);
  }

  @Override
  public boolean canEntityGoOnTile(GameObject entity) {
    // monsters are allowed to enter the square that chip is on
    if (entity instanceof Monster) {
      return true;
    }
    return false;
  }

  @Override
  public void entityEnteredTile(GameObject entity) {
    // a monster stepped on the same square as chip, so the player lost
    if (!(entity instanceof Monster)) {
      throw new RuntimeException(
          "Non Monster entered the same tile as chip! at:" + currentTile.location + " ->" + entity);
    }
    currentTile.board.getWorld().playerLost();
  }

  @Override
  public void update(double elapsedTime, World w) {
    // I'm not sure if we should be getting the lower level objects to move
    // themselves?
    // or {currently} send a command back up to the top level class to move the
    // object for us?
    controller.update(w, this, elapsedTime).execute(w);
  }

  /**
   * This method is called when Chip collects a treasure chip.
   */
  public void collectedChip() {
    treasureCollected++;
    currentTile.board.getWorld().collectedChip();
  }

  /**
   * This method is called when Chip picks up an item.
   *
   * @param item the item chip picked up
   */
  public void addItem(Item item) {
    this.invetory.add(item);
    currentTile.board.getWorld().playerGainedItem(item);
  }

  /**
   * Determine if chip is holding a certain item.
   *
   * @param item the item being checked
   * @return true if the item is in Chip's inventory
   */
  public boolean hasItem(Item item) {
    return this.invetory.contains(item);
  }

  /**
   * Remove an item from chip's inventory.
   *
   * @param item item to remove
   */
  public void removeItem(Item item) {
    this.invetory.remove(item);
    currentTile.board.getWorld().playerConsumedItem(item);
  }

  @Override
  public String getName() {
    return getClass().getSimpleName();
  }

  @Override
  public char boardChar() {
    return 'C';
  }

  @Override
  public String toString() {
    return super.toString() + " " + getClass().getSimpleName() + printInvetory();
  }

  /**
   * Print the items Chip's Inventory.
   *
   * @return a summary of Chips inventory
   */
  private String printInvetory() {
    StringBuilder answer = new StringBuilder();
    answer.append(" Chip's Invetory: [");
    for (Item i : invetory) {
      answer.append(i + ", ");
    }
    answer.append("]");
    return answer.toString();
  }

}
