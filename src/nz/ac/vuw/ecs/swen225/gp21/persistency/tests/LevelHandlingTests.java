package nz.ac.vuw.ecs.swen225.gp21.persistency.tests;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.persistency.*;
import org.junit.Rule;
import org.junit.jupiter.api.Test;
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
    ExpectedException exceptionRule = ExpectedException.none();

    @Test
    public void saveLevel1() throws PersistException {
        LevelHandler.saveLevelOne();

// FIXME: 24/09/2021

//        doNothing().when(parser).save(any(File.class), levelMementoCaptor);
//        Level levelCaptured = ((LevelMemento)levelMementoCaptor.getValue()).toLevel();
    }

    @Test
    public void loadLevel1() throws FileNotFoundException, PersistException {

        Level levelOne = LevelHandler.getLevel(1);
        assertEquals(16, levelOne.rows);

// FIXME: 24/09/2021 
//        doNothing().when(domain).loadLevelData(levelArgumentCaptor.capture());
//        doNothing().when(domain).doneLoading();
//        LevelHandler.loadLevel(1, domain);
//        Level levelCapture = levelArgumentCaptor.getValue();
        
    }
}
