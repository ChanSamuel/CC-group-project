package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * Interface to represent events that can happen in the game.
 *
 * @author sansonbenj 300482847
 *
 */
public interface GameEvent {
  /**
   * Get the update this event was generated in. i.e. the second update, the first
   * update etc. etc.
   *
   * @return which update generated this GameEvent
   */
  public int getUpdateIndex();

  // public void setUpdateIndex(int index);
  // public void setTimeStamp(long timeStamp);

  /**
   * Get the time this GameEvent was generated. Determined from
   * System.currentTimeMillis()
   *
   * @return the time this update was created.
   */
  public long getTimeStamp();

  /**
   * determine if this event is the final event for a gameplay session.
   *
   * @return true if this was the final event in a gameplay session
   */
  public boolean isFinalUpdate();

  /**
   * Set if this event is the final update in a gameplay session.
   *
   * @param isFinal whether this update is the final update for a session
   */
  public void setFinalUpate(boolean isFinal);

  /**
   * Re-perform the action encapsulated in this event.
   *
   * @param w the domain this event should be applied to
   */
  public void redoEvent(Domain w);

  /**
   * Undo the action encapsulated in this event.
   *
   * @param w the domain this event should be applied to
   */
  public void undoEvent(Domain w);

  /**
   * Override the toString to something useful.
   *
   * @return a textual representation of this object.
   */
  @Override
  public String toString();

}
