package nz.ac.vuw.ecs.swen225.gp21.recorder;

/**
 * The primary class for the recording package.
 * Represents a 'recording' of a game, containing all the states in the game, 
 * plus methods for navigating through the states manually, or auto-replaying a game.
 * 
 * @author Peter Liley 2021
 */
public class Recorder {
    GameState[] gameStates;
    int statePointer;
    private boolean autoReplayRunning = false;
    private float autoReplaySpeed;

    public Recorder(){
        this.statePointer = 0;
        this.autoReplaySpeed = 1.0f;
    }

    /**
     * Saves the given list of game states to an xml file
     * 
     * return: True if save successful
     */
    public boolean save(GameState[] statesToSave){

    }

    /**
     * Parses an xml file into a list of game states
     * @return A list of all game states in loaded recording
     */
    public GameState[] load(File xmlToLoad){

    }

    /**
     * Returns the next game state
     * TODO: this could involve an algorithm that sifts through game states to find 'interesting'
     *       ones where things actually change
     * @return The next meaningful game state
     */
    public GameState playOneStep(){

    }

    /**
     * Updates state pointer and returns game state one ahead in the list
     * @return Next game state index in the list (or current state if no prev state)
     */
    private GameState next() {
        if(statePointer < gameStates.length) statePointer++;
        return gameStates[statePointer];
    }

    /**
     * Updates state pointer and returns game state one before in the list
     * @return Previous game state index in the list (or current state if no prev state)
     */
    private GameState prev() {
        if(statePointer > 0) statePointer--;
        return gameStates[statePointer];
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
