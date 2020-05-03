package pt.iscte.poo.manuelcovas.sokoban.level;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import pt.iscte.poo.manuelcovas.sokoban.tiles.*;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;


public class Level {
	
	private String name;
	private int width = 0;
	private int height = 0;
	
	private boolean lost = false;
	private String lossCause = "";
	
	private Player player;
	private ArrayList<GameTile> tiles = new ArrayList<GameTile>();
	
	
	public Level(File file) throws Exception {  // Setup scanner and attempt to parse level
		name = file.getName();
		
		Scanner scanner = new Scanner(file);
		int y = 0;
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			width = (line.length() > width) ? line.length() : width;
			
			for (int x = 0; x < line.length(); x++) {
				switch (line.charAt(x))
				{
					case '#':
						tiles.add(new Wall(x, y));
					break;
					
					case 'b':
						tiles.add(new Battery(x, y));
					break;
					
					case 'P':
						tiles.add(new BigStone(x, y));
					break;
					
					case 'C':
						tiles.add(new Box(x, y));
					break;
					
					case 'O':
						tiles.add(new Hole(x, y));
					break;
					
					case 'E':
						if (player != null)
							throw new Exception("More than one player position was specified.");
						player = new Player(x, y);
						tiles.add(player);
					break;
					
					case 'p':
						tiles.add(new SmallStone(x, y));
					break;
					
					case 'X':
						tiles.add(new Target(x, y));
					break;
					
					case ' ':
						break;
					
					default:
						throw new Exception("Unknown tile: '"+line.charAt(x)+"' at line "+(y+1)+" column "+(x+1)+".");
				}
				tiles.add(new Floor(x, y));
			} y++;
		}
		scanner.close();
		height = y;
	}
	
	
	public String getName() {
		return name;
	}
	
	public int getWidth() {
		return width;
	}
	public int getHeight() {
		return height;
	}
	
	public Player getPlayer() {
		return player;
	}
	
	
	public ArrayList<GameTile> getTiles() {
		return tiles;
	}
	
	public void removeTile(ImageTile tile) {
		tiles.remove(tile);
	}
	
	
	public boolean isLost() {
		return lost;
	}
	public void setLost(String cause) {
		lost = true;
		lossCause = cause;
	}
	public String getLossCause() {
		return lossCause;
	}
	
	
	public boolean isFinished() {
		return false;
	}
}
