package pt.iscte.poo.manuelcovas.sokoban.tiles;

import java.util.ArrayList;

import pt.iscte.poo.manuelcovas.sokoban.GameTile;
import pt.iscte.poo.manuelcovas.sokoban.level.Level;
import pt.iul.ista.poo.utils.Direction;


public class Floor extends GameTile implements TraversableTile {

	private static String name = "Chao";
	private static int layer = 0;
	
	public Floor(int x, int y) {
		super(x, y, layer, name);
	}
	
	
	@Override
	public void traverse(GameTile movedTile, Direction movingDirection, ArrayList<GameTile> tileGrid, Level level) {
		// Nothing to do
	}
}
