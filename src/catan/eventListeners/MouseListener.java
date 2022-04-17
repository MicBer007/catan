package catan.eventListeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.event.MouseInputListener;

import catan.CatanGame;

public class MouseListener implements MouseMotionListener, MouseInputListener {
	
	private CatanGame game;
	
	public MouseListener(CatanGame game) {
		this.game = game;
	}

	@Override
	public void mouseClicked(MouseEvent e) { }

	@Override
	public void mousePressed(MouseEvent e) {
		game.getPlayerManager().mousePressedEvent(e);
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		game.getPlayerManager().mouseReleasedEvent(e);
	}

	@Override
	public void mouseEntered(MouseEvent e) { }

	@Override
	public void mouseExited(MouseEvent e) { }

	@Override
	public void mouseDragged(MouseEvent e) { }

	@Override
	public void mouseMoved(MouseEvent e) {
		game.getPlayerManager().mouseMoveEvent(e);
	}

}
