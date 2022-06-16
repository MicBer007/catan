package packets;

import catan.player.Team;

public class NextTurnPacket extends Packet {

	private static final long serialVersionUID = 1L;
	
	public int diceRoll;
	
	public Team teamToPlay;
	
	public NextTurnPacket(int diceRoll, Team teamToPlay) {
		this.diceRoll = diceRoll;
		this.teamToPlay = teamToPlay;
	}

}
