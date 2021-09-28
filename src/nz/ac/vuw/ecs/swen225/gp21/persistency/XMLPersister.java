package nz.ac.vuw.ecs.swen225.gp21.persistency;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * This class provides functionality for mapping java objects to XML file format.
 *
 * @author Lucy Goodwin
 */
public class XMLPersister {

    /**
     * The XmlMapper that will be used to persist.
     */
    private XmlMapper xmlMapper;

    /**
     * Constructor for an XmlPersister object.
     *
     * @param xmlMapper to persist with
     */
    public XMLPersister(XmlMapper xmlMapper) {
        this.xmlMapper = xmlMapper;
    }

    /**
     * Method for loading from XML.
     *
     * @param is input stream
     * @return value loaded
     * @throws PersistException that will provide an informative message that should be shown to the user
     */
    public <T> T load(InputStream is, Class<T> valueType) throws PersistException {
        try {
            return xmlMapper.readValue(is, valueType);
        } catch (JsonMappingException e) {
            throw new PersistException("Mapping problem while loading xml file");
        } catch (JsonParseException e) {
            throw new PersistException("Parsing problem while loading xml file");
        } catch (IOException e) {
            throw new PersistException("File reading problem while loading xml file");
        }
    }

    /**
     * Method for saving to XML.
     *
     * @param file to save value to
     * @param value to save to file
     * @throws PersistException that will provide an informative message that should be shown to the user
     */
    public <T> void save(File file, T value) throws PersistException {
        try {
            xmlMapper.writeValue(file, value);
        } catch (JsonMappingException e) {
            throw new PersistException("Mapping problem while saving xml file");
        } catch (JsonGenerationException e) {
            throw new PersistException("Problem writing xml to file");
        } catch (IOException e) {
            throw new PersistException("File writing problem while saving xml file");
        }
    }
}

