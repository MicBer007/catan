package catan;

import server.Server;
import settings.Settings;

public class ServerManager {
	
	private static Server server;
	
	public static boolean startServer() {
		server = new Server(Settings.SERVER_PORT, 2);
		return server.start();
	}
	
	public static void stopServer() {
		server.close();
	}

}
