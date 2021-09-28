package nz.ac.vuw.ecs.swen225.gp21.recorder;

import java.io.File;
import java.io.IOException;
import java.util.List;

import nz.ac.vuw.ecs.swen225.gp21.app.Persister;
import nz.ac.vuw.ecs.swen225.gp21.persistency.*;

/**
 * This class saves a list of game states to an xml file in the form of a Recording object.
 * Uses the Persistency package's xml encoder to encode.
 * 
 */
public class SaveRecording {

    /**
     * Transfers a Recording object to the Persistency package for encoding and saving.
     * @param file
     * @param recording
     * @throws PersistException
     */
    public static void save(File file, Recording recording) throws PersistException {
        if(recording.getLevel() < 1){
            throw new PersistException("Attempting to save recording with no level");
        }
        try{
            Persister cp = new Persister(null, null);
            cp.saveRecording(file, recording);
        }
        catch(Exception e){
            throw new PersistException(e.getMessage());
        }
    }
    
}
