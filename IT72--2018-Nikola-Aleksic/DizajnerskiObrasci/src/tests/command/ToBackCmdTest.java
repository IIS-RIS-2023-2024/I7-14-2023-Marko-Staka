package tests.command;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import command.ToBackCmd;
import geometry.Point;
import geometry.Shape;
import mvc.DrawingModel;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ToBackCmdTest {
	
	private static DrawingModel drawingModel;
	private static Shape shape1;
	private static Shape shape2;
	private static Shape shape3;
	private static ToBackCmd toBackCmd; 
	
	@BeforeEach
	public void setUpBeforeClass() throws Exception {
		drawingModel = new DrawingModel();
		shape1 = new Point(10,10);
		shape2 = new Point(20,20);
		shape3 = new Point(30,30);
		
		drawingModel.add(shape1);
		drawingModel.add(shape2);
		drawingModel.add(shape3);
		toBackCmd = new ToBackCmd(drawingModel, shape3, drawingModel.getShapes().indexOf(shape3));
	}
	
	@Test
	@Order(1)
	public final void testExecute() {
		toBackCmd.execute();
		assertTrue(drawingModel.getShapes().indexOf(shape3) == 1);
	}
	
	@Test
	@Order(2)
	public final void testUnexecute() {
		toBackCmd.unexecute();
		assertTrue(drawingModel.getShapes().indexOf(shape3) == 2);
	}
}
