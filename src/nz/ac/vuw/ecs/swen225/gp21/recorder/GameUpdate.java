package nz.ac.vuw.ecs.swen225.gp21.recorder;

/**
 * Forces given objects to have the required data for Recorder
 */
public interface GameUpdate {
    /**
     * Returns a unique number that identifies on which game loop this command was executed.
     * @return index of game loop
     */
    public long getUpdateIndex();
}