package nz.ac.vuw.ecs.swen225.gp21.persistency.tests;

import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import nz.ac.vuw.ecs.swen225.gp21.domain.*;
import nz.ac.vuw.ecs.swen225.gp21.domain.controllers.NoMovement;
import nz.ac.vuw.ecs.swen225.gp21.domain.controllers.PlayerController;
import nz.ac.vuw.ecs.swen225.gp21.domain.controllers.RandomMovement;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Block;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Chip;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Monster;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.GameOver;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Loading;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Replaying;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Running;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.*;
import nz.ac.vuw.ecs.swen225.gp21.persistency.GameCaretaker;
import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;
import nz.ac.vuw.ecs.swen225.gp21.persistency.XMLPersister;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SaveWorldSaveTests {

    /**
     * WorldSave object for testing
     */
    private static WorldSave worldSave;
    static {
        worldSave = new WorldSave();
        worldSave.setCols(2);
        worldSave.setRows(2);
        worldSave.setUpdates(6);
        worldSave.setCurrentState(new Running());
        worldSave.setTotalTreasure(7);

        List<GameObject> gameObjects = new ArrayList<>();
        gameObjects.add(new Chip());
        gameObjects.add(new Block());

        List<Coord> gameObjectLocations = new ArrayList<>();
        gameObjectLocations.add(new Coord(0,0));
        gameObjectLocations.add(new Coord(0, 1));

        gameObjects.get(0).setTile(new Tile(gameObjectLocations.get(0), null));

        List<MovementController> gameObjectMoveControllers = new ArrayList<>();
        gameObjectMoveControllers.add(new PlayerController());

        List<Terrain> terrains = new ArrayList<>();
        terrains.add(GreenKey.getInstance());
        terrains.add(GreenDoor.getInstance());
        terrains.add(Teleporter.makeInstance(new Coord(0,0)));

        worldSave.setGameObjects(gameObjects);
        worldSave.setGameObjectLocations(gameObjectLocations);
        worldSave.setGameObjectMoveControllers(gameObjectMoveControllers);
        worldSave.setTerrains(terrains);
    }

    /**
     * XMLmapper with registered subtypes for testing
     */
    private static XmlMapper xmlMapper;
    static {
        xmlMapper = new XmlMapper();

        // Register GameObject subtypes
        xmlMapper.registerSubtypes(
                new NamedType(Chip.class, "Chip"),
                new NamedType(Block.class, "Block"),
                new NamedType(Monster.class, "Monster")
        );

        // Register MovementController subtypes
        xmlMapper.registerSubtypes(
                new NamedType(NoMovement.class, "NoMovement"),
                new NamedType(PlayerController.class, "PlayerController"),
                new NamedType(RandomMovement.class, "RandomMovement")
        );

        // Register State subtypes
        xmlMapper.registerSubtypes(
                new NamedType(Running.class, "Running"),
                new NamedType(GameOver.class, "GameOver"),
                new NamedType(Loading.class, "Loading"),
                new NamedType(Replaying.class, "Replaying")
        );

        // Register Terrain subtypes
        xmlMapper.registerSubtypes(
                new NamedType(CopperDoor.class, "CopperDoor"),
                new NamedType(CopperKey.class, "CopperKey"),
                new NamedType(ExitLock.class, "ExitLock"),
                new NamedType(ExitTile.class, "ExitTile"),
                new NamedType(Free.class, "Free"),
                new NamedType(GoldDoor.class, "GoldDoor"),
                new NamedType(GoldKey.class, "GoldKey"),
                new NamedType(GreenDoor.class, "GreenDoor"),
                new NamedType(GreenKey.class, "GreenKey"),
                new NamedType(Info.class, "Info"),
                new NamedType(OneWayEast.class, "OneWayEast"),
                new NamedType(OneWayNorth.class, "OneWayNorth"),
                new NamedType(OneWaySouth.class, "OneWaySouth"),
                new NamedType(OneWayWest.class, "OneWayWest"),
                new NamedType(SilverDoor.class, "SilverDoor"),
                new NamedType(SilverKey.class, "SilverKey"),
                new NamedType(Teleporter.class, "Teleporter"),
                new NamedType(Treasure.class, "Treasure"),
                new NamedType(Wall.class, "Wall")
        );

        xmlMapper.getFactory().getXMLOutputFactory().setProperty("javax.xml.stream.isRepairingNamespaces", false);
    }

    @Test
    public void saveLoadWorldWithPersister() throws PersistException, IOException {
        File f = new File("worldsave_with_persister.xml");
        XMLPersister persister = new XMLPersister(xmlMapper);
        persister.save(f, worldSave);
        WorldSave ws = xmlMapper.readValue(new FileInputStream(f), WorldSave.class);

        assertEquals("Key tile Green", ws.getTerrains().get(0).toString());
        assertEquals("Door  Green", ws.getTerrains().get(1).toString());
    }

    @Test
    public void saveLoadWorldWithCaretaker() throws PersistException, FileNotFoundException {
        File f = new File("worldsave_with_caretaker.xml");
        Domain domain = new TestWorld2() {
            @Override
            public WorldSave generateSaveData() {
                return worldSave;
            }
        };
        GameCaretaker gameCaretaker = new GameCaretaker(domain);
        gameCaretaker.saveGame(f);
        WorldSave ws = gameCaretaker.getMemento(new FileInputStream(f));

        assertEquals("Key tile Green", ws.getTerrains().get(0).toString());
        assertEquals("Door  Green", ws.getTerrains().get(1).toString());
    }
}

class TestWorld2 extends World {
    @Override
    public void collectedChip() {}

    @Override
    public void openedDoor() {}

    @Override
    public void enteredExit() {}

    @Override
    public void enteredInfo(String msg) {}

    @Override
    public void leftInfo() {}

    @Override
    public void playerLost() {}

    @Override
    public void playerGainedItem(Item item) {}

    @Override
    public void playerConsumedItem(Item item) {}

    @Override
    public void objectTeleported() {}

    @Override
    public void objectPushed() {}

    @Override
    public String toString() {
        return super.toString();
    }
}
