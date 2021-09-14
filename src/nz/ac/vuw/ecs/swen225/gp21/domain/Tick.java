package nz.ac.vuw.ecs.swen225.gp21.domain;

import java.util.*;

import nz.ac.vuw.ecs.swen225.gp21.domain.commands.MultiMove;

/**
 * A tick is an encapsulation of all the events that occured during a game update
 * @author Benjamin
 *
 */
public final class Tick {
	/**
	 * this number specifies which update generated this tick
	 */
	public final int index;
	/**
	 * List of all the events that were performed in the update
	 * that this tick was generated in.
	 */
	final List<MultiMove> events;
	/**
	 * Is this tick the final tick in the replay stream?.
	 * NOTE: This MUST be set by an external monitor of the game-play.
	 * The domain has no way of knowing which update will be the last
	 * because the game could be ended at anypoint in time.
	 */
	public boolean isFinalTick = false;
	/**
	 * Create a new tick to store all the events that occur in an update.
	 * @param index the tick ID.
	 */
	public Tick(int index) {
		this.index = index;
		this.events = new LinkedList<MultiMove>();
	}
	
	/**
	 * Add a new command to this tick's record of events that happened during the update
	 * @param event an event that happened during the update.
	 */
	public void addEvent(MultiMove event) { events.add(event); }
	
	/**
	 * Redo all the actions that occurred during this tick.
	 * @param w the world this tick is being applied to.
	 */
	public void redoTick(World w) {
		Collections.reverse(events);
		for(Command c : events) c.execute(w);
		Collections.reverse(events);
	}
	/**
	 * Undo all the actions that occurred during this tick.
	 * @param w the world this tick is being applied to.
	 */
	public void undoTick(World w) {
		for(Command c : events) c.undo(w);
	}
	/**
	 * Get all the events that happened during this tick.
	 * @return the list of events that happened during this update
	 */
	public List<Command> getEvents(){
		return Collections.unmodifiableList(events);
	}
	/**
	 * Determine if the player entity moved in this update.
	 * @return true if the player object moved
	 */
	public boolean didPlayerMove() {
		if(events.isEmpty()) return false;
		return !events.get(0).isFirstNoMove();
	}	
	/**
	 * Determine if any object moved in this tick
	 */
	public boolean didAnyObjectMove() {
		if(events.isEmpty()) return false;
		for(MultiMove c : events) if(!c.isFirstNoMove()) return true;
		return false;
	}
}
