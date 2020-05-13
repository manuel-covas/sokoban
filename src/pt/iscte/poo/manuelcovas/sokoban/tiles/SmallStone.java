package pt.iscte.poo.manuelcovas.sokoban.tiles;

import java.util.ArrayList;

import pt.iscte.poo.manuelcovas.sokoban.GameTile;
import pt.iscte.poo.manuelcovas.sokoban.level.Level;
import pt.iul.ista.poo.utils.Direction;

public class SmallStone extends GameTile {

	private static String name = "SmallStone";
	private static int layer = 2;
	
	public SmallStone(int x, int y) {
		super(x, y, layer, name);
	}
	
	
	public boolean playerInteract(Direction direction, ArrayList<GameTile> tileGrid, Level level){  // Small stones can be moved if the environment allows it.
		return true;
	}
	
	public boolean movableInteract(GameTile movedTile, Level level) {  // Nothing can be pushed on to a SmallStone
		return false;
	}
}
