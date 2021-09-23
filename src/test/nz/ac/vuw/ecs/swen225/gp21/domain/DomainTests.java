package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp21.domain.*;

/**
 * These tests test the functionality of the Domain package
 *
 * @author sansonbenj 300482847
 *
 */
class DomainTests {
  /**
   * The level these tests will use
   */
  static Level testLevel = new Level(
      // level dimensions
      10, 10,
      // tiles
      ".....c...." + ".........." + ".....c...." + ".........." + "......1..." + ".........."
          + ".........." + "......1..." + ".........X" + ".........E",
      // game objects
      "C........." + "..=......." + ".........." + ".........." + ".........." + ".........."
          + ".........." + ".........." + ".........." + "..........",
      // info tile message
      "No info");

  /**
   * Try creating the world object No exceptions should be thrown
   */
  @Test
  void createWorld() {
    boolean exception = false;
    try {
      World w = new TestWorld();
      System.out.println(w.toString());
    } catch (Exception e) {
      exception = true;
    }
    assertFalse(exception);
    System.out.println("\ntest one complete\n");
  }

  /**
   * Try to add some movement commands to the command queue No exceptions should
   * occur.
   */
  @Test
  void addMovePlayer() {
    boolean exception = false;
    try {
      World w = new TestWorld();
      w.loadLevelData(testLevel);
      w.doneLoading();
      System.out.println(w.toString());
      w.moveChipDown();
      System.out.println(w.toString());
      w.moveChipRight();
      System.out.println(w.toString());
      w.moveChipLeft();
      w.moveChipUp();
    } catch (Exception e) {
      exception = true;
    }
    assertFalse(exception);
    System.out.println("\ntest two complete\n");
  }

  /**
   * Try to simulate the world for one tick. No exceptions should be thrown
   */
  @Test
  void trySimulate() {
    boolean exception = false;
    try {
      World w = new TestWorld();
      w.loadLevelData(testLevel);
      w.doneLoading();
      System.out.println(w.toString());
      w.moveChipDown();
      System.out.println(w.toString());
      w.update(200);
      System.out.println(w.toString());
    } catch (Exception e) {
      exception = true;
    }
    assertFalse(exception);
    System.out.println("\ntest three complete\n");
  }

  /**
   * Simulate the world several times with no moves to execute No exceptions
   * should be thrown
   */
  @Test
  void tryMultipleSimulate() {
    boolean exception = false;
    try {
      World w = new TestWorld();
      w.loadLevelData(testLevel);
      w.doneLoading();
      for (int updates = 0; updates < 10; ++updates) {
        w.update(200);
      }
      System.out.println(w.toString());
    } catch (Exception e) {
      exception = true;
    }
    assertFalse(exception);
    System.out.println("\ntest four complete\n");
  }

  /**
   * Try to move up when already in the corner No Exceptions should occur
   */
  @Test
  void tryMoveUp() {
    boolean exception = false;
    try {
      World w = new TestWorld();
      w.loadLevelData(testLevel);
      w.doneLoading();
      w.moveChipUp();
      w.update(200);
      System.out.println(w.toString());
    } catch (Exception e) {
      e.printStackTrace();
      exception = true;
    }
    assertFalse(exception);
    System.out.println("\ntest five complete\n");
  }

  /**
   * Try moving to the exit No exceptions should be thrown
   */
  @Test
  void tryReachExit() {
    boolean exception = false;
    try {
      World w = new TestWorld();
      w.loadLevelData(testLevel);
      w.doneLoading();
      for (int row = 1; row < 10; row++) {
        w.moveChipDown();
        w.update(200);
      }
      System.out.println(w.toString());
      for (int col = 1; col < 10; col++) {
        w.moveChipRight();
        w.update(200);
      }
      System.out.println(w.toString());
      // assertTrue(w.isGameComplete());
    } catch (Exception e) {
      e.printStackTrace();
      exception = true;
    }
    assertFalse(exception);
    System.out.println("\ntest six complete\n");
  }

