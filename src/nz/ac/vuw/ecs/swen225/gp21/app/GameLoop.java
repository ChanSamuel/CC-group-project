package nz.ac.vuw.ecs.swen225.gp21.app;

import java.util.Optional;
import java.util.Queue;

import nz.ac.vuw.ecs.swen225.gp21.app.actions.Action;
import nz.ac.vuw.ecs.swen225.gp21.app.controllers.Controller;

/**
 * A runnable which runs the game loop.
 * This runnable is meant to be run on a seperate thread.
 * @author Sam
 *
 */
public class GameLoop implements Runnable {
	
	/**
	 * The queue of actions from the Controller that we poll every tick.
	 */
	private Queue<Action> actions;
	
	/**
	 * The controller, we need it to execute actions with.
	 */
	private Controller control;
	
	/**
	 * Construct the GameLoop.
	 * Doesn't actually run until you call run().
	 * @param actions : the reference to the actions queue in the Controller.
	 * @param control : the reference to the Controller.
	 */
	public GameLoop(Queue<Action> actions, Controller control) {
		this.actions = actions;
		this.control = control;
	}
	
	@Override
	public void run() {
		while (true) {
			// Poll something from the queue if it's there.
			if (!actions.isEmpty()) {
				Optional<Boolean> op = actions.poll().execute(control);
				if (op.isPresent() && op.get()) {
					System.out.println("Input was valid!");
				}
			}
			
			// Finally, wait for 200ms
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				throw new Error("Thread sleep interrupted!", e);
			}
		}
	}

}
