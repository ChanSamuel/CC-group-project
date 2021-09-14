package nz.ac.vuw.ecs.swen225.gp21.persistency.tests;

import java.util.List;
import java.util.Objects;

public class TestObject {

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

    public TestObject() {
    }

    public String getString1() {
        return string1;
    }

    public String getString2() {
        return string2;
    }

    public int getInt1() {
        return int1;
    }

    public int getInt2() {
        return int2;
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
