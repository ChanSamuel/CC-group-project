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
    List<GameTick> ticks;
    int tickPointer;
    private boolean autoReplayRunning = false;
    private float autoReplaySpeed;

    public Recorder(){
        this.tickPointer = 0;
        this.autoReplaySpeed = 1.0f;
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
    public List<GameTick> load(File xmlToLoad){
        return LoadRecording.load(xmlToLoad);
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
     * Returns true if the tick is valid
     */
    private boolean tickValid(GameTick tick) {
        if(tick == null) return false;
        return true;
    }

    /**
     * Returns the next game state in which an actor moves
     * @return The next meaningful game state
     */
    public GameTick playOneStep(){
        while(!ticks.get(tickPointer).tickIsMeaningful()){
            tickPointer++;
        }
        return ticks.get(tickPointer);
    }

    /**
     * Returns the next tick in the list of ticks by incrementing the pointer.
     * @return Next game state index in the list (or current state if no prev state)
     */
    private GameTick next() {
        if(tickPointer < ticks.size()) tickPointer++;
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
     * Sets whether or not auto-replay is runnning.
     */
    public void setAutoReplayRunning(boolean running){
         autoReplayRunning = running;
    } 

    /**
     * Sets auto replay speed 
     * (1 = normal speed, 2 = double speed, 0.5 = 50% speed, etc)
     * @param speed Replay speed, with 1 being 'normal' (i.e. 'input') speed.
     */
    public void setAutoReplaySpeed(float speed){
        autoReplaySpeed = speed;
    }

}
