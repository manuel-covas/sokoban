package pt.iscte.poo.manuelcovas.sokoban;


public class Main {
	
	public static final String WINDOW_TITLE = "POO Sokoban";
	public static final String DEFAULT_HOST = "sokoban.ddns.net";
	public static final int    HOST_PORT = 29261;
	public static final int    CONNECTION_TIMEOUT = 3000;  // Milliseconds
	
	public static void main(String[] args) {
		
		int choice = Menu.mainMenu();
		
		switch (choice) {
		case 0:
			Menu.singlePlayer();
			break;

		case 1:
			break;
			
		case 2:
			Menu.scoreboard();
			break;
			
		default:
			System.exit(0);
			return;
		}
	}
	
	
	private static void launchMultiplayer() {
		
	}
}