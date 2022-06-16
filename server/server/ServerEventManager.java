package server;

import packets.ConnectionRequestedPacket;
import packets.GameInitPacket;
import packets.Packet;
import packets.BuildStructurePacket;
import packets.PlayerFinishTurnPacket;
import packets.ConnectionTerminatedPacket;
import packets.StructureBuiltPacket;

public class ServerEventManager {
	
	private ServerEventListener listener;
	
	public ServerEventManager(ServerEventListener listener) {
		this.listener = listener;
	}
	
	public void received(Packet packet, ServerConnection connection) {
		if(packet instanceof ConnectionRequestedPacket) {
			ConnectionRequestedPacket addConnectionPacket = (ConnectionRequestedPacket) packet;
			listener.playerRequestConnection(addConnectionPacket, connection);
		} else if(packet instanceof ConnectionTerminatedPacket) {
			ConnectionTerminatedPacket removeConnectionPacket = (ConnectionTerminatedPacket) packet;
			removeConnectionPacket.id = connection.id;
			listener.playerConnectionTerminated(removeConnectionPacket, connection);
		} else if(packet instanceof PlayerFinishTurnPacket) {
			PlayerFinishTurnPacket playerFinishTurnPacket = (PlayerFinishTurnPacket) packet;
			listener.playerFinishTurn(playerFinishTurnPacket, connection);
		} else if(packet instanceof StructureBuiltPacket) {
			StructureBuiltPacket structureBuiltPacket = (StructureBuiltPacket) packet;
			listener.structureBuilt(structureBuiltPacket, connection);
		}
		
		
		
		
		else if(packet instanceof GameInitPacket) {
			System.out.println("A GameInitPacket should not be receieved by the server!");
		} else if(packet instanceof BuildStructurePacket) {
			System.out.println("A PermitBuildPacket should not be receieved by the server!");
		}
	}

}
