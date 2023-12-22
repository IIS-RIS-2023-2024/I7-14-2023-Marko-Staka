package command;

import java.util.Collections;

import geometry.Shape;
import mvc.DrawingModel;

public class BringToFrontCmd implements Command {

	private DrawingModel model;
	private Shape shape;
	private int position;

	public BringToFrontCmd(DrawingModel model, Shape shape, int position) {
		this.model = model;
		this.shape = shape;
		this.position = position;
	}

	@Override
	public void execute() {
		for (int i = position; i < (model.getShapes().size() - 1); i++) {
			Collections.swap(model.getShapes(), i, i + 1);
		}

	}

	@Override
	public void unexecute() {
		for (int i = (model.getShapes().size() - 1); i > position; i--) {
			Collections.swap(model.getShapes(), i, i - 1);
		}

	}

	@Override
	public String toString() {
		return "Bring to front: " + shape + ", " + position + "\n";
	}
}