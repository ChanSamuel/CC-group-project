package nz.ac.vuw.ecs.swen225.gp21.domain;

import nz.ac.vuw.ecs.swen225.gp21.domain.commands.TerrainChange;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.ExitLock;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Free;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Terrain;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Treasure;
import nz.ac.vuw.ecs.swen225.gp21.persistency.GameMemento;

/**
 * ArrayBoard implements the Board functionality with a 2D array.
 *
 * @author sansonbenj 300482847
 *
 */
public class ArrayBoard implements Board {
  /**
   * A reference back up to the world.
   */
  private World world;
  /**
   * The internal representation of the game.
   */
  private final Tile[][] board;

  /**
   * The height of the world.
   */
  final int rows;
  /**
   * The width of the world.
   */
  final int columns;

  /**
   * Remember if we called the open exit method.
   */
  private boolean isExitOpen;

  /**
   * Create an array board from a level object only initializes the terrain
   * fields.
   *
   * @param level object that contains the information needed to build the board
   * @param w     The world that is using this board
   */
  public ArrayBoard(Level level, World w) {
    this.world = w;
    this.rows = level.rows;
    this.columns = level.columns;
    this.isExitOpen = false;
    board = new Tile[rows][columns];
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < columns; col++) {
        Coord c = new Coord(row, col);
        board[row][col] = new Tile(c, this);
        board[row][col].setTerrain(level.terrainAt(c));
      }
    }
  }

  /**
   * Restore an arrayboard from a Memento. Does not add the GameObjects.
   *
   * @param w    the world that is using this board.
   * @param save the Memento we are restoring from.
   */
  public ArrayBoard(GameMemento save, World w) {
    this.world = w;
    this.rows = save.getRows();
    this.columns = save.getCols();
    this.isExitOpen = save.getb();
    this.board = new Tile[rows][columns];
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < columns; col++) {
        Coord c = new Coord(row, col);
        int index = twoDtoOneD(row, col);
        board[row][col] = new Tile(c, this);
        board[row][col].setTerrain(save.getTerrains().get(index));
      }
    }
  }

  /**
   * convert two dimensional array index into one dimensional index.
   *
   * @param row the row of the target
   * @param col the column of the target
   * @return the targets location in a 1 dimensional array
   */
  private int twoDtoOneD(int row, int col) {
    return col + (row * columns);
  }

  @Override
  public void addObject(GameObject o, Coord location) {
    boundsCheck(location); // NOTE: method for loading
    Tile t = coordToTile(location);
    if (t.isTileOccupied()) {
      throw new RuntimeException(
          "Cannot spawn one entity on an occupied tile: " + location + " Object: " + o);
    }
    t.addOccupier(o);
  }

  /**
   * Throw an exception if a passed coordinate that should be valid is not.
   *
   * @param c the coord being checked
   */
  private void boundsCheck(Coord c) {
    if (!coordInBoard(c)) {
      throw new RuntimeException("Coordinate is out of bounds: " + c);
    }
  }

  /**
   * Check if a coordinate is within the bounds of the board.
   *
   * @param c the coordinate being checked
   * @return true if the coordinate is 'inside' the board
   */
  private boolean coordInBoard(Coord c) {
    if (c.getRow() < 0 || c.getRow() > rows - 1 || c.getColumn() < 0
        || c.getColumn() > columns - 1) {
      return false;
    }
    return true;
  }

  /**
   * Convert a coordinate to a tile in the board.
   *
   * @param c the coordinate of the tile being located
   * @return the tile that has this coordinate as its location
   * @throws IndexOutOfBoundsException Thrown if you try to access a tile out of
   *                                   bounds
   */
  Tile coordToTile(Coord c) {
    boundsCheck(c);
    return board[c.getRow()][c.getColumn()];
  }

  @Override
  public int getRemainingChips() {
    int answer = 0;
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < columns; col++) {
        if (board[row][col].getTerrain() instanceof Treasure) {
          answer++;
        }
      }
    }
    return answer;
  }

  /**
   * Perform a full object move for a one square move Checks the occupier AND the
   * terrain type before the move Assumes caller already did a bounds check.
   *
   * @param dest the location the object is moving to
   * @param o    the object being moved
   */
  @Override
  public Terrain tryMoveObject(Coord dest, GameObject o) {
    if (!coordInBoard(dest)) {
      return null;
    }
    Tile t = coordToTile(dest);
    if (!t.canEntityGoOnTile(o)) {
      return null;
    }
    return t.addOccupier(o);
  }

  /**
   * Don't perform any checks, forcefully move an object to a location We need
   * this method because some moves can't be undone normally, due to one way tile
   * restrictions.
   *
   * @param beforePos Location the object is being moved back to
   * @param o         the object being moved
   */
  @Override
  public void moveObjectBack(Coord beforePos, GameObject o) {
    Tile t = coordToTile(beforePos);
    t.forcePlace(o);
    // When this method returns we will go to afterPos and call
    // t.resetTerrainChange(...)
  }

  @Override
  public void openExit() {
    isExitOpen = true;
    for (int row = 0; row < rows; row++) {
      for (int col = 0; col < columns; col++) {
        if (board[row][col].getTerrain() instanceof ExitLock) {
          int updates = this.world.updates;
          this.world.eventOccured(new TerrainChange(updates, new Coord(row, col),
              board[row][col].getTerrain(), Free.getInstance())); // make an event
          board[row][col].setTerrain(Free.getInstance());
        }
      }
    }
  }

  @Override
  public Tile getTileAt(Coord location) {
    return coordToTile(location);
  }

  @Override
  public int getWidth() {
    return columns;
  }

  @Override
  public int getHeight() {
    return rows;
  }

  @Override
  public boolean isCoordValid(Coord c) {
    return this.coordInBoard(c);
  }

  @Override
  public World getWorld() {
    return this.world;
  }

  @Override
  public boolean isExitOpen() {
    return isExitOpen;
  }

  @Override
  public String toString() {
    StringBuilder ans = new StringBuilder();
    for (int row = 0; row < rows; row++) {
      ans.append(row + "|");
      for (int col = 0; col < columns; col++) {
        Tile t = coordToTile(new Coord(row, col));
        ans.append(t.boardString());
        ans.append("|");
      }
      ans.append("\n");
    }
    return ans.toString();
  }
}
