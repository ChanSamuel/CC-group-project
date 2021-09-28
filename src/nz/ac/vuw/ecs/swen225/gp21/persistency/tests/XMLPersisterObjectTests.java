package nz.ac.vuw.ecs.swen225.gp21.persistency.tests;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;
import nz.ac.vuw.ecs.swen225.gp21.persistency.XMLPersister;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * TODO
 *  I maaaaay be able to delete this class. Check code coverage from other XML tests
 *
 * These tests test the functionality of the XmlPersister class by saving and loading a TestObject
 */
public class XMLPersisterObjectTests {

    @Test
    public void saveAndLoadTestObject() throws PersistException, FileNotFoundException {
        saveTestObject();
        loadTestObject();
    }

    public void saveTestObject() throws PersistException {
        TestObject testObject = new TestObject(Arrays.asList("Hello", "World"), "test1", 8, 9);
        File f = new File("test_testobject_save.xml");
        XMLPersister parser = new XMLPersister(new XmlMapper());
        parser.save(f, testObject);
    }

    public void loadTestObject() throws PersistException, FileNotFoundException {
        File f = new File("test_testobject_save.xml");
        XMLPersister parser = new XMLPersister(new XmlMapper());
        TestObject testObject = parser.load(new FileInputStream(f), TestObject.class);
        assertEquals("Hello", testObject.getStringList().get(0));
    }

    static class TestObject {
        private String string1, string2 = "some default string";
        private int int1, int2;
        private List<String> stringList;

        public TestObject(List<String> stringList, String s1, int i1, int i2) {
            this.stringList = stringList;
            this.string1 = s1;
            this.int1 = i1;
            this.int2 = i2;
        }

        private TestObject() {
        }

        public List<String> getStringList() {
            return stringList;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TestObject that = (TestObject) o;
            return int1 == that.int1 && int2 == that.int2 && string1.equals(that.string1) && string2.equals(that.string2) && stringList.equals(that.stringList);
        }

        @Override
        public int hashCode() {
            return Objects.hash(string1, string2, int1, int2, stringList);
        }
    }
}


