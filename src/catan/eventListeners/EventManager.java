package catan.eventListeners;

import java.awt.event.MouseEvent;

public class EventManager {
	
	private MouseListener mouseListener;
	
	public EventManager(MouseListener mouseListener) {
		this.mouseListener = mouseListener;
	}

	public MouseListener getMouseListener() {
		return mouseListener;
	}
	
	public void mouseMoveEvent(MouseEvent e) {
		
	}

}
