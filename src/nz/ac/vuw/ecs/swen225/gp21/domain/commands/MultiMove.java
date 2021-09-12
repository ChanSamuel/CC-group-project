package nz.ac.vuw.ecs.swen225.gp21.domain.commands;

import nz.ac.vuw.ecs.swen225.gp21.domain.Command;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;
import java.util.*;
/**
 * The MultiMove encapsulates an action that occurred and any cascading actions that happened because of it.
 * @author Benjamin
 *
 */
public final class MultiMove implements Command {
	/**
	 * List of all the events that occurred as a result
	 * of one GameObjects update() call
	 */
	List<Command> events = new LinkedList<>();
	/**
	 * Save an event that occured during an update
	 * @param event the event that occured during one object's update
	 */
	public void saveEvent(Command event) {
		events.add(event);
	}
	
	@Override
	public void execute(World w) {
		for(Command c : events) c.execute(w);
	}

	@Override
	public void undo(World w) {
		Collections.reverse(events); //TODO needs testing!
		for(Command c : events) c.undo(w);
		Collections.reverse(events);
	}
	/**
	 * Determine if this MultiMove's first event is a No Move command.
	 * @return true if first move is no move.
	 */
	public boolean isFirstNoMove() {
		return events.isEmpty() ? false : (events.get(0) instanceof NoMove) ;
	}

}
