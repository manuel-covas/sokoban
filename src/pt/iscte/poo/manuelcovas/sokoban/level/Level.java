package pt.iscte.poo.manuelcovas.sokoban.level;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.zip.CRC32;

import pt.iscte.poo.manuelcovas.sokoban.tiles.*;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.utils.Point2D;


public class Level {
	
	private String name, contents = "", hash;
	public int scoreMoves;
	private int width = 0;
	private int height = 0;
	
	public ArrayList<Target> targets = new ArrayList<Target>();
	public ArrayList<Portal> portals = new ArrayList<Portal>();
	
	private boolean lost = false;
	private String lossCause = "";
	
	private Player player;
	private ArrayList<ImageTile> tiles = new ArrayList<ImageTile>();
	
	
	public Level(File file) throws Exception {  // Setup scanner and attempt to parse level
		name = file.getName();
		
		Scanner scanner = new Scanner(file);
		parseData(scanner);
	}
	
	public Level(String data, String name) throws Exception {  // Setup scanner and attempt to parse level
		this.name = name;
		
		Scanner scanner = new Scanner(data);
		parseData(scanner);
	}
	
	
	private void parseData(Scanner scanner) throws Exception {
		int y = 0;
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			contents = contents + line + "\n";
			width = (line.length() > width) ? line.length() : width;
			
			for (int x = 0; x < line.length(); x++) {
				switch (line.charAt(x))
				{
					case '#':
						tiles.add(new Wall(x, y, false));
					break;
					
					case '%':
						tiles.add(new Wall(x, y, true));
					break;
					
					case 'g':
						tiles.add(new Ice(x, y));
					break;
					
					case 'm':
						tiles.add(new Hammer(x, y));
					break;
					
					case 't':
						Portal newPortal = new Portal(x, y, portals.isEmpty() ? null : portals.get(0));
						tiles.add(newPortal);
						portals.add(newPortal);
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
						Target target = new Target(x, y);
						tiles.add(target);
						targets.add(target);
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
		
		CRC32 crc32 = new CRC32();  // Non secure hash, used for easy level comparison.
		crc32.update(contents.getBytes());
		hash = Long.toHexString(crc32.getValue());
	}
	
	
	public String getName() {
		return name;
	}
	
	public String getContents() {
		return contents;
	}
	
	public String getHash() {
		return hash;
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
	
	
	public ArrayList<ImageTile> getTiles() {
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
	
	
	public boolean isInBounds(Point2D location) {
		int x = location.getX(), y = location.getY();
		return x >= 0 && x < width  &&  y >= 0 && y < height;
	}
}
