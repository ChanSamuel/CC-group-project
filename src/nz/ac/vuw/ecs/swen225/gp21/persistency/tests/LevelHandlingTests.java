package nz.ac.vuw.ecs.swen225.gp21.persistency.tests;

import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
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
import static org.mockito.Mockito.doThrow;

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

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void loadLevel1() throws PersistException {
        doNothing().when(domain).loadLevelData(levelCaptor.capture());
        doNothing().when(domain).doneLoading();
        LevelHandler.loadLevel(1, domain);
        Level level1Capture = levelCaptor.getValue();
        assertEquals(16, level1Capture.columns);
        assertEquals(16, level1Capture.rows);
    }

//    @Test fixme
    public void loadLevel2() throws PersistException {
        doNothing().when(domain).loadLevelData(levelCaptor.capture());
        doNothing().when(domain).doneLoading();
        LevelHandler.loadLevel(2, domain);
        Level level2Capture = levelCaptor.getValue();
        // todo add some assertions
    }

    @Test
    public void loadLevel0() throws PersistException {
        exceptionRule.expect(PersistException.class);
        LevelHandler.loadLevel(0, domain);
    }

    @Test
    public void saveLevel1CaptureMemento() throws PersistException, IOException {
        doNothing().when(xmlMapper).writeValue(any(File.class), levelMementoCaptor.capture());
        LevelHandler.saveLevelOne(xmlMapper);
        Object memento1 = levelMementoCaptor.getValue();
        Level level1 = LevelHandler.mementoToLevel(memento1);
        assertEquals(16, level1.rows);
        assertEquals(16, level1.columns);
    }

//    @Test fixme
    public void saveLevel2CaptureMemento() throws PersistException, IOException {
        doNothing().when(xmlMapper).writeValue(any(File.class), levelMementoCaptor.capture());
        LevelHandler.saveLevelTwo(xmlMapper);
        Object memento2 = levelMementoCaptor.getValue();
        Level level2 = LevelHandler.mementoToLevel(memento2);
        // todo add some assertions
    }

    @Test
    public void nullMementoToLevel() throws PersistException {
        exceptionRule.expect(PersistException.class);
        LevelHandler.mementoToLevel(null);
    }

    @Test
    public void notMementoToLevel() throws PersistException {
        exceptionRule.expect(PersistException.class);
        LevelHandler.mementoToLevel("Lorem ipsum");
    }

    @Test
    public void saveLevel1XML() throws PersistException {
        LevelHandler.saveLevelOne(new XmlMapper());
    }

//    @Test fixme
    public void saveLevel2XML() throws PersistException {
        LevelHandler.saveLevelTwo(new XmlMapper());
    }

    @Test
    public void testLoadLevelNullDomain() throws PersistException {
        PersistException exception = assertThrows(PersistException.class, ()->{LevelHandler.loadLevel(1, null);});
        assertEquals("Error loading level 1", exception.getMessage());
    }

    @Test
    public void saveLevel1NullMapper() throws PersistException {
        PersistException exception = assertThrows(PersistException.class, ()->{LevelHandler.saveLevelOne(null);});
        assertEquals("Error saving level 1", exception.getMessage());
    }

    @Test
    public void saveLevel2NullMapper() throws PersistException {
        PersistException exception = assertThrows(PersistException.class, ()->{LevelHandler.saveLevelTwo(null);});
        assertEquals("Error saving level 2", exception.getMessage());
    }
}
