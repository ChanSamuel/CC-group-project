package nz.ac.vuw.ecs.swen225.gp21.persistency.tests;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.TestWorld;
import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;
import nz.ac.vuw.ecs.swen225.gp21.persistency.XMLParser;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import junit.framework.TestCase;

import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import org.mockito.junit.MockitoJUnitRunner;
import static org.mockito.Mockito.doThrow;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;


/**
 * These tests test the functionality of the ConcretePersister class
 * @author Lucy Goodwin
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class XMLParserMockTests extends TestCase {

    @Mock
    private XmlMapper xmlMapper;

    @Captor
    ArgumentCaptor<Object> objectCaptor;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testSaveObjectOk() throws PersistException, IOException {
        TestObject testObject = new TestObject(Arrays.asList("Hello", "World"), "test", 8, 9);
        File f = new File("test1.xml");
        XMLParser parser = new XMLParser(xmlMapper);

        doNothing().when(xmlMapper).writeValue(any(File.class), objectCaptor.capture());
        parser.save(f, testObject);

        TestObject testObjectCapture = (TestObject) objectCaptor.getValue();
        TestObject expectedObject = new TestObject(Arrays.asList("Hello", "World"), "test", 8, 9);

        assertEquals(expectedObject, testObjectCapture);
    }

    @Test
    public void testSaveWithIOException() throws IOException, PersistException {
        exceptionRule.expect(PersistException.class);
        TestObject testObject = new TestObject(Arrays.asList("Hello", "World"), "test", 8, 9);
        File f = new File("test.xml");
        XMLParser parser = new XMLParser(xmlMapper);
        doThrow(IOException.class).when(xmlMapper).writeValue(any(File.class), any());
        parser.save(f, testObject);
    }

    //TODO test other exceptions
}
