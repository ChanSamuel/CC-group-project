package nz.ac.vuw.ecs.swen225.gp21.recorder;

import java.io.File;
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

    public Recorder(){
        this.pointer = 0;
        updates = new LinkedList<GameUpdate>();
    }

    /**
     * Adds one update to the list of stored updates
     */
    public void add(GameUpdate update) throws RecorderException {
        if(updateValid(update)) updates.add(update);
        else throw new RecorderException("null tick added");
    }

    /**
     * Get the next command/s in a loaded recording.
     * Returned as a list to allow for 'simultaneous' actions 
     * (e.g. player pushes a box, so player and box both move)
     * @return next command, or no command if user has reached the end of the recording
     */
    public List<GameUpdate> next(){
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
     */
    public List<GameUpdate> prev(){
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
     * @throws RecorderException if save fails.
     */
    public void save(File saveFile) throws RecorderException{
        Recording r = new Recording(updates, level);
        try{
            //SaveRecording.save(saveFile, r);
            System.out.println(ticks); // temporary output for integration!
            if(false) throw new PersistException("no error"); // TODO: this is only here to keep the catch block
                                                              //  get rid of it!!!!
        } catch (PersistException e) {
            throw new RecorderException(e.getMessage());
        }
    }

    /**
     * Returns a loaded 
     * 
     * @return A list of all game states in loaded recording
     * @throws RecorderException
     */
    public void load(File loadFile) throws RecorderException{
        Recording r = LoadRecording.load(loadFile);
        updates = r.getUpdates();
        level = r.getLevel();
        pointer = 0;
    }

    /**
     * Sets the leve to which the current recorder object refers.
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
     * Returns true if the tick is valid
     */
    private boolean updateValid(GameUpdate update) {
        if(update == null) return false;
        return true;
    }

}