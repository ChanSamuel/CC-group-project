package nz.ac.vuw.ecs.swen225.gp21.app.controllers;

import java.util.List;
import nz.ac.vuw.ecs.swen225.gp21.app.Controller;
import nz.ac.vuw.ecs.swen225.gp21.domain.Item;

/**
 * The Controller for the Fuzzer. This Controller is headless, and has empty
 * implementations for methods which display things. Instead, events are logged
 * and can be retrieved by the Fuzzer when needed.

 * @author chansamu1 300545169
 *
 */
public class FuzzController extends Controller {

  /**
   * The max size of the log.
   */
  public static final int MAX_LOG_SIZE = 1000;

  /**
   * The log of event and warning messages.
   */
  private LogMessage[] log = new LogMessage[MAX_LOG_SIZE];

  /**
   * The pointer to the latest log index + 1.
   */
  private int logIdx = 0;

  @Override
  public void run() {
    // Do nothing, no need to initialise a GUI interface.
  }

  @Override
  protected void warning(String message) {
    addToLog(new LogMessage(message, true));
  }

  @Override
  protected void report(String message) {
    addToLog(new LogMessage(message, false));
  }

  @Override
  protected void inform(String message) {
    addToLog(new LogMessage(message, false));
  }

  @Override
  protected void enteredExit() {
    report("ENTERED EXIT");
  }

  @Override
  protected void enteredInfo(String msg) {
    report(msg);
  }

  @Override
  protected void playerLost() {
    report("PLAYER LOST");
  }

  @Override
  protected void playerGainedItem(List<Item> playerInventory, Item item) {
    report("GAINED " + item.toString());
  }

  @Override
  protected void playerConsumedItem(List<Item> playerInventory, Item item) {
    report("CONSUMED + " + item.toString());
  }

  @Override
  protected void openedDoor() {
    report("OPENED DOOR");
  }

  @Override
  protected void collectedChip(int remainingChips) {
    report("REMAINING CHIPS: " + remainingChips);
  }

  @Override
  protected void objectTeleported() {
    report("OBJECT TELEPORTED");
  }

  @Override
  protected void objectPushed() {
    report("OBJECT PUSHED");
  }

  @Override
  protected void pauseOperation() {
    report("GAME PAUSED");
  }

  @Override
  protected void updateTimerOperation(int timeLeft) {
    // Do nothing, no need to display current time.
  }

  @Override
  protected void exitToMenu() {
    // Do nothing, there is no menu for fuzz.
  }

  /**
   * Add to the log history.

   * @param m : The message to add
   */
  private void addToLog(LogMessage m) {
    if (this.log.length < MAX_LOG_SIZE) {
      this.log[logIdx] = m;
      logIdx++;
    }
  }

  /**
   * Returns the log of warnings and notifications provided by the game and
   * controller.

   * @return the log history.
   */
  @SuppressWarnings("unused")
  private LogMessage[] getLog() {
    return this.log;
  }

}
