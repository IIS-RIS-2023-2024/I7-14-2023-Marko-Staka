package command;

import geometry.Shape;
import mvc.DrawingController;

public class UnselectShapeCmd implements Command{

	private DrawingController controller;
	private Shape shape;

	public UnselectShapeCmd(DrawingController controller, Shape shape) {
		this.controller = controller;
		this.shape = shape;
	}

	@Override
	public void execute() {
		shape.setSelected(false);
		controller.getSelectedShapeList().remove(shape);
	}

	@Override
	public void unexecute() {
		shape.setSelected(true);
		controller.getSelectedShapeList().add(shape);
	}
	
	@Override
	public String toString() {
		return "Unselect: " + shape + "\n";
	}

}
