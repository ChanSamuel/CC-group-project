package nz.ac.vuw.ecs.swen225.gp21.recorder;

import java.io.File;
import java.io.InputStream;
import java.util.List;

import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;
import nz.ac.vuw.ecs.swen225.gp21.persistency.XMLPersister;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 * Generates a list of game ticks in the form of a Recording object from an xml file.
 * Uses the Persistency package's persister to load.
 */
public class LoadRecording {

     /**
      * Loads a recording from a file using Persistency's xmlPersister.
      * @param is input stream of saved file
      * @param mapper the given XmlMapper for Persister to use
      * @return Relevant recording
      * @throws RecorderException
      */
    public static Recording load(InputStream is, XmlMapper mapper) throws RecorderException {
        try{
            XMLPersister p = new XMLPersister(mapper);
            return p.load(is, Recording.class);
        } catch (PersistException e){
            throw new RecorderException(e.getMessage());
        }
    }
}
