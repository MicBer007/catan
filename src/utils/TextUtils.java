package utils;

import java.awt.Graphics;

public class TextUtils {
	
	public static int renderTextCentered(Graphics g, String text, int xCentre, int yTop) {
		int xOffset = (int) g.getFontMetrics().stringWidth(text) / 2;
		g.drawString(text, xCentre - xOffset, yTop);
		return xOffset;
	}

}
