package ui;

import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class UIComponent {
	
	private Hitbox hitbox;
	
	private boolean visible;
	
	private List<UIComponent> subComponents = new ArrayList<UIComponent>();
	
	public UIComponent(Hitbox hitbox) {
		this.hitbox = hitbox;
		this.visible = true;
	}
	
	public void mousePressed(MouseEvent e) { }
	
	public void mouseReleased(MouseEvent e) { }
	
	public void mouseMoved(MouseEvent e) { }
	
	public void render(Graphics g) { }

	public Hitbox getHitbox() {
		return hitbox;
	}

	public List<UIComponent> getSubComponents() {
		return subComponents;
	}
	
	public void addComponent(UIComponent component) {
		subComponents.add(component);
	}
	
	public void removeComponent(UIComponent component) {
		subComponents.remove(component);
	}

	public boolean isVisible() {
		return visible;
	}

	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public boolean toggleVisibility() {
		return (visible = !visible);
	}

}
