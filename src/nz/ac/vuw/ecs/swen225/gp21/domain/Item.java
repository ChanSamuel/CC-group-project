package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * Item interface defines the operations an item needs to be able to perform.
 *
 * @author sansonbenj 300482847
 *
 */
public interface Item extends Cloneable {
  /**
   * Get the colour of this item.
   *
   * @return the colour of this item
   */
  public String getColour();

  /**
   * Clone an item.
   *
   * @return a deep copy of the item.
   */
  public Item clone();
}
