package nz.ac.vuw.ecs.swen225.gp21.domain;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import nz.ac.vuw.ecs.swen225.gp21.domain.objects.Chip;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.GameOver;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Loading;
import nz.ac.vuw.ecs.swen225.gp21.domain.state.Running;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Terrain;
import nz.ac.vuw.ecs.swen225.gp21.persistency.GameMemento;

/**
 * The world provides the main interface of the domain package, for other
 * modules to interact with.
 *
 * @author sansonbenj 300482847
 *
 */
public abstract class World implements Domain {

  /**
   * The state of the world. This dictates what actions the world can perform.
   */
  protected State worldState;

  /**
   * Number of updates that have occurred since the game started running.
   */
  public int updates;
  /**
   * Store the movement requests that have been made by the controller. (and by
   * extension, the user.)
   */
  protected Deque<Command> playerCommands;
  /**
   * Handy reference to the player controller game object. Using a reference
   * instead of having to scan through the array. (is this a good approach?)
   */
  protected Chip playerEntity;
  /**
   * Handy reference to all game objects that are in the world. Allows easy access
   * to the game objects instead of trying to find them through scanning the
   * array. TODO "Dead" GameObjects might still live here, but won't have a
   * reference in the Board array? <\p>
   */
  protected List<GameObject> allEntities;
  // ^NOTE: to define entities (like the bug and block) I'm going to hard code
  // them as classes for now
  // this resource: http://gameprogrammingpatterns.com/type-object.html
  // explains how to do this as data in a config file, although, this still needs
  // to be determined
  // Somehow, the persistence module needs to feed in new entities
  /**
   * The board is the internal representation of of the game.
   */
  protected Board board;
  /**
   * Central record of total treasure.
   */
  public int totalTreasure;

  // ===========================================================
  // WORLD INITIALIZATION METHODS TODO the domain system needs decoupling!
  /**
   * Create a new world. Initializes the following fields: state player command
   * queue entity list Does not initialize: Board, init via load level updates,
   * init via load level, restore game, load replay totalTreasure, init via load
   * level event, init via running.update() playerEntity, init via load level
   */
  public World() {
    worldState = new Loading();
    playerCommands = new ArrayDeque<Command>();
    allEntities = new ArrayList<GameObject>();
    board = null;
    updates = -1;
    totalTreasure = -1;
    playerEntity = null;
  }

  // ===========================================================
  // PUBLIC API METHODS FOR OTHER MODULES TO TALK TO
  /**
   * The big method, calling this will simulate the world and its behaviors for
   * one tick. Resource: http://gameprogrammingpatterns.com/game-loop.html
   *
   * @param elapsedTime the time since this method was last called, calculated by
   *                    the caller
   */
  @Override
  public void update(double elapsedTime) {
    worldState.update(this, elapsedTime);
  }

  /**
   * Initialize the world with data from the level object.
   *
   * @param level the level information
   */
  @Override // NOTE: call when starting a new game
  public void loadLevelData(Level level) {
    worldState.loadLevel(this, level);
  }

  @Override
  public void restoreGame(GameMemento save) {
    worldState.restoreGame(this, save);
  }

  @Override
  public GameMemento generateSaveData() {
    return worldState.generateSaveData(this);
  }

  @Override
  public void forwardTick(GameEvent e) {
    worldState.forwardTick(this, e);
  }

  @Override
  public void backTick(GameEvent e) {
    worldState.backTick(this, e);
  }

  @Override
  public State getDomainState() {
    return worldState;
  }

  @Override
  public void setState(State s) {
    this.worldState = s;
  }

  /**
   * External package entity should call this when all the external objects have
   * been added to the world via addObject(...)
   */
  @Override
  public void doneLoading() {
    if (!(worldState instanceof Loading)) {
      throw new UnsupportedOperationException("Cannot finish loading when not in loading state!");
    }
    if (board == null) {
      throw new UnsupportedOperationException(
          "doneLoading was called, but no level data was loaded!");
    }
    this.worldState = new Running();
  }
  // TODO this should probably be a temp method? There has gotta be a better way
  // to load in external entities

