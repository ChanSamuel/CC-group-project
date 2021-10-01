package test.nz.ac.vuw.ecs.swen225.gp21.fuzz;

import nz.ac.vuw.ecs.swen225.gp21.app.controllers.FuzzController;
import org.junit.jupiter.api.Test;
import java.util.Random;

public class FuzzTest {
    private final Random random = new Random();
    private final FuzzController fc = new FuzzController();

    @Test
    public void test1(){
        fc.newGame(1);
        doRandomMovement(fc, 10); // 10 seconds of random movement.
    }

    @Test
    public void test2(){
        fc.newGame(2);
        doRandomMovement(fc, 20); // 20 seconds of random movement.
    }


    void doRandomMovement(FuzzController fc, long seconds) {
        long time = System.currentTimeMillis();
        while (System.currentTimeMillis() < time + seconds * 1000) {
            switch (random.nextInt(4)) {
                case 0: fc.moveUp(); break;
                case 1: fc.moveDown(); break;
                case 2: fc.moveLeft(); break;
                case 3: fc.moveRight(); break;
            }
        }

    }

}
