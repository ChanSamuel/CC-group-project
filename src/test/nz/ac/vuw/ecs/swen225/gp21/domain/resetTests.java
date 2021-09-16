package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.domain.TestWorld;
import nz.ac.vuw.ecs.swen225.gp21.domain.Tick;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Replaying;

/**
 * This suite of tests makes use of the new level loading functionality
 *
 * @author Benjamin
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
    tiles += "..........";
    tiles += "..........";
    tiles += ".....cc...";
    tiles += "........#X";
    tiles += "........#E";
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
    domain.moveChipRight();
    for (int sims = 0; sims < 11; sims++) {
      ticks.add(domain.update(200));
    }
    ticks.get(ticks.size() - 1).isFinalTick = true;

    domain = new TestWorld();
    domain.loadLevelData(testLevel);
    domain.doneLoading();
    domain.setState(new Replaying());

    for (int tick = 0; tick < ticks.size(); tick++) {
      domain.forwardTick(ticks.get(tick));
    }

    // TODO TEST HERE!
    System.out.println(domain.toString());

    for (int tick = ticks.size() - 1; tick != 0; tick--) {
      domain.backTick(ticks.get(tick));
    }

    // TODO TEST HERE!
    System.out.println(domain.toString());
    assertFalse(true);
  }

  /**
   * Test if we can actually undo key + door
   */
  @Test
  void testUndoDoor() {
    assertFalse(true);
  }

}
