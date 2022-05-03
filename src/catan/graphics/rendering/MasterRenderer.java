package catan.graphics.rendering;

import java.awt.Color;
import java.awt.Graphics;

import catan.graphics.objects.map.GameTile;
import catan.graphics.objects.map.GameTileType;
import catan.graphics.objects.map.MapEdge;
import catan.graphics.objects.map.MapIntersection;
import catan.graphics.objects.map.Port;
import catan.player.Team;
import methods.SmartText;
import settings.Settings;

public class MasterRenderer {
	
	private int width, height;
	
	public MasterRenderer(int width, int height) {
		this.width = width;
		this.height = height;
	}
	
	public void renderBackground(Graphics g) {
		g.setColor(Settings.BACKGROUND_COLOR);
		g.fillRect(0, 0, width, height);
	}
	
	public void renderGameTile(Graphics g, GameTile tile) {
		g.setColor(tile.getTileType().getTileColour());
		g.fillPolygon(tile.getPolygon());
		if(tile.getTileType() != GameTileType.DESERT) {
			g.setColor(tile.getTileType().getTextColour());
			SmartText.renderTextCentered(g, Integer.toString(tile.getDiceNumber()), tile.getXMid(), tile.getYMid() + 4);
		}
	}
	
	public void renderMapIntersection(Graphics g, MapIntersection intersection) {
		if(intersection.getTeam() == Team.NONE) {
			return;
		}
		switch(intersection.getSettlementType()) {
			case EMPTY:
				break;
			case VILLAGE:
				g.setColor(intersection.getTeam().getColour());
				g.fillOval(intersection.getX() - 6, intersection.getY() - 6, 12, 12);
				break;
			case CITY:
				g.setColor(intersection.getTeam().getColour());
				g.fillOval(intersection.getX() - 10, intersection.getY() - 10, 20, 20);
				break;
		}
	}
	
	public void renderMapEdge(Graphics g, MapEdge edge) {
		if(edge.getTeam() == Team.NONE) {
			return;
		}
		g.setColor(edge.getTeam().getColour());
		g.fillPolygon(edge.getPolygon());
	}
	
	public void renderMapPort(Graphics g, Port port) {
		g.setColor(Settings.PORT_OUTLINE);
		g.fillOval(port.getX() - 13, port.getY() - 13, 26, 26);
		g.setColor(port.getType().getColour());
		g.fillOval(port.getX() - 11, port.getY() - 11, 22, 22);
		g.setColor(Settings.PORT_TEXT_COLOUR);
		g.drawString(port.getType().getTradeName(), port.getX() - 8, port.getY() + 5);
	}
	
	public void renderMapEdgeHitbox(Graphics g, MapEdge edge) {
		g.setColor(Color.RED);
		g.fillRect(edge.getXMid() - 25, edge.getYMid() - 25, 50, 50);
	}

}
