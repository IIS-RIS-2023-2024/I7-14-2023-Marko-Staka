package tests.add;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import command.AddShapeCmd;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import mvc.DrawingController;
import mvc.DrawingModel;
import adapter.HexagonAdapter;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AddShapeCmdTest {
	private static DrawingModel drawingModel;
	
	private static AddShapeCmd addPointCmd;
	private static AddShapeCmd addLineCmd;
	private static AddShapeCmd addCircleCmd;
	private static AddShapeCmd addDonutCmd;
	private static AddShapeCmd addRectangleCmd;
	private static AddShapeCmd addHexagonCmd;
	
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
		
		addPointCmd = new AddShapeCmd(drawingModel, point);
		addLineCmd = new AddShapeCmd(drawingModel, line);
		addCircleCmd = new AddShapeCmd(drawingModel, circle);
		addRectangleCmd = new AddShapeCmd(drawingModel, rectangle);
		addDonutCmd = new AddShapeCmd(drawingModel, donut);
		addHexagonCmd = new AddShapeCmd(drawingModel, hexagon);
	}

	@Test
	@Order(1)
	public final void testAddPointExecute() {
		addPointCmd.execute();
		
		Shape shapeFromModel = drawingModel.getOneShape(0);
		System.out.println(drawingModel.getShapes());
		assertSame(point, (Point)shapeFromModel);
	}

	@Test
	@Order(2)
	public final void testAddPointUnexecute() {
		addPointCmd.unexecute();

		assertTrue(drawingModel.getShapes().size() == 0);
	}
	
	@Test
	@Order(3)
	public final void testAddLineExecute() {
		addLineCmd.execute();
		
		Shape shapeFromModel = drawingModel.getOneShape(0);
		System.out.println(drawingModel.getShapes());
		assertSame(line, (Line)shapeFromModel);
	}
	
	@Test
	@Order(4)
	public final void testAddLineUnexecute() {
		addLineCmd.unexecute();

		assertTrue(drawingModel.getShapes().size() == 0);
	}
	
	@Test
	@Order(5)
	public final void testAddCircleExecute() {
		addCircleCmd.execute();
		
		Shape shapeFromModel = drawingModel.getOneShape(0);
		System.out.println(drawingModel.getShapes());
		assertSame(circle, (Circle)shapeFromModel);
	}
	
	@Test
	@Order(6)
	public final void testAddCircleUnexecute() {
		addCircleCmd.unexecute();

		assertTrue(drawingModel.getShapes().size() == 0);
	}
	
	@Test
	@Order(7)
	public final void testAddDonutExecute() {
		addDonutCmd.execute();
		
		Shape shapeFromModel = drawingModel.getOneShape(0);
		System.out.println(drawingModel.getShapes());
		assertSame(donut, (Donut)shapeFromModel);
	}
	
	@Test
	@Order(8)
	public final void testAddDonutUnexecute() {
		addDonutCmd.unexecute();

		assertTrue(drawingModel.getShapes().size() == 0);
	}
	

	@Test
	@Order(9)
	public final void testAddRectangleExecute() {
		addRectangleCmd.execute();
		
		Shape shapeFromModel = drawingModel.getOneShape(0);
		System.out.println(drawingModel.getShapes());
		assertSame(rectangle, (Rectangle)shapeFromModel);
	}
	
	@Test
	@Order(10)
	public final void testAddRectangleUnexecute() {
		addRectangleCmd.unexecute();

		assertTrue(drawingModel.getShapes().size() == 0);
	}
	
	@Test
	@Order(11)
	public final void testAddHexagonExecute() {
		addHexagonCmd.execute();
		
		Shape shapeFromModel = drawingModel.getOneShape(0);
		//System.out.println(drawingModel.getShapes().size());
		assertSame(hexagon, (HexagonAdapter)shapeFromModel);
	}
	
	@Test
	@Order(12)
	public final void testAddHexagonUnexecute() {
		addHexagonCmd.unexecute();

		assertTrue(drawingModel.getShapes().size() == 0);
	}
	
}
