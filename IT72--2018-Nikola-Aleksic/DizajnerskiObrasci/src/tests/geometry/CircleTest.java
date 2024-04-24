package tests.geometry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import geometry.Circle;
import geometry.Point;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class CircleTest {
    private Point center;
    private int radius;

	@BeforeEach
	public void setUpBeforeClass() throws Exception {
        center = new Point(10, 10);
        radius = 20;
	}
	
	@Test
	@Order(1)
	public final void testCircleConstructor1() {
        Circle c = new Circle(center, radius);

        assertEquals(c.getCenter().getX(), 10);
        assertEquals(c.getCenter().getY(), 10);
        assertEquals(c.getRadius(), radius);
	}
	
	@Test
	@Order(2)
	public final void testCircleConstructor2() {
        Circle c = new Circle(center, radius, true);

        assertEquals(c.getCenter().getX(), 10);
        assertEquals(c.getCenter().getY(), 10);
        assertEquals(c.getRadius(), radius);
        assertEquals(c.isSelected(), true);
	}
	
	@Test
	@Order(3)
	public final void testCircleConstructor3() {
        Circle c = new Circle(center, radius, true, Color.red);

        assertEquals(c.getCenter().getX(), 10);
        assertEquals(c.getCenter().getY(), 10);
        assertEquals(c.getRadius(), radius);
        assertEquals(c.isSelected(), true);
        assertEquals(c.getColor(), Color.red);
	}
	
	@Test
	@Order(4)
	public final void testCircleConstructor5() {
        Circle c = new Circle(center, radius, true, Color.red, Color.blue);

        assertEquals(c.getCenter().getX(), 10);
        assertEquals(c.getCenter().getY(), 10);
        assertEquals(c.getRadius(), radius);
        assertEquals(c.isSelected(), true);
        assertEquals(c.getColor(), Color.red);
        assertEquals(c.getInnerColor(), Color.blue);
	}
	
	@Test
	@Order(5)
	public final void testCircleConstructor6() {
        Circle c = new Circle(center, radius,Color.red);

        assertEquals(c.getCenter().getX(), 10);
        assertEquals(c.getCenter().getY(), 10);
        assertEquals(c.getRadius(), radius);
        assertEquals(c.getColor(), Color.red);
	}
	
	@Test
	@Order(6)
	public final void testCircleConstructor7() {
        Circle c = new Circle(center, radius, Color.red, Color.blue);

        assertEquals(c.getCenter().getX(), 10);
        assertEquals(c.getCenter().getY(), 10);
        assertEquals(c.getRadius(), radius);
        assertEquals(c.getColor(), Color.red);
        assertEquals(c.getInnerColor(), Color.blue);
	}
	
	@Test
	@Order(7)
	public final void testRadius() {       
		Circle circle = new Circle();
	    Exception exception = assertThrows(NumberFormatException.class, () -> {
	        circle.setRadius(-5);
	    });
	    String expectedMessage = "Radius value must be greater than 0!";
	    String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
	}
	
	
	@Test
	@Order(8)
	public final void testMoveBy() {
        Circle c = new Circle(center, radius);
        c.moveBy(20, 20);

        assertEquals(c.getCenter().getX(), 30);
        assertEquals(c.getCenter().getY(), 30);
	}
	
	@Test
	@Order(9)
	public final void testCenter() {
        Circle circle = new Circle(center, radius);
        circle.setCenter(new Point(20, 20));

        assertEquals(circle.getCenter().getX(), 20);
        assertEquals(circle.getCenter().getY(), 20);
	}
	
	@Test
	@Order(10)
	public final void testContaints() {
        Circle circle = new Circle(center, radius);

        assertTrue(circle.contains(10,15));
    }
	
	@Test
	@Order(11)
	public final void testContaintsPoint() {
        Circle circle = new Circle(center, radius);
        
        Point containsPoint = new Point(10,15);
        assertTrue(circle.contains(containsPoint));
    }
	
}
