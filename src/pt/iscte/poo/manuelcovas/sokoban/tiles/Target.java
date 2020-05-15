package pt.iscte.poo.manuelcovas.sokoban.tiles;

import java.util.ArrayList;

import pt.iscte.poo.manuelcovas.sokoban.GameTile;
import pt.iscte.poo.manuelcovas.sokoban.level.Level;
import pt.iul.ista.poo.utils.Direction;


public class Target extends GameTile implements TraversableTile {

	private static String name = "Alvo";
	private static int layer = 1;
	public boolean full = false;
	
	public Target(int x, int y) {
		super(x, y, layer, name);
	}
	
	
	@Override
	public void traverse(GameTile movedTile, Direction movingDirection, ArrayList<GameTile> tileGrid, Level level) {
		if (movedTile.getName().equalsIgnoreCase("Caixote")) {
			full = true;
		}else{
			full = false;
		}
	}
}
