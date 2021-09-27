package nz.ac.vuw.ecs.swen225.gp21.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * A Game object is something that exists in the game world that can move.
 *
 * @author sansonbenj 300482847
 *
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NAME)
public abstract class GameObject {
  /**
   * The file path to the image used to render this character. Use if the renderer
   * wants to use an image.
   */
  public final String filePathLeft;
  /**
   * The file path to the image used to render this character. Use if the renderer
   * wants to use an image.
   */
  public final String filePathRight;
  /**
   * The logic that GameObjects use to choose how to move is encapsulated away
   * into a movement controller.
   */
  protected MovementController controller;
  /**
   * The direction this object is facing.
   */
  public Direction dir;
  /**
   * The tile that this GameObject is currently on.
   */
  protected Tile currentTile;

  /**
   * Create a new GameObject, defaults to no direction. NOTE: Changed visibility
   * to package-private, might need to be changed later? how will persistence
   * module load in new GameObjects?
   *
   * @param c         The movement controller component that controls this object
   * @param pathLeft  The file path used to render the GameObject facing Left
   * @param pathRight The file path used to render the GameObject facing right
   */
  protected GameObject(MovementController c, String pathLeft, String pathRight) {
    this.controller = c;
    dir = Direction.NONE;
    this.filePathLeft = pathLeft;
    this.filePathRight = pathRight;
  }

  /**
   * Create a new GameObject with a direction.
   *
   * @param c         The movement controller component that controls this object
   * @param d         The direction this object has
   * @param pathLeft  The file path to the resource that draws the object facing
   *                  left
   * @param pathRight The file path to the resource that draws the object facing
   *                  right
   */
  protected GameObject(MovementController c, Direction d, String pathLeft, String pathRight) {
    this.controller = c;
    dir = d;
    this.filePathLeft = pathLeft;
    this.filePathRight = pathRight;
  }

  /**
   * GameObjects must state whether an entity can enter onto the same tile as them
   * For instance, a bug can enter the same tile as Chip, but a bug cannot enter
   * the same tile as a block.
   *
   * @param entity the entity trying to move onto the same tile as 'this'
   * @return Whether or not the entity can enter the tile 'this' object is on,
   *         according to the game logic
   */
  public abstract boolean canEntityGoOnTile(GameObject entity);

  /**
   * Execute this object's behavior when another GameObject enters the same tile
   * it is on. For example, if Chip enters the same tile as a movable block, the
   * block moves itself { w.moveUp(this) }
   *
   * @param entity the entity that just entered the tile 'this' object is on
   */
  public abstract void entityEnteredTile(GameObject entity);

  /**
   * Perform this objects behavior for one game simulation tick. { for example,
   * the bug type moves in a random direction each second }
   *
   * @param elapsedTime time in milliseconds since the last update
   * @param w           The world this update is occuring in
   */
  public abstract void update(double elapsedTime, World w);

  /**
   * Get the name of this object type. For example, the player controlled entity
   * will return "Chip" the bug entity will return "Bug"
   *
   * @return the name of this entity
   */
  @JsonIgnore
  public abstract String getName();

  /**
   * Update the direction of the GameObject, provided it has one.
   *
   * @param d the new direction
   */
  public void updateDirection(Direction d) {
    this.dir = d;
  }

  /**
   * Updates the old tile reference to have no occupier Updates this GameObject's
   * tile reference.
   *
   * @param tile the new tile this object is on
   */
  public void setTile(Tile tile) {
    if (currentTile != null) {
      this.currentTile.removeOccupier();
    }
    this.currentTile = tile;
  }

  /**
   * Get the tile this gameObject is currently inside of.
   *
   * @return the tile this object is on
   */
  @JsonIgnore
  public Tile getTile() {
    return this.currentTile;
  }

  /**
   * Execute any behavior needed after the object exits a tile. Default does
   * nothing. If your game object needs to perform behavior after moving, override
   * this method.
   */
  public void doneMoving() {
  }

  /**
   * Get the char that represents this terrain type in the board toString, for
   * debugging.
   *
   * @return the char that represents this terrain type in the board toString
   */
  public abstract char boardChar();

  @Override
  public String toString() {
    return "GameObject: " + getClass().getSimpleName() + " facing->" + dir + " at->"
        + (currentTile == null ? "No location" : currentTile.location);
  }

}
