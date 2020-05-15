package pt.iscte.poo.manuelcovas.sokoban;

import java.util.ArrayList;
import java.util.function.Consumer;
import javax.swing.JOptionPane;

import pt.iscte.poo.manuelcovas.sokoban.level.Level;
import pt.iscte.poo.manuelcovas.sokoban.tiles.Player;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class SokobanGameSP implements Observer {
	
	private static final int KEY_UP = 38, KEY_DOWN = 40, KEY_RIGHT = 39, KEY_LEFT = 37;
	
	private int levelIndex;
	private Level level;
	private ArrayList<Level> levels;
	private ArrayList<GameTile> tileGrid;
	private ImageMatrixGUI gui;
	
	public SokobanGameSP(int i, ArrayList<Level> levels) {
		levelIndex = i;
		this.levels = levels;
		level = levels.get(levelIndex);
		init();
	}
	
	
	private void init() {
		ImageMatrixGUI.setSize(level.getWidth(), level.getHeight());
		refreshTileGrid();
		
		gui = ImageMatrixGUI.getInstance();
		gui.addImages(level.getTiles());
		gui.setName(level.getName() + " ("+level.getHash()+")");
		gui.setStatusMessage("Move to begin.");
		gui.registerObserver(this);
		gui.go();
	}
	

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
		gui.setStatusMessage("Moves: "+player.getMoves()+" Energy: "+player.getEnergy());
		gui.update();
		
		if (checkWin()) {
			level.scoreMoves = player.getMoves();
			JOptionPane.showMessageDialog(null, "Level completed.", "Sokoban", JOptionPane.INFORMATION_MESSAGE);
			
			if (levelIndex == (levels.size() - 1)) {
				JOptionPane.showMessageDialog(null, "No more levels.", "Sokoban", JOptionPane.INFORMATION_MESSAGE);
				
				String name = null;
				while (name == null) {
					try { name = JOptionPane.showInputDialog("What's your name?").replaceAll(":", "").replaceAll("\n", ""); } catch (Exception e) {}
				}
					
				Scoreboard.saveScores(levels, name);
				gui.dispose();
				Main.main(null);
				return;
			}
			gui.unregisterObserver(this);
			gui.clearImages();
			gui.update();
			new SokobanGameSP(levelIndex + 1, levels);
		}
		
		checkLoss(player);
	}
	
	
	boolean checkWin() {
		for (int i = 0; i < level.targets.size(); i++) {
			if (!level.targets.get(i).full)
				return false; 
		}
		return true;
	}
	void checkLoss(Player player) {
		
		if (player.getEnergy() == 0) {
			level.setLost("No more energy available!");
		}
		
		if (level.isLost()) {
			JOptionPane.showMessageDialog(null, level.getLossCause(), "Game Over", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
		}
	}
	
	
	private void refreshTileGrid() {
		tileGrid = new ArrayList<GameTile>(level.getWidth()*level.getHeight());
		
		for (int i = 0; i < level.getWidth()*level.getHeight(); i++) {
			tileGrid.add(null);
		}

		level.getTiles().forEach(new Consumer<GameTile>() {
			@Override
			public void accept(GameTile tile) {
				
				Point2D position = tile.getPosition();
				int index = position.getY()*level.getWidth() + position.getX();
				
				GameTile current = tileGrid.get(index);
				
				if (current == null || current.getLayer() < tile.getLayer())
					tileGrid.set(index, tile);
			}
		});
	}
}
