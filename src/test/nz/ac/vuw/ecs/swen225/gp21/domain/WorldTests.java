package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp21.domain.TestWorld;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.GameOver;

class WorldTests {

  @Test
  void doneLoadingTest() {

    assertThrows(UnsupportedOperationException.class, () -> {
      TestWorld world = new TestWorld();
      world.doneLoading();
    });

    assertThrows(UnsupportedOperationException.class, () -> {
      TestWorld world = new TestWorld();
      world.setState(new GameOver());
      world.doneLoading();
    });

  }
}
