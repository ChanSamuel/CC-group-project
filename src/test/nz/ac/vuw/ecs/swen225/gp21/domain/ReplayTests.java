package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.*;
import nz.ac.vuw.ecs.swen225.gp21.domain.*;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.*;

/**
 * Test that the domain can undo & redo ticks Test that the domain produces good
 * ticks
 *
 * @author sansonbenj 300482847
 *
 */
class ReplayTests {
  /**
   * The level the tests will use
   */
  Level testLevel;
  {
    int rows = 10;
    int columns = 10;
    String tiles = "";
    tiles += "..........";
    tiles += "..........";
    tiles += "..c..1....";
    tiles += "##########";
    tiles += ".....1....";
    tiles += "..........";
    tiles += "..........";
    tiles += "..........";
    tiles += ".cc.....#X";
    tiles += "........#E";
    String entities = "";
    entities += ".....C....";
    entities += ".....B....";
    entities += "..........";
    entities += "..........";
    entities += "..........";
    entities += "..........";
    entities += "..........";
    entities += "..........";
    entities += "..........";
    entities += "..........";
    testLevel = new Level(rows, columns, tiles, entities, "No Info");
  }

  /**
   * Try making a move while in the loading state an exception should be thrown
   */
  @Test
  void test() {
    boolean passed = false;
    try {
      World w = new TestWorld();
      w.loadLevelData(testLevel);
      w.update(200);
    } catch (IllegalStateException s) {
      System.out.println("Exception thrown: " + s.getLocalizedMessage());
      passed = true;
    }
    assertTrue(passed);
  }

  /**
   * Try recording some moves.
   */
  @Test
  void testTickGeneration() {
    boolean passed = true;
    try {
      World w = new TestWorld();
      w.loadLevelData(testLevel);
      w.doneLoading();
      w.update(200);
      w.moveChipDown();
      w.update(200);
      String expected = "Game is: Running\n" + "Is game over? -> false\n" + "PlayerQueue: \n"
          + "EMPTY\n" + "All entities: \n"
          + "GameObject: Chip facing->SOUTH at->Row: 1 Columns: 5 Chip Chip's Invetory: []\n"
          + "GameObject: Block facing->NONE at->Row: 5 Columns: 5 Block\n" + "\n" + "Board: \n"
          + "0|_|_|_|_|_|_|_|_|_|_|\n" + "1|_|_|_|_|_|C|_|_|_|_|\n" + "2|_|_|c|_|_|O|_|_|_|_|\n"
          + "3|#|#|#|#|#|#|#|#|#|#|\n" + "4|_|_|_|_|_|O|_|_|_|_|\n" + "5|_|_|_|_|_|=|_|_|_|_|\n"
          + "6|_|_|_|_|_|_|_|_|_|_|\n" + "7|_|_|_|_|_|_|_|_|_|_|\n" + "8|_|c|c|_|_|_|_|_|#|X|\n"
          + "9|_|_|_|_|_|_|_|_|#|e|\n";
      assertEquals(expected, w.toString());
    } catch (Exception s) {
      s.printStackTrace();
      passed = false;
    }
    assertTrue(passed);
  }

