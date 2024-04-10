package tests.modify;

import static org.junit.Assert.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import adapter.HexagonAdapter;
import command.ModifyHexagonCmd;
import geometry.Point;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ModifyHexagonCmdTest {

	private static ModifyHexagonCmd modifyHexagonCmd;
	private static HexagonAdapter hexagon;
	private static HexagonAdapter newState;
	private static HexagonAdapter tempOldState;

	@BeforeEach
	public void setUpBeforeClass() throws Exception {
		hexagon = new HexagonAdapter(new Point(10,10), 10);
		newState = new HexagonAdapter(new Point(20,20), 20);
		tempOldState = hexagon;
		modifyHexagonCmd = new ModifyHexagonCmd(hexagon,newState);
	}

	@Test
	@Order(1)
	public final void testExecute() {
		modifyHexagonCmd.execute();
		assertTrue(hexagon.getHexagonCenter().getX() == newState.getHexagonCenter().getX());
		assertTrue(hexagon.getHexagonCenter().getY() == newState.getHexagonCenter().getY());
		assertTrue(hexagon.getHexagonRadius() == newState.getHexagonRadius());
	}
	

	@Test
	@Order(2)
	public final void testUnxecute() {
		modifyHexagonCmd.unexecute();
		assertTrue(hexagon.getHexagonCenter().getX() == tempOldState.getHexagonCenter().getX());
		assertTrue(hexagon.getHexagonCenter().getY() == tempOldState.getHexagonCenter().getY());
		assertTrue(hexagon.getHexagonRadius() == tempOldState.getHexagonRadius());
	}
}