  @Override
  public boolean isGameOver() {
    return worldState instanceof GameOver;
  }

  /**
   * Get the number of columns the board has. Throws RTE if there is no board.
   *
   * @return the number of columns
   */
  public int getBoardWidth() {
    return worldState.getBoardWidth(this);
  }

  /**
   * Get the number of rows the board has. Throws RTE if there is no board.
   *
   * @return the number of rows
   */
  public int getBoardHeight() {
    return worldState.getBoardHeight(this);
  }

  /**
   * Determine if a coordinate is valid on the board. Throws RTE if there is no
   * board.
   *
   * @param c the coordinate
   * @return if it is valid on the board
   */
  public boolean isCoordValid(Coord c) {
    return worldState.isCoordValid(this, c);
  }

  @Override
  public Coord getPlayerLocation() {
    return worldState.getPlayerLocation(this);
  }

  @Override
  public void addGameObject(GameObject e, Coord c) {
    worldState.addObject(this, e, c);
  }

  @Override
  public int getUpdateCount() {
    return this.updates;
  }

  // ==========================================================
  // EVEN MORE PUBLIC API METHODS
  /**
   * Enqueues a move up command for the playerEntity into the movement queue.
   */
  @Override
  public void moveChipUp() {
    worldState.moveChipUp(this);
  }

  /**
   * Enqueues a move down command for the playerEntity into the movement queue.
   */
  @Override
  public void moveChipDown() {
    worldState.moveChipDown(this);
  }

  /**
   * Enqueues a move left command for the playerEntity into the movement queue.
   */
  @Override
  public void moveChipLeft() {
    worldState.moveChipLeft(this);
  }

  /**
   * Enqueues a move right command for the playerEntity into the movement queue.
   */
  @Override
  public void moveChipRight() {
    worldState.moveChipRight(this);
  }

  // ==========================================================
  // INTERNAL GAME GETTERS AND SETTERS - these methods are needed so
  // worldState.load(...) can set fields
  /**
   * Remove the top element player commands queue.
   *
   * @return the oldest player command in the queue, or null if there are no
   *         commands
   */
  public Command poll() {
    return playerCommands.poll();
  }

  /**
   * Get the tile at a location. Can be used by game objects to make decisions.
   *
   * @param location the location of the tile of interest
   * @return the tile at the location
   */
  public Tile getTileAt(Coord location) {
    return board.getTileAt(location);
  }

  /**
   * Get the list of all entities.
   *
   * @return the list of all entities
   */
  public List<GameObject> getEntities() {
    return this.allEntities;
  }

  /**
   * Get the command queue.
   *
   * @return the command queue
   */
  public Deque<Command> getCommandQueue() {
    return this.playerCommands;
  }

  /**
   * Get the board for this world.
   *
   * @return the board this world is using
   */
  public Board getBoardWorld() {
    return this.board;
  }

  @Override
  public Board getBoard() {
    return new UnmodifiableBoard(board);
  }

  /**
   * Set the board for this world. Caution, GameEntities will still have
   * references to tiles in the old board.
   *
   * @param b the board this world will use
   */
  public void setBoard(Board b) {
    this.board = b;
  }

  /**
   * Get the player entity object.
   *
   * @return the player entity
   */
  public Chip getPlayer() {
    return this.playerEntity;
  }

  /**
   * Set the player entity for this world.
   *
   * @param c the player entity this world will use
   */
  public void setPlayer(Chip c) {
    this.playerEntity = c;
  }

  // ====================================================================================
  // INTERNAL GAME EVENT METHODS - These methods are invoked when the relevant
  // event occurs
  /**
   * This method is called when chip enters a treasure tile. But before the
   * treasure terrain is replaced with 'free' terrain
   */
  public abstract void collectedChip();

