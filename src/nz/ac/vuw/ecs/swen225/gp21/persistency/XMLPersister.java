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
 * Use of this class requires an XmlMapper object. Any objects the XmlMapper persists must be
 * provide a default constructor and appropriate getters for fields that need to be persisted.
 * Otherwise fields need to be annotated with @JsonIgnore. If objects to be persisted are not
 * concrete types then they need to be annotated with @JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
 * and have their subtypes registered to the XmlMapper object. 
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
    public XMLPersister(XmlMapper xmlMapper) throws PersistException {
        if (xmlMapper==null) throw new PersistException("Cannot persist without a valid mapper");
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
        if (is==null) throw new PersistException("IO Error while loading xml");
        if (valueType==null) throw new PersistException("Error loading xml");

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
        if (file==null) throw new PersistException("IO Error while saving xml");
        if (value==null) throw new PersistException("Error saving xml");

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

