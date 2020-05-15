package pt.iscte.poo.manuelcovas.sokoban.tiles;

import java.util.ArrayList;

import pt.iscte.poo.manuelcovas.sokoban.GameTile;
import pt.iscte.poo.manuelcovas.sokoban.level.Level;
import pt.iul.ista.poo.utils.Direction;

public class Portal extends GameTile implements TraversableTile {
	
	public Portal pair;
	private boolean first;
	private static int layer = 1;
	
	public Portal(int x, int y, Portal pair) {
		super(x, y, layer, null);
		
		if (pair == null) {
			first = true;
		}else{
			first = false;
			this.pair = pair;
			pair.pair = this;
		}	
	}
	
	@Override
	public String getName() {
		return first ? "Portal_Azul" : "Portal_Verde";
	}
	
	
	@Override
	public void traverse(GameTile movedTile, Direction movingDirection, ArrayList<GameTile> tileGrid, Level level) {
		if (tileGrid.get(pair.getPosition().getY() * level.getWidth() + pair.getPosition().getX()) instanceof TraversableTile) {
			movedTile.setPosition(pair.getPosition());
		}else{
			movedTile.setPosition(this.getPosition());
		}
	}
}
