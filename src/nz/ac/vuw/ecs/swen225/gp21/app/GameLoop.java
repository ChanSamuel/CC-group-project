package nz.ac.vuw.ecs.swen225.gp21.app;

import java.lang.reflect.InvocationTargetException;
import java.util.Queue;
import javax.swing.SwingUtilities;

/**
 * GameLoop contains a loop within it's run method which periodically updates
 * the game, and accepts new actions every tick.
 * 
 * <p>The GameLoop restricts the different actions possible depending on the
 * current state. It also has getters and setters for fields which represent
 * it's state, Actions can mutate these fields to change the state of the game.
 * 
 * <p>Such fields are made volatile to prevent thread caching and inconsistent
 * viewing of the field.

 * @author chansamu1 300545169
 *
 */
public final class GameLoop implements Runnable {

  /**
   * The base playback speed.
   */
  public static final int BASE_TPS = 40;

  /**
   * The queue of actions from the Controller that we poll every tick.
   */
  private volatile Queue<Action> actions;

  /**
   * The controller, we need it to execute actions with.
   */
  private Controller control;

  /**
   * The time left to play the game.
   */
  private volatile double timeLeft;

  /**
   * The time since last timer update.
   */
  private volatile long t1;

  /**
   * Boolean indicating if the game is paused or not.
   */
  private volatile boolean isPaused;

  /**
   * Boolean indicating if the updated mechanic is running or not.
   */
  private volatile boolean isPlaying;

  /**
   * Boolean indicating if the game is in autoplay mode or not.
   */
  private volatile boolean isAutoPlay;

  /**
   * Boolean indicating if the game is in replay mode or not.
   */
  private volatile boolean isReplay;

  /**
   * The multiplier of the base speed.
   */
  private volatile double speedMult = 1;

  /**
   * The time the current replay occured at.
   */
  private long currentEventTime = -1;

  /**
   * The time the previous replay event occured at.
   */
  private long previousEventTime = -1;

  /**
   * Construct the GameLoop. Doesn't actually run until you call run().

   * @param actions : the reference to the actions queue in the Controller.
   * @param control : the reference to the Controller.
   */
  public GameLoop(Queue<Action> actions, Controller control) {
    this.actions = actions;
    this.control = control;
    this.isPaused = false;
    this.isPlaying = false;
    this.isAutoPlay = false;
    this.isReplay = false;
  }

  @Override
  public void run() {
    long updatedTime = -1;
    t1 = -1; // The time at last update.
    long elapsedTime = 0;

    while (true) {
      long start = System.currentTimeMillis();

      // First, check if other modules can be updated.
      if (isPlaying) {
        if (isPaused) {
          // Poll something from the queue if it's there but don't execute
          // if it's a forward/back tick, or movement.

          Action a = actions.peek();
          boolean cond = !(a instanceof AdvanceTickAction) && !(a instanceof MovementAction);
          pollAction(cond);

          // Update the renderer (even though nothing happens, we still do this so that it
          // doesn't
          // give a black screen upon resizing the window).
          updateRenderer();

          t1 = System.currentTimeMillis();

        } else { // Otherwise, proceed normally.

          if (isAutoPlay) { // Here, we assume that auto play is true only when replay is true.

            AutoTickAction.executeStatic(control);

            // Update the renderer.
            updateRenderer();

          } else if (isReplay) {

            // Don't need to do anything else, because the actions will handle this.

            // Update the renderer.
            updateRenderer();

          } else {
            // Update the world.
            if (updatedTime != -1) {
              elapsedTime = System.currentTimeMillis() - updatedTime;
            }
            control.world.update(elapsedTime);
            updatedTime = System.currentTimeMillis();

            // Update the renderer.
            updateRenderer();
          }

          if (t1 != -1) { // If we are not on the first tick.
            long t2 = System.currentTimeMillis() - t1; // The time since last timer update.

            // Calculate the time left using formula : timeLeft - elapsed.
            this.timeLeft = this.timeLeft - ((double) t2 / 1000);
            updateTimer();

            // Set the last updated time to just now.
            t1 = System.currentTimeMillis();

          } else {
            t1 = System.currentTimeMillis();
          }

          // Poll an action from the queue if it's there.
          pollAction(true);
        }
      } else {
        // If other modules can't be updated yet, then only execute an action if it is a
        // start action
        // or help action.
        Action topAct = actions.peek();
        boolean c = topAct instanceof StartAction || topAct instanceof DisplayHelpAction;

        pollAction(c);

        t1 = System.currentTimeMillis();

      }

      // Finally, wait for the remainder time of the 40ms (or however much).
      // The formula is different depending if we are in autoplay or not.
      try {
        long delay = -1;

        // If in auto-play and a previous replay event exists
        if (isAutoPlay && this.previousEventTime != -1) {

          // Formula here is:
          // Let deltaE = difference in event timestamps
          // Let deltaT = difference in current time and last update.
          // Let speedMult = how many times faster to playback at.
          // delay = (deltaE - deltaT) / speedMult
          long deltaE = this.currentEventTime - this.previousEventTime;
          long deltaT = System.currentTimeMillis() - start;
          delay = deltaE - deltaT;
          delay = (int) (((double) delay / speedMult) + 0.5);

        } else {
          delay = BASE_TPS - (System.currentTimeMillis() - start);
        }

        if (delay > 0) {
          Thread.sleep(delay);
        }
      } catch (InterruptedException e) {
        throw new Error("Thread sleep interrupted!", e);
      }
    }
  }

