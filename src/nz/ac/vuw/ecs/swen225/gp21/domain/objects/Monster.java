package nz.ac.vuw.ecs.swen225.gp21.domain.objects;

import nz.ac.vuw.ecs.swen225.gp21.domain.Direction;
import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.MovementController;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;

/**
 * Monster objects are considered hostile to the player entity, chip.
 *
 * @author Benjamin
 *
 */
public abstract class Monster extends GameObject {
  /**
   * Create a new monster.
   *
   * @param w         the world this monster will exist in
   * @param c         the controller that decides how the monster will move
   * @param d         the direction this monster will face when it is created.
   * @param pathLeft  The file path used to render the GameObject facing Left
   * @param pathRight The file path used to render the GameObject facing right
   */
  public Monster(World w, MovementController c, Direction d, String pathLeft, String pathRight) {
    super(w, c, d, pathLeft, pathRight);
  }

  @Override
  public boolean canEntityGoOnTile(GameObject entity) {
    // Chip can walk onto tiles that monsters are one, although this will result in
    // a game over :P
    if (entity instanceof Chip) {
      return true;
    }
    return false;
  }

  @Override
  public void entityEnteredTile(GameObject entity) {
    if (!(entity instanceof Chip)) {
      throw new RuntimeException("Non-chip entity entered tile occupied by monster! at: "
          + currentTile.location + " ->" + entity);
    }
    // Chip walked onto the same tile as a monster, oops
    wor.playerLost();
  }

  @Override
  public void update(double elapsedTime) {
    controller.update(wor, elapsedTime).execute(wor);
    // TODO
    // w.recordExecutedCommand(c.update().execute())
    // doing (w.enqueueCommandToExecuteLater(c.update())) creates a weird
    // concurrency issue if multiple
    // things want to move into the same tile
    // the commands moving them into the tile will still be there, but only one of
    // them will actually move into the tile
    // but when the moves are undone, they won't be put into their original
    // positions

    // So when a game tick record is created, all the commands within it would have
    // already been executed.
    // So it should expose public methods: undoTick() & RedoTick() to make it very
    // clear what it is doing, it could even have a flag
    // to stop you from trying to RedoTick() if you haven't undone it first

    // "This tick represents everything that HAPPENED during the last update."
    // VS
    // "This tick represents everything that will happen during this update."

    // That won't work either
    // Move Chip right -> moves a block right
    // replay move chip right -> moves block right -> execute the move we made for
    // the block -> block moves twice << BlockMove Command :P undo, but not redo ?
  }

  @Override
  public String getName() {
    return getClass().getSimpleName();
  }

  @Override
  public abstract char boardChar();

  @Override
  public String toString() {
    return super.toString() + "Monster";
  }
}
