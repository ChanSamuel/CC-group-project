package nz.ac.vuw.ecs.swen225.gp21.domain.items;

import java.util.Objects;
import nz.ac.vuw.ecs.swen225.gp21.domain.Item;

/**
 * The key item can be placed in Chip's Inventory It is consumed when chip opens
 * a door with it.
 *
 * @author sansonbenj 300482847
 *
 */
public final class KeyItem implements Item {
  /**
   * The color of this key.
   */
  String colour;

  /**
   * Create a new key item.
   *
   * @param c the color of the door this key unlocks
   */
  public KeyItem(String c) {
    if (c == null || c.isEmpty()) {
      throw new IllegalArgumentException("Key must have a color!");
    }
    this.colour = c;
  }

  /**
   * Make a key with default field.
   */
  public KeyItem() {
  }

  @Override
  public String toString() {
    return "Key: [" + colour + "]";
  }

  @Override
  public String getColour() {
    return colour;
  }

  @Override
  public int hashCode() {
    return Objects.hash(colour);
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) {
      return true;
    }
    if (obj == null) {
      return false;
    }
    if (getClass() != obj.getClass()) {
      return false;
    }
    KeyItem other = (KeyItem) obj;
    return Objects.equals(colour, other.colour);
  }

  @Override
  public Item clone() {
    return new KeyItem(new String(this.colour));
  }

}
