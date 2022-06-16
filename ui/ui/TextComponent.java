package ui;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;

public class TextComponent extends UIComponent {
	
	private String text;
	
	private Color textColour;
	
	private Color backgroundColour;
	
	private int textX = -1000, textY;
	
	private boolean renderOutline = false;
	private int outlineThickness = 2;

	public TextComponent(Hitbox hitbox, String text, Color textColour, Color backgroundColour) {
		super(hitbox);
		this.text = text;
		this.textColour = textColour;
		this.backgroundColour = backgroundColour;
	}

	public TextComponent(Hitbox hitbox, String text, Color textColour, Color backgroundColour, boolean outline) {
		super(hitbox);
		this.text = text;
		this.textColour = textColour;
		this.backgroundColour = backgroundColour;
		this.renderOutline = outline;
	}
	
	@Override
	public void render(Graphics g) {
		if(renderOutline) {
			g.setColor(Color.BLACK);
			getHitbox().drawOutline(g, outlineThickness);
		}
		g.setColor(backgroundColour);
		getHitbox().draw(g);
		g.setColor(textColour);
		if(textX == -1000) {
			FontMetrics f = g.getFontMetrics();
			int textWidth = f.stringWidth(text);
			int textHeight = f.getHeight();
			textX = getHitbox().getX1() + (getHitbox().getWidth() - textWidth) / 2;
			textY = getHitbox().getY1() + (getHitbox().getHeight() - textHeight) / 2 + 12;
		}
		g.drawString(text, textX, textY);
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public Color getTextColour() {
		return textColour;
	}

	public void setTextColour(Color textColour) {
		this.textColour = textColour;
	}

	public Color getBackgroundColour() {
		return backgroundColour;
	}

	public void setBackgroundColour(Color backgroundColour) {
		this.backgroundColour = backgroundColour;
	}

}
