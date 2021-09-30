package nz.ac.vuw.ecs.swen225.gp21.domain;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * Generic event stores all the GameEvent information that is not related to
 * performing or undoing the action.
 *
 * @author sansonbenj 300482847
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
public abstract class GenericEvent implements GameEvent {

  /**
   * The time that this GameEvent was created.
   */
  protected long timeStamp;
  /**
   * The number of the update that generated this GameEvent.
   */
  protected int updateIndex;

  /**
   * Create a generic update. Encapsulates all the non-event-specific information
   * of the GameEvent.
   *
   * @param updateIndex the number of the update that generated this event
   */
  public GenericEvent(int updateIndex) {
    this.updateIndex = updateIndex;
    this.timeStamp = System.currentTimeMillis();
  }

  /**
   * Default constructor for saving.
   */
  public GenericEvent() {
  }

  @Override
  public int getUpdateIndex() {
    return this.updateIndex;
  }

  @Override
  public long getTimeStamp() {
    return this.timeStamp;
  }

  @Override
  public abstract void redoEvent(World w);

  @Override
  public abstract void undoEvent(World w);

}