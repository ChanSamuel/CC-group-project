package nz.ac.vuw.ecs.swen225.gp21.app.controllers;

import java.awt.CardLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class GUIController extends GUI {
	
	private final JFileChooser fileChooser = new JFileChooser();
	
	/**
	 * A controller side flag to know if the program is paused or not.
	 * When the corresponding action is executed, GameLoop sets it's own flag and this flag.
	 */
	private volatile boolean isPaused = false;
	
	/**
	 * A controller side flag to know if the program is in auto replay mode.
	 * When the corresponding action is executed, GameLoop sets it's own flag and this flag.
	 */
	private volatile boolean isAutoPlay = false;
	
	/**
	 * A controller side flag to know if the program is in replay mode.
	 * When the corresponding action is executed, GameLoop sets it's own flag and this flag.
	 */
	private volatile boolean isReplay = false;
	
	
	public GUIController() {
		super();
	}

	@Override
	public void run() {
		super.run();
		addListeners();
	}
	
	private void addListeners() {
		
		gamePage.replayPanel.stepBackward.addActionListener((ae) -> {
			this.backTick();
			requestFocus();
		});
		gamePage.replayPanel.stepForward.addActionListener((ae) -> {
			this.forwardTick();
			requestFocus();
		});
		
		gamePage.replayPanel.setSpeedField.addActionListener((ae) -> {
			this.changeSpeed(gamePage.replayPanel.setSpeedField.getText());
		});
		
		gamePage.replayPanel.pausePlay.addActionListener((ae) -> {
			this.togglePause();
		});
		
		gamePage.replayPanel.autoPlay.addActionListener((ae) -> {
			this.toggleAutoPlay();
		});
		
		// Menu bar exit to menu button action
		frame.fileExitToMenu.addActionListener((ae) -> {
			 this.exitToMenu();
			 
			// Disable buttons which involve saving when we go back to main menu.
			frame.fileExitSave.setEnabled(false);
			frame.fileSaveState.setEnabled(false);
			frame.fileSaveReplay.setEnabled(false);
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

	@Override
	protected void inform(String message) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void requestFocus() {
		// Request back focus for key listener.
		frame.requestFocusInWindow();
	}
	
	
	/* ****************************
	 * GUI SPECIFIC GETTERS.
	 * ****************************
	 */
	
	/**
	 * Gets the JFrame of this GUI.
	 * @return the JFrame.
	 */
	public JFrame getFrame() {
		return this.frame;
	}
	
}
