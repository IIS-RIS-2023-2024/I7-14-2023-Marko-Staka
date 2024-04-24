package tests.command;

import static org.junit.Assert.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import command.SelectShapeCmd;
import geometry.Point;
import geometry.Shape;
import mvc.DrawingController;
import mvc.DrawingFrame;
import mvc.DrawingModel;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SelectShapeCmdTest {
	private static Shape shape;
	private static SelectShapeCmd selectShapeCommand;
	private static DrawingController controller;
	
	@BeforeEach
	public void setUpBeforeClass() throws Exception {

		shape = new Point(10,10, false);
		controller = new DrawingController(new DrawingModel(), new DrawingFrame());
		selectShapeCommand = new SelectShapeCmd(controller, shape);
	}
	
	@Test
	@Order(1)
	public final void testExecute() {
		selectShapeCommand.execute();
		assertSame(shape.isSelected(), true);
	}
	
	@Test
	@Order(2)
	public final void testUnexecute() {
		selectShapeCommand.unexecute();
		assertSame(shape.isSelected(), false);
	}
}
