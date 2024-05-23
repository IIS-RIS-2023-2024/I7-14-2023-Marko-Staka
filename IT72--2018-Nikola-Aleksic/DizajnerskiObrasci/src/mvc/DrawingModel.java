package mvc;

import java.util.ArrayList;

import geometry.Shape;

public class DrawingModel {
	
	private ArrayList<Shape> shapes = new ArrayList<Shape>();

	public DrawingModel() {

	}

	public ArrayList<Shape> getShapes() {
		return shapes;
	}

	public void add(Shape shape) {
		shapes.add(shape);
	}

	public void remove(Shape shape) {
		shapes.remove(shape);
	}

	public Shape getOneShape(int index) {
		return shapes.get(index);
	}

}
