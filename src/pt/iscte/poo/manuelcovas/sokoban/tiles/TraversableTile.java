package pt.iscte.poo.manuelcovas.sokoban.tiles;

import pt.iscte.poo.manuelcovas.sokoban.GameTile;
import pt.iscte.poo.manuelcovas.sokoban.level.Level;

public interface TraversableTile {
	
	public void traverse(GameTile movedTile, Level level);
	
}
