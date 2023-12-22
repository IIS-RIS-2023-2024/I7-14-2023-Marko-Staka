package command;

import geometry.Shape;
import mvc.DrawingModel;

public class RemoveShapeCmd implements Command{

	private Shape shape;
	private DrawingModel model;
	private int position;
	
	public RemoveShapeCmd(DrawingModel model, Shape shape, int position) {
		this.model = model;
		this.shape = shape;
		this.position = position;
	}
	
	@Override
	public void execute() {
		model.remove(shape);
		
	}

	@Override
	public void unexecute() {
		model.getShapes().add(position, shape);
		
	}
	
	@Override
	public String toString() {
		return "Delete: " + shape + ", " + position + "\n";
	}

}
