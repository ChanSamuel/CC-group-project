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

    public static Recording load(InputStream is) throws RecorderException {
        XMLPersister p = null;
		try {
			p = new XMLPersister(new XmlMapper());
		} catch (PersistException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        try{
            return p.load(is, Recording.class);
        } catch (PersistException e){
            throw new RecorderException(e.getMessage());
        }
    }
}
