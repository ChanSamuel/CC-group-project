package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.domain.TestWorld;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Loading;
import nz.ac.vuw.ecs.swen225.gp21.persistency.GameMemento;

/**
 * Test the functionality of the saving system.
 *
 * @author sansonbenj 300482847
 *
 */
class SaveTests {

  /**
   * Tests will use this testLevel
   */
  Level testLevel;
  {
    int rows = 10;
    int columns = 10;
    String tiles = "";
    tiles += "..........";
    tiles += "..........";
    tiles += "v#........";
    tiles += ".#........";
    tiles += "1#........";
    tiles += "##########";
    tiles += "1#........";
    tiles += ".#......#X";
    tiles += "v#......#.";
    tiles += ".....ccc#E";
    String entities = "";
    entities += "C.........";
    entities += "..........";
    entities += "..B.......";
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
   * Try saving, then loading, and saving again.
   */
  @Test
  void testDoubleSave() {
//    assertDoesNotThrow(() -> {
//      TestWorld w1 = new TestWorld();
//
//      w1.loadLevelData(testLevel);
//      w1.doneLoading();
//      GameMemento m1 = w1.generateSaveData();
//
//      w1.setState(new Loading());
//      w1.restoreGame(m1);
//      w1.doneLoading();
//
//      GameMemento m2 = w1.generateSaveData();
//
//      w1.setState(new Loading());
//      w1.restoreGame(m2);
//      w1.doneLoading();
//    });
  }

}
