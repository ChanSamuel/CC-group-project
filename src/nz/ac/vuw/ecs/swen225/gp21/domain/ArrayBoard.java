package nz.ac.vuw.ecs.swen225.gp21.domain;
/**
 * 
 * @author Benjamin
 *
 */
public class ArrayBoard implements Board {
	/**
	 * TODO
	 */
	private Tile[][] board;
	/**
	 * TODO
	 */
	final int rows;
	/**
	 * TODO
	 */
	final int columns;
	/**
	 * Creates a default test level. Simple 10*10 level 
	 * Exit in the corner
	 */
	public ArrayBoard() {
		this.rows = 10; this.columns = 10;
		board = new Tile[rows][columns];
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < columns; col++) {
				board[row][col] = new Tile(new Coord(row, col));
				board[row][col].setTerrain(new Free());
			}
		}
		board[rows-1][columns-1].setTerrain(new ExitTile());
	}
	
	@Override
	public void addObject(GameObject o, Coord location) {
		boundsCheck(location);
		Tile t = coordToTile(location);
		if(t.isTileOccupied()) throw new RuntimeException("Cannot spawn one entity on an occupied tile: "+location+" Object: "+o);
		t.addOccupier(o);
	}
	/**
	 * Throw an exception if a passed coordinate that should be valid is not.
	 * @param c the coord being checked
	 */
	private void boundsCheck(Coord c) {
		if(!coordInBoard(c)) throw new RuntimeException("Coordinate is out of bounds: "+c);
	}
	/**
	 * Check if a coordinate is within the bounds of the board
	 * @param c the coordinate being checked
	 * @return true if the coordinate is 'inside' the board
	 */
	private boolean coordInBoard(Coord c) {
		if(c.getRow() < 0 || c.getRow() > rows - 1 || c.getCol() < 0 || c.getCol() > columns - 1) return false;
		return true;
	}
	/**
	 * Convert a coordinate to a tile in the board
	 * @param c the coordinate of the tile being located
	 * @return the tile that has this coordinate as its location
	 * @throws IndexOutOfBoundsException 
	 */
	Tile coordToTile(Coord c){
		boundsCheck(c);
		return board[c.getRow()][c.getCol()];
	}
	
	@Override
	public int getRemainingChips() {
		int answer = 0;
		for(int row = 0; row < rows; row++) {
			for(int col = 0; col < columns; col++) {
				if(board[row][col].getTerrain() instanceof Treasure) answer++;
			}
		}
		return answer;
	}
	@Override
	public void moveObject(GameObject o, Coord destination) {
		Tile t = coordToTile(destination);
		if(t.getTerrain().canEntityGoOn(o)) {
			if(t.isTileOccupied() && !t.getOccupier().canEntityGoOnTile(o)) return;
			t.setOccupier(o);
		}
	}
	/**
	 * Perform a full object move for a one square move
	 * Checks the occupier AND the terrain type before the move
	 * Assumes caller already did a bounds check
	 * @param dest the location the object is moving to
	 * @param o the object being moved
	 */
	private void moveObject(Coord dest, GameObject o) {
		Tile t = coordToTile(dest);
		if(t.getTerrain().canEntityGoOn(o)) {
			if(t.isTileOccupied() && !t.getOccupier().canEntityGoOnTile(o)) return;
			t.addOccupier(o);
		}
	}
	
	@Override
	public void moveUp(GameObject o) {
		Coord dest = o.currentTile.location.up();
		if(coordInBoard(dest)) moveObject(dest, o);
	}

	@Override
	public void moveDown(GameObject o) {
		Coord dest = o.currentTile.location.down();
		if(coordInBoard(dest)) moveObject(dest, o);
	}

	@Override
	public void moveLeft(GameObject o) {
		Coord dest = o.currentTile.location.left();
		if(coordInBoard(dest)) moveObject(dest, o);
	}

	@Override
	public void moveRight(GameObject o) {
		Coord dest = o.currentTile.location.right();
		if(coordInBoard(dest)) moveObject(dest, o);
	}

	@Override
	public void openExit() {
		for(int row = 0; row < rows; row++)
			for(int col = 0; col < columns; col++) 
				if(board[row][col].getTerrain() instanceof ExitLock) board[row][col].setTerrain(new Free());
	}
	@Override
	public String toString() {
		StringBuilder ans = new StringBuilder();
		for(int row = 0; row < rows; row++) {
			ans.append(row+"|");
			for(int col = 0; col < columns; col++) {
				Tile t = coordToTile(new Coord(row, col));
				if(t.isTileOccupied()) {
					ans.append(t.getOccupier().boardChar());
				} else {
					ans.append(t.getTerrain().boardChar());
				}
				ans.append("|");
			}
			ans.append("\n");
		}
		return ans.toString();
	}

	@Override
	public Tile getTileAt(Coord location) {
		return coordToTile(location);
	}

	@Override
	public int getWidth() { return columns;	}

	@Override
	public int getHeight() { return rows; }
}
