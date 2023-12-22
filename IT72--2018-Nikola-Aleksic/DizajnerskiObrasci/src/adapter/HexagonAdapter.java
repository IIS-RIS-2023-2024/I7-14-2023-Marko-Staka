package adapter;

import java.awt.Color;
import java.awt.Graphics;

import drawing.Moveable;
import geometry.Point;
import geometry.SurfaceShape;
import hexagon.Hexagon;

public class HexagonAdapter extends SurfaceShape implements Moveable{
	
	private Hexagon hexagon = new Hexagon(0, 0, 0);

	public HexagonAdapter() {

	}
	
	public HexagonAdapter(Point center, int radius) {
		this.hexagon = new Hexagon(center.getX(), center.getY(), radius);
	}
	
	public HexagonAdapter(Point center, int radius, boolean selected) {
		this(center, radius);
		this.hexagon.setSelected(selected);
	}

	public HexagonAdapter(Point center, int radius, boolean selected, Color color) {
		this(center, radius, selected);
		this.hexagon.setBorderColor(color);
	}

	public HexagonAdapter(Point center, int radius, boolean selected, Color color, Color innerColor) {
		this(center, radius, selected, color);
		this.hexagon.setAreaColor(innerColor);
	}

	public HexagonAdapter(Point center, int radius, Color color, Color innerColor) {
		this.hexagon = new Hexagon(center.getX(), center.getY(), radius);
		this.hexagon.setBorderColor(color);
		this.hexagon.setAreaColor(innerColor);
	}
	
	@Override
	public void moveBy(int byX, int byY) {
		this.hexagon.setX(this.hexagon.getX() + byX);
		this.hexagon.setY(this.hexagon.getY() + byY);
	}
	
	@Override
	public void fill(Graphics g) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void draw(Graphics g) {
		this.hexagon.paint(g);
	}
	
	public boolean contains(int x, int y) {
		return hexagon.doesContain(x, y);
	}
	
	public boolean contains(Point p) {
		return hexagon.doesContain(p.getX(), p.getY());
	}
	
	public double area() {
		return (int) (((3 * Math.sqrt(3)) * this.hexagon.getR() * this.hexagon.getR())/ 2);
	}
	
	@Override
	public boolean isSelected() {
		return hexagon.isSelected();
	}

	@Override
	public void setSelected(boolean selected) {
		this.hexagon.setSelected(selected);
	}
	
	public Hexagon getHexagon() {
		return this.hexagon;
	}

	public void setHexagon(Point center, int radius, Color color, Color innerColor) {
		this.hexagon = new Hexagon(center.getX(), center.getY(), radius);
		this.hexagon.setBorderColor(color);
		this.hexagon.setAreaColor(innerColor);
		hexagon.setSelected(true);
	}

	public Point getHexagonCenter() {
		return new Point(this.hexagon.getX(), this.hexagon.getY());
	}

	public void setHexagonCenter(Point center) {
		this.hexagon.setX(center.getX());
		this.hexagon.setY(center.getY());
	}

	public int getHexagonRadius() {
		return this.hexagon.getR();
	}

	public void setHexagonRadius(int radius) {
		this.hexagon.setR(radius);
	}

	public Color getHexagonBorderColor() {
		return this.hexagon.getBorderColor();
	}

	public void setHexagonBorderColor(Color color) {
		this.hexagon.setBorderColor(color);
	}

	public Color getHexagonInnerColor() {
		return this.hexagon.getAreaColor();
	}

	public void setHexagonInnerColor(Color innerColor) {
		this.hexagon.setAreaColor(innerColor);
	}
	
	@Override
	public String toString() {
		return "Hexagon: [center= " + getHexagonCenter().getX() + ", " + getHexagonCenter().getY() + ", radius= " + getHexagonRadius() + ", color= " + Integer.toString(getHexagonInnerColor().getRGB()) + ", border color= " + Integer.toString(getHexagonBorderColor().getRGB()) + "]";
	}
	
	public HexagonAdapter clone(HexagonAdapter hexagon) {		
		hexagon.setHexagonCenter(this.getHexagonCenter());
		hexagon.setHexagonRadius(this.getHexagonRadius());
		hexagon.setHexagonBorderColor(this.getHexagonBorderColor());
		hexagon.setHexagonInnerColor(this.getHexagonInnerColor());

		return hexagon;
	}

}
