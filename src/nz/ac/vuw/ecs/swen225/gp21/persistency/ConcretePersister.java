package nz.ac.vuw.ecs.swen225.gp21.persistency;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.*;
import java.util.Arrays;
import java.util.List;

import nz.ac.vuw.ecs.swen225.gp21.domain.*;
import nz.ac.vuw.ecs.swen225.gp21.recorder.Recording;

/**
 * This class is responsible for reading map files and reading/writing files representing
 * the current game state (in XML format) in order for the player to resume games.
 * @author Lucy Goodwin
 */
public class ConcretePersister implements Persister {

    private static List<Integer> levelsThatExist = Arrays.asList(1); //todo add as we have levels


    @Override
    public void loadLevel(int levelNumber, Domain domain) throws PersistException {
        if (!levelsThatExist.contains(levelNumber)) {
            throw new PersistException("Level " + levelNumber + " does not exist");
        }

        XMLParser parser = new XMLParser(new XmlMapper());
        LevelHandler levelHandler = parser.load(getLevelFileStream(levelNumber), LevelHandler.class);
        domain.loadLevelData(LevelHandler.toLevel(levelHandler));
        domain.doneLoading();

        // todo (if level 2, add Game object before changing state to done loading)
        //        String pathName = "levels/level" + levelNumber;
        //        Class cls = Class.forName(pathName);
        //        Object obj = cls.newInstance();
    }

    @Override
    public void loadGame(File fileToLoad, Domain domain) throws PersistException {
        FileInputStream fs = getXMLFileStream(fileToLoad);
        XMLParser parser = new XMLParser(new XmlMapper());
        Domain domainToLoad = (Domain)parser.load(fs, Domain.class);

        // TODO
        // Load domainToLoad into the domain object from method call
    }

    @Override
    public void saveCurrentGame(File fileToSave, Domain domain) throws PersistException {
        if (!checkFileXML(fileToSave)) {
            throw new PersistException("File to save a game to must be a .xml file.");
        }

        //TODO in all these methods need to check that Domain is not null AND file not null

        XMLParser parser = new XMLParser(new XmlMapper());
        parser.save(fileToSave, domain);
    }

    @Override
    public Recording getRecording(File recordingFile) throws PersistException {
        if (!checkFileXML(recordingFile)) {
            throw new PersistException("File to save a game to must be a .xml file.");
        }

        XMLParser parser = new XMLParser(new XmlMapper());
        return (Recording) parser.load(getXMLFileStream(recordingFile), Recording.class);
    }

    @Override
    public void saveRecording(File recordingFile, Recording recording) throws PersistException {
        if (!checkFileXML(recordingFile)) {
            throw new PersistException("File to save a game to must be a .xml file.");
        }

        XMLParser parser = new XMLParser(new XmlMapper());
        parser.save(recordingFile, recording);
    }


    // TODO I will be defining and saving the level myself
    public void saveLevel(File fileToSave, LevelHandler level) throws PersistException {
        if (!checkFileXML(fileToSave)) {
            throw new PersistException("File is not a .xml file!");
        }
        XMLParser parser = new XMLParser(new XmlMapper());
        parser.save(fileToSave, level);
    }


    /**
     * Helper method that returns a filestream of an XML file.
     * @param file
     * @return FileInputStream
     * @throws PersistException if file is not an XML file
     */
    private FileInputStream getXMLFileStream(File file) throws PersistException {
        if (!checkFileXML(file)) {
            throw new PersistException("File is not a .xml file!");
        }

        FileInputStream fs = null;
        try {
            return new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new PersistException("Game loading failed, please try again.");
        }
    }

    /**
     * Helper method that creates a new .xml file for a level to be saved in (as the given level number).
     * @param levelNumber
     * @returna file stream of the file created
     * @throws PersistException
     */
    private FileInputStream getLevelFileStream(int levelNumber) throws PersistException {
        try {
            return new FileInputStream(new File("levels/level" + levelNumber + ".xml")); //fixme?
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new PersistException("Level loading failed, please try again.");
        }
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
