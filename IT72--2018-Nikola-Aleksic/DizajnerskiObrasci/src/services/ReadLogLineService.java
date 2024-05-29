package services;

import java.awt.Color;
import java.util.ArrayList;

import adapter.HexagonAdapter;
import command.AddShapeCmd;
import command.BringToBackCmd;
import command.BringToFrontCmd;
import command.Command;
import command.ModifyCircleCmd;
import command.ModifyDonutCmd;
import command.ModifyHexagonCmd;
import command.ModifyLineCmd;
import command.ModifyPointCmd;
import command.ModifyRectangleCmd;
import command.RemoveShapeCmd;
import command.SelectShapeCmd;
import command.ToBackCmd;
import command.ToFrontCmd;
import command.UnselectShapeCmd;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import mvc.DrawingController;
import mvc.DrawingFrame;
import mvc.DrawingModel;

public class ReadLogLineService {
	
	private DrawingModel model;
	private DrawingFrame frame;
	private DrawingController controller;
	
	public Command command;

	public ArrayList<Shape> selectedShapeList = new ArrayList<Shape>();
	
	public ReadLogLineService(DrawingModel model, DrawingFrame frame, DrawingController controller) {
		this.model = model;
		this.frame = frame;
		this.controller = controller;
	}
		
	public void readLogLine(String line) {	
		String[] lineElements = line.split(":"); 														
		String[] values = replaceCharacter(lineElements).split(",");

		if (lineElements[0].equals("Add"))
			readAddShape(lineElements[1], values);
		else if(lineElements[0].equals("Select"))
			readSelectShape(line.substring(8));
		else if(lineElements[0].equals("Unselect"))
			readDeselectShape(line.substring(10));
		else if (lineElements[0].equals("Modify")) 
			readModifyShape(lineElements, values);
		else if (lineElements[0].equals("Delete")) 
			readDeleteShape(lineElements);
		else if ((lineElements[0].equals("Move to back")) || (lineElements[0].equals("Move to front")) || (lineElements[0].equals("Bring to back")) || (lineElements[0].equals("Bring to front"))) 
			readShapeMoveTo(lineElements, values);
		else if (lineElements[0].equals("Undo")) 
			controller.undo();
		else if (lineElements[0].equals("Redo")) 
			controller.redo();
	}
	
	public void readAddShape(String shape, String[] values) {
		if (shape.equals(" Point"))
			readAddPoint(values);
		else if (shape.equals(" Line")) 
			readAddLine(values);
		else if (shape.equals(" Rectangle")) 
			readAddRectangle(values);
		else if (shape.equals(" Circle")) 
			readAddCircle(values);
		else if (shape.equals(" Donut")) 
			readAddDonut(values);
		else if (shape.equals(" Hexagon")) 
			readAddHexagon(values);
	}

