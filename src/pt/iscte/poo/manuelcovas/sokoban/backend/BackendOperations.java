package pt.iscte.poo.manuelcovas.sokoban.backend;

public enum BackendOperations {
	
	RETRIEVE_SCOREBOARD((byte) 0x01),
	UPDATE_SCOREBOARD((byte) 0x02),
	START_MATCH((byte) 0x03),
	JOIN_MATCH((byte) 0x04);
	
	BackendOperations(byte op) {
		operation = op;
	}
	
	private byte operation;
	
	public byte getByte() {
		return operation;
	}
}