package pt.iscte.poo.manuelcovas.sokoban;

import pt.iscte.poo.manuelcovas.sokoban.level.Level;
import pt.iscte.poo.manuelcovas.sokoban.tiles.Floor;
import pt.iul.ista.poo.gui.ImageMatrixGUI;

public class SokobanGameSP {
	
	Level[] levels;
	
	
	public SokobanGameSP(Level[] levels) {
		this.levels = levels;
	}
	
	
	private void gui() {
		ImageMatrixGUI.setSize(10, 10);
		ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
		
		for (int x = 0; x < 10; x++) {
			for (int y = 0; y < 10; y++) {
				gui.addImage(new Floor(x, y));
			}
		}
		
		gui.setName(Main.WINDOW_TITLE);
		gui.setStatusMessage("Loaded level.");
		gui.go();
	}
}
