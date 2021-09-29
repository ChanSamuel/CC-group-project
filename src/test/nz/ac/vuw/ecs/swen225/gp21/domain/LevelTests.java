package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.Level;

class LevelTests {

  @Test
  void testLevelInvalidArgs() {
    // test invalid args 3x
    assertThrows(IllegalArgumentException.class, () -> {
      Level l = new Level(-1, -1, "", "", "null");
      l.addGameObject(null, null);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      Level l = new Level(10, -1, "", "", "null");
      l.addGameObject(null, null);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      Level l = new Level(0, 10, "", "", "null");
      l.addGameObject(null, null);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      Level l = new Level(3, 3, "...", "..", "null");
      l.addGameObject(null, null);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      Level l = new Level(10, 10, ".", ".", "null");
      l.addGameObject(null, null);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      Level l = new Level(1, 1, " ", ".", "null");
      l.addGameObject(null, null);
    });

    assertThrows(IllegalArgumentException.class, () -> {
      Level l = new Level(1, 1, ".", " ", "null");
      l.addGameObject(null, null);
    });

    assertDoesNotThrow(() -> {
      Level l = new Level(1, 1, ".", ".", "null");
      l.addGameObject(null, null);
    });
  }

  @Test
  void testTerrainAt() {
    assertThrows(RuntimeException.class, () -> {
      Level l = new Level(2, 2, "1...", "....", "testLevel");
      l.terrainAt(new Coord(0, 0));
    });
  }

}
