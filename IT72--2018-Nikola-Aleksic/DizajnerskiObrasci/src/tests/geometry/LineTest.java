package tests.geometry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import geometry.Line;
import geometry.Point;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class LineTest {
    private Point startPoint;
    private Point endPoint;
    

	@BeforeEach
	public void setUpBeforeClass() throws Exception {
		startPoint = new Point(10, 10);
		endPoint = new Point(20, 20);
	}
	
	@Test
	@Order(1)
	public final void testLineConstructor1() {
		Line line = new Line(startPoint, endPoint, true);

        assertEquals(line.getStartPoint().getX(), 10);
        assertEquals(line.getStartPoint().getY(), 10);
        assertEquals(line.getEndPoint().getX(), 20);
        assertEquals(line.getEndPoint().getY(), 20);
        assertEquals(line.isSelected(), true);
	}
	
	@Test
	@Order(2)
	public final void testLineConstructor2() {
		Line line = new Line(startPoint, endPoint, true, Color.red);

        assertEquals(line.getStartPoint().getX(), 10);
        assertEquals(line.getStartPoint().getY(), 10);
        assertEquals(line.getEndPoint().getX(), 20);
        assertEquals(line.getEndPoint().getY(), 20);
        assertEquals(line.isSelected(), true);
        assertEquals(line.getColor(), Color.red);
	}
	
	@Test
	@Order(3)
	public final void testLineConstructor3() {
		Line line = new Line(startPoint, endPoint, Color.red);

        assertEquals(line.getStartPoint().getX(), 10);
        assertEquals(line.getStartPoint().getY(), 10);
        assertEquals(line.getEndPoint().getX(), 20);
        assertEquals(line.getEndPoint().getY(), 20);
        assertEquals(line.getColor(), Color.red);
	}
	
	@Test
	@Order(4)
	public final void testMoveBy() {
		Line line = new Line(startPoint, endPoint, Color.red);
		line.moveBy(20, 20);

        assertEquals(line.getStartPoint().getX(), 30);
        assertEquals(line.getStartPoint().getY(), 30);
        assertEquals(line.getEndPoint().getX(), 40);
        assertEquals(line.getEndPoint().getY(), 40);
	}

	@Test
	@Order(5)
	public final void testLength() {
		Line line = new Line(new Point(10, 0), new Point(20, 0), Color.red);
		
		assertEquals(line.length(), 10, 0.1);
	}
	
	@Test
	@Order(6)
	public final void testContaints() {
		Line line = new Line(new Point(10, 0), new Point(20, 0), Color.red);
		
		assertTrue(line.contains(15, 0));
		assertFalse(line.contains(115, 0));
	}
	
	@Test
	@Order(7)
	public final void testSet() {
		Line line = new Line(new Point(10, 0), new Point(20, 0), Color.red);
		
		line.setStartPoint(startPoint);
		line.setEndPoint(endPoint);
        assertEquals(line.getStartPoint().getX(), 10);
        assertEquals(line.getStartPoint().getY(), 10);
        assertEquals(line.getEndPoint().getX(), 20);
        assertEquals(line.getEndPoint().getY(), 20);
	}
	
	@Test
	@Order(8)
	public final void testEquals() {
		Line firstLine = new Line(new Point(10, 0), new Point(20, 0), Color.red);
		Line secondLine = new Line(new Point(110, 110), new Point(20, 0), Color.red);
		Point point = new Point(10,10);
		
		assertTrue(firstLine.equals(firstLine));
		assertFalse(firstLine.equals(secondLine));
		assertFalse(firstLine.equals(point));
	}
}
