package pt.iscte.poo.manuelcovas.sokoban;

import java.io.File;
import java.util.ArrayList;
import java.util.function.Consumer;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import pt.iscte.poo.manuelcovas.sokoban.level.Level;


public class Main {
	
	public static String WINDOW_TITLE = "POO Sokoban";
	private static String BACKEND_KEY = "c8c1742bf755f1447739ba9d0d7106cf";  // Project indentifier to connect to the online server.
	
	
	public static void main(String[] args) {
		
		int choice = JOptionPane.showOptionDialog(null, "Please select gamemode.", WINDOW_TITLE, JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, new Object[] {"Singleplayer", "Multiplayer"}, null);
	
		switch (choice) {
		case 0:
			singleplayer();
			break;

		case 1:
			launchMultiplayer();
			break;
			
		case -1:
			System.exit(0);
			return;
			
		default:
			JOptionPane.showMessageDialog(null, "An errror ocurred:\nUknown choice!", "Error", JOptionPane.ERROR_MESSAGE);
			break;
		}
	}
	
	
	private static void singleplayer() {
		File[] files;
		ArrayList<Level> levels = new ArrayList<Level>();
		ArrayList<String> failures = new ArrayList<String>();
		
		int choice = JOptionPane.showOptionDialog(null, "Which levels do you want to load?", WINDOW_TITLE,
														JOptionPane.DEFAULT_OPTION,
														JOptionPane.QUESTION_MESSAGE, null,
														new Object[] {"Default", "Select files..."}, null);
		
		switch (choice) {
		case 0:
			try {
				File defaultDir = new File(System.getProperty("user.dir")+File.separator+"levels");
				if (!defaultDir.exists())
					throw new Exception("Default directory is not present.");
				
				files = defaultDir.listFiles();
				
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Couldn't load default levels:\n"+e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
				return;
			}
			break;

		case 1:
			JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
			chooser.setDialogTitle("Select levels to load");
			chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
			chooser.setMultiSelectionEnabled(true);
			
			int status = chooser.showOpenDialog(null);
			files = chooser.getSelectedFiles();
			
			if (status != JFileChooser.APPROVE_OPTION || files.length == 0) {
				JOptionPane.showMessageDialog(null, "No levels were selected.\nExiting.", "Error", JOptionPane.ERROR_MESSAGE);
				System.exit(0);
				return;
			}
			break;
			
		case -1:
			System.exit(0);
			return;
			
		default:
			JOptionPane.showMessageDialog(null, "An errror ocurred:\nUknown choice!", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			return;
		}
		
		
		for (int i = 0; i < files.length; i++) {
			try {
				levels.add(new Level(files[i]));
			} catch (Exception e) {
				failures.add(files[i].getName()+": "+e.getMessage());
			}
		}
		
		String message = "Loaded "+levels.size()+" levels. Failed to load "+failures.size()+" levels.\n";
		for (int i = 0; i < failures.size(); i++) {
			message = message.concat(failures.get(i)+"\n");
		}
		JOptionPane.showMessageDialog(null, message, Main.WINDOW_TITLE, JOptionPane.INFORMATION_MESSAGE);
		
		if (levels.size() == 0) {
			JOptionPane.showMessageDialog(null, "No levels were selected.\nExiting.", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			return;
		}
	}
	
	private static void launchMultiplayer() {
		
	}
}