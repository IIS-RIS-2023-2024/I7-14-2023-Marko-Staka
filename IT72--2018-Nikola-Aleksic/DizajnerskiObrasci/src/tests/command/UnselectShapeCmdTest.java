package tests.command;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertSame;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.TestMethodOrder;

import command.UnselectShapeCmd;
import geometry.Point;
import geometry.Shape;
import mvc.DrawingController;
import mvc.DrawingFrame;
import mvc.DrawingModel;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UnselectShapeCmdTest {

	private static Shape shape;
	private static UnselectShapeCmd unselectShapeCommand;
	private static DrawingController controller;
	
	@BeforeEach
	public void setUpBeforeClass() throws Exception {

		shape = new Point(10,10, false);
		controller = new DrawingController(new DrawingModel(), new DrawingFrame());
		unselectShapeCommand = new UnselectShapeCmd(controller, shape);
	}
	
	@Test
	@Order(1)
	public final void testExecute() {
		unselectShapeCommand.execute();
		assertSame(shape.isSelected(), false);
	}
	
	@Test
	@Order(2)
	public final void testUnexecute() {
		unselectShapeCommand.unexecute();
		assertSame(shape.isSelected(), true);
	}
}
