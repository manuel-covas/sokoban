package pt.iscte.poo.manuelcovas.sokoban.tiles;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public class PlayerLeft implements ImageTile{

	private static String name = "Empilhadora_L";
	private static int layer = 4;
	private Point2D position;
	
	public PlayerLeft(int x, int y) {
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
	
}
