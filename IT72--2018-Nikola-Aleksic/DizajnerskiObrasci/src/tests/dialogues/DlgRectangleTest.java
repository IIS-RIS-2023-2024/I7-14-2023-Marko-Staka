package tests.dialogues;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import dialogues.DlgRectangle;
import geometry.Rectangle;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DlgRectangleTest {
	
  @Test
  @Order(1)
  public void testGetRectangle() {
      DlgRectangle dialog = new DlgRectangle();
      dialog.getTxtX().setText("100");
      dialog.getTxtY().setText("100");
      dialog.getTxtHeight().setText("50");
      dialog.getTxtWidth().setText("50");
      dialog.getCcLineColor().setColor(Color.RED);
      dialog.getCcInnerColor().setColor(Color.BLUE);

      Rectangle rectangle = dialog.getRectangle();

      assertEquals(100, rectangle.getUpperLeftPoint().getX());
      assertEquals(100, rectangle.getUpperLeftPoint().getY());
      assertEquals(50, rectangle.getHeight());
      assertEquals(50, rectangle.getWidth());
      assertEquals(Color.RED, rectangle.getColor());
      assertEquals(Color.BLUE, rectangle.getInnerColor());
  }
}
