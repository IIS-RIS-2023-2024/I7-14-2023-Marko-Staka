package command;

import java.util.Collections;

import geometry.Shape;
import mvc.DrawingModel;

public class ToBackCmd implements Command{

	private Shape shape;
	private DrawingModel model;
	private int position;
	
	public ToBackCmd(DrawingModel model, Shape shape, int position) {
		this.model = model;
		this.shape = shape;
		this.position = position;
	}
	
	@Override
	public void execute() {
		if (position > 0) {
			Collections.swap(model.getShapes(), position, position - 1);
		}
	}

	@Override
	public void unexecute() {
		if (position < model.getShapes().size() - 1) {
			Collections.swap(model.getShapes(), position, position + 1);
		}
	}
	
	@Override
	public String toString() {
		return "Move to back: " + shape + ", " + position + "\n";
	}

}