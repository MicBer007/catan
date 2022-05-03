package catan.graphics.rendering;

import java.awt.Graphics;

import catan.graphics.objects.ui.Hitbox;
import catan.player.Player;

public class PlayerRenderer {
	
	private Hitbox activePlayerStatusBox;
	
	public PlayerRenderer(Hitbox activePlayerStatusBox){
		this.activePlayerStatusBox = activePlayerStatusBox;
	}
	
	public void renderCurrentPlayer(Graphics g, Player activePlayer) {
		g.setColor(activePlayer.getTeam().getColour());
		g.fillRect(activePlayerStatusBox.getX1(), activePlayerStatusBox.getY1(), activePlayerStatusBox.getWidth(), activePlayerStatusBox.getHeight());
	}

}
