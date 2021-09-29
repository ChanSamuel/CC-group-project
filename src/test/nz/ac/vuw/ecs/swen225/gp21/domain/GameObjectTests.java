package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.domain.TestWorld;
import nz.ac.vuw.ecs.swen225.gp21.domain.controllers.NoMovement;
import nz.ac.vuw.ecs.swen225.gp21.domain.items.KeyItem;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Block;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Chip;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Monster;

class GameObjectTests {

  @Test
  void toStringTest() {
    GameObject o1 = new Chip();
    GameObject o2 = new Block();

    assertNotEquals(o1.toString(), o2.toString());
  }

  @Test
  void ChipTests() {
    assertTrue(
        new Chip().canEntityGoOnTile(new Monster(new NoMovement(), Direction.NORTH, null, null) {
          @Override
          public char boardChar() {
            return 0;
          }

          @Override
          public GameObject clone() {
            return this;
          }
        }));
    assertFalse(new Chip().canEntityGoOnTile(new Block()));

    assertThrows(RuntimeException.class, () -> {
      new Chip().entityEnteredTile(new Block());
    });

    assertDoesNotThrow(() -> {
      TestWorld w = new TestWorld();
      w.loadLevelData(new Level(1, 3, "...", "C..", "testLevel"));
      Monster m = new Monster(new NoMovement(), Direction.NORTH, null, null) {
        @Override
        public char boardChar() {
          return 0;
        }

        @Override
        public GameObject clone() {
          return this;
        }
      };
      w.addGameObject(m, new Coord(0, 1));

      w.doneLoading();
      w.getPlayer().entityEnteredTile(m);
    });
    Chip temp = new Chip();
    temp.addItem(new KeyItem("Blue"));
    System.out.println(temp);
  }

  @Test
  void blockTests() {
    assertFalse(new Block().canEntityGoOnTile(new Block()));
    TestWorld w = new TestWorld();
    w.loadLevelData(new Level(2, 2, "....", "=.C=", "test"));
    w.doneLoading();
    assertDoesNotThrow(() -> {
      w.moveChipRight();
      w.update(40);
      w.moveChipUp();
      w.update(40);
    });
  }

  @Test
  void MonsterTests() {
    Monster m = new Monster(new NoMovement(), Direction.NORTH, null, null) {
      @Override
      public char boardChar() {
        return 0;
      }

      @Override
      public GameObject clone() {
        return this;
      }
    };

    assertTrue(m.canEntityGoOnTile(new Chip()));
    assertFalse(m.canEntityGoOnTile(new Block()));

    assertThrows(RuntimeException.class, () -> {
      m.entityEnteredTile(new Block());
    });
  }

}
