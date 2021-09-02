package nz.ac.vuw.ecs.swen225.gp21.recorder;

import java.io.File;
import java.util.List;

/**
 * The primary class for the recording package.
 * Represents a 'recording' of a game, containing all the states in the game, 
 * plus methods for navigating through the states manually, or auto-replaying a game.
 * 
 * @author Peter Liley 2021
 */
public class Recorder {
    private List<GameTick> ticks;
    private int tickPointer;
    private boolean autoReplayRunning = false;

    public Recorder(){
        this.tickPointer = 0;
    }

    /**
     * Returns whether or not the current mode is automatic or manual replay
     * @return
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
     * Saves the given list of game states to an xml file
     * 
     * return: True if save successful
     */
    public boolean save(){
        return SaveRecording.save(ticks);
    }

    /**
     * Parses an xml file into a list of game states
     * @return A list of all game states in loaded recording
     */
    public void load(File xmlToLoad){
        ticks = LoadRecording.load(xmlToLoad);
    }

    /**
     * Add a single tick to the list
     * @throws IllegalArgumenException if tick is not valid (e.g. null ticks)
     */
    public void addTick(GameTick tick){
        if(tickValid(tick)) ticks.add(tick);
        else throw new IllegalArgumentException();
    }

    /**
     * Returns the next tick in the tick list if playback mode is auto
     * Returns the next *meaningful* tick in the list if playback mode is manual
     *  - a 'meaningful' tick is one in which any actor moves
     * @return next (meaningful*) GameTick object
     */
    public GameTick nextTick(){
        if(autoReplayRunning) return next();
        else return nextMeaningful();
    }


    public GameTick prevTick(){
        if(autoReplayRunning) return prev();
        else return prevMeaningful();
    }

    // PRIVATE METHODS ===============

    /**
     * Returns the next tick in the list of ticks by incrementing the pointer.
     * @return Next game state index in the list (or current tick if no next tick)
     */
    private GameTick next() {
        if(tickPointer < ticks.size()-1) tickPointer++;
        return ticks.get(tickPointer);
    }

    /**
     * Updates state pointer and returns game state one before in the list
     * @return Previous game state index in the list (or current state if no prev state)
     */
    private GameTick prev() {
        if(tickPointer > 0) tickPointer--;
        return ticks.get(tickPointer);
    }

    /**
     * Returns the next 'meaningful' tick, or current tick if no next tick
     *  - - a 'meaningful' tick is one in which any actor moves
     * @return
     */
    private GameTick nextMeaningful() {
        while(tickPointer < ticks.size()-1){
            tickPointer++;
            if(ticks.get(tickPointer).isMeaningful()) return ticks.get(tickPointer);
        }
        return null;
    }

    /**
     * Returns the next 'meaningful' tick, or current tick if no next tick
     *  - - a 'meaningful' tick is one in which any actor moves
     * @return
     */
    private GameTick prevMeaningful() {
        while(tickPointer > 0){
            tickPointer--;
            if(ticks.get(tickPointer).isMeaningful()) return ticks.get(tickPointer);
        }
        return null;
    }

        /**
     * Returns true if the tick is valid
     */
    private boolean tickValid(GameTick tick) {
        if(tick == null) return false;
        return true;
    }

}
