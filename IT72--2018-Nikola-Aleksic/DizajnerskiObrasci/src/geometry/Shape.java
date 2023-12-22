package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

import drawing.Moveable;

public abstract class Shape implements Moveable, Serializable{

	private boolean selected;
	private Color color = Color.BLACK;
	
	public abstract void draw(Graphics g);
	public abstract boolean contains(int x, int y);
	
	public boolean isSelected() {
		return selected;
	}
	
	public void setSelected(boolean selected) {
		this.selected=selected;
	}
	
	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}
	
}
