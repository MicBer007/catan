package catan;

import java.awt.Graphics;
import java.awt.event.MouseEvent;

import catan.player.types.Human;
import catan.player.types.Player;
import client.Client;
import map.Map;
import mapGeneration.MapCreator;
import menu.Menu;
import rendering.MasterRenderer;
import settings.Settings;

public class CatanGame {
	
	public static Map map;
	
	public static Player player;
	
	private static MasterRenderer renderer = new MasterRenderer();
	
	private static Menu menu = new Menu(new Runnable() {@Override public void run() {startServer();}}, new Runnable() {@Override public void run() {joinServer();}});
	
	public static Client client;
	
	public static GamePhase state = GamePhase.WAITING_FOR_PLAYERS;
	
	public static void stop() {
		if(client != null) {
			client.disconnect();
		}
	}
	
	public static void startServer() {
		if(ServerManager.startServer()) {
			client = new Client("localhost", Settings.SERVER_PORT, "Garry");
			client.connect();
		}
	}
	
	public static void joinServer() {
		client = new Client("localhost", Settings.SERVER_PORT, "Larry");
		client.connect();
	}
	
	public static void startGame(String mapGenerationSettings) {
		map = MapCreator.generateVanillaMap(mapGenerationSettings);
		state = GamePhase.INIT;
		player = new Human(client.team, "Garry");
	}
	
	public static void render(Graphics g) {
		if(state == GamePhase.WAITING_FOR_PLAYERS) {
			menu.render(g);
		} else {
			renderer.renderBackground(g);
			renderer.renderMap(g, map);
			renderer.renderPlayerTurnOrderIcons(g, client.turnOrder, client.currentPlayer);
		}
	}

	public static void mousePressed(MouseEvent e) {
		menu.mousePressed(e);
		if(player instanceof Human) {
			((Human) player).mousePressed(e, map);
		}
	}

	public static void mouseReleased(MouseEvent e) {
		menu.mouseReleased(e);
	}

	public static void mouseMoved(MouseEvent e) {
		menu.mouseMoved(e);
	}
	
}
