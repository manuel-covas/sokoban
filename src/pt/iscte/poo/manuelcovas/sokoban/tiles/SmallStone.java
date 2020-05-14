package pt.iscte.poo.manuelcovas.sokoban.tiles;

import java.util.ArrayList;

import pt.iscte.poo.manuelcovas.sokoban.GameTile;
import pt.iscte.poo.manuelcovas.sokoban.level.Level;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class SmallStone extends GameTile implements PushableTile {

	private static String name = "SmallStone";
	private static int layer = 2;
	
	public SmallStone(int x, int y) {
		super(x, y, layer, name);
	}
	
	
	@Override
	public boolean push(Direction direction, ArrayList<GameTile> tileGrid, Level level) {
		Point2D position = super.getPosition();
		Point2D candidatePosition = position.plus(direction.asVector());
		GameTile movingOnTo = tileGrid.get(candidatePosition.getY() * level.getWidth() + candidatePosition.getX());
		
		if (movingOnTo instanceof TraversableTile) {
			((TraversableTile) movingOnTo).traverse(this, level);
			super.setPosition(candidatePosition);
			return true;
		}else{
			return false;
		}
	}
}