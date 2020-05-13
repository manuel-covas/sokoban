package pt.iscte.poo.manuelcovas.sokoban.tiles;

import java.util.ArrayList;

import pt.iscte.poo.manuelcovas.sokoban.GameTile;
import pt.iscte.poo.manuelcovas.sokoban.level.Level;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;

public class Battery extends GameTile {
	
	private static String name = "Bateria";
	private static int layer = 2;
	
	public Battery(int x, int y) {
		super(x, y, layer, name);
	}

	
	public boolean playerInteract(Direction direction, ArrayList<GameTile> tileGrid, Level level){  // Battery can be moved on to and will be consumed.
		level.getPlayer().restoreEnergy();
		ImageMatrixGUI.getInstance().removeImage(this);
		level.removeTile(this);
		return true;
	}
	
	public boolean movableInteract(GameTile movedTile, Level level) {  // Nothing can be pushed on to a battery.
		return false;
	}
}
