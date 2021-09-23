package nz.ac.vuw.ecs.swen225.gp21.domain.state;

import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.domain.State;
import nz.ac.vuw.ecs.swen225.gp21.domain.Tick;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;
import nz.ac.vuw.ecs.swen225.gp21.domain.WorldSave;

/**
 * Special state where the world will accept tick objects. World will apply the
 * commands in the tick to simulate a game (effectively replaying it) Ticks can
 * also be undone, to simulate rewinding the recording.
 *
 * @author sansonbenj 300482847
 *
 */
public final class Replaying implements State {

  @Override
  public Tick update(World w, double elapsedTime) {
    throw new IllegalStateException("Cannot simulate world while replaying!");
  }

  @Override
  public void loadLevel(World world, Level level) {
    throw new IllegalStateException("Cannot load world while replaying a game."
        + "\nEnsure the Level is loaded before trying to replay.");
  }

  @Override
  public void makeMove(World w, GameObject o, Direction d) {
    throw new IllegalStateException("Cannot generate moves while replaying!");
  }

  @Override
  public int getBoardWidth(World w) {
    return w.getBoardWidth();
  }

  @Override
  public int getBoardHeight(World w) {
    return w.getBoardHeight();
  }

  @Override
  public boolean isCoordValid(World w, Coord c) {
    return w.getBoardWorld().isCoordValid(c);
  }

  @Override
  public boolean addObject(World w, GameObject e, Coord c) {
    throw new IllegalStateException("Cannot add new GameObject while replaying!");
  }

  @Override
  public void moveChipLeft(World w) {
    throw new IllegalStateException("Cannot accept player inputs while replaying!");
  }

  @Override
  public void moveChipUp(World w) {
    throw new IllegalStateException("Cannot accept player inputs while replaying!");
  }

  @Override
  public void moveChipDown(World w) {
    throw new IllegalStateException("Cannot accept player inputs while replaying!");
  }

  @Override
  public void moveChipRight(World w) {
    throw new IllegalStateException("Cannot accept player inputs while replaying!");
  }

  @Override
  public Coord getPlayerLocation(World w) {
    return w.getPlayer().getTile().location;
  }

  @Override
  public void forwardTick(World w, Tick t) {
    if (++w.updates != t.index) {
      throw new RuntimeException(
          "World was expecting tick: " + w.updates + " but received tick: " + t.index);
    }
    t.redoTick(w);
  }

  @Override
  public void backTick(World w, Tick t) {
    if (w.updates-- != t.index) {
      throw new RuntimeException(
          "World was expecting tick: " + w.updates + " but received tick: " + t.index);
    }
    t.undoTick(w);
  }

  @Override
  public void restoreGame(World world, WorldSave save) {
    throw new IllegalStateException("Cannot restore save game while in replaying state!");
  }

  @Override
  public WorldSave generateSaveData(World w) {
    throw new IllegalStateException("Cannot save game when in replay!");
  }

}
