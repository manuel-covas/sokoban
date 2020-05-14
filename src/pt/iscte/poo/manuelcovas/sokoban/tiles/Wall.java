package pt.iscte.poo.manuelcovas.sokoban.tiles;

import pt.iscte.poo.manuelcovas.sokoban.GameTile;


public class Wall extends GameTile {

	private static String name = "Parede";
	private static int layer = 1;
	
	public Wall(int x, int y) {
		super(x, y, layer, name);
	}
}