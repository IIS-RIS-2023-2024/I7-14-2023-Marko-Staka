package tests.modify;
import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import command.ModifyLineCmd;
import geometry.Line;
import geometry.Point;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ModifyLineCmdTest {

	private static ModifyLineCmd modifyLineCmd;
	private static Line line;
	private static Line newState;
	private static Line tempOldState;
	
	@BeforeEach
	public void setUpBeforeClass() throws Exception {
		line = new Line(new Point(10,10), new Point(20,20));
		newState = new Line(new Point(30,30), new Point(40,40));
		tempOldState = line;
		modifyLineCmd = new ModifyLineCmd(line,newState);
	}
	
	@Test
	@Order(1)
	public final void testExecute() {
		modifyLineCmd.execute();	
		assertTrue(line.toString().equals(newState.toString()));
	}
	

	@Test
	@Order(2)
	public final void testUnxecute() {
		modifyLineCmd.unexecute();	
		assertTrue(line.toString().equals(tempOldState.toString()));
	}
}
