package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.domain.TestWorld;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Loading;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Replaying;

/**
 * These tests are for the Command type.
 *
 * @author sansonbenj 300482847
 *
 */
class CommandTests {
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
  void testDirectMove() {
    assertThrows(RuntimeException.class, () -> {
      TestWorld w = new TestWorld();
      w.loadLevelData(testLevel);
      w.doneLoading();
      w.moveChipRight();
      w.update(40);

      w.setState(new Loading());
      w.loadLevelData(testLevel);
      w.doneLoading();
      w.setState(new Replaying());
      w.getPlayer().setTile(null); // simulate error
      w.forwardTick(w.events.get(0));
    });
  }

}
