package nz.ac.vuw.ecs.swen225.gp21.persistency.tests;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.persistency.*;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

/**
 * These tests test the functionality of the LevelHandling class.
 *
 * @author Lucy Goodwin
 */
@RunWith(MockitoJUnitRunner.class)
public class LevelHandlingTests {

    @Mock
    Domain domain;

    @Mock
    private XmlMapper xmlMapper;

    @Captor
    ArgumentCaptor<Level> levelCaptor;

    @Captor
    ArgumentCaptor<Object> levelMementoCaptor;

    @Captor
    ArgumentCaptor<GameObject> gameObjectCaptor;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void testLoadLevel1Ok() throws PersistException {
        doNothing().when(domain).loadLevelData(levelCaptor.capture());
        doNothing().when(domain).doneLoading();
        LevelHandler.loadLevel(1, domain);
        Level level1Capture = levelCaptor.getValue();
        assertEquals(16, level1Capture.columns);
        assertEquals(16, level1Capture.rows);
    }

    @Test
    public void testLoadLevel2Ok() throws PersistException {
        doNothing().when(domain).loadLevelData(levelCaptor.capture());
        doNothing().when(domain).addGameObject(gameObjectCaptor.capture(), any(Coord.class));
        doNothing().when(domain).doneLoading();
        LevelHandler.loadLevel(2, domain);
        Level level2Capture = levelCaptor.getValue();
        //assertEquals(16, level2Capture.columns); // todo assert some things

        GameObject secondActor = gameObjectCaptor.getValue();
        assertEquals('D', secondActor.boardChar());
        assertEquals("Dragon", secondActor.getName());
    }

    @Test
    public void testLoadLevel0ThrowsException() throws PersistException {
        exceptionRule.expect(PersistException.class);
        LevelHandler.loadLevel(0, domain);
    }

    @Test
    public void testSaveLevelsOk() throws PersistException, IOException {
        doNothing().when(xmlMapper).writeValue(any(File.class), levelMementoCaptor.capture());
        LevelHandler.saveLevels(xmlMapper);
        Object memento1 = levelMementoCaptor.getValue();
        Level level1 = LevelHandler.mementoToLevel(memento1);
        assertEquals(16, level1.rows);
        assertEquals(16, level1.columns);
    }

    @Test
    public void testNullMementoToLevelThrowsException() throws PersistException {
        exceptionRule.expect(PersistException.class);
        LevelHandler.mementoToLevel(null);
    }

    @Test
    public void testNonMementoToLevelThrowsException() throws PersistException {
        exceptionRule.expect(PersistException.class);
        LevelHandler.mementoToLevel("Lorem ipsum");
    }

    @Test
    public void testSaveLevelsXMLOk() throws PersistException {
        LevelHandler.saveLevels(new XmlMapper());
    }

    @Test
    public void testLoadLevelNullDomainThrowsException() throws PersistException {
        PersistException exception = assertThrows(PersistException.class, ()->{LevelHandler.loadLevel(1, null);});
        assertEquals("Error loading level 1", exception.getMessage());
    }

    @Test
    public void testSaveLevelsNullMapperThrowsException() throws PersistException {
        PersistException exception = assertThrows(PersistException.class, ()->{LevelHandler.saveLevels(null);});
        assertEquals("Error saving levels", exception.getMessage());
    }
}
