package nz.ac.vuw.ecs.swen225.gp21.persistency;

import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.controllers.NoMovement;
import nz.ac.vuw.ecs.swen225.gp21.domain.controllers.PlayerController;
import nz.ac.vuw.ecs.swen225.gp21.domain.controllers.RandomMovement;
import nz.ac.vuw.ecs.swen225.gp21.domain.items.KeyItem;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Block;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Chip;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Monster;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.GameOver;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Loading;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Replaying;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Running;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URLClassLoader;

/**
 * This class knows 'when' and 'why' to capture and restore a Domain's state (as a GameMemento).
 * It holds a Domain (originator) and captures/restores its state as a GameMemento when the app
 * calls to save or restore a game.
 *
 * @author Lucy Goodwin
 */
public class GameCaretaker {

    /**
     * XMLMapper object with specific settings for mapping game state.
     */
    private static final XmlMapper xmlMapper;

    /**
     * The static XmlMapper for this class is registered with all of the subtypes inside the Domain module.
     * This is necessary to be able to use the XmlPersister class.
     */
    static {
        xmlMapper = new XmlMapper();
        xmlMapper.registerSubtypes(
                new NamedType(Chip.class, "Chip"),
                new NamedType(Block.class, "Block"),
                new NamedType(Monster.class, "Monster"),

                new NamedType(KeyItem.class, "KeyItem"),

                new NamedType(NoMovement.class, "NoMovement"),
                new NamedType(PlayerController.class, "PlayerController"),
                new NamedType(RandomMovement.class, "RandomMovement"),

                new NamedType(Running.class, "Running"),
                new NamedType(GameOver.class, "GameOver"),
                new NamedType(Loading.class, "Loading"),
                new NamedType(Replaying.class, "Replaying"),

                new NamedType(CopperDoor.class, "CopperDoor"),
                new NamedType(CopperKey.class, "CopperKey"),
                new NamedType(ExitLock.class, "ExitLock"),
                new NamedType(ExitTile.class, "ExitTile"),
                new NamedType(Free.class, "Free"),
                new NamedType(GoldDoor.class, "GoldDoor"),
                new NamedType(GoldKey.class, "GoldKey"),
                new NamedType(GreenDoor.class, "GreenDoor"),
                new NamedType(GreenKey.class, "GreenKey"),
                new NamedType(Info.class, "Info"),
                new NamedType(OneWayEast.class, "OneWayEast"),
                new NamedType(OneWayNorth.class, "OneWayNorth"),
                new NamedType(OneWaySouth.class, "OneWaySouth"),
                new NamedType(OneWayWest.class, "OneWayWest"),
                new NamedType(SilverDoor.class, "SilverDoor"),
                new NamedType(SilverKey.class, "SilverKey"),
                new NamedType(Teleporter.class, "Teleporter"),
                new NamedType(Treasure.class, "Treasure"),
                new NamedType(Wall.class, "Wall")
        );
        xmlMapper.getFactory().getXMLOutputFactory().setProperty("javax.xml.stream.isRepairingNamespaces", false);
    }

    /**
     * The originator for the memento design pattern.
     * Can produce a snap shot (GameMemento) of its own state, which is accessed through its generateSaveData() method.
     */
    private final Domain domain;

    /**
     * Constructor for a Game Caretaker, takes a domain object as the originator.
     *
     * @param domain the game will be captured from this Domain and/or restored to this Domain
     */
    public GameCaretaker(Domain domain) throws PersistException {
        if (domain == null) throw new PersistException("Cannot persist a null game");
        this.domain = domain;

        // Because loading a level 2 game requires knowledge of the second actor as a subtype
        try {
            URLClassLoader classLoader = LevelHandler.getSecondActorClassLoader();
            Class clazz = Class.forName("Dragon", false, classLoader);
            registerMapperSubtype(clazz, clazz.getName());
        } catch (Exception e) {
            throw new PersistException("Cannot persist level 2 data");
        }
    }

