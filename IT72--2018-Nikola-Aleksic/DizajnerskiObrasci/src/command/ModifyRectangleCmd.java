package command;

import geometry.Rectangle;

public class ModifyRectangleCmd implements Command{

	private static Rectangle oldState;
	private static Rectangle newState;
	private static Rectangle original = new Rectangle();

	public ModifyRectangleCmd(Rectangle oldState, Rectangle newState) {
		ModifyRectangleCmd.oldState = oldState;
		ModifyRectangleCmd.newState = newState;
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
