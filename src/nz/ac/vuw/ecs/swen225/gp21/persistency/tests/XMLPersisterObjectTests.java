package nz.ac.vuw.ecs.swen225.gp21.persistency.tests;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.SerializationFeature;
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

public class XMLPersisterObjectTests {

    static XmlMapper mapper;
    static {
        mapper = new XmlMapper();
        mapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY); //fixme?
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS); //fixme?
    }

    @Test
    public void saveTestObject() throws PersistException {
        TestObject testObject = new TestObject(Arrays.asList("Hello", "World"), "test1", 8, 9);
        File f = new File("test_testobject_save.xml");
        XMLPersister parser = new XMLPersister(mapper);
        parser.save(f, testObject);
    }

    @Test
    public void loadTestObject() throws PersistException, FileNotFoundException {
        File f = new File("test_testobject_save.xml");
        XMLPersister parser = new XMLPersister(mapper);
        TestObject testObject = parser.load(new FileInputStream(f), TestObject.class);
        assertEquals("Hello", testObject.getTheList().get(0));
    }
}

class TestObject {
    private String string1;
    private String string2 = "some default string";
    private int int1;
    private int int2;
    private List<String> stringList;

    public TestObject(List<String> stringList, String s1, int i1, int i2) {
        this.stringList = stringList;
        this.string1 = s1;
        this.int1 = i1;
        this.int2 = i2;
    }

    private TestObject() {
    }

    public List<String> getTheList() {
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

