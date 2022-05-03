package catan.graphics.objects.ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.util.List;

public class OptionBox extends UIComponent {
	
	private Button currentOption;
	
	private boolean showOptions = false;
	
	private Color backgroundColour;
	
	public OptionBox(Hitbox hitbox, Color backgroundColour) {
		super(hitbox);
		this.backgroundColour = backgroundColour;
	}
	
	public OptionBox(Hitbox hitbox, List<String> options, Color base, Color hover, Color click, Color backgroundColour) {
		super(hitbox);
		this.backgroundColour = backgroundColour;
		for(String title: options) {
			addOption(title, base, hover, click);
		}
	}
	
	@Override
	public void mousePressed(MouseEvent e) {
		if(isVisible()) {
			currentOption.mousePressed(e);
			if(showOptions) {
				for(UIComponent c: getSubComponents()) {
					c.mousePressed(e);
				}
			}
			if(e.getX() < getHitbox().getX1() || e.getX() > getHitbox().getX2()) {
				showOptions = false;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		if(isVisible()) {
			currentOption.mouseReleased(e);
			if(showOptions) {
				for(UIComponent c: getSubComponents()) {
					c.mouseReleased(e);
				}
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		if(isVisible()) {
			currentOption.mouseMoved(e);
			if(showOptions) {
				for(UIComponent c: getSubComponents()) {
					c.mouseMoved(e);
				}
			}
		}
	}

	@Override
	public void render(Graphics g) {
		if(isVisible()) {
			g.setColor(backgroundColour);
			getHitbox().draw(g);
			currentOption.render(g);
			if(showOptions) {
				for(UIComponent component: getSubComponents()) {
					component.render(g);
				}
			}
		}
	}

	public void addOption(String title, Color base, Color hover, Color click) {
		Hitbox h = getHitbox();
		int optionBoxHeight = 30;
		int y = h.getY1() + (getSubComponents().size() + 1) * optionBoxHeight;
		Runnable run = new Runnable() {@Override public void run(){setAsChosenOption(title, base, hover, click);}};
		getSubComponents().add(new Button(new Hitbox(h.getX1() + 2, y, h.getWidth() - 2, optionBoxHeight), title, base, hover, click, run));
		if(getSubComponents().size() == 1) {
			setAsChosenOption(title, base, hover, click);
		}
	}

	public void addOption(String title, Color base) {
		Hitbox h = getHitbox();
		int optionBoxHeight = 30;
		int y = h.getY1() + (getSubComponents().size() + 1) * optionBoxHeight;
		Runnable run = new Runnable() {@Override public void run(){setAsChosenOption(title, base);}};
		getSubComponents().add(new Button(new Hitbox(h.getX1() + 2, y, h.getWidth() - 2, optionBoxHeight), title, base, run));
		if(getSubComponents().size() == 1) {
			setAsChosenOption(title, base);
		}
	}
	
	public void setAsChosenOption(String title, Color base, Color hover, Color click) {
		if(currentOption != null) {
			currentOption.setMouseState(0);
		}
		Hitbox h = getHitbox();
		Runnable run = new Runnable() {@Override public void run() {changeShowOptions();}};
		currentOption = new Button(new Hitbox(h.getX1() + 2, h.getY1() + 2, h.getWidth() - 4, h.getHeight() - 4), title, base, hover, click, run);
		showOptions = false;
	}
	
	public void setAsChosenOption(String title, Color base) {
		if(currentOption != null) {
			currentOption.setMouseState(0);
		}
		Hitbox h = getHitbox();
		Runnable run = new Runnable() {@Override public void run() {changeShowOptions();}};
		currentOption = new Button(new Hitbox(h.getX1() + 2, h.getY1() + 2, h.getWidth() - 4, h.getHeight() - 4), title, base, run);
		showOptions = false;
	}
	
	public void changeShowOptions() {
		showOptions = !showOptions;
	}

	public Button getCurrentOption() {
		return currentOption;
	}

}
