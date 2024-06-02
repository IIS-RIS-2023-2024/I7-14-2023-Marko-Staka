package command;

import java.util.Collections;

import geometry.Shape;
import mvc.DrawingModel;

public class BringToBackCmd implements Command {

	private static DrawingModel model;
	private static Shape shape;
	private static int position;

	public BringToBackCmd(DrawingModel model, Shape shape, int position) {
		BringToBackCmd.model = model;
		BringToBackCmd.shape = shape;
		BringToBackCmd.position = position;
	}

	@Override
	public void execute() {
		for (int i = position; i > 0; i--) {
			Collections.swap(model.getShapes(), i, i - 1);
		}
	}

	@Override
	public void unexecute() {
		model.remove(shape);
		model.getShapes().add(position, shape);
	}

	@Override
	public String toString() {
		return "Bring to back: " + shape + ", " + position + "\n";
	}
}