package pt.iscte.poo.manuelcovas.sokoban;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.function.Consumer;
import javax.swing.JOptionPane;

import pt.iscte.poo.manuelcovas.sokoban.backend.BackendOperations;
import pt.iscte.poo.manuelcovas.sokoban.level.Level;
import pt.iscte.poo.manuelcovas.sokoban.tiles.Battery;
import pt.iscte.poo.manuelcovas.sokoban.tiles.BigStone;
import pt.iscte.poo.manuelcovas.sokoban.tiles.Box;
import pt.iscte.poo.manuelcovas.sokoban.tiles.Floor;
import pt.iscte.poo.manuelcovas.sokoban.tiles.Hammer;
import pt.iscte.poo.manuelcovas.sokoban.tiles.Hole;
import pt.iscte.poo.manuelcovas.sokoban.tiles.Ice;
import pt.iscte.poo.manuelcovas.sokoban.tiles.Player;
import pt.iscte.poo.manuelcovas.sokoban.tiles.Portal;
import pt.iscte.poo.manuelcovas.sokoban.tiles.SmallStone;
import pt.iscte.poo.manuelcovas.sokoban.tiles.Target;
import pt.iscte.poo.manuelcovas.sokoban.tiles.Wall;
import pt.iul.ista.poo.gui.ImageMatrixGUI;
import pt.iul.ista.poo.gui.ImageTile;
import pt.iul.ista.poo.observer.Observed;
import pt.iul.ista.poo.observer.Observer;
import pt.iul.ista.poo.utils.Direction;
import pt.iul.ista.poo.utils.Point2D;

public class SokobanGameMP implements Observer {

	private static final int KEY_UP = 38, KEY_DOWN = 40, KEY_RIGHT = 39, KEY_LEFT = 37;

	private String matchCode;
	private boolean matchStarted = false;
	private boolean isMatchHost;
	private Level level;
	private ArrayList<GameTile> tileGrid;
	private ArrayList<ImageTile> remoteTileGrid;
	private ImageMatrixGUI gui;
	private SokobanGameMP self;
	
