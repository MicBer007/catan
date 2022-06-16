package packets;

import catan.player.PlayerBuildAction;

public class BuildStructurePacket extends Packet {
	
	private static final long serialVersionUID = 1L;
	
	public PlayerBuildAction action;
	
	public BuildStructurePacket(PlayerBuildAction action) {
		this.action = action;
	}

}
