package pt.iscte.poo.manuelcovas.sokoban;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public class GameTile implements ImageTile {
	
	private Point2D position;
	private int layer;
	private String name;
	
	public GameTile(int x, int y, int z, String n) {
		position = new Point2D(x, y);
		layer = z;
		name = n;
	}

	
	public String getName() {
		return name;
	}

	
	public Point2D getPosition() {
		return position;
	}
	
	public void setPosition(Point2D point) {
		position = point;
	}
	
	
	public int getLayer() {
		return layer;
	}
}
