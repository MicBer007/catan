package map;

public class Port {
	
	private PortType type;
	
	private int x, y;

	public Port(PortType type, int x, int y) {
		this.type = type;
		this.x = x;
		this.y = y;
	}

	public PortType getType() {
		return type;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

}
