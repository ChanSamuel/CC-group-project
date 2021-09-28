package nz.ac.vuw.ecs.swen225.gp21.persistency.tests;

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

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

/**
 * These tests test the functionality of the LevelHandling class
 */
@RunWith(MockitoJUnitRunner.class)
public class LevelHandlingTests {

    @Mock
    Domain domain;

    @Mock
    XMLPersister parser;

    @Captor
    ArgumentCaptor<Level> levelCaptor;

    @Captor
    ArgumentCaptor<Object> levelMementoCaptor;

//    @Rule
//    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void loadLevel1() throws PersistException {
        doNothing().when(domain).loadLevelData(levelCaptor.capture());
        doNothing().when(domain).doneLoading();
        LevelHandler.loadLevel(1, domain);
        Level level1Capture = levelCaptor.getValue();
        assertEquals(16, level1Capture.columns);
        assertEquals(16, level1Capture.rows);
    }

    @Test
    public void loadLevel2() throws PersistException {
        // todo test as I did for level 1
    }

    @Test
    public void loadLevel0() throws PersistException {
        // todo expect a persist exception
        // capture exception message
    }

    @Test
    public void saveLevelCaptureMemento() {
        // call save level but capture the memento
        // call mementoToLevel method with it
    }

    @Test
    public void nullMementoToLevel() {
        // todo expect exception
        // try convert null to level
    }

    @Test
    public void notMementoToLevel() {
        // todo expect exception
        // try convert string (or something !memento) to level
    }

    private void saveLevel1XML() throws PersistException {
        LevelHandler.saveLevelOne();
    }

    private void saveLevel2XML() throws PersistException {
        LevelHandler.saveLevelTwo();
    }
}