  /**
   * This method is called when chip opens a door. It can be used to notify the.
   * view to do something special like play a sound.
   */
  public abstract void openedDoor();

  /**
   * This method is called when chip enters the exit square.
   */
  public abstract void enteredExit();

  /**
   * Called when an entity enters an info tile.
   *
   * @param msg the message the info tile contained
   */
  public abstract void enteredInfo(String msg);

  /**
   * Called when the player leaves an info tile.
   */
  public abstract void leftInfo();

  /**
   * Called when the player loses.
   */
  public abstract void playerLost();

  /**
   * Called when the player gains an item.
   *
   * @param item the item the player gained
   */
  public abstract void playerGainedItem(Item item);

  /**
   * Called when the player uses an item. For example, uses a key to unlock door.
   *
   * @param item the item that was used
   */
  public abstract void playerConsumedItem(Item item);

  /**
   * Called when a GameObject goes through a teleporter.
   */
  public abstract void objectTeleported();

  /**
   * Called when a pushable object is moved.
   */
  public abstract void objectPushed();

  /**
   * Called when a GameEvent occurs.
   *
   * @param e the event that occurred
   */
  public abstract void eventOccured(GameEvent e);

  // ==========================================================
  // MOVEMENT METHODS - these methods talk to the board to move stuff
  // I'm open to criticism on why we have this extra layer of indirection
  // GameObject/Tile -> world -> Board -> world information needed to make a
  // decision
  /**
   * Try Move an object to a destination Moving an object can cause a cascade of
   * further events to occur This method does not save moves into a tick,
   * makeMove(...) does
   *
   * @param o           the object being moved
   * @param destination where the object is being moved to
   * @return The terrain at the destination before the move was applied, or NULL
   *         if couldn't make the move.
   */
  public Terrain moveObject(GameObject o, Coord destination) {
    return board.tryMoveObject(destination, o);
  }

  /**
   * Try to move an object in a direction. Defers the movement to worldState. If
   * the move succeeds, it is recorded.
   *
   * @param o object that is moving
   * @param d the direction the object is moving in.
   */
  public void makeMove(GameObject o, Direction d) {
    worldState.makeMove(this, o, d);
  }

  /**
   * Try to move the specified game object up.
   *
   * @param o the object being moved up
   */
  public void moveUp(GameObject o) {
    makeMove(o, Direction.NORTH);
  }

  /**
   * Try to move the specified game object down.
   *
   * @param o the object being moved down
   */
  public void moveDown(GameObject o) {
    makeMove(o, Direction.SOUTH);
  }

  /**
   * Try to move the specified game object left.
   *
   * @param o the object being moved left
   */
  public void moveLeft(GameObject o) {
    makeMove(o, Direction.WEST);
  }

  /**
   * Try to move the specified game object right.
   *
   * @param o the object being moved right
   */
  public void moveRight(GameObject o) {
    makeMove(o, Direction.EAST);
  }

  // ====================================================================================
  // TO-STRING
  @Override
  public String toString() {
    StringBuilder ans = new StringBuilder();
    // add board state
    ans.append("Game is: " + getDomainState().getClass().getSimpleName() + "\n");
    ans.append("Is game over? -> " + isGameOver() + "\n");
    // add player inventory
    // add queue
    ans.append("PlayerQueue: \n");
    if (playerCommands.isEmpty()) {
      ans.append("EMPTY");
    }
    for (Command c : playerCommands) {
      ans.append(c + "\n");
    }
    ans.append("\n");
    // add all entities
    ans.append("All entities: \n");
    for (GameObject e : allEntities) {
      ans.append(e + "\n");
    }
    ans.append("\n");
    // add board
    ans.append("Board: \n" + (board == null ? "Not initialized" : board));
    return ans.toString();
  }

}
