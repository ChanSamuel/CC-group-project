package nz.ac.vuw.ecs.swen225.gp21.domain;

/**
 * GameObjects have a direction.
 *
 * @author sansonbenj 300482847
 */
public enum Direction {
  /**
   * Object is facing north.
   */
  NORTH {
    @Override
    public Coord next(Coord location) {
      return location.up();
    }

    @Override
    public Direction opposite() {
      return SOUTH;
    }
  },
  /**
   * Object is facing south.
   */
  SOUTH {
    @Override
    public Coord next(Coord location) {
      return location.down();
    }

    @Override
    public Direction opposite() {
      return NORTH;
    }
  },
  /**
   * Object is facing east.
   */
  EAST {
    @Override
    public Coord next(Coord location) {
      return location.right();
    }

    @Override
    public Direction opposite() {
      return WEST;
    }
  },
  /**
   * Object is facing west.
   */
  WEST {
    @Override
    public Coord next(Coord location) {
      return location.left();
    }

    @Override
    public Direction opposite() {
      return EAST;
    }
  },
  /**
   * Object has no direction.
   */
  NONE {
    @Override
    public Coord next(Coord location) {
      return location;
    }

    @Override
    public Direction opposite() {
      return NONE;
    }
  };

  /**
   * What would the next location be for a given direction.
   *
   * @param location a location
   * @return the next location along in that direction
   */
  public abstract Coord next(Coord location);

  /**
   * Return the direction that is opposite this direction.
   *
   * @return the direction that is opposite this direction
   */
  public abstract Direction opposite();
}
