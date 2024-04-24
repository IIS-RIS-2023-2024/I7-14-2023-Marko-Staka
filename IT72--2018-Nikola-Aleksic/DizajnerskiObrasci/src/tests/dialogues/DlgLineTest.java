package tests.dialogues;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import dialogues.DlgLine;
import geometry.Line;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DlgLineTest {

	  @Test
	  @Order(1)
	  public void testGetLine() {
	        DlgLine dialog = new DlgLine();
	        dialog.getTxtStartX().setText("10");
	        dialog.getTxtStartY().setText("20");
	        dialog.getTxtEndX().setText("30");
	        dialog.getTxtEndY().setText("40");
	        dialog.getCcLineColor().setColor(Color.RED);

	        Line line = dialog.getLine();

	        assertEquals(10, line.getStartPoint().getX());
	        assertEquals(20, line.getStartPoint().getY());
	        assertEquals(30, line.getEndPoint().getX());
	        assertEquals(40, line.getEndPoint().getY());
	        assertEquals(Color.RED, line.getColor());
	  }
}
