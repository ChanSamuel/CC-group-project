package nz.ac.vuw.ecs.swen225.gp21.persistency;

import java.io.File;
import nz.ac.vuw.ecs.swen225.gp21.domain.*;
import nz.ac.vuw.ecs.swen225.gp21.recorder.Recording;


public interface Persister {

    /**
     * Loads level (determined by which level number) into a given domain.
     *
     * @param levelNumber must be a level that exists
     * @throws PersistException that will provide an informative message that should be shown to the user
     */
    public void loadLevel(int levelNumber, Domain domain) throws PersistException;


    /**
     * Loads a previously saved Domain (in an XML file) into a given Domain object.
     *
     * @param fileToLoad needs to be a .xml file
     * @param domain the domain to load the game state into
     * @throws PersistException that will provide an informative message that should be shown to the user
     */
    public void loadGame(File fileToLoad, Domain domain) throws PersistException;

    /**
     *  This method writes an XML file representing a Domain object that is holding the current game state.
     *
     * @param fileToSave should be a .xml file
     * @param domain the domain that holds the game state to save
     * @throws PersistException
     */
    public void saveCurrentGame(File fileToSave, Domain domain) throws PersistException;

    /**
     * TODO
     * @param recordingFile
     * @return
     * @throws PersistException
     */
    public Recording getRecording(File recordingFile) throws PersistException;

    /**
     * TODO
     * @param recordingFile
     * @param recording
     * @throws PersistException
     */
    public void saveRecording(File recordingFile, Recording recording) throws PersistException;

}
