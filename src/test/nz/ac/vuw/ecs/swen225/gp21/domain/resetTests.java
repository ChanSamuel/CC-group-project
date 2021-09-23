package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.domain.TestWorld;
import nz.ac.vuw.ecs.swen225.gp21.domain.Tick;
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
    List<Tick> ticks = new ArrayList<>();
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
      ticks.add(domain.update(200));
    }
    ticks.get(ticks.size() - 1).isFinalTick = true;
    System.out.println(domain.toString());
    domain = new TestWorld();
    domain.loadLevelData(testLevel);
    domain.doneLoading();
    domain.setState(new Replaying());
    System.out.println(domain.toString());

    for (int tick = 0; tick < ticks.size(); tick++) {
      domain.forwardTick(ticks.get(tick));
    }

    assertEquals("Game is: Replaying\n" + "Is game over? -> false\n" + "PlayerQueue: \n" + "EMPTY\n"
        + "All entities: \n"
        + "GameObject: Chip facing->SOUTH at->Row: 4 Columns: 6 Chip Chip's Invetory: []\n" + "\n"
        + "Board: \n" + "0|_|_|_|_|_|_|_|k|D|_|\n" + "1|_|_|_|_|_|_|_|_|_|_|\n"
        + "2|_|_|_|_|_|_|_|_|_|_|\n" + "3|_|_|_|_|_|_|_|_|#|_|\n" + "4|_|O|_|_|_|O|C|_|#|e|\n"
        + "5|#|#|#|#|#|#|#|#|#|#|\n", domain.toString());
    System.out.println(domain.toString());

    for (int tick = ticks.size() - 1; tick != -1; tick--) {
      domain.backTick(ticks.get(tick));
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
    List<Tick> ticks = new ArrayList<>();
    Domain d = new TestWorld();
    d.loadLevelData(testLevel);
    d.doneLoading();
    for (int move = 0; move < 10; move++) {
      d.moveChipRight();
      ticks.add(d.update(200));
    }
    ticks.get(ticks.size() - 1).isFinalTick = true;
    System.out.println(d.toString());
    d.setState(new Loading());
    d.loadLevelData(testLevel);
    d.setState(new Replaying());
    System.out.println(d.toString());
    for (int tick = 0; tick < ticks.size(); tick++) {
      d.forwardTick(ticks.get(tick));
    }
    System.out.println(d.toString());
    for (int tick = ticks.size() - 1; tick != -1; tick--) {
      d.backTick(ticks.get(tick));
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
    System.out.println(ticks);
  }

  /**
   * Test if we can undo pickups through a teleport block
   */
  @Test
  void testTeleportUndo() {
    System.out.println("TEST THREE!");
    List<Tick> ticks = new ArrayList<>();
    Domain d = new TestWorld();
    d.loadLevelData(testLevel);
    d.doneLoading();
    for (int move = 0; move < 4; move++) {
      d.moveChipDown();
      ticks.add(d.update(200));
    }
    d.moveChipRight();
    ticks.add(d.update(200));
    ticks.get(ticks.size() - 1).isFinalTick = true;
    System.out.println(d.toString());
    d.setState(new Loading());
    d.loadLevelData(testLevel);
    d.setState(new Replaying());
    System.out.println(d.toString());
    for (int tick = 0; tick < ticks.size(); tick++) {
      d.forwardTick(ticks.get(tick));
    }
    System.out.println(d.toString());
    for (int tick = ticks.size() - 1; tick != -1; tick--) {
      d.backTick(ticks.get(tick));
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
    System.out.println(ticks);
  }

}
