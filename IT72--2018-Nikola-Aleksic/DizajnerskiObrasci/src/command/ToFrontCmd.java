package command;

import java.util.Collections;

import geometry.Shape;
import mvc.DrawingModel;

public class ToFrontCmd implements Command{

	private Shape shape;
	private DrawingModel model;
	private int position;
	
	public ToFrontCmd(DrawingModel model, Shape shape, int position) {
		this.model = model;
		this.shape = shape;
		this.position = position;
	}
	
	@Override
	public void execute() {
		if (position < model.getShapes().size() - 1) {
			Collections.swap(model.getShapes(), position, position + 1);
			position++;
		}
	}

	@Override
	public void unexecute() {
		if (position > 0) {
			Collections.swap(model.getShapes(), position, position - 1);
			position--;
		}
	}
	
	@Override
	public String toString() {
		return "Move to front: " + shape + ", " + position + "\n";
	}

}