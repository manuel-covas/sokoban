package pt.iscte.poo.manuelcovas.sokoban;

import java.util.ArrayList;

import pt.iscte.poo.manuelcovas.sokoban.level.Level;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public abstract class GameTile implements ImageTile {
	
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
	
	
	// To be implemented by each game tile child
	abstract public boolean playerInteract(Direction direction, ArrayList<GameTile> tileGrid, Level level);
	
	// To be implemented by each game tile child
	abstract public boolean movableInteract(GameTile movedTile, Level level);
}
