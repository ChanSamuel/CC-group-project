package nz.ac.vuw.ecs.swen225.gp21.persistency;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.Level;

import java.io.File;
import java.io.InputStream;
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
    private static final List<Integer> levelsThatExist = Arrays.asList(1); // todo add as we have levels

    /**
     * LevelMemento that represents level one todo update to relevant info
     */
    private static final LevelMemento levelOne = new LevelMemento(16, 16,
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
     * Saves level one LevelMemento field to an XML
     * @throws PersistException that will provide an informative message that should be shown to the user
     */
    public static void saveLevelOne(XmlMapper mapper) throws PersistException {
        XMLPersister parser = new XMLPersister(mapper);
        parser.save(new File("src/nz/ac/vuw/ecs/swen225/gp21/persistency/levels/level1.xml"), levelOne);
    }

    /**
     * Saves level two LevelMemento field to an XML
     * @throws PersistException that will provide an informative message that should be shown to the user
     */
    public static void saveLevelTwo(XmlMapper mapper) throws PersistException {
//          TODO:
//        XMLPersister parser = new XMLPersister(mapper);
//        parser.save(new File("src/nz/ac/vuw/ecs/swen225/gp21/persistency/levels/level2.xml"), levelTwo);
    }

    /**
     * Converts a LevelMemento into a Level. Since LevelMemento is an inner class, an Object type object is accepted as
     * the parameter and cast into a LevelMemento is it is an instance of one.
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
     * @param levelNumber which level to load
     * @return that will provide an informative message that should be shown to the user
     * @throws PersistException that will provide an informative message that should be shown to the user
     */
    private static Level getLevel(int levelNumber) throws PersistException {
        if (!levelsThatExist.contains(levelNumber)) {
            throw new PersistException("Level " + levelNumber + " does not exist");
        }

        XMLPersister parser = new XMLPersister(xmlMapper);
        InputStream is = LevelHandler.class.getResourceAsStream
                ("/nz/ac/vuw/ecs/swen225/gp21/persistency/levels/level" + levelNumber + ".xml");

        LevelMemento levelMemento = parser.load(is, LevelMemento.class);
        return mementoToLevel(levelMemento);
    }

    /**
     * Captures a Level state so it can be written to an XML file. Only used by the LevelHandler class.
     */
    static class LevelMemento {

        /**
         * Captures the rows and columns of a level's board.
         */
        private int rows, cols;

        /**
         * Captures the terrain and entities that are on the levels board.
         */
        private String terrainLayout, entityLayout;

        /**
         * Captures what information is shown to the user when the level's info tile is activated.
         */
        private String info;

        /**
         * Constructor for a Level Memento which captures a level.
         * @param rows number of rows in levels board
         * @param cols number of cols in levels board
         * @param terrainLayout the terrain of the level board
         * @param entityLayout the entities in the levels board
         * @param info the info for the levels info tile
         */
        public LevelMemento(int rows, int cols, String terrainLayout, String entityLayout, String info) {
            this.rows = rows;
            this.cols = cols;
            this.terrainLayout = terrainLayout;
            this.entityLayout = entityLayout;
            this.info = info;
        }

        /**
         * Default constructor to allow for object to be parsed from an XML.
         */
        private LevelMemento() {}

        /**
         * Getter for rows field.
         *
         * @return rows
         */
        public int getRows() {
            return rows;
        }

        /**
         * Getter for cols field.
         *
         * @return cols
         */
        public int getCols() {
            return cols;
        }

        /**
         * Getter for terrain field.
         *
         * @return terrains
         */
        public String getTerrainLayout() {
            return terrainLayout;
        }

        /**
         * Getter for entities field.
         *
         * @return entities
         */
        public String getEntityLayout() {
            return entityLayout;
        }

        /**
         * Getter for info field.
         *
         * @return info for info tile
         */
        public String getInfo() {
            return info;
        }
    }
}