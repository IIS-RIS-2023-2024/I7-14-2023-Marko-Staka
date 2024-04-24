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

import geometry.Donut;
import geometry.Point;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DonutTest {

    private Point center;
    private int radius;
    private int innerRadius;
    
	@BeforeEach
	public void setUpBeforeClass() throws Exception {
        center = new Point(10, 10);
        radius = 20;
        innerRadius = 10;
	}
	
	@Test
	@Order(1)
	public final void testDonutConstructor1() {
        Donut donut = new Donut(center, radius, innerRadius, true);

        assertEquals(donut.getCenter().getX(), 10);
        assertEquals(donut.getCenter().getY(), 10);
        assertEquals(donut.getRadius(), radius);
        assertEquals(donut.getInnerRadius(), innerRadius);
        assertEquals(donut.isSelected(), true);
	}
	
	@Test
	@Order(2)
	public final void testDonutConstructor2() {
        Donut donut = new Donut(center, radius, innerRadius, true, Color.red);

        assertEquals(donut.getCenter().getX(), 10);
        assertEquals(donut.getCenter().getY(), 10);
        assertEquals(donut.getRadius(), radius);
        assertEquals(donut.getInnerRadius(), innerRadius);
        assertEquals(donut.isSelected(), true);
        assertEquals(donut.getColor(), Color.red);
	}
	
	@Test
	@Order(3)
	public final void testDonutConstructor3() {
        Donut donut = new Donut(center, radius, innerRadius, true, Color.red, Color.blue);

        assertEquals(donut.getCenter().getX(), 10);
        assertEquals(donut.getCenter().getY(), 10);
        assertEquals(donut.getRadius(), radius);
        assertEquals(donut.getInnerRadius(), innerRadius);
        assertEquals(donut.isSelected(), true);
        assertEquals(donut.getColor(), Color.red);
        assertEquals(donut.getInnerColor(), Color.blue);
	}
	
	@Test
	@Order(4)
	public final void testDonutConstructor4() {
        Donut donut = new Donut(center, radius, innerRadius, Color.red);

        assertEquals(donut.getCenter().getX(), 10);
        assertEquals(donut.getCenter().getY(), 10);
        assertEquals(donut.getRadius(), radius);
        assertEquals(donut.getInnerRadius(), innerRadius);
        assertEquals(donut.getColor(), Color.red);
	}
	
	@Test
	@Order(5)
	public final void testDonutConstructor5() {
        Donut donut = new Donut(center, radius, innerRadius, Color.red, Color.blue);

        assertEquals(donut.getCenter().getX(), 10);
        assertEquals(donut.getCenter().getY(), 10);
        assertEquals(donut.getRadius(), radius);
        assertEquals(donut.getInnerRadius(), innerRadius);
        assertEquals(donut.getColor(), Color.red);
        assertEquals(donut.getInnerColor(), Color.blue);
	}
	
	@Test
	@Order(6)
	public final void testContaints() {
        Donut donut = new Donut(center, radius, innerRadius, Color.red, Color.blue);

        assertTrue(donut.contains(20,25));
    }
	
	@Test
	@Order(7)
	public final void testContaintsPoint() {
        Donut donut = new Donut(center, radius, innerRadius, Color.red, Color.blue);
        
        Point containsPoint = new Point(20,25);
        assertTrue(donut.contains(containsPoint));
    }
	
	@Test
	@Order(8)
	public final void testRadius() {       
		Donut donut = new Donut();
	    Exception exception = assertThrows(NumberFormatException.class, () -> {
	    	donut.setInnerRadius(-5);
	    });
	    String expectedMessage = "Inner radius value must be greater than 0!";
	    String actualMessage = exception.getMessage();
        assertEquals(expectedMessage, actualMessage);
	}
	
}
