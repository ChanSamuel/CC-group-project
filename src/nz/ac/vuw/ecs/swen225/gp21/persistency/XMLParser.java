package nz.ac.vuw.ecs.swen225.gp21.persistency;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.KeyDeserializer;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import nz.ac.vuw.ecs.swen225.gp21.domain.*;

public class XMLParser {

    private XmlMapper xmlMapper;

    public XMLParser(XmlMapper xmlMapper) {
        this.xmlMapper = xmlMapper;
        this.xmlMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY); //fixme
        this.xmlMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS); //fixme
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
            e.printStackTrace();
            throw new PersistException(""); // todo handle this
        } catch (JsonParseException e) {
            e.printStackTrace();
            throw new PersistException(""); // todo handle this
        } catch (IOException e) {
            e.printStackTrace();
            throw new PersistException(""); // todo handle this
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
            e.printStackTrace();
            throw new PersistException(""); // todo handle this
        } catch (JsonGenerationException e) {
            e.printStackTrace();
            throw new PersistException(""); // todo handle this
        } catch (IOException e) {
            e.printStackTrace();
            throw new PersistException(""); // todo handle this
        }
    }
}

