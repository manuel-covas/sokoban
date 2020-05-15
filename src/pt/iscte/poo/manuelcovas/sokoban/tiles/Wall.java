package pt.iscte.poo.manuelcovas.sokoban.tiles;

import pt.iscte.poo.manuelcovas.sokoban.GameTile;


public class Wall extends GameTile {

	private boolean breakable;
	private static int layer = 1;
	
	public Wall(int x, int y, boolean breakable) {
		super(x, y, layer, null);
		this.breakable = breakable;
	}
	
	
	@Override
	public String getName() {
		return breakable ? "Parede_Partida" : "Parede";
	}
	
	public boolean isBreakable() {
		return breakable;
	}
}