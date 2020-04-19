package pt.iscte.poo.manuelcovas.sokoban.tiles;

import pt.iscte.poo.manuelcovas.sokoban.level.Level;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

@SuppressWarnings("rawtypes")
public class Hole implements ImageTile {

	private static final Class[] consumesMovables = {SmallStone.class, BigStone.class, Box.class};
	private static final Class[] isFiledBy = {BigStone.class};
	
	private boolean full = false;
	private static int layer = 1;
	private Point2D position;
	
	public Hole(int x, int y) {
		position = new Point2D(x, y);
	}
	
	
	@Override
	public String getName() {
		return full ? "BuracoCheio" : "Buraco";
	}

	@Override
	public Point2D getPosition() {
		return position;
	}

	@Override
	public int getLayer() {
		return layer;
	}
	
	
	@Override
	public boolean playerInteract(Direction direction, ImageTile[][] tileGrid, Level level){  // Holes can be moved in to.
		if (full)
			return true;
		
		ImageMatrixGUI.getInstance().removeImage(level.getPlayer());
		level.removeTile(level.getPlayer());
		level.setLost("The player fell in a hole.");
		
		return true;
	}
	
	@Override
	public boolean movableInteract(ImageTile movedTile, Level level) {  // Anything can be pushed in to a Hole and will be consumed if present in the
		if (!full) {													// consumesMovables array, filling the Hole if present in the isFilledBy array.
			for (int i = 0; i < consumesMovables.length; i++) {
				if (movedTile.getClass().isAssignableFrom(consumesMovables[i])) {
					ImageMatrixGUI.getInstance().removeImage(movedTile);
					level.removeTile(movedTile);
					
					for (int j = 0; j < isFiledBy.length; j++) {
						if (movedTile.getClass().isAssignableFrom(isFiledBy[j])) {
							full = true;
							return true;
						}
					}
				}
			}
		}
		
		return true;
	}
}
