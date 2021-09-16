package nz.ac.vuw.ecs.swen225.gp21.persistency.tests;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.persistency.ConcretePersister;
import nz.ac.vuw.ecs.swen225.gp21.persistency.LevelHandler;
import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;
import nz.ac.vuw.ecs.swen225.gp21.persistency.XMLParser;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class LevelHandlingTests {

    @Test
    public void saveLevel1() throws PersistException {
        String tiles = "";
        tiles += "################";
        tiles += "#..........#...#";
        tiles += "#..........#.g.#";
        tiles += "#...########...#";
        tiles += "#...#......#...#";
        tiles += "#...#.a..g.#.c.#";
        tiles += "#...#......A...#";
        tiles += "#...#.c....#...#";
        tiles += "#...G...##G#####";
        tiles += "#...#...#......#";
        tiles += "#...#...#...c..#";
        tiles += "#...#...#..###.#";
        tiles += "#.c.#...#..XE#.#";
        tiles += "#...#...#..###.#";
        tiles += "#...#####......#";
        tiles += "################";

        String entities = "";
        entities +="................";
        entities +="................";
        entities +="................";
        entities +="................";
        entities +="................";
        entities +="................";
        entities +="................";
        entities +="................";
        entities +="................";
        entities +="................";
        entities +="................";
        entities +="................";
        entities +="......C.........";
        entities +="................";
        entities +="................";
        entities +="................";

        LevelHandler levelOne = new LevelHandler(16, 16, 1, entities, tiles);
        File fileToSave = new File("level1.xml");
        new ConcretePersister().saveLevel(fileToSave, levelOne);
    }

    @Test
    public void loadSavedLevel() throws FileNotFoundException, PersistException {
        InputStream is = getClass().getResourceAsStream("/nz/ac/vuw/ecs/swen225/gp21/persistency/levels/level1.xml");
        LevelHandler levelHandler = new XMLParser(new XmlMapper()).load(is, LevelHandler.class);
        Level loadedLevel = LevelHandler.toLevel(levelHandler);
        System.out.println(loadedLevel.rows);
    }
}
