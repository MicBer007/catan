package server;

import packets.ConnectionRequestedPacket;
import packets.ConnectionTerminatedPacket;
import packets.PlayerFinishTurnPacket;
import packets.StructureBuiltPacket;

public interface ServerEventListener {
	
	public void playerRequestConnection(ConnectionRequestedPacket packet, ServerConnection connection);
	
	public void playerConnectionTerminated(ConnectionTerminatedPacket packet, ServerConnection connection);
	
	public void structureBuilt(StructureBuiltPacket packet, ServerConnection connection);
	
	public void playerFinishTurn(PlayerFinishTurnPacket packet, ServerConnection connection);

}
