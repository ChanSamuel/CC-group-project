package nz.ac.vuw.ecs.swen225.gp21.persistency.tests;

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
import java.io.FileInputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

/**
 * These tests test the functionality of the LevelHandling class.
 *
 * @author Lucy Goodwin
 */
@RunWith(MockitoJUnitRunner.class)
public class GameCaretakerTests {

//    @Rule
//    public ExpectedException exceptionRule = ExpectedException.none();

    @Mock
    Domain domain;

    private static File savedGame;
    static {
        savedGame = null;
        // todo save a real game!!!!!!!!!!!!
    }

    @Test
    public void testLoadGameOk() throws PersistException {
        GameCaretaker gameCaretaker = new GameCaretaker(domain);
        doNothing().when(domain).restoreGame(any(GameMemento.class));
        doNothing().when(domain).doneLoading();
        gameCaretaker.loadGame(savedGame);
    }

    @Test
    public void testLoadNullGame() {
        File nullFile = null;
        GameCaretaker gameCaretaker = new GameCaretaker(domain);
        PersistException exception = assertThrows(PersistException.class, ()->{gameCaretaker.loadGame(nullFile);});
        assertEquals("File to load a game to must be a .xml file.", exception.getMessage());
    }

    @Test
    public void testLoadGameNonXml() {
        File nonXml = new File("nonXml.txt");
        GameCaretaker gameCaretaker = new GameCaretaker(domain);
        PersistException exception = assertThrows(PersistException.class, ()->{gameCaretaker.loadGame(nonXml);});
        assertEquals("File to load a game to must be a .xml file.", exception.getMessage());
    }

    @Test
    public void testLoadGameFileNotFoundException() {
        File nonExistXml = new File("thisDoesNotExist.xml");
        GameCaretaker gameCaretaker = new GameCaretaker(domain);
        PersistException exception = assertThrows(PersistException.class, ()->{gameCaretaker.loadGame(nonExistXml);});
        assertEquals("Game loading failed, please try again.", exception.getMessage());
    }

    @Test
    public void saveNullGame() {
        File nullFile = null;
        GameCaretaker gameCaretaker = new GameCaretaker(domain);
        PersistException exception = assertThrows(PersistException.class, ()->{gameCaretaker.saveGame(nullFile);});
        assertEquals("File to save a game to must be a .xml file.", exception.getMessage());
    }

    @Test
    public void saveNonXmlGame() {
        File nonXml = new File("nonXml.txt");
        GameCaretaker gameCaretaker = new GameCaretaker(domain);
        PersistException exception = assertThrows(PersistException.class, ()->{gameCaretaker.saveGame(nonXml);});
        assertEquals("File to save a game to must be a .xml file.", exception.getMessage());
    }
}
