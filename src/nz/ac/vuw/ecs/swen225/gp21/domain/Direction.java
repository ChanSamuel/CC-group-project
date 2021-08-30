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
			return location.down();
		}
	},
	/**
	 * Object is facing south
	 */
	SOUTH {
		public Coord next(Coord location) {
			return location.down();
		}
	},
	/**
	 * Object is facing east
	 */
	EAST{
		public Coord next(Coord location) {
			return location.right();
		}
	},
	/**
	 * Object is facing west
	 */
	WEST {
		public Coord next(Coord location) {
			return location.left();
		}
	},
	/**
	 * Object has no direction
	 */
	NONE {
		public Coord next(Coord location) {
			return location;
		}
	};
	/**
	 * What would the next location be for a given direction
	 * @param location a location
	 * @return the next location along in that direction
	 */
	public abstract Coord next(Coord location);
}
