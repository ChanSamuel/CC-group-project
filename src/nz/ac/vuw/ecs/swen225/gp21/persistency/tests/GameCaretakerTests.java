package nz.ac.vuw.ecs.swen225.gp21.persistency.tests;

import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.persistency.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;

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
public class GameCaretakerTests {

    @Mock
    Domain domain;

    @Test
    public void testNullDomainThrowsException() throws PersistException {
        PersistException exception = assertThrows(PersistException.class, ()->{new GameCaretaker(null);});
        assertEquals("Cannot persist a null game", exception.getMessage());
    }

    @Test
    public void testLoadGameOk() throws PersistException {
        String path = "src/nz/ac/vuw/ecs/swen225/gp21/persistency/tests/memento_test.xml";
        File savedGame = new File(path);
        GameCaretaker gameCaretaker = new GameCaretaker(domain);
        doNothing().when(domain).restoreGame(any(GameMemento.class));
        doNothing().when(domain).doneLoading();
        gameCaretaker.loadGame(savedGame);
    }

    @Test
    public void testLoadNullGameThrowsException() throws PersistException {
        File nullFile = null;
        GameCaretaker gameCaretaker = new GameCaretaker(domain);
        PersistException exception = assertThrows(PersistException.class, ()->{gameCaretaker.loadGame(nullFile);});
        assertEquals("File to load a game to must be a .xml file.", exception.getMessage());
    }

    @Test
    public void testLoadNonXmlGameThrowsException() throws PersistException {
        File nonXml = new File("nonXml.txt");
        GameCaretaker gameCaretaker = new GameCaretaker(domain);
        PersistException exception = assertThrows(PersistException.class, ()->{gameCaretaker.loadGame(nonXml);});
        assertEquals("File to load a game to must be a .xml file.", exception.getMessage());
    }

    @Test
    public void testLoadGameFileNotFoundExceptionThrowsPersistException() throws PersistException {
        File nonExistXml = new File("thisDoesNotExist.xml");
        GameCaretaker gameCaretaker = new GameCaretaker(domain);
        PersistException exception = assertThrows(PersistException.class, ()->{gameCaretaker.loadGame(nonExistXml);});
        assertEquals("Game loading failed, please try again.", exception.getMessage());
    }

    @Test
    public void saveNullGameThrowsException() throws PersistException {
        GameCaretaker gameCaretaker = new GameCaretaker(domain);
        PersistException exception = assertThrows(PersistException.class, ()->{gameCaretaker.saveGame(null, 1, 10);});
        assertEquals("File to save a game to must be a .xml file.", exception.getMessage());
    }

    @Test
    public void saveNonXmlGameThrowsException() throws PersistException {
        File nonXml = new File("nonXml.txt");
        GameCaretaker gameCaretaker = new GameCaretaker(domain);
        PersistException exception = assertThrows(PersistException.class, ()->{gameCaretaker.saveGame(nonXml, 1, 10);});
        assertEquals("File to save a game to must be a .xml file.", exception.getMessage());
    }

    @Test
    public void saveNonXmlGame2ThrowsException() throws PersistException {
        File nonXml = new File("nonXml");
        GameCaretaker gameCaretaker = new GameCaretaker(domain);
        PersistException exception = assertThrows(PersistException.class, ()->{gameCaretaker.saveGame(nonXml, 1, 10);});
        assertEquals("File to save a game to must be a .xml file.", exception.getMessage());
    }

    @Test
    public void testGetMementoNulLFilestreamThrowsException() throws PersistException {
        GameCaretaker gameCaretaker = new GameCaretaker(domain);
        PersistException exception = assertThrows(PersistException.class, ()->{gameCaretaker.getMemento(null);});
        assertEquals("Loading game failed", exception.getMessage());
    }
}
