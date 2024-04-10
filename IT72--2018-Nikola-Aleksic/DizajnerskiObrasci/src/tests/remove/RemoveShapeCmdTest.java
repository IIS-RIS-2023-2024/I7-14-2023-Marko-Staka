package tests.remove;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import adapter.HexagonAdapter;
import command.AddShapeCmd;
import command.RemoveShapeCmd;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import mvc.DrawingModel;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RemoveShapeCmdTest {
	private static DrawingModel drawingModel;
	
	private static RemoveShapeCmd removePointCmd;
	private static RemoveShapeCmd removeLineCmd;
	private static RemoveShapeCmd removeCircleCmd;
	private static RemoveShapeCmd removeDonutCmd;
	private static RemoveShapeCmd removeRectangleCmd;
	private static RemoveShapeCmd removeHexagonCmd;
	
	private static Shape point, line, circle, donut, rectangle, hexagon;

	@BeforeEach
	public void setUpBeforeClass() throws Exception {
		drawingModel = new DrawingModel();

		point = new Point(10,10);
		line = new Line(new Point(10,10), new Point(20,20));
		circle = new Circle(new Point(10,10), 10);
		rectangle = new Rectangle(new Point(10,10), 10, 10);
		donut = new Donut(new Point(10,10), 100, 10);
		hexagon = new HexagonAdapter(new Point(10,10), 10);
		
		removePointCmd = new RemoveShapeCmd(drawingModel, point, 0);
		removeLineCmd = new RemoveShapeCmd(drawingModel, line, 0);
		removeCircleCmd = new RemoveShapeCmd(drawingModel, circle, 0);
		removeRectangleCmd = new RemoveShapeCmd(drawingModel, rectangle, 0);
		removeDonutCmd = new RemoveShapeCmd(drawingModel, donut, 0);
		removeHexagonCmd = new RemoveShapeCmd(drawingModel, hexagon, 0);
	}
	
	@Test
	@Order(1)
	public final void testRemovePointExecute() {
		removePointCmd.execute();

		assertTrue(drawingModel.getShapes().size() == 0);
	}

	@Test
	@Order(2)
	public final void testRemovePointUnexecute() {
		removePointCmd.unexecute();

		Shape shapeFromModel = drawingModel.getOneShape(0);
		System.out.println(drawingModel.getShapes());
		assertSame(point, (Point)shapeFromModel);
	}
	
	@Test
	@Order(3)
	public final void testRemoveLineExecute() {
		removeLineCmd.execute();

		assertTrue(drawingModel.getShapes().size() == 0);
	}
	
	@Test
	@Order(4)
	public final void testRemoveLineUnexecute() {
		removeLineCmd.unexecute();
		
		Shape shapeFromModel = drawingModel.getOneShape(0);
		System.out.println(drawingModel.getShapes());
		assertSame(line, (Line)shapeFromModel);
	}
	
	@Test
	@Order(5)
	public final void testRemoveCircleExecute() {
		removeCircleCmd.execute();

		assertTrue(drawingModel.getShapes().size() == 0);
	}
	
	@Test
	@Order(6)
	public final void testRemoveCircleUnxecute() {
		removeCircleCmd.unexecute();
		
		Shape shapeFromModel = drawingModel.getOneShape(0);
		System.out.println(drawingModel.getShapes());
		assertSame(circle, (Circle)shapeFromModel);
	}
	
	@Test
	@Order(7)
	public final void testRemoveDonutExecute() {
		removeDonutCmd.execute();

		assertTrue(drawingModel.getShapes().size() == 0);
	}
	
	@Test
	@Order(8)
	public final void testRemoveDonutUnxecute() {
		removeDonutCmd.unexecute();
		
		Shape shapeFromModel = drawingModel.getOneShape(0);
		System.out.println(drawingModel.getShapes());
		assertSame(donut, (Donut)shapeFromModel);
	}	
	
	@Test
	@Order(9)
	public final void testRemoveRectangleExecute() {
		removeRectangleCmd.execute();

		assertTrue(drawingModel.getShapes().size() == 0);
	}
	
	@Test
	@Order(10)
	public final void testRemoveRectangleUnexecute() {
		removeRectangleCmd.unexecute();
		
		Shape shapeFromModel = drawingModel.getOneShape(0);
		System.out.println(drawingModel.getShapes());
		assertSame(rectangle, (Rectangle)shapeFromModel);
	}
	
	@Test
	@Order(11)
	public final void testRemoveHexagonExecute() {
		removeHexagonCmd.execute();
		
		assertTrue(drawingModel.getShapes().size() == 0);
	}
	
	@Test
	@Order(12)
	public final void testRemoveHexagonUnexecute() {
		removeHexagonCmd.unexecute();
		
		Shape shapeFromModel = drawingModel.getOneShape(0);
		//System.out.println(drawingModel.getShapes().size());
		assertSame(hexagon, (HexagonAdapter)shapeFromModel);
	}
	

}
