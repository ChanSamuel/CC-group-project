package nz.ac.vuw.ecs.swen225.gp21.persistency.tests;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonParseException;
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
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * These tests test the XmlPersister class
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

        // FIXME

        String toReturn = "Returned String";
        when(xmlMapper.readValue(any(FileInputStream.class), String.class)).thenReturn(toReturn);

        XMLPersister parser = new XMLPersister(xmlMapper);
        String returned = parser.load(fileInputStream, String.class);

        assertEquals(toReturn, returned);
    }

    @Test
    public void testSaveOkay() throws IOException, PersistException {
        String testString = "Lorem ipsum";
        doNothing().when(xmlMapper).writeValue(any(File.class), stringArgumentCaptor.capture());

        XMLPersister parser = new XMLPersister(xmlMapper);
        parser.save(file, testString);

        String stringCall = stringArgumentCaptor.getValue();
        assertEquals("Lorem ipsum", stringCall);
    }

    @Test
    public void testLoadWithJsonMappingException() {
        // TODO
        //  expected = PersistException("Mapping problem while loading xml file");
    }

    @Test
    public void testLoadWithJsonParseException() {
        // TODO
        //  expected =  PersistException("Parsing problem while loading xml file");
    }

    @Test
    public void testLoadWithIOException() {
        // TODO
        //  PersistException("File reading problem while loading xml file");
    }

    @Test
    public void testSaveWithJsonMappingException() throws IOException {
        doThrow(JsonMappingException.class).when(xmlMapper).writeValue(any(File.class), any());
        XMLPersister parser = new XMLPersister(xmlMapper);
        String errorMsg = "Not correct";

        try {
            parser.save(file, object);
        } catch (PersistException e) {
            errorMsg = e.getMessage();
        }

        assertEquals("Mapping problem while saving xml file", errorMsg);
    }

    @Test
    public void testSaveWithJsonGenerationException() throws IOException {
        doThrow(JsonGenerationException.class).when(xmlMapper).writeValue(any(File.class), any());
        XMLPersister parser = new XMLPersister(xmlMapper);
        String errorMsg = "Not correct";

        try {
            parser.save(file, object);
        } catch (PersistException e) {
            errorMsg = e.getMessage();
        }

        assertEquals("Problem writing xml to file", errorMsg);
    }

    @Test
    public void testSaveWithIOException() throws IOException {
        doThrow(IOException.class).when(xmlMapper).writeValue(any(File.class), any());
        XMLPersister parser = new XMLPersister(xmlMapper);
        String errorMsg = "Not correct";

        try {
            parser.save(file, object);
        } catch (PersistException e) {
            errorMsg = e.getMessage();
        }

        assertEquals("File writing problem while saving xml file", errorMsg);
    }
}