	private Socket serverSocket;
	String delimiter     = new String(new byte[] { 0x01 });
	String opponentLeft  = new String(new byte[] { 0x02 });
	String victoryMarker = new String(new byte[] { 0x03 });
	String matchNotFound = new String(new byte[] { 0x04 });
	
	
	public SokobanGameMP(Level level, String remoteHost, boolean matchHost) throws Exception {
		this.level = level;
		this.isMatchHost = matchHost;
		self = this;

		if (matchHost) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						serverSocket = new Socket();
						serverSocket.connect(new InetSocketAddress(remoteHost, Main.HOST_PORT), Main.CONNECTION_TIMEOUT);

						OutputStream socketOut = serverSocket.getOutputStream();
						socketOut.write(BackendOperations.START_MATCH.getByte());
						socketOut.write(level.getContents().getBytes());
						socketOut.write(0x01);
						
						InputStream socketIn = serverSocket.getInputStream();
						StringBuilder responseBuilder = new StringBuilder();

						while (true) {
							int incomingByte = socketIn.read();
							if (incomingByte == -1)
								break;
							responseBuilder.append((char) incomingByte);

							if (responseBuilder.length() == 6 && matchCode == null) { // Wait for match code from server
								matchCode = responseBuilder.toString();
								self.init();
								responseBuilder = new StringBuilder();
							}

							if (matchCode != null && responseBuilder.indexOf(delimiter) != -1) {
								if (!matchStarted) {
									matchStarted = true;
									gui.setStatusMessage("Opponent joined. Move to begin!");
									gui.registerObserver(self);
									refreshTileGrid();
								}
								
								refreshRemoteTileGrid(responseBuilder.substring(0, responseBuilder.length()-1));
								responseBuilder = new StringBuilder();
							}
							
							if (matchCode != null && responseBuilder.indexOf(opponentLeft) != -1) {
								serverSocket.close();
								JOptionPane.showMessageDialog(null, "Opponent left the match.\nExiting.", "Error", JOptionPane.ERROR_MESSAGE);
								System.exit(0);
								return;
							}
							
							if (matchCode != null && responseBuilder.indexOf(victoryMarker) != -1) {
								serverSocket.close();
								JOptionPane.showMessageDialog(null, "Opponent finished first!\nMatch over.", "Error", JOptionPane.INFORMATION_MESSAGE);
								System.exit(0);
								return;
							}
						}
						serverSocket.close();
						JOptionPane.showMessageDialog(null, "Lost connection to the server.\nExiting.", "Error", JOptionPane.ERROR_MESSAGE);
						System.exit(0);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						System.exit(0);
					}
				}
			}).start();
			
		}else{
			
			matchCode = null;
			while (matchCode == null) {
				try { matchCode = JOptionPane.showInputDialog(null, "Enter the match code:"); } catch (Exception e) {}
			}
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						serverSocket = new Socket();
						serverSocket.connect(new InetSocketAddress(remoteHost, Main.HOST_PORT), Main.CONNECTION_TIMEOUT);

						OutputStream socketOut = serverSocket.getOutputStream();
						socketOut.write(BackendOperations.JOIN_MATCH.getByte());
						socketOut.write(matchCode.getBytes());
						socketOut.write(0x01);
						
						InputStream socketIn = serverSocket.getInputStream();
						StringBuilder responseBuilder = new StringBuilder();

						while (true) {
							int incomingByte = socketIn.read();
							if (incomingByte == -1)
								break;
							responseBuilder.append((char) incomingByte);

							if (responseBuilder.indexOf(delimiter) != -1 && !matchStarted) {
								if (responseBuilder.indexOf(matchNotFound) != -1) {
									serverSocket.close();
									JOptionPane.showMessageDialog(null, "Match not found.\nExiting.", "Error", JOptionPane.ERROR_MESSAGE);
									System.exit(0);
									return;
								}else{
									self.level = new Level(responseBuilder.substring(0, responseBuilder.length()-1), "Remote Level");
								}
								matchStarted = true;
								self.init();
								responseBuilder = new StringBuilder();
							}

							if (matchStarted && responseBuilder.indexOf(delimiter) != -1) {
								
								if (responseBuilder.indexOf(opponentLeft) != -1) {
									serverSocket.close();
									JOptionPane.showMessageDialog(null, "Opponent left the match.\nExiting.", "Error", JOptionPane.ERROR_MESSAGE);
									System.exit(0);
									return;
								}
								
								if (responseBuilder.indexOf(victoryMarker) != -1) {
									serverSocket.close();
									JOptionPane.showMessageDialog(null, "Opponent finished first!\nMatch over.", "Error", JOptionPane.INFORMATION_MESSAGE);
									System.exit(0);
									return;
								}
								
								refreshRemoteTileGrid(responseBuilder.substring(0, responseBuilder.length()-1));
								responseBuilder = new StringBuilder();
							}
						}
						serverSocket.close();
						JOptionPane.showMessageDialog(null, "Lost connection to the server.\nExiting.", "Error", JOptionPane.ERROR_MESSAGE);
						System.exit(0);
					} catch (Exception e) {
						JOptionPane.showMessageDialog(null, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
						System.exit(0);
					}
				}
			}).start();
		}
	}

	private void init() {
		ImageMatrixGUI.setSize(level.getWidth() * 2 + 1, level.getHeight());
		refreshTileGrid();

		gui = ImageMatrixGUI.getInstance();
		gui.addImages(level.getTiles());
		gui.setName(level.getName() + " (" + level.getHash() + ")");
		
		if (isMatchHost) {
			gui.setStatusMessage("Waiting for opponent. Match code: " + matchCode);
		}else{
			gui.setStatusMessage("Match joined. Move to begin!");
			gui.registerObserver(self);
		}
			
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
		gui.setStatusMessage("Moves: " + player.getMoves() + " Energy: " + player.getEnergy());
		gui.update();

		if (checkWin()) {
			level.scoreMoves = player.getMoves();
			
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						serverSocket.getOutputStream().write(victoryMarker.getBytes());
						serverSocket.getOutputStream().write(0x01);
					} catch (IOException e) {
						JOptionPane.showMessageDialog(null, "Error sending victory to server: "+e.getMessage()+"\nExiting.", "Error", JOptionPane.ERROR_MESSAGE);
						System.exit(0);
					}
				}
			}).start();
			
			JOptionPane.showMessageDialog(null, "You finished first!\nLevel completed.", "Sokoban", JOptionPane.INFORMATION_MESSAGE);
			System.exit(0);
			return;
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
		tileGrid = new ArrayList<GameTile>(level.getWidth() * level.getHeight());

		for (int i = 0; i < level.getWidth() * level.getHeight(); i++) {
			tileGrid.add(null);
		}

		level.getTiles().forEach(new Consumer<ImageTile>() {
			@Override
			public void accept(ImageTile tile) {

				Point2D position = tile.getPosition();
				int index = position.getY() * level.getWidth() + position.getX();

				GameTile current = tileGrid.get(index);

				if (current == null || current.getLayer() < tile.getLayer())
					tileGrid.set(index, (GameTile) tile);
			}
		});
		
		if (!matchStarted)
			return;
		
		// Compose and send update for opponent
		
		StringBuilder updateBuilder = new StringBuilder();
		
		for (int y = 0; y < level.getHeight(); y++) {
			for (int x = 0; x < level.getWidth(); x++) {
				
				GameTile tile = tileGrid.get(y * level.getWidth() + x);
				
				if (tile instanceof Battery)
					updateBuilder.append('b');
				if (tile instanceof BigStone)
					updateBuilder.append('P');
				if (tile instanceof Box)
					updateBuilder.append('C');
				if (tile instanceof Floor)
					updateBuilder.append(' ');
				if (tile instanceof Hammer)
					updateBuilder.append('m');
				if (tile instanceof Hole)
					updateBuilder.append('O');
				if (tile instanceof Ice)
					updateBuilder.append('g');
				if (tile instanceof Player)
					updateBuilder.append('E');
				if (tile instanceof Portal)
					updateBuilder.append('t');
				if (tile instanceof SmallStone)
					updateBuilder.append('p');
				if (tile instanceof Target)
					updateBuilder.append('X');
				if (tile instanceof Wall) {
					if (((Wall) tile).isBreakable()) {
						updateBuilder.append('%');
					}else{
						updateBuilder.append('#');
					}
				}
			}
			updateBuilder.append('\n');
		}
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					serverSocket.getOutputStream().write(updateBuilder.toString().getBytes());
					serverSocket.getOutputStream().write(0x01);
				} catch (IOException e) {
					JOptionPane.showMessageDialog(null, "Error sending update to server: "+e.getMessage()+"\nExiting.", "Error", JOptionPane.ERROR_MESSAGE);
					System.exit(0);
				}
			}
		}).start();
	}
	
	
	private void refreshRemoteTileGrid(String contents) {
		if (remoteTileGrid != null)
			gui.removeImages(remoteTileGrid);
		
		remoteTileGrid = new ArrayList<ImageTile>();
		Portal portal = null;
		
		Scanner scanner = new Scanner(contents);
		int y = 0;
		while (scanner.hasNext()) {
			String line = scanner.nextLine();
			
			for (int x1 = 0; x1 < line.length(); x1++) {
				int x = x1 + level.getWidth() + 1;
				
				switch (line.charAt(x1))
				{
					case '#':
						remoteTileGrid.add(new Wall(x, y, false));
					break;
					
					case '%':
						remoteTileGrid.add(new Wall(x, y, true));
					break;
					
					case 'g':
						remoteTileGrid.add(new Ice(x, y));
					break;
					
					case 'm':
						remoteTileGrid.add(new Hammer(x, y));
					break;
					
					case 't':
						Portal newPortal = new Portal(x, y, portal);
						portal = newPortal;
						remoteTileGrid.add(newPortal);
					break;
					
					case 'b':
						remoteTileGrid.add(new Battery(x, y));
					break;
					
					case 'P':
						remoteTileGrid.add(new BigStone(x, y));
					break;
					
					case 'C':
						remoteTileGrid.add(new Box(x, y));
					break;
					
					case 'O':
						remoteTileGrid.add(new Hole(x, y));
					break;
					
					case 'E':
						remoteTileGrid.add(new Player(x, y));
					break;
					
					case 'p':
						remoteTileGrid.add(new SmallStone(x, y));
					break;
					
					case 'X':
						remoteTileGrid.add(new Target(x, y));
					break;
					
					case ' ':
						remoteTileGrid.add(new Floor(x, y));
						break;
					
					default:
						break;
				}
			} y++;
		}
		scanner.close();
		
		gui.addImages(remoteTileGrid);
		gui.update();
	}
}
