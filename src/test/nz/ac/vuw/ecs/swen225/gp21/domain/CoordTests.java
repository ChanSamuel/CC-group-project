package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;

/**
 * These tests are for the coordinate type.
 *
 * @author sansonbenj 300482847
 *
 */
class CoordTests {

  /**
   * Test all aspects of the equals methods in Coord
   */
  @Test
  void equalsTest() {
    Coord one = new Coord(0, 0);
    Coord two = new Coord(1, 1);
    Coord three = new Coord(0, 0);
    Coord four = new Coord();
    Coord five = null;
    Coord six = new Coord(1, 0);
    Coord seven = new Coord(0, 1);
    Coord eight = one.right().up().left().down();
    Coord nine = new Coord(one.getRow(), one.getColumn());
    Object dummy = new Object();

    assertFalse(one.equals(two));
    assertFalse(two.equals(one));

    assertTrue(one.equals(one));
    assertTrue(one.equals(three));
    assertFalse(one.equals(dummy));
    assertTrue(one.equals(four));
    assertFalse(one.equals(five));
    assertFalse(one.equals(six));
    assertFalse(one.equals(seven));
    assertTrue(one.equals(eight));
    assertTrue(one.equals(nine));
    assertEquals(one.hashCode(), eight.hashCode());
    assertNotEquals(one.hashCode(), two.hashCode());
    assertEquals(one.toString(), nine.toString());
    assertNotEquals(one.toString(), two.toString());
  }
}
