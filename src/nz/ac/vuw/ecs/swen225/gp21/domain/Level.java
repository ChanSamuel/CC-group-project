package nz.ac.vuw.ecs.swen225.gp21.domain;
import java.util.*;

import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.GreenDoor;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.GreenKey;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.SilverDoor;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.SilverKey;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.CopperDoor;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.CopperKey;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.ExitLock;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.ExitTile;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Free;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.GoldDoor;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.GoldKey;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Info;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.OneWayEast;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.OneWayNorth;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.OneWaySouth;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.OneWayWest;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Teleporter;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Terrain;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Treasure;
import nz.ac.vuw.ecs.swen225.gp21.domain.terrain.Wall;

import static java.util.Map.entry;

/**
 * This class represents a level
 * It contains all the information needed to initialize the world to play a level. 
 * It does not contain the active state of the world i.e. it can't be used to record a game
 * TODO NOTE: currently cannot add external GameObjects via subclass. They must by added via world.addEntitiy after world initialization :(
 * @see "https://stackoverflow.com/questions/6802483/how-to-directly-initialize-a-hashmap-in-a-literal-way"
 * @author Benjamin
 *
 */
public final class Level {
	/**
	 * Mapping of characters to terrain types
	 */
	private Map<String, Terrain> charToTerrain; 
	/**
	 * Mapping of characters to names of GameObjects
	 */
	private Map<String, String> charToGameObjName;
	/**
	 * The height of the level in tiles
	 */
	public final int rows;
	/**
	 * The width of the level in tiles
	 */
	public final int columns;
	/**
	 * A string representing the location of terrain types
	 * in the tiles as a 1D array in Row-Major format
	 */
	private final String terrainLayout;
	/**
	 * A string representing spawn locations of entities in the level
	 * as a 1D array in Row-Major format
	 */
	private final String entityLayout;
	/** 
	 * @param rows the height of the level
	 * @param columns the width of the level
	 * @param terrainLayout the locations of the terrain types in the level
	 * @param entityLayout the locations of entities in the level
	 * @param info the string that the information tile will display when it is stepped on
	 */
	public Level(int rows, int columns, String terrainLayout, String entityLayout, String info) {
		if(rows < 1 || columns < 1) throw new IllegalArgumentException("Level dimensions are invalid!");
		if((terrainLayout.length() != entityLayout.length()) || (terrainLayout.length() != rows*columns)) throw new IllegalArgumentException("Level data is inconsistent!");
		if(terrainLayout.isBlank() || entityLayout.isBlank()) throw new IllegalArgumentException("Level data cannot be blank!");
		Info.setInfoText(info); // TODO make this a setter in the level type, issue: current approach only allows one message per level. Cannot have two info tiles that provide different messages :( 
		//Note:
		//I would love to have something like:
		//Tiles.getFree() ... Tiles.getWall() ... some kind of factory class that provides all the tiles, and within it, only keeps one of each tile
		//But I don't know how to do this, and I don't know how to make that system support new tile additions
		charToTerrain = Map.ofEntries(
			entry(".", Free.getInstance()),
			entry("c", Treasure.getInstance()),
			entry("#", Wall.getInstance()),
			entry("X", ExitLock.getInstance()),
			entry("E", ExitTile.getInstance()),
			
			entry("B", GreenDoor.getInstance()),
			entry("b", GreenKey.getInstance()), //TODO add yellow door, yellow key etc etc
			entry("g", GoldKey.getInstance()),
			entry("G", GoldDoor.getInstance()),
			entry("u", CopperKey.getInstance()),
			entry("U", CopperDoor.getInstance()),
			entry("s", SilverKey.getInstance()),
			entry("S", SilverDoor.getInstance()),
			
			entry("i", Info.getInstance()),
			entry("^", OneWayNorth.getInstance()),
			entry("<", OneWayWest.getInstance()),
			entry(">", OneWayEast.getInstance()),
			entry("v", OneWaySouth.getInstance()),
			entry("teleport", Teleporter.getInstance())
		);
		//TODO this isn't ideal, preferably we just give the object directly in the map
		charToGameObjName = Map.ofEntries(
				entry("C", "Chip"),
				entry("B", "Block")
		);
		
		this.rows = rows;
		this.columns = columns;
		this.terrainLayout = validateLevelLayout(terrainLayout);
		this.entityLayout = entityLayout;
//		Teleporter.links = makeTeleportLinks(); //NOTE moved this into board initialization
	}
	/**
	 * Analyze the terrain layout string to make
	 * the teleporter coordinate pairs.
	 * @return A record of one way teleporter links via tile coordiantes
	 */
	public Map<Coord, Coord> makeTeleportLinks() {
		Map<Coord, Coord> links = new HashMap<>();
		//record the location of the first nodes of the teleporters
		Map<Integer, Coord> initialLinkLocation = new HashMap<>();
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < columns; col++) {
				int index = twoDtoOneD(row, col);
				try {
					Integer linkNumber = Integer.parseInt(Character.toString(terrainLayout.charAt(index)));
					if(initialLinkLocation.containsKey(linkNumber)) {
						//full pair
						links.put(initialLinkLocation.get(linkNumber), new Coord(row, col));
					} else {
						initialLinkLocation.put(linkNumber, new Coord(row, col));
					}
					
				}catch(NumberFormatException e) {
					//The terrain type at this position is not a number (not a teleporter)
					continue;
				}
			}
		}
		return Collections.unmodifiableMap(links);
	}
	/**
	 * Convert a two dimensional array index
	 * into its equivalent one dimensional array index
	 * @param row
	 * @param col
	 * @return position of this element in a one d array
	 */
	private int twoDtoOneD(int row, int col) {
		return col + (row * columns); //TODO formula needs testing
	}
	/**
	 * Validate the layout of the level
	 * throws an exception if the level layout it invalid
	 * @param layout tile layout of the level
	 * @return the level layout
	 */
	private String validateLevelLayout(String layout) {
		//TODO perform any layout validation checks here
		//NOTE: default -> approves all levels
		return layout;
	}
	/**
	 * Get the name of the GameObject type at a position
	 * @param c the location in the level of interest
	 * @return null if there is no entity at this position
	 */
	public String entityNameAt(Coord c) {
		int index = twoDtoOneD(c.getRow(), c.getCol());
		String objectChar = Character.toString(entityLayout.charAt(index)); 
		return charToGameObjName.get(objectChar);
		
	}
	/**
	 * Get the terrain at a location in the level
	 * @param c the location
	 * @return the terrain at this location
	 */
	public Terrain terrainAt(Coord c) {
		int index = twoDtoOneD(c.getRow(), c.getCol());
		Character terrainChar = terrainLayout.charAt(index);
		String terrainString = Character.toString(terrainChar);
		if(Character.isDigit(terrainChar)) terrainString = "teleport";
		return this.charToTerrain.get(terrainString);
	}
	
	/**
	 * Add a new terrain type to the map to be used
	 * in the levels
	 * @param charRep the character representation of this terrain in the level data
	 * @param t - the new terrain type
	 */
	public void addTerrain(String charRep, Terrain t) {
		charToTerrain = new HashMap<>(charToTerrain);
		charToTerrain.put(charRep, t);
		charToTerrain = Collections.unmodifiableMap(charToTerrain);
	}
	/**
	 * Add a new type of game object to the map 
	 * to be used in the levels
	 * @param charRep the character representation of this object in the level data
	 * @param name the name of the GameObject this character maps to
	 */
	public void addGameObject(String charRep, String name) {
		charToGameObjName = new HashMap<>(charToGameObjName);
		charToGameObjName.put(charRep, name);
		charToGameObjName = Collections.unmodifiableMap(charToGameObjName); 
	}
}
