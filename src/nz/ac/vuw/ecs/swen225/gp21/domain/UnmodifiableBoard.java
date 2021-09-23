package nz.ac.vuw.ecs.swen225.gp21.domain;

import nz.ac.vuw.ecs.swen225.gp21.domain.commands.MultiMove;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Terrain;

/**
 * Unmodifiable board does not support operations that alter the board state. It
 * is given to the other modules if they need to read data from the Domain's
 * board.
 *
 * @author sansonbenj 300482847
 *
 */
public final class UnmodifiableBoard implements Board {

  /**
   * The actual board this object is proxying.
   */
  private final Board actual;

  /**
   * Create a board to proxy for a real board. Don't allow operations that modify
   * the board.
   *
   * @param b the board being proxied
   */
  UnmodifiableBoard(Board b) {
    actual = b;
  }

  @Override
  public void addObject(GameObject o, Coord location) {
    throw new UnsupportedOperationException("Cannot modify read only board!");
  }

  @Override
  public World getWorld() {
    throw new UnsupportedOperationException("Cannot access world with read only board.");
  }

  @Override
  public int getRemainingChips() {
    return actual.getRemainingChips();
  }

  @Override
  public MultiMove openExit() {
    throw new UnsupportedOperationException("Cannot modify read only board!");
  }

  @Override
  public Terrain tryMoveObject(Coord destination, GameObject o) {
    throw new UnsupportedOperationException("Cannot modify read only board!");
  }

  @Override
  public void moveObjectBack(Coord beforeDest, GameObject o) {
    throw new UnsupportedOperationException("Cannot modify read only board!");
  }

  @Override
  public Tile getTileAt(Coord location) {
    return actual.getTileAt(location);
  }

  @Override
  public int getWidth() {
    return actual.getWidth();
  }

  @Override
  public int getHeight() {
    return actual.getHeight();
  }

  @Override
  public boolean isCoordValid(Coord c) {
    return actual.isCoordValid(c);
  }

  @Override
  public boolean isExitOpen() {
    return actual.isExitOpen();
  }

}
