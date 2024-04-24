package tests.adapter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import adapter.HexagonAdapter;
import geometry.Point;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class HexagonAdapterTest {

    private Point center;
    private int radius;

	@BeforeEach
	public void setUpBeforeClass() throws Exception {
        center = new Point(10, 10);
        radius = 20;
	}
	
	@Test
	@Order(1)
	public final void testHexagonConstructor1() {
		HexagonAdapter hexagon = new HexagonAdapter(center, radius, true);
		
        assertEquals(hexagon.getHexagonCenter().getX(), 10);
        assertEquals(hexagon.getHexagonCenter().getY(), 10);
        assertEquals(hexagon.getHexagonRadius(), 20);
        assertEquals(hexagon.isSelected(), true);
	}
	
	@Test
	@Order(2)
	public final void testHexagonConstructor2() {
		HexagonAdapter hexagon = new HexagonAdapter(center, radius, true, Color.red);
		
        assertEquals(hexagon.getHexagonCenter().getX(), 10);
        assertEquals(hexagon.getHexagonCenter().getY(), 10);
        assertEquals(hexagon.getHexagonRadius(), 20);
        assertEquals(hexagon.isSelected(), true);
        assertEquals(hexagon.getHexagonBorderColor(), Color.red);
	}
	
	@Test
	@Order(3)
	public final void testHexagonConstructor3() {
		HexagonAdapter hexagon = new HexagonAdapter(center, radius, true, Color.red, Color.blue);
		
        assertEquals(hexagon.getHexagonCenter().getX(), 10);
        assertEquals(hexagon.getHexagonCenter().getY(), 10);
        assertEquals(hexagon.getHexagonRadius(), 20);
        assertEquals(hexagon.isSelected(), true);
        assertEquals(hexagon.getHexagonBorderColor(), Color.red);
        assertEquals(hexagon.getHexagonInnerColor(), Color.blue);
	}
	
	@Test
	@Order(4)
	public final void testHexagonConstructor4() {
		HexagonAdapter hexagon = new HexagonAdapter(center, radius, Color.red, Color.blue);
		
        assertEquals(hexagon.getHexagonCenter().getX(), 10);
        assertEquals(hexagon.getHexagonCenter().getY(), 10);
        assertEquals(hexagon.getHexagonRadius(), 20);
        assertEquals(hexagon.getHexagonBorderColor(), Color.red);
        assertEquals(hexagon.getHexagonInnerColor(), Color.blue);
	}
	
	@Test
	@Order(5)
	public final void testMoveBy() {
		HexagonAdapter hexagon = new HexagonAdapter(center, radius, Color.red, Color.blue);
		hexagon.moveBy(20, 20);

        assertEquals(hexagon.getHexagonCenter().getX(), 30);
        assertEquals(hexagon.getHexagonCenter().getY(), 30);
	}
	
	@Test
	@Order(6)
	public final void testContaints() {
		HexagonAdapter hexagon = new HexagonAdapter(center, radius, Color.red, Color.blue);

        assertTrue(hexagon.contains(10,15));
    }
	
	@Test
	@Order(7)
	public final void testContaintsPoint() {
		HexagonAdapter hexagon = new HexagonAdapter(center, radius, Color.red, Color.blue);
        
        Point containsPoint = new Point(10,15);
        assertTrue(hexagon.contains(containsPoint));
    }
	
	@Test
	@Order(8)
	public final void testArea() {
		HexagonAdapter hexagon = new HexagonAdapter(center, radius, Color.red, Color.blue);
        assertEquals(hexagon.area(), 1039, 0.001);
	}
	
	
}
