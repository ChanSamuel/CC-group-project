package nz.ac.vuw.ecs.swen225.gp21.persistency;

/**
 * Captures a Level state so it can be written to an XML file. Only used by the LevelHandler class.
 */
class LevelMemento {

    /**
     * LevelMemento that represents level one todo update to relevant info
     */
    protected static final LevelMemento levelOne = new LevelMemento(16, 16,
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
     * todo - change this. At the moment it is just a copy of level one....
     */
    protected static final LevelMemento levelTwo = new LevelMemento(16, 16,
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
                    + "................", "Level Two information");

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
         *
         * @param rows          number of rows in levels board
         * @param cols          number of cols in levels board
         * @param terrainLayout the terrain of the level board
         * @param entityLayout  the entities in the levels board
         * @param info          the info for the levels info tile
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
        private LevelMemento() {
        }

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

