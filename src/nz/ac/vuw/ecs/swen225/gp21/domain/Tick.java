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
	 * reference to the world that generated this tick
	 */
	public final World w;
	/**
	 * this number specifies which update generated this tick
	 */
	public final int index;
	/**
	 * List of all the events that were performed in the update
	 * that this tick was generated in.
	 */
	final List<Command> events;
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
	 * @param w the world that generated this tick
	 */
	public Tick(int index, World w) {
		this.w = w;
		this.index = index;
		this.events = new LinkedList<Command>();
	}
	
	/**
	 * Add a new command to this tick's record of events that happened during the update
	 * @param event an event that happened during the update.
	 */
	public void addEvent(MultiMove event) { events.add(event); }
	
	/**
	 * Redo all the actions that occurred during this tick.
	 */
	public void redoTick() {
		Collections.reverse(events);
		for(Command c : events) c.execute(w);
		Collections.reverse(events);
	}
	/**
	 * Undo all the actions that occurred during this tick.
	 */
	public void undoTick() {
		for(Command c : events) c.undo(w);
	}
	/**
	 * Get all the events that happened during this tick.
	 * @return the list of events that happened during this update
	 */
	public List<Command> getEvents(){
		return Collections.unmodifiableList(events);
	}	
}
