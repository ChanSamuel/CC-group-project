package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Block;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Chip;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.*;

/**
 * Test the terrain type.
 *
 * @author sansonbenj 300482847
 *
 */
class TerrainTests {

  @Test
  void DoorTests() {
    assertFalse(CopperDoor.getInstance().canEntityGoOn(new Block()));
    assertFalse(GoldDoor.getInstance().canEntityGoOn(new Block()));
    assertFalse(GreenDoor.getInstance().canEntityGoOn(new Block()));
    assertFalse(SilverDoor.getInstance().canEntityGoOn(new Block()));

    assertThrows(RuntimeException.class, () -> {
      CopperDoor.getInstance().entityEntered(new Block());
    });

    assertThrows(RuntimeException.class, () -> {
      GoldDoor.getInstance().entityEntered(new Block());
    });

    assertThrows(RuntimeException.class, () -> {
      SilverDoor.getInstance().entityEntered(new Block());
    });

    assertThrows(RuntimeException.class, () -> {
      GreenDoor.getInstance().entityEntered(new Block());
    });

    assertThrows(RuntimeException.class, () -> {
      CopperDoor.getInstance().undoEntityActions(new Block());
    });

    assertThrows(RuntimeException.class, () -> {
      GoldDoor.getInstance().undoEntityActions(new Block());
    });

    assertThrows(RuntimeException.class, () -> {
      SilverDoor.getInstance().undoEntityActions(new Block());
    });

    assertThrows(RuntimeException.class, () -> {
      GreenDoor.getInstance().undoEntityActions(new Block());
    });

  }

  @Test
  void keyTileTest() {
    assertDoesNotThrow(() -> {
      CopperKey.getInstance().entityEntered(new Block());
      GreenKey.getInstance().entityEntered(new Block());
      SilverKey.getInstance().entityEntered(new Block());
      GoldKey.getInstance().entityEntered(new Block());

      CopperKey.getInstance().nextType(new Block());
      GreenKey.getInstance().nextType(new Block());
      SilverKey.getInstance().nextType(new Block());
      GoldKey.getInstance().nextType(new Block());

      CopperKey.getInstance().undoEntityActions(new Chip());
      GreenKey.getInstance().undoEntityActions(new Chip());
      SilverKey.getInstance().undoEntityActions(new Chip());
      GoldKey.getInstance().undoEntityActions(new Chip());

    });
  }

  @Test
  void infoParamTest() {
    assertThrows(IllegalArgumentException.class, () -> {
      Info.setInfoText(null);
    });
  }

  @Test
  void teleporterTest() {
    assertThrows(RuntimeException.class, () -> {
      Teleporter t = Teleporter.makeInstance(new Coord(10, 10));
      t.entityEntered(new Block());
    });

    assertThrows(RuntimeException.class, () -> {
      Teleporter t = Teleporter.makeInstance(new Coord(10, 10));
      t.canEntityGoOn(new Block());
    });
  }
}