  /**
   * Internal helper method for polling an action and executes it if canExecute is
   * true and the actions queue is not empty.

   * @param canExecute : whether this action should also be executed as well.
   */
  private void pollAction(boolean canExecute) {
    if (actions.isEmpty()) {
      return;
    }
    Action a = actions.poll();
    if (canExecute) {
      a.execute(control);
    }
  }

  /**
   * Helper method to update the renderer on the EventQueue.
   */
  private void updateRenderer() {
    try {
      SwingUtilities.invokeAndWait(() -> {
        control.renderer.redraw(control.world);
      });
    } catch (InvocationTargetException e) {
      throw new Error(e);
    } catch (InterruptedException e) {
      throw new Error(e);
    }
  }

  /**
   * Helper method to update the timer to the time left to the nearest integer.
   */
  private void updateTimer() {
    int timeSecs = (int) (this.timeLeft + 0.5); // Time left in seconds.
    if (this.timeLeft > 0d) {
      UpdateTimerAction.execute(control, timeSecs);
    } else {
      new TimeOutAction().execute(control);
    }
  }

  /*
   * *********************************************** Setters to write to the
   * GameLoop. These should only be used by Action instances.
   * ***********************************************
   */

  /**
   * Set the pause state of the game loop. The state change will occur next tick
   * if valid.

   * @param p : whether the game should be paused or not.
   */
  void setPause(boolean p) {
    this.isPaused = p;
  }

  /**
   * Set the playing state of the game loop. The state change will occur next tick
   * if valid.

   * @param p : whether the game should be in the playing state or menu state.
   */
  void setIsPlaying(boolean p) {
    this.isPlaying = p;
  }

  /**
   * Set the auto play state of the game loop. The state change will occur next
   * tick if valid.

   * @param a : Set whether autoplay back should be on or not.
   */
  void setAutoPlay(boolean a) {
    if (a && !this.isReplay) {
      return; // Don't set to true if we aren't in replay yet.
    }
    this.isAutoPlay = a;
  }

  /**
   * Set the replay state of the game loop. The state change will occur next tick
   * if valid.

   * @param r : Set whether we should be in replay or not.
   */
  void setIsReplay(boolean r) {
    this.isReplay = r;
    // Postcondition to ensure auto play is only ever true when replay is true.
    if (!r) {
      this.isAutoPlay = false;
    }
  }

  /**
   * Get the pause state of the game loop.

   * @return whether paused or not.
   */
  boolean getIsPaused() {
    return this.isPaused;
  }

  /**
   * Get the playing state of the game loop.

   * @return whether playing or not.
   */
  boolean getIsPlaying() {
    return this.isPlaying;
  }

  /**
   * Get the auto playing state of the game loop.

   * @return whether the game is in auto play or not.
   */
  boolean getIsAutoPlay() {
    return this.isAutoPlay;
  }

  /**
   * Get the replay state of the game loop.

   * @return whether the game loop is in replay or not.
   */
  boolean getIsReplay() {
    return this.isReplay;
  }

  /**
   * Get the time left as according to the game loop.

   * @return the time left to play the game.
   */
  int getTimeLeft() {
    return (int) (this.timeLeft + 0.5);
  }

  /**
   * Set the amount of time that the player starts with to play through the level.

   * @param time : time in seconds.
   */
  void setLevelStartTime(int time) {
    this.timeLeft = time;
  }

  /**
   * Set the the timestamp of the current tick being replayed. Also ensures the
   * previous timestamp is updated as well. The state change will occur next tick
   * if valid.

   * @param t : the timestamp of the current replay tick.
   */
  void setReplayTime(long t) {
    this.previousEventTime = this.currentEventTime;
    this.currentEventTime = t;
  }

  /**
   * Set the coefficent of which to multiply the auto-playback speed by. The state
   * change will occur next tick if valid.

   * @param m : the coefficent of the playback speed.
   */
  void setSpeedMult(double m) {
    this.speedMult = m;
  }

  /**
   * Sets all the parameters for the state at the very beginning of play. Does not
   * set the any level related parameters, these must be set by the caller.
   */
  void setToInitialPlayState() {
    this.isAutoPlay = false;
    this.isReplay = false;
    this.isPaused = false;
    this.isPlaying = true;
    this.t1 = System.currentTimeMillis();
    this.currentEventTime = -1;
    this.previousEventTime = -1;
    this.speedMult = 1;
  }

  /**
   * Sets all the parameters for the state during a menu.
   */
  void setToMenuState() {
    this.isAutoPlay = false;
    this.isReplay = false;
    this.isPaused = false;
    this.isPlaying = false;
    this.t1 = -1;
    this.currentEventTime = -1;
    this.previousEventTime = -1;
    this.speedMult = 1;
  }

}
