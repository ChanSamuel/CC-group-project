package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Block;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Chip;

class GameObjectTests {

  @Test
  void toStringTest() {
    GameObject o1 = new Chip();
    GameObject o2 = new Block();

    assertNotEquals(o1.toString(), o2.toString());
  }

}
