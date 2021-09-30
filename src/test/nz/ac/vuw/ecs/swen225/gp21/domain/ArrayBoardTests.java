package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.domain.TestWorld;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Block;
import nz.ac.vuw.ecs.swen225.gp21.persistency.GameMemento;

/**
 * These tests are for the array board type.
 *
 * @author sansonbenj 300482847
 *
 */
class ArrayBoardTests {

  Level testLevel;

  {
    int rows = 5;
    int cols = 5;
    String terrainLayout = "";
    terrainLayout += ".....";
    terrainLayout += ".....";
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
  void testMementoRestore() {
    TestWorld w = new TestWorld();
    w.loadLevelData(testLevel);
    w.doneLoading();

    GameMemento m = w.generateSaveData();

    TestWorld restored = new TestWorld();
    restored.restoreGame(m);
    System.out.println(w.toString() + "\n===\n" + restored.toString());

    assertEquals(w.getBoard().toString(), restored.getBoard().toString());
    assertFalse(w.getPlayer() == restored.getPlayer());
  }

  @Test
  void testInvalidCoords() {
    TestWorld w = new TestWorld();
    w.loadLevelData(testLevel);
    w.doneLoading();

    assertFalse(w.isCoordValid(new Coord(-1, 0)));
    assertFalse(w.isCoordValid(new Coord(0, -1)));
    assertFalse(w.isCoordValid(new Coord(6, 0)));
    assertFalse(w.isCoordValid(new Coord(0, 6)));
  }

  @Test
  void testSpawnOnEntity() {
    assertThrows(RuntimeException.class, () -> {
      TestWorld w = new TestWorld();
      w.loadLevelData(testLevel);
      w.addGameObject(new Block(), new Coord(0, 0));
      w.doneLoading();
    });
  }

  @Test
  void testInvalidCoordInBoard() {
    assertThrows(RuntimeException.class, () -> {
      TestWorld w = new TestWorld();
      w.loadLevelData(testLevel);
      w.addGameObject(new Block(), new Coord(-1, 0));
      w.doneLoading();
    });
  }

}
