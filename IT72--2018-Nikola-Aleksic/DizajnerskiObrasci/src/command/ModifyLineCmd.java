package command;

import geometry.Line;

public class ModifyLineCmd implements Command{

	private static Line oldState;
	private static Line newState;
	private static Line original = new Line();

	public ModifyLineCmd(Line oldState, Line newState) {
		ModifyLineCmd.oldState = oldState;
		ModifyLineCmd.newState = newState;
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
