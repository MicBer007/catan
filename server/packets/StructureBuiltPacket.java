package packets;

import catan.player.Team;
import map.RoadType;
import map.SettlementType;

public class StructureBuiltPacket extends Packet {

	private static final long serialVersionUID = 1L;
	
	public RoadType roadType;
	
	public SettlementType settlementType;
	
	public int index;
	
	public Team team;
	
	public StructureBuiltPacket(RoadType roadType, int index, Team team) {
		this.index = index;
		this.team = team;
		this.roadType = roadType;
	}
	
	public StructureBuiltPacket(SettlementType settlementType, int index, Team team) {
		this.index = index;
		this.team = team;
		this.settlementType = settlementType;
	}

}
