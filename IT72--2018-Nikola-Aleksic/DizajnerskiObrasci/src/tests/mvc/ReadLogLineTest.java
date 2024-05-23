package tests.mvc;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import adapter.HexagonAdapter;
import command.AddShapeCmd;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import mvc.DrawingController;
import mvc.DrawingFrame;
import mvc.DrawingModel;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ReadLogLineTest {
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
	public void testReadAddPoint() {
        String line = "Add: Point: [x= 482, y= 81, color= -16777216]";
		controller.readLogLine(line);
		  
	    assertEquals(1, model.getShapes().size());
	    assertTrue(model.getShapes().get(0) instanceof Point);
	    Point addedPoint = (Point)model.getShapes().get(0);
	    assertEquals(addedPoint.getX(), 482);
	    assertEquals(addedPoint.getY(), 81);
	}
	
	@Test
	@Order(2)
	public void testReadAddLine() {
        String line = "Add: Line: [startPoint= 536, 58, endPoint= 258, 210, color= -16777216]";
		controller.readLogLine(line);
		  
	    assertEquals(1, model.getShapes().size());
	    assertTrue(model.getShapes().get(0) instanceof Line);
	    Line addedLine = (Line)model.getShapes().get(0);
	    assertEquals(addedLine.getStartPoint().getX(), 536);
	    assertEquals(addedLine.getStartPoint().getY(), 58);
	    assertEquals(addedLine.getEndPoint().getX(), 258);
	    assertEquals(addedLine.getEndPoint().getY(), 210);
	}
	
	@Test
	@Order(3)
	public void testReadAddCircle() {
        String line = "Add: Circle: [center= 651, 190, radius= 55, color= -1, border color= -16777216]";
		controller.readLogLine(line);
		  
	    assertEquals(1, model.getShapes().size());
	    assertTrue(model.getShapes().get(0) instanceof Circle);
	    Circle addedCircle = (Circle)model.getShapes().get(0);
	    assertEquals(addedCircle.getCenter().getX(), 651);
	    assertEquals(addedCircle.getCenter().getY(), 190);
	    assertEquals(addedCircle.getRadius(), 55);
	}
	
	@Test
	@Order(4)
	public void testReadAddDonut() {
        String line = "Add: Donut: [center= 357, 265, radius= 55, innerRadius= 44, color= -1, border color= -16777216]";
		controller.readLogLine(line);
		  
	    assertEquals(1, model.getShapes().size());
	    assertTrue(model.getShapes().get(0) instanceof Donut);
	    Donut addedDonut = (Donut)model.getShapes().get(0);
	    assertEquals(addedDonut.getCenter().getX(), 357);
	    assertEquals(addedDonut.getCenter().getY(), 265);
	    assertEquals(addedDonut.getRadius(), 55);
	    assertEquals(addedDonut.getInnerRadius(), 44);
	}
	
	@Test
	@Order(5)
	public void testReadAddRectangle() {
        String line = "Add: Rectangle: [upperLeftPoint= 506, 329, height=55, width=120, color= -1, border color= -16777216]";
		controller.readLogLine(line);
		  
	    assertEquals(1, model.getShapes().size());
	    assertTrue(model.getShapes().get(0) instanceof Rectangle);
	    Rectangle addedRectangle = (Rectangle)model.getShapes().get(0);
	    assertEquals(addedRectangle.getUpperLeftPoint().getX(), 506);
	    assertEquals(addedRectangle.getUpperLeftPoint().getY(), 329);
	    assertEquals(addedRectangle.getWidth(), 120);
	    assertEquals(addedRectangle.getHeight(), 55);
	}
	
	@Test
	@Order(6)
	public void testReadAddHexagon() {
        String line = "Add: Hexagon: [center= 102, 360, radius= 55, color= -1, border color= -16777216]";
		controller.readLogLine(line);
		  
	    assertEquals(1, model.getShapes().size());
	    assertTrue(model.getShapes().get(0) instanceof HexagonAdapter);
	    HexagonAdapter addedHexagon = (HexagonAdapter)model.getShapes().get(0);
	    assertEquals(addedHexagon.getHexagonCenter().getX(), 102);
	    assertEquals(addedHexagon.getHexagonCenter().getY(), 360);
	    assertEquals(addedHexagon.getHexagonRadius(), 55);
	}
	
	@Test
	@Order(7)
	public void testSelect() {
        String addPoint = "Add: Point: [x= 50, y= 50, color= -16777216]";
		controller.readLogLine(addPoint);
        String line = "Select: Point: [x= 50, y= 50, color= -16777216]";
		controller.readLogLine(line);
		  
		Point selectedPoint = (Point)model.getShapes().get(0);
	    assertTrue(selectedPoint.isSelected());
	}
	
	@Test
	@Order(8)
	public void testUnselect() {
        String addPointLine = "Add: Point: [x= 50, y= 50, color= -16777216]";
		controller.readLogLine(addPointLine);
        String selectedPointLine = "Select: Point: [x= 50, y= 50, color= -16777216]";
		controller.readLogLine(selectedPointLine);	    
        String line = "Unselect: Point: [x= 50, y= 50, color= -16777216]";
		controller.readLogLine(line);
		  
		Point selectedPoint = (Point)model.getShapes().get(0);
	    assertFalse(selectedPoint.isSelected());
	}
	
	@Test
	@Order(9)
	public void testModifyPoint() {
		String addPoint = "Add: Point: [x= 288, y= 73, color= -16777216]";
		controller.readLogLine(addPoint);
        String line = "Modify: Point: [x= 288, y= 73, color= -16777216] to Point: [x= 100, y= 100, color= -16777216]";
		controller.readLogLine(line);
		  
	    assertEquals(1, model.getShapes().size());
	    assertTrue(model.getShapes().get(0) instanceof Point);
	    Point modifiedPoint = (Point)model.getShapes().get(0);
	    assertEquals(modifiedPoint.getX(), 100);
	    assertEquals(modifiedPoint.getY(), 100);
	}
	
	@Test
	@Order(10)
	public void testModifyLine() {
        String addLine = "Add: Line: [startPoint= 536, 58, endPoint= 258, 210, color= -16777216]";
		controller.readLogLine(addLine);
        String line = "Modify: Line: [startPoint= 536, 58, endPoint= 258, 210, color= -16777216] to Line: [startPoint= 100, 100, endPoint= 200, 200, color= -16777216]";
		controller.readLogLine(line);
		  
	    assertEquals(1, model.getShapes().size());
	    assertTrue(model.getShapes().get(0) instanceof Line);
	    Line modifiedLine = (Line)model.getShapes().get(0);
	    assertEquals(modifiedLine.getStartPoint().getX(), 100);
	    assertEquals(modifiedLine.getStartPoint().getY(), 100);
	    assertEquals(modifiedLine.getEndPoint().getX(), 200);
	    assertEquals(modifiedLine.getEndPoint().getY(), 200);
	}
	
	@Test
	@Order(11)
	public void testModifyCircle() {
        String addCircle = "Add: Circle: [center= 651, 190, radius= 55, color= -1, border color= -16777216]";
		controller.readLogLine(addCircle);
        String line = "Modify: Circle: [center= 651, 190, radius= 55, color= -1, border color= -16777216] to Circle: [center= 100, 100, radius= 100, color= -1, border color= -16777216]";
		controller.readLogLine(line);
		  
	    assertEquals(1, model.getShapes().size());
	    assertTrue(model.getShapes().get(0) instanceof Circle);
	    Circle addedCircle = (Circle)model.getShapes().get(0);
	    assertEquals(addedCircle.getCenter().getX(), 100);
	    assertEquals(addedCircle.getCenter().getY(), 100);
	    assertEquals(addedCircle.getRadius(), 100);
	}
	
	@Test
	@Order(12)
	public void testModifyRectangle() {
        String addRectangle = "Add: Rectangle: [upperLeftPoint= 506, 329, height=55, width=120, border color= -16777216, color= -1]";
		controller.readLogLine(addRectangle);
        String line = "Modify: Rectangle: [upperLeftPoint= 506, 329, height=55, width=120, border color= -16777216, color= -1] to Rectangle: [upperLeftPoint= 100, 100, height=100, width=100, border color= -16777216, color= -1]";
		controller.readLogLine(line);
		  
	    assertEquals(1, model.getShapes().size());
	    assertTrue(model.getShapes().get(0) instanceof Rectangle);
	    Rectangle addedRectangle = (Rectangle)model.getShapes().get(0);
	    assertEquals(addedRectangle.getUpperLeftPoint().getX(), 100);
	    assertEquals(addedRectangle.getUpperLeftPoint().getY(), 100);
	    assertEquals(addedRectangle.getWidth(), 100);
	    assertEquals(addedRectangle.getHeight(), 100);
	}
	
	@Test
	@Order(13)
	public void testModifyDonut() {        
		String addDonut = "Add: Donut: [center= 357, 265, radius= 55, innerRadius= 44, color= -1, border color= -16777216]";
		controller.readLogLine(addDonut);
        String line = "Modify: Donut: [center= 357, 265, radius= 55, innerRadius= 44, color= -1, border color= -16777216] to Donut: [center= 100, 100, radius= 100, innerRadius= 50, color= -1, border color= -16777216]";
		controller.readLogLine(line);
		  
	    assertEquals(1, model.getShapes().size());
	    assertTrue(model.getShapes().get(0) instanceof Donut);
	    Donut addedDonut = (Donut)model.getShapes().get(0);
	    assertEquals(addedDonut.getCenter().getX(), 100);
	    assertEquals(addedDonut.getCenter().getY(), 100);
	    assertEquals(addedDonut.getRadius(), 100);
	    assertEquals(addedDonut.getInnerRadius(), 50);
	}	
	
	@Test
	@Order(14)
	public void testModifyHexagon() {
        String addHexagon = "Add: Hexagon: [center= 102, 360, radius= 55, color= -1, border color= -16777216]";
		controller.readLogLine(addHexagon);
        String line = "Modify: Hexagon: [center= 102, 360, radius= 55, color= -1, border color= -16777216] to Hexagon: [center= 100, 100, radius= 100, color= -1, border color= -16777216]";
		controller.readLogLine(line);
		  
	    assertEquals(1, model.getShapes().size());
	    assertTrue(model.getShapes().get(0) instanceof HexagonAdapter);
	    HexagonAdapter addedHexagon = (HexagonAdapter)model.getShapes().get(0);
	    assertEquals(addedHexagon.getHexagonCenter().getX(), 100);
	    assertEquals(addedHexagon.getHexagonCenter().getY(), 100);
	    assertEquals(addedHexagon.getHexagonRadius(), 100);
	}
	
	@Test
	@Order(15)
	public void testReadRemovePoint() {
        String addPointLine = "Add: Point: [x= 50, y= 50, color= -16777216]";
		controller.readLogLine(addPointLine);
        String line = "Delete: Point: [x= 50, y= 50, color= -16777216], 0";
		controller.readLogLine(line);
		  
	    assertEquals(0, model.getShapes().size());
	}
	
	@Test
	@Order(16)
	public void testReadRemoveLine() {
        String addLineLine = "Add: Line: [startPoint= 536, 58, endPoint= 258, 210, color= -16777216]";
		controller.readLogLine(addLineLine);
        String line = "Delete: Line: [startPoint= 536, 58, endPoint= 258, 210, color= -16777216], 0";
		controller.readLogLine(line);

	    assertEquals(0, model.getShapes().size());
	}
	
	@Test
	@Order(17)
	public void testReadRemoveCircle() {
        String addCircleLine = "Add: Circle: [center= 651, 190, radius= 55, color= -1, border color= -16777216]";
		controller.readLogLine(addCircleLine);
        String line = "Delete: Circle: [center= 651, 190, radius= 55, color= -1, border color= -16777216], 0";
		controller.readLogLine(line);
		  
	    assertEquals(0, model.getShapes().size());
	}
	
	@Test
	@Order(18)
	public void testReadRemoveDonut() {
        String addDonutLine = "Add: Donut: [center= 357, 265, radius= 55, innerRadius= 44, color= -1, border color= -16777216]";
		controller.readLogLine(addDonutLine);       
		String line = "Delete: Donut: [center= 357, 265, radius= 55, innerRadius= 44, color= -1, border color= -16777216], 0";
		controller.readLogLine(line);
		
	    assertEquals(0, model.getShapes().size());
	}
	
	@Test
	@Order(19)
	public void testReadRemoveRectangle() {
        String addRectangleLine = "Add: Rectangle: [upperLeftPoint= 506, 329, height=55, width=120, color= -1, border color= -16777216]";
		controller.readLogLine(addRectangleLine);
        String line = "Delete: Rectangle: [upperLeftPoint= 506, 329, height=55, width=120, color= -1, border color= -16777216], 0";
		controller.readLogLine(line);
		
	    assertEquals(0, model.getShapes().size());
	}
	
	@Test
	@Order(20)
	public void testReadRemoveHexagon() {
        String addHexagonLine = "Add: Hexagon: [center= 102, 360, radius= 55, color= -1, border color= -16777216]";
		controller.readLogLine(addHexagonLine);
        String line = "Delete: Hexagon: [center= 102, 360, radius= 55, color= -1, border color= -16777216], 0";
		controller.readLogLine(line);

	    assertEquals(0, model.getShapes().size());
	}
	
	
	@Test
	@Order(21)
	public void testReadUndo() {
        String line = "Add: Point: [x= 288, y= 73, color= -16777216]";
        controller.readLogLine(line);
        String undoLine = "Undo: [ Add: Point: [x= 288, y= 73, color= -16777216] ]";
        controller.readLogLine(undoLine);
		  
	    assertEquals(0, model.getShapes().size());
	    assertEquals(0, controller.undoStack.size());
	    assertEquals(1, controller.redoStack.size());
	    assertTrue(controller.redoStack.get(0) instanceof AddShapeCmd);
	}
	
	
	@Test
	@Order(22)
	public void testReadRedo() {
        String line = "Add: Point: [x= 288, y= 73, color= -16777216]";
        controller.readLogLine(line);
        String undoLine = "Undo: [ Add: Point: [x= 288, y= 73, color= -16777216] ]";
        controller.readLogLine(undoLine);
        String redoLine = "Redo: [ Add: Point: [x= 1, y= 73, color= -16777216] ]";
        controller.readLogLine(redoLine);
		  
	    assertEquals(1, model.getShapes().size());
	    assertEquals(1, controller.undoStack.size());
	    assertEquals(0, controller.redoStack.size());
	    assertTrue(controller.undoStack.get(0) instanceof AddShapeCmd);
	}
	
	@Test
	@Order(23)
	public void testMoveToBackPoint() {
        String addedPoint1 = "Add: Point: [x= 10, y= 10, color= -16777216]";
        controller.readLogLine(addedPoint1);
        String addedPoint2 = "Add: Point: [x= 20, y= 20, color= -16777216]";
        controller.readLogLine(addedPoint2);
        
        String line = "Move to back: Point: [x= 20, y= 20, color= -16777216], 0";
        controller.readLogLine(line);
        
        Point checkPoint = (Point)model.getOneShape(0);
		  
	    assertEquals(model.getShapes().size(), 2);
	    assertEquals(checkPoint.getX(), 20);
	    assertEquals(checkPoint.getY(), 20);
	}
	
	
	@Test
	@Order(24)
	public void testMoveToFrontPoint() {
        String addedPoint1 = "Add: Point: [x= 10, y= 10, color= -16777216]";
        controller.readLogLine(addedPoint1);
        String addedPoint2 = "Add: Point: [x= 20, y= 20, color= -16777216]";
        controller.readLogLine(addedPoint2);
        
        String line = "Move to front: Point: [x= 10, y= 10, color= -16777216], 1";
        controller.readLogLine(line);
        
        Point checkPoint = (Point)model.getOneShape(1);
		  
	    assertEquals(model.getShapes().size(), 2);
	    assertEquals(checkPoint.getX(), 10);
	    assertEquals(checkPoint.getY(), 10);
	}
	
	@Test
	@Order(25)
	public void testMoveToBackLine() {
        String addedLine1 = "Add: Line: [startPoint= 10, 10, endPoint= 10, 10, color= -16777216]";
        controller.readLogLine(addedLine1);
        String addedLine2 = "Add: Line: [startPoint= 20, 20, endPoint= 20, 20, color= -16777216]";
        controller.readLogLine(addedLine2);
        
        String line = "Move to back: Line: [startPoint= 20, 20, endPoint= 20, 20, color= -16777216], 0";
        controller.readLogLine(line);
        
        Line checkLine = (Line)model.getOneShape(0);
		  
	    assertEquals(model.getShapes().size(), 2);
	    assertEquals(checkLine.getStartPoint().getX(), 20);
	    assertEquals(checkLine.getStartPoint().getY(), 20);
	    assertEquals(checkLine.getEndPoint().getX(), 20);
	    assertEquals(checkLine.getEndPoint().getY(), 20);
	}
	
	
	@Test
	@Order(26)
	public void testMoveToFrontLine() {
        String addedLine1 = "Add: Line: [startPoint= 10, 10, endPoint= 10, 10, color= -16777216]";
        controller.readLogLine(addedLine1);
        String addedLine2 = "Add: Line: [startPoint= 20, 20, endPoint= 20, 20, color= -16777216]";
        controller.readLogLine(addedLine2);
        
        String line = "Move to front: Line: [startPoint= 10, 10, endPoint= 10, 10, color= -16777216], 1";
        controller.readLogLine(line);
        
        Line checkLine = (Line)model.getOneShape(1);
		  
	    assertEquals(model.getShapes().size(), 2);
	    assertEquals(checkLine.getStartPoint().getX(), 10);
	    assertEquals(checkLine.getStartPoint().getY(), 10);
	    assertEquals(checkLine.getEndPoint().getX(), 10);
	    assertEquals(checkLine.getEndPoint().getY(), 10);
	}
	
	@Test
	@Order(27)
	public void testMoveToBackCircle() {
        String addedCircle1 = "Add: Circle: [center= 10, 10, radius= 10, color= -1, border color= -16777216]";
        controller.readLogLine(addedCircle1);
        String addedCircle2 = "Add: Circle: [center= 20, 20, radius= 20, color= -1, border color= -16777216]";
        controller.readLogLine(addedCircle2);
        
        String line = "Move to back: Circle: [center= 20, 20, radius= 20, color= -1, border color= -16777216], 0";
        controller.readLogLine(line);
        
        Circle checkCircle = (Circle)model.getOneShape(0);
		  
	    assertEquals(model.getShapes().size(), 2);
	    assertEquals(checkCircle.getCenter().getX(), 20);
	    assertEquals(checkCircle.getCenter().getY(), 20);
	    assertEquals(checkCircle.getRadius(), 20);
	}
	
	
	@Test
	@Order(28)
	public void testMoveToFrontCircle() {
        String addedCircle1 = "Add: Circle: [center= 10, 10, radius= 10, color= -1, border color= -16777216]";
        controller.readLogLine(addedCircle1);
        String addedCircle2 = "Add: Circle: [center= 20, 20, radius= 20, color= -1, border color= -16777216]";
        controller.readLogLine(addedCircle2);
        
        String line = "Move to front: Circle: [center= 10, 10, radius= 10, color= -1, border color= -16777216], 1";
        controller.readLogLine(line);
        
        Circle checkCircle = (Circle)model.getOneShape(1);
		  
	    assertEquals(model.getShapes().size(), 2);
	    assertEquals(checkCircle.getCenter().getX(), 10);
	    assertEquals(checkCircle.getCenter().getY(), 10);
	    assertEquals(checkCircle.getRadius(), 10);
	}
	
	@Test
	@Order(29)
	public void testMoveToBackDonut() {
        String addedCircle1 = "Add: Donut: [center= 10, 10, radius= 10, innerRadius= 10, color= -1, border color= -16777216]";
        controller.readLogLine(addedCircle1);
        String addedCircle2 = "Add: Donut: [center= 20, 20, radius= 20, innerRadius= 20, color= -1, border color= -16777216]";
        controller.readLogLine(addedCircle2);
        
        String line = "Move to back: Donut: [center= 20, 20, radius= 20, innerRadius= 20, color= -1, border color= -16777216], 0";
        controller.readLogLine(line);
        
        Donut checkDonut = (Donut)model.getOneShape(0);
		  
	    assertEquals(model.getShapes().size(), 2);
	    assertEquals(checkDonut.getCenter().getX(), 20);
	    assertEquals(checkDonut.getCenter().getY(), 20);
	    assertEquals(checkDonut.getRadius(), 20);
	    assertEquals(checkDonut.getInnerRadius(), 20);
	}
	
	@Test
	@Order(30)
	public void testMoveToFrontDonut() {
        String addedDonut1 = "Add: Donut: [center= 10, 10, radius= 10, innerRadius= 10, color= -1, border color= -16777216]";
        controller.readLogLine(addedDonut1);
        String addedDonut2 = "Add: Donut: [center= 20, 20, radius= 20, innerRadius= 20, color= -1, border color= -16777216]";
        controller.readLogLine(addedDonut2);
        
        String line = "Move to front: Donut: [center= 10, 10, radius= 10, innerRadius= 10, color= -1, border color= -16777216], 1";
        controller.readLogLine(line);
        
        Donut checkDonut = (Donut)model.getOneShape(1);
		  
	    assertEquals(model.getShapes().size(), 2);
	    assertEquals(checkDonut.getCenter().getX(), 10);
	    assertEquals(checkDonut.getCenter().getY(), 10);
	    assertEquals(checkDonut.getRadius(), 10);
	    assertEquals(checkDonut.getInnerRadius(), 10);
	}
	
	@Test
	@Order(31)
	public void testMoveToBackRectangle() {
        String addedRectangle1 = "Add: Rectangle: [upperLeftPoint= 10, 10, height=10, width=10, color= -1, border color= -16777216]";
        controller.readLogLine(addedRectangle1);
        String addedRectangle2 = "Add: Rectangle: [upperLeftPoint= 20, 20, height=20, width=20, color= -1, border color= -16777216]";
        controller.readLogLine(addedRectangle2);
        
        String line = "Move to back: Rectangle: [upperLeftPoint= 20, 20, height=20, width=20, color= -1, border color= -16777216], 0";
        controller.readLogLine(line);
        
        Rectangle checkRectangle = (Rectangle)model.getOneShape(0);
		  
	    assertEquals(model.getShapes().size(), 2);
	    assertEquals(checkRectangle.getUpperLeftPoint().getX(), 20);
	    assertEquals(checkRectangle.getUpperLeftPoint().getY(), 20);
	    assertEquals(checkRectangle.getWidth(), 20);
	    assertEquals(checkRectangle.getHeight(), 20);
	}
	
	@Test
	@Order(32)
	public void testMoveToFrontRectangle() {
        String addedRectangle1 = "Add: Rectangle: [upperLeftPoint= 10, 10, height=10, width=10, color= -1, border color= -16777216]";
        controller.readLogLine(addedRectangle1);
        String addedRectangle2 = "Add: Rectangle: [upperLeftPoint= 20, 20, height=20, width=20, color= -1, border color= -16777216]";
        controller.readLogLine(addedRectangle2);
        
        String line = "Move to front: Rectangle: [upperLeftPoint= 10, 10, height=10, width=10, color= -1, border color= -16777216], 1";
        controller.readLogLine(line);
        
        Rectangle checkRectangle = (Rectangle)model.getOneShape(0);
		  
	    assertEquals(model.getShapes().size(), 2);
	    assertEquals(checkRectangle.getUpperLeftPoint().getX(), 20);
	    assertEquals(checkRectangle.getUpperLeftPoint().getY(), 20);
	    assertEquals(checkRectangle.getWidth(), 20);
	    assertEquals(checkRectangle.getHeight(), 20);
	}
	
	@Test
	@Order(33)
	public void testMoveToBackHexagon() {
        String addedRectangle1 = "Add: Hexagon: [center= 10, 10, radius= 10, color= -1, border color= -16777216]";
        controller.readLogLine(addedRectangle1);
        String addedRectangle2 = "Add: Hexagon: [center= 20, 20, radius= 20, color= -1, border color= -16777216]";
        controller.readLogLine(addedRectangle2);
        
        String line = "Move to back: Hexagon: [center= 20, 20, radius= 20, color= -1, border color= -16777216], 0";
        controller.readLogLine(line);
        
        HexagonAdapter checkHexagon = (HexagonAdapter)model.getOneShape(0);
		  
	    assertEquals(model.getShapes().size(), 2);
	    assertEquals(checkHexagon.getHexagonCenter().getX(), 20);
	    assertEquals(checkHexagon.getHexagonCenter().getY(), 20);
	    assertEquals(checkHexagon.getHexagonRadius(), 20);
	}
	
	@Test
	@Order(34)
	public void testMoveToFrontHexagon() {
        String addedHexagon1 = "Add: Hexagon: [center= 10, 10, radius= 10, color= -1, border color= -16777216]";
        controller.readLogLine(addedHexagon1);
        String addedHexagon2 = "Add: Hexagon: [center= 20, 20, radius= 20, color= -1, border color= -16777216]";
        controller.readLogLine(addedHexagon2);
        
        String line = "Move to front: Hexagon: [center= 10, 10, radius= 10, color= -1, border color= -16777216], 0";
        controller.readLogLine(line);
        
        HexagonAdapter checkHexagon = (HexagonAdapter)model.getOneShape(1);
		  
	    assertEquals(model.getShapes().size(), 2);
	    assertEquals(checkHexagon.getHexagonCenter().getX(), 10);
	    assertEquals(checkHexagon.getHexagonCenter().getY(), 10);
	    assertEquals(checkHexagon.getHexagonRadius(), 10);
	}
	
	@Test
	@Order(35)
	public void testBringToBackPoint() {
        String addedPoint1 = "Add: Point: [x= 10, y= 10, color= -16777216]";
        controller.readLogLine(addedPoint1);
        String addedPoint2 = "Add: Point: [x= 20, y= 20, color= -16777216]";
        controller.readLogLine(addedPoint2);
        String addedPoint3 = "Add: Point: [x= 30, y= 30, color= -16777216]";
        controller.readLogLine(addedPoint3);
        
        String line = "Bring to back: Point: [x= 30, y= 30, color= -16777216], 2";
        controller.readLogLine(line);
        
        Point checkPoint = (Point)model.getOneShape(0);
		  
	    assertEquals(model.getShapes().size(), 3);
	    assertEquals(checkPoint.getX(), 30);
	    assertEquals(checkPoint.getY(), 30);
	}
	
	@Test
	@Order(36)
	public void testBringToFrontPoint() {
        String addedPoint1 = "Add: Point: [x= 10, y= 10, color= -16777216]";
        controller.readLogLine(addedPoint1);
        String addedPoint2 = "Add: Point: [x= 20, y= 20, color= -16777216]";
        controller.readLogLine(addedPoint2);
        String addedPoint3 = "Add: Point: [x= 30, y= 30, color= -16777216]";
        controller.readLogLine(addedPoint3);
        
        String line = "Bring to front: Point: [x= 10, y= 10, color= -16777216], 0";
        controller.readLogLine(line);
        
        Point checkPoint = (Point)model.getOneShape(2);
		  
	    assertEquals(model.getShapes().size(), 3);
	    assertEquals(checkPoint.getX(), 10);
	    assertEquals(checkPoint.getY(), 10);
	}
	
	@Test
	@Order(37)
	public void testBringToBackLine() {
        String addedLine1 = "Add: Line: [startPoint= 10, 10, endPoint= 10, 10, color= -16777216]";
        controller.readLogLine(addedLine1);
        String addedLine2 = "Add: Line: [startPoint= 20, 20, endPoint= 20, 20, color= -16777216]";
        controller.readLogLine(addedLine2);
        String addedLine3 = "Add: Line: [startPoint= 30, 30, endPoint= 30, 30, color= -16777216]";
        controller.readLogLine(addedLine3);
        
        String line = "Bring to back: Line: [startPoint= 30, 30, endPoint= 30, 30, color= -16777216], 2";
        controller.readLogLine(line);
        
        Line checkLine = (Line)model.getOneShape(0);
		  
	    assertEquals(model.getShapes().size(), 3);
	    assertEquals(checkLine.getStartPoint().getX(), 30);
	    assertEquals(checkLine.getStartPoint().getY(), 30);
	    assertEquals(checkLine.getEndPoint().getX(), 30);
	    assertEquals(checkLine.getEndPoint().getY(), 30);
	}
	
	
	@Test
	@Order(38)
	public void testBringToFrontLine() {
        String addedLine1 = "Add: Line: [startPoint= 10, 10, endPoint= 10, 10, color= -16777216]";
        controller.readLogLine(addedLine1);
        String addedLine2 = "Add: Line: [startPoint= 20, 20, endPoint= 20, 20, color= -16777216]";
        controller.readLogLine(addedLine2);
        String addedLine3 = "Add: Line: [startPoint= 30, 30, endPoint= 30, 30, color= -16777216]";
        controller.readLogLine(addedLine3);
        
        String line = "Bring to front: Line: [startPoint= 10, 10, endPoint= 10, 10, color= -16777216], 0";
        controller.readLogLine(line);
        
        Line checkLine = (Line)model.getOneShape(2);
		  
	    assertEquals(model.getShapes().size(), 3);
	    assertEquals(checkLine.getStartPoint().getX(), 10);
	    assertEquals(checkLine.getStartPoint().getY(), 10);
	    assertEquals(checkLine.getEndPoint().getX(), 10);
	    assertEquals(checkLine.getEndPoint().getY(), 10);
	}
	
	@Test
	@Order(39)
	public void testBringToBackCircle() {
        String addedCircle1 = "Add: Circle: [center= 10, 10, radius= 10, color= -1, border color= -16777216]";
        controller.readLogLine(addedCircle1);
        String addedCircle2 = "Add: Circle: [center= 20, 20, radius= 20, color= -1, border color= -16777216]";
        controller.readLogLine(addedCircle2);
        String addedCircle3 = "Add: Circle: [center= 30, 30, radius= 30, color= -1, border color= -16777216]";
        controller.readLogLine(addedCircle3);
        
        String line = "Bring to back: Circle: [center= 30, 30, radius= 30, color= -1, border color= -16777216], 2";
        controller.readLogLine(line);
        
        Circle checkCircle = (Circle)model.getOneShape(0);
		  
	    assertEquals(model.getShapes().size(), 3);
	    assertEquals(checkCircle.getCenter().getX(), 30);
	    assertEquals(checkCircle.getCenter().getY(), 30);
	    assertEquals(checkCircle.getRadius(), 30);
	}
	
	
	@Test
	@Order(40)
	public void testBringToFrontCircle() {
        String addedCircle1 = "Add: Circle: [center= 10, 10, radius= 10, color= -1, border color= -16777216]";
        controller.readLogLine(addedCircle1);
        String addedCircle2 = "Add: Circle: [center= 20, 20, radius= 20, color= -1, border color= -16777216]";
        controller.readLogLine(addedCircle2);
        String addedCircle3 = "Add: Circle: [center= 30, 30, radius= 30, color= -1, border color= -16777216]";
        controller.readLogLine(addedCircle3);
        
        String line = "Bring to front: Circle: [center= 10, 10, radius= 10, color= -1, border color= -16777216], 0";
        controller.readLogLine(line);
        
        Circle checkCircle = (Circle)model.getOneShape(2);
		  
	    assertEquals(model.getShapes().size(), 3);
	    assertEquals(checkCircle.getCenter().getX(), 10);
	    assertEquals(checkCircle.getCenter().getY(), 10);
	    assertEquals(checkCircle.getRadius(), 10);
	}
	
	@Test
	@Order(41)
	public void testBringToBackDonut() {
        String addedCircle1 = "Add: Donut: [center= 10, 10, radius= 10, innerRadius= 10, color= -1, border color= -16777216]";
        controller.readLogLine(addedCircle1);
        String addedCircle2 = "Add: Donut: [center= 20, 20, radius= 20, innerRadius= 20, color= -1, border color= -16777216]";
        controller.readLogLine(addedCircle2);
        String addedCircle3 = "Add: Donut: [center= 30, 30, radius= 30, innerRadius= 30, color= -1, border color= -16777216]";
        controller.readLogLine(addedCircle3);
        
        String line = "Bring to back: Donut: [center= 30, 30, radius= 30, innerRadius= 30, color= -1, border color= -16777216], 2";
        controller.readLogLine(line);
        
        Donut checkDonut = (Donut)model.getOneShape(0);
		  
	    assertEquals(model.getShapes().size(), 3);
	    assertEquals(checkDonut.getCenter().getX(), 30);
	    assertEquals(checkDonut.getCenter().getY(), 30);
	    assertEquals(checkDonut.getRadius(), 30);
	    assertEquals(checkDonut.getInnerRadius(), 30);
	}
	
	@Test
	@Order(42)
	public void testBringToFrontDonut() {
        String addedCircle1 = "Add: Donut: [center= 10, 10, radius= 10, innerRadius= 10, color= -1, border color= -16777216]";
        controller.readLogLine(addedCircle1);
        String addedCircle2 = "Add: Donut: [center= 20, 20, radius= 20, innerRadius= 20, color= -1, border color= -16777216]";
        controller.readLogLine(addedCircle2);
        String addedCircle3 = "Add: Donut: [center= 30, 30, radius= 30, innerRadius= 30, color= -1, border color= -16777216]";
        controller.readLogLine(addedCircle3);
        
        String line = "Bring to front: Donut: [center= 10, 10, radius= 10, innerRadius= 10, color= -1, border color= -16777216], 0";
        controller.readLogLine(line);
        
        Donut checkDonut = (Donut)model.getOneShape(2);
		  
	    assertEquals(model.getShapes().size(), 3);
	    assertEquals(checkDonut.getCenter().getX(), 10);
	    assertEquals(checkDonut.getCenter().getY(), 10);
	    assertEquals(checkDonut.getRadius(), 10);
	    assertEquals(checkDonut.getInnerRadius(), 10);
	}
	
	@Test
	@Order(43)
	public void testBringToBackRectangle() {
        String addedRectangle1 = "Add: Rectangle: [upperLeftPoint= 10, 10, height=10, width=10, color= -1, border color= -16777216]";
        controller.readLogLine(addedRectangle1);
        String addedRectangle2 = "Add: Rectangle: [upperLeftPoint= 20, 20, height=20, width=20, color= -1, border color= -16777216]";
        controller.readLogLine(addedRectangle2);
        String addedRectangle3 = "Add: Rectangle: [upperLeftPoint= 30, 30, height=30, width=30, color= -1, border color= -16777216]";
        controller.readLogLine(addedRectangle3);
        
        String line = "Bring to back: Rectangle: [upperLeftPoint= 30, 30, height=30, width=30, color= -1, border color= -16777216], 2";
        controller.readLogLine(line);
        
        Rectangle checkRectangle = (Rectangle)model.getOneShape(0);
		  
	    assertEquals(model.getShapes().size(), 3);
	    assertEquals(checkRectangle.getUpperLeftPoint().getX(), 30);
	    assertEquals(checkRectangle.getUpperLeftPoint().getY(), 30);
	    assertEquals(checkRectangle.getWidth(), 30);
	    assertEquals(checkRectangle.getHeight(), 30);
	}
	
	@Test
	@Order(44)
	public void testBringToFrontRectangle() {
        String addedRectangle1 = "Add: Rectangle: [upperLeftPoint= 10, 10, height=10, width=10, color= -1, border color= -16777216]";
        controller.readLogLine(addedRectangle1);
        String addedRectangle2 = "Add: Rectangle: [upperLeftPoint= 20, 20, height=20, width=20, color= -1, border color= -16777216]";
        controller.readLogLine(addedRectangle2);
        String addedRectangle3 = "Add: Rectangle: [upperLeftPoint= 30, 30, height=30, width=30, color= -1, border color= -16777216]";
        controller.readLogLine(addedRectangle3);
        
        String line = "Bring to front: Rectangle: [upperLeftPoint= 10, 10, height=10, width=10, color= -1, border color= -16777216], 0";
        controller.readLogLine(line);
        
        Rectangle checkRectangle = (Rectangle)model.getOneShape(2);
		  
	    assertEquals(model.getShapes().size(), 3);
	    assertEquals(checkRectangle.getUpperLeftPoint().getX(), 10);
	    assertEquals(checkRectangle.getUpperLeftPoint().getY(), 10);
	    assertEquals(checkRectangle.getWidth(), 10);
	    assertEquals(checkRectangle.getHeight(), 10);
	}
	
	@Test
	@Order(45)
	public void testBringToBackHexagon() {
        String addedHexagon1 = "Add: Hexagon: [center= 10, 10, radius= 10, color= -1, border color= -16777216]";
        controller.readLogLine(addedHexagon1);
        String addedHexagon2 = "Add: Hexagon: [center= 20, 20, radius= 20, color= -1, border color= -16777216]";
        controller.readLogLine(addedHexagon2);
        String addedHexagon3 = "Add: Hexagon: [center= 30, 30, radius= 30, color= -1, border color= -16777216]";
        controller.readLogLine(addedHexagon3);
        
        String line = "Bring to back: Hexagon: [center= 30, 30, radius= 30, color= -1, border color= -16777216], 2";
        controller.readLogLine(line);
        
        HexagonAdapter checkRectangle = (HexagonAdapter)model.getOneShape(0);
		  
	    assertEquals(model.getShapes().size(), 3);
	    assertEquals(checkRectangle.getHexagonCenter().getX(), 30);
	    assertEquals(checkRectangle.getHexagonCenter().getY(), 30);
	    assertEquals(checkRectangle.getHexagonRadius(), 30);
	}
	
	@Test
	@Order(46)
	public void testBringToFrontHexagon() {
        String addedHexagon1 = "Add: Hexagon: [center= 10, 10, radius= 10, color= -1, border color= -16777216]";
        controller.readLogLine(addedHexagon1);
        String addedHexagon2 = "Add: Hexagon: [center= 20, 20, radius= 20, color= -1, border color= -16777216]";
        controller.readLogLine(addedHexagon2);
        String addedHexagon3 = "Add: Hexagon: [center= 30, 30, radius= 30, color= -1, border color= -16777216]";
        controller.readLogLine(addedHexagon3);
        
        String line = "Bring to front: Hexagon: [center= 10, 10, radius= 10, color= -1, border color= -16777216], 0";
        controller.readLogLine(line);
        
        HexagonAdapter checkRectangle = (HexagonAdapter)model.getOneShape(2);
		  
	    assertEquals(model.getShapes().size(), 3);
	    assertEquals(checkRectangle.getHexagonCenter().getX(), 10);
	    assertEquals(checkRectangle.getHexagonCenter().getY(), 10);
	    assertEquals(checkRectangle.getHexagonRadius(), 10);
	}
}
