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
 * @author Benjamin
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
      List<Tick> ticks = new LinkedList<>();
      World w = new TestWorld();
      w.loadLevelData(testLevel);
      w.doneLoading();
      ticks.add(w.update(200));
      w.moveChipDown();
      ticks.add(w.update(200));

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
      List<Tick> ticks = new LinkedList<>();
      World w = new TestWorld();
      w.loadLevelData(testLevel);
      w.doneLoading();
      ticks.add(w.update(200));
      w.moveChipDown();
      ticks.add(w.update(200));

      // END OF PLAYER INPUTS
      // There are no more ticks coming. <- this 100% should be the replay
      // modules job of managing

      ticks.get(ticks.size() - 1).isFinalTick = true; // (Note, replaying currently doesn't inspect
                                                      // this field)
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
      assertFalse(ticks.get(0).didPlayerMove());
      assertTrue(ticks.get(1).didPlayerMove());
      w.backTick(ticks.get(1));
      w.backTick(ticks.get(0));
      assertEquals(expectedTwo, w.toString());
      // try to roll the replay forward
      w.forwardTick(ticks.get(0));
      w.forwardTick(ticks.get(1));
      assertEquals(expectedThree, w.toString());
      // and run it back one last time
      w.backTick(ticks.get(1));
      w.backTick(ticks.get(0));
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
      List<Tick> ticks = new ArrayList<>();
      World first = new TestWorld();
      first.loadLevelData(testLevel);
      first.doneLoading();

      World second = new TestWorld();
      second.loadLevelData(testLevel);
      second.doneLoading();

      first.moveChipDown();
      first.moveChipDown();
      ticks.add(first.update(200));
      ticks.add(first.update(200));
      ticks.add(first.update(200));
      ticks.get(ticks.size() - 1).isFinalTick = true;

      first = null;
      System.gc();

      second.setState(new Replaying());

      second.forwardTick(ticks.get(0));
      second.forwardTick(ticks.get(1));
      second.forwardTick(ticks.get(2));

      String expectedOne = "Game is: Replaying\n" + "Is game over? -> false\n" + "PlayerQueue: \n"
          + "EMPTY\n" + "All entities: \n"
          + "GameObject: Chip facing->SOUTH at->Row: 5 Columns: 5 Chip Chip's Invetory: []\n"
          + "GameObject: Block facing->NONE at->Row: 6 Columns: 5 Block\n" + "\n" + "Board: \n"
          + "0|_|_|_|_|_|_|_|_|_|_|\n" + "1|_|_|_|_|_|_|_|_|_|_|\n" + "2|_|_|c|_|_|O|_|_|_|_|\n"
          + "3|#|#|#|#|#|#|#|#|#|#|\n" + "4|_|_|_|_|_|O|_|_|_|_|\n" + "5|_|_|_|_|_|C|_|_|_|_|\n"
          + "6|_|_|_|_|_|=|_|_|_|_|\n" + "7|_|_|_|_|_|_|_|_|_|_|\n" + "8|_|c|c|_|_|_|_|_|#|X|\n"
          + "9|_|_|_|_|_|_|_|_|#|e|\n";

      assertEquals(expectedOne, second.toString());

      second.backTick(ticks.get(2));
      second.backTick(ticks.get(1));
      second.backTick(ticks.get(0));

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

}
