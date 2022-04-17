package catan.graphics.rendering;

import java.awt.Graphics;
import java.util.Map;

import catan.graphics.objects.ui.Hitbox;
import catan.player.Player;
import catan.player.ResourceType;

public class PlayerRenderer {
	
	private Hitbox activePlayerStatusBox;
	
	private int resourceStartX, resourceStartY;
	
	public PlayerRenderer(Hitbox activePlayerStatusBox, int resourceStartX, int resourceStartY){
		this.activePlayerStatusBox = activePlayerStatusBox;
		this.resourceStartX = resourceStartX;
		this.resourceStartY = resourceStartY;
	}
	
	public void renderPlayerResources(Graphics g, Map<ResourceType, Integer> resources) {
		for(int i = 0; i < ResourceType.values().length; i++) {
			ResourceType type = ResourceType.values()[i];
			g.setColor(type.getColour());
			g.drawString(type.name() + ": " + resources.get(type), resourceStartX, resourceStartY + (i * 20));
		}
	}
	
	public void renderCurrentPlayer(Graphics g, Player activePlayer) {
		g.setColor(activePlayer.getTeam().getColour());
		g.fillRect(activePlayerStatusBox.getX1(), activePlayerStatusBox.getY1(), activePlayerStatusBox.getWidth(), activePlayerStatusBox.getHeight());
	}

}
