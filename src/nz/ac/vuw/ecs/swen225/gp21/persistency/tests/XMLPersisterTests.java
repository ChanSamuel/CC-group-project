package nz.ac.vuw.ecs.swen225.gp21.persistency.tests;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;

import nz.ac.vuw.ecs.swen225.gp21.persistency.XMLPersister;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import junit.framework.TestCase;

import org.mockito.ArgumentCaptor;
import org.mockito.ArgumentMatchers;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * These tests test the XmlPersister class.
 *
 * @author Lucy Goodwin
 */
@RunWith(MockitoJUnitRunner.class)
public class XMLPersisterTests extends TestCase {

    @Mock
    private XmlMapper xmlMapper;

    @Mock
    private File file;

    @Mock
    private Object object;

    @Mock
    private FileInputStream fileInputStream;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @Test
    public void testLoadOkay() throws IOException, PersistException {
        when(xmlMapper.readValue(any(FileInputStream.class), any(Class.class))).thenReturn("Returned String");
        XMLPersister parser = new XMLPersister(xmlMapper);
        String returned = parser.load(fileInputStream, String.class);
        assertEquals("Returned String", returned);
    }

    @Test
    public void testSaveOkay() throws IOException, PersistException {
        doNothing().when(xmlMapper).writeValue(any(File.class), stringArgumentCaptor.capture());
        XMLPersister parser = new XMLPersister(xmlMapper);
        parser.save(file, "Lorem ipsum");
        String stringCall = stringArgumentCaptor.getValue();
        assertEquals("Lorem ipsum", stringCall);
    }

    @Test
    public void testLoadWithJsonMappingException() throws IOException {
        doThrow(JsonMappingException.class).when(xmlMapper).readValue(any(FileInputStream.class), any(Class.class));
        XMLPersister parser = new XMLPersister(xmlMapper);
        PersistException exception = assertThrows(PersistException.class, ()->{parser.load(fileInputStream, String.class);});
        assertEquals("Mapping problem while loading xml file", exception.getMessage());
    }

    @Test
    public void testLoadWithJsonParseException() throws IOException {
        doThrow(JsonParseException.class).when(xmlMapper).readValue(any(FileInputStream.class), any(Class.class));
        XMLPersister parser = new XMLPersister(xmlMapper);
        PersistException exception = assertThrows(PersistException.class, ()->{parser.load(fileInputStream, String.class);});
        assertEquals("Parsing problem while loading xml file", exception.getMessage());
    }

    @Test
    public void testLoadWithIOException() throws IOException {
        doThrow(IOException.class).when(xmlMapper).readValue(any(FileInputStream.class), any(Class.class));
        XMLPersister parser = new XMLPersister(xmlMapper);
        PersistException exception = assertThrows(PersistException.class, ()->{parser.load(fileInputStream, String.class);});
        assertEquals("File reading problem while loading xml file", exception.getMessage());
    }

    @Test
    public void testSaveWithJsonMappingException() throws IOException {
        doThrow(JsonMappingException.class).when(xmlMapper).writeValue(any(File.class), any());
        XMLPersister parser = new XMLPersister(xmlMapper);
        PersistException exception = assertThrows(PersistException.class, ()->{parser.save(file, object);});
        assertEquals("Mapping problem while saving xml file", exception.getMessage());
    }

    @Test
    public void testSaveWithJsonGenerationException() throws IOException {
        doThrow(JsonGenerationException.class).when(xmlMapper).writeValue(any(File.class), any());
        XMLPersister parser = new XMLPersister(xmlMapper);
        PersistException exception = assertThrows(PersistException.class, ()->{parser.save(file, object);});
        assertEquals("Problem writing xml to file", exception.getMessage());
    }

    @Test
    public void testSaveWithIOException() throws IOException {
        doThrow(IOException.class).when(xmlMapper).writeValue(any(File.class), any());
        XMLPersister parser = new XMLPersister(xmlMapper);
        PersistException exception = assertThrows(PersistException.class, ()->{parser.save(file, object);});
        assertEquals("File writing problem while saving xml file", exception.getMessage());
    }
}
