package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.domain.TestWorld;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Block;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Loading;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Running;
import nz.ac.vuw.ecs.swen225.gp21.persistency.GameMemento;

/**
 * Test the state type.
 *
 * @author sansonbenj 300482847
 *
 */
class StateTests {

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
  void testInvalidOperationsRunning() {
    TestWorld w = new TestWorld();
    w.loadLevelData(testLevel);
    w.doneLoading();

    assertThrows(IllegalStateException.class, () -> {
      w.loadLevelData(testLevel);
    });

    assertThrows(IllegalStateException.class, () -> {
      w.forwardTick(null);
    });

    assertThrows(IllegalStateException.class, () -> {
      w.backTick(null);
    });

    assertThrows(IllegalStateException.class, () -> {
      w.addGameObject(null, null);
    });

    assertThrows(UnsupportedOperationException.class, () -> {
      w.doneLoading();
    });

    assertThrows(IllegalStateException.class, () -> {
      w.restoreGame(null);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      new Running().generateSaveData(null);
    });

  }

  @Test
  void LoadingTests() {
    // test memento input param checking
    assertThrows(IllegalArgumentException.class, () -> {
      new Loading().restoreGame(null, null);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      new Loading().restoreGame(new TestWorld(), null);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      TestWorld w = new TestWorld();
      w.loadLevelData(testLevel);
      w.doneLoading();

      GameMemento m = w.generateSaveData();
      // simulate an error
      m.getGameObjects().remove(0);

      new TestWorld().restoreGame(m);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      TestWorld w = new TestWorld();
      w.loadLevelData(testLevel);
      w.doneLoading();

      GameMemento m = w.generateSaveData();
      // simulate an error
      m.getTerrains().remove(0);

      new TestWorld().restoreGame(m);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      new Loading().addObject(null, null, null);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      new Loading().addObject(null, new Block(), null);
    });

    //
  }

}