    /**
     * Helper method that returns true if the file passed to the method does not end in .xml
     *
     * @param file The file to check
     * @return boolean (true if file does not end in '.xml')
     */
    private static boolean notXMLFile(File file) {
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        // if there is no '.' in the filename then immediately return true
        if (dotIndex == -1) return true;
        // otherwise return whether the filename doesn't end in 'xml'
        return !(fileName.substring(dotIndex + 1).equals("xml"));
    }

    /**
     * todo
     * @param clazz
     * @param name
     */
    public static void registerMapperSubtype(Class clazz, String name) {
        xmlMapper.registerSubtypes(new NamedType(clazz, name));
    }

    /**
     * This method is called by App when the user wants to load a saved game.
     * It calls on the XmlPersister class to restore a GameMemento object from an XML file. It then passes the
     * GameMemento to the Domain (originator) to be restored. This method returns metadata to the app regarding
     * the level and time left in the game.
     *
     * @param fileToLoad needs to be a .xml file
     * @return an array containing the level number at index 0 and the time left at index 1
     * @throws PersistException that will provide an informative message that should be shown to the user
     */
    public int[] loadGame(File fileToLoad) throws PersistException {
        if ((fileToLoad == null) || notXMLFile(fileToLoad)) {
            throw new PersistException("File to load a game to must be a .xml file.");
        }
        FileInputStream fs = getFileInputStream(fileToLoad);
        GameMemento memento = getMemento(fs);

        // Cannot persist a javas InputStream so have to manually populate this if loading level two
        if (memento.getLevelNumber()==2) {
            for (GameObject go: memento.gameObjects) {
                if (go instanceof Monster) {
                    try {
                        URLClassLoader classLoader = LevelHandler.getSecondActorClassLoader();
                        Class clazz = Class.forName("Dragon", false, classLoader);
                        registerMapperSubtype(clazz, clazz.getName());
                        go.setLeftStream(classLoader.getResourceAsStream("dragon_left.GIF"));
                        go.setRightStream(classLoader.getResourceAsStream("dragon_right.gif"));
                    } catch (Exception e) {
                        throw new PersistException("Cannot render second actor");
                    }
                }
            }
        }
        domain.restoreGame(memento);
        domain.doneLoading();
        return new int[] {memento.getLevelNumber(), memento.getTimeLeft()};
    }

    /**
     * Helper method for loading games that returns a file stream of a given file.
     *
     * @param fileToLoad xml file to be loaded
     * @return FileInput stream of the given file
     * @throws PersistException with information to be shown to the user
     */
    private FileInputStream getFileInputStream(File fileToLoad) throws PersistException {
        try {
            return new FileInputStream(fileToLoad);
        } catch (FileNotFoundException e) {
            throw new PersistException("Game loading failed, please try again.");
        }
    }

    /**
     * Helper method for restoring a game.
     *
     * @param fs FileInputStream to load memento from
     * @return Memento from FileInputStream
     * @throws PersistException with information to be shown to the user
     */
    public GameMemento getMemento(FileInputStream fs) throws PersistException {
        if (fs==null) throw new PersistException("Loading game failed");
        XMLPersister parser = new XMLPersister(xmlMapper);
        return parser.load(fs, GameMemento.class);
    }


    /**
     * This method is called by the App module when the user wants to save a current game.
     * This method calls on the Domain (originator) to generate a GameMemento which captures the state of the current
     * game. This method adds metadata from the app regarding the level being played and the time left in the game.
     * This method calls on the XmlPersister class to persist the GameMemento as an XML file.
     *
     * @param fileToSave should be an .xml file
     * @param level the number of the level currently being played
     * @param timeLeft the amount of seconds left in the game currently being played
     * @throws PersistException The message of the exception shown should be shown in a pop-up message to the user
     */
    public void saveGame(File fileToSave, int level, int timeLeft) throws PersistException {
        if ((fileToSave == null) || notXMLFile(fileToSave)) {
            throw new PersistException("File to save a game to must be a .xml file.");
        }
        XMLPersister parser = new XMLPersister(xmlMapper);
        GameMemento gameMemento = domain.generateSaveData();
        gameMemento.setLevelNumber(level);
        gameMemento.setTimeLeft(timeLeft);
        parser.save(fileToSave, gameMemento);
    }
}
