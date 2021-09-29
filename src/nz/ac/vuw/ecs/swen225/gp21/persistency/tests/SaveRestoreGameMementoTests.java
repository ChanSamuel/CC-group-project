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
import nz.ac.vuw.ecs.swen225.gp21.persistency.GameMemento;
import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;
import nz.ac.vuw.ecs.swen225.gp21.persistency.XMLPersister;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * These tests test whether a game state can be saved and restored successfully
 *
 * @author Lucy Goodwin
 */
public class SaveRestoreGameMementoTests {

    /**
     * GameMemento object for testing.
     */
    private static final GameMemento testMemento;
    static {
        List<GameObject> gameObjects = new ArrayList<>();
        gameObjects.add(new Chip());
        gameObjects.add(new Block());

        List<Coord> gameObjectLocations = new ArrayList<>();
        gameObjectLocations.add(new Coord(0,0));
        gameObjectLocations.add(new Coord(0, 1));

        List<MovementController> gameObjectMoveControllers = new ArrayList<>();
        gameObjectMoveControllers.add(new PlayerController());

        List<Terrain> terrains = new ArrayList<>();
        terrains.add(GreenKey.getInstance());
        terrains.add(GreenDoor.getInstance());
        terrains.add(Teleporter.makeInstance(new Coord(0,0)));

        testMemento = new GameMemento(2, 2, gameObjects, gameObjectLocations, gameObjectMoveControllers,
                terrains, 6, new Running(), 7, true);
    }

    /**
     * XmlMapper with registered subtypes for testing
     */
    private static final XmlMapper xmlMapper;
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
        File f = new File("memento_save_with_persister.xml");
        XMLPersister persister = new XMLPersister(xmlMapper);
        persister.save(f, testMemento);
        GameMemento loadedMemento;
        try (FileInputStream fis = new FileInputStream(f)) {
            loadedMemento = xmlMapper.readValue(fis, GameMemento.class);
        }
        assertTrue(sameAsTest(loadedMemento));
    }

    @Test
    public void saveLoadWorldWithCaretaker() throws PersistException, IOException {
        File f = new File("memento_save_with_caretaker.xml");
        Domain domain = new TestWorld2() {
            @Override
            public GameMemento generateSaveData() {
                return testMemento;
            }
        };
        GameCaretaker gameCaretaker = new GameCaretaker(domain);
            gameCaretaker.saveGame(f);
        GameMemento loadedMemento;
        try (FileInputStream stream = new FileInputStream(f)) {
            loadedMemento = gameCaretaker.getMemento(stream);
        }
        assertTrue(sameAsTest(loadedMemento));

    }

    private boolean sameAsTest(GameMemento loaded) {
        return  loaded != null
                && loaded.getRows() == 2
                && loaded.getCols() == 2
                && loaded.getUpdates() == 6
                && loaded.getTotalTreasure() == 7
                && loaded.getCurrentState().toString().contains("Running")
                && loaded.getGameObjects().get(0).getName().equals("Chip")
                && loaded.getGameObjects().get(1).getName().equals("Block")
                && loaded.getGameObjectLocations().get(0).toString().equals("Row: 0 Columns: 0")
                && loaded.getGameObjectLocations().get(1).toString().equals("Row: 0 Columns: 1")
                && loaded.getGameObjectMoveControllers().get(0).toString().contains("PlayerController")
                && loaded.getTerrains().get(0).toString().equals("Key tile Green")
                && loaded.getTerrains().get(1).toString().equals("Door  Green")
                && loaded.getTerrains().get(2).toString().equals("Teleporter")
                && loaded.getIsExitOpen()==true;
    }

    static class TestWorld2 extends World {
        public void collectedChip() {}
        public void openedDoor() {}
        public void enteredExit() {}
        public void enteredInfo(String msg) {}
        public void leftInfo() {}
        public void playerLost() {}
        public void playerGainedItem(Item item) {}
        public void playerConsumedItem(Item item) {}
        public void objectTeleported() {}
        public void objectPushed() {}
        public void eventOccured(GameEvent e) { }
        public String toString() {
            return super.toString();
        }
    }

}

