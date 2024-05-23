package tests.mvc;

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
public class UnselectTest {
	private static DrawingModel model;
	private static DrawingFrame frame;
	private static DrawingController controller;
	
	private static SelectShapeCmd selectedShapeCmd;
	private static SelectShapeCmd selectedShapeCmd2;
	private static Shape point;
	private static Shape point2;
	
	@BeforeEach
	public void setUpBeforeClass() throws Exception {
        model = new DrawingModel();
        frame = new DrawingFrame();
        controller = new DrawingController(model, frame);   

		point = new Point(10,10);
		point2 = new Point(20,20);
		selectedShapeCmd = new SelectShapeCmd(controller, point);
		selectedShapeCmd2 = new SelectShapeCmd(controller, point2);
	}
	
    @Test
	@Order(1)
    public final void testUnselect() {
		point.setSelected(true);
		point2.setSelected(true);
		selectedShapeCmd.execute();
		selectedShapeCmd2.execute();
		
		controller.unselectShapes();
		assertSame(controller.getSelectedShapeList().size(), 0);
    }
}
