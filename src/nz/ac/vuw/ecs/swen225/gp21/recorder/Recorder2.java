package nz.ac.vuw.ecs.swen225.gp21.recorder;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;

public class Recorder2 {
    private int level;
    private List<GameUpdate> updates;
    private int pointer;

    public Recorder2(){
        this.pointer = 0;
        updates = new LinkedList<GameUpdate>();
    }

    public void add(GameUpdate update) throws RecorderException {
        if(updateValid(update)) updates.add(update);
        else throw new RecorderException("null tick added");
    }

    public List<GameUpdate> next(){
        List<GameUpdate> l = new LinkedList<>();
        if(pointer < updates.size()-1) pointer++;
        else return l; // return empty list if reached last command

        // make a list of all commands that happened within one update
        while(pointer < updates.size()-1){
            l.add(updates.get(pointer));
            if(updates.get(pointer).getUpdateCount() == updates.get(pointer+1).getUpdateCount()){
                pointer++;
            }
            else break;
        }

        return l;
    }

    public List<GameUpdate> prev(){
        List<GameUpdate> l = new LinkedList<>();
        if(pointer > 0) pointer--;
        else return l; // return empty list if reached first command

        // make a list of all commands that happened within one update
        while(pointer > 0){
            l.add(updates.get(pointer));
            if(updates.get(pointer).getUpdateCount() == updates.get(pointer-1).getUpdateCount()){
                pointer--;
            }
            else break;
        }
        return l;
    }


    /**
     * Saves the given list of game states to an xml file, after setting the last tick's
     * 'finalTick' field to true.
     * 
     * @return True if save successful
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
     * Parses an xml file into a list of game states.
     * 
     * @return A list of all game states in loaded recording
     */
    public void load(File loadFile){
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