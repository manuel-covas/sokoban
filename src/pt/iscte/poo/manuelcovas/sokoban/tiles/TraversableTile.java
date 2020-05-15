package pt.iscte.poo.manuelcovas.sokoban.tiles;

import java.util.ArrayList;

import pt.iscte.poo.manuelcovas.sokoban.GameTile;
import pt.iscte.poo.manuelcovas.sokoban.level.Level;
import pt.iul.ista.poo.utils.Direction;

public interface TraversableTile {
	
	public void traverse(GameTile movedTile, Direction movingDirection, ArrayList<GameTile> tileGrid, Level level);
	
}
