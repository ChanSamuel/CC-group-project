package nz.ac.vuw.ecs.swen225.gp21.domain.state;

import java.util.*; //TODO temp addition
import java.util.stream.Collectors;
import nz.ac.vuw.ecs.swen225.gp21.domain.Coord; //TODO temp addition
import nz.ac.vuw.ecs.swen225.gp21.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.domain.State;
import nz.ac.vuw.ecs.swen225.gp21.domain.Tick;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;
import nz.ac.vuw.ecs.swen225.gp21.domain.WorldSave;
import nz.ac.vuw.ecs.swen225.gp21.domain.commands.DirectMove;
import nz.ac.vuw.ecs.swen225.gp21.domain.commands.MoveDown;
import nz.ac.vuw.ecs.swen225.gp21.domain.commands.MoveLeft;
import nz.ac.vuw.ecs.swen225.gp21.domain.commands.MoveRight;
import nz.ac.vuw.ecs.swen225.gp21.domain.commands.MoveUp;
import nz.ac.vuw.ecs.swen225.gp21.domain.commands.MultiMove;
import nz.ac.vuw.ecs.swen225.gp21.domain.commands.NoMove;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Terrain;

/**
 * The running state represents a world that has been initialized and is capable
 * of simulating the world.
 *
 * @author Benjamin
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
  public Tick update(World w, double elapsedTime) {
    worldCheck(w);
    w.updates++;
    Tick tick = new Tick(w.updates);
    // TODO make ticks && commands && moves independent of a
    // specific world instance.
    // update all game objects
    for (GameObject e : w.getEntities()) {
      w.event = new MultiMove();
      e.update(elapsedTime, w);
      tick.addEvent(w.event);
    }
    if (w.getBoard().getRemainingChips() == 0) {
      tick.addEvent(w.getBoardWorld().openExit());
      // return multimove that captures the terrain change
    }

    assert (w.totalTreasure == w.getBoard().getRemainingChips()
        + (w.getPlayer() == null ? 0 : w.getPlayer().treasureCollected));
    return tick;
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
      w.event.saveEvent(new DirectMove(beforeD, beforeC, terrainAtDest, o));
    } else {
      w.event.saveEvent(new NoMove());
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
  public void forwardTick(World w, Tick t) {
    throw new IllegalStateException(
        "Cannot apply tick when game is running! World should be in replaying state!");
  }

  @Override
  public void backTick(World w, Tick t) {
    throw new IllegalStateException(
        "Cannot apply tick when game is running! World should be in replaying state!");
  }

  @Override
  public void restoreGame(World world, WorldSave save) {
    throw new IllegalStateException("Cannot restore save game while game is running!");
  }

  @Override
  public WorldSave generateSaveData(World w) {
    WorldSave answer = new WorldSave();

    answer.setRows(w.getBoardHeight());
    answer.setCols(w.getBoardWidth());
    answer.setCurrentState(w.getDomainState());
    answer.setGameObjects(w.getEntities());
    answer.setGameObjectLocations(w.getEntities().stream().map(GameObject::getTile).map(t -> {
      return t.location;
    }).collect(Collectors.toList()));
    List<Terrain> terr = new ArrayList<>();
    for (int row = 0; row < w.getBoardHeight(); row++) {
      for (int col = 0; col < w.getBoardWidth(); col++) {
        terr.add(w.getBoard().getTileAt(new Coord(row, col)).getTerrain());
      }
    }
    answer.setUpdates(w.updates);
    answer.setTotalTreasure(w.totalTreasure);

    return answer;
  }
}
