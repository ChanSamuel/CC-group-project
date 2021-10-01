package nz.ac.vuw.ecs.swen225.gp21.app;

import nz.ac.vuw.ecs.swen225.gp21.domain.GameEvent;
import nz.ac.vuw.ecs.swen225.gp21.recorder.GameUpdate;

/**
 * A proxy for the GameEvent (from domain) to the GameUpdate (from recording).
 * This class is needed due to the dependency requirements.

 * @author chansamu1 300545169
 *
 */
public class GameUpdateProxy implements GameUpdate {

  /**
   * The GameEvent (from domain).
   */
  private GameEvent ge;

  /**
   * Construct the GameUpdateProxy. This is needed for the XMLMapper to use.
   */
  public GameUpdateProxy() {

  }

  /**
   * Getter for the GameEvent (needed for XMLMapper).

   * @return the game event.
   */
  public GameEvent getGe() {
    return ge;
  }

  /**
   * Construct a GameUpdateProxy.

   * @param ge : the game event to proxy
   */
  public GameUpdateProxy(GameEvent ge) {
    this.ge = ge;
  }

  @Override
  public long getUpdateIndex() {
    return ge.getUpdateIndex();
  }

  /**
   * Get the GameEvent (need this to comply with something).

   * @return the game event.
   */
  public GameEvent getGameEvent() {
    return this.ge;
  }

}
