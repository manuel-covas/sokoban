package pt.iscte.poo.manuelcovas.sokoban.level;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import pt.iscte.poo.manuelcovas.sokoban.tiles.*;
import pt.iul.ista.poo.gui.ImageTile;

public class Level {
	
	private ArrayList<ImageTile> tiles = new ArrayList<ImageTile>();
	private int width = 0;
	private int height = 0;
	
	
	public Level(File file) throws Exception {  // Setup scanner and attempt to parse level
		Scanner scanner = new Scanner(file);
		int y = 0;
		
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			width = (line.length() > width) ? line.length() : width;
			
			for (int x = 0; x < line.length(); x++) {
				switch (line.charAt(x))
				{
					case ' ':
						tiles.add(new Floor(x, y));
					break;
					
					case '#':
						tiles.add(new Wall(x, y));
					break;
					
					default:
						throw new Exception("Unknown tile: '"+line.charAt(x)+"' at line "+(y+1)+" column "+(x+1)+".");
				}
			} y++;
		}
		
		height = y;
		scanner.close();
	}
}
