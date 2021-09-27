package nz.ac.vuw.ecs.swen225.gp21.persistency;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.Level;

import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

// TODO: 24/09/2021
/**
 *
 */
public class LevelHandler {

    /**
     * XMLMapper object with specific settings for mapping levels.
     */
    private static final XmlMapper xmlMapper = new XmlMapper();

    /**
     * List of integers that represent the levels that are defined and can be loaded
     */
    private static final List<Integer> levelsThatExist = Arrays.asList(1); // todo add as we have levels

    /**
     * LevelMemento that represents level one todo update to relevant info
     */
    private static final LevelMemento levelOne = new LevelMemento(16, 16, 1,
    "################"
    + "#..........#...#"
    + "#..........#.g.#"
    + "#...########...#"
    + "#...#......#...#"
    + "#...#.a..g.#.c.#"
    + "#...#......A...#"
    + "#...#.c....#...#"
    + "#...G...##G#####"
    + "#...#...#......#"
    + "#...#...#...c..#"
    + "#...#...#..###.#"
    + "#.c.#.i.#..XE#.#"
    + "#...#...#..###.#"
    + "#...#####......#"
    + "################",

    "................"
    + "................"
    + "................"
    + "................"
    + "................"
    + "................"
    + "................"
    + "................"
    + "................"
    + "................"
    + "......C........."
    + "................"
    + "................"
    + "................"
    + "................"
    + "................", "Level One information");

    /**
     * LevelMemento that represents level two
     * todo - initialise this
     */
    private static LevelMemento levelTwo;

    /**
     * Loads level (determined by which level number) into a given domain.
     *
     * @param levelNumber must be a level that exists
     * @throws PersistException that will provide an informative message that should be shown to the user
     */
    public static void loadLevel(int levelNumber, Domain domain) throws PersistException {
        Level levelToLoad = getLevel(levelNumber);
        domain.loadLevelData(levelToLoad);

        if (levelNumber==2) {

// TODO: 24/09/2021 Add Game Object
//            String pathName = "levels/level" + levelNumber;
//            Class cls = Class.forName(pathName);
//            Object obj = cls.newInstance();
//            addGameObject(GameObject o, Coord c);
        }

        domain.doneLoading();
    }

    /**
     * Helper method todo finish doc
     * @param levelNumber which level to load
     * @return that will provide an informative message that should be shown to the user
     * @throws PersistException
     */
    public static Level getLevel(int levelNumber) throws PersistException {
        if (!levelsThatExist.contains(levelNumber)) {
            throw new PersistException("Level " + levelNumber + " does not exist");
        }

        XMLPersister parser = new XMLPersister(xmlMapper);
        InputStream is = LevelHandler.class.getResourceAsStream
                ("/nz/ac/vuw/ecs/swen225/gp21/persistency/levels/level" + levelNumber + ".xml"); //fixme?

        LevelMemento levelMemento = parser.load(is, LevelMemento.class);
        return levelMemento.toLevel();
    }

    /**
     * Saves level one LevelMemento field to an XML
     * @throws PersistException that will provide an informative message that should be shown to the user
     */
    public static void saveLevelOne() throws PersistException {
        XMLPersister parser = new XMLPersister(xmlMapper);
        parser.save(new File("src/nz/ac/vuw/ecs/swen225/gp21/persistency/levels/level1.xml"), levelOne);
    }

    /**
     * Saves level two LevelMemento field to an XML
     * @throws PersistException that will provide an informative message that should be shown to the user
     */
    public static void saveLevelTwo() throws PersistException {
// TODO: 24/09/2021
//        XMLParser parser = new XMLParser(new XmlMapper());
//        parser.save(new File("src/nz/ac/vuw/ecs/swen225/gp21/persistency/levels/level2.xml"), levelTwo);
    }
}

// TODO: 24/09/2021 Add more JavaDoc?
/**
 * Captures a Level state so it can be written to an XML. Only used by the LevelHandler class.
 */
class LevelMemento {
    private int rows, cols, levelNumber;
    private String terrainLayout, entityLayout, info;

    public LevelMemento(int rows, int cols, int levelNumber, String terrainLayout, String entityLayout, String info) {
        this.rows = rows;
        this.cols = cols;
        this.levelNumber = levelNumber;
        this.terrainLayout = terrainLayout;
        this.entityLayout = entityLayout;
        this.info = info;
    }

    private LevelMemento() {}

    public Level toLevel() {
        // TODO: 24/09/2021
        //   Do I need to add checks here? even though only I can call this class?
        return new Level(this.rows, this.cols, this.terrainLayout, this.entityLayout, info);
    }

    public int getRows() {
        return rows;
    }

    public int getCols() {
        return cols;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public String getTerrainLayout() {
        return terrainLayout;
    }

    public String getEntityLayout() {
        return entityLayout;
    }

    public String getInfo() {
        return info;
    }
}