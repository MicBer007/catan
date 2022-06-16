package client;

import packets.GameInitPacket;
import packets.GameInitPhasePacket;
import packets.GamePlayingPhasePacket;
import packets.NextTurnPacket;
import packets.BuildStructurePacket;
import packets.ConnectionGrantedPacket;
import packets.ConnectionTerminatedPacket;
import packets.StructureBuiltPacket;

public interface ClientEventListener {
	
	public void connectionGranted(ConnectionGrantedPacket packet);
	
	public void playerTerminatedConnection(ConnectionTerminatedPacket packet);
	
	public void gameInit(GameInitPacket packet);
	
	public void structureBuilt(StructureBuiltPacket packet);
	
	public void structureBuildCommand(BuildStructurePacket packet);
	
	public void nextTurn(NextTurnPacket packet);
	
	public void gameInInitPhaseEvent(GameInitPhasePacket packet);
	
	public void gameInPlayingPhaseEvent(GamePlayingPhasePacket packet);

}
