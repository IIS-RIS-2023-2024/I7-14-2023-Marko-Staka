package tests.mvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import adapter.HexagonAdapter;
import command.AddShapeCmd;
import dialogues.DlgCircle;
import dialogues.DlgDonut;
import dialogues.DlgHexagon;
import dialogues.DlgRectangle;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;

import java.awt.event.MouseEvent;

import mvc.DrawingController;
import mvc.DrawingFrame;
import mvc.DrawingModel;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MouseEventTest {
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
    void testClickedWithPointSelected() {
        MouseEvent mockEvent = new MouseEvent(frame, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, 100, 100, 1, false);
        frame.gettglPoint().setSelected(true);
        int listSize = model.getShapes().size();

        controller.mouseClicked(mockEvent);

        assertEquals(listSize + 1, model.getShapes().size());
        assertTrue(model.getShapes().get(listSize) instanceof Point);
    }
    
    @Test
	@Order(2)
    void testClickedWithLineSelected() {
        MouseEvent mockEventStartPoint = new MouseEvent(frame, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, 100, 100, 1, false);
        MouseEvent mockEventEndPoint = new MouseEvent(frame, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, 200, 200, 1, false);
        frame.gettglLine().setSelected(true);
        int listSize = model.getShapes().size();
        
        controller.mouseClicked(mockEventStartPoint);
        controller.mouseClicked(mockEventEndPoint);

        assertEquals(listSize + 1, model.getShapes().size());
        assertTrue(model.getShapes().get(listSize) instanceof Line);
    }
    
    @Test
	@Order(3)
    void testClickedWithRectangleSelected() {        
    	 DlgRectangle mockDialog = mock(DlgRectangle.class);

    	 when(mockDialog.getRectangle()).thenReturn(new Rectangle(new Point(10,10), 10, 10));
    	 frame.gettglRectangle().setSelected(true);

    	 MouseEvent mockEvent = mock(MouseEvent.class);
    	 controller.setDialogRectangle(mockDialog);

    	 int listSize = model.getShapes().size();
    	 controller.drawShape(mockEvent);
    	   
         assertEquals(listSize + 1, model.getShapes().size());
         assertTrue(model.getShapes().get(listSize) instanceof Rectangle);    	   
}
    
    @Test
	@Order(4)
    void testClickedWithCircleSelected() {
    	DlgCircle mockDialog = mock(DlgCircle.class);

    	when(mockDialog.getCircle()).thenReturn(new Circle(new Point(10,10), 10));
   	 	frame.gettglCircle().setSelected(true);

   	 	MouseEvent mockEvent = mock(MouseEvent.class);
   	 	controller.setDialogCircle(mockDialog);

   	 	int listSize = model.getShapes().size();
   	 	controller.drawShape(mockEvent);
   	   
   	 	assertEquals(listSize + 1, model.getShapes().size());
   	 	assertTrue(model.getShapes().get(listSize) instanceof Circle);    
    }
    
    @Test
	@Order(5)
    void testClickedWithDonutSelected() {
    	DlgDonut mockDialog = mock(DlgDonut.class);

    	when(mockDialog.getDonut()).thenReturn(new Donut(new Point(10,10), 10, 10));
   	 	frame.gettglDonut().setSelected(true);

   	 	MouseEvent mockEvent = mock(MouseEvent.class);
   	 	controller.setDialogDonut(mockDialog);

   	 	int listSize = model.getShapes().size();
   	 	controller.drawShape(mockEvent);
   	   
   	 	assertEquals(listSize + 1, model.getShapes().size());
   	 	assertTrue(model.getShapes().get(listSize) instanceof Donut); 
    }
    
    @Test
	@Order(6)
    void testClickedWithHexagonSelected() {
   	 	frame.gettglHexagon().setSelected(true);
    	DlgHexagon mockDialog = mock(DlgHexagon.class);

    	when(mockDialog.getHexagon()).thenReturn(new HexagonAdapter(new Point(10,10), 10));
   	 	frame.gettglHexagon().setSelected(true);

   	 	MouseEvent mockEvent = mock(MouseEvent.class);
   	 	controller.setDialogHexagon(mockDialog);
   	 	HexagonAdapter hexagon = (HexagonAdapter) controller.makeHexagon(mockEvent);
   	   
   	 	assertEquals(hexagon.getHexagonCenter().getX(), 10);
   	 	assertEquals(hexagon.getHexagonCenter().getY(), 10);
   	 	assertEquals(hexagon.getHexagonRadius(), 10);
    }
    
    @Test
	@Order(7)
    void testClickedWithSelectionButton() {   
    	Rectangle rectangle = new Rectangle(new Point(0,0), 100, 100);
    	AddShapeCmd addRectangleCmd = new AddShapeCmd(model,rectangle);
    	addRectangleCmd.execute();
    	
    	frame.gettglSelection().setSelected(true); 
    	
    	MouseEvent mockEvent = new MouseEvent(frame, MouseEvent.MOUSE_CLICKED, System.currentTimeMillis(), 0, 100, 100, 1, false);

    	assertFalse(model.getOneShape(0).isSelected());
    	controller.mouseClicked(mockEvent);
    	assertTrue(model.getOneShape(0).isSelected());
    	controller.mouseClicked(mockEvent);
    	assertFalse(model.getOneShape(0).isSelected());
    }
    
    
}
