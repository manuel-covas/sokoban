package pt.iscte.poo.manuelcovas.sokoban.tiles;

import java.util.ArrayList;

import pt.iscte.poo.manuelcovas.sokoban.level.Level;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Player extends GameTile {
	
	private static int layer = 3;
	private Direction direction;
	private int energy = 100;
	
	public Player(int x, int y) {
		super(x, y, layer, null);
		direction = Direction.UP;
	}
	
	
	public int getEnergy() {
		return energy;
	}
	
	public void restoreEnergy() {
		energy = 100;
	}
	
	public void move(Direction newDirection, ArrayList<GameTile> tileGrid, ImageMatrixGUI gui, Level level) {
		direction = newDirection;
		
		Point2D position = super.getPosition();
		Point2D moveCandidate = position.plus(direction.asVector());
		
		if (gui.isWithinBounds(moveCandidate)) {
			
			GameTile interactedTile = tileGrid.get(moveCandidate.getY()*level.getWidth() + moveCandidate.getX());
			
			if (interactedTile != null && interactedTile.playerInteract(newDirection, tileGrid, level)) {
				super.setPosition(moveCandidate);
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

	
	public boolean playerInteract(Direction direction, ArrayList<GameTile> tileGrid, Level level) {  // Players can not be moved.
		return false;
	}
	
	public boolean movableInteract(GameTile movedTile, Level level) {  // Nothing can be pushed on to a Player
		return false;
	}
}
