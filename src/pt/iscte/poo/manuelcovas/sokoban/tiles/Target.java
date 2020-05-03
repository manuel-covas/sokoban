package pt.iscte.poo.manuelcovas.sokoban.tiles;

import java.util.ArrayList;

import pt.iscte.poo.manuelcovas.sokoban.level.Level;
import pt.iul.ista.poo.utils.Direction;

public class Target extends GameTile {

	private static String name = "Alvo";
	private static int layer = 1;
	
	public Target(int x, int y) {
		super(x, y, layer, name);
	}
	
	
	public boolean playerInteract(Direction direction, ArrayList<GameTile> tileGrid, Level level){  // Targets can be moved on.
		return true;
	}
	
	public boolean movableInteract(GameTile movedTile, Level level) {  // Anything can be pushed on to a Target
		return true;
	}
}
