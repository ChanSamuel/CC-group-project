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
   * The object this movement controller moves oops, now each entity needs its own
   * seperate controller. I don't think this is what I was aiming for
   */
  private final GameObject obj;
  /**
   * Defines the probability that a movement will occur on a given tick.
   */
  private final double probability;
  /**
   * Frequency of moves, i.e. how many times entities controlled by this
   * controller move per second i.e. if I want to move one tile in one second and
   * ticks happen every 200ms { Game.freq } then freq should be f = 1 / T (second)
   */
  private final int frequency;
  /**
   * Number of milliseconds before we move again oops, now each entity needs its
   * own movement controller. :P
   */
  private double timeToNextMove;

  /**
   * Random controller that will try to move every tick, with a given probability
   * each tick.
   *
   * @param o           the object being moved
   *
   * @param probability the probability the object moves each tick
   */
  RandomMovement(GameObject o, double probability) {
    if (o == null) {
      throw new IllegalArgumentException("Object cannot be null");
    }
    if (probability < 0 || probability > 1) {
      throw new IllegalArgumentException(
          "Probability of random movement must be between 0 and 1: " + probability);
    }
    this.obj = o;
    this.frequency = (int) (1 / 0.2); // get the update rate here
    this.probability = probability;
    this.timeToNextMove = 0.0;
  }

  /**
   * Random controller that will move in a random direction a certain number of
   * times per second.
   *
   * @param o         the object being moved
   *
   * @param frequency the number of times entities controlled by this controller
   *                  will move each second
   */
  RandomMovement(GameObject o, int frequency) {
    if (o == null) {
      throw new IllegalArgumentException("Object cannot be null");
    }
    if (frequency > (int) (1 / 0.2) || frequency < 0) {
      throw new IllegalArgumentException(
          "the frequency of random movements must not be greater than the update rate of the game: "
              + frequency);
    }
    this.obj = o;
    this.probability = 1.0;
    this.frequency = frequency;
    this.timeToNextMove = 0.0;
  }

  @Override
  public Command update(World w, double elapsedTime) {
    if (timeToNextMove > 0.0) {
      // do some calculation to bring the time down TODO needs testing
      timeToNextMove -= elapsedTime;
    } else {
      if (probabilityCheck()) { // yes we are definitely going to move, reset the time to next move
        timeToNextMove = (1 / frequency);
        return randomMove();
      }
    } // No, it was not time to move yet
    Command response = new NoMove();
    w.event.saveEvent(response);
    return response;
  }

  /**
   * Make probability based decision on whether the should move this update.
   *
   * @return whether the controller randomly decided that we should move this tick
   */
  private boolean probabilityCheck() {
    if (Math.random() < probability) {
      return true; // TODO needs testing
    }
    return false;
  }

  /**
   * Picks a random move.
   *
   * @return a move in a random direction
   */
  private Command randomMove() {
    double random = Math.random();
    if (random < 0.25) {
      return new MoveUp(obj);
    } else if (random < 0.50) {
      return new MoveDown(obj);
    } else if (random < 0.75) {
      return new MoveLeft(obj);
    } else { // TODO needs testing
      return new MoveRight(obj);
    }
  }
}
