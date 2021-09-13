package nz.ac.vuw.ecs.swen225.gp21.app.controllers;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class GUIFrame extends JFrame {
	
	// Menu bar
	JMenuBar menuBar = new JMenuBar();
	JMenu fileMenu = new JMenu("File");
	JMenu helpMenu = new JMenu("Help");
	JMenuItem helpRules = new JMenuItem("Rules");
	JMenuItem helpControls = new JMenuItem("Controls");
	JMenuItem fileLoadGame = new JMenuItem("Load game");
	JMenuItem fileLoadReplay = new JMenuItem("Load replay");
	JMenuItem fileExit = new JMenuItem("Exit to menu");
	JMenuItem fileQuit = new JMenuItem("Quit game");
	JMenuItem fileExitSave = new JMenuItem("Exit and save");
	JMenuItem fileSaveState = new JMenuItem("Save state");
	JMenuItem fileSaveReplay = new JMenuItem("Save replay");
	
	public GUIFrame(List<Page> pages) {
		
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		fileMenu.add(fileLoadGame);
		fileMenu.add(fileLoadReplay);
		fileMenu.add(fileExit);
		fileMenu.add(fileQuit);
		fileMenu.add(fileExitSave);
		fileMenu.add(fileSaveState);
		fileMenu.add(fileSaveReplay);
		helpMenu.add(helpRules);
		helpMenu.add(helpControls);
		
		this.setJMenuBar(menuBar);
		
		
		setLayout(new CardLayout());
		// Add all pages to frame.
		Container c = getContentPane();
		for (int i = 0; i < pages.size(); i++) {
			Page p = pages.get(i);
			c.add(p.getPanel(), p.getInformalName());
		}
		
		fileExitSave.setEnabled(false);
		fileSaveState.setEnabled(false);
		fileSaveReplay.setEnabled(false);
		
		Dimension screenDim = Toolkit.getDefaultToolkit().getScreenSize();
		
		pack();
		setSize((int) (0.95 * screenDim.getWidth()), (int) (0.8 * screenDim.getHeight()));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Chap's Challenge");
		setVisible(true);
		
	}
}
