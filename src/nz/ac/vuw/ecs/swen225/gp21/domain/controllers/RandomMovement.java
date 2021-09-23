package nz.ac.vuw.ecs.swen225.gp21.domain.controllers;

import nz.ac.vuw.ecs.swen225.gp21.domain.Command;
import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.MovementController;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;
import nz.ac.vuw.ecs.swen225.gp21.domain.commands.MoveDown;
import nz.ac.vuw.ecs.swen225.gp21.domain.commands.MoveLeft;
import nz.ac.vuw.ecs.swen225.gp21.domain.commands.MoveRight;
import nz.ac.vuw.ecs.swen225.gp21.domain.commands.MoveUp;
import nz.ac.vuw.ecs.swen225.gp21.domain.commands.NoMove;

/**
 * This movement controller makes the GameObjects that use it move in random
 * directions The constructor types determine properties about the movement,
 * such as the frequency of movements, or probability of moving.
 *
 * @author Benjamin
 *
 */
public final class RandomMovement implements MovementController {

  /**
   * Frequency of moves, i.e. how many times entities controlled by this
   * controller move per second i.e. if I want to move one tile in one second and
   * ticks happen every 200ms { Game.freq } then freq should be f = 1 / T (second)
   */
  private final double frequency;
  /**
   * Number of milliseconds before we move again oops, now each entity needs its
   * own movement controller. :P
   */
  private double timeToNextMove;

  /**
   * Random controller that will move in a random direction a certain number of
   * times per second.
   *
   * @param frequency the number of times entities controlled by this controller
   *                  will move each second
   */
  public RandomMovement(double frequency) {
    if (frequency < 0) {
      throw new IllegalArgumentException(
          "the frequency of random movements must be positive: " + frequency);
    }
    this.frequency = frequency;
    this.timeToNextMove = 0.0;
  }

  @Override
  public Command update(World w, GameObject o, double elapsedTime) {
    if (timeToNextMove > 0.0) {
      // do some calculation to bring the time down TODO needs testing
      timeToNextMove -= elapsedTime;
    } else {
      // reset the time to next move
      double secondsToNextMove = 1.0 / frequency;
      double millisInSecond = 1000.0;
      timeToNextMove = (secondsToNextMove * millisInSecond);
      return randomMove(o);
    } // No, it was not time to move yet
    Command response = new NoMove();
    w.event.saveEvent(response);
    return response;
  }

  /**
   * Picks a random move.
   *
   * @return a move in a random direction
   */
  private Command randomMove(GameObject o) {
    double random = Math.random();
    if (random < 0.25) {
      return new MoveUp(o);
    } else if (random < 0.50) {
      return new MoveDown(o);
    } else if (random < 0.75) {
      return new MoveLeft(o);
    } else { // TODO needs testing
      return new MoveRight(o);
    }
  }
}
