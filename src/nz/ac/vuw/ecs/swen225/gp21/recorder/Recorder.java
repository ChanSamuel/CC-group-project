package nz.ac.vuw.ecs.swen225.gp21.recorder;

import java.io.File;
import java.util.LinkedList;
import java.util.List;

// GOTTA GET RID OF THIS
import nz.ac.vuw.ecs.swen225.gp21.domain.Tick;
import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;

/**
 * The primary class for the recording package.
 * Represents a 'recording' of a game, containing all the states in the game, 
 * plus methods for navigating through the states manually, or auto-replaying a game.
 * 
 * @author Peter Liley 2021
 */
public class Recorder {
    private int level;
    private List<Tick> ticks;
    private int tickPointer;
    private boolean autoReplayRunning = false;

    public Recorder(){
        this.tickPointer = 0;
        ticks = new LinkedList<Tick>();
    }

    /**
     * Returns whether or not the current mode is automatic or manual replay
     *
     * @return true if auto-replay is running
     */
    public boolean getAutoReplayRunning(){
        return autoReplayRunning;
    }

    /**
    * Sets whether or not auto-replay is runnning.
    */
    public void setAutoReplayRunning(boolean running){
        autoReplayRunning = running;
    } 

    /**
     * Saves the given list of game states to an xml file, after setting the last tick's
     * 'finalTick' field to true.
     * 
     * @return True if save successful
     */
    public void save(File saveFile) throws RecorderException{
        ticks.get(ticks.size()-1).isFinalTick = true; // set final tick
        Recording r = new Recording(ticks, level);
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
     * @throws RecorderException
     */
    public void load(File loadFile) throws RecorderException{
        Recording r = LoadRecording.load(loadFile);
        ticks = r.getTicks();
        level = r.getLevel();
        tickPointer = 0;
    }

    /**
     * Add a single tick to the list.
     *
     * @throws RecorderException if tick is not valid (e.g. null ticks)
     */
    public void addTick(Tick tick) throws RecorderException{
        if(tickValid(tick)) ticks.add(tick);
        else throw new RecorderException("null tick added");
    }

    /**
     * Returns the next tick in the tick list if playback mode is auto.
     * Returns the next meaningful tick in the list if playback mode is manual.
     *  - a 'meaningful' tick is one in which any actor moves
     * 
     * @return next relevant Tick object (depending on mode)
     */
    public Tick nextTick(){
        if(autoReplayRunning) return next();
        else return nextMeaningful();
    }

    /**
     * Returns the previous tick in the tick list if playback mode is auto.
     * Returns the previous meaningful tick in the list if playback mode is manual.
     *  - a 'meaningful' tick is one in which any actor moves
     * 
     * @return previous relevant Tick object (depending on mode)
     */
    public Tick prevTick(){
        if(autoReplayRunning) return prev();
        else return prevMeaningful();
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

    // PRIVATE METHODS ===============

    /**
     * Returns the next tick in the list of ticks by incrementing the pointer.
     * 
     * @return Next game state index in the list (or current tick if no next tick)
     */
    private Tick next() {
        if(tickPointer < ticks.size()-1) tickPointer++;
        return ticks.get(tickPointer);
    }

    /**
     * Updates state pointer and returns game state one before in the list
     * 
     * @return Previous game state index in the list (or current state if no prev state)
     */
    private Tick prev() {
        if(tickPointer > 0) tickPointer--;
        return ticks.get(tickPointer);
    }

    /**
     * Returns the next 'meaningful' tick, or current tick if no next tick.
     *  - a 'meaningful' tick is one in which any actor moves
     * 
     * @return next tick that contains any movement
     */
    private Tick nextMeaningful() {
        while(tickPointer < ticks.size()-1){
            tickPointer++;
            if(ticks.get(tickPointer).didAnyObjectMove()) return ticks.get(tickPointer);
        }
        return null;
    }

    /**
     * Returns the next 'meaningful' tick, or current tick if no next tick
     *  - a 'meaningful' tick is one in which any actor moves
     * @return
     */
    private Tick prevMeaningful() {
        while(tickPointer > 0){
            tickPointer--;
            if(ticks.get(tickPointer).didAnyObjectMove()) return ticks.get(tickPointer);
        }
        return null;
    }

    /**
     * Returns true if the tick is valid
     */
    private boolean tickValid(Tick tick) {
        if(tick == null) return false;
        return true;
    }

}
