package catan.graphics.objects.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;

public class Button extends UIComponent {

	private int mouseState;
	
	private Color base;
	private Color hover;
	private Color click;
	
	private TextComponent textComponent;
	
	private Runnable run;
	
	public Button(Hitbox hitbox, String text, Color base, Color hover, Color click, Runnable run) {
		super(hitbox);
		this.run = run;
		this.textComponent = new TextComponent(hitbox, text, Color.BLACK, base);
		this.base = base;
		this.hover = hover;
		this.click = click;
		setMouseState(0);
	}
	
	@Override
	public void render(Graphics g) {
		if(isVisible()) {
			textComponent.render(g);
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(getHitbox().isPointInHitbox(e.getX(), e.getY())) {
			setMouseState(2);
		}
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		if(getHitbox().isPointInHitbox(e.getX(), e.getY())) {
			setMouseState(1);
		}
	}
	
	@Override
	public void mouseMoved(MouseEvent e) {
		if(getHitbox().isPointInHitbox(e.getX(), e.getY())) {
			setMouseState(1);
		} else {
			setMouseState(0);
		}
	}

	public int getMouseState() {
		return mouseState;
	}

	public void setMouseState(int mouseState) {
		this.mouseState = mouseState;
		if(mouseState == 0) {
			textComponent.setBackgroundColour(base);
		} else if(mouseState == 1) {
			textComponent.setBackgroundColour(hover);
		} else {
			textComponent.setBackgroundColour(click);
			run.run();
		}
	}

	public Color getBaseImage() {
		return base;
	}

	public Color getHover() {
		return hover;
	}

	public Color getClick() {
		return click;
	}

	public TextComponent getTextComponent() {
		return textComponent;
	}

}
