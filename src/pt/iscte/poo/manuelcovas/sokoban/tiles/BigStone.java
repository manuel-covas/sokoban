package pt.iscte.poo.manuelcovas.sokoban.tiles;

import pt.iscte.poo.manuelcovas.sokoban.level.Level;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class BigStone implements ImageTile {

	private static String name = "BigStone";
	private static int layer = 2;
	private Point2D position;
	
	public BigStone(int x, int y) {
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
	public boolean playerInteract(Direction direction, ImageTile[][] tileGrid, Level level){  // Big stones can be moved if the environment allows it.
		Point2D candidatePosition = position.plus(direction.asVector());
		ImageTile movingInTo = tileGrid[candidatePosition.getY()][candidatePosition.getX()];
		
		if (movingInTo.movableInteract(this, level)) {
			this.position = candidatePosition;
			return true;
		}else{
			return false;
		}
	}
	
	@Override
	public boolean movableInteract(ImageTile movedTile, Level level) {  // Nothing can be pushed on to a BigStone.
		return false;
	}
}