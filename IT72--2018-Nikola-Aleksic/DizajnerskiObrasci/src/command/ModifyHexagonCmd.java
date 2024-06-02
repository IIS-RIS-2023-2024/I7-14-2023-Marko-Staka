package command;

import adapter.HexagonAdapter;

public class ModifyHexagonCmd implements Command{

	private static HexagonAdapter oldState;
	private static HexagonAdapter newState;
	private static HexagonAdapter original = new HexagonAdapter();
	
	public ModifyHexagonCmd(HexagonAdapter oldState, HexagonAdapter newState) {
		ModifyHexagonCmd.oldState = oldState;
		ModifyHexagonCmd.newState = newState;
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
