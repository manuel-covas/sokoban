package pt.iul.ista.poo.gui;

import pt.iscte.poo.manuelcovas.sokoban.level.Level;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

/**
 * @author POO2016
 * 
 *         ImageTile is the interface required to all "images" used by
 *         ImageMatrixGUI.
 * 
 *         ImageTile is only required to provide the name of the image (e.g.
 *         "XxxX") and its position (in tile coordinates, with 0,0 in the top
 *         left corner and increasing to the right in the horizontal axis and
 *         downwards in the vertical axis). The ImageMatrixGUI will look for an
 *         image with that name in the "images" folder (e.g. "XxxX.png") and
 *         draw that image in the window.
 *
 */
public interface ImageTile {

	
	/**
	 * The name of the image. Must correspond to the name of an image file in
	 * the "images" folder otherwise it will trigger one of the following
	 * exceptions: FileNotFoundException, IllegalArgumentException
	 * 
	 * @return The name of the image file containing the desired image (without
	 *         extention)
	 * 
	 */
	String getName();

	/**
	 * Getter for the position (in grid coordinates) were the image should be
	 * placed.
	 * 
	 * @return A position.
	 */
	Point2D getPosition();

	/**
	 * Getter for the level of the image, higher levels are displed on top of
	 * lower ones (level 0 will be the first images to be displayed, the higher
	 * the level the later in the rendering the image will be added).
	 * 
	 * @return int.
	 */
	int getLayer();
	
	
	/**
	 * Determines whether the player can move on to this tile.
	 * 
	 * @param direction Direction in which the player is moving.
	 * @param tileGrid Level top-layer grid of ImageTiles.
	 * @param level TODO
	 * @return Whether the move is allowed.
	 */
	boolean playerInteract(Direction direction, ImageTile[][] tileGrid, Level level);
	
	
	/**
	 * Determines whether and object can move on to this tile.
	 * @param level TODO
	 * @param mvoedTile The tile that is moving on to this one.
	 * 
	 * @return Whether the move is allowed.
	 */
	boolean movableInteract(ImageTile movedTile, Level level);

}
