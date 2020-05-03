package pt.iscte.poo.manuelcovas.sokoban.tiles;

import java.util.ArrayList;

import pt.iscte.poo.manuelcovas.sokoban.level.Level;
import pt.iul.ista.poo.utils.Direction;


public class Wall extends GameTile {

	private static String name = "Parede";
	private static int layer = 1;
	
	public Wall(int x, int y) {
		super(x, y, layer, name);
	}
	
	
	public boolean playerInteract(Direction direction, ArrayList<GameTile> tileGrid, Level level){  // Walls can't be pushed.
		return false;
	}
	
	public boolean movableInteract(GameTile movedTile, Level level) {  // Nothing can be pushed on to a Wall
		return false;
	}
}
