package tests.dialogues;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import dialogues.DlgCircle;
import geometry.Circle;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DlgCircleTest {

	  @Test
	  @Order(1)
	  public void testGetCircle() {
	        DlgCircle dialog = new DlgCircle();
	        dialog.getTxtX().setText("100");
	        dialog.getTxtY().setText("100");
	        dialog.getTxtRadius().setText("50");
	        dialog.getCcLineColor().setColor(Color.RED);
	        dialog.getCcInnerColor().setColor(Color.BLUE);

	        Circle circle = dialog.getCircle();

	        assertEquals(100, circle.getCenter().getX());
	        assertEquals(100, circle.getCenter().getY());
	        assertEquals(50, circle.getRadius());
	        assertEquals(Color.RED, circle.getColor());
	        assertEquals(Color.BLUE, circle.getInnerColor());
	  }
}
