package nz.ac.vuw.ecs.swen225.gp21.domain;
/**
 * @author Benjamin
 * GameObjects have a direction
 */
public enum Direction {
	/**
	 * Object is facing north
	 */
	NORTH {
		public Coord next(Coord location) {
			return location.up();
		}
		public Direction opposite() { return SOUTH; }
	},
	/**
	 * Object is facing south
	 */
	SOUTH {
		public Coord next(Coord location) {
			return location.down();
		}
		public Direction opposite() { return NORTH; }
	},
	/**
	 * Object is facing east
	 */
	EAST{
		public Coord next(Coord location) {
			return location.right();
		}
		public Direction opposite() { return WEST; }
	},
	/**
	 * Object is facing west
	 */
	WEST {
		public Coord next(Coord location) {
			return location.left();
		}
		public Direction opposite() { return EAST; }
	},
	/**
	 * Object has no direction
	 */
	NONE {
		public Coord next(Coord location) {
			return location;
		}
		public Direction opposite() { return NONE; }
	};
	/**
	 * What would the next location be for a given direction
	 * @param location a location
	 * @return the next location along in that direction
	 */
	public abstract Coord next(Coord location);
	/**
	 * Return the direction that is opposite this direction
	 * @return the direction that is opposite this direction
	 */
	public abstract Direction opposite();
}
