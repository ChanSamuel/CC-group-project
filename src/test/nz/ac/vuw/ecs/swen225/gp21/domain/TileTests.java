package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import nz.ac.vuw.ecs.swen225.gp21.domain.Board;
import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.domain.TestWorld;
import nz.ac.vuw.ecs.swen225.gp21.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Terrain;

/**
 * Test the tile type.
 *
 * @author sansonbenj 300482847
 *
 */
class TileTests {

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
  void testTileBadTerrain() {
    assertThrows(IllegalArgumentException.class, () -> {
      TestWorld w = new TestWorld();
      w.loadLevelData(testLevel);
      w.doneLoading();
      w.getBoard().getTileAt(new Coord(0, 0)).toString();
      w.getBoard().getTileAt(new Coord(1, 0)).toString();
      w.getBoard().getTileAt(new Coord(1, 0)).setTerrain(null);
    });
  }

  @Test
  void testTileNoTerrain() {

    assertThrows(IllegalStateException.class, () -> {
      Board board = new Board() {
        {
          Tile t = new Tile();
          t.getTerrain();
        }

        @Override
        public void addObject(GameObject o, Coord location) {
        }

        @Override
        public World getWorld() {
          return null;
        }

        @Override
        public int getRemainingChips() {
          return -1;
        }

        @Override
        public void openExit() {
        }

        @Override
        public Terrain tryMoveObject(Coord destination, GameObject o) {
          return null;
        }

        @Override
        public void moveObjectBack(Coord beforeDest, GameObject o) {
        }

        @Override
        public Tile getTileAt(Coord location) {
          return null;
        }

        @Override
        public int getWidth() {
          return -1;
        }

        @Override
        public int getHeight() {
          return -1;
        }

        @Override
        public boolean isCoordValid(Coord c) {
          return false;
        }

        @Override
        public boolean isExitOpen() {
          return false;
        }
      };
      board.addObject(null, null);
    });
  }
}
