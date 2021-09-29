package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import static org.junit.jupiter.api.Assertions.*;
import java.util.Collections;
import org.junit.jupiter.api.Test;
import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.GameEvent;
import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.domain.TestWorld;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Loading;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Replaying;

/**
 * This suite of tests makes use of the new level loading functionality
 *
 * @author sansonbenj 300482847
 *
 */
class resetTests {
  /**
   * Tests will use this testLevel
   */
  Level testLevel;
  {
    int rows = 6;
    int columns = 10;
    String tiles = "";
    tiles += ".......gG.";
    tiles += "..........";
    tiles += ".....cc...";
    tiles += "........#X";
    tiles += ".1...1c.#E";
    tiles += "##########";
    String entities = "";
    entities += "C.........";
    entities += "..........";
    entities += "..........";
    entities += "..........";
    entities += "..........";
    entities += "..........";
    testLevel = new Level(rows, columns, tiles, entities, "No Info");
  }

  /**
   * Test if we can actually undo treasure pickup
   */
  @Test
  void testUndoTreasure() {
    System.out.println("TEST ONE!");
    Domain domain = new TestWorld();
    domain.loadLevelData(testLevel);
    domain.doneLoading();
    domain.moveChipDown();
    domain.moveChipDown();
    domain.moveChipRight();
    domain.moveChipRight();
    domain.moveChipRight();
    domain.moveChipRight();
    domain.moveChipRight();
    domain.moveChipRight();
    domain.moveChipDown();
    domain.moveChipDown();
    for (int sims = 0; sims < 11; sims++) {
      domain.update(200);
    }
    System.out.println(domain.toString());

    TestWorld newWor = new TestWorld();
    newWor.events = ((TestWorld) domain).events;

    domain = newWor;
    domain.loadLevelData(testLevel);
    domain.doneLoading();
    domain.setState(new Replaying());
    System.out.println(domain.toString());

    for (GameEvent e : newWor.events) {
      domain.forwardTick(e);
    }

    assertEquals("Game is: Replaying\n" + "Is game over? -> false\n" + "PlayerQueue: \n" + "EMPTY\n"
        + "All entities: \n"
        + "GameObject: Chip facing->SOUTH at->Row: 4 Columns: 6 Chip Chip's Invetory: []\n" + "\n"
        + "Board: \n" + "0|_|_|_|_|_|_|_|k|D|_|\n" + "1|_|_|_|_|_|_|_|_|_|_|\n"
        + "2|_|_|_|_|_|_|_|_|_|_|\n" + "3|_|_|_|_|_|_|_|_|#|_|\n" + "4|_|O|_|_|_|O|C|_|#|e|\n"
        + "5|#|#|#|#|#|#|#|#|#|#|\n", domain.toString());
    System.out.println(domain.toString());

    Collections.reverse(newWor.events);
    for (GameEvent e : newWor.events) {
      domain.backTick(e);
    }

    System.out.println(domain.toString());
    System.out.println("TEST ONE COMPLETE");
    String expected = "Game is: Replaying\n" + "Is game over? -> false\n" + "PlayerQueue: \n"
        + "EMPTY\n" + "All entities: \n"
        + "GameObject: Chip facing->NORTH at->Row: 0 Columns: 0 Chip Chip's Invetory: []\n" + "\n"
        + "Board: \n" + "0|C|_|_|_|_|_|_|k|D|_|\n" + "1|_|_|_|_|_|_|_|_|_|_|\n"
        + "2|_|_|_|_|_|c|c|_|_|_|\n" + "3|_|_|_|_|_|_|_|_|#|X|\n" + "4|_|O|_|_|_|O|c|_|#|e|\n"
        + "5|#|#|#|#|#|#|#|#|#|#|\n";
    assertEquals(expected, domain.toString());
  }

  /**
   * Test if we can actually undo key + door
   */
  @Test
  void testUndoDoor() {
    System.out.println("TEST TWO!");
    Domain d = new TestWorld();
    d.loadLevelData(testLevel);
    d.doneLoading();
    for (int move = 0; move < 10; move++) {
      d.moveChipRight();
      d.update(200);
    }
    System.out.println(d.toString());
    d.setState(new Loading());
    d.loadLevelData(testLevel);
    d.setState(new Replaying());

    System.out.println(d.toString());
    for (GameEvent e : ((TestWorld) d).events) {
      d.forwardTick(e);
    }

    System.out.println(d.toString());
    Collections.reverse(((TestWorld) d).events);
    for (GameEvent e : ((TestWorld) d).events) {
      d.backTick(e);
    }
    System.out.println(d.toString());
    System.out.println("TEST TWO COMPLETE");
    String expected = "Game is: Replaying\n" + "Is game over? -> false\n" + "PlayerQueue: \n"
        + "EMPTY\n" + "All entities: \n"
        + "GameObject: Chip facing->NORTH at->Row: 0 Columns: 0 Chip Chip's Invetory: []\n" + "\n"
        + "Board: \n" + "0|C|_|_|_|_|_|_|k|D|_|\n" + "1|_|_|_|_|_|_|_|_|_|_|\n"
        + "2|_|_|_|_|_|c|c|_|_|_|\n" + "3|_|_|_|_|_|_|_|_|#|X|\n" + "4|_|O|_|_|_|O|c|_|#|e|\n"
        + "5|#|#|#|#|#|#|#|#|#|#|\n";
    assertEquals(expected, d.toString());
    System.out.println(((TestWorld) d).events);
  }

  /**
   * Test if we can undo pickups through a teleport block
   */
  @Test
  void testTeleportUndo() {
    System.out.println("TEST THREE!");
    Domain d = new TestWorld();
    d.loadLevelData(testLevel);
    d.doneLoading();
    for (int move = 0; move < 4; move++) {
      d.moveChipDown();
      d.update(200);
    }
    d.moveChipRight();
    d.update(200);
    System.out.println(d.toString());
    d.setState(new Loading());
    d.loadLevelData(testLevel);
    d.setState(new Replaying());
    System.out.println(d.toString());
    for (GameEvent e : ((TestWorld) d).events) {
      d.forwardTick(e);
    }
    System.out.println(d.toString());
    Collections.reverse(((TestWorld) d).events);
    for (GameEvent e : ((TestWorld) d).events) {
      d.backTick(e);
    }
    System.out.println(d.toString());
    System.out.println("TEST THREE COMPLETE");
    String expected = "Game is: Replaying\n" + "Is game over? -> false\n" + "PlayerQueue: \n"
        + "EMPTY\n" + "All entities: \n"
        + "GameObject: Chip facing->NORTH at->Row: 0 Columns: 0 Chip Chip's Invetory: []\n" + "\n"
        + "Board: \n" + "0|C|_|_|_|_|_|_|k|D|_|\n" + "1|_|_|_|_|_|_|_|_|_|_|\n"
        + "2|_|_|_|_|_|c|c|_|_|_|\n" + "3|_|_|_|_|_|_|_|_|#|X|\n" + "4|_|O|_|_|_|O|c|_|#|e|\n"
        + "5|#|#|#|#|#|#|#|#|#|#|\n";
    assertEquals(expected, d.toString());
    System.out.println(((TestWorld) d).events);
  }

}
