package pt.iscte.poo.manuelcovas.sokoban;

import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import pt.iscte.poo.manuelcovas.sokoban.level.Level;
import pt.iscte.poo.manuelcovas.sokoban.level.LevelLoader;

public class Menu {
	
	public static int mainMenu() {
		Object[] options = (Object[]) new String[] {"Singleplayer", "Online Race", "Scoreboard"};
		return JOptionPane.showOptionDialog(null, new JLabel("Main Menu", JLabel.CENTER), Main.WINDOW_TITLE, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
	}
	
	
	public static void singlePlayer() {
		Object[] options = (Object[]) new String[] {"Default", "Load Levels..."};
		int choice = JOptionPane.showOptionDialog(null, new JLabel("Singleplayer", JLabel.CENTER), Main.WINDOW_TITLE, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
		
		ArrayList<Level> levels = new ArrayList<Level>();
		ArrayList<String> failures = new ArrayList<String>();
		
		
		switch (choice) {
			case 0:
				try {
					LevelLoader.defaultLevels(levels, failures);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Couldn't load default levels:\n"+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				break;

			case 1:
				try {
					LevelLoader.selectLevels(levels, failures);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Couldn't load default levels:\n"+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
					return;
				}
				break;
			
			default:
				System.exit(0);
				return;
		}
		
		
		String message = "Loaded "+levels.size()+" levels. Failed to load "+failures.size()+" levels.\n";
		for (int i = 0; i < failures.size(); i++) {
			message = message.concat(failures.get(i)+"\n");
		}
		JOptionPane.showMessageDialog(null, message, Main.WINDOW_TITLE, JOptionPane.INFORMATION_MESSAGE);
		
		if (levels.size() == 0) {
			JOptionPane.showMessageDialog(null, "No levels were loaded.\nExiting.", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			return;
		}
		
		new SokobanGameSP(0, levels);
	}
}