	public void readAddPoint(String[] values) {
		Point p = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]),
				 new Color(Integer.parseInt("-"+(values[2]))));
		command = new AddShapeCmd(model, p);
		executeCommand(command);
	}
		
	public void readAddLine(String[] values) {
		Point start = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
		Point end = new Point(Integer.parseInt(values[2]), Integer.parseInt(values[3]));

		Line l = new Line(start, end, new Color(Integer.parseInt("-"+(values[4]))));

		command = new AddShapeCmd(model, l);
		executeCommand(command);
	}
	
	public void readAddRectangle(String[] values) {
		Point upperLeft = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
		int height = Integer.parseInt(values[2]);
		int width = Integer.parseInt(values[3]);

		Rectangle r = new Rectangle(upperLeft, height, width, new Color(Integer.parseInt("-"+(values[5]))),
				new Color(Integer.parseInt("-"+(values[4]))));
		command = new AddShapeCmd(model, r);
		executeCommand(command);
	}
	
	public void readAddCircle(String[] values) {
		Point center = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
		int radius = Integer.parseInt(values[2]);

		Circle c = new Circle(center, radius, new Color(Integer.parseInt("-"+(values[4]))), new Color(Integer.parseInt("-"+(values[3]))));
		command = new AddShapeCmd(model, c);
		executeCommand(command);
	}
	
	public void readAddDonut(String[] values) {
		Point center = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
		int radius = Integer.parseInt(values[2]);
		int innerRadius = Integer.parseInt(values[3]);

		Donut d = new Donut(center, radius, innerRadius, new Color(Integer.parseInt("-"+(values[5]))), new Color(Integer.parseInt("-"+(values[4]))));
		command = new AddShapeCmd(model, d);
		executeCommand(command);
	}
	
	public void readAddHexagon(String[] values) {
		int x = Integer.parseInt(values[0]);
		int y = Integer.parseInt(values[1]);
		int r = Integer.parseInt(values[2]);
		Point p = new Point(x , y);
		HexagonAdapter h = new HexagonAdapter(p, r, new Color(Integer.parseInt("-"+(values[4]))), new Color(Integer.parseInt("-"+(values[3]))));
		command = new AddShapeCmd(model, h);
		executeCommand(command);
	}

	public void readSelectShape(String shape) {
		for(Shape s : model.getShapes()) {
			if(s.toString().equals(shape)) {
				command = new SelectShapeCmd(controller, s);
				executeCommand(command);
				break;
			}
		}
	}
	
	public void readDeselectShape(String shape) {
		for(Shape s : model.getShapes()) {
			if(s.toString().equals(shape)) {
				command = new UnselectShapeCmd(controller, s);
				executeCommand(command);
				break;
			}
		}
	}
	
	public void readModifyShape(String[] lineElements, String[] values) {
		String newValuesLine = lineElements[3].replaceAll("[^0-9,.]", "");
		String[] newValues = newValuesLine.split(",");

		if (lineElements[1].equals(" Point")) 
			readModifyPoint(values, newValues);
		else if (lineElements[1].equals(" Line")) 
			readModifyLine(values, newValues);
		else if (lineElements[1].equals(" Rectangle")) 
			readModifyRectangle(values, newValues);
		else if (lineElements[1].equals(" Circle")) 
			readModifyCircle(values, newValues);
		else if (lineElements[1].equals(" Donut")) 
			readModifyDonut(values, newValues);
		else if (lineElements[1].equals(" Hexagon")) 
			readModifyHexagon(values, newValues);
	}
	
	public void readModifyPoint(String[] values, String[] newValues) {
		Point oldPoint = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]), true,
				new Color(Integer.parseInt("-"+(values[2]))));
		Point newPoint = new Point(Integer.parseInt(newValues[0]), Integer.parseInt(newValues[1]),
				new Color(Integer.parseInt("-"+(newValues[2]))));
		
		for (Shape s : model.getShapes()) {
			if (s.toString().equals(oldPoint.toString())) {
				command = new ModifyPointCmd((Point) s, newPoint);
				executeCommand(command);
				break;
			}
		}
	}
	
	public void readModifyLine(String[] values, String[] newValues) {
		Line oldLine = new Line(new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1])),
				new Point(Integer.parseInt(values[2]), Integer.parseInt(values[3])),
				true,
				new Color(Integer.parseInt("-"+(values[4]))));
		Line newLine = new Line(
				new Point(Integer.parseInt(newValues[0]), Integer.parseInt(newValues[1])),
				new Point(Integer.parseInt(newValues[2]), Integer.parseInt(newValues[3])),
				new Color(Integer.parseInt("-"+(newValues[4]))));
		
		for (Shape s : model.getShapes()) {
			if (s.toString().equals(oldLine.toString())) {
				command = new ModifyLineCmd((Line) s, newLine);
				executeCommand(command);
				break;
			}
		}
	}
	
	public void readModifyRectangle(String[] values, String[] newValues) {
		Rectangle oldRectangle = new Rectangle(
				new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1])),
				Integer.parseInt(values[2]), Integer.parseInt(values[3]), 
				true,
				new Color(Integer.parseInt("-"+(values[5]))),
				new Color(Integer.parseInt("-"+(values[4]))));
		Rectangle newRectangle = new Rectangle(
				new Point(Integer.parseInt(newValues[0]), Integer.parseInt(newValues[1])),
				Integer.parseInt(newValues[2]), Integer.parseInt(newValues[3]),
				new Color(Integer.parseInt("-"+(newValues[4]))), new Color(Integer.parseInt("-"+(newValues[5]))));

		for (Shape s : model.getShapes()) {
			if (s.toString().equals(oldRectangle.toString())) {
				command = new ModifyRectangleCmd((Rectangle) s, newRectangle);
				executeCommand(command);
				break;
			}
		}
	}
	
	public void readModifyCircle(String[] values, String[] newValues) {
		Circle oldCircle = new Circle(
				new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1])),
				Integer.parseInt(values[2]), 
				true,
				new Color(Integer.parseInt("-"+(values[4]))), 
				new Color(Integer.parseInt("-"+(values[3]))));
		Circle newCircle = new Circle(
				new Point(Integer.parseInt(newValues[0]), Integer.parseInt(newValues[1])),
				Integer.parseInt(newValues[2]), new Color(Integer.parseInt("-"+(newValues[4]))), new Color(Integer.parseInt("-"+(newValues[3]))));

		for (Shape s : model.getShapes()) {
			if (s.toString().equals(oldCircle.toString())) {
				command = new ModifyCircleCmd((Circle) s, newCircle);
				executeCommand(command);
				break;
			}
		}
	}
	
	public void readModifyDonut(String[] values, String[] newValues) {
		Donut oldDonut = new Donut(
				new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1])),
				Integer.parseInt(values[2]), Integer.parseInt(values[3]), 
				true,
				new Color(Integer.parseInt("-"+(values[5]))), 
				new Color(Integer.parseInt("-"+(values[4]))));
		Donut newDonut = new Donut(
				new Point(Integer.parseInt(newValues[0]), Integer.parseInt(newValues[1])),
				Integer.parseInt(newValues[2]), Integer.parseInt(newValues[3]), new Color(Integer.parseInt("-"+(newValues[5]))), new Color(Integer.parseInt("-"+(newValues[4]))));

		for (Shape s : model.getShapes()) {
			if (s.toString().equals(oldDonut.toString())) {
				command = new ModifyDonutCmd((Donut) s, newDonut);
				executeCommand(command);
				break;
			}
		}
	}
	
	public void readModifyHexagon(String[] values, String[] newValues) {
		Point p = new Point(Integer.parseInt(values[0]) , Integer.parseInt(values[1]));
		Point newP = new Point(Integer.parseInt(newValues[0]) , Integer.parseInt(newValues[1]));
		HexagonAdapter oldHexagon = new HexagonAdapter(p, Integer.parseInt(values[2]), 
				true,
				new Color(Integer.parseInt("-"+(values[4]))),
				new Color(Integer.parseInt("-"+(values[3]))));
		HexagonAdapter newHexagon = new HexagonAdapter(newP, Integer.parseInt(newValues[2]),
				new Color(Integer.parseInt("-"+(newValues[4]))), new Color(Integer.parseInt("-"+(newValues[3]))));

		for (Shape s : model.getShapes()) {
			if (s.toString().equals(oldHexagon.toString())) {
				command = new ModifyHexagonCmd((HexagonAdapter) s, newHexagon);
				executeCommand(command);
				break;
			}
		}
	}
	
	public void readDeleteShape(String[] lineElements) {
		String shapeValues = lineElements[2].replaceAll("[^0-9,.]", "");
		String[] sValues = shapeValues.split(",");
		
		if (lineElements[1].equals(" Point")) 
			readDeletePoint(sValues);
		else if (lineElements[1].equals(" Line")) 
			readDeleteLine(sValues);
		else if (lineElements[1].equals(" Rectangle")) 
			readDeleteRectangle(sValues);
		else if (lineElements[1].equals(" Circle")) 
			readDeleteCircle(sValues);
		else if (lineElements[1].equals(" Donut")) 
			readDeleteDonut(sValues);
		else if (lineElements[1].equals(" Hexagon")) 
			readDeleteHexagon(sValues);
	}
	
	public void readDeletePoint(String[] values) {
		Point p = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]), 
				true, new Color(Integer.parseInt("-"+(values[2]))));
		int position = Integer.parseInt(values[3]);
		
		for (Shape s : model.getShapes()) {
			if (s.toString().equals(p.toString())) {
				Shape temp = s;
				position = model.getShapes().indexOf(s);
				command = new RemoveShapeCmd(model, temp, position);
				executeCommand(command);
				break;
			}
		}
	}

	public void readDeleteLine(String[] values) {
		Point start = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
		Point end = new Point(Integer.parseInt(values[2]), Integer.parseInt(values[3]));
		Color color = new Color(Integer.parseInt("-"+(values[4])));
		int position = Integer.parseInt(values[5]);
		
		Line l = new Line(start, end, color);
		for (Shape s : model.getShapes()) {
			if (s.toString().equals(l.toString())) {
				Shape temp = s;
				position = model.getShapes().indexOf(s);
				command = new RemoveShapeCmd(model, temp, position);
				executeCommand(command);
				break;
			}
		}
	}
	
	public void readDeleteRectangle(String[] values) {
		Point start = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
		int height = Integer.parseInt(values[2]);
		int width = Integer.parseInt(values[3]);
		Color color = new Color(Integer.parseInt("-"+(values[5])));
		Color innerColor = new Color(Integer.parseInt("-"+(values[4])));
		int position = Integer.parseInt(values[6]);
		
		Rectangle r = new Rectangle(start, height, width, color, innerColor);
		for (Shape s : model.getShapes()) {
			if (s.toString().equals(r.toString())) {
				Shape temp = s;
				position = model.getShapes().indexOf(s);
				command = new RemoveShapeCmd(model, temp, position);
				executeCommand(command);
				break;
			}
		}
	}
	
	public void readDeleteCircle(String[] values) {
		Point center = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
		int radius = Integer.parseInt(values[2]);
		Color color = new Color(Integer.parseInt("-"+(values[4])));
		Color innerColor = new Color(Integer.parseInt("-"+(values[3])));
		int position = Integer.parseInt(values[5]);

		Circle c = new Circle(center, radius, color, innerColor);
		for (Shape s : model.getShapes()) {
			if (s.toString().equals(c.toString())) {
				Shape temp = s;
				position = model.getShapes().indexOf(s);
				command = new RemoveShapeCmd(model, temp, position);
				executeCommand(command);
				break;
			}
		}
	}
	
	public void readDeleteDonut(String[] values) {
		Point center = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
		int radius = Integer.parseInt(values[2]);
		int innerRadius = Integer.parseInt(values[3]);
		Color color = new Color(Integer.parseInt("-"+(values[5])));
		Color innerColor = new Color(Integer.parseInt("-"+(values[4])));
		int position = Integer.parseInt(values[6]);

		Donut d = new Donut(center, radius, innerRadius, color, innerColor);
		for (Shape s : model.getShapes()) {
			if (s.toString().equals(d.toString())) {
				Shape temp = s;
				position = model.getShapes().indexOf(s);
				command = new RemoveShapeCmd(model, temp, position);
				executeCommand(command);
				break;
			}
		}
	}
	
	public void readDeleteHexagon(String[] values) {
		Color color = new Color(Integer.parseInt("-"+(values[4])));
		Color innerColor = new Color(Integer.parseInt("-"+(values[3])));
		int position = Integer.parseInt(values[5]);

		Point p = new Point(Integer.parseInt(values[0]) , Integer.parseInt(values[1]));
		HexagonAdapter h = new HexagonAdapter(p , Integer.parseInt(values[2]), color, innerColor);
		for (Shape s : model.getShapes()) {
			if (s.toString().equals(h.toString())) {
				Shape temp = s;
				position = model.getShapes().indexOf(s);
				command = new RemoveShapeCmd(model, temp, position);
				executeCommand(command);
				break;
			}
		}	
	}
	
	public void readShapeMoveTo(String[] lineElements, String[] values) {
		if (lineElements[1].equals(" Point")) 
			readPointMoveTo(lineElements[0], values);
		else if (lineElements[1].equals(" Line")) 
			readLineMoveTo(lineElements[0], values);
		else if (lineElements[1].equals(" Rectangle")) 
			readRectangleMoveTo(lineElements[0], values);
		else if (lineElements[1].equals(" Circle")) 
			readCircleMoveTo(lineElements[0], values);
		else if (lineElements[1].equals(" Donut")) 
			readDonutMoveTo(lineElements[0], values);
		else if (lineElements[1].equals(" Hexagon")) 
			readHexagonMoveTo(lineElements[0], values);
	}
	
	public void readPointMoveTo(String commandLog, String[] values) {
		Color color = new Color(Integer.parseInt("-"+(values[2])));
		
		Point point = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]), color);
		executeMoveToCommand(commandLog, point);
	}
	
	public void readLineMoveTo(String commandLog, String[] values) {
		Point startPoint = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
		Point endPoint = new Point(Integer.parseInt(values[2]), Integer.parseInt(values[3]));
		Color color = new Color(Integer.parseInt("-"+(values[4])));

		Line line = new Line(startPoint, endPoint, color);
		executeMoveToCommand(commandLog, line);
	}
	
	public void readRectangleMoveTo(String commandLog, String[] values) {
		Point start = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
		int height = Integer.parseInt(values[2]);
		int width = Integer.parseInt(values[3]);
		Color color = new Color(Integer.parseInt("-"+(values[4])));
		Color innerColor = new Color(Integer.parseInt("-"+(values[5])));

		Rectangle rectangle = new Rectangle(start, height, width, innerColor, color);
		executeMoveToCommand(commandLog, rectangle);
	}
	
	public void readCircleMoveTo(String commandLog, String[] values) {
		Point center = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
		int radius = Integer.parseInt(values[2]);
		Color color = new Color(Integer.parseInt("-"+(values[4])));
		Color innerColor = new Color(Integer.parseInt("-"+(values[3])));

		Circle circle = new Circle(center, radius, color, innerColor);
		executeMoveToCommand(commandLog, circle);	
	}
	
	public void readDonutMoveTo(String commandLog, String[] values) {
		Point center = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
		int radius = Integer.parseInt(values[2]);
		int innerRadius = Integer.parseInt(values[3]);
		Color color = new Color(Integer.parseInt("-"+(values[5])));
		Color innerColor = new Color(Integer.parseInt("-"+(values[4])));

		Donut donut = new Donut(center, radius, innerRadius, color, innerColor);
		executeMoveToCommand(commandLog, donut);
	}
	
	public void readHexagonMoveTo(String commandLog, String[] values) {
		int x = Integer.parseInt(values[0]);
		int y = Integer.parseInt(values[1]);
		int radius = Integer.parseInt(values[2]);
		Point center = new Point(x , y);
		
		HexagonAdapter hexagon = new HexagonAdapter(center, radius, new Color(Integer.parseInt("-"+(values[4]))), new Color(Integer.parseInt("-"+(values[3]))));
		executeMoveToCommand(commandLog, hexagon);
	}
	
	public void executeMoveToCommand(String commandLog, Shape shape) {
		for (Shape s : model.getShapes()) {
			if (s.toString().equals(shape.toString())) {
				command = createMoveToCommand(commandLog, s);
				executeCommand(command);
				break;
			}
		}
	}
	
	public Command createMoveToCommand(String commandLog, Shape shape) {
		int shapePosition = model.getShapes().indexOf(shape);
		
		if (commandLog.equals("Move to back")) 
			command = new ToBackCmd(model, shape, shapePosition);
		else if (commandLog.equals("Move to front"))
			command = new ToFrontCmd(model, shape, shapePosition);
		else if (commandLog.equals("Bring to back"))
			command = new BringToBackCmd(model, shape, shapePosition);
		else if (commandLog.equals("Bring to front"))
			command = new BringToFrontCmd(model, shape, shapePosition);
		return command;
	}
	
	public void executeCommand(Command command) {
		command.execute();
		controller.getUndoStack().push(command);
		controller.getRedoStack().clear();
		controller.changeButtonsAvailability();
		frame.getTextArea().append(command.toString());
		frame.repaint();
	}
	
	public String replaceCharacter(String[] lineElements){
		if(lineElements[2] != null) {
			return lineElements[2].replaceAll("[^0-9,.]", "");
		} else {
			return lineElements[1].replaceAll("[^0-9,.]", "");
		}
	}
}