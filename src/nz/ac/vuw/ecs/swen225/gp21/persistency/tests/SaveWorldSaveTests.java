package nz.ac.vuw.ecs.swen225.gp21.persistency.tests;

import nz.ac.vuw.ecs.swen225.gp21.domain.*;
import nz.ac.vuw.ecs.swen225.gp21.domain.controllers.PlayerController;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Block;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Chip;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Running;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.GreenDoor;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.GreenKey;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Terrain;
import nz.ac.vuw.ecs.swen225.gp21.persistency.GameCaretaker;
import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;
import nz.ac.vuw.ecs.swen225.gp21.persistency.XMLPersister;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

public class SaveWorldSaveTests {

    @Mock
    Domain domain;

    @Mock
    XMLPersister parser;

    static String filename = "2.xml";

    /**
     * WorldSave object for testing
     */
    static WorldSave worldSave;
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
        gameObjectLocations.add(new Coord(1, 0));
        gameObjectLocations.add(new Coord(1,1));

        List<MovementController> gameObjectMoveControllers = new ArrayList<>();
        gameObjectMoveControllers.add(new PlayerController());

        List<Terrain> terrains = new ArrayList<>();
        terrains.add(new GreenKey());
        terrains.add(new GreenDoor());

        worldSave.setGameObjects(gameObjects);
        worldSave.setGameObjectLocations(gameObjectLocations);
        worldSave.setGameObjectMoveControllers(gameObjectMoveControllers);
        worldSave.setTerrains(terrains);
    }

    @Test
    public void saveWorldSave() throws PersistException {
        File f = new File(filename);
        GameCaretaker gameCaretaker = new GameCaretaker(domain);
        gameCaretaker.saveMemento(f, worldSave);

// FIXME: 24/09/2021
//         doReturn(worldSave).when(domain).generateSaveData();
    }

    @Test
    public void loadWorldSave() throws PersistException, FileNotFoundException {
        File f = new File(filename);
        GameCaretaker gameCaretaker = new GameCaretaker(domain);
        WorldSave worldSaveLoaded = gameCaretaker.getMemento(new FileInputStream(f));

// FIXME: 24/09/2021
//         doReturn(worldSave).when(domain).generateSaveData();
    }

}
