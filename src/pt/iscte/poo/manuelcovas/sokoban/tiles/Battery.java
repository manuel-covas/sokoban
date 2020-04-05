package pt.iscte.poo.manuelcovas.sokoban.tiles;

import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;

public class Battery implements ImageTile {
	
	private static String name = "Bateria";
	private static int layer = 3;
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

}
