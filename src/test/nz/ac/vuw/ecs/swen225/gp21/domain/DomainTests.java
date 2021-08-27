package test.nz.ac.vuw.ecs.swen225.gp21.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import nz.ac.vuw.ecs.swen225.gp21.domain.*;
/**
 * These tests test the functionality of the Domain package
 * @author Benjamin
 *
 */
class DomainTests {
	/**
	 * Try creating the world object
	 * No exceptions should be thrown
	 */
	@Test
	void createWorld() {
		boolean exception = false;
		try {
			World w = new World();
			System.out.println(w.toString());
		} catch (Exception e) {
			exception = true;
		}
		assertFalse(exception);
		System.out.println("\ntest one complete\n");
	}

	/**
	 * Try to add some movement commands to the command queue
	 * No exceptions should occur.
	 */
	@Test
	void addMovePlayer() {
		boolean exception = false;
		try {
			World w = new World();
			System.out.println(w.toString());
			w.moveChipDown();
			System.out.println(w.toString());
			w.moveChipRight();
			System.out.println(w.toString());
			w.moveChipLeft();
			w.moveChipUp();
		} catch (Exception e) {
			exception = true;
		}
		assertFalse(exception);
		System.out.println("\ntest two complete\n");
	}
	
	/**
	 * Try to simulate the world
	 * for one tick. No exceptions 
	 * should be thrown
	 */
	@Test
	void trySimulate() {
		boolean exception = false;
		try {
			World w = new World();
			System.out.println(w.toString());
			w.moveChipDown();
			System.out.println(w.toString());
			w.update(200);
			System.out.println(w.toString());
		} catch (Exception e) {
			exception = true;
		}
		assertFalse(exception);
		System.out.println("\ntest three complete\n");
	}
	
	/**
	 * Simulate the world several times with no moves to execute
	 * No exceptions should be thrown
	 */
	@Test
	void tryMultipleSimulate() {
		boolean exception = false;
		try {
			World w = new World();
			for(int updates = 0; updates < 10; ++updates) w.update(200);
			System.out.println(w.toString());
		} catch (Exception e) {
			exception = true;
		}
		assertFalse(exception);
		System.out.println("\ntest four complete\n");
	}
	
	/**
	 * Try to move up when already in the corner
	 * No Exceptions should occur
	 */
	@Test
	void tryMoveUp() {
		boolean exception = false;
		try {
			World w = new World();
			w.moveChipUp();
			w.update(200);
			System.out.println(w.toString());
		} catch (Exception e) {
			exception = true;
		}
		assertFalse(exception);
		System.out.println("\ntest five complete\n");
	}
	/**
	 * Try moving to the exit
	 * No exceptions should be thrown
	 */
	@Test
	void tryReachExit() {
		boolean exception = false;
		try {
			World w = new World();
			for(int row = 1; row < 10; row++) { 
				w.moveChipDown(); 
				w.update(200); 
			}
			System.out.println(w.toString());
			for(int col = 1; col < 10; col++) { 
				w.moveChipRight(); 
				w.update(200); 
			}
			System.out.println(w.toString());
			//assertTrue(w.isGameComplete());
		} catch (Exception e) {
			e.printStackTrace();
			exception = true;
		}
		assertFalse(exception);
		System.out.println("\ntest six complete\n");
	}
}
