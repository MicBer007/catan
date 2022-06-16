package packets;

public class ConnectionGrantedPacket extends Packet {
	
	private static final long serialVersionUID = 1L;
	
	public int id;
	
	public ConnectionGrantedPacket(int id) {
		this.id = id;
	}
	
}
