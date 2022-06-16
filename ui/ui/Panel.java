package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class Panel extends UIComponent {
	
	private Color displayColour;
	
	private boolean notifyComponentsAlways = false;
	
	public Panel(Hitbox hitbox, Color fill) {
		super(hitbox);
		this.displayColour = fill;
	}
	
	public void render(Graphics g) {
		if(isVisible()) {
			g.setColor(getDisplayColour());
			getHitbox().draw(g);
			for(UIComponent c: getSubComponents()) {
				c.render(g);
			}
		}
	}
	
	public void mousePressed(MouseEvent e) {
		if(isVisible() && (getHitbox().isPointInHitbox(e.getX(), e.getY()) || notifyComponentsAlways)) {
			for(UIComponent c: getSubComponents()) {
				c.mousePressed(e);
			}
		}
	}
	
	public void mouseReleased(MouseEvent e) {
		if(isVisible() && (getHitbox().isPointInHitbox(e.getX(), e.getY()) || notifyComponentsAlways)) {
			for(UIComponent c: getSubComponents()) {
				c.mouseReleased(e);
			}
		}
	}
	
	public void mouseMoved(MouseEvent e) {
		if(isVisible() && (getHitbox().isPointInHitbox(e.getX(), e.getY()) || notifyComponentsAlways)) {
			for(UIComponent c: getSubComponents()) {
				c.mouseMoved(e);
			}
		}
	}

	public Color getDisplayColour() {
		return displayColour;
	}

	public boolean isNotifyComponentsAlways() {
		return notifyComponentsAlways;
	}

	public void setNotifyComponentsAlways(boolean notifyComponentsAlways) {
		this.notifyComponentsAlways = notifyComponentsAlways;
	}

}
