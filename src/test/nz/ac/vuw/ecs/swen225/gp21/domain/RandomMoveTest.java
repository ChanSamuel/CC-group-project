package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.domain.TestWorld;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;
import nz.ac.vuw.ecs.swen225.gp21.domain.controllers.RandomMovement;

/**
 * Test the random movement controller
 *
 * @author sansonbenj 300482847
 *
 */
class RandomMoveTest {
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
    tiles += "..........";
    tiles += "..........";
    tiles += "..........";
    tiles += "..........";
    tiles += "..........";
    tiles += "..........";
    tiles += "..........";
    tiles += "..........";
    String entities = "";
    entities += "..........";
    entities += "..........";
    entities += "..........";
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
   * check the behavior of random movement controller.
   */
  @Test
  void test() {
    Domain domain = new TestWorld(); // make the test domain
    domain.loadLevelData(testLevel); // and add an anonymous GameObject that moves 10 times per
                                     // second
    domain.addGameObject(new GameObject(new RandomMovement(5), null, null) {

      @Override
      public boolean canEntityGoOnTile(GameObject entity) {
        return false;
      }

      @Override
      public void entityEnteredTile(GameObject entity) {
        throw new RuntimeException(
            "Entity entered onto tile with object that cannot be stepped on");
      }

      @Override
      public void update(double elapsedTime, World w) {
        this.controller.update(w, this, elapsedTime).execute(w);
      }

      @Override
      public String getName() {
        return "GenericObject";
      }

      @Override
      public String toString() {
        return "Generic Object [ " + (getTile() != null ? getTile().location : "No location")
            + " ]";
      }

      @Override
      public char boardChar() {
        return '&';
      }

      @Override
      public GameObject clone() {
        return this;
      }
    }, new Coord(4, 4));

    domain.doneLoading();
    int tickFreq = 40;
    int seconds = 1;
    // number of updates that occur in 1 second, essentially running the game for
    // one second
    for (int sims = 0; sims < (1000 / tickFreq) * seconds; sims++) {
      domain.update(tickFreq);
      // System.out.println("times: " + sims + "\n" + domain);
    }
    // OK, now verify that the object moved 10 or 11 times
    int moves = ((TestWorld) domain).events.size();
    System.out.println("There were " + moves + " moves made by the generic object");
    assertTrue(moves == 5);
  }

  // TODO try moving less than one time per second
  /**
   * check the behavior of random movement controller.
   */
  @Test
  void testOneMoveTwoSecond() {
    Domain domain = new TestWorld(); // make the test domain
    domain.loadLevelData(testLevel); // and add an anonymous GameObject that moves 10 times per
                                     // second
    domain.addGameObject(new GameObject(new RandomMovement(0.5), null, null) {

      @Override
      public boolean canEntityGoOnTile(GameObject entity) {
        return false;
      }

      @Override
      public void entityEnteredTile(GameObject entity) {
        throw new RuntimeException(
            "Entity entered onto tile with object that cannot be stepped on");
      }

      @Override
      public void update(double elapsedTime, World w) {
        this.controller.update(w, this, elapsedTime).execute(w);
      }

      @Override
      public String getName() {
        return "GenericObject";
      }

      @Override
      public String toString() {
        return "Generic Object [ " + (getTile() != null ? getTile().location : "No location")
            + " ]";
      }

      @Override
      public char boardChar() {
        return '&';
      }

      @Override
      public GameObject clone() {
        return this;
      }
    }, new Coord(4, 4));

    domain.doneLoading();
    int tickFreq = 40;
    int seconds = 2;
    // number of updates that occur in 1 second, essentially running the game for
    // one second
    for (int sims = 0; sims < (1000 / tickFreq) * seconds; sims++) {
      domain.update(tickFreq);
      // System.out.println("times: " + sims + "\n" + domain);
    }
    // OK, now verify that the object moved 10 or 11 times
    int moves = ((TestWorld) domain).events.size();
    System.out.println("There were " + moves + " moves made by the generic object");
    assertTrue(moves == 1);
  }

  @Test
  void tryBadParamInput() {
    assertThrows(IllegalArgumentException.class, () -> {
      Domain domain = new TestWorld(); // make the test domain
      domain.loadLevelData(testLevel); // and add an anonymous GameObject that moves 10 times per
                                       // second
      domain.addGameObject(new GameObject(new RandomMovement(0), null, null) {

        @Override
        public boolean canEntityGoOnTile(GameObject entity) {
          return false;
        }

        @Override
        public void entityEnteredTile(GameObject entity) {
          throw new RuntimeException(
              "Entity entered onto tile with object that cannot be stepped on");
        }

        @Override
        public void update(double elapsedTime, World w) {
          this.controller.update(w, this, elapsedTime).execute(w);
        }

        @Override
        public String getName() {
          return "GenericObject";
        }

        @Override
        public String toString() {
          return "Generic Object [ " + (getTile() != null ? getTile().location : "No location")
              + " ]";
        }

        @Override
        public char boardChar() {
          return '&';
        }

        @Override
        public GameObject clone() {
          return this;
        }
      }, new Coord(4, 4));
    });
  }

}
