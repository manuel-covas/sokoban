package pt.iscte.poo.manuelcovas.sokoban.tiles;

import pt.iscte.poo.manuelcovas.sokoban.level.Level;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Target implements ImageTile {

	private static String name = "Alvo";
	private static int layer = 1;
	private Point2D position;
	
	public Target(int x, int y) {
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
	public boolean playerInteract(Direction direction, ImageTile[][] tileGrid, Level level){  // Targets can be moved on.
		return true;
	}
	
	@Override
	public boolean movableInteract(ImageTile movedTile, Level level) {  // Anything can be pushed on to a Target
		return true;
	}
}
