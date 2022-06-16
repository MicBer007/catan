package client;

import catan.player.Team;

public class ClientConnection {

	public int id;
	
	public String name;
	
	public Team team;
	
	public ClientConnection(int id, String name, Team team) {
		this.id = id;
		this.name = name;
		this.team = team;
	}

}