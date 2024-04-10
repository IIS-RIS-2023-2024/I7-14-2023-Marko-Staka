package command;

import geometry.Point;

public class ModifyPointCmd implements Command{

	private static Point oldState;
	private static Point newState;
	private static Point original = new Point();

	public ModifyPointCmd(Point oldState, Point newState) {
		this.oldState = oldState;
		this.newState = newState;
	}
	
	@Override
	public void execute() {
		original = oldState.clone(original);
		oldState = newState.clone(oldState);
		
	}

	@Override
	public void unexecute() {
		oldState = original.clone(oldState);
		
	}
	
	@Override
	public String toString() {
		return "Modify: " + original + " to "+ newState +  "\n";
	}

}
