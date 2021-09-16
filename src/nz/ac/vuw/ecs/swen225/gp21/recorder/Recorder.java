package nz.ac.vuw.ecs.swen225.gp21.recorder;

import java.io.File;
import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import nz.ac.vuw.ecs.swen225.gp21.domain.Tick;

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
    private Queue<Tick> queTick;
    private int tickPointer;
    private boolean autoReplayRunning = false;

    public Recorder(){
        this.tickPointer = 0;
        this.ticks = new LinkedList<Tick>();
        this.queTick = new ArrayDeque<Tick>();
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
    public boolean save(File saveFile){
        ticks.get(ticks.size()-1).isFinalTick = true; // set final tick
        Recording r = new Recording(ticks, level);
        return SaveRecording.save(saveFile, r);
    }

    /**
     * Parses an xml file into a list of game states.
     * 
     * @return A list of all game states in loaded recording
     */
    public void load(File loadFile){
        Recording r = LoadRecording.load(loadFile);
        ticks = r.getTicks();
        level = r.getLevel();
    }

    /**
     * Add a single tick to the list.
     *
     * @throws IllegalArgumenException if tick is not valid (e.g. null ticks)
     */
    public void addTick(Tick tick){
        if(tickValid(tick)) queTick.add(tick);
        else throw new IllegalArgumentException();
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
            //if(ticks.get(tickPointer).isAnyMove()) return ticks.get(tickPointer);
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
            //if(ticks.get(tickPointer).isAnyMove()) return ticks.get(tickPointer);
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
