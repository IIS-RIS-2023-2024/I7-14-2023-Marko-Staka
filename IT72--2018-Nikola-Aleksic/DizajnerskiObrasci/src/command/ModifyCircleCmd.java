package command;

import geometry.Circle;

public class ModifyCircleCmd implements Command{

	private static Circle oldState;
	private static Circle newState;
	private static Circle original = new Circle();

	public ModifyCircleCmd(Circle oldState, Circle newState) {
		ModifyCircleCmd.oldState = oldState;
		ModifyCircleCmd.newState = newState;
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
