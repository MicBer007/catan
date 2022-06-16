package catan.player;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {
	
	private List<Team> players = new ArrayList<Team>();
	
	public void addPlayer(Team team) {
		players.add(team);
	}

}
