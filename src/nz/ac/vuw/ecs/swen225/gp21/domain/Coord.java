package nz.ac.vuw.ecs.swen225.gp21.domain;

import java.util.Objects;

/**
 * Coordinate data type for Tiles, to remember where they are.
 *
 * @author sansonbenj 300482847
 */
public final class Coord {
  /**
   * The column of this coordinate.
   */
  private int column;
  /**
   * the row of this coordinate.
   */
  private int row;

  /**
   * default init column and row to zero.
   */
  public Coord() {
  }

  /**
   * Make a new Coordinate at row, col.
   *
   * @param row - the row of this coordinate
   * @param col - the column of this coordinate
   */
  public Coord(int row, int col) {
    this.row = row;
    this.column = col;
  }

  /**
   * Get the Row of this coordinate.
   *
   * @return the row
   */
  public int getRow() {
    return row;
  }

  /**
   * Get the Column of this coordinate.
   *
   * @return the column
   */
  public int getColumn() {
    return column;
  }

  /**
   * Get the coordinate that represents the position above this one.
   *
   * @return the coordinate of the location above this coord
   */
  public Coord up() {
    return new Coord(this.row - 1, this.column);
  }

  /**
   * Get the coordinate that represents the position below this one.
   *
   * @return the coordinate of the location below this coord
   */
  public Coord down() {
    return new Coord(this.row + 1, this.column);
  }

  /**
   * Get the coordinate that represents the position to the left of this one.
   *
   * @return the coordinate of the location to the left of this coord
   */
  public Coord left() {
    return new Coord(this.row, this.column - 1);
  }

  /**
   * Get the coordinate that represents the position to the right of this one.
   *
   * @return the coordinate of the location to the right of this coord
   */
  public Coord right() {
    return new Coord(this.row, this.column + 1);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Coord coord = (Coord) o;
    return column == coord.column && row == coord.row;
  }

  @Override
  public int hashCode() {
    return Objects.hash(column, row);
  }

  @Override
  public String toString() {
    return "Row: " + row + " Columns: " + column;
  }
}