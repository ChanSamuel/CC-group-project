package nz.ac.vuw.ecs.swen225.gp21.domain.commands;

import nz.ac.vuw.ecs.swen225.gp21.domain.*;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.*;
/**
 * Direct move is used by replay to directly move actors, without worrying about game logic checks.
 * @author Benjamin
 *
 */
public final class DirectMove implements Command {
	/**
	 * Direction object was facing before the move
	 */
	private final Direction beforeDir;
	/**
	 * Direction object was facing after the move
	 */
	private final Direction afterDir;
	/**
	 * The position of the game object before the move
	 */
	private final Coord beforePos;
	/**
	 * Position of the gameObject after the move
	 */
	private final Coord afterPos;
	/**
	 * The terrain of the tile at afterPos BEFORE the move was applied
	 */
	private final Terrain beforeTerrain;
	/**
	 * The object that moved
	 */
	private final GameObject moved;
	/**
	 * Create a new direct move command.
	 * It needs to store all in the information needed to undo the move.
	 * @param beforeDir
	 * @param beforePos
	 * @param beforeTerrain
	 * @param moved
	 */
	public DirectMove(Direction beforeDir ,Coord beforePos, Terrain beforeTerrain, GameObject moved) {
		this.beforeDir = beforeDir;
		this.beforePos = beforePos;
		this.beforeTerrain = beforeTerrain;
		this.moved = moved;
		this.afterPos = moved.getTile().location;
		this.afterDir = moved.dir;
	}
	
	@Override
	public void execute(World w) {
		moved.dir = afterDir;
		w.getBoard().tryMoveObject(afterPos, moved);
	}

	@Override
	public void undo(World w) {
		moved.dir = beforeDir;
		w.getBoard().moveObjectBack(beforePos, moved);
		w.getTileAt(afterPos).resetTerrain(moved, beforeTerrain);
	}

}