  /**
   * Try to push a block down Test must consume all player actions No exceptions
   * should occur
   */
  @Test
  void tryToMoveBlock() {
    boolean exception = false;
    try {
      World w = new TestWorld();
      w.loadLevelData(testLevel);
      w.doneLoading();
      w.moveChipRight();
      w.moveChipRight();
      w.moveChipDown();
      w.update(200);
      w.update(200);
      w.update(200);
      w.update(200);
      System.out.println(w);
      // the block is at (1,2)
      String expected = "Game is: Running\n" + "Is game over? -> false\n" + "PlayerQueue: \n"
          + "EMPTY\n" + "All entities: \n"
          + "GameObject: Chip facing->SOUTH at->Row: 1 Columns: 2 Chip Chip's Invetory: []\n"
          + "GameObject: Block facing->NONE at->Row: 2 Columns: 2 Block\n" + "\n" + "Board: \n"
          + "0|_|_|_|_|_|c|_|_|_|_|\n" + "1|_|_|C|_|_|_|_|_|_|_|\n" + "2|_|_|=|_|_|c|_|_|_|_|\n"
          + "3|_|_|_|_|_|_|_|_|_|_|\n" + "4|_|_|_|_|_|_|O|_|_|_|\n" + "5|_|_|_|_|_|_|_|_|_|_|\n"
          + "6|_|_|_|_|_|_|_|_|_|_|\n" + "7|_|_|_|_|_|_|O|_|_|_|\n" + "8|_|_|_|_|_|_|_|_|_|X|\n"
          + "9|_|_|_|_|_|_|_|_|_|e|\n";
      assertEquals(w.toString(), expected);
    } catch (Exception e) {
      e.printStackTrace();
      exception = true;
    }
    assertFalse(exception);
    System.out.println("\ntest seven complete\n");
  }

  /**
   * Move chip to try and collect some treasure No exceptions should occur
   */
  @Test
  void tryCollectTreasure() {
    boolean exception = false;
    try {
      World w = new TestWorld();
      w.loadLevelData(testLevel);
      w.doneLoading();
      for (int r = 0; r < 5; r++) {
        w.moveChipRight();
        w.update(200);
      }
      System.out.println(w);
      for (int d = 0; d < 2; d++) {
        w.moveChipDown();
        w.update(200);
      }
      System.out.println(w);
      String expected = "Game is: Running\n" + "Is game over? -> false\n" + "PlayerQueue: \n"
          + "EMPTY\n" + "All entities: \n"
          + "GameObject: Chip facing->SOUTH at->Row: 2 Columns: 5 Chip Chip's Invetory: []\n"
          + "GameObject: Block facing->NONE at->Row: 1 Columns: 2 Block\n" + "\n" + "Board: \n"
          + "0|_|_|_|_|_|_|_|_|_|_|\n" + "1|_|_|=|_|_|_|_|_|_|_|\n" + "2|_|_|_|_|_|C|_|_|_|_|\n"
          + "3|_|_|_|_|_|_|_|_|_|_|\n" + "4|_|_|_|_|_|_|O|_|_|_|\n" + "5|_|_|_|_|_|_|_|_|_|_|\n"
          + "6|_|_|_|_|_|_|_|_|_|_|\n" + "7|_|_|_|_|_|_|O|_|_|_|\n" + "8|_|_|_|_|_|_|_|_|_|_|\n"
          + "9|_|_|_|_|_|_|_|_|_|e|\n";
      assertEquals(w.toString(), expected);
    } catch (Exception e) {
      e.printStackTrace();
      exception = true;
    }
    assertFalse(exception);
    System.out.println("\ntest eight complete\n");
  }

  /**
   * Attempt to move chip through the teleporter tile No exceptions should occur
   */
  @Test
  void tryTeleportChip() {
    boolean exception = false;
    try {
      World w = new TestWorld(); //
      w.loadLevelData(testLevel);
      w.doneLoading();
      for (int r = 0; r < 6; r++) {
        w.moveChipRight();
        w.update(200);
      }
      System.out.println(w);
      for (int d = 0; d < 4; d++) {
        w.moveChipDown();
        w.update(200);
      }
      System.out.println(w);
      String expected = "Game is: Running\n" + "Is game over? -> false\n" + "PlayerQueue: \n"
          + "EMPTY\n" + "All entities: \n"
          + "GameObject: Chip facing->SOUTH at->Row: 8 Columns: 6 Chip Chip's Invetory: []\n"
          + "GameObject: Block facing->NONE at->Row: 1 Columns: 2 Block\n" + "\n" + "Board: \n"
          + "0|_|_|_|_|_|_|_|_|_|_|\n" + "1|_|_|=|_|_|_|_|_|_|_|\n" + "2|_|_|_|_|_|c|_|_|_|_|\n"
          + "3|_|_|_|_|_|_|_|_|_|_|\n" + "4|_|_|_|_|_|_|O|_|_|_|\n" + "5|_|_|_|_|_|_|_|_|_|_|\n"
          + "6|_|_|_|_|_|_|_|_|_|_|\n" + "7|_|_|_|_|_|_|O|_|_|_|\n" + "8|_|_|_|_|_|_|C|_|_|X|\n"
          + "9|_|_|_|_|_|_|_|_|_|e|\n";
      assertEquals(w.toString(), expected);
    } catch (Exception e) {
      e.printStackTrace();
      exception = true;
    }
    assertFalse(exception);
    System.out.println("\ntest nine complete\n");
  }

