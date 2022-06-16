package client;

import packets.ConnectionGrantedPacket;
import packets.ConnectionRequestedPacket;
import packets.GameInitPacket;
import packets.GameInitPhasePacket;
import packets.GamePlayingPhasePacket;
import packets.NextTurnPacket;
import packets.Packet;
import packets.BuildStructurePacket;
import packets.ConnectionTerminatedPacket;
import packets.StructureBuiltPacket;

public class ClientEventManager {
	
	private ClientEventListener listener;
	
	public ClientEventManager(ClientEventListener listener) {
		this.listener = listener;
	}
	
	public void received(Packet p) {
		if(p instanceof ConnectionGrantedPacket) {
			ConnectionGrantedPacket packet = (ConnectionGrantedPacket) p;
			listener.connectionGranted(packet);
		} else if(p instanceof ConnectionTerminatedPacket) {
			ConnectionTerminatedPacket packet = (ConnectionTerminatedPacket) p;
			listener.playerTerminatedConnection(packet);
		} else if(p instanceof GameInitPacket) {
			GameInitPacket packet = (GameInitPacket) p;
			listener.gameInit(packet);
		} else if(p instanceof NextTurnPacket) {
			NextTurnPacket packet = (NextTurnPacket) p;
			listener.nextTurn(packet);
		} else if(p instanceof StructureBuiltPacket) {
			StructureBuiltPacket packet = (StructureBuiltPacket) p;
			listener.structureBuilt(packet);
		} else if(p instanceof BuildStructurePacket) {
			BuildStructurePacket packet = (BuildStructurePacket) p;
			listener.structureBuildCommand(packet);
		} else if(p instanceof GameInitPhasePacket) {
			GameInitPhasePacket packet = (GameInitPhasePacket) p;
			listener.gameInInitPhaseEvent(packet);
		} else if(p instanceof GamePlayingPhasePacket) {
			GamePlayingPhasePacket packet = (GamePlayingPhasePacket) p;
			listener.gameInPlayingPhaseEvent(packet);
		}
		
		
		
		
		
		else if(p instanceof ConnectionRequestedPacket) {
			System.out.println("Add connection packet is not supported!");
		}
	}

}