package catan.graphics.objects.ui;

import java.awt.Graphics;

public class Hitbox {
	
	private float x1, y1, x2, y2;
	
	private int width, height;
	
	public Hitbox(float x, float y, int width, int height) {
		this.x1 = x;
		this.y1 = y;
		this.width = width;
		this.height = height;
		this.x2 = x + width;
		this.y2 = y + height;
	}
	
	public void draw(Graphics g) {
		g.fillRect((int) x1, (int) y1, width, height);
	}
	
	public void drawOutline(Graphics g, int thickness) {
		g.fillRect((int) x1 - thickness, (int) y1 - thickness, width + thickness, height + thickness);
	}
	
	public boolean isPointInHitbox(int x, int y) {
		return x > x1 && x <= x2 && y > y1 && y <= y2;
	}
	
	public boolean doesHitboxIntersectHitbox(Hitbox hitbox) {
		return x1 < hitbox.getX2() && x2 > hitbox.getX1() && y1 < hitbox.getY2() && y2 > hitbox.getY1();
	}
	
	public void transform(float x, float y) {
		transformX(x);
		transformY(y);
	}
	
	public void transformX(float x) {
		x1 += x;
		x2 += x;
	}
	
	public void transformY(float y) {
		y1 += y;
		y2 += y;
	}

	public int getX1() {
		return (int) x1;
	}

	public int getY1() {
		return (int) y1;
	}

	public int getX2() {
		return (int) x2;
	}

	public int getY2() {
		return (int) y2;
	}

	public float getPreciseX1() {
		return x1;
	}

	public float getPreciseY1() {
		return y1;
	}

	public float getPreciseX2() {
		return x2;
	}

	public float getPreciseY2() {
		return y2;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
