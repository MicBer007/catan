package packets;

public class GameInitPacket extends Packet {

	private static final long serialVersionUID = 1L;
	
	public String mapGeneratorSetting;
	
	public PlayerInitData[] turnOrder;
	
	public GameInitPacket(String mapGeneratorSetting, PlayerInitData[] turnOrder) {
		this.mapGeneratorSetting = mapGeneratorSetting;
		this.turnOrder = turnOrder;
	}

}
