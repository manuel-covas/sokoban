package pt.iscte.poo.manuelcovas.sokoban.tiles;

import java.util.ArrayList;

import pt.iscte.poo.manuelcovas.sokoban.GameTile;
import pt.iscte.poo.manuelcovas.sokoban.level.Level;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.utils.Direction;


@SuppressWarnings("rawtypes")
public class Hole extends GameTile implements TraversableTile {
	
	private static final Class[] consumesPushables = {SmallStone.class, BigStone.class, Box.class};
	private static final Class[] isFiledBy = {BigStone.class};
	
	private boolean full = false;
	private static int layer = 1;
	
	public Hole(int x, int y) {
		super(x, y, layer, null);
	}
	
	
	@Override
	public String getName() {
		return full ? "BuracoCheio" : "Buraco";
	}


	@Override
	public void traverse(GameTile movedTile, Direction movingDirection, ArrayList<GameTile> tileGrid, Level level) {    // Anything can be pushed in to a Hole and will be consumed if present in the
		if (full)											   // consumesMovables array, filling the Hole if present in the isFilledBy array.
			return;
		
		if (movedTile instanceof Player) {
			ImageMatrixGUI.getInstance().removeImage(level.getPlayer());
			level.removeTile(level.getPlayer());
			level.setLost("The player fell in a hole.");
			return;
		}
		
		for (int i = 0; i < consumesPushables.length; i++) {
			if (movedTile.getClass().isAssignableFrom(consumesPushables[i])) {
				ImageMatrixGUI.getInstance().removeImage(movedTile);
				level.removeTile(movedTile);
				
				for (int j = 0; j < isFiledBy.length; j++) {
					if (movedTile.getClass().isAssignableFrom(isFiledBy[j])) {
						full = true;
						return;
					}
				}
			}
		}
	}
}
