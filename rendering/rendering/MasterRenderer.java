package rendering;

import java.awt.Color;
import java.awt.Graphics;
import java.util.List;

import catan.player.Team;
import map.GameTileType;
import map.Intersection;
import map.Map;
import map.Port;
import map.Road;
import map.Tile;
import settings.Settings;
import utils.TextUtils;

public class MasterRenderer {
	
	public void renderBackground(Graphics g) {
		g.setColor(Settings.BACKGROUND_COLOR);
		g.fillRect(0, 0, Settings.SCREEN_WIDTH, Settings.SCREEN_HEIGHT);
	}
	
	public void renderMap(Graphics g, Map map) {
		for(Tile tile: map.getTiles()) {
			renderGameTile(g, tile);
		}
		for(Intersection intersection: map.getIntersections()) {
			renderMapIntersection(g, intersection);
		}
		for(Road road: map.getRoads()) {
			renderRoad(g, road);
		}
		for(Port port: map.getPorts()) {
			renderMapPort(g, port);
		}
	}
	
	public void renderGameTile(Graphics g, Tile tile) {
		g.setColor(tile.getTileType().getTileColour());
		g.fillPolygon(tile.getPolygon());
		if(tile.getTileType() != GameTileType.DESERT) {
			g.setColor(tile.getTileType().getTextColour());
			TextUtils.renderTextCentered(g, Integer.toString(tile.getDiceNumber()), tile.getXMid(), tile.getYMid() + 4);
		}
	}
	
	public void renderMapIntersection(Graphics g, Intersection intersection) {
		if(intersection.getTeam() == null) {
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
	
	public void renderRoad(Graphics g, Road road) {
		if(road.getTeam() == null) {
			return;
		}
		g.setColor(road.getTeam().getColour());
		g.fillPolygon(road.getPolygon());
	}
	
	public void renderMapPort(Graphics g, Port port) {
		g.setColor(Settings.PORT_OUTLINE);
		g.fillOval(port.getX() - 13, port.getY() - 13, 26, 26);
		g.setColor(port.getType().getColour());
		g.fillOval(port.getX() - 11, port.getY() - 11, 22, 22);
		g.setColor(Settings.PORT_TEXT_COLOUR);
		g.drawString(port.getType().getTradeName(), port.getX() - 8, port.getY() + 5);
	}
	
	public void renderRoadHitbox(Graphics g, Road road) {
		g.setColor(Color.RED);
		g.fillRect(road.getXMid() - 25, road.getYMid() - 25, 50, 50);
	}
	
	public void renderPlayerTurnOrderIcons(Graphics g, List<Team> playerOrder, int currentPlayingPlayer) {
		g.setColor(Settings.TURN_ORDER_PANEL_COLOUR);
		g.fillRect(0, Settings.SCREEN_HEIGHT - 90, Settings.SCREEN_WIDTH, 90);
		int xOffset = 0;
		for(int i = 0; i < playerOrder.size(); i++) {
			g.setColor(playerOrder.get(i).getColour());
			if(i == currentPlayingPlayer) {
				g.fillRect(xOffset + 20, Settings.SCREEN_HEIGHT - 70, 50, 50);
				xOffset += 70;
			} else {
				g.fillRect(xOffset + 20, Settings.SCREEN_HEIGHT - 55, 30, 30);
				xOffset += 50;
			}
		}
	}

}
