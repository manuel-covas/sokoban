package pt.iscte.poo.manuelcovas.sokoban.tiles;

import pt.iscte.poo.manuelcovas.sokoban.level.Level;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Player implements ImageTile{
	
	private static int layer = 3;
	private Point2D position;
	private Direction direction;
	private int energy = 100;
	
	public Player(int x, int y) {
		position = new Point2D(x, y);
		direction = Direction.UP;
	}
	
	
	public void move(Direction newDirection, ImageTile[][] tileGrid, ImageMatrixGUI gui, Level level) {
		direction = newDirection;
		
		Point2D moveCandidate = position.plus(direction.asVector());
		
		if (gui.isWithinBounds(moveCandidate)) {
			
			ImageTile interactedTile = tileGrid[moveCandidate.getY()][moveCandidate.getX()];
			
			if (interactedTile != null && interactedTile.playerInteract(newDirection, tileGrid, level)) {
				position = moveCandidate;
				energy--;
			}
		}
	}
	
	
	@Override
	public String getName() {
		switch (direction) {
			case UP:
				return "Empilhadora_U";
			case DOWN:
				return "Empilhadora_D";
			case LEFT:
				return "Empilhadora_L";
			case RIGHT:
				return "Empilhadora_R";
			default:
				return "Empilhadora_U";
		}
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return layer;
	}
	
	
	public int getEnergy() {
		return energy;
	}
	
	
	@Override
	public boolean playerInteract(Direction direction, ImageTile[][] tileGrid, Level level) {  // Players can not be moved.
		return false;
	}
	
	@Override
	public boolean movableInteract(ImageTile movedTile, Level level) {  // Nothing can be pushed on to a Player
		return false;
	}
}
