package pt.iscte.poo.manuelcovas.sokoban;


public class Main {
	
	public static String WINDOW_TITLE = "POO Sokoban";
	private static String BACKEND_KEY = "c8c1742bf755f1447739ba9d0d7106cf";  // Project indentifier to connect to the online server.
	
	
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