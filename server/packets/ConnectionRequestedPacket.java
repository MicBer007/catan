package packets;

public class ConnectionRequestedPacket extends Packet {

	private static final long serialVersionUID = 1L;
	
	public String name;
	
	public ConnectionRequestedPacket(String name) {
		this.name = name;
	}

}
