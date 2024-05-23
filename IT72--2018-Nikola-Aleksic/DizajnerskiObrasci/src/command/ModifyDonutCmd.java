package command;

import geometry.Donut;

public class ModifyDonutCmd implements Command{

	private static Donut oldState;
	private static Donut newState;
	private static Donut original = new Donut();

	public ModifyDonutCmd(Donut oldState, Donut newState) {
		ModifyDonutCmd.oldState = oldState;
		ModifyDonutCmd.newState = newState;
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
