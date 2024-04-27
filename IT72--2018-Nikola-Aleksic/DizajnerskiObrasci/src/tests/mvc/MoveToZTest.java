package tests.mvc;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import command.AddShapeCmd;
import command.SelectShapeCmd;
import geometry.Point;
import mvc.DrawingController;
import mvc.DrawingFrame;
import mvc.DrawingModel;

public class MoveToZTest {
	private static DrawingModel model;
	private static DrawingFrame frame;
	private static DrawingController controller;
	private static AddShapeCmd command;
	
	@BeforeEach
	public void setUpBeforeClass() throws Exception {
        model = new DrawingModel();
        frame = new DrawingFrame();
        controller = new DrawingController(model, frame);
	}
	
	
    @Test
	@Order(1)
    void testBringToFront() {
    	command = new AddShapeCmd(model, new Point(1,1));
        command.execute();
    	command = new AddShapeCmd(model, new Point(2,2));
        command.execute();
    	command = new AddShapeCmd(model, new Point(3,3));
        command.execute();  

    	SelectShapeCmd selectShapeCmd2 = new SelectShapeCmd(controller, model.getOneShape(0));
    	selectShapeCmd2.execute();
    	
    	controller.bringToFront();
    	
    	Point p = (Point)model.getOneShape(2);
        assertEquals(p.getX(), 1);
        assertEquals(p.getY(), 1);
    }
    
    @Test
	@Order(3)
    void testToBack() {
    	command = new AddShapeCmd(model, new Point(1,1));
        command.execute();
    	command = new AddShapeCmd(model, new Point(2,2));
        command.execute();
    	command = new AddShapeCmd(model, new Point(3,3));
        command.execute();  

    	SelectShapeCmd selectShapeCmd2 = new SelectShapeCmd(controller, model.getOneShape(2));
    	selectShapeCmd2.execute();
    	
    	controller.toBack();
    	
    	Point p = (Point)model.getOneShape(1);
        assertEquals(p.getX(), 3);
        assertEquals(p.getY(), 3);
    }
    
    @Test
	@Order(2)
    void testToFront() {
    	command = new AddShapeCmd(model, new Point(1,1));
        command.execute();
    	command = new AddShapeCmd(model, new Point(2,2));
        command.execute();
    	command = new AddShapeCmd(model, new Point(3,3));
        command.execute();  

    	SelectShapeCmd selectShapeCmd2 = new SelectShapeCmd(controller, model.getOneShape(1));
    	selectShapeCmd2.execute();
    	
    	controller.toFront();
    	
    	Point p = (Point)model.getOneShape(2);
        assertEquals(p.getX(), 2);
        assertEquals(p.getY(), 2);
    }
    
    @Test
	@Order(2)
    void testBringToBack() {
    	command = new AddShapeCmd(model, new Point(1,1));
        command.execute();
    	command = new AddShapeCmd(model, new Point(2,2));
        command.execute();
    	command = new AddShapeCmd(model, new Point(3,3));
        command.execute();  

    	SelectShapeCmd selectShapeCmd2 = new SelectShapeCmd(controller, model.getOneShape(2));
    	selectShapeCmd2.execute();
    	
    	controller.bringToBack();
    	
    	Point p = (Point)model.getOneShape(0);
        assertEquals(p.getX(), 3);
        assertEquals(p.getY(), 3);
    }
}
