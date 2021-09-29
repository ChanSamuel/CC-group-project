package nz.ac.vuw.ecs.swen225.gp21.persistency;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.GameObject;
import nz.ac.vuw.ecs.swen225.gp21.domain.Level;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.List;

/**
 * TODO
 *
 * @author Lucy Goodwin
 */
public class LevelHandler {

    /**
     * XMLMapper object with specific settings for mapping levels.
     */
    private static final XmlMapper xmlMapper = new XmlMapper();

    /**
     * List of integers that represent the levels that are defined and can be loaded
     */
    private static final List<Integer> levelsThatExist = Arrays.asList(1, 2);

    /**
     * Loads level (determined by which level number) into a given domain.
     *
     * @param levelNumber must be a level that exists
     * @throws PersistException that will provide an informative message that should be shown to the user
     */
    public static void loadLevel(int levelNumber, Domain domain) throws PersistException {
        if (domain == null) throw new PersistException("Error loading level " + levelNumber);

        Level levelToLoad = getLevel(levelNumber);
        domain.loadLevelData(levelToLoad);

        if (levelNumber == 2) {
            try {
                domain.addGameObject(getSecondActor(), new Coord(10, 7)); // FIXME use correct coordinate
            } catch (Exception e) {
                throw new PersistException("Error loading logic for level 2 actor");
            }
        }

        domain.doneLoading();
    }

    /**
     * TODO
     *
     * @return
     * @throws PersistException
     */
    private static GameObject getSecondActor() throws PersistException, MalformedURLException, ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        File jarFile = new File("levels/level2.jar");
        String className = "Dragon";
        URL fileURL = jarFile.toURI().toURL();
        String jarURL = "jar:" + fileURL + "!/";
        URL[] urls = {new URL(jarURL)};
        URLClassLoader ucl = new URLClassLoader(urls);
        Class clazz = Class.forName(className, false, ucl);
        GameCaretaker.registerMapperSubtype(clazz, clazz.getName());

        InputStream leftStream = ucl.getResourceAsStream("dragon_left.GIF");
        InputStream rightStream = ucl.getResourceAsStream("dragon_right.gif");

        return (GameObject) clazz.getConstructor(InputStream.class, InputStream.class)
                .newInstance(leftStream, rightStream);
    }

    /**
     * Saves LevelMemento fields into XML so that levels can be loaded.
     *
     * @throws PersistException that will provide an informative message that should be shown to the user
     */
    public static void saveLevels(XmlMapper mapper) throws PersistException {
        if (mapper == null) throw new PersistException("Error saving levels");
        XMLPersister parser = new XMLPersister(mapper);
        parser.save(new File("levels/level1.xml"), LevelMemento.levelOne);
        parser.save(new File("levels/level2.xml"), LevelMemento.levelTwo);
    }

    /**
     * Converts a LevelMemento into a Level. Since LevelMemento is an inner class, an Object type object is accepted as
     * the parameter and cast into a LevelMemento is it is an instance of one.
     *
     * @param o object should be a LevelMemento
     * @return Level object
     * @throws PersistException that will provide an informative message that should be shown to the user
     */
    public static Level mementoToLevel(Object o) throws PersistException {
        if (!(o instanceof LevelMemento)) {
            throw new PersistException("Object must be a LevelMemento");
        }
        // Now I know it is a level memento object I can cast it to one
        LevelMemento toConvert = (LevelMemento) o;
        return new Level(
                toConvert.getRows(),
                toConvert.getCols(),
                toConvert.getTerrainLayout(),
                toConvert.getEntityLayout(),
                toConvert.getInfo());
    }

    /**
     * Helper method for loading level. Loads and returns a level (number given as parameter) if it exists.
     *
     * @param levelNumber which level to load
     * @return that will provide an informative message that should be shown to the user
     * @throws PersistException that will provide an informative message that should be shown to the user
     */
    private static Level getLevel(int levelNumber) throws PersistException {
        if (!levelsThatExist.contains(levelNumber)) {
            throw new PersistException("Level " + levelNumber + " does not exist");
        }
        try {
            XMLPersister parser = new XMLPersister(xmlMapper);
            File file = new File("levels/level" + levelNumber + ".xml");
            FileInputStream fileStream = new FileInputStream(file);
            LevelMemento levelMemento = parser.load(fileStream, LevelMemento.class);
            return mementoToLevel(levelMemento);
        } catch (FileNotFoundException e) {
            throw new PersistException("");
        }
    }

}