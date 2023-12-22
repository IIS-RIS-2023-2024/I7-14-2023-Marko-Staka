package command;

import java.util.Collections;

import geometry.Shape;
import mvc.DrawingModel;

public class BringToBackCmd implements Command {

	private DrawingModel model;
	private Shape shape;
	private int position;

	public BringToBackCmd(DrawingModel model, Shape shape, int position) {
		this.model = model;
		this.shape = shape;
		this.position = position;
	}

	@Override
	public void execute() {
		for (int i = position; i > 0; i--) {
			Collections.swap(model.getShapes(), i, i - 1);
		}

	}

	@Override
	public void unexecute() {
		for (int i = 0; i < position; i++) {
			Collections.swap(model.getShapes(), i, i + 1);
		}

	}

	@Override
	public String toString() {
		return "Bring to back: " + shape + ", " + position + "\n";
	}
}