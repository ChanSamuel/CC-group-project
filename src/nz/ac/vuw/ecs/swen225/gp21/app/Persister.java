package nz.ac.vuw.ecs.swen225.gp21.app;

import java.io.File;
import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.persistency.GameCaretaker;
import nz.ac.vuw.ecs.swen225.gp21.persistency.LevelHandler;
import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;

/**
 * A class which packages up Persistency's LevelHandler and GameCaretaker for
 * convinence.

 * @author chansamu1 300545169
 *
 */
public class Persister {

  /**
   * The GameCaretaker from persistency.
   */
  private GameCaretaker caretaker;

  /**
   * Construct a Persister.
   *
   * @param gc : the GameCaretaker
   */
  public Persister(GameCaretaker gc) {
    this.caretaker = gc;
  }

  /**
   * Save a game with persistency.

   * @param saveFile : the file to save to.
   * @param level    : the level this save is of.
   * @param timeLeft : the time left in the level.
   * @throws PersistException : the Exception thrown on failure.
   */
  public void saveCurrentGame(File saveFile, int level, int timeLeft) throws PersistException {
    caretaker.saveGame(saveFile, level, timeLeft);
  }

  /**
   * Load a game with persistency.

   * @param f : the file to load
   * @return an int array containing the level at index 0, and time at index 1.
   * @throws PersistException : the Exception thrown on failure.
   */
  public int[] loadGame(File f) throws PersistException {
    int[] levelAndTime = caretaker.loadGame(f);
    return levelAndTime;
  }

  /**
   * Load a level using persistency.

   * @param i     : the level number (1 or 2).
   * @param world : the world object.
   * @throws PersistException : the Exception thrown on failure.
   */
  public static void loadLevel(int i, Domain world) throws PersistException {
    LevelHandler.loadLevel(i, world);

  }

}
