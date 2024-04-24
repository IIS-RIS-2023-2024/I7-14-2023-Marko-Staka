package tests.command;

import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import command.ModifyPointCmd;
import geometry.Point;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ModifyPointCmdTest {

	private static ModifyPointCmd modifyPointCmd;
	private static Point point;
	private static Point newState;
	private static Point tempOldState;
	
	@BeforeEach
	public void setUpBeforeClass() throws Exception {
		point = new Point(150,10);
		newState = new Point(20,20);
		tempOldState = point;
		modifyPointCmd = new ModifyPointCmd(point,newState);
	}
	
	@Test
	@Order(1)
	public final void testExecute() {
		modifyPointCmd.execute();	
		assertTrue(point.toString().equals(newState.toString()));
	}
	

	@Test
	@Order(2)
	public final void testUnxecute() {
		modifyPointCmd.unexecute();		
		assertTrue(point.toString().equals(tempOldState.toString()));
	}
}
