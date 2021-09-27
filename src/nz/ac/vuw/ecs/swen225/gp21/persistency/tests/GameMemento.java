package nz.ac.vuw.ecs.swen225.gp21.persistency.tests;

import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.MovementController;
import nz.ac.vuw.ecs.swen225.gp21.domain.State;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Terrain;

import java.util.List;

public class GameMemento {

    int rows;
    int cols;

    List<GameObject> gameObjects;
    List<Coord> gameObjectLocations;
    List<MovementController> gameObjectMoveControllers;
    List<Terrain> terrains;

    int updates;
    State currentState;
    int totalTreasure;

    public GameMemento() {}

    public GameMemento(int rows, int cols, List<GameObject> gameObjects, List<Coord> gameObjectLocations,
                       List<MovementController> gameObjectMoveControllers, List<Terrain> terrains, int updates,
                       State currentState, int totalTreasure)
    {
        this.rows = rows;
        this.cols = cols;
        this.gameObjects = gameObjects;
        this.gameObjectLocations = gameObjectLocations;
        this.gameObjectMoveControllers = gameObjectMoveControllers;
        this.terrains = terrains;
        this.updates = updates;
        this.currentState = currentState;
        this.totalTreasure = totalTreasure;
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    public List<Coord> getGameObjectLocations() {
        return gameObjectLocations;
    }

    public List<MovementController> getGameObjectMoveControllers() {
        return gameObjectMoveControllers;
    }

    public List<Terrain> getTerrains() {
        return terrains;
    }

    public int getUpdates() {
        return updates;
    }

    public State getCurrentState() {
        return currentState;
    }

    public int getTotalTreasure() {
        return totalTreasure;
    }
}
