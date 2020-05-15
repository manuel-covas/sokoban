package pt.iscte.poo.manuelcovas.sokoban.tiles;

import pt.iscte.poo.manuelcovas.sokoban.GameTile;
import pt.iscte.poo.manuelcovas.sokoban.level.Level;
import pt.iul.ista.poo.gui.ImageMatrixGUI;

public class Hammer extends GameTile implements ConsumableTile {

	private static String name = "Martelo";
	private static int layer = 2;
	
	public Hammer(int x, int y) {
		super(x, y, layer, name);
	}
	
	@Override
	public void consume(Level level) {
		level.getPlayer().giveHammer();
		ImageMatrixGUI.getInstance().removeImage(this);
		level.removeTile(this);
	}
}
