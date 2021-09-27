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
public class ConcretePersister {
	
	private LevelHandler handler;
	
	private GameCaretaker caretaker;
	
	public ConcretePersister(LevelHandler lh, GameCaretaker gc) {
		this.handler = lh;
		this.caretaker = gc;
	}

	/**
	 * Save a game with persistency.
	 * @param saveFile
	 * @throws PersistException
	 */
	public void saveCurrentGame(File saveFile) throws PersistException {
		caretaker.saveGame(saveFile);
	}

	/**
	 * Load a game with persistency.
	 * @param f
	 * @throws PersistException
	 */
	public void loadGame(File f) throws PersistException {
		caretaker.loadGame(f);
	}

	public static void loadLevel(int i, Domain world) throws PersistException {
		LevelHandler.loadLevel(i, world);
		
	}
	
	/**
	 * DUMMY METHOD FOR TESTING.
	 * @param fileToLoad
	 * @return null
	 * @throws PersistException
	 */
	public Recording getRecording(File fileToLoad) throws PersistException{
		return null;
	}

	/**
	 * DUMMY METHOD FOR TESTING.
	 * @param file
	 * @param recording
	 */
	public void saveRecording(File file, Recording recording) {
		
	}
	
	
}
