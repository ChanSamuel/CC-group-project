package nz.ac.vuw.ecs.swen225.gp21.persistency;

import nz.ac.vuw.ecs.swen225.gp21.domain.Level;

public class LevelHandler {

    private int rows, cols, levelNumber;
    private String terrainLayout, entityLayout;

    public LevelHandler(int rows, int cols, int levelNumber, String terrainLayout, String entityLayout) {
        this.rows = rows;
        this.cols = cols;
        this.levelNumber = levelNumber;
        this.terrainLayout = terrainLayout;
        this.entityLayout = entityLayout;
    }

    public LevelHandler() {
    }

    public static Level toLevel(LevelHandler levelHandler) {
        // TODO do checks here
        return new Level(levelHandler.rows, levelHandler.cols, levelHandler.terrainLayout, levelHandler.entityLayout, "No Info");
    }
}
