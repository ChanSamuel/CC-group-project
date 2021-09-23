package nz.ac.vuw.ecs.swen225.gp21.domain;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import nz.ac.vuw.ecs.swen225.gp21.domain.controllers.PlayerController;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Block;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Chip;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Terrain;

/**
 * World save contains all the instance information from a world object.
 *
 * @author sansonbenj 300482847
 *
 */
public final class WorldSave {

  public static void main(String[] args) {
    new WorldSave();
  }

  int rows;
  int cols;

  List<GameObject> gameObjects;
  List<Coord> gameObjectLocations;
  List<MovementController> gameObjectMoveControllers;
  List<Terrain> terrains;

  int updates;
  State currentState;
  int totalTreasure;

  public WorldSave() {
    List<Class<? extends GameObject>> GO = List.of(Chip.class, Block.class);

    try {
      GameObject resurected = GO.get(0).getDeclaredConstructor().newInstance();
      System.out.println(resurected);
      GameObject fromTheGrave = GO.get(1).getDeclaredConstructor().newInstance();
      System.out.println(fromTheGrave);
    } catch (InstantiationException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalAccessException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (IllegalArgumentException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (InvocationTargetException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (NoSuchMethodException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    } catch (SecurityException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }

  }

  /**
   * @return the rows
   */
  public int getRows() {
    return rows;
  }

  /**
   * @param rows the rows to set
   */
  public void setRows(int rows) {
    this.rows = rows;
  }

  /**
   * @return the cols
   */
  public int getCols() {
    return cols;
  }

  /**
   * @param cols the cols to set
   */
  public void setCols(int cols) {
    this.cols = cols;
  }

  /**
   * @return the gameObjects
   */
  public List<GameObject> getGameObjects() {
    return gameObjects;
  }

  /**
   * @param gameObjects the gameObjects to set
   */
  public void setGameObjects(List<GameObject> gameObjects) {
    this.gameObjects = gameObjects;
  }

  /**
   * @return the gameObjectLocations
   */
  public List<Coord> getGameObjectLocations() {
    return gameObjectLocations;
  }

  /**
   * @param gameObjectLocations the gameObjectLocations to set
   */
  public void setGameObjectLocations(List<Coord> gameObjectLocations) {
    this.gameObjectLocations = gameObjectLocations;
  }

  /**
   * @return the gameObjectMoveControllers
   */
  public List<MovementController> getGameObjectMoveControllers() {
    return gameObjectMoveControllers;
  }

  /**
   * @param gameObjectMoveControllers the gameObjectMoveControllers to set
   */
  public void setGameObjectMoveControllers(List<MovementController> gameObjectMoveControllers) {
    this.gameObjectMoveControllers = gameObjectMoveControllers;
  }

  /**
   * @return the terrains
   */
  public List<Terrain> getTerrains() {
    return terrains;
  }

  /**
   * @param terrains the terrains to set
   */
  public void setTerrains(List<Terrain> terrains) {
    this.terrains = terrains;
  }

  /**
   * @return the updates
   */
  public int getUpdates() {
    return updates;
  }

  /**
   * @param updates the updates to set
   */
  public void setUpdates(int updates) {
    this.updates = updates;
  }

  /**
   * @return the currentState
   */
  public State getCurrentState() {
    return currentState;
  }

  /**
   * @param currentState the currentState to set
   */
  public void setCurrentState(State currentState) {
    this.currentState = currentState;
  }

  /**
   * @return the totalTreasure
   */
  public int getTotalTreasure() {
    return totalTreasure;
  }

  /**
   * @param totalTreasure the totalTreasure to set
   */
  public void setTotalTreasure(int totalTreasure) {
    this.totalTreasure = totalTreasure;
  }

}
