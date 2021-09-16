package nz.ac.vuw.ecs.swen225.gp21.recorder;

import java.io.File;
import java.io.IOException;
import java.util.List;
import nz.ac.vuw.ecs.swen225.gp21.persistency.*;

/**
 * This class saves a list of game states to an xml file.
 * Uses the Persistency package's xml encoder to encode individual game states
 * and links them together into a list in the output file.
 * 
 */
public class SaveRecording {

    public static void save(File file, Recording recording) throws PersistException {
        if(recording.getLevel() < 1){
            throw new PersistException("Attempting to save recording with no level");
        }
        try{
            // TODO: check with Lucy about the saving process:
                // method name
                // exceptions
                // return boolean / exception?
            //persistecy.saveFile(recording);
        }
        catch(Exception e){
            throw new PersistException("Failed to save recording");
        }
    }
    
}
