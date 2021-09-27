package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * Generic event stores all the GameEvent information that is not related to
 * performing or undoing the action.
 *
 * @author sansonbenj 300482847
 *
 */
public abstract class GenericEvent implements GameEvent {

  /**
   * Store if this GameUpdate the final one.
   */
  protected boolean isFinal;
  /**
   * The time that this GameEvent was created.
   */
  protected final long timeStamp;
  /**
   * The number of the update that generated this GameEvent.
   */
  protected final int updateIndex;

  /**
   * Create a generic update. Encapsulates all the non-event-specific information
   * of the GameEvent.
   *
   * @param updateIndex the number of the update that generated this event
   */
  public GenericEvent(int updateIndex) {
    this.updateIndex = updateIndex;
    this.isFinal = false;
    this.timeStamp = System.currentTimeMillis();
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
  public boolean isFinalUpdate() {
    return this.isFinal;
  }

  @Override
  public void setFinalUpate(boolean isFinal) {
    this.isFinal = isFinal;
  }

  @Override
  public abstract void redoEvent(World w);

  @Override
  public abstract void undoEvent(World w);

}