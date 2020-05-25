package pt.iscte.poo.manuelcovas.sokoban;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.function.Consumer;

import javax.swing.JOptionPane;

import pt.iscte.poo.manuelcovas.sokoban.backend.BackendOperations;
import pt.iscte.poo.manuelcovas.sokoban.level.Level;

public class Scoreboard {
	
	public static void saveScores(ArrayList<Level> levels, String name) {
		File localScoreboardDir = new File(System.getProperty("user.dir")+File.separator+"highscores");
		if (!localScoreboardDir.exists())
			localScoreboardDir.mkdir();
		
		levels.forEach(new Consumer<Level>() {
			@Override
			public void accept(Level level) {
				try {
					Files.write(Paths.get(localScoreboardDir.getPath()+File.separator+level.getHash()),
								new String("("+level.getName().replaceAll(":", "").replaceAll("\n", "")+") "+name+":"+level.scoreMoves+"\n").getBytes(),
								StandardOpenOption.CREATE, StandardOpenOption.APPEND);
				} catch (IOException e) {}
			}
		});
	}
	
	
	public static void saveRemoteScores(String host) throws Exception {
		File[] files;
		File localScoreboardDir = new File(System.getProperty("user.dir")+File.separator+"highscores");
		if (!localScoreboardDir.exists())
			localScoreboardDir.mkdir();
		
		StringBuilder payload = new StringBuilder();
		files = localScoreboardDir.listFiles();
		
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			
			payload.append(file.getName());
			payload.append((char)0x01);	// Level checksum delimiter
			payload.append(new String(Files.readAllBytes(file.toPath())));
			payload.append((char)0x01);	// Score data delimiter
		}

		byte[] payloadBytes = Arrays.copyOf(payload.toString().getBytes(), payload.length() + 1);
		payloadBytes[payload.length()] = 0x00;    // Message terminator;
		
		
		Socket socket = new Socket();
		socket.connect(new InetSocketAddress(host, Main.HOST_PORT), Main.CONNECTION_TIMEOUT);
		
		OutputStream socketOut = socket.getOutputStream();
		socketOut.write(BackendOperations.UPDATE_SCOREBOARD.getByte());
		socketOut.write(payloadBytes);
		
		InputStream socketIn = socket.getInputStream();
		StringBuilder responseBuilder = new StringBuilder();
		
		while (true) {
			int incomingByte = socketIn.read();
			if (incomingByte == -1)
				break;
			responseBuilder.append((char) incomingByte);
		}
		socket.close();
		
		if (responseBuilder.length() == 0) {
			JOptionPane.showMessageDialog(null, "Couldn't save remote scoreboard:\nReceived an empty response.", "Error", JOptionPane.ERROR_MESSAGE);
		}else{
			JOptionPane.showMessageDialog(null, responseBuilder.toString(), "Remote scoreboard", JOptionPane.INFORMATION_MESSAGE);
		}
	}
	
	
	public static void localScoreboard() throws Exception {
		String message = "";
		File[] files;
		
		File localScoreboardDir = new File(System.getProperty("user.dir")+File.separator+"highscores");
		if (!localScoreboardDir.exists())
			localScoreboardDir.mkdir();
		files = localScoreboardDir.listFiles();
		
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			message = message + file.getName() + "\n";
			
			Scanner scanner = new Scanner(file);
			
			while (scanner.hasNext()) {
				String line = scanner.nextLine();
				String[] entry = line.split(":");
				
				if (entry.length == 2) {
					message = message + "    "+entry[0]+": "+entry[1]+" moves\n";
				}else{
					scanner.close();
					throw new Exception("Invalid score entry: \""+line+"\"");
				}
			}
			scanner.close();
			message = message + "\n";
		}
		
		if (files.length == 0)
			message = "No scores stored locally.";
		
		JOptionPane.showMessageDialog(null, message, "Local scoreboard", JOptionPane.INFORMATION_MESSAGE);
	}
	
	
	public static void remoteScoreboard(String host) throws Exception {
		
		Socket socket = new Socket();
		socket.connect(new InetSocketAddress(host, Main.HOST_PORT), Main.CONNECTION_TIMEOUT);
		
		OutputStream socketOut = socket.getOutputStream();
		socketOut.write(BackendOperations.RETRIEVE_SCOREBOARD.getByte());
		
		InputStream socketIn = socket.getInputStream();
		StringBuilder payloadBuilder = new StringBuilder();
		
		while (true) {
			int incomingByte = socketIn.read();
			if (incomingByte == -1)
				break;
			payloadBuilder.append((char) incomingByte);
		}
		socket.close();
		
		if (payloadBuilder.length() == 0) {
			JOptionPane.showMessageDialog(null, "Couldn't load remote scoreboard:\nReceived an empty response.", "Error", JOptionPane.ERROR_MESSAGE);
		}else{
			JOptionPane.showMessageDialog(null, payloadBuilder.toString(), "Remote scoreboard", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
