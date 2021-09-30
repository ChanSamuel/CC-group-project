package nz.ac.vuw.ecs.swen225.gp21.domain.commands;

import nz.ac.vuw.ecs.swen225.gp21.domain.Command;
import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.GenericEvent;
import nz.ac.vuw.ecs.swen225.gp21.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Terrain;

/**
 * Direct move is used by replay to directly move actors, without worrying about
 * game logic checks.
 *
 * @author sansonbenj 300482847
 *
 */
public final class DirectMove extends GenericEvent implements Command {
  /**
   * Direction object was facing before the move.
   */
  private Direction beforeDir;
  /**
   * Direction object was facing after the move.
   */
  private Direction afterDir;
  /**
   * The position of the game object before the move.
   */
  private Coord beforePos;
  /**
   * Position of the gameObject after the move.
   */
  private Coord afterPos;
  /**
   * The terrain of the tile at afterPos BEFORE the move was applied.
   */
  private Terrain beforeTerrain;

  /**
   * Create a new direct move command. It needs to store all in the information
   * needed to undo the move.
   *
   * @param updateIndex   the number of the update that generated this move.
   * @param beforeDir     the direction the object was facing before the move
   * @param beforePos     the position of the object before the move
   * @param beforeTerrain the terrain of the tile the object moved to before the
   *                      move
   * @param moved         the object that was moved
   */
  public DirectMove(int updateIndex, Direction beforeDir, Coord beforePos, Terrain beforeTerrain,
      GameObject moved) {
    super(updateIndex);
    this.beforeDir = beforeDir;
    this.beforePos = beforePos;
    this.beforeTerrain = beforeTerrain;
    this.afterPos = moved.getTile().location;
    this.afterDir = moved.dir;
  }

  /**
   * default constructor for saving.
   */
  public DirectMove() {
  }

  /**
   * Get the object that will be moved in execute / undo. Perform null checks.
   *
   * @param w the world this move is being applied to.
   * @param c the location the GameObject should be at.
   * @return the GameObject.
   */
  private GameObject getObjectToMove(World w, Coord c) {
    Tile t = w.getTileAt(c);
    if (!t.isTileOccupied()) {
      throw new RuntimeException(
          "Coord: " + c + " is empty! Yet direct move expected it to be occupied!");
    }
    return t.getOccupier();
  }

  @Override
  public void execute(World w) {
    GameObject moved = getObjectToMove(w, beforePos);
    moved.dir = afterDir;
    assert (w.getBoardWorld().tryMoveObject(afterPos, moved) != null);
  }

  @Override
  public void undo(World w) {
    GameObject moved = getObjectToMove(w, afterPos);
    moved.dir = beforeDir;
    w.getBoardWorld().moveObjectBack(beforePos, moved);
    w.getTileAt(afterPos).resetTerrain(moved, beforeTerrain);
  }

  @Override
  public void redoEvent(World w) {
    this.execute(w);
  }

  @Override
  public void undoEvent(World w) {
    this.undo(w);
  }

  /**
   * Get the direction before the move.
   *
   * @return the beforeDir
   */
  public Direction getBeforeDir() {
    return beforeDir;
  }

  /**
   * Get the direction after the move.
   *
   * @return the afterDir
   */
  public Direction getAfterDir() {
    return afterDir;
  }

  /**
   * Get the position of the object before the move.
   *
   * @return the beforePos
   */
  public Coord getBeforePos() {
    return beforePos;
  }

  /**
   * Get the position of the object after the move.
   *
   * @return the afterPos
   */
  public Coord getAfterPos() {
    return afterPos;
  }

  /**
   * Get the before terrain.
   *
   * @return the beforeTerrain
   */
  public Terrain getBeforeTerrain() {
    return beforeTerrain;
  }

  @Override
  public String toString() {
    String answer = "DirectMove [ ";
    answer += " from: (" + beforePos + ") (" + beforeDir + ")";
    answer += " to: (" + afterPos + ") (" + afterDir + ")";
    answer += " Terrain was => " + beforeTerrain;
    answer += " on update: " + updateIndex + " @ " + timeStamp;
    answer += "]";

    return answer;
  }
}