  /**
   * Attempt to move the block through the teleporter tile No exceptions should
   * occur
   */
  @Test
  void tryTeleportBlock() {
    boolean exception = false;
    try {
      World w = new TestWorld();
      w.loadLevelData(testLevel);
      w.doneLoading();
      w.moveChipDown();
      w.update(200);
      for (int r = 0; r < 5; r++) {
        w.moveChipRight();
        w.update(200);
      }
      System.out.println(w);
      w.moveChipUp();
      w.update(200);
      w.moveChipRight();
      w.update(200);
      System.out.println(w);
      for (int d = 0; d < 3; d++) {
        w.moveChipDown();
        w.update(200);
      }
      System.out.println(w);
      String expected = "Game is: Running\n" + "Is game over? -> false\n" + "PlayerQueue: \n"
          + "EMPTY\n" + "All entities: \n"
          + "GameObject: Chip facing->SOUTH at->Row: 3 Columns: 6 Chip Chip's Invetory: []\n"
          + "GameObject: Block facing->NONE at->Row: 8 Columns: 6 Block\n" + "\n" + "Board: \n"
          + "0|_|_|_|_|_|_|_|_|_|_|\n" + "1|_|_|_|_|_|_|_|_|_|_|\n" + "2|_|_|_|_|_|c|_|_|_|_|\n"
          + "3|_|_|_|_|_|_|C|_|_|_|\n" + "4|_|_|_|_|_|_|O|_|_|_|\n" + "5|_|_|_|_|_|_|_|_|_|_|\n"
          + "6|_|_|_|_|_|_|_|_|_|_|\n" + "7|_|_|_|_|_|_|O|_|_|_|\n" + "8|_|_|_|_|_|_|=|_|_|X|\n"
          + "9|_|_|_|_|_|_|_|_|_|e|\n";
      assertEquals(w.toString(), expected);
    } catch (Exception e) {
      e.printStackTrace();
      exception = true;
    }
    assertFalse(exception);
    System.out.println("\ntest ten complete\n");
  }

  /**
   * Attempt to cause a chain of movement events via the teleporter No exceptions
   * should occur
   */
  @Test
  void tryMovementChain() {
    boolean exception = false;
    try {
      World w = new TestWorld();
      w.loadLevelData(testLevel);
      w.doneLoading();
      w.moveChipDown();
      w.update(200);
      for (int r = 0; r < 5; r++) {
        w.moveChipRight();
        w.update(200);
      }
      System.out.println(w);
      w.moveChipUp();
      w.update(200);
      w.moveChipRight();
      w.update(200);
      System.out.println(w);
      for (int d = 0; d < 4; d++) {
        w.moveChipDown();
        w.update(200);
      }
      System.out.println(w);
      String expected = "Game is: Running\n" + "Is game over? -> false\n" + "PlayerQueue: \n"
          + "EMPTY\n" + "All entities: \n"
          + "GameObject: Chip facing->SOUTH at->Row: 8 Columns: 6 Chip Chip's Invetory: []\n"
          + "GameObject: Block facing->NONE at->Row: 9 Columns: 6 Block\n" + "\n" + "Board: \n"
          + "0|_|_|_|_|_|_|_|_|_|_|\n" + "1|_|_|_|_|_|_|_|_|_|_|\n" + "2|_|_|_|_|_|c|_|_|_|_|\n"
          + "3|_|_|_|_|_|_|_|_|_|_|\n" + "4|_|_|_|_|_|_|O|_|_|_|\n" + "5|_|_|_|_|_|_|_|_|_|_|\n"
          + "6|_|_|_|_|_|_|_|_|_|_|\n" + "7|_|_|_|_|_|_|O|_|_|_|\n" + "8|_|_|_|_|_|_|C|_|_|X|\n"
          + "9|_|_|_|_|_|_|=|_|_|e|\n";
      assertEquals(w.toString(), expected);
    } catch (Exception e) {
      e.printStackTrace();
      exception = true;
    }
    assertFalse(exception);
    System.out.println("\ntest eleven complete\n");
  }
}
//default board toString
//+"Board: \n"
//+ "0|C|_|_|_|_|c|_|_|_|_|\n"
//+ "1|_|_|=|_|_|_|_|_|_|_|\n"
//+ "2|_|_|_|_|_|c|_|_|_|_|\n"
//+ "3|_|_|_|_|_|_|_|_|_|_|\n"
//+ "4|_|_|_|_|_|_|O|_|_|_|\n"
//+ "5|_|_|_|_|_|_|_|_|_|_|\n"
//+ "6|_|_|_|_|_|_|_|_|_|_|\n"
//+ "7|_|_|_|_|_|_|O|_|_|_|\n"
//+ "8|_|_|_|_|_|_|_|_|_|X|\n"
//+ "9|_|_|_|_|_|_|_|_|_|e|\n";
