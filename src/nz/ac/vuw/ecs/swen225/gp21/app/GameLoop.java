package nz.ac.vuw.ecs.swen225.gp21.app;

import java.util.Queue;

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
	
	private boolean isPaused;
	
	private boolean isTerminated;
	
	/**
	 * Construct the GameLoop.
	 * Doesn't actually run until you call run().
	 * @param actions : the reference to the actions queue in the Controller.
	 * @param control : the reference to the Controller.
	 */
	public GameLoop(Queue<Action> actions, Controller control) {
		this.actions = actions;
		this.control = control;
		this.isPaused = false;
		this.isTerminated = false;
	}
	
	@Override
	public void run() {
		while (true) {
			// First thing is to check if paused or terminated.
			if (isTerminated) break;
			if (isPaused) {
				// Poll something from the queue if it's there but don't execute unless it's a resume 
				// or termination.
				if (!actions.isEmpty()) {
					Action a = actions.poll();
					if (a instanceof ResumeAction || a instanceof ExitAction) {
						a.execute(control);
					}
				}
			} else { // Otherwise, execute away.
				// Poll something from the queue if it's there.
				if (!actions.isEmpty()) {
					Action a = actions.poll();
					a.execute(control);
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
	
	void setPause(boolean p) {
		this.isPaused = p;
	}
	
	void setTerminated(boolean t) {
		this.isTerminated = t;
	}

}
