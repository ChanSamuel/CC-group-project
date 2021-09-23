package nz.ac.vuw.ecs.swen225.gp21.fuzz;

import nz.ac.vuw.ecs.swen225.gp21.app.controllers.FuzzController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.awt.event.KeyEvent;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class FuzzTest {
    private final Random random = new Random();

    @Test
    public void test1(){
        FuzzController fc = new FuzzController();
        fc.newGame(1);
        long time = System.currentTimeMillis();
        System.out.println(time);
        long seconds = 10;
        while (System.currentTimeMillis() < time + seconds * 1000) {
            switch (random.nextInt(4)) {
                case 0 -> fc.moveUp();
                case 1 -> fc.moveDown();
                case 2 -> fc.moveLeft();
                case 3 -> fc.moveRight();
            }
        }



    }

    @Test
    public void test2(){
        return; //pass
    }


}
