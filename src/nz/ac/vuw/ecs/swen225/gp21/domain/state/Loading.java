package nz.ac.vuw.ecs.swen225.gp21.domain.state;

import nz.ac.vuw.ecs.swen225.gp21.domain.ArrayBoard;
import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.domain.State;
import nz.ac.vuw.ecs.swen225.gp21.domain.Tick;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Block;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Chip;

/**
 * The loading state represents a world that is not initialized
 * and cannot simulate the world
 * But it can load in level data
 * Remember to call doneLoading() when you've added 
 * the external entities
 * @author Benjamin
 *
 */
public class Loading implements State {
	
	@Override
	public void loadLevel(World w, Level level) {
		w.updates = 0;
		w.setBoard(new ArrayBoard(level));
		for(int row = 0; row < level.rows; row++) {
			for(int col = 0; col < level.columns; col++) {
				Coord c = new Coord(row, col);
				String entityNameAtIndex = level.entityNameAt(c);
				if(entityNameAtIndex == null) continue;
				boolean added = w.addObject(nameToGameObject(w, entityNameAtIndex), c);
			}
		}
//		w.doneLoading();
	}
	/**
	 * Convert a name into a game object that has the name
	 * @param w the world the object will be added to
	 * @param name the name of the game object
	 * @return GameObject of that type
	 */
	private GameObject nameToGameObject(World w, String name) {
		//TODO ouch! not sure how we can make this better :(
		//Marco would be displeased
		switch(name) {
		case "Chip":
			return new Chip(w);
		case "Block":
			return new Block(w);
		default:
			throw new IllegalArgumentException("Unknown GameObject type! ->"+name);
		}
	}
	
	@Override
	public Tick update(World w, double elapsedTime) {
		throw new IllegalStateException("Cannot simulate world while world is loading!");
	}

	@Override
	public int getBoardWidth(World w) {
		throw new IllegalStateException("Cannot get board width while world is loading!");
	}

	@Override
	public int getBoardHeight(World w) {
		throw new IllegalStateException("Cannot get board height while world is loading!");
	}

	@Override
	public boolean isCoordValid(World w, Coord c) {
		throw new IllegalStateException("Cannot verify coordinate while world is loading!");
	}

	@Override
	public boolean addObject(World w, GameObject e, Coord c) {
		if(e == null || c == null) throw new IllegalArgumentException("Cannot add null to the game!");
		if(e instanceof Chip) w.setPlayer((Chip)e);
		w.getEntities().add(e);
		w.getBoard().addObject(e, c); //this method will throw an exception if it fails
		return true;
	}

	@Override
	public void moveChipLeft(World w) {
		throw new IllegalStateException("Cannot move chip while world is loading!");
	}

	@Override
	public void moveChipUp(World w) {
		throw new IllegalStateException("Cannot move chip while world is loading!");
	}

	@Override
	public void moveChipDown(World w) {
		throw new IllegalStateException("Cannot move chip while world is loading!");
	}

	@Override
	public void moveChipRight(World w) {
		throw new IllegalStateException("Cannot move chip while world is loading!");
	}
}
