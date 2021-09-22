package nz.ac.vuw.ecs.swen225.gp21.app;

import java.lang.reflect.InvocationTargetException;
import java.util.Queue;

import javax.swing.SwingUtilities;

import nz.ac.vuw.ecs.swen225.gp21.domain.Tick;
import nz.ac.vuw.ecs.swen225.gp21.renderer.WorldJPanel;
import nz.ac.vuw.ecs.swen225.gp21.renderer.WrapperJPanel;

/**
 * A runnable which runs the game loop.
 * This runnable is meant to be run on a seperate thread.
 * @author Sam
 *
 */
public class GameLoop implements Runnable {
	
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
	 * The number of ticks (iterations through the game loop) each second.
	 */
	private volatile int tps = BASE_TPS;
	
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
		this.isPlaying = false;
		this.isAutoPlay = false;
		this.isReplay = false;
	}
	
	@Override
	public void run() {
		long updatedTime = -1;
		long elapsedTime = 0;
		
		while (true) {
			long start = System.currentTimeMillis();
			
			// First, check if other modules can be updated.
			if (isPlaying) {
				if (isPaused) {
					// Poll something from the queue if it's there but don't execute unless it's a resume.
					
					Action a = actions.peek();
					boolean cond = a instanceof TogglePauseAction;
					pollAction(cond);
					
					// Update the renderer (even though nothing happens, we still do this so that it doesn't
					// give a black screen upon resizing the window).
					updateRenderer();
				} else { // Otherwise, proceed normally.
					
					if (isAutoPlay) { // Here, we assume that auto play is true only when replay is true.
						
						Tick t = control.recorder.nextTick();
						control.world.forwardTick(t);
						
						// Update the renderer.
						updateRenderer();
						
					} else if (isReplay) {
						
						// Update the renderer.
						updateRenderer();
						
					} else {
						// Update the world.
						if (updatedTime != -1) {elapsedTime = System.currentTimeMillis() - updatedTime;}
						Tick t = control.world.update(elapsedTime);
						updatedTime = System.currentTimeMillis();
						
						// Update the renderer.
						updateRenderer();
						
						
						// Recorder things here.
						control.recorder.addTick(t);
						
					}
					
					// Poll an action from the queue if it's there.
					pollAction(true);
				}
			} else {
				// If other modules can't be updated yet, then only execute an action if it is a start action.
				Action topAct = actions.peek();
				boolean c = topAct instanceof StartAction || topAct instanceof NewGameAction || 
						topAct instanceof LoadGameAction || topAct instanceof LoadLevel1Action || 
						topAct instanceof LoadTestLevelAction || topAct instanceof LoadLevel2Action || 
						topAct instanceof LoadReplayAction;
				
				pollAction(c);
			}
			
			// Finally, wait for the remainder time of the 40ms (or however much) since start has not occured.
			try {
				long delay = tps - (System.currentTimeMillis() - start);
				if (delay > 0) {
					Thread.sleep(delay);
				}
			} catch (InterruptedException e) {
				throw new Error("Thread sleep interrupted!", e);
			}
		}
	}
	
	/**
	 * Internal helper method for polling an action and executes it if canExecute is true and the 
	 * actions queue is not empty.
	 * @param canExecute
	 */
	private void pollAction(boolean canExecute) {
		if (actions.isEmpty()) return;
		
		Action a = actions.poll();
		if (canExecute) {
			a.execute(control);
			System.out.println("Performed action " + a.actionName());
		}
	}
	
	private void updateRenderer() {
		try {
			SwingUtilities.invokeAndWait(() -> {
				control.renderer.redraw(control.world);
			});
		} catch (InvocationTargetException e) {
			throw new Error(e.getMessage(), e);
		} catch (InterruptedException e) {
			throw new Error(e.getMessage(), e);
		}
	}
	
	
	/*
	 * Setters to write to the GameLoop.
	 * These should only be used by Action instances.
	 */
	
	void setPause(boolean p) {
		this.isPaused = p;
	}
	
	void setIsPlaying(boolean p) {
		this.isPlaying = p;
	}
	
	void setTps(int t) {
		this.tps = t;
	}
	
	void setAutoPlay(boolean a) {
		if (a && !this.isReplay) return; // Don't set to true if we aren't in replay yet.
		this.isAutoPlay = a;
	}
	
	void setIsReplay(boolean r) {
		this.isReplay = r;
		// Precondition to ensure auto play is only ever true when replay is true.
		if (!r) this.isAutoPlay = false;
	}
	
	boolean getIsPaused() {
		return this.isPaused;
	}
	
	boolean getIsPlaying() {
		return this.isPlaying;
	}
	
	int getTps() {
		return tps;
	}
	
	boolean getIsAutoPlay() {
		return this.isAutoPlay;
	}
	
	boolean getIsReplay() {
		return this.isReplay;
	}

}
