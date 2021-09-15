package nz.ac.vuw.ecs.swen225.gp21.persistency;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.*;
import java.util.Arrays;
import java.util.List;

import nz.ac.vuw.ecs.swen225.gp21.domain.*;

/**
 * This class is responsible for reading map files and reading/writing files representing
 * the current game state (in XML format) in order for the player to resume games.
 * @author Lucy Goodwin
 */
public class ConcretePersister implements Persister {

    private static List<Integer> levelsThatExist = Arrays.asList();

    @Override
    public void loadLevel(int levelNumber, Domain domain) throws PersistException {
        if (!levelsThatExist.contains(levelNumber)) {
            throw new PersistException("Level " + levelNumber + " does not exist");
        }

        XMLParser<Level> parser = new XMLParser(new XmlMapper(), Level.class);
        //Level levelToLoad = parser.load(getLevelFileStream(levelNumber)); fixme

        // TODO
        // Load level into Domain
        // (if level 2, add Game object (see below))
        // change Domain state to 'doneLoading)

        //todo for loading the JAR file
        //        String pathName = "levels/level" + levelNumber;
        //        Class cls = Class.forName(pathName);
        //        Object obj = cls.newInstance();
    }

    @Override
    public void loadGame(File fileToLoad, Domain domain) throws PersistException {
        FileInputStream fs = getXMLFileStream(fileToLoad);
        XMLParser parser = new XMLParser(new XmlMapper(), Domain.class);
        Domain domainToLoad = (Domain)parser.load(fs);

        // TODO
        // Load domainToLoad into the domain object from method call
    }

    @Override
    public void saveCurrentGame(File fileToSave, Domain domain) throws PersistException {
        if (!checkFileXML(fileToSave)) {
            throw new PersistException("File to save a game to must be a .xml file.");
        }

        //TODO in all these methods need to check that Domain is not null AND file not null

        XMLParser parser = new XMLParser(new XmlMapper(), Domain.class);
        parser.save(fileToSave, domain);
    }




    // TODO I will be defining and saving the level myself
    private void saveLevel(int levelNumber, Level level) throws PersistException {
        if (levelNumber<1) {
            throw new PersistException("Level number must be a positive integer");
        }

        File fileToSave = new File("levels/level" + levelNumber + ".xml"); //fixme?
        XMLParser<Level> parser = new XMLParser(new XmlMapper(), Level.class);
        //parser.save(fileToSave, level); fixme how is this going to be handles
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
