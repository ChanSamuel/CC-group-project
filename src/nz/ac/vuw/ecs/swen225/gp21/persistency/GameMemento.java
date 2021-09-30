package nz.ac.vuw.ecs.swen225.gp21.persistency;

import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.MovementController;
import nz.ac.vuw.ecs.swen225.gp21.domain.State;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Terrain;

import java.util.List;

/**
 * TODO Fill in doc when I've written some report
 *
 * @author Lucy Goodwin
 */
public class GameMemento {

    /**
     * The rows and columns of the board, the number of updates and the total treasures from the game to be captured.
     */
    int rows, cols, updates, totalTreasure;

    /**
     * TODO
     */
    int levelNumber, timeLeft = 0;

    /**
     * Whether the boards exit has been opened from the game to be captured.
     */
    boolean isExitOpen;

    /**
     * Current state of the game to be captured.
     */
    State currentState;

    /**
     * List of game objects in the game to be captured.
     */
    List<GameObject> gameObjects;

    /**
     * List of coords belonging to the game objects.
     */
    List<Coord> gameObjectLocations;

    /**
     * List of movement controllers belonging to the game objects.
     */
    List<MovementController> gameObjectMoveControllers;

    /**
     * List of terrains of the game to be captured.
     */
    List<Terrain> terrains;

    /**
     * Default constructor so the GameMemento can be parsed by the XMLPersister.
     */
    public GameMemento() {}

    /**
     * Constructor for a GameMemento that gets called from the Domain module.
     * All fields capturing game state are handed on construction.
     *
     * @param rows number of rows in the game to be captured
     * @param cols number of cols in the game to be captured
     * @param gameObjects list of game objects
     * @param gameObjectLocations list of coords
     * @param gameObjectMoveControllers list of movement controllers
     * @param terrains list of terrains
     * @param updates  number of updates in the game to be captured
     * @param currentState state of the game
     * @param totalTreasure number of total treasure in the game to be captured
     * @param isExitOpen boolean of whether the board has been opened
     */
    public GameMemento(int rows, int cols, List<GameObject> gameObjects, List<Coord> gameObjectLocations,
                       List<MovementController> gameObjectMoveControllers, List<Terrain> terrains, int updates,
                       State currentState, int totalTreasure, boolean isExitOpen)
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
        this.isExitOpen = isExitOpen;
    }

    /**
     * Getter for rows field.
     *
     * @return rows
     */
    public int getRows() {
        return rows;
    }

    /**
     * Getter for cols field.
     * @return cols
     */
    public int getCols() {
        return cols;
    }

    /**
     * Getter for gameObjects field.
     *
     * @return list of game objects
     */
    public List<GameObject> getGameObjects() {
        return gameObjects;
    }

    /**
     * Getter for gameObjectLocations field.
     *
     * @return list of coords
     */
    public List<Coord> getGameObjectLocations() {
        return gameObjectLocations;
    }

    /**
     * Getter for gameObjectMoveControllers field.
     *
     * @return list of movement controllers
     */
    public List<MovementController> getGameObjectMoveControllers() {
        return gameObjectMoveControllers;
    }

    /**
     * Getter for terrains field.
     *
     * @return list of terrains
     */
    public List<Terrain> getTerrains() {
        return terrains;
    }

    /**
     * Getter for updates field.
     *
     * @return number of updates
     */
    public int getUpdates() {
        return updates;
    }

    /**
     * Getter for currentState field.
     *
     * @return game state
     */
    public State getCurrentState() {
        return currentState;
    }

    /**
     * Getter for isExitOpen field.
     *
     * @return boolean
     */
    public boolean getIsExitOpen() {
        return isExitOpen;
    }

    /**
     * Getter for totalTreasure field.
     *
     * @return int representing total treasures
     */
    public int getTotalTreasure() {
        return totalTreasure;
    }

    /**
     * FIXME
     * @return
     */
    public int getLevelNumber() {
        return levelNumber;
    }

    /**
     * FIXME
     *
     * @return
     */
    public int getTimeLeft() {
        return timeLeft;
    }

    /**
     * FIXME
     * @param levelNumber
     */
    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    /**
     * FIXME
     * @param timeLeft
     */
    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }
}
