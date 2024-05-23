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
		
		String valuesLine = replaceCharacter(lineElements);
																	
		String[] values = valuesLine.split(",");

		if (lineElements[0].equals("Add")) {
			if (lineElements[1].equals(" Point")) {
				readAddPoint(values);
			} else if (lineElements[1].equals(" Line")) {
				readAddLine(values);
			} else if (lineElements[1].equals(" Rectangle")) {
				readAddRectangle(values);
			} else if (lineElements[1].equals(" Circle")) {
				readAddCircle(values);
			} else if (lineElements[1].equals(" Donut")) {
				readAddDonut(values);
			} else if (lineElements[1].equals(" Hexagon")) {
				readAddHexagon(values);
			}
		} else if(lineElements[0].equals("Select")){
			readSelectShape(line.substring(8));
		} else if(lineElements[0].equals("Unselect")){
			readDeselectShape(line.substring(10));
		} else if (lineElements[0].equals("Modify")) {
			String newValuesLine = lineElements[3].replaceAll("[^0-9,.]", "");
			String[] newValues = newValuesLine.split(",");

			if (lineElements[1].equals(" Point")) {
				readModifyPoint(values, newValues);
			} else if (lineElements[1].equals(" Line")) {
				readModifyLine(values, newValues);
			} else if (lineElements[1].equals(" Rectangle")) {
				readModifyRectangle(values, newValues);
			} else if (lineElements[1].equals(" Circle")) {
				readModifyCircle(values, newValues);
			} else if (lineElements[1].equals(" Donut")) {
				readModifyDonut(values, newValues);
			} else if (lineElements[1].equals(" Hexagon")) {
				readModifyHexagon(values, newValues);
			}
		} else if (lineElements[0].equals("Delete")) {
			String shapeValues = lineElements[2].replaceAll("[^0-9,.]", "");
			String[] sValues = shapeValues.split(",");
			
			if (lineElements[1].equals(" Point")) {
				readDeletePoint(sValues);
			} else if (lineElements[1].equals(" Line")) {
				readDeleteLine(sValues);
			} else if (lineElements[1].equals(" Rectangle")) {
				readDeleteRectangle(sValues);
			} else if (lineElements[1].equals(" Circle")) {
				readDeleteCircle(sValues);
			} else if (lineElements[1].equals(" Donut")) {
				readDeleteDonut(sValues);
			} else if (lineElements[1].equals(" Hexagon")) {
				readDeleteHexagon(sValues);
			}
		} else if ((lineElements[0].equals("Move to back")) || (lineElements[0].equals("Move to front"))
				|| (lineElements[0].equals("Bring to back")) || (lineElements[0].equals("Bring to front"))) {

			if (lineElements[1].equals(" Point")) {
				readPointMoveTo(lineElements[0], values);
			} else if (lineElements[1].equals(" Line")) {
				readLineMoveTo(lineElements[0], values);
			} else if (lineElements[1].equals(" Rectangle")) {
				readRectangleMoveTo(lineElements[0], values);
			} else if (lineElements[1].equals(" Circle")) {
				readCircleMoveTo(lineElements[0], values);
			} else if (lineElements[1].equals(" Donut")) {
				readDonutMoveTo(lineElements[0], values);
			} else if (lineElements[1].equals(" Hexagon")) {
				readHexagonMoveTo(lineElements[0], values);
			}
		} else if (lineElements[0].equals("Undo")) {
			command = controller.getUndoStack().peek();
			command.unexecute();
			frame.getTextArea().append("Undo: [ " + controller.getUndoStack().peek().toString().replace("\n","") + " ]\n");
			frame.repaint();
			controller.getUndoStack().pop();
			controller.getRedoStack().push(command);
		} else if (lineElements[0].equals("Redo")) {
			controller.redo();
		}
		controller.changeButtonsAvailability();
		frame.getView().repaint();
		
	}

	public void readAddPoint(String[] values) {
		Point p = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]),
				 new Color(Integer.parseInt("-"+(values[2]))));
		command = new AddShapeCmd(model, p);
		command.execute();
		frame.getTextArea().append(command.toString());
		controller.getUndoStack().push(command);
		controller.getRedoStack().clear();
	}
		
	public void readAddLine(String[] values) {
		Point start = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
		Point end = new Point(Integer.parseInt(values[2]), Integer.parseInt(values[3]));

		Line l = new Line(start, end, new Color(Integer.parseInt("-"+(values[4]))));

		command = new AddShapeCmd(model, l);
		command.execute();
		frame.getTextArea().append(command.toString());
		controller.getUndoStack().push(command);
		controller.getRedoStack().clear();
	}
	
	public void readAddRectangle(String[] values) {
		Point upperLeft = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
		int height = Integer.parseInt(values[2]);
		int width = Integer.parseInt(values[3]);

		Rectangle r = new Rectangle(upperLeft, height, width, new Color(Integer.parseInt("-"+(values[5]))),
				new Color(Integer.parseInt("-"+(values[4]))));
		command = new AddShapeCmd(model, r);
		command.execute();
		frame.getTextArea().append(command.toString());
		controller.getUndoStack().push(command);
		controller.getRedoStack().clear();
	}
	
	public void readAddCircle(String[] values) {
		Point center = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
		int radius = Integer.parseInt(values[2]);

		Circle c = new Circle(center, radius, new Color(Integer.parseInt("-"+(values[4]))), new Color(Integer.parseInt("-"+(values[3]))));
		command = new AddShapeCmd(model, c);
		command.execute();
		frame.getTextArea().append(command.toString());
		controller.getUndoStack().push(command);
		controller.getRedoStack().clear();
	}
	
	public void readAddDonut(String[] values) {
		Point center = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
		int radius = Integer.parseInt(values[2]);
		int innerRadius = Integer.parseInt(values[3]);

		Donut d = new Donut(center, radius, innerRadius, new Color(Integer.parseInt("-"+(values[5]))), new Color(Integer.parseInt("-"+(values[4]))));
		command = new AddShapeCmd(model, d);
		command.execute();
		frame.getTextArea().append(command.toString());
		controller.getUndoStack().push(command);
		controller.getRedoStack().clear();
	}
	
	public void readAddHexagon(String[] values) {
		int x = Integer.parseInt(values[0]);
		int y = Integer.parseInt(values[1]);
		int r = Integer.parseInt(values[2]);
		Point p = new Point(x , y);
		HexagonAdapter h = new HexagonAdapter(p, r, new Color(Integer.parseInt("-"+(values[4]))), new Color(Integer.parseInt("-"+(values[3]))));
		command = new AddShapeCmd(model, h);
		command.execute();
		frame.getTextArea().append(command.toString());
		controller.getUndoStack().push(command);
		controller.getRedoStack().clear();	
	}
	
	public String replaceCharacter(String[] lineElements){
		if(lineElements[2] != null) {
			return lineElements[2].replaceAll("[^0-9,.]", "");
		} else {
			return lineElements[1].replaceAll("[^0-9,.]", "");
		}
	}

	public void readSelectShape(String shape) {
		for(Shape s : model.getShapes()) {
			if(s.toString().equals(shape)) {
				
				if(!s.isSelected()) {
					command = new SelectShapeCmd(controller, s);
					command.execute();
					frame.getTextArea().append(command.toString());
					controller.getUndoStack().push(command);
					controller.getRedoStack().clear();
					controller.changeButtonsAvailability();
					frame.repaint();
					break;
				}
			}
		}
	}
	
	public void readDeselectShape(String shape) {
		for(Shape s : model.getShapes()) {
			if(s.toString().equals(shape)) {
				
				if(s.isSelected()) {
					command = new UnselectShapeCmd(controller, s);
					command.execute();
					frame.getTextArea().append(command.toString());
					controller.getUndoStack().push(command);
					controller.getRedoStack().clear();
					controller.changeButtonsAvailability();
					frame.repaint();
					break;
				}
			}
		}
	}
	
	public void readModifyPoint(String[] values, String[] newValues) {
		Point oldPoint = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]), true,
				new Color(Integer.parseInt("-"+(values[2]))));
		Point newPoint = new Point(Integer.parseInt(newValues[0]), Integer.parseInt(newValues[1]),
				new Color(Integer.parseInt("-"+(newValues[2]))));
		for (Shape s : model.getShapes()) {
			if (s.toString().equals(oldPoint.toString())) {
				command = new ModifyPointCmd((Point) s, newPoint);
				command.execute();
				frame.getTextArea().append(command.toString());
				controller.getUndoStack().push(command);
				controller.getRedoStack().clear();
				controller.changeButtonsAvailability();
				frame.repaint();
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
				command.execute();
				frame.getTextArea().append(command.toString());
				controller.getUndoStack().push(command);
				controller.getRedoStack().clear();
				controller.changeButtonsAvailability();
				frame.repaint();
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
				command.execute();
				frame.getTextArea().append(command.toString());
				controller.getUndoStack().push(command);
				controller.getRedoStack().clear();
				controller.changeButtonsAvailability();
				frame.repaint();
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
				command.execute();
				frame.getTextArea().append(command.toString());
				controller.getUndoStack().push(command);
				controller.getRedoStack().clear();
				controller.changeButtonsAvailability();
				frame.repaint();
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
				command.execute();
				frame.getTextArea().append(command.toString());
				controller.getUndoStack().push(command);
				controller.getRedoStack().clear();
				controller.changeButtonsAvailability();
				frame.repaint();
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
				command.execute();
				frame.getTextArea().append(command.toString());
				controller.getUndoStack().push(command);
				controller.getRedoStack().clear();
				controller.changeButtonsAvailability();
				frame.repaint();
				break;
			}
		}
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
				command.execute();
				frame.getTextArea().append(command.toString());
				controller.getUndoStack().push(command);
				selectedShapeList.remove(temp);
				controller.changeButtonsAvailability();
				frame.repaint();
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
				command.execute();
				frame.getTextArea().append(command.toString());
				controller.getUndoStack().push(command);
				selectedShapeList.remove(temp);
				controller.changeButtonsAvailability();
				frame.repaint();
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
				command.execute();
				frame.getTextArea().append(command.toString());
				controller.getUndoStack().push(command);
				selectedShapeList.remove(temp);
				controller.changeButtonsAvailability();
				frame.repaint();
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
				command.execute();
				frame.getTextArea().append(command.toString());
				controller.getUndoStack().push(command);
				selectedShapeList.remove(temp);
				controller.changeButtonsAvailability();
				frame.repaint();
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
				command.execute();
				frame.getTextArea().append(command.toString());
				controller.getUndoStack().push(command);
				selectedShapeList.remove(temp);
				controller.changeButtonsAvailability();
				frame.repaint();
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
				command.execute();
				frame.getTextArea().append(command.toString());
				controller.getUndoStack().push(command);
				selectedShapeList.remove(temp);
				controller.changeButtonsAvailability();
				frame.repaint();
				break;
			}
		}	
	}
	
	public void readPointMoveTo(String commandLog, String[] values) {
		Color color = new Color(Integer.parseInt("-"+(values[2])));
		Point p = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]), color);

		for (Shape s : model.getShapes()) {
			if (s.toString().equals(p.toString())) {
				int position = model.getShapes().indexOf(s);
				if (commandLog.equals("Move to back")) {
					command = new ToBackCmd(model, (Point) s, position);
					command.execute();
					frame.getTextArea().append(command.toString());
					controller.getUndoStack().push(command);
					controller.getRedoStack().clear();
				}
				else if (commandLog.equals("Move to front")){
					command = new ToFrontCmd(model, (Point) s, position);
					command.execute();
					frame.getTextArea().append(command.toString());
					controller.getUndoStack().push(command);
					controller.getRedoStack().clear();
				}
				else if (commandLog.equals("Bring to back")){
					command = new BringToBackCmd(model, (Point) s, position);
					command.execute();
					frame.getTextArea().append(command.toString());
					controller.getUndoStack().push(command);
					controller.getRedoStack().clear();
				}
				else if (commandLog.equals("Bring to front")){
					command = new BringToFrontCmd(model, (Point) s, position);
					command.execute();
					frame.getTextArea().append(command.toString());
					controller.getUndoStack().push(command);
					controller.getRedoStack().clear();
				}
				controller.changeButtonsAvailability();
				frame.repaint();
				break;
			}
		}
	}
	
	public void readLineMoveTo(String commandLog, String[] values) {

		Point start = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
		Point end = new Point(Integer.parseInt(values[2]), Integer.parseInt(values[3]));
		Color color = new Color(Integer.parseInt("-"+(values[4])));

		Line l = new Line(start, end, color);

		for (Shape s : model.getShapes()) {
			if (s.toString().equals(l.toString())) {
				int position = model.getShapes().indexOf(s);
				if (commandLog.equals("Move to back")) {
					command = new ToBackCmd(model, (Line) s, position);
					command.execute();
					frame.getTextArea().append(command.toString());
					controller.getUndoStack().push(command);
					controller.getRedoStack().clear();
				}
				else if (commandLog.equals("Move to front")){
					command = new ToFrontCmd(model, (Line) s, position);
					command.execute();
					frame.getTextArea().append(command.toString());
					controller.getUndoStack().push(command);
					controller.getRedoStack().clear();
				}
				else if (commandLog.equals("Bring to back")){
					command = new BringToBackCmd(model, (Line) s, position);
					command.execute();
					frame.getTextArea().append(command.toString());
					controller.getUndoStack().push(command);
					controller.getRedoStack().clear();
				}
				else if (commandLog.equals("Bring to front")){
					command = new BringToFrontCmd(model, (Line) s, position);
					command.execute();
					frame.getTextArea().append(command.toString());
					controller.getUndoStack().push(command);
					controller.getRedoStack().clear();
				}
				controller.changeButtonsAvailability();
				frame.repaint();
				break;
			}
		}
	}
	
	public void readRectangleMoveTo(String commandLog, String[] values) {
		Point start = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
		int height = Integer.parseInt(values[2]);
		int width = Integer.parseInt(values[3]);
		Color color = new Color(Integer.parseInt("-"+(values[4])));
		Color innerColor = new Color(Integer.parseInt("-"+(values[5])));

		Rectangle r = new Rectangle(start, height, width, innerColor, color);
		for (Shape s : model.getShapes()) {
			if (s.toString().equals(r.toString())) {
				int position = model.getShapes().indexOf(s);
				if (commandLog.equals("Move to back")) {
					command = new ToBackCmd(model, (Rectangle) s, position);
					command.execute();
					frame.getTextArea().append(command.toString());
					controller.getUndoStack().push(command);
					controller.getRedoStack().clear();
				}
				else if (commandLog.equals("Move to front")){
					command = new ToFrontCmd(model, (Rectangle) s, position);
					command.execute();
					frame.getTextArea().append(command.toString());
					controller.getUndoStack().push(command);
					controller.getRedoStack().clear();
				}
				else if (commandLog.equals("Bring to back")){
					command = new BringToBackCmd(model, (Rectangle) s, position);
					command.execute();
					frame.getTextArea().append(command.toString());
					controller.getUndoStack().push(command);
					controller.getRedoStack().clear();
				}
				else if (commandLog.equals("Bring to front")){
					command = new BringToFrontCmd(model, (Rectangle) s, position);
					command.execute();
					frame.getTextArea().append(command.toString());
					controller.getUndoStack().push(command);
					controller.getRedoStack().clear();
				}
				controller.changeButtonsAvailability();
				frame.repaint();
				break;
			}
		}
	}
	
	public void readCircleMoveTo(String commandLog, String[] values) {
		Point center = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
		int radius = Integer.parseInt(values[2]);
		Color color = new Color(Integer.parseInt("-"+(values[4])));
		Color innerColor = new Color(Integer.parseInt("-"+(values[3])));

		Circle c = new Circle(center, radius, color, innerColor);

		for (Shape s : model.getShapes()) {
			if (s.toString().equals(c.toString())) {
				int position = model.getShapes().indexOf(s);
				if (commandLog.equals("Move to back")) {
					command = new ToBackCmd(model, (Circle) s, position);
					command.execute();
					frame.getTextArea().append(command.toString());
					controller.getUndoStack().push(command);
					controller.getRedoStack().clear();
				}
				else if (commandLog.equals("Move to front")){
					command = new ToFrontCmd(model, (Circle) s, position);
					command.execute();
					frame.getTextArea().append(command.toString());
					controller.getUndoStack().push(command);
					controller.getRedoStack().clear();
				}
				else if (commandLog.equals("Bring to back")){
					command = new BringToBackCmd(model, (Circle) s, position);
					command.execute();
					frame.getTextArea().append(command.toString());
					controller.getUndoStack().push(command);
					controller.getRedoStack().clear();
				}
				else if (commandLog.equals("Bring to front")){
					command = new BringToFrontCmd(model, (Circle) s, position);
					command.execute();
					frame.getTextArea().append(command.toString());
					controller.getUndoStack().push(command);
					controller.getRedoStack().clear();
				}
				controller.changeButtonsAvailability();
				frame.repaint();
				break;
			}
		}	
	}
	
	public void readDonutMoveTo(String commandLog, String[] values) {
		Point center = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
		int radius = Integer.parseInt(values[2]);
		int innerRadius = Integer.parseInt(values[3]);
		Color color = new Color(Integer.parseInt("-"+(values[5])));
		Color innerColor = new Color(Integer.parseInt("-"+(values[4])));

		Donut d = new Donut(center, radius, innerRadius, color, innerColor);

		for (Shape s : model.getShapes()) {
			int position = model.getShapes().indexOf(s);
			if (s.toString().equals(d.toString())) {
				if (commandLog.equals("Move to back")) {
					command = new ToBackCmd(model, (Donut) s, position);
					command.execute();
					frame.getTextArea().append(command.toString());
					controller.getUndoStack().push(command);
					controller.getRedoStack().clear();
				}
				else if (commandLog.equals("Move to front")){
					command = new ToFrontCmd(model, (Donut) s, position);
					command.execute();
					frame.getTextArea().append(command.toString());
					controller.getUndoStack().push(command);
					controller.getRedoStack().clear();
				}
				else if (commandLog.equals("Bring to back")){
					command = new BringToBackCmd(model, (Donut) s, position);
					command.execute();
					frame.getTextArea().append(command.toString());
					controller.getUndoStack().push(command);
					controller.getRedoStack().clear();
				}
				else if (commandLog.equals("Bring to front")){
					command = new BringToFrontCmd(model, (Donut) s, position);
					command.execute();
					frame.getTextArea().append(command.toString());
					controller.getUndoStack().push(command);
					controller.getRedoStack().clear();
				}
				controller.changeButtonsAvailability();
				frame.repaint();
				break;
			}
		}
	}
	
	public void readHexagonMoveTo(String commandLog, String[] values) {
		int x = Integer.parseInt(values[0]);
		int y = Integer.parseInt(values[1]);
		int r = Integer.parseInt(values[2]);
		Point p = new Point(x , y);
		HexagonAdapter h = new HexagonAdapter(p, r, new Color(Integer.parseInt("-"+(values[4]))), new Color(Integer.parseInt("-"+(values[3]))));
		
		for (Shape s : model.getShapes()) {
			if (s.toString().equals(h.toString())) {
				int position = model.getShapes().indexOf(s);
				if (commandLog.equals("Move to back")) {
					command = new ToBackCmd(model, (HexagonAdapter) s, position);
					command.execute();
					frame.getTextArea().append(command.toString());
					controller.getUndoStack().push(command);
					controller.getRedoStack().clear();
				}
				else if (commandLog.equals("Move to front")){
					command = new ToFrontCmd(model, (HexagonAdapter) s, position);
					command.execute();
					frame.getTextArea().append(command.toString());
					controller.getUndoStack().push(command);
					controller.getRedoStack().clear();
				}
				else if (commandLog.equals("Bring to back")){
					command = new BringToBackCmd(model, (HexagonAdapter) s, position);
					command.execute();
					frame.getTextArea().append(command.toString());
					controller.getUndoStack().push(command);
					controller.getRedoStack().clear();
				}
				else if (commandLog.equals("Bring to front")){
					command = new BringToFrontCmd(model, (HexagonAdapter) s, position);
					command.execute();
					frame.getTextArea().append(command.toString());
					controller.getUndoStack().push(command);
					controller.getRedoStack().clear();
				}
				controller.changeButtonsAvailability();
				frame.repaint();
				break;
			}
		}
	}
	
}
