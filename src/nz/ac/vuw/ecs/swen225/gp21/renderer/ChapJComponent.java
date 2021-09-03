package nz.ac.vuw.ecs.swen225.gp21.renderer;

import java.awt.Graphics;
import java.awt.Image;
import nz.ac.vuw.ecs.swen225.gp21.domain.Coord;
import nz.ac.vuw.ecs.swen225.gp21.domain.Direction;
/**
 * The hero chap's JComponent
 * @author mengli
 *
 */
public class ChapJComponent extends GameObjectJComponent{
	/**
	 * Chap's image face left
	 */
	private final Image chapImageLeft = FileUtil.getGIF("chap-3-left.gif") ;
	/**
	 * Chap's image face right
	 */
	private final Image chapImageRight = FileUtil.getGIF("chap-3-right.gif") ;
	/**
	 * Chap's current image
	 */
	private Image chapImage;
	/**
	 * Chap's coordinates
	 */
	private Coord coord;
	/**
	 * Chap's direction
	 */
	private Direction dir;
	/**
	 * Constructor for chap
	 */
	ChapJComponent(Coord coord,Direction dir) {
		super(coord,dir);
		this.coord = coord;
		this.dir = dir;
		//default direction set as left.
		chapImage = chapImageLeft;
		setBounds(coord.getCol()*WorldJPanel.TILE_WIDTH,coord.getRow()*WorldJPanel.TILE_HEIGHT,WorldJPanel.TILE_WIDTH,WorldJPanel.TILE_HEIGHT);
		setVisible(true);
	}
	/**
	 * Update chap when moving
	 * @param x x coordinate of chap
	 * @param y y coordinate of chap
	 */
	void updateChap(Coord coord, Direction dir) {
		this.coord = coord;
		this.dir = dir;
		//update the location of this JComponent.
		setBounds(coord.getCol()*WorldJPanel.TILE_WIDTH,coord.getRow()*WorldJPanel.TILE_HEIGHT,WorldJPanel.TILE_WIDTH,WorldJPanel.TILE_HEIGHT);
		//create a new chapMoving thread for the animation
		this.repaint();
		//TODO create a new thread ChapMoving for animation
		ChapMoving cm = new ChapMoving();
		
	}
	/**
	 * Override the paint method of chap
	 */
	@Override
	public void paint(Graphics g) {
		//NOTE the last parameter couldn't be null, otherwise gif won't move.
		//if chap's direction change to WEST OR EAST, change the current chapImage, otherwise dont' change.
		if(dir==Direction.WEST) {
			chapImage = chapImageLeft;
		}else if(dir==Direction.EAST){
			chapImage = chapImageRight;
		}
		g.drawImage(chapImage,0,0,WorldJPanel.TILE_WIDTH,WorldJPanel.TILE_HEIGHT,this);
		}
}
/**
 * a subClass extends thread handling the chap moving animation
 * @author mengli
 *
 */
//TODO
class ChapMoving extends Thread{
	
}
