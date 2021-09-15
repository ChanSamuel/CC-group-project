package nz.ac.vuw.ecs.swen225.gp21.domain;

import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Terrain;

/**
 * The game world is comprised of tiles Tiles contain a location, possible game
 * object, and Terrain type.
 *
 * @author Benjamin
 *
 */
public final class Tile {
  /**
   * The location of this tile on the board.
   */
  public final Coord location;
  /**
   * Reference to the object that is on this tile.
   */
  private GameObject occupier;
  /**
   * Reference to the type of terrain this tile has.
   */
  private Terrain terrain;

  /**
   * Create a Tile with no terrain type {uninitialized}.
   *
   * @param location the location of the tile
   */
  Tile(Coord location) {
    this.location = location;
    this.terrain = null;
    occupier = null;
  }

  /**
   * Helper for replays Just set the occupier reference. This is called on the
   * tile at beforePos
   *
   * @param o object being moved back
   */
  public void forcePlace(GameObject o) {
    o.setTile(this);
    occupier = o;
  }

  /**
   * Helper method for replays Reset the terrain at afterPos.
   *
   * @param o      object that was moved away from this tile.
   * @param before the terrain this tile is being changed back to.
   */
  public void resetTerrain(GameObject o, Terrain before) {
    setTerrain(before);
    getTerrain().undoEntityActions(o);
  }

  /**
   * Remove the GameObject reference for this tile.
   */
  void removeOccupier() {
    terrain.entityExited(occupier);
    this.occupier = null;
  }

  /**
   * Called when manually moving an object around the board for example, when an
   * object teleports. Doesn't ask the terrain for permission
   *
   * @param o the object moving onto this tile
   */
  private void setOccupier(GameObject o) {
    if (isTileOccupied()) {
      occupier.entityEnteredTile(o);
    }
    o.setTile(this);
    occupier = o;
  }

  /**
   * Follow the complete move procedure. Notify terrain, then the occupier
   *
   * @param o the object being moved onto this tile
   */
  void addOccupier(GameObject o) {
    setOccupier(o);
    terrain.entityEntered(o);
    setTerrain(terrain.nextType(o));
    o.doneMoving();
  }

  /**
   * Gets the GameObject on this tile.
   *
   * @return The GameObject on this tile
   */
  public GameObject getOccupier() {
    return occupier;
  }

  /**
   * Returns whether this tile has a GameObject on it or not.
   *
   * @return true if there is a gameObject on this tile
   */
  public boolean isTileOccupied() {
    return getOccupier() != null;
  }

  /**
   * Change the type of terrain this tile has.
   *
   * @param t the type of terrain
   */
  public void setTerrain(Terrain t) {
    if (t == null) {
      throw new IllegalArgumentException("Terrain cannot be null!");
    }
    this.terrain = t;
  }

  /**
   * Get the type of terrain this tile has.
   *
   * @return the type of terrain of this tile
   */
  public Terrain getTerrain() {
    if (this.terrain == null) {
      throw new IllegalStateException(
          "This tile has not been given a terrain type! " + this.location);
    }
    return this.terrain;
  }

  /**
   * Determine if an object can enter this tile based on if the previous terrain.
   * will allow the object to leave and if the next terrain will allow the object
   * on it and if the occupier will allow the object on top of it.
   *
   * @param o the object trying to move onto this tile
   * @return whether object o can enter this tile
   */
  public boolean canEntityGoOnTile(GameObject o) {
    if (!o.currentTile.getTerrain().canEntityLeave(o)) {
      return false;
    }
    if (getTerrain().canEntityGoOn(o)) { // check the terrain first
      if (isTileOccupied()) { // if there is an occupier, ask them next
        return getOccupier().canEntityGoOnTile(o);
      } else {
        return true;
      }
    }
    return false;
  }

  /**
   * Determine the String that should represent this tile in the boards toString
   * method.
   *
   * @return the string that should represent this tile in the boards toString
   */
  public String boardString() {
    return Character
        .toString(isTileOccupied() ? getOccupier().boardChar() : getTerrain().boardChar());
  }

  @Override
  public String toString() {
    return "Tile Location: " + location.toString() + "\nOccupier: "
        + (isTileOccupied() ? occupier.toString() : "Empty") + "\nTerrain: "
        + getTerrain().toString();
  }
}
