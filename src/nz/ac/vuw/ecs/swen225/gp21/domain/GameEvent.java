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
   * Re-perform the action encapsulated in this event.
   *
   * @param w the world this event should be applied to
   */
  public void redoEvent(World w);

  /**
   * Undo the action encapsulated in this event.
   *
   * @param w the world this event should be applied to
   */
  public void undoEvent(World w);

  /**
   * Override the toString to something useful.
   *
   * @return a textual representation of this object.
   */
  @Override
  public String toString();

}
