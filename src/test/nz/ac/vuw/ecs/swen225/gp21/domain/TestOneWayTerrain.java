package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.domain.TestWorld;

/**
 * Test the one way tile type.
 *
 * @author sansonbenj 300482847
 *
 */
class TestOneWayTerrain {

  Level testLevel;

  {
    int rows = 5;
    int cols = 5;
    String terrainLayout = "";
    terrainLayout += ">v...";
    terrainLayout += "^<...";
    terrainLayout += ".....";
    terrainLayout += ".....";
    terrainLayout += ".....";

    String objLayout = "";
    objLayout += "C....";
    objLayout += ".....";
    objLayout += ".....";
    objLayout += ".....";
    objLayout += ".....";
    testLevel = new Level(rows, cols, terrainLayout, objLayout, "Test level!");
  }

  @Test
  void testCannotGoDown() {
    String expected = "Game is: Running\n" + "Is game over? -> false\n" + "PlayerQueue: \n"
        + "EMPTY\n" + "All entities: \n"
        + "GameObject: Chip facing->SOUTH at->Row: 0 Columns: 0 Chip Chip's Invetory: []\n" + "\n"
        + "Board: \n" + "0|C|v|_|_|_|\n" + "1|^|<|_|_|_|\n" + "2|_|_|_|_|_|\n" + "3|_|_|_|_|_|\n"
        + "4|_|_|_|_|_|\n" + "";
    TestWorld w = new TestWorld();
    w.loadLevelData(testLevel);
    w.doneLoading();

    w.moveChipDown();
    w.update(100);
    System.out.println(w.toString());
    assertEquals(expected, w.toString());
  }

}
