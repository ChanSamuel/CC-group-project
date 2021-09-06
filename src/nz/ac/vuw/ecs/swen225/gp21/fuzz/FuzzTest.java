package nz.ac.vuw.ecs.swen225.gp21.fuzz;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

public class FuzzTest {
    private final Robot robot = new Robot();
    private final Random random = new Random();

    public FuzzTest() throws AWTException {
    }

    public void generateKeyPress() throws AWTException {

        switch (random.nextInt(4)) {
            case 0 -> robot.keyPress(KeyEvent.VK_LEFT);
            case 1 -> robot.keyPress(KeyEvent.VK_RIGHT);
            case 2 -> robot.keyPress(KeyEvent.VK_UP);
            case 3 -> robot.keyPress(KeyEvent.VK_DOWN);
        }

    }

    public static void main(String[] args) throws AWTException {
        FuzzTest fuzzTest = new FuzzTest();
        for (int i=0; i<100; ++i) {
            fuzzTest.generateKeyPress();
        }


    }


}
