package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * The controller interface defines the operations that a controller must be
 * able to perform. A controller is a module that encapsulates the logic that a
 * GameObject uses to decide how it will move during a game tick. Classes that
 * implement this interface effectively define the AI for the objects. In that
 * sense the game offers a fixed number of behaviors to choose from.
 *
 * @author Benjamin
 *
 */
public interface MovementController {
  /**
   * Generate the command that the object this controller represents wants to do.
   * NOTE: If your movement controller decides to do nothing, it should append a
   * new NoMove() to world.event to help produce a record of all the actions that
   * occured.
   *
   * @param w           the world this move will be applied to
   * @param elapsedTime the time since the last game update - this may be changed
   *                    in favour for a different game loop that uses fixed time
   *                    steps
   * @return the command the gameObject wants to perform
   */
  public Command update(World w, double elapsedTime);
}
