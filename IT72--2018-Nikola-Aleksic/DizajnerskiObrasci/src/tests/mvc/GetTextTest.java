package tests.mvc;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import geometry.Point;
import mvc.DrawingController;
import mvc.DrawingFrame;
import mvc.DrawingModel;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GetTextTest {
	private static DrawingModel model;
	private static DrawingFrame frame;
	private static DrawingController controller;
	
	@BeforeEach
	public void setUpBeforeClass() throws Exception {
        model = new DrawingModel();
        frame = new DrawingFrame();
        controller = new DrawingController(model, frame);
	}
	

	@Test
	@Order(1)
	public void testDelete() {
		controller.getSelectedShapeList().add(new Point(1,1));
		controller.getSelectedShapeList().add(new Point(2,2));
		controller.delete();

		assertEquals(controller.getSelectedShapeList().size(), 0);
	}
	
	@Test
	@Order(2)
	public void testTextToList() {
		frame.getTextArea().setText("Tekst");
		controller.textToList(frame.getTextArea());
		
		assertEquals(controller.logList.get(0), "Tekst");
	}
	
}
