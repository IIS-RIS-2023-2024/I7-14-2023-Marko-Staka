package tests.mvc;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import command.AddShapeCmd;
import geometry.Point;
import mvc.DrawingController;
import mvc.DrawingFrame;
import mvc.DrawingModel;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UndoRedoTest {

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
    void testUndo() {
    	command = new AddShapeCmd(model, new Point(1,1));
        command.execute();
        controller.undoStack.push(command);
      	controller.undo();
        assertTrue(controller.redoStack.get(0).equals(command));
    }
    
    @Test
	@Order(2)
    void testRedo() {
    	command = new AddShapeCmd(model, new Point(1,1));
        command.execute();
        controller.redoStack.push(command);
      	controller.redo();
        assertTrue(controller.undoStack.get(0).equals(command));
    }
    
    

}
