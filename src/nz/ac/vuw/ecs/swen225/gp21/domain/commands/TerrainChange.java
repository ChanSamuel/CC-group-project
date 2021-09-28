package nz.ac.vuw.ecs.swen225.gp21.domain.commands;

import nz.ac.vuw.ecs.swen225.gp21.domain.Command;
import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.GenericEvent;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Terrain;

/**
 * Terrain change is used to remember terrain alterations caused by GameObject
 * moves for replays.
 *
 * @author sansonbenj 300482847
 *
 */
public final class TerrainChange extends GenericEvent implements Command {
  /**
   * Store the location where the change occurred.
   */
  private final Coord location;
  /**
   * Store the terrain before the change.
   */
  private final Terrain before;
  /**
   * Store the terrain after the change.
   */
  private final Terrain after;

  /**
   * Create a new store of a terrain change.
   *
   * @param updateIndex the number of the update that generated this event
   * @param location    the location of the tile that changed its terrain
   * @param before      the terrain before the change
   * @param after       the terrain after the change
   */
  public TerrainChange(int updateIndex, Coord location, Terrain before, Terrain after) {
    super(updateIndex);
    this.location = location;
    this.before = before;
    this.after = after;
  }

  @Override
  public void execute(World w) {
    w.getBoard().getTileAt(location).setTerrain(after);
  }

  @Override
  public void undo(World w) {
    w.getBoard().getTileAt(location).setTerrain(before);
  }

  @Override
  public void redoEvent(World w) {
    this.execute(w);
  }

  @Override
  public void undoEvent(World w) {
    this.undo(w);
  }

  @Override
  public String toString() {
    String answer = "TerrainChange [ ";
    answer += " prev: " + before;
    answer += " after: " + after + " at->" + location;
    answer += " on update: " + updateIndex + " @ " + timeStamp;
    answer += "]";
    return answer;
  }
}
