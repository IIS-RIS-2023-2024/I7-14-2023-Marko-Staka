package tests.axisZ;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import command.ToFrontCmd;
import geometry.Point;
import geometry.Shape;
import mvc.DrawingModel;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ToFrontCmdTest {

	private static DrawingModel drawingModel;
	private static Shape shape1;
	private static Shape shape2;
	private static Shape shape3;
	private static ToFrontCmd toFrontCmd;
	
	@BeforeEach
	public void setUpBeforeClass() throws Exception {
		drawingModel = new DrawingModel();
		shape1 = new Point(10,10);
		shape2 = new Point(20,20);
		shape3 = new Point(30,30);
		
		drawingModel.add(shape1);
		drawingModel.add(shape2);
		drawingModel.add(shape3);
		toFrontCmd = new ToFrontCmd(drawingModel, shape1, drawingModel.getShapes().indexOf(shape1));
	}
	
	@Test
	@Order(1)
	public final void testExecute() {
		toFrontCmd.execute();
		assertTrue(drawingModel.getShapes().indexOf(shape1) == 1);
	}
	
	@Test
	@Order(2)
	public final void testUnxecute() {
		toFrontCmd.unexecute();
		assertTrue(drawingModel.getShapes().indexOf(shape1) == 0);
	}
}
