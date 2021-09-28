package nz.ac.vuw.ecs.swen225.gp21.persistency.tests;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.persistency.LevelHandler;
import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doNothing;

/**
 * These tests test the functionality of the LevelHandling class.
 *
 * @author Lucy Goodwin
 */
@RunWith(MockitoJUnitRunner.class)
public class GameCaretakerTests {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Mock
    Domain domain;

    @Captor
    ArgumentCaptor<Level> levelCaptor;

    @Captor
    ArgumentCaptor<Object> levelMementoCaptor;


    @Test
    public void testLoadGameOk() {

    }

    @Test
    public void testLoadNullGame() {

    }

    @Test
    public void testLoadGameNonXml() {

    }

    @Test
    public void testLoadGameFileNotFoundException() {

    }

    @Test
    public void saveNullGame() {

    }

    @Test
    public void saveNonXmlGame() {

    }

    @Test
    public void loadLevel1() throws PersistException {
        doNothing().when(domain).loadLevelData(levelCaptor.capture());
        doNothing().when(domain).doneLoading();
        LevelHandler.loadLevel(1, domain);
        Level level1Capture = levelCaptor.getValue();
        assertEquals(16, level1Capture.columns);
        assertEquals(16, level1Capture.rows);
    }
}
