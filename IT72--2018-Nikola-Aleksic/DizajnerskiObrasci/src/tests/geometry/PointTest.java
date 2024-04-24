package tests.geometry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import geometry.Point;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PointTest {
	
	@Test
	@Order(1)
	public final void testPointConstructor1() {
        Point p = new Point(10, 10, true, Color.red);

        assertEquals(p.getX(), 10);
        assertEquals(p.getY(), 10);
        assertEquals(p.isSelected(), true);
        assertEquals(p.getColor(), Color.red);
	}
	
	@Test
	@Order(2)
	public final void testPointConstructor2() {
        Point p = new Point(10, 10, Color.red);

        assertEquals(p.getX(), 10);
        assertEquals(p.getY(), 10);
        assertEquals(p.getColor(), Color.red);
	}
	

	@Test
	@Order(3)
	public final void testPointConstructor3() {
        Point point = new Point(3, 4);
        int x2 = 6;
        int y2 = 8;

        assertEquals(5.0, point.distance(x2, y2), 0.0001);
    }
	
	@Test
	@Order(4)
	public final void testContaints() {
        Point point = new Point(10, 10);

        assertTrue(point.contains(10, 10));
    }
}
