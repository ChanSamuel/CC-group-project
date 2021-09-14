package nz.ac.vuw.ecs.swen225.gp21.persistency.tests;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import nz.ac.vuw.ecs.swen225.gp21.domain.Domain;
import nz.ac.vuw.ecs.swen225.gp21.domain.Level;
import nz.ac.vuw.ecs.swen225.gp21.domain.TestWorld;
import nz.ac.vuw.ecs.swen225.gp21.domain.World;
import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;
import nz.ac.vuw.ecs.swen225.gp21.persistency.XMLParser;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class XMLParserObjectTests {

    // If the TestObject changes its constructor to private, 'saving' it doesn't work
    // If the TestObject doesn't have getters, 'saving' it doesn't work

    @Test
    @Order(1)
    public void saveTestObject() throws PersistException {
        TestObject testObject = new TestObject(Arrays.asList("Hello", "World"), "test1", 8, 9);
        File f = new File("test_testobject_save.xml");
        XMLParser<TestObject> parser = new XMLParser<>(new XmlMapper(), TestObject.class);
        parser.save(f, testObject);
    }

    @Test
    @Order(2)
    public void loadTestObject() throws PersistException, FileNotFoundException {
        File f = new File("test_testobject_save.xml");
        XMLParser<TestObject> parser = new XMLParser<>(new XmlMapper(), TestObject.class);
        TestObject testObject = parser.load(new FileInputStream(f));
        assertEquals("Hello", testObject.getStringList().get(0));
    }

    @Test
    public void saveDomain() throws PersistException {
        Domain testDomain = new TestWorld();
        File f = new File("test_domain_save.xml");
        XMLParser<Domain> parser = new XMLParser<>(new XmlMapper(), Domain.class);
        parser.save(f, testDomain);
    }

    @Test
    public void saveTestWorld() throws PersistException {
        TestWorld testWorld = new TestWorld();
        File f = new File("test_testworld_save.xml");
        XMLParser<TestWorld> parser = new XMLParser<>(new XmlMapper(), TestWorld.class);
        parser.save(f, testWorld);
    }

}

