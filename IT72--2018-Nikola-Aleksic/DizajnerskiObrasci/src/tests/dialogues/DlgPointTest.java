package tests.dialogues;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import dialogues.DlgPoint;
import geometry.Point;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DlgPointTest {

	  @Test
	  @Order(1)
	  public void testGetPoint() {
	        DlgPoint dialog = new DlgPoint();
	        dialog.getTxtX().setText("100");
	        dialog.getTxtY().setText("100");
	        dialog.getCcColor().setColor(Color.RED);

	        Point point = dialog.getPoint();

	        assertEquals(100, point.getX());
	        assertEquals(100, point.getY());
	        assertEquals(Color.RED, point.getColor());
	  }
}
