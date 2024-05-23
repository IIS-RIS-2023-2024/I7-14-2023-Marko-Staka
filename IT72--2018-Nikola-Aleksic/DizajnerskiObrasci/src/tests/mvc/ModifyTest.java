package tests.mvc;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import adapter.HexagonAdapter;
import command.AddShapeCmd;
import command.SelectShapeCmd;
import dialogues.DlgCircle;
import dialogues.DlgDonut;
import dialogues.DlgHexagon;
import dialogues.DlgLine;
import dialogues.DlgPoint;
import dialogues.DlgRectangle;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import mvc.DrawingController;
import mvc.DrawingFrame;
import mvc.DrawingModel;
import services.ModificationService;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ModifyTest {
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
    void testModifyPoint() {
    	Point point = new Point(1,1);
    	AddShapeCmd addRectangleCommand = new AddShapeCmd(model, point);
    	addRectangleCommand.execute();

   	 	int listSize = model.getShapes().size();
    	SelectShapeCmd selectShapeCmd = new SelectShapeCmd(controller, model.getOneShape(listSize - 1));
    	selectShapeCmd.execute();
    	
    	DlgPoint mockDialog = mock(DlgPoint.class);
   	 	when(mockDialog.isCommited()).thenReturn(true);
   	 	when(mockDialog.getPoint()).thenReturn(new Point(50,50));

   	 	controller.getModificationService().setDialogPoint(mockDialog);
    	controller.modify();

   	 	Point checkPoint = (Point)model.getOneShape(0);

        assertTrue(checkPoint instanceof Point); 
        assertEquals(checkPoint.getX(), 50);
        assertEquals(checkPoint.getY(), 50);
    }
    
    @Test
	@Order(2)
    void testModifyLine() {
    	Line line = new Line(new Point(1,1), new Point(2,2));
    	AddShapeCmd addRectangleCommand = new AddShapeCmd(model, line);
    	addRectangleCommand.execute();

   	 	int listSize = model.getShapes().size();
    	SelectShapeCmd selectShapeCmd = new SelectShapeCmd(controller, model.getOneShape(listSize - 1));
    	selectShapeCmd.execute();
    	
    	DlgLine mockDialog = mock(DlgLine.class);
   	 	when(mockDialog.isCommited()).thenReturn(true);
   	 	when(mockDialog.getLine()).thenReturn(new Line(new Point(50,50), new Point(60,60)));

   	 	controller.getModificationService().setDialogLine(mockDialog);
    	controller.modify();

    	Line checkLine = (Line)model.getOneShape(0);

        assertTrue(checkLine instanceof Line); 
        assertEquals(checkLine.getStartPoint().getX(), 50);
        assertEquals(checkLine.getStartPoint().getY(), 50);
        assertEquals(checkLine.getEndPoint().getX(), 60);
        assertEquals(checkLine.getEndPoint().getY(), 60);
    }

    @Test
	@Order(3)
    void testModifyRectangle() {
    	Rectangle rectangle = new Rectangle(new Point(1,1), 1, 1);
    	AddShapeCmd addRectangleCommand = new AddShapeCmd(model, rectangle);
    	addRectangleCommand.execute();

   	 	int listSize = model.getShapes().size();
    	SelectShapeCmd selectShapeCmd = new SelectShapeCmd(controller, model.getOneShape(listSize  -1));
    	selectShapeCmd.execute();
    	
    	DlgRectangle mockDialog = mock(DlgRectangle.class);
   	 	when(mockDialog.isCommited()).thenReturn(true);
   	 	when(mockDialog.getRectangle()).thenReturn(new Rectangle(new Point(50,50), 50, 50));

   	 	controller.getModificationService().setDialogRectangle(mockDialog);
    	controller.modify();

   	 	Rectangle checkRectangle = (Rectangle)model.getOneShape(0);

        assertTrue(checkRectangle instanceof Rectangle); 
        assertEquals(checkRectangle.getUpperLeftPoint().getX(), 50);
        assertEquals(checkRectangle.getUpperLeftPoint().getY(), 50);
        assertEquals(checkRectangle.getHeight(), 50);
        assertEquals(checkRectangle.getWidth(), 50);
    }
    
    @Test
  	@Order(4)
      void testModifyCircle() {
      	Circle circle = new Circle(new Point(1,1), 5);
      	AddShapeCmd addRectangleCommand = new AddShapeCmd(model, circle);
      	addRectangleCommand.execute();

     	int listSize = model.getShapes().size();
      	SelectShapeCmd selectShapeCmd = new SelectShapeCmd(controller, model.getOneShape(listSize - 1));
      	selectShapeCmd.execute();
      	
      	DlgCircle mockDialog = mock(DlgCircle.class);
     	when(mockDialog.isCommited()).thenReturn(true);
     	when(mockDialog.getCircle()).thenReturn(new Circle(new Point(10,10), 50));

     	controller.getModificationService().setDialogCircle(mockDialog);
      	controller.modify();

      	Circle checkCircle = (Circle)model.getOneShape(0);

        assertTrue(checkCircle instanceof Circle); 
        assertEquals(checkCircle.getCenter().getX(), 10);
        assertEquals(checkCircle.getCenter().getY(), 10);
        assertEquals(checkCircle.getRadius(), 50);
      }
    
    @Test
  	@Order(5)
      void testModifyDonut() {
      	Donut donut = new Donut(new Point(1,1), 10, 5);
      	AddShapeCmd addRectangleCommand = new AddShapeCmd(model, donut);
      	addRectangleCommand.execute();

     	int listSize = model.getShapes().size();
      	SelectShapeCmd selectShapeCmd = new SelectShapeCmd(controller, model.getOneShape(listSize - 1));
      	selectShapeCmd.execute();
      	
      	DlgDonut mockDialog = mock(DlgDonut.class);
     	when(mockDialog.isCommited()).thenReturn(true);
     	when(mockDialog.getDonut()).thenReturn(new Donut(new Point(10,10), 50, 20));

     	controller.getModificationService().setDialogDonut(mockDialog);
      	controller.modify();

      	Donut checkDonut = (Donut)model.getOneShape(0);

        assertTrue(checkDonut instanceof Donut); 
        assertEquals(checkDonut.getCenter().getX(), 10);
        assertEquals(checkDonut.getCenter().getY(), 10);
        assertEquals(checkDonut.getRadius(), 50);
        assertEquals(checkDonut.getInnerRadius(), 20);
      }
    
    @Test
  	@Order(6)
      void testModifyHexagon() {
      	HexagonAdapter hexagon = new HexagonAdapter(new Point(1,1), 10, Color.black, Color.white);
      	AddShapeCmd addRectangleCommand = new AddShapeCmd(model, hexagon);
      	addRectangleCommand.execute();

     	controller.getSelectedShapeList().add(hexagon);
     	
      	DlgHexagon mockDialog = mock(DlgHexagon.class);
     	when(mockDialog.isCommited()).thenReturn(true);
     	when(mockDialog.getHexagon()).thenReturn(new HexagonAdapter(new Point(5,5), 50, Color.black, Color.white));

     	controller.getModificationService().setDialogHexagon(mockDialog);
      	controller.modify();

      	HexagonAdapter checkHexagon = (HexagonAdapter)model.getOneShape(0);

        assertTrue(checkHexagon instanceof HexagonAdapter); 
        assertEquals(checkHexagon.getHexagonCenter().getX(), 5);
        assertEquals(checkHexagon.getHexagonCenter().getY(), 5);
        assertEquals(checkHexagon.getHexagonRadius(), 50);
      }
    
	@Test
	@Order(7)
	public void checkBtnModify() {
		controller.getSelectedShapeList().add(new Point(1,1));
		controller.getSelectedShapeList().add(new Point(2,2));
		
		controller.disableButtons();

		assertFalse(frame.getBtnModify().isEnabled());
	}
    
}
