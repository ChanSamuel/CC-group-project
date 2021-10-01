package nz.ac.vuw.ecs.swen225.gp21.recorder;

import java.io.File;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * Represents a list of commands that constitute a game. These can be saved to
 * or loaded from a file.
 */
public class Recorder {
<<<<<<< Updated upstream
=======
<<<<<<< Updated upstream
    private int level;
    private List<GameUpdate> updates;
    private int pointer;
    private boolean loadedGame = false;

    /**
     * Constructs a new Recorder object.
     */
    public Recorder(){
        this.pointer = 0;
        updates = new LinkedList<GameUpdate>();
=======
>>>>>>> Stashed changes
  private int level;
  private List<GameUpdate> updates;
  private int pointer;
  private boolean loadedGame = false;
<<<<<<< Updated upstream
=======
  static Recording memRecord;
>>>>>>> Stashed changes

  /**
   * Constructs a new Recorder object.
   */
  public Recorder() {
    this.pointer = 0;
    updates = new LinkedList<GameUpdate>();
  }

  /**
   * Adds one update to the list of stored updates.
   *
   * @param update The update to add to the recording.
   * @throws RecorderException
   */
  public void add(GameUpdate update) throws RecorderException {
    if (loadedGame) {
      throw new RecorderException(
          "Cannot add ticks to a pre-recorded game. Use clear() to remove a loaded game.");
<<<<<<< Updated upstream
=======
    }
    if (updateValid(update)) {
      updates.add(update);
    } else {
      throw new RecorderException("null tick added");
    }
  }

  /**
   * Get the next command/s in a loaded recording. Returned as a list to allow for
   * 'simultaneous' actions (e.g. player pushes a box, so player and box both
   * move)
   *
   * @return next command, or no command if user has reached the end of the
   *         recording
   * @throws RecorderException
   */
  public List<GameUpdate> next() throws RecorderException {
    if (!loadedGame) {
      throw new RecorderException("Recording not loaded. Load a game to navigate the recording.");
    }
    List<GameUpdate> l = new LinkedList<>();
    // check if this is the last update / are there any updates?
    if (pointer == updates.size() || updates.isEmpty()) {
      System.out.println("No more ticks to replay." + pointer + "updates size:" + updates.size());
      return l;
>>>>>>> Stashed changes
>>>>>>> Stashed changes
    }
    if (updateValid(update)) {
      updates.add(update);
    } else {
      throw new RecorderException("null tick added");
    }
  }

  /**
   * Get the next command/s in a loaded recording. Returned as a list to allow for
   * 'simultaneous' actions (e.g. player pushes a box, so player and box both
   * move)
   *
   * @return next command, or no command if user has reached the end of the
   *         recording
   * @throws RecorderException
   */
  public List<GameUpdate> next() throws RecorderException {
    if (!loadedGame) {
      throw new RecorderException("Recording not loaded. Load a game to navigate the recording.");
    }
    List<GameUpdate> l = new LinkedList<>();
    // check if this is the last update / are there any updates?
    if (pointer == updates.size() || updates.isEmpty()) {
      System.out.println("No more ticks to replay." + pointer + "updates size:" + updates.size());
      return l;
    }
    if (pointer < 0) {
      pointer = 0;
    }
    // make a list of all commands that happened within one update
    while (pointer < updates.size()) {
      l.add(updates.get(pointer++));
      if (pointer + 1 >= updates.size()) {
        break; // avoid out of bounds error
      }
      if (updates.get(pointer - 1).getUpdateIndex() != updates.get(pointer).getUpdateIndex()) {
        // if this event is a part of a different update than we the one we just read,
        // then exit.
        break;
      }
    }
    System.out.println("pointer:" + pointer);
    return l;
  }

  /**
   * Get the previous command/s in a loaded recording. Returned as a list to allow
   * for 'simultaneous' actions (e.g. player pushes a box, so player and box both
   * move)
   *
   * @return prev command, or no command if user has reached the start of the
   *         recording
   * @throws RecorderException
   */
  public List<GameUpdate> prev() throws RecorderException {
    if (!loadedGame) {
      throw new RecorderException("Recording not loaded. Load a game to navigate the recording.");
    }
<<<<<<< Updated upstream
    List<GameUpdate> l = new LinkedList<>();
    // check if reached first command
    if (pointer < 0 || updates.isEmpty()) {
      System.out.println("Can't go back further");
      return l;
    }
    if (pointer >= updates.size()) {
      pointer = updates.size() - 1;
=======
<<<<<<< Updated upstream

    /**
     * Sets the level to which the current recorder object refers.
     * 
     * @param level
     * @return true if level input is valid (i.e. >1)
     */
    public boolean setLevel(int level){
        if(level < 1) return false;
        this.level = level;
        return true;
    }

    /**
     * Returns the level to which the recording object refers.
     * 
     * @return the level to which this recording object refers.
     */
    public int getLevel(){
        return level;
=======
    return l;
  }

  /**
   * Saves a recording to disk using the Persistency's saving process.
   *
   * @param saveFile The file to save the recording to.
   * @param mapper   the given XmlMapper for Persister to use
   * @throws RecorderException
   */
  public void save(File saveFile, XmlMapper mapper) throws RecorderException {
    Recording r = new Recording(new LinkedList<>(updates), level);
    /*
     * try { SaveRecording.save(saveFile, r, mapper); } catch (RecorderException e)
     * { throw new RecorderException(e.getMessage()); }
     */
    memRecord = r;
  }

  /**
   * Sets the current Recorder's fields to represent a recorded game. Once a game
   * is loaded, the next() and prev() methods can be used to navigate the game
   *
   * @param mapper the given XmlMapper for Persister to use
   * @param is     the given input stream for Persister to use
   * @throws RecorderException
   */
  public void load(InputStream is, XmlMapper mapper) throws RecorderException {
    // Recording r = LoadRecording.load(is, mapper);
    Recording r = memRecord;
    updates = r.getUpdates();
    level = r.getLevel();
    pointer = 0;
    loadedGame = true;
  }

  /**
   * Clears the command list and resets the pointer to index 0
   */
  public void clear() {
    updates.clear();
    pointer = 0;
    loadedGame = false;
  }

  /**
   * Sets the level to which the current recorder object refers.
   *
   * @param level
   * @return true if level input is valid (i.e. >1)
   */
  public boolean setLevel(int level) {
    if (level < 1) {
      return false;
>>>>>>> Stashed changes
>>>>>>> Stashed changes
    }
    // make a list of all commands that happened within one update
    while (pointer > -1) {
      l.add(updates.get(pointer--));
      if (pointer - 1 < -1) {
        break; // avoid out of bounds error
      }
      System.out.println("pointer:" + pointer + " updates size:" + updates.size() + " prev");
      if (updates.get(pointer + 1).getUpdateIndex() != updates.get(pointer).getUpdateIndex()) {
        // check that the next update is a part of this update. if it is not, then exit.
        break;
      }
    }
    return l;
  }

  /**
   * Saves a recording to disk using the Persistency's saving process.
   *
   * @param saveFile The file to save the recording to.
   * @param mapper   the given XmlMapper for Persister to use
   * @throws RecorderException
   */
  public void save(File saveFile, XmlMapper mapper) throws RecorderException {
    Recording r = new Recording(updates, level);
    try {
      SaveRecording.save(saveFile, r, mapper);
    } catch (RecorderException e) {
      throw new RecorderException(e.getMessage());
    }
  }

  /**
   * Sets the current Recorder's fields to represent a recorded game. Once a game
   * is loaded, the next() and prev() methods can be used to navigate the game
   *
   * @param mapper the given XmlMapper for Persister to use
   * @param is     the given input stream for Persister to use
   * @throws RecorderException
   */
  public void load(InputStream is, XmlMapper mapper) throws RecorderException {
    Recording r = LoadRecording.load(is, mapper);
    updates = r.getUpdates();
    level = r.getLevel();
    pointer = 0;
    loadedGame = true;
  }

  /**
   * Clears the command list and resets the pointer to index 0
   */
  public void clear() {
    updates.clear();
    pointer = 0;
    loadedGame = false;
  }

  /**
   * Sets the level to which the current recorder object refers.
   *
   * @param level
   * @return true if level input is valid (i.e. >1)
   */
  public boolean setLevel(int level) {
    if (level < 1) {
      return false;
    }
    this.level = level;
    return true;
  }

  /**
   * Returns the level to which the recording object refers.
   *
   * @return the level to which this recording object refers.
   */
  public int getLevel() {
    return level;
  }

  // ================ PRIVATE METHODS

  /**
   * Returns true if the tick is valid.
   *
   * @param update The update to be queried.
   * @return True if tick is valid.
   */
  private boolean updateValid(GameUpdate update) {
    if (update == null) {
      return false;
    }
    return true;
  }

}