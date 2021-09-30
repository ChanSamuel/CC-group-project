package nz.ac.vuw.ecs.swen225.gp21.app.controllers;


import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import nz.ac.vuw.ecs.swen225.gp21.app.DisplayHelpAction;
import nz.ac.vuw.ecs.swen225.gp21.app.NextLevelAction;
import nz.ac.vuw.ecs.swen225.gp21.app.PlayerLostAction;
import nz.ac.vuw.ecs.swen225.gp21.domain.Item;
import nz.ac.vuw.ecs.swen225.gp21.renderer.SoundType;
import nz.ac.vuw.ecs.swen225.gp21.renderer.WrapperJPanel;

public class GUIController extends GUI {
	
	/**
	 * The file chooser object.
	 * This has the ability to choose existing files and create new files.
	 */
	private final JFileChooser fileChooser = new JFileChooser();
	
	public GUIController() {
		super();
	}

	@Override
	public void run() {
		super.run();
		addListeners();
		KeyboardConfigurer.addListener(homePage, this);
		KeyboardConfigurer.addListener(gamePage, this);
	}
	
	private void addListeners() {
		
		// Add a mouse listener to the frame so that we can always click to request back focus.
		frame.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				requestFocus();
			}
		});
		
		// ----- REPLAY PANEL -----
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
			requestFocus();
		});
		
		gamePage.replayPanel.pausePlay.addActionListener((ae) -> {
			this.togglePause();
			requestFocus();
		});
		
		gamePage.replayPanel.autoPlay.addActionListener((ae) -> {
			this.toggleAutoPlay();
			requestFocus();
		});
		
		// ----- MENU BAR -----
		
		// Menu bar exit to menu button action
		frame.fileExitToMenu.addActionListener((ae) -> {
			this.exitToMenu();
			 
			// Disable buttons which involve saving when we go back to main menu.
			frame.fileExitSave.setEnabled(false);
			frame.fileSaveState.setEnabled(false);
			frame.fileSaveReplay.setEnabled(false);
		});
		
		KeyStroke ctrlE = KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK);
		frame.fileExitToMenu.setAccelerator(ctrlE);
		
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
		
		// Setting Ctrl + R to open the file selector to load game.
		KeyStroke ctrlR = KeyStroke.getKeyStroke(KeyEvent.VK_R, KeyEvent.CTRL_DOWN_MASK);
		frame.fileLoadGame.setAccelerator(ctrlR);
		
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
		
		KeyStroke ctrlX = KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK);
		frame.fileQuit.setAccelerator(ctrlX);
		
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
		
		KeyStroke ctrlS = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
		frame.fileExitSave.setAccelerator(ctrlS);
		
		
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
		
		KeyStroke ctrlU = KeyStroke.getKeyStroke(KeyEvent.VK_U, KeyEvent.CTRL_DOWN_MASK);
		frame.fileSaveState.setAccelerator(ctrlU);
		
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
		
		KeyStroke ctrlO = KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK);
		frame.fileLoadReplay.setAccelerator(ctrlO);
		
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
		
		KeyStroke ctrlY = KeyStroke.getKeyStroke(KeyEvent.VK_Y, KeyEvent.CTRL_DOWN_MASK);
		frame.fileSaveReplay.setAccelerator(ctrlY);
		
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
		this.gamePage.textArea.append(message + "\n");
		gamePage.textArea.setCaretPosition(gamePage.textArea.getDocument().getLength());
	}
	
	/* ****************************************************
	 * METHODS WHICH ARE CALLED BY THE WORLD IMPLEMENTATION
	 * ****************************************************
	 */
	
	@Override
	protected void enteredExit() {
		WrapperJPanel.playSound(SoundType.ENTER_EXIT);
		issue(new NextLevelAction());
	}
	
	@Override
	protected void enteredInfo(String msg) {
		WrapperJPanel.playSound(SoundType.SHOW_INFO);
		report(msg);
	}
	
	@Override
	protected void playerLost() {
		WrapperJPanel.playSound(SoundType.GAME_OVER);
		issue(new PlayerLostAction());
	}

	@Override
	protected void playerGainedItem(List<Item> playerInventory, Item item) {
		List<Item> inventory = playerInventory;
		String s = "";
		for (int i = 0; i < inventory.size(); i++) {
			if (i == inventory.size() - 1) {
				s += inventory.get(i).toString();
			} else {
				s += inventory.get(i).toString() + "\n";
			}
		}
		WrapperJPanel.playSound(SoundType.PICK_UP_A_KEY);
		inform("You have gained a " + item.getColour() + " Key" + ".\n" + 
				"Your current inventory is:\n" + s);
	}
	
	@Override
	protected void playerConsumedItem(List<Item> playerInventory, Item item) {
		List<Item> inventory = playerInventory;
		String s = "";
		for (int i = 0; i < inventory.size(); i++) {
			if (i == inventory.size() - 1) {
				s += inventory.get(i).toString();
			} else {
				s += inventory.get(i).toString() + ", ";
			}
		}
		
		if (inventory.isEmpty()) {
			inform("You have consumed a " + item.getColour() + " key " + ".\n" + 
					"Your current inventory is empty.");
			return;
		}
		
		inform("You have consumed a " + item.getColour() + " key " + ".\n" + 
				"Your current inventory is:\n" + s);
	}

	@Override
	protected void openedDoor() {
		WrapperJPanel.playSound(SoundType.DOOR_OPEN);
	}

	@Override
	protected void collectedChip(int remainingChips) {
		WrapperJPanel.playSound(SoundType.PICK_UP_A_CHIP);
		inform("Remaining Chips: " + remainingChips);
	}
	
	@Override
	protected void objectTeleported() {
		WrapperJPanel.playSound(SoundType.TELEPORT);
		
	}
	
	@Override
	protected void objectPushed() {
		WrapperJPanel.playSound(SoundType.PUSH_BLOCK);
	}
	
	/* ***********************
	 * OTHER INHERITED METHODS
	 * ***********************
	 */
	
	@Override
	protected void updateTimerOperation(int timeLeft) {
		try {
			SwingUtilities.invokeAndWait(() -> {
				gamePage.timeLeftLabel.setText(String.valueOf(timeLeft));
			});
		} catch (InvocationTargetException e) {
			warning("Timer update was interrupted:\n" + e.getMessage());
		} catch (InterruptedException e) {
			warning("Timer update was interrupted:\n" + e.getMessage());
		}
	}
	
	@Override
	protected void pauseOperation() {
		try {
			SwingUtilities.invokeAndWait(() -> {
				gamePage.timeLeftLabel.setText("Paused");
			});
		} catch (InvocationTargetException e) {
			warning("Timer update was interrupted:\n" + e.getMessage());
		} catch (InterruptedException e) {
			warning("Timer update was interrupted:\n" + e.getMessage());
		}
	}
	
	
	/* ****************************
	 * GUI SPECIFIC HELPER METHODS.
	 * ****************************
	 */
	
	/**
	 * Gets the JFrame of this GUI.
	 * Not great practice, please forgive me...
	 * @return the JFrame.
	 */
	public JFrame getFrame() {
		return this.frame;
	}
	
	public void clearTextPanel() {
		this.gamePage.textArea.setText("");
	}
	
	private void requestFocus() {
		// Request back focus into the frame.
		frame.requestFocusInWindow();
	}
	
}
