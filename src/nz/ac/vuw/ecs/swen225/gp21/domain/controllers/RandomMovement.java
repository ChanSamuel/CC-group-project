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
 * @author sansonbenj 300482847
 *
 */
public final class RandomMovement implements MovementController {

  /**
   * Frequency of moves, i.e. how many times entities controlled by this
   * controller move per second i.e. if I want to move one tile in one second and
   * ticks happen every 200ms { Game.freq } then freq should be f = 1 / T (second)
   */
  private double frequency;
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
    if (frequency <= 0) {
      throw new IllegalArgumentException(
          "the frequency of random movements must be positive: " + frequency);
    }
    this.frequency = frequency;
    // convert to millis by timing by 1000
    this.timeToNextMove = (1.0 / frequency) * 1000.0;
  }

  /**
   * Create a deep copy of this controller.
   *
   * @param randomMovement the controller being copied
   */
  private RandomMovement(RandomMovement randomMovement) {
    this.frequency = randomMovement.frequency;
    this.timeToNextMove = randomMovement.timeToNextMove;
  }

  /**
   * Default movement constructor for saving.
   */
  public RandomMovement() {
  }

  @Override
  public Command update(World w, GameObject o, double elapsedTime) {
    timeToNextMove -= elapsedTime;
    if (timeToNextMove <= 0.0) {
      // reset the time to next move
      double secondsToNextMove = 1.0 / frequency;
      double millisInSecond = 1000.0;
      timeToNextMove = (secondsToNextMove * millisInSecond);
      return randomMove(o);
    }
    Command response = new NoMove();
    return response;
  }

  /**
   * The amount of times this object moves in a second.
   *
   * @return the frequency of movements.
   */
  public double getFrequency() {
    return frequency;
  }

  /**
   * Get number of milliseconds until this object moves again.
   *
   * @return milliseconds to next move.
   */
  public double getTimeToNextMove() {
    return timeToNextMove;
  }

  /**
   * Picks a random move.
   *
   * @param o the object that will be moved
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

  @Override
  public MovementController clone() {
    return new RandomMovement(this);
  }
}
