package pt.iscte.poo.manuelcovas.sokoban.backend;

public enum BackendOperations {
	
	RETRIEVE_SCOREBOARD((byte) 0x01),
	UPDATE_SCOREBOARD((byte) 0x02);
	
	private byte operation;
	
	
	BackendOperations(byte op) {
		operation = op;
	}
	
	public byte getByte() {
		return operation;
	}
}
