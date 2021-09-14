package nz.ac.vuw.ecs.swen225.gp21.app.controllers;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class GUIController extends GUI {
	
	
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
		frame.fileExit.addActionListener((ae) -> {
			
			// Disable buttons which involve saving when we go back to main menu.
			frame.fileExitSave.setEnabled(false);
			frame.fileSaveState.setEnabled(false);
			frame.fileSaveReplay.setEnabled(false);
			
			CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
			cl.show(frame.getContentPane(), "Home page");
		});
		
		
		// Menu bar quit game action
		frame.fileQuit.addActionListener((ae) -> {
			// Close all threads and exit.
			System.exit(0);
		});
		
		// Menu bar quit and save action
		frame.fileExitSave.addActionListener((ae) -> {
			// TODO: Need to also save the state here.
			
			// Close all threads and exit.
			System.exit(0);
		});
		
		// Home page new game button action
		homePage.newGameButton.addActionListener((ae) -> {
			
			// Re-enable save buttons for game page.
			frame.fileExitSave.setEnabled(true);
			frame.fileSaveState.setEnabled(true);
			frame.fileSaveReplay.setEnabled(true);
			
			CardLayout cl = (CardLayout) frame.getContentPane().getLayout();
			cl.show(frame.getContentPane(), "Game page");
			gamePage.background.repaint();
		});
		
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void leftInfoTrans() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playerLostTrans() {
		// TODO Auto-generated method stub
		
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
