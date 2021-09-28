package nz.ac.vuw.ecs.swen225.gp21.persistency.tests;

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
    private FileInputStream inputStream;

    @Captor
    ArgumentCaptor<String> stringArgumentCaptor;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testLoadOkay() throws IOException, PersistException {

        // FIXME

        String toReturn = "Returned String";
        when(xmlMapper.readValue(any(File.class), String.class)).thenReturn(toReturn);

        XMLPersister parser = new XMLPersister(xmlMapper);
        String returned = parser.load(inputStream, String.class);

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
        //  Am I able to capture the message of the exception thrown
        //  Can I use a try/catch or something?

        // PersistException("Mapping problem while loading xml file");
    }

    @Test
    public void testLoadWithJsonParseException() {
        // TODO

        // PersistException("Parsing problem while loading xml file");
    }

    @Test
    public void testLoadWithIOException() {
        // TODO

        // PersistException("File reading problem while loading xml file");
    }

    @Test
    public void testSaveWithJsonMappingException() {
        // TODO

        // PersistException("Mapping problem while saving xml file");
    }

    @Test
    public void testSaveWithJsonGenerationException() {
        // TODO

//        PersistException("Problem writing xml to file");
    }

    @Test
    public void testSaveWithIOException() {
        // TODO

        // PersistException("File writing problem while saving xml file");

// fixme
//        exceptionRule.expect(PersistException.class);
//        String testString = "Lorem ipsum";
//        File f = new File("test.xml");
//        XMLPersister parser = new XMLPersister(xmlMapper);
//        doThrow(IOException.class).when(xmlMapper).writeValue(any(File.class), any());
//        parser.save(any(File.class), testString);
    }
}
