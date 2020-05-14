package pt.iscte.poo.manuelcovas.sokoban.tiles;

import java.util.ArrayList;
import java.util.function.Consumer;

import pt.iscte.poo.manuelcovas.sokoban.GameTile;
import pt.iscte.poo.manuelcovas.sokoban.level.Level;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Player extends GameTile {
	
	private static int layer = 3;
	private Direction direction;
	private int energy = 100;
	private int moves = 0;
	
	public Player(int x, int y) {
		super(x, y, layer, null);
		direction = Direction.UP;
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
	
	
	public int getMoves() {
		return moves;
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
			
			if (interactedTile == null)
				return;
			
			if (interactedTile instanceof TraversableTile)
			{
				((TraversableTile) interactedTile).traverse(this, level);
			}
			else if (interactedTile instanceof PushableTile)
			{
				if (!((PushableTile) interactedTile).push(direction, tileGrid, level))
					return;
			}
			else if (interactedTile instanceof ConsumableTile)
			{
				((ConsumableTile) interactedTile).consume(level);
			}
			else { return; }
			
			updateAffectedTarget(level, moveCandidate);
			super.setPosition(moveCandidate);
			
			energy--;
			moves++;
			return;
		}
	}
	
	
	private void updateAffectedTarget(Level level, Point2D movedOnTo) {
		level.targets.forEach(new Consumer<Target>() {
			@Override
			public void accept(Target target) {
				if (target.getPosition().equals(movedOnTo)) {
					target.full = false;
				}
			}
		});
	}
}
