package tests.modify;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import command.ModifyDonutCmd;
import geometry.Donut;
import geometry.Point;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ModifyDonutCmdTest {
	
	private static ModifyDonutCmd modifyDonutCmd;
	private static Donut donut;
	private static Donut newState;
	private static Donut tempOldState;
	

	@BeforeEach
	public void setUpBeforeClass() throws Exception {
		donut = new Donut(new Point(10,10), 100, 10);
		newState = new Donut(new Point(20,20), 200, 20);
		tempOldState = donut;
		modifyDonutCmd = new ModifyDonutCmd(donut,newState);
	}
	
	@Test
	@Order(1)
	public final void testExecute() {
		modifyDonutCmd.execute();
		assertTrue(donut.toString().equals(newState.toString()));
	}
	

	@Test
	@Order(2)
	public final void testUnexecute() {
		modifyDonutCmd.unexecute();
		assertTrue(donut.toString().equals(tempOldState.toString()));
	}
}
