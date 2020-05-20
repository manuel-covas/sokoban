package pt.iscte.poo.manuelcovas.sokoban.level;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class LevelLoader {
	
	public static void defaultLevels(ArrayList<Level> levels, ArrayList<String> failures) throws Exception {
		File[] files;
		
		File defaultDir = new File(System.getProperty("user.dir")+File.separator+"levels");
		if (!defaultDir.exists())
			throw new Exception("Default directory is not present.");
		files = defaultDir.listFiles();
		
		for (int i = 0; i < files.length; i++) {
			try {
				levels.add(new Level(files[i]));
			} catch (Exception e) {
				failures.add(files[i].getName()+": "+e.getMessage());
			}
		}
	}
	
	
	public static void selectLevels(ArrayList<Level> levels, ArrayList<String> failures) throws Exception {
		File[] files;
		
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
		
		for (int i = 0; i < files.length; i++) {
			try {
				levels.add(new Level(files[i]));
			} catch (Exception e) {
				failures.add(files[i].getName()+": "+e.getMessage());
			}
		}
	}
	
	public static Level selectLevel() throws Exception {
		
		JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
		chooser.setDialogTitle("Select level to load");
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		chooser.setMultiSelectionEnabled(false);
		
		int status = chooser.showOpenDialog(null);
		File file = chooser.getSelectedFile();
		
		if (status != JFileChooser.APPROVE_OPTION || file == null) {
			JOptionPane.showMessageDialog(null, "No levels were selected.\nExiting.", "Error", JOptionPane.ERROR_MESSAGE);
			System.exit(0);
			return null;
		}
		
		return new Level(file);
	}
}
