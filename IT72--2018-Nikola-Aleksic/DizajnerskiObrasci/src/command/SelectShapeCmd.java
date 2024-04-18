package command;

import geometry.Shape;
import mvc.DrawingController;

public class SelectShapeCmd implements Command{
	
	private DrawingController controller;
	private Shape shape;

	public SelectShapeCmd(DrawingController controller, Shape shape) {
		this.controller = controller;
		this.shape = shape;
	}

	@Override
	public void execute() {
		shape.setSelected(true);
		controller.getSelectedShapeList().add(shape);

	}

	@Override
	public void unexecute() {
		shape.setSelected(false);
		controller.getSelectedShapeList().remove(shape);
	}
	
	@Override
	public String toString() {
		return "Select: " + shape + "\n";
	}

}
