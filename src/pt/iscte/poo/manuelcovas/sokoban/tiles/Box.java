package pt.iscte.poo.manuelcovas.sokoban.tiles;

import java.util.ArrayList;

import pt.iscte.poo.manuelcovas.sokoban.GameTile;
import pt.iscte.poo.manuelcovas.sokoban.level.Level;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Box extends GameTile implements PushableTile {

	private static String name = "Caixote";
	private static int layer = 2;
	
	public Box(int x, int y) {
		super(x, y, layer, name);
	}
	
	
	@Override
	public boolean push(Direction direction, ArrayList<GameTile> tileGrid, Level level) {
		Point2D position = super.getPosition();
		Point2D candidatePosition = position.plus(direction.asVector());
		
		if (!level.isInBounds(candidatePosition))
			return false;
		
		GameTile movingOnTo = tileGrid.get(candidatePosition.getY() * level.getWidth() + candidatePosition.getX());
		
		if (movingOnTo instanceof TraversableTile) {
			super.setPosition(candidatePosition);
			((TraversableTile) movingOnTo).traverse(this, direction, tileGrid, level);
			return true;
		}else{
			return false;
		}
	}
}
