package nz.ac.vuw.ecs.swen225.gp21.app.controllers;

import java.awt.CardLayout;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import nz.ac.vuw.ecs.swen225.gp21.persistency.PersistException;

public class GUIController extends GUI {
	
	private final JFileChooser fileChooser = new JFileChooser();
	
	public GUIController() {
		super();
	}

	@Override
	public void run() {
		super.run();
		addListeners();
	}
	
	private void addListeners() {
		// Menu bar new game button action
		frame.fileExitToMenu.addActionListener((ae) -> {
			
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
	            try {
					this.persister.loadGame(selectedFile, world);
					this.initialise();
					
				} catch (PersistException e) {
					warning(e.getMessage());
				}
	        } else {
	            report("Cancelled file selection");
	        }
			
		});
		
		homePage.loadGameButton.addActionListener((ae) -> {
			
			int approval = fileChooser.showOpenDialog(fileChooser);
	        if (approval == JFileChooser.APPROVE_OPTION) {
	            File selectedFile = fileChooser.getSelectedFile();
	            
	            report("Attempting to load " + selectedFile.toPath());
	            try {
					this.persister.loadGame(selectedFile, world);
					this.initialise();
				} catch (PersistException e) {
					warning(e.getMessage());
				}
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
			
			try {
				persister.saveCurrentGame(new File(""), world);
			} catch (PersistException e) {
				warning(e.getMessage());
			}
			
			// Close all threads and exit.
			System.exit(0);
		});
		
		frame.fileSaveState.addActionListener((ae) -> {
			try {
				persister.saveCurrentGame(new File(""), world);
			} catch (PersistException e) {
				warning(e.getMessage());
			}
		});
		
		frame.fileLoadReplay.addActionListener((ae) -> {
			
			int approval = fileChooser.showOpenDialog(fileChooser);
	        if (approval == JFileChooser.APPROVE_OPTION) {
	            File selectedFile = fileChooser.getSelectedFile();
	            
	            report("Loading " + selectedFile.toPath());
            	recorder.load(selectedFile);
	        } else {
	            report("Cancelled file selection");
	        }
	        
		});
		
		frame.fileSaveReplay.addActionListener((ae) -> {
			if (recorder.save()) {
				report("Save successful");
			} else {
				warning("Save unsuccessful");
			}
		});
		
		// Home page new game button action
		homePage.newGameButton.addActionListener((ae) -> {
			
			try {
				persister.loadLevel(homePage.levelChooser.getSelectedIndex() + 1, world);
				this.initialise();
			} catch (PersistException e) {
				warning(e.getMessage());
				return;
			}
			
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
	private void report(String message) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JOptionPane.showMessageDialog(f, message);
	}
	
	/**
	 * Bring up a dialog to warn the user of something.
	 * @param message
	 */
	public void warning(String message) {
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JOptionPane.showMessageDialog(f, message, "Warning", JOptionPane.ERROR_MESSAGE);
	}
	
	
	
	/* ****************************
	 * GUI ACTIONS
	 * ****************************
	 */

	@Override
	public void chipCollectedTrans() {
		// TODO Auto-generated method stub
		
	}

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
	public void playerOpenedADoorTrans() {
		// TODO Auto-generated method stub
		
	}
	
}
