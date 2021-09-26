package nz.ac.vuw.ecs.swen225.gp21.persistency;

import com.fasterxml.jackson.databind.jsontype.NamedType;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.WorldSave;
import nz.ac.vuw.ecs.swen225.gp21.domain.controllers.NoMovement;
import nz.ac.vuw.ecs.swen225.gp21.domain.controllers.PlayerController;
import nz.ac.vuw.ecs.swen225.gp21.domain.controllers.RandomMovement;
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

/**
 * TODO
 *  - I require that any interface/abstract type in WorldSave has the following annotation:
 *  @JsonTypeInfo(use= JsonTypeInfo.Id.NAME)
 *  - I also need JsonIgnore annotation in GameObject getName
 *  - And a default constructor for Coord class, and renaming getCol to getColumns or whatever the actual field is called
 *
 * Caretaker holds Mementos
 * Mementos are world save
 * Memento is a capture of the originator which is the Domain
 */
public class GameCaretaker {

    /**
     * The Originator... todo add some more info...
     */
    private Domain domain;

    /**
     * XMLMapper object with specific settings for mapping game state.
     */
    private static XmlMapper xmlMapper;
    static {
        xmlMapper = new XmlMapper();

        // Register GameObject subtypes
        xmlMapper.registerSubtypes(
                new NamedType(Chip.class, "Chip"),
                new NamedType(Block.class, "Block"),
                new NamedType(Monster.class, "Monster")
        );

        // Register MovementController subtypes
        xmlMapper.registerSubtypes(
                new NamedType(NoMovement.class, "NoMovement"),
                new NamedType(PlayerController.class, "PlayerController"),
                new NamedType(RandomMovement.class, "RandomMovement")
        );

        // Register State subtypes
        xmlMapper.registerSubtypes(
                new NamedType(Running.class, "Running"),
                new NamedType(GameOver.class, "GameOver"),
                new NamedType(Loading.class, "Loading"),
                new NamedType(Replaying.class, "Replaying")
        );

        // Register Terrain subtypes
        xmlMapper.registerSubtypes(
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
     * Constructor for a Game Caretaker, takes a domain object as the originator
     * @param domain the game will be captured from this Domain and/or restored to this Domain
     */
    public GameCaretaker(Domain domain) {
        this.domain = domain;
    }

    /**
     * This method is called by App when the user wants to load a saved game.
     * Loads a previously saved Domain (in an XML file) into a given Domain object.
     *  todo explain - load from a memento (world save)
     *
     * @param fileToLoad needs to be a .xml file
     * @throws PersistException that will provide an informative message that should be shown to the user
     */
    public void loadGame(File fileToLoad) throws PersistException {
        if (!checkFileXML(fileToLoad)) {
            throw new PersistException("File to save a game to must be a .xml file.");
        }
        FileInputStream fs = null;
        try {
            fs = new FileInputStream(fileToLoad);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new PersistException("Game loading failed, please try again.");
        }

        WorldSave worldSaveMemento = getMemento(fs);

        // domain.load data
        // TODO
        // Load domainToLoad into the domain object from method call
    }

    /**
     * Helper method for.... todo elaborate
     * Means I can test...
     * @param fs
     * @return
     * @throws PersistException
     */
    public WorldSave getMemento(FileInputStream fs) throws PersistException {
        XMLPersister parser = new XMLPersister(xmlMapper);
        return parser.load(fs, WorldSave.class);
    }

    /**
     *  This method is called by App when the user wants to save a current game.
     *  This method writes an XML file representing a Domain object that is holding the current game state.
     *  todo explain save a memento of originator (world save)
     *
     * @param fileToSave should be an .xml file
     * @throws PersistException The message of the exception shown should be shown in a pop-up message to the user
     */
    public void saveGame(File fileToSave) throws PersistException {
        if ((fileToSave==null) || (!checkFileXML(fileToSave))) {
            throw new PersistException("File to save a game to must be a .xml file.");
        }
        saveMemento(fileToSave, domain.generateSaveData());
    }

    /**
     * Helper method todo elaborate
     * @param fileToSave
     * @param worldSave
     * @throws PersistException
     */
    public void saveMemento(File fileToSave, WorldSave worldSave) throws PersistException {
        XMLPersister parser = new XMLPersister(xmlMapper);
        parser.save(fileToSave, worldSave);
    }

    /**
     * Helper method that returns true if the file passed to the method ends in .xml but false otherwise
     * @param file
     * @return boolean
     */
    private static boolean checkFileXML(File file) {
        assert(file!=null);
        String fileName = file.getName();
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? false : (fileName.substring(dotIndex + 1).equals("xml"));
    }

}
