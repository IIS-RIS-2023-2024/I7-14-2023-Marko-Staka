package tests.dialogues;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import dialogues.DlgDonut;
import geometry.Donut;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DlgDonutTest {
	
	  @Test
	  @Order(1)
	  public void testGetDonut() {
	        DlgDonut dialog = new DlgDonut();
	        dialog.getTxtX().setText("100");
	        dialog.getTxtY().setText("100");
	        dialog.getTxtRadius().setText("80");
	        dialog.getTxtInnerRadius().setText("50");
	        dialog.getCcLineColor().setColor(Color.RED);
	        dialog.getCcInnerColor().setColor(Color.BLUE);

	        Donut donut = dialog.getDonut();

	        assertEquals(100, donut.getCenter().getX());
	        assertEquals(100, donut.getCenter().getY());
	        assertEquals(80, donut.getRadius());
	        assertEquals(50, donut.getInnerRadius());
	        assertEquals(Color.RED, donut.getColor());
	        assertEquals(Color.BLUE, donut.getInnerColor());
	  }

}
