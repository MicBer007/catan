package packets;

import java.io.Serializable;

import catan.player.Team;

public class PlayerInitData implements Serializable {
	
	private static final long serialVersionUID = 1L;

	public int id;
	
	public String name;
	
	public Team team;
	
	public PlayerInitData(int id, String name, Team team) {
		this.id = id;
		this.name = name;
		this.team = team;
	}

}
