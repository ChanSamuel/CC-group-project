package nz.ac.vuw.ecs.swen225.gp21.persistency;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

// TODO: 24/09/2021
/**
 *
 */
public class XMLPersister {

    // TODO: 24/09/2021
    /**
     *
     */
    private XmlMapper xmlMapper;

    // TODO: 24/09/2021
    /**
     *
     * @param xmlMapper
     */
    public XMLPersister(XmlMapper xmlMapper) {
        this.xmlMapper = xmlMapper;
    }

    /**
     * Method for loading from XML
     * @param is input stream
     * @return
     * @throws PersistException
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
     * Method for saving to XML
     * @param file
     * @param value
     * @throws PersistException
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

