package tests.command;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import command.ModifyCircleCmd;
import geometry.Circle;
import geometry.Point;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ModifyCircleCmdTest {
	
	private static ModifyCircleCmd modifyCircleCmd;
	private static Circle circle;
	private static Circle newState;
	private static Circle tempOldState;
	
	@BeforeEach
	public void setUpBeforeClass() throws Exception {
		circle = new Circle(new Point(10,10), 10);
		newState = new Circle(new Point(20,20), 20);
		tempOldState = circle;
		modifyCircleCmd = new ModifyCircleCmd(circle,newState);
		
	}
	
	@Test
	@Order(1)
	public final void testExecute() {
		modifyCircleCmd.execute();
		assertTrue(circle.toString().equals(newState.toString()));
	}
	
	@Test
	@Order(2)
	public final void testUnexecute() {
		modifyCircleCmd.unexecute();
		assertTrue(circle.toString().equals(tempOldState.toString()));
	}
}
