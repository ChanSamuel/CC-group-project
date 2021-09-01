package nz.ac.vuw.ecs.swen225.gp21.domain.items;

import java.util.Objects;

import nz.ac.vuw.ecs.swen225.gp21.domain.Item;

/**
 * The key item can be placed in Chip's Inventory
 * It is consumed when chip opens a door with it
 * @author Benjamin
 *
 */
public final class KeyItem implements Item {
	/**
	 * The color of this key
	 */
	final String color;
	/**
	 * Create a new key item
	 * @param c the color of the door this key unlocks
	 */
	public KeyItem(String c){
		if(c == null || c.isEmpty()) throw new IllegalArgumentException("Key must have a color!");
		this.color = c;
	}
	@Override 
	public String toString() {
		return "Key: ["+color+"]";
	}
	@Override
	public String getColour() {return color;}
	@Override
	public int hashCode() {
		return Objects.hash(color);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		KeyItem other = (KeyItem) obj;
		return Objects.equals(color, other.color);
	}
	
}
