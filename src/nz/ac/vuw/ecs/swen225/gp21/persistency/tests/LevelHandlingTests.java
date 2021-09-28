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

import java.io.File;
import java.io.FileNotFoundException;

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
    ArgumentCaptor<Level> levelArgumentCaptor;

    @Captor
    ArgumentCaptor<Object> levelMementoCaptor;

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void loadSaveLevel1() throws PersistException {
        saveLevel1();
        loadLevel1();
    }

    //@Test fixme
    public void loadSaveLevel2() throws PersistException {
        saveLevel2();
        loadLevel2();
    }

    private void saveLevel1() throws PersistException {
        LevelHandler.saveLevelOne();
    }

    private void saveLevel2() throws PersistException {
        LevelHandler.saveLevelTwo();
    }

    private void loadLevel1() throws PersistException {
        Level l1 = LevelHandler.getLevel(1);
        assertEquals(16, l1.columns);
        assertEquals(16, l1.rows);
    }
    private void loadLevel2() throws PersistException {
        Level l1 = LevelHandler.getLevel(2);
        //assertEquals(16, l1.columns); todo
        //assertEquals(16, l1.rows); todo
    }

}
