package nz.ac.vuw.ecs.swen225.gp21.fuzz;

import nz.ac.vuw.ecs.swen225.gp21.app.controllers.FuzzController;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;
import java.util.Random;

import static java.time.Duration.ofMillis;
import static java.time.Duration.ofMinutes;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FuzzTest {
    private final Random random = new Random();

    @Test
    public void test1(){
        FuzzController fc = new FuzzController();
        fc.newGame(1);
        assertTimeout(ofMinutes(1), () -> {
            // Perform task that takes less than 1 minute.
            while (true) {
                switch (random.nextInt(4)) {
                    case 0 -> fc.moveLeft();
                    case 1 -> fc.moveRight();
                    case 2 -> fc.moveUp();
                    case 3 -> fc.moveDown();
                }

            }
        });



    }

    @Test
    public void test2(){
        return; //pass
    }


}
