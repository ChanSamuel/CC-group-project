package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp21.domain.Item;
import nz.ac.vuw.ecs.swen225.gp21.domain.items.KeyItem;

class ItemTest {

  @Test
  void testEquals() {
    Item key = new KeyItem("Blue");
    Item other = null;
    Item friend = new KeyItem("Blue");
    Item clone = key.clone();
    assertFalse(key.equals(other));
    assertTrue(key.equals(friend));
    assertTrue(key.equals(clone));
    assertFalse(key == clone);
    assertFalse(key.equals(new Object()));
    assertTrue(key.equals(key));
  }

  @Test
  void testParamValidation() {
    assertThrows(IllegalArgumentException.class, () -> {
      new KeyItem(null);
    });
    assertThrows(IllegalArgumentException.class, () -> {
      new KeyItem("");
    });
  }

}
