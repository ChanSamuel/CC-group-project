package test.nz.ac.vuw.ecs.swen225.gp21.fuzz;

import nz.ac.vuw.ecs.swen225.gp21.app.controllers.FuzzController;
import org.junit.jupiter.api.Test;
import java.util.Random;

public class FuzzTest {
    private final FuzzController fc = new FuzzController();

    /**
     * Generates random input so methods are called in the app module.
     * Plays level 1.
     */
    @Test
    public void test1(){
        fc.newGame(1);
        doRandomMovement(fc, 10); // 10 seconds.
    }

    /**
     * Generates random input so methods are called in the app module.
     * Plays level 2.
     */
    @Test
    public void test2(){
        fc.newGame(2);
        doRandomMovement(fc, 10); // 10 seconds.
    }


    /**
     * Generates random movement for a length of time
     * @param fc - The FuzzController object.
     * @param seconds - The length of time to generate movement in seconds.
     */
    void doRandomMovement(FuzzController fc, long seconds) {
        long time = System.currentTimeMillis();
        while (System.currentTimeMillis() < time + seconds * 1000) {
            switch (getRandomInt()) {
                case 0: fc.moveUp(); break;
                case 1: fc.moveDown(); break;
                case 2: fc.moveLeft(); break;
                case 3: fc.moveRight(); break;
            }
        }
    }

    /**
     * Generates a random int between 0 and 3 (inclusive).
     * Function created because SpotBugs was complaining about using
     * a Random object.
     * @return - and int between 0 and 3 (inclusive)
     */
    private int getRandomInt() {
        double r = Math.random();
        if (r < 0.25) return 0;
        if (r < 0.50) return 1;
        if (r < 0.75) return 2;
        return 3;
    }


}
