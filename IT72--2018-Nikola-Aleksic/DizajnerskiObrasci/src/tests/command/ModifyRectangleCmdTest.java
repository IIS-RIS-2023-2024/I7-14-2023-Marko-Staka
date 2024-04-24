package tests.command;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import command.ModifyRectangleCmd;
import geometry.Point;
import geometry.Rectangle;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ModifyRectangleCmdTest {

	private static ModifyRectangleCmd modifyRectangleCmd;
	private static Rectangle rectangle;
	private static Rectangle newState;
	private static Rectangle tempOldState;
	

	@BeforeEach
	public void setUpBeforeClass() throws Exception {
		rectangle = new Rectangle(new Point(10,10), 10, 10);
		newState = new Rectangle(new Point(20,20), 20, 20);
		tempOldState = rectangle;
		modifyRectangleCmd = new ModifyRectangleCmd(rectangle,newState);
	}

	@Test
	@Order(1)
	public final void testExecute() {
		modifyRectangleCmd.execute();
		assertTrue(rectangle.toString().equals(newState.toString()));
		
	}
	

	@Test
	@Order(2)
	public final void testUnexecute() {
		modifyRectangleCmd.unexecute();
		assertTrue(rectangle.toString().equals(tempOldState.toString()));		
	}
}
