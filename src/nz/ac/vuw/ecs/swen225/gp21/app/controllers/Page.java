package nz.ac.vuw.ecs.swen225.gp21.app.controllers;

import javax.swing.JPanel;

/**
 * A Page is a wrapper for a JPanel which represents some page which the GUI can
 * flip to and from.

 * @author chansamu1 300545169
 *
 */
public interface Page {

  /**
   * Get the JPanel this Page wraps.

   * @return the JPanel
   */
  public JPanel getPanel();

  /**
   * Get the name of this page.

   * @return page name
   */
  public String getInformalName();

}
