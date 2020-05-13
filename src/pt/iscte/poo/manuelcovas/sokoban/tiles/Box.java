package pt.iscte.poo.manuelcovas.sokoban.tiles;

import java.util.ArrayList;

import pt.iscte.poo.manuelcovas.sokoban.GameTile;
import pt.iscte.poo.manuelcovas.sokoban.level.Level;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class Box extends GameTile {

	private static String name = "Caixote";
	private static int layer = 2;
	
	public Box(int x, int y) {
		super(x, y, layer, name);
	}
	

	public boolean playerInteract(Direction direction, ArrayList<GameTile> tileGrid, Level level) {  // Boxes can be moved if the environment allows it.
		
		Point2D position = super.getPosition();
		Point2D candidatePosition = position.plus(direction.asVector());
		GameTile movingInTo = tileGrid.get(candidatePosition.getY() * level.getWidth() + candidatePosition.getX());
		
		if (movingInTo.movableInteract(this, level)) {
			super.setPosition(candidatePosition);
			return true;
		}else{
			return false;
		}
	}
	
	public boolean movableInteract(GameTile movedTile, Level level) {  // Nothing can be pushed on to a Box.
		return false;
	}
}
