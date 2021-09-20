package nz.ac.vuw.ecs.swen225.gp21.recorder;

import java.io.File;
import java.util.List;
import nz.ac.vuw.ecs.swen225.gp21.persistency.*;

/**
 * This class saves a list of game states to an xml file.
 * Uses the Persistency package's xml encoder to encode individual game states
 * and links them together into a list in the output file.
 * 
 */
public class SaveRecording {

    public static boolean save(File file, Recording recording) {
        try{
            // TODO: check with Lucy about the saving process:
                // method name
                // exceptions
                // return boolean / exception?
            saveFile(recording);
            return true;
        }
        catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
    
}
