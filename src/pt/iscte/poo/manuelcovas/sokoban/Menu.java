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
	
	
	public static void onlineRace() {
		
		Object[] options = (Object[]) new String[] {"Create", "Join"};
		int choice = JOptionPane.showOptionDialog(null, new JLabel("Online Race", JLabel.CENTER), Main.WINDOW_TITLE, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
		
		if (choice == -1) {
			System.exit(0);
			return;
		}
		
		try {
			Level level = null;
			if (choice == 0)
				level = LevelLoader.selectLevel();
			
			String remoteHost = null;
			while (remoteHost == null) {
				try { remoteHost = JOptionPane.showInputDialog(null, "Where's the host at?", Main.DEFAULT_HOST); } catch (Exception e) {}
			}
			
			new SokobanGameMP(level, remoteHost, choice == 0 ? true : false);
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			return;
		}
	}
	
	
	public static void scoreboard() {
		Object[] options = (Object[]) new String[] {"Local", "Online", "Upload Scores"};
		int choice = JOptionPane.showOptionDialog(null, new JLabel("Scoreboard", JLabel.CENTER), Main.WINDOW_TITLE, JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
		
		switch (choice) {
		case 0:
			try {
				Scoreboard.localScoreboard();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Couldn't load local scoreboard:\n"+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			Main.main(null);
			break;

		case 1:
			try {
				String remoteHost = null;
				while (remoteHost == null) {
					try { remoteHost = JOptionPane.showInputDialog(null, "Where's the host at?", Main.DEFAULT_HOST); } catch (Exception e) {}
				}
				Scoreboard.remoteScoreboard(remoteHost);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Couldn't load remote scoreboard:\n"+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			Main.main(null);
			break;
		
		case 2:
			try {
				String remoteHost = null;
				while (remoteHost == null) {
					try { remoteHost = JOptionPane.showInputDialog(null, "Where's the host at?", Main.DEFAULT_HOST); } catch (Exception e) {}
				}
				Scoreboard.saveRemoteScores(remoteHost);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Couldn't update remote scoreboard:\n"+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
			}
			Main.main(null);
			break;
			
		default:
			System.exit(0);
			return;
		}
	}
}
