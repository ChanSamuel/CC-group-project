package nz.ac.vuw.ecs.swen225.gp21.recorder;

import java.io.File;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;
import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;

/**
 * Represents a list of commands that constitute a game.
 * These can be saved to or loaded from a file.
 */
public class Recorder {
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
    }

    /**
     * Adds one update to the list of stored updates.
     * @param update The update to add to the recording.
     * @throws RecorderException
     */
    void add(GameUpdate update) throws RecorderException {
        if(loadedGame) throw new RecorderException("Cannot add ticks to a pre-recorded game. Use clear() to remove a loaded game.");
        if(updateValid(update)) updates.add(update);
        else throw new RecorderException("null tick added");
    }

    /**
     * Get the next command/s in a loaded recording.
     * Returned as a list to allow for 'simultaneous' actions 
     * (e.g. player pushes a box, so player and box both move)
     * @return next command, or no command if user has reached the end of the recording
     * @throws RecorderException
     */
    public List<GameUpdate> next() throws RecorderException{
        if(!loadedGame) throw new RecorderException("Recording not loaded. Load a game to navigate the recording.");
        List<GameUpdate> l = new LinkedList<>();
        if(pointer < updates.size()-1) pointer++;
        else return l; // return empty list if reached last command

        // make a list of all commands that happened within one update
        while(pointer < updates.size()-1){
            l.add(updates.get(pointer));
            if(updates.get(pointer).getUpdateIndex() == updates.get(pointer+1).getUpdateIndex()){
                pointer++;
            }
            else break;
        }

        return l;
    }

    /**
     * Get the previous command/s in a loaded recording.
     * Returned as a list to allow for 'simultaneous' actions 
     * (e.g. player pushes a box, so player and box both move)
     * @return prev command, or no command if user has reached the start of the recording
     * @throws RecorderException
     */
    public List<GameUpdate> prev() throws RecorderException{
        if(!loadedGame) throw new RecorderException("Recording not loaded. Load a game to navigate the recording.");
        List<GameUpdate> l = new LinkedList<>();
        if(pointer > 0) pointer--;
        else return l; // return empty list if reached first command

        // make a list of all commands that happened within one update
        while(pointer > 0){
            l.add(updates.get(pointer));
            if(updates.get(pointer).getUpdateIndex() == updates.get(pointer-1).getUpdateIndex()){
                pointer--;
            }
            else break;
        }
        return l;
    }


    /**
     * Saves a recording to disk using the Persistency's saving process.
     * @param saveFile The file to save the recording to.
     * @throws RecorderException
     */
    public void save(File saveFile) throws RecorderException{
        Recording r = new Recording(updates, level);
        try{
            SaveRecording.save(saveFile, r);
        } catch (PersistException e) {
            throw new RecorderException(e.getMessage());
        }
    }

    /**
     * Sets the current Recorder's fields to represent a recorded game.
     * Once a game is loaded, the next() and prev() methods can be used to navigate the game
     * 
     * @throws RecorderException
     */
    public void load(InputStream is) throws RecorderException{
        Recording r = LoadRecording.load(is);
        updates = r.getUpdates();
        level = r.getLevel();
        pointer = 0;
        loadedGame = true;
    }

    /**
     * Clears the command list and resets the pointer to index 0
     */
    public void clear(){
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
    }

    // ================ PRIVATE METHODS

    /**
     * Returns true if the tick is valid.
     * @param update The update to be queried.
     * @return True if tick is valid.
     */
    private boolean updateValid(GameUpdate update) {
        if(update == null) return false;
        return true;
    }

}