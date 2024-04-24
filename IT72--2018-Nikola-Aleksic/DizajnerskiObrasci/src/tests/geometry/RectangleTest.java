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

import geometry.Point;
import geometry.Rectangle;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RectangleTest {
    private Point upperLeftPoint;
    private int height;
    private int width;

	@BeforeEach
	public void setUpBeforeClass() throws Exception {
        upperLeftPoint = new Point(10, 10);
        height = 40;
        width = 70;
	}
	
	@Test
	@Order(1)
	public final void testRectangleConstructor1() {
		Rectangle rectangle = new Rectangle(upperLeftPoint, height, width, true);
		
        assertEquals(rectangle.getUpperLeftPoint().getX(), 10);
        assertEquals(rectangle.getUpperLeftPoint().getY(), 10);
        assertEquals(rectangle.getHeight(), 40);
        assertEquals(rectangle.getWidth(), 70);
        assertEquals(rectangle.isSelected(), true);
	}
	
	@Test
	@Order(2)
	public final void testRectangleConstructor2() {
		Rectangle rectangle = new Rectangle(upperLeftPoint, height, width, true, Color.red);
		
        assertEquals(rectangle.getUpperLeftPoint().getX(), 10);
        assertEquals(rectangle.getUpperLeftPoint().getY(), 10);
        assertEquals(rectangle.getHeight(), 40);
        assertEquals(rectangle.getWidth(), 70);
        assertEquals(rectangle.isSelected(), true);
        assertEquals(rectangle.getColor(), Color.red);
	}
	
	@Test
	@Order(3)
	public final void testRectangleConstructor3() {
		Rectangle rectangle = new Rectangle(upperLeftPoint, height, width, true, Color.red, Color.blue);
		
        assertEquals(rectangle.getUpperLeftPoint().getX(), 10);
        assertEquals(rectangle.getUpperLeftPoint().getY(), 10);
        assertEquals(rectangle.getHeight(), 40);
        assertEquals(rectangle.getWidth(), 70);
        assertEquals(rectangle.isSelected(), true);
        assertEquals(rectangle.getColor(), Color.red);
        assertEquals(rectangle.getInnerColor(), Color.blue);
	}
	
	@Test
	@Order(4)
	public final void testRectangleConstructor4() {
		Rectangle rectangle = new Rectangle(upperLeftPoint, height, width, Color.red);
		
        assertEquals(rectangle.getUpperLeftPoint().getX(), 10);
        assertEquals(rectangle.getUpperLeftPoint().getY(), 10);
        assertEquals(rectangle.getHeight(), 40);
        assertEquals(rectangle.getWidth(), 70);
        assertEquals(rectangle.getColor(), Color.red);
	}
	
	@Test
	@Order(5)
	public final void testRectangleConstructor5() {
		Rectangle rectangle = new Rectangle(upperLeftPoint, height, width, Color.red, Color.blue);
		
        assertEquals(rectangle.getUpperLeftPoint().getX(), 10);
        assertEquals(rectangle.getUpperLeftPoint().getY(), 10);
        assertEquals(rectangle.getHeight(), 40);
        assertEquals(rectangle.getWidth(), 70);
        assertEquals(rectangle.getColor(), Color.red);
        assertEquals(rectangle.getInnerColor(), Color.blue);
	}
	
	@Test
	@Order(6)
	public final void testArea() {
		Rectangle rectangle = new Rectangle(upperLeftPoint, height, width, Color.red, Color.blue);
		
        assertEquals(rectangle.area(), 2800, 0.001);
	}
	
	@Test
	@Order(7)
	public final void testMoveBy() {
		Rectangle rectangle = new Rectangle(upperLeftPoint, height, width, Color.red, Color.blue);
		rectangle.moveBy(20, 20);

        assertEquals(rectangle.getUpperLeftPoint().getX(), 30);
        assertEquals(rectangle.getUpperLeftPoint().getY(), 30);
	}

	@Test
	@Order(8)
	public final void testContaints() {
		Rectangle rectangle = new Rectangle(upperLeftPoint, height, width, Color.red, Color.blue);
        
        assertTrue(rectangle.contains(20,20));
        assertFalse(rectangle.contains(100,100));
    }
	
	@Test
	@Order(9)
	public final void testContaintsPoint() {
		Rectangle rectangle = new Rectangle(upperLeftPoint, height, width, Color.red, Color.blue);
        
        Point containsPoint1 = new Point(20,20);
        Point containsPoint2 = new Point(100,100);
        assertTrue(rectangle.contains(containsPoint1));
        assertFalse(rectangle.contains(containsPoint2));
    }
	
	@Test
	@Order(10)
	public final void testSetVariables() {
		Rectangle rectangle = new Rectangle(upperLeftPoint, height, width, Color.red, Color.blue);
        
        rectangle.setColor(Color.white);
        rectangle.setInnerColor(Color.white);
        rectangle.setSelected(false);
        rectangle.setHeight(100);
        rectangle.setWidth(300);
        rectangle.setUpperLeftPoint(new Point(50,50));
        
        assertEquals(rectangle.getUpperLeftPoint().getX(), 50);
        assertEquals(rectangle.getUpperLeftPoint().getY(), 50);
        assertEquals(rectangle.getHeight(), 100);
        assertEquals(rectangle.getWidth(), 300);
        assertEquals(rectangle.getColor(), Color.white);
        assertEquals(rectangle.getInnerColor(), Color.white);
    }
}
