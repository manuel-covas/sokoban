package pt.iscte.poo.manuelcovas.sokoban.tiles;

import pt.iscte.poo.manuelcovas.sokoban.level.Level;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Battery implements ImageTile {
	
	private static String name = "Bateria";
	private static int layer = 2;
	private Point2D position;
	
	public Battery(int x, int y) {
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
	public boolean playerInteract(Direction direction, ImageTile[][] tileGrid, Level level){  // Battery can be moved on to and will be consumed.
		return true;
	}
	
	@Override
	public boolean movableInteract(ImageTile movedTile, Level level) {  // Nothing can be pushed on to a battery.
		return false;
	}
}
