package pt.iscte.poo.manuelcovas.sokoban.tiles;

import pt.iscte.poo.manuelcovas.sokoban.level.Level;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Floor implements ImageTile {

	private static String name = "Chao";
	private static int layer = 0;
	private Point2D position;
	
	public Floor(int x, int y) {
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
	public boolean playerInteract(Direction direction, ImageTile[][] tileGrid, Level level){  // Floor can always be moved on.
		return true;
	}
	
	@Override
	public boolean movableInteract(ImageTile movedTile, Level level) {  // Anything can be pushed on to the floor.
		return true;
	}
}
