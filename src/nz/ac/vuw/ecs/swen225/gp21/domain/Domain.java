package nz.ac.vuw.ecs.swen225.gp21.domain;
import java.util.*;
/**
 * Domain will be the publicly accessible interface that other modules will use to interact with domain.
 * Domains need to be able to support the following operations:
 * 	-Load levels {Maze Data, GameObject location data}.
 * 	-Generate data to represent the state of the world after each update. ("Ticks") -> [can be used by renderer and recorder and persistence modules]
 * 	-Restore a game state from a level + tick (To facilitate loading saved games)
 * 	-Expose public API methods to simulate the game { update(...), moveChip(...), getState(...), loadObject(...), loadLevel() }
 * @author Benjamin
 *
 */
public interface Domain extends Subject{
	/**
	 * Get the state that the domain is in.
	 * Some operations can't be applied if the domain is in the wrong state
	 * i.e. Can't update while loading
	 * @return The state of the domain
	 */
	public State getDomainState();
	/**
	 * When in the loading state, call this method to insert a new GameObject into the world.
	 * @param o The object being added
	 * @param c the location it is being added to
	 */
	public void addGameObject(GameObject o, Coord c);
	/**
	 * Load a level into the world.
	 * Calling this method assumes a new game is being started.
	 * A new list of ticks will be generated as past of level initialization. 
	 * @param level the level data.
	 */
	public void LoadLevelData(Level level);
	/**
	 * Restore the world to some previous state.
	 * Works by loading the level then fast forwarding through the ticks to get to 
	 * the latest update.
	 * @param level The level data the restored game was using
	 * @param updates List of updates leading to the state the game was in
	 */
	public void restoreGame(Level level, List<Tick> updates);
	/**
	 * Simulate the game for one time interval, generates a new tick
	 * TODO should persistence module receive the tick?
	 * @param elapsedTime the amount of time since the last update
	 */
	public void update(double elapsedTime);
	/**
	 * Re-applies the events in the next tick.
	 * Moves the current tick forward one.
	 * Does nothing if the game is already at the latest update. TODO change to exception? return boolean if it worked?
	 */
	public void replayTick();
	/**
	 * Undoes the updates in the last update/tick that occurred
	 * Moves the current tick back one.
	 * Does nothing if the game is at the first update (initial game conditions) TODO change to exception? return boolean if it worked?
	 */
	public void undoTick();
	/**
	 * Fast forward or rewind through the ticks until you reach the specified tick.
	 * @param index the location of the tick
	 */
	public void goToTick(int index);
	/**
	 * Get all the ticks that have occurred so far in this game
	 * A tick represents all the events that occurred in one game update
	 * TODO should persistence module store these?
	 * @return All the ticks that occurred since the session started
	 */
	public List<Tick> getAllTicks();
	/**
	 * Get the tick the Domain is currently at
	 * @return the current tick
	 */
	public Tick getCurrentTick();
	/**
	 * Attempt to move chip up on the next update
	 */
	public void moveChipUp();
	/**
	 * Attempt to move chip down on the next update
	 */
	public void moveChipDown();
	/**
	 * Attempt to move chip to the left next update
	 */
	public void moveChipLeft();
	/**
	 * Attempt to move chip to the right next update
	 */
	public void moveChipRight();
}
