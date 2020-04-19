package pt.iscte.poo.manuelcovas.sokoban.tiles;

import pt.iscte.poo.manuelcovas.sokoban.level.Level;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Wall implements ImageTile {

	private static String name = "Parede";
	private static int layer = 1;
	
	private Point2D position;
	
	public Wall(int x, int y) {
		position = new Point2D(x, y);
	}
	
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return layer;
	}

	
	@Override
	public boolean playerInteract(Direction direction, ImageTile[][] tileGrid, Level level){  // Walls can't be pushed.
		return false;
	}
	
	@Override
	public boolean movableInteract(ImageTile movedTile, Level level) {  // Nothing can be pushed on to a Wall
		return false;
	}
}
