package nz.ac.vuw.ecs.swen225.gp21.recorder;

import java.io.File;
import java.util.List;

import nz.ac.vuw.ecs.swen225.gp21.app.ConcretePersister;
import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;

/**
 * Generates a list of game ticks in the form of a Recording object from an xml file.
 * Uses the Persistency package's persister to load.
 */
public class LoadRecording {

    public static Recording load(File fileToLoad) throws RecorderException {
        ConcretePersister p = new ConcretePersister(null, null);
        try{
            return p.getRecording(fileToLoad);
        } catch (PersistException e){
            throw new RecorderException(e.getMessage());
        }
    }
    
}