  /**
   * Try recording some moves.
   */
  @Test
  void testTickReplay() {
    boolean passed = true;
    try {
      World w = new TestWorld();
      w.loadLevelData(testLevel);
      w.doneLoading();
      w.update(200);
      w.moveChipDown();
      w.update(200);
      // NOTE: This raises an interesting question:
      // Should I be able to save and exit the game,
      // then replay that partially completed game?
      // OR should you only be able to replay games that
      // have been completed? (either lost, or won)?
      // I think this also implies that if you loose, and
      // restart a level, a new recording will start being
      // generated.

      String expected = "Game is: Running\n" + "Is game over? -> false\n" + "PlayerQueue: \n"
          + "EMPTY\n" + "All entities: \n"
          + "GameObject: Chip facing->SOUTH at->Row: 1 Columns: 5 Chip Chip's Invetory: []\n"
          + "GameObject: Block facing->NONE at->Row: 5 Columns: 5 Block\n" + "\n" + "Board: \n"
          + "0|_|_|_|_|_|_|_|_|_|_|\n" + "1|_|_|_|_|_|C|_|_|_|_|\n" + "2|_|_|c|_|_|O|_|_|_|_|\n"
          + "3|#|#|#|#|#|#|#|#|#|#|\n" + "4|_|_|_|_|_|O|_|_|_|_|\n" + "5|_|_|_|_|_|=|_|_|_|_|\n"
          + "6|_|_|_|_|_|_|_|_|_|_|\n" + "7|_|_|_|_|_|_|_|_|_|_|\n" + "8|_|c|c|_|_|_|_|_|#|X|\n"
          + "9|_|_|_|_|_|_|_|_|#|e|\n";

      String expectedThree = "Game is: Replaying\n" + "Is game over? -> false\n" + "PlayerQueue: \n"
          + "EMPTY\n" + "All entities: \n"
          + "GameObject: Chip facing->SOUTH at->Row: 1 Columns: 5 Chip Chip's Invetory: []\n"
          + "GameObject: Block facing->NONE at->Row: 5 Columns: 5 Block\n" + "\n" + "Board: \n"
          + "0|_|_|_|_|_|_|_|_|_|_|\n" + "1|_|_|_|_|_|C|_|_|_|_|\n" + "2|_|_|c|_|_|O|_|_|_|_|\n"
          + "3|#|#|#|#|#|#|#|#|#|#|\n" + "4|_|_|_|_|_|O|_|_|_|_|\n" + "5|_|_|_|_|_|=|_|_|_|_|\n"
          + "6|_|_|_|_|_|_|_|_|_|_|\n" + "7|_|_|_|_|_|_|_|_|_|_|\n" + "8|_|c|c|_|_|_|_|_|#|X|\n"
          + "9|_|_|_|_|_|_|_|_|#|e|\n";
      String expectedTwo = "Game is: Replaying\n" + "Is game over? -> false\n" + "PlayerQueue: \n"
          + "EMPTY\n" + "All entities: \n"
          + "GameObject: Chip facing->NORTH at->Row: 0 Columns: 5 Chip Chip's Invetory: []\n"
          + "GameObject: Block facing->NONE at->Row: 1 Columns: 5 Block\n" + "\n" + "Board: \n"
          + "0|_|_|_|_|_|C|_|_|_|_|\n" + "1|_|_|_|_|_|=|_|_|_|_|\n" + "2|_|_|c|_|_|O|_|_|_|_|\n"
          + "3|#|#|#|#|#|#|#|#|#|#|\n" + "4|_|_|_|_|_|O|_|_|_|_|\n" + "5|_|_|_|_|_|_|_|_|_|_|\n"
          + "6|_|_|_|_|_|_|_|_|_|_|\n" + "7|_|_|_|_|_|_|_|_|_|_|\n" + "8|_|c|c|_|_|_|_|_|#|X|\n"
          + "9|_|_|_|_|_|_|_|_|#|e|\n";
      assertEquals(expected, w.toString());
      // try to roll the replay back
      w.setState(new Replaying());// TODO this assumes the state
      // of the world is the same
      // as the state of the last tick.

      // This is not good enough, because our plan
      // was to load the initial level conditions
      // the go forward through the ticks.
      TestWorld testWor = (TestWorld) w;
      w.backTick(testWor.events.get(1));
      w.backTick(testWor.events.get(0));
      assertEquals(expectedTwo, w.toString());
      // try to roll the replay forward
      w.forwardTick(testWor.events.get(0));
      w.forwardTick(testWor.events.get(1));
      assertEquals(expectedThree, w.toString());
      // and run it back one last time
      w.backTick(testWor.events.get(1));
      w.backTick(testWor.events.get(0));
      assertEquals(expectedTwo, w.toString());
    } catch (Exception s) {
      s.printStackTrace();
      passed = false;
    }
    assertTrue(passed);
  }

