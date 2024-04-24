package tests.dialogues;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import adapter.HexagonAdapter;
import dialogues.DlgHexagon;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DlgHexagonTest {

	  @Test
	  @Order(1)
	  public void testGetHexagon() {
		   	DlgHexagon dialog = new DlgHexagon();
	        dialog.getTxtX().setText("50");
	        dialog.getTxtY().setText("60");
	        dialog.getTxtRadius().setText("70");
	        dialog.getCcLineColor().setColor(Color.BLUE);
	        dialog.getCcInnerColor().setColor(Color.RED);

	        HexagonAdapter hexagon = dialog.getHexagon();

	        assertEquals(50, hexagon.getHexagonCenter().getX());
	        assertEquals(60, hexagon.getHexagonCenter().getY());
	        assertEquals(70, hexagon.getHexagonRadius());
	        assertEquals(Color.BLUE, hexagon.getHexagonBorderColor());
	        assertEquals(Color.RED, hexagon.getHexagonInnerColor());
	  }
}
