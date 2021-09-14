package nz.ac.vuw.ecs.swen225.gp21.persistency;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import nz.ac.vuw.ecs.swen225.gp21.domain.*;

public class XMLParser<T> {

    private XmlMapper xmlMapper;
    private Class<T> valueType;

    public XMLParser(XmlMapper xmlMapper, Class<T> valueType) {
        this.xmlMapper = xmlMapper;
        this.xmlMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        //this.xmlMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS); fixme
        this.valueType = valueType;
    }

    /**
     * Method for loading from XML
     * @param is input stream
     * @return
     * @throws PersistException
     */
    public T load(InputStream is) throws PersistException {
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
    public void save(File file, T value) throws PersistException {
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
