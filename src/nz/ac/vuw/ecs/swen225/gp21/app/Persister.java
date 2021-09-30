package nz.ac.vuw.ecs.swen225.gp21.app;

import java.io.File;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.persistency.GameCaretaker;
import nz.ac.vuw.ecs.swen225.gp21.persistency.LevelHandler;
import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;
import nz.ac.vuw.ecs.swen225.gp21.recorder.Recording;

/**
 * A class which packages up Persistency's LevelHandler and GameCaretaker for convinence.
 * @author Sam
 *
 */
public class Persister {
	
	private LevelHandler handler;
	
	private GameCaretaker caretaker;
	
	public Persister(LevelHandler lh, GameCaretaker gc) {
		this.handler = lh;
		this.caretaker = gc;
	}

	/**
	 * Save a game with persistency.
	 * @param saveFile
	 * @param level 
	 * @param timeLeft 
	 * @throws PersistException
	 */
	public void saveCurrentGame(File saveFile, int level, int timeLeft) throws PersistException {
		caretaker.saveGame(saveFile, level, timeLeft);
	}

	/**
	 * Load a game with persistency.
	 * @param f
	 * @return an int array containing the level at index 0, and time at index 1.
	 * @throws PersistException
	 */
	public int[] loadGame(File f) throws PersistException {
		int[] levelAndTime = caretaker.loadGame(f);
		return levelAndTime;
	}

	public static void loadLevel(int i, Domain world) throws PersistException {
		LevelHandler.loadLevel(i, world);
		
	}
	
}
