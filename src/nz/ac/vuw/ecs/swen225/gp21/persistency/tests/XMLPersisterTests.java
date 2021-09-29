package nz.ac.vuw.ecs.swen225.gp21.persistency.tests;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;

import nz.ac.vuw.ecs.swen225.gp21.persistency.XMLPersister;
import org.junit.Test;
import org.junit.runner.RunWith;
import junit.framework.TestCase;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.FileInputStream;
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

    public XMLPersisterTests() throws PersistException {
    }

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
    public void testNullMapperThrowsException() {
        PersistException exception = assertThrows(PersistException.class, ()->{new XMLPersister(null);});
        assertEquals("Cannot persist without a valid mapper", exception.getMessage());
    }

    @Test
    public void testNullFileStreamThrowsException() throws PersistException {
        XMLPersister parser = new XMLPersister(xmlMapper);
        PersistException exception = assertThrows(PersistException.class, ()->{parser.load(null, String.class);});
        assertEquals("IO Error while loading xml", exception.getMessage());
    }

    @Test
    public void testSaveNullFileThrowsException() throws PersistException {
        XMLPersister parser = new XMLPersister(xmlMapper);
        PersistException exception = assertThrows(PersistException.class, ()->{parser.save(null, "Anything");});
        assertEquals("IO Error while saving xml", exception.getMessage());
    }

    @Test
    public void testLoadNullClassThrowsException() throws PersistException {
        XMLPersister parser = new XMLPersister(xmlMapper);
        PersistException exception = assertThrows(PersistException.class, ()->{parser.load(fileInputStream, null);});
        assertEquals("Error loading xml", exception.getMessage());
    }

    @Test
    public void testSaveNullValueThrowsException() throws PersistException {
        XMLPersister parser = new XMLPersister(xmlMapper);
        PersistException exception = assertThrows(PersistException.class, ()->{parser.save(file, null);});
        assertEquals("Error saving xml", exception.getMessage());
    }


    @Test
    public void testLoadJsonMappingExceptionThrowsPersistException() throws IOException, PersistException {
        doThrow(JsonMappingException.class).when(xmlMapper).readValue(any(FileInputStream.class), any(Class.class));
        XMLPersister parser = new XMLPersister(xmlMapper);
        PersistException exception = assertThrows(PersistException.class, ()->{parser.load(fileInputStream, String.class);});
        assertEquals("Mapping problem while loading xml file", exception.getMessage());
    }

    @Test
    public void testLoadJsonParseExceptionThrowsPersistException() throws IOException, PersistException {
        doThrow(JsonParseException.class).when(xmlMapper).readValue(any(FileInputStream.class), any(Class.class));
        XMLPersister parser = new XMLPersister(xmlMapper);
        PersistException exception = assertThrows(PersistException.class, ()->{parser.load(fileInputStream, String.class);});
        assertEquals("Parsing problem while loading xml file", exception.getMessage());
    }

    @Test
    public void testLoadIOExceptionThrowsPersistException() throws IOException, PersistException {
        doThrow(IOException.class).when(xmlMapper).readValue(any(FileInputStream.class), any(Class.class));
        XMLPersister parser = new XMLPersister(xmlMapper);
        PersistException exception = assertThrows(PersistException.class, ()->{parser.load(fileInputStream, String.class);});
        assertEquals("File reading problem while loading xml file", exception.getMessage());
    }

    @Test
    public void testSaveJsonMappingExceptionThrowsPersistException() throws IOException, PersistException {
        doThrow(JsonMappingException.class).when(xmlMapper).writeValue(any(File.class), any());
        XMLPersister parser = new XMLPersister(xmlMapper);
        PersistException exception = assertThrows(PersistException.class, ()->{parser.save(file, object);});
        assertEquals("Mapping problem while saving xml file", exception.getMessage());
    }

    @Test
    public void testSaveJsonGenerationExceptionThrowsPersistException() throws IOException, PersistException {
        doThrow(JsonGenerationException.class).when(xmlMapper).writeValue(any(File.class), any());
        XMLPersister parser = new XMLPersister(xmlMapper);
        PersistException exception = assertThrows(PersistException.class, ()->{parser.save(file, object);});
        assertEquals("Problem writing xml to file", exception.getMessage());
    }

    @Test
    public void testSaveIOExceptionThrowsPersistException() throws IOException, PersistException {
        doThrow(IOException.class).when(xmlMapper).writeValue(any(File.class), any());
        XMLPersister parser = new XMLPersister(xmlMapper);
        PersistException exception = assertThrows(PersistException.class, ()->{parser.save(file, object);});
        assertEquals("File writing problem while saving xml file", exception.getMessage());
    }
}
