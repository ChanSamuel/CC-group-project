package nz.ac.vuw.ecs.swen225.gp21.app.controllers;

import java.awt.CardLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;

public class GUIController extends GUI {
	
	private final JFileChooser fileChooser = new JFileChooser();
	
	/**
	 * A controller side flag to know if the program is paused or not.
	 * GameLoop has it's own flag which we must set.
	 */
	private boolean isPaused = false;
	
	/**
	 * A controller side flag to know if the program is in auto replay mode.
	 * GameLoop has it's own flag which we must set.
	 */
	private boolean isAutoPlay = false;
	
	
	public GUIController() {
		super();
	}

	@Override
	public void run() {
		super.run();
		addListeners();
	}
	
	private void addListeners() {
		
		gamePage.replayPanel.pausePlay.addActionListener((ae) -> {
			if (isPaused) {
				// Need to request back focus otherwise key listener will go numb.
				// This may be due to an issue regarding acquiring the lock for Keyboard.
				// FIXME: bug here regarding double pauses.
				frame.requestFocusInWindow();
				this.resumeGame();
				this.isPaused = false;
			} else {
				this.pauseGame();
				this.isPaused = true;
			}
		});
		
		gamePage.replayPanel.autoPlay.addActionListener((ae) -> {
			this.isAutoPlay = !this.isAutoPlay;
			this.setAutoPlay(this.isAutoPlay);
			
			// Request back focus for key listener.
			frame.requestFocusInWindow();
		});
		
		// Menu bar exit to menu button action
		frame.fileExitToMenu.addActionListener((ae) -> {
			
			this.haltGame();
			 
			// Disable buttons which involve saving when we go back to main menu.
			frame.fileExitSave.setEnabled(false);
			frame.fileSaveState.setEnabled(false);
			frame.fileSaveReplay.setEnabled(false);
			
			CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
			cl.show(frame.getContentPane(), "Home page");
		});
		
		frame.fileLoadGame.addActionListener((ae) -> {
			
			int returnVal = fileChooser.showOpenDialog(fileChooser);

	        if (returnVal == JFileChooser.APPROVE_OPTION) {
	            File selectedFile = fileChooser.getSelectedFile();
	            
	            report("Attempting to load " + selectedFile.toPath());
	            
	            this.loadGame(selectedFile);
	            
	        } else {
	            report("Cancelled file selection");
	        }
			
		});
		
		homePage.loadGameButton.addActionListener((ae) -> {
			
			int approval = fileChooser.showOpenDialog(fileChooser);
	        if (approval == JFileChooser.APPROVE_OPTION) {
	            File selectedFile = fileChooser.getSelectedFile();
	            
	            report("Attempting to load " + selectedFile.toPath());
	            
	            this.loadGame(selectedFile);
	            
	        } else {
	            report("Cancelled file selection");
	        }
			
		});
		
		// Menu bar quit game action
		frame.fileQuit.addActionListener((ae) -> {
			// Close all threads and exit.
			System.exit(0);
		});
		
		// Menu bar quit and save action
		frame.fileExitSave.addActionListener((ae) -> {
			int approval = fileChooser.showSaveDialog(fileChooser);
	        if (approval == JFileChooser.APPROVE_OPTION) {
	            File selectedFile = fileChooser.getSelectedFile();
	            
	            report("Loading " + selectedFile.toPath());
            	this.exitAndSave(selectedFile);
	        } else {
	            report("Cancelled file selection");
	        }
		});
		
		frame.fileSaveState.addActionListener((ae) -> {
			int approval = fileChooser.showSaveDialog(fileChooser);
	        if (approval == JFileChooser.APPROVE_OPTION) {
	            File selectedFile = fileChooser.getSelectedFile();
	            
	            report("Loading " + selectedFile.toPath());
            	this.saveGameState(selectedFile);
	        } else {
	            report("Cancelled file selection");
	        }
		});
		
		frame.fileLoadReplay.addActionListener((ae) -> {
			
			int approval = fileChooser.showOpenDialog(fileChooser);
	        if (approval == JFileChooser.APPROVE_OPTION) {
	            File selectedFile = fileChooser.getSelectedFile();
	            
	            report("Loading " + selectedFile.toPath());
            	this.loadReplay(selectedFile);
	        } else {
	            report("Cancelled file selection");
	        }
	        
		});
		
		frame.fileSaveReplay.addActionListener((ae) -> {
			int approval = fileChooser.showSaveDialog(fileChooser);
	        if (approval == JFileChooser.APPROVE_OPTION) {
	            File selectedFile = fileChooser.getSelectedFile();
	            
	            report("Loading " + selectedFile.toPath());
            	this.saveReplay(selectedFile);
	        } else {
	            report("Cancelled file selection");
	        }
		});
		
		// Home page new game button action
		homePage.newGameButton.addActionListener((ae) -> {
			this.newGame(((String) homePage.levelChooser.getSelectedItem()));
			
			// Re-enable save buttons for game page.
			frame.fileExitSave.setEnabled(true);
			frame.fileSaveState.setEnabled(true);
			frame.fileSaveReplay.setEnabled(true);
			
			CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
			cl.show(frame.getContentPane(), "Game page");
		});
		
	}
	
	/* ***************************
	 * Notification alert methods.
	 * ***************************
	 */
	
	/**
	 * Bring up a dialog to inform the user.
	 * @param message
	 */
	protected void report(String message) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JOptionPane.showMessageDialog(f, message);
	}
	
	/**
	 * Bring up a dialog to warn the user of something.
	 * @param message
	 */
	protected void warning(String message) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JOptionPane.showMessageDialog(f, message, "Warning", JOptionPane.ERROR_MESSAGE);
	}
	
	
	/* ****************************
	 * GUI ACTIONS
	 * ****************************
	 */

	@Override
	public void enteredExitTrans() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void enteredInfoTrans() {
		report("Arbitrary informative message.");
	}

	@Override
	public void leftInfoTrans() {
		report("You have left an tile");
		
	}

	@Override
	public void playerLostTrans() {
		report("You have lost the game\nReturning to main menu");
		
	}

	@Override
	public void playerGainedItemTrans() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerConsumedItemTrans() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerOpenedDoorTrans() {
		// TODO Auto-generated method stub
		
	}
	
}
