package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.SocketException;

import catan.player.Team;
import packets.Packet;

public class ServerConnection implements Runnable {
	
	private Thread thread;
	
	private Socket socket;
	private ObjectInputStream in;
	private ObjectOutputStream out;
	
	public int id;
	private String name;
	private Team team;
	
	private ServerEventManager eventManager;
	private boolean running = false;
	
	public ServerConnection(Socket socket, int connectionID, ServerEventManager eventManager) {
		this.socket = socket;
		this.id = connectionID;
		this.eventManager = eventManager;
		try {
			out = new ObjectOutputStream(socket.getOutputStream());
			in = new ObjectInputStream(socket.getInputStream());
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void start() {
		thread = new Thread(this);
		thread.start();
	}
	
	public void stop() {
		running = false;
	}
	
	public void sendObject(Packet packet) {
		try {
			out.writeObject(packet);
			out.flush();
		} catch(SocketException e) {
			
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	private void close() {
		try {
			socket.close();
			thread.interrupt();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void run() {
		running = true;
		while(running) {
			try {
				Object data = in.readObject();
				if(data instanceof Packet) {
					eventManager.received(((Packet) data), this);
				}
			} catch(EOFException e) {
				
			} catch(ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
		close();
	}

}