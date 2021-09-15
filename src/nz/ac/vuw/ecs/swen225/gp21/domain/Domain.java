package nz.ac.vuw.ecs.swen225.gp21.domain;
import java.util.*;

import nz.ac.vuw.ecs.swen225.gp21.domain.state.Running;
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
	 * Hacked method to make my program work.
	 */
	public default void doneLoading() {
		setState(new Running());
	}
	
	/**
	 * Get the state that the domain is in.
	 * Some operations can't be applied if the domain is in the wrong state
	 * i.e. Can't update while loading
	 * @return The state of the domain
	 */
	public State getDomainState();
	/**
	 * Set the state of the domain.
	 * Not sure if we need this one.
	 * But it allows the App to tell us if we are:
	 * 	loading
	 * 	replaying
	 * @param s the new domain state
	 */
	public void setState(State s);
	/**
	 * Get the number of updates that have occurred to the domain.
	 * When running, this is the number of updates since a game has started.
	 * When replaying, this is the current point in the replay.
	 * @return the number of updates that have occurred.
	 */
	public int getUpdateCount();
//==============
//HELPERS FOR RENDERING INTERFACE
	/**
	 * Return a reference to the board.
	 * Board can be queried for the information needed
	 * to display the game.
	 * TODO reject request if in loading state?
	 * @return A reference to the game board
	 */
	public Board getBoard();
	/**
	 * Returns the row / column of the GameObject the player is Controlling.
	 * Can be used in conjunction with getBoard() to get the player Object. 
	 * @return the board location of the object the player is controlling.
	 */
	public Coord getPlayerLocation();
//==============
//LOADING + SAVING METHODS
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
	public void loadLevelData(Level level);
	/**
	 * Restore the world to some previous state.
	 * Works by loading the level then fast forwarding through the ticks to get to 
	 * the latest update.
	 * @param level The level data the restored game was using
	 * @param updates List of updates leading to the state the game was in
	 */
	public void restoreGame(Level level, List<Tick> updates); //I'm not sure if we really want any of these three methods.
	//OR!!! <Generate a deep copy>								Replaying should just be: load level + setState(replaying) + start sending ticks
	public void restoreDomain(Domain d);					  //Restoring saved games, Still not sure about, because there's such tight coupling at the moment
	public Domain getDomain(Domain d);
//why not both?
//==============
	
//=============
//INFORMATION GENERATION
	/**
	 * Simulate the game for one time interval, generates a new tick
	 * @param elapsedTime the amount of time since the last update
	 * @return A record of the events that occured during the update
	 */
	public Tick update(double elapsedTime); 

//===================
//REPLAYING TICK HELPERS
//NOTE: to replay -> 	load initial level conditions.
//						forwardTick(1st tick);
//						forwardTick(2nd tick); etc etc
	
//NOTE: to rewind ->	forwardTick(Nth tick);
//						backTick(Nth tick);
//						backTick(Nth-1 tick);
	/**
	 * Applies the events stored in the loaded tick.
	 * Does nothing if the events have already been applied. TODO change to exception? return boolean if it worked?
	 * @param t the next tick in the replay stream
	 */
	public void forwardTick(Tick t);
	/**
	 * Undoes the events in the loaded tick.
	 * Changes the next expected tick to (t.index - 1)
	 * Does nothing if the game is at the first update (initial game conditions) TODO change to exception? return boolean if it worked?
	 * @param t the last applied tick in the replay stream
	 */
	public void backTick(Tick t);
//===================
//API 
	/**
	 * Attempt to move chip up on the next update
	 * Only accepted when in running state.
	 */
	public void moveChipUp();
	/**
	 * Attempt to move chip down on the next update
	 * Only accepted when in running state.
	 */
	public void moveChipDown();
	/**
	 * Attempt to move chip to the left next update
	 * Only accepted when in running state.
	 */
	public void moveChipLeft();
	/**
	 * Attempt to move chip to the right next update
	 * Only accepted when in running state.
	 */
	public void moveChipRight();
//=======================
//DOMAIN CONDITION
	/**
	 * Return if the domain is in the game over state.
	 * @return true if the game is in the game over state
	 */
	public boolean isGameOver();
}
