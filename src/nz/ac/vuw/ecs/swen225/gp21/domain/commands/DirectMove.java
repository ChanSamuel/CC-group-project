package nz.ac.vuw.ecs.swen225.gp21.domain.commands;

import nz.ac.vuw.ecs.swen225.gp21.domain.Command;
import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.Tile;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Terrain;

/**
 * Direct move is used by replay to directly move actors, without worrying about
 * game logic checks.
 *
 * @author Benjamin
 *
 */
public final class DirectMove implements Command {
  /**
   * Direction object was facing before the move.
   */
  private final Direction beforeDir;
  /**
   * Direction object was facing after the move.
   */
  private final Direction afterDir;
  /**
   * The position of the game object before the move.
   */
  private final Coord beforePos;
  /**
   * Position of the gameObject after the move.
   */
  private final Coord afterPos;
  /**
   * The terrain of the tile at afterPos BEFORE the move was applied.
   */
  private final Terrain beforeTerrain;

  /**
   * Create a new direct move command. It needs to store all in the information
   * needed to undo the move.
   *
   * @param beforeDir     the direction the object was facing before the move
   * @param beforePos     the position of the object before the move
   * @param beforeTerrain the terrain of the tile the object moved to before the
   *                      move
   * @param moved         the object that was moved
   */
  public DirectMove(Direction beforeDir, Coord beforePos, Terrain beforeTerrain, GameObject moved) {
    this.beforeDir = beforeDir;
    this.beforePos = beforePos;
    this.beforeTerrain = beforeTerrain;
    this.afterPos = moved.getTile().location;
    this.afterDir = moved.dir;
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
    assert (w.getBoard().tryMoveObject(afterPos, moved) != null);
  }

  @Override
  public void undo(World w) {
    GameObject moved = getObjectToMove(w, afterPos);
    moved.dir = beforeDir;
    w.getBoard().moveObjectBack(beforePos, moved);
    w.getTileAt(afterPos).resetTerrain(moved, beforeTerrain);
  }

}
