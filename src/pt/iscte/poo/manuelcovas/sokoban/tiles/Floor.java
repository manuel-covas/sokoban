package pt.iscte.poo.manuelcovas.sokoban.tiles;

import java.util.ArrayList;

import pt.iscte.poo.manuelcovas.sokoban.level.Level;
import pt.iul.ista.poo.utils.Direction;


public class Floor extends GameTile {

	private static String name = "Chao";
	private static int layer = 0;
	
	public Floor(int x, int y) {
		super(x, y, layer, name);
	}

	
	public boolean playerInteract(Direction direction, ArrayList<GameTile> tileGrid, Level level){  // Floor can always be moved on.
		return true;
	}
	
	public boolean movableInteract(GameTile movedTile, Level level) {  // Anything can be pushed on to the floor.
		return true;
	}
}
