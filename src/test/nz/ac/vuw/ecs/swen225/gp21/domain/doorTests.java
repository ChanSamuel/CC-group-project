package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.domain.TestWorld;

/**
 * Test if all the different doors work.
 *
 * @author Benjamin
 *
 */
class doorTests {

  /**
   * Tests will use this testLevel
   */
  Level testLevel;
  {
    int rows = 6;
    int columns = 10;
    String tiles = "";
    tiles += ".......gG.";
    tiles += ".......aA.";
    tiles += ".......uU.";
    tiles += ".......sS.";
    tiles += ".1...1c..X";
    tiles += "#########E";
    String entities = "";
    entities += "......C...";
    entities += "..........";
    entities += "..........";
    entities += "..........";
    entities += "..........";
    entities += "..........";
    testLevel = new Level(rows, columns, tiles, entities, "No Info");
  }

  /**
   * Test chip can open all the doors
   */
  @Test
  void openDoors() {
    Domain d = new TestWorld();
    d.loadLevelData(testLevel);
    d.doneLoading();

    moveRight(d, 2);
    moveLeft(d, 2);
    d.moveChipDown();
    d.update(200);

    moveRight(d, 2);
    moveLeft(d, 2);
    d.moveChipDown();
    d.update(200);

    moveRight(d, 2);
    moveLeft(d, 2);
    d.moveChipDown();
    d.update(200);

    moveRight(d, 2);
    moveLeft(d, 2);
    d.moveChipDown();
    d.update(200);

    System.out.println(d.toString());

  }

  private void moveRight(Domain d, int times) {
    for (int i = 0; i < times; i++) {
      d.moveChipRight();
      d.update(200);
    }
  }

  private void moveLeft(Domain d, int times) {
    for (int i = 0; i < times; i++) {
      d.moveChipLeft();
      d.update(200);
    }
  }
}
