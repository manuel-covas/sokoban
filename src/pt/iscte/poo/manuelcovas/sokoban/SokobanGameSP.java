package pt.iscte.poo.manuelcovas.sokoban;

import java.util.ArrayList;
import java.util.function.Consumer;

import javax.swing.JOptionPane;

import pt.iscte.poo.manuelcovas.sokoban.level.Level;
import pt.iscte.poo.manuelcovas.sokoban.tiles.Player;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class SokobanGameSP implements Observer {
	
	private static final int KEY_UP = 38, KEY_DOWN = 40, KEY_RIGHT = 39, KEY_LEFT = 37;
	
	private ArrayList<Level> levels;
	private Level level;
	private int[] levelDimensions;
	private int levelIndex;
	
	private ImageTile[][] tileGrid;
	private ImageMatrixGUI gui;
	
	
	public SokobanGameSP(int i, ArrayList<Level> l) {
		this.levelIndex = i;
		this.level = l.get(0);
		this.levels = l;
		init();
	}
	
	
	private void init() {
		levelDimensions = level.getDimensions();
		
		ImageMatrixGUI.setSize(levelDimensions[0], levelDimensions[1]);
		refreshTileGrid();
		
		gui = ImageMatrixGUI.getInstance();
		gui.addImages(level.getTiles());
		gui.setName(level.getName());
		gui.setStatusMessage("Move to begin.");
		gui.registerObserver(this);
		gui.go();
	}
	

	@Override
	public void update(Observed source) {
		
		int pressedKey = gui.keyPressed();
		Direction newDirection;
		
		switch (pressedKey) {
			case KEY_UP:
				newDirection = Direction.UP;
				break;
			case KEY_DOWN:
				newDirection = Direction.DOWN;
				break;
			case KEY_LEFT:
				newDirection = Direction.LEFT;
				break;
			case KEY_RIGHT:
				newDirection = Direction.RIGHT;
				break;

			default:
				return;
		}
		
		Player player = level.getPlayer();
		player.move(newDirection, tileGrid, gui, level);
		
		refreshTileGrid();
		gui.setStatusMessage("Energy: "+player.getEnergy());
		gui.update();
		
		
		if (player.getEnergy() == 0) {
			level.setLost("No more energy available!");
		}
		
		if (level.isLost()) {
			JOptionPane.showMessageDialog(null, level.getLossCause(), "Game Over", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
	}
	
	
	private void refreshTileGrid() {
		tileGrid = new ImageTile[levelDimensions[1]][levelDimensions[0]];
		level.getTiles().forEach(new Consumer<ImageTile>() {
			@Override
			public void accept(ImageTile tile) {
				
				Point2D position = tile.getPosition();
				int x = position.getX(), y = position.getY();
				
				ImageTile current = tileGrid[y][x];
				
				if (current == null || current.getLayer() < tile.getLayer())
					tileGrid[y][x] = tile;
			}
		});
	}
}
