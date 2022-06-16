package server;

import java.net.Socket;
import java.util.HashMap;

public class ConnectionHandler {
	
	public static HashMap<Integer, ServerConnection> connections = new HashMap<Integer, ServerConnection>();
	
	public static void shutdown() {
		for(int connectionID: connections.keySet()) {
			connections.get(connectionID).stop();
		}
		connections.clear();
	}
	
	public static void addConnection(Socket socket, int connectionName, ServerEventManager eventManager) {
		ServerConnection connection = new ServerConnection(socket, connectionName, eventManager);
		ConnectionHandler.connections.put(connectionName, connection);
		connection.start();
	}
	
	public static void removeConnection(int connectionID) {
		ServerConnection connection = connections.get(connectionID);
		if(connection == null) return;
		connection.stop();
		connections.remove(connectionID);
	}

}