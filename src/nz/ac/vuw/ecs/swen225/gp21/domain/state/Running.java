package nz.ac.vuw.ecs.swen225.gp21.domain.state;

import java.util.*; //TODO temp addition
import java.util.stream.Collectors;
import nz.ac.vuw.ecs.swen225.gp21.domain.Coord; //TODO temp addition
import nz.ac.vuw.ecs.swen225.gp21.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp21.domain.GameEvent;
import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.domain.State;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;
import nz.ac.vuw.ecs.swen225.gp21.domain.commands.DirectMove;
import nz.ac.vuw.ecs.swen225.gp21.domain.commands.MoveDown;
import nz.ac.vuw.ecs.swen225.gp21.domain.commands.MoveLeft;
import nz.ac.vuw.ecs.swen225.gp21.domain.commands.MoveRight;
import nz.ac.vuw.ecs.swen225.gp21.domain.commands.MoveUp;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Terrain;
import nz.ac.vuw.ecs.swen225.gp21.persistency.tests.GameMemento;

/**
 * The running state represents a world that has been initialized and is capable
 * of simulating the world.
 *
 * @author sansonbenj 300482847
 *
 */
public final class Running implements State {
  /**
   * Check world isn't null.
   *
   * @param w world parameter
   */
  private void worldCheck(World w) {
    if (w == null) {
      throw new RuntimeException("World is null!");
    }
  }

  @Override
  public void update(World w, double elapsedTime) {
    worldCheck(w);
    w.updates++;
    for (GameObject e : w.getEntities()) {
      e.update(elapsedTime, w); // this method may generate events via w.eventOccured()
    }
    if (w.getBoard().getRemainingChips() == 0 && !w.getBoard().isExitOpen()) {
      w.getBoardWorld().openExit(); // this method may generate events via w.eventOccured()
    }

    assert (w.totalTreasure == w.getBoard().getRemainingChips()
        + (w.getPlayer() == null ? 0 : w.getPlayer().treasureCollected));
  }

  @Override
  public void makeMove(World w, GameObject o, Direction d) {
    Direction beforeD = o.dir;
    Coord beforeC = o.getTile().location;

    o.updateDirection(d);
    // This destination is not necessarily the final square, because teleporters
    // could move the GameObject
    Coord destination = o.dir.next(o.getTile().location);
    // The terrain at the destination before the move was applied.
    Terrain terrainAtDest = w.getBoardWorld().tryMoveObject(destination, o);
    if (terrainAtDest != null) {
      int updates = w.updates;
      w.eventOccured(new DirectMove(updates, beforeD, beforeC, terrainAtDest, o));
    }
  }

  @Override
  public int getBoardWidth(World w) {
    worldCheck(w);
    return w.getBoard().getWidth();
  }

  @Override
  public int getBoardHeight(World w) {
    worldCheck(w);
    return w.getBoard().getHeight();
  }

  @Override
  public boolean isCoordValid(World w, Coord c) {
    worldCheck(w);
    return w.getBoard().isCoordValid(c);
  }

  @Override
  public boolean addObject(World w, GameObject e, Coord c) {
    throw new IllegalStateException("Cannot add new entity while game is running");
  }

  @Override
  public void moveChipLeft(World w) {
    worldCheck(w);
    w.getCommandQueue().add(new MoveLeft(w.getPlayer()));
  }

  @Override
  public void moveChipUp(World w) {
    worldCheck(w);
    w.getCommandQueue().add(new MoveUp(w.getPlayer()));
  }

  @Override
  public void moveChipDown(World w) {
    worldCheck(w);
    w.getCommandQueue().add(new MoveDown(w.getPlayer()));
  }

  @Override
  public void moveChipRight(World w) {
    worldCheck(w);
    w.getCommandQueue().add(new MoveRight(w.getPlayer()));
  }

  @Override
  public void loadLevel(World world, Level level) {
    throw new IllegalStateException("Cannot load level while game is running!");
  }

  @Override
  public Coord getPlayerLocation(World w) {
    return w.getPlayer().getTile().location;
  }

  @Override
  public void forwardTick(World w, GameEvent e) {
    throw new IllegalStateException(
        "Cannot apply tick when game is running! World should be in replaying state!");
  }

  @Override
  public void backTick(World w, GameEvent e) {
    throw new IllegalStateException(
        "Cannot apply tick when game is running! World should be in replaying state!");
  }

  @Override
  public void restoreGame(World world, GameMemento save) {
    throw new IllegalStateException("Cannot restore save game while game is running!");
  }

  @Override
  public GameMemento generateSaveData(World w) {

    List<Terrain> terr = new ArrayList<>();
    for (int row = 0; row < w.getBoardHeight(); row++) {
      for (int col = 0; col < w.getBoardWidth(); col++) {
        terr.add(w.getBoard().getTileAt(new Coord(row, col)).getTerrain());
      }
    }

    return new GameMemento(w.getBoardHeight(), w.getBoardWidth(), w.getEntities(),
        w.getEntities().stream().map(GameObject::getTile).map(t -> {
          return t.location;
        }).collect(Collectors.toList()), null, terr, w.updates, w.getDomainState(),
        w.totalTreasure); // we don't need the movement controllers at this time
  }
}
