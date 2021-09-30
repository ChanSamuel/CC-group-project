package nz.ac.vuw.ecs.swen225.gp21.recorder;

import java.io.File;
import java.io.IOException;
import java.util.List;

import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;
import nz.ac.vuw.ecs.swen225.gp21.persistency.XMLPersister;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

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
     * @throws RecorderException
     */
    public static void save(File file, Recording recording) throws PersistException {
        if(recording.getLevel() < 1){
            throw new RecorderException("Attempting to save recording with no level");
        }
        try{
            XMLPersister p = new XMLPersister(new XmlMapper());
            p.save(file, recording);
        }
        catch(PersistException e){
            throw new RecorderException(e.getMessage());
        }
    }
    
}