  /**
   * Test that ticks work properly on different world Objects
   */
  @Test
  void testReplayDifferentWorld() {
    boolean passed = true;
    try {
      World first = new TestWorld();
      first.loadLevelData(testLevel);
      first.doneLoading();

      World second = new TestWorld();
      second.loadLevelData(testLevel);
      second.doneLoading();

      first.moveChipDown();
      first.moveChipDown();
      first.update(200);
      first.update(200);
      first.update(200);

      ((TestWorld) second).events = List.copyOf(((TestWorld) first).events);
      first = null;
      System.gc();

      second.setState(new Replaying());

      TestWorld test = (TestWorld) second;

      second.forwardTick(test.events.get(0));
      second.forwardTick(test.events.get(1));
      second.forwardTick(test.events.get(2));
      second.forwardTick(test.events.get(3));

      String expectedOne = "Game is: Replaying\n" + "Is game over? -> false\n" + "PlayerQueue: \n"
          + "EMPTY\n" + "All entities: \n"
          + "GameObject: Chip facing->SOUTH at->Row: 5 Columns: 5 Chip Chip's Invetory: []\n"
          + "GameObject: Block facing->NONE at->Row: 6 Columns: 5 Block\n" + "\n" + "Board: \n"
          + "0|_|_|_|_|_|_|_|_|_|_|\n" + "1|_|_|_|_|_|_|_|_|_|_|\n" + "2|_|_|c|_|_|O|_|_|_|_|\n"
          + "3|#|#|#|#|#|#|#|#|#|#|\n" + "4|_|_|_|_|_|O|_|_|_|_|\n" + "5|_|_|_|_|_|C|_|_|_|_|\n"
          + "6|_|_|_|_|_|=|_|_|_|_|\n" + "7|_|_|_|_|_|_|_|_|_|_|\n" + "8|_|c|c|_|_|_|_|_|#|X|\n"
          + "9|_|_|_|_|_|_|_|_|#|e|\n";

      assertEquals(expectedOne, second.toString());

      second.backTick(test.events.get(3));
      second.backTick(test.events.get(2));
      second.backTick(test.events.get(1));
      second.backTick(test.events.get(0));

      String expectedTwo = "Game is: Replaying\n" + "Is game over? -> false\n" + "PlayerQueue: \n"
          + "EMPTY\n" + "All entities: \n"
          + "GameObject: Chip facing->NORTH at->Row: 0 Columns: 5 Chip Chip's Invetory: []\n"
          + "GameObject: Block facing->NONE at->Row: 1 Columns: 5 Block\n" + "\n" + "Board: \n"
          + "0|_|_|_|_|_|C|_|_|_|_|\n" + "1|_|_|_|_|_|=|_|_|_|_|\n" + "2|_|_|c|_|_|O|_|_|_|_|\n"
          + "3|#|#|#|#|#|#|#|#|#|#|\n" + "4|_|_|_|_|_|O|_|_|_|_|\n" + "5|_|_|_|_|_|_|_|_|_|_|\n"
          + "6|_|_|_|_|_|_|_|_|_|_|\n" + "7|_|_|_|_|_|_|_|_|_|_|\n" + "8|_|c|c|_|_|_|_|_|#|X|\n"
          + "9|_|_|_|_|_|_|_|_|#|e|\n";

      assertEquals(expectedTwo, second.toString());

    } catch (IllegalStateException s) {
      System.out.println("Exception thrown: " + s.getLocalizedMessage());
      passed = false;
    }
    assertTrue(passed);
  }

  @Test
  void testGeneratedEvents() {
    TestWorld w = new TestWorld();
    w.loadLevelData(testLevel);
    w.doneLoading();
    w.moveChipLeft();
    w.moveChipLeft();
    w.moveChipLeft();

    w.update(40);
    w.update(40);
    w.update(40);
    w.update(40);

    assertTrue(w.events.size() == 3);
  }

}
