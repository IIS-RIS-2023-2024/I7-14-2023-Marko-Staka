package mvc;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

import command.ModifyCircleCmd;
import command.ModifyDonutCmd;
import command.ModifyHexagonCmd;
import command.ModifyLineCmd;
import command.ModifyPointCmd;
import command.ModifyRectangleCmd;
import command.AddShapeCmd;
import command.BringToBackCmd;
import command.BringToFrontCmd;
import command.UnselectShapeCmd;
import command.RemoveShapeCmd;
import command.SelectShapeCmd;
import command.ToBackCmd;
import command.ToFrontCmd;
import command.Command;

import dialogues.DlgCircle;
import dialogues.DlgDonut;
import dialogues.DlgLine;
import dialogues.DlgPoint;
import dialogues.DlgRectangle;
import dialogues.DlgHexagon;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import observer.Observer;
import observer.ObserverChange;
import strategy.SaveDrawing;
import strategy.SaveLog;
import strategy.SaveManager;
import adapter.HexagonAdapter;

public class DrawingController {
	
	private DrawingModel model;
	private DrawingFrame frame;
	
	DlgPoint dialogPoint;
	DlgLine dialogLine;
	DlgRectangle dialogRectangle;
	DlgCircle dialogCircle;
	DlgDonut dialogDonut;
	DlgHexagon dialogHexagon;

	private Point startPoint;
	private Shape selectedShape;
	
	public ArrayList<Shape> selectedShapeList = new ArrayList<Shape>();
	public ArrayList<String> logList;

	public Stack<Command> undoStack = new Stack<Command>();
	public Stack<Command> redoStack = new Stack<Command>();
	
	public Command command;
	
	private Observer observer = new Observer();
	private ObserverChange observerModify;
	
	private FileReader fileReader;
	private BufferedReader bufferReader;

	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
		observerModify = new ObserverChange(frame);
		observer.addPropertyChangeListener(observerModify);
	}
	
	public void mouseClicked(MouseEvent e) {
		if (frame.gettglSelection().isSelected())
			buttonSelectionClick(e);
		else 
			drawShape(e);
		
		disableButtons();
		frame.repaint();
	}
	
	public void drawShape(MouseEvent e) {
		Shape newShape = makeShape(e);
		if(newShape != null) {
			command = new AddShapeCmd(model, newShape);
			command.execute();
			frame.getTextArea().append(command.toString());
			undoStack.push(command);
			redoStack.clear();
		}
	}
	
	public Shape makeShape(MouseEvent e) {
		if (frame.gettglPoint().isSelected()) {
			return makePoint(e);
		} else if (frame.gettglLine().isSelected()) {
			return makeLine(e);
		} else if (frame.gettglRectangle().isSelected()) {
			return makeRectangle(e);
		} else if (frame.gettglCircle().isSelected()) {
			return makeCircle(e);
		} else if (frame.gettglDonut().isSelected()) {
			return makeDonut(e);
		} else if (frame.gettglHexagon().isSelected()) {
			return makeHexagon(e);
		}
		return null;
	}	

	public Shape makePoint(MouseEvent e) {
		return new Point(e.getX(), e.getY(), frame.getBtnColor().getBackground());
	}
	
	public Shape makeLine(MouseEvent e) {
		if (startPoint == null) {
            startPoint = new Point(e.getX(), e.getY());
            return null;
        } else {
            Shape newLine = new Line(startPoint, new Point(e.getX(), e.getY()), frame.getBtnColor().getBackground());
            startPoint = null; 
            return newLine;
        }
	}
	
	public Shape makeRectangle(MouseEvent e) {
	    if (dialogRectangle == null) 
	    	dialogRectangle = new DlgRectangle();
	    dialogRectangle.setModal(true);
	    dialogRectangle.setRectangle(new Rectangle(new Point(e.getX(), e.getY()), -1, -1, frame.getBtnColor().getBackground(), frame.getBtnInnerColor().getBackground()));
	    dialogRectangle.setVisible(true);
		try {
			return dialogRectangle.getRectangle();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(frame, "Wrong data type", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}
	
	public Shape makeCircle(MouseEvent e) {
	    if (dialogCircle == null)
	    	dialogCircle = new DlgCircle();
		dialogCircle.setModal(true);
		dialogCircle.setCircle(new Circle(new Point(e.getX(), e.getY()), -1, frame.getBtnColor().getBackground(), frame.getBtnInnerColor().getBackground()));
		dialogCircle.setVisible(true);
		try {
			return dialogCircle.getCircle();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(frame, "Wrong data type", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}
	
	public Shape makeDonut(MouseEvent e) {
	    if (dialogDonut == null)
	    	dialogDonut = new DlgDonut();
		dialogDonut.setModal(true);
		dialogDonut.setDonut(new Donut(new Point(e.getX(), e.getY()), -1, -1, frame.getBtnColor().getBackground(), frame.getBtnInnerColor().getBackground()));
		dialogDonut.setVisible(true);
		try {
			return dialogDonut.getDonut();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(frame, "Wrong data type", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}
	
	public Shape makeHexagon(MouseEvent e) {
	    if (dialogHexagon == null)
	    	dialogHexagon = new DlgHexagon();
		dialogHexagon.setModal(true);
		dialogHexagon.setHexagon(new HexagonAdapter(new Point(e.getX(), e.getY()), -1, frame.getBtnColor().getBackground(), frame.getBtnInnerColor().getBackground()));
		dialogHexagon.setVisible(true);
		try {
			return dialogHexagon.getHexagon();
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(frame, "Wrong data type", "Error", JOptionPane.ERROR_MESSAGE);
		}
		return null;
	}
	
	public void modify() {
		if (selectedShapeList.get(0) instanceof Point) 
			modifyPoint();
		else if (selectedShapeList.get(0) instanceof Line) 
			modifyLine();
		else if (selectedShapeList.get(0) instanceof Donut) 
			modifyDonut();
		else if (selectedShapeList.get(0) instanceof Circle) 
			modifyCircle();
		else if (selectedShapeList.get(0) instanceof Rectangle) 
			modifyRectangle();
		else if (selectedShapeList.get(0) instanceof HexagonAdapter) 
			modifyHexagon();	

		command.execute();
		frame.getTextArea().append(command.toString());
		undoStack.push(command);
		redoStack.clear();
		disableButtons();
		frame.repaint();
	}

	public void modifyPoint() {
		Point oldPoint = (Point) selectedShapeList.get(0);
	    if (dialogPoint == null) 
	    	dialogPoint = new DlgPoint();
	    dialogPoint.setModal(true);
	    dialogPoint.setPoint(oldPoint);
	    dialogPoint.setVisible(true);
		if (dialogPoint.isCommited()) 
			command = new ModifyPointCmd(oldPoint, dialogPoint.getPoint());
	}
	
	public void modifyLine() {
		Line oldLine = (Line) selectedShapeList.get(0);
	    if (dialogLine == null) 
	    	dialogLine = new DlgLine();
	    dialogLine.setModal(true);
	    dialogLine.setLine(oldLine);
	    dialogLine.setVisible(true);
		if (dialogLine.isCommited()) 
			command = new ModifyLineCmd(oldLine, dialogLine.getLine());
	}
	
	public void modifyRectangle() {
		Rectangle oldRectangle = (Rectangle) selectedShapeList.get(0);
	    if (dialogRectangle == null) 
	    	dialogRectangle = new DlgRectangle();
	    dialogRectangle.setModal(true);
	    dialogRectangle.setRectangle(oldRectangle);
	    dialogRectangle.setVisible(true);
		if (dialogRectangle.isCommited())
			command = new ModifyRectangleCmd(oldRectangle, dialogRectangle.getRectangle());
	}
	
	public void modifyCircle() {
		Circle oldCircle = (Circle) selectedShapeList.get(0);
	    if (dialogCircle == null) 
	    	dialogCircle = new DlgCircle();
	    dialogCircle.setModal(true);
	    dialogCircle.setCircle(oldCircle);
	    dialogCircle.setVisible(true);
		if (dialogCircle.isCommited()) 
			command = new ModifyCircleCmd(oldCircle, dialogCircle.getCircle());
	}
	
	public void modifyDonut() {
		Donut oldDonut = (Donut) selectedShapeList.get(0);
	    if (dialogDonut == null) 
	    	dialogDonut = new DlgDonut();
	    dialogDonut.setModal(true);
	    dialogDonut.setDonut(oldDonut);
	    dialogDonut.setVisible(true);
		if (dialogDonut.isCommited())
			command = new ModifyDonutCmd(oldDonut, dialogDonut.getDonut());
	}
	
	public void modifyHexagon() {
		HexagonAdapter oldHexagon = (HexagonAdapter) selectedShapeList.get(0);
	    if (dialogHexagon == null) 
	    	dialogHexagon = new DlgHexagon();
	    dialogHexagon.setModal(true);
	    dialogHexagon.setHexagon(oldHexagon);
	    dialogHexagon.setVisible(true);
		if (dialogHexagon.isCommited())
			command = new ModifyHexagonCmd(oldHexagon, dialogHexagon.getHexagon());
		
	}
	
	public void delete() {
		if (selectedShapeList.get(0) != null) {
				Shape temp;
				int position;
				while(selectedShapeList.size() > 0) {
					temp = selectedShapeList.get(0);
					position = model.getShapes().indexOf(temp);
					command = new RemoveShapeCmd(model, temp, position);
					command.execute();
					frame.getTextArea().append(command.toString());
					undoStack.push(command);
					selectedShapeList.remove(temp);
				}
				redoStack.clear();
				disableButtons();
				frame.repaint();
		}
	}
	
	public void undo() {
		command = undoStack.peek();
		command.unexecute();
		frame.getTextArea().append("Undo: [ " + undoStack.peek().toString().replace("\n","") + " ]\n");
		frame.repaint();
		undoStack.pop();
		redoStack.push(command);
		disableButtons();
	}
	
	public void redo() {
		command = redoStack.peek();
		command.execute();
		frame.getTextArea().append("Redo: [ " + redoStack.peek().toString().replace("\n","") + " ]\n");
		frame.repaint();
		redoStack.pop();
		undoStack.push(command);
		disableButtons();
	}
	
	public void textToList(JTextArea txaArea) {
		String str = txaArea.getText().toString();
		String[] strSplit = str.split(System.lineSeparator());
		logList = new ArrayList<String>(Arrays.asList(strSplit));
	}
	
	public void saveLog() {
		textToList(frame.getTextArea());
		SaveManager manager = new SaveManager(new SaveLog(logList));
		manager.save();
	}
	
	public void saveDrawing() {
		SaveManager manager = new SaveManager(new SaveDrawing(model));
		manager.save();
	}
	
	public void loadLog() {
		try {

			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Load log");
			FileNameExtensionFilter fileNameExtensionFilter = new FileNameExtensionFilter(".txt", "txt");
			fileChooser.setAcceptAllFileFilterUsed(false);
			fileChooser.setFileFilter(fileNameExtensionFilter);

			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				File file = (fileChooser.getSelectedFile());

				model.getShapes().clear();
				
				redoStack.clear();
				undoStack.clear();
				disableButtons();

				fileReader = new FileReader(file);
				bufferReader = new BufferedReader(fileReader);

				JOptionPane.showMessageDialog(null, "Log is loaded! Use load command button to draw!");
				frame.getBtnCmdByCmd().setEnabled(true);
				
				/*String stringLine;
				
				frame.getTextArea().setText("");
				while ((stringLine = bufferReader.readLine()) != null) {
					frame.getTextArea().append(stringLine + "\n");
				}
				frame.getBtnCmdByCmd().setEnabled(true);
				
				frame.getView().repaint();*/
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Error occured!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void loadDrawing() {
		try {
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Load drawing");
			
			if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
				File file = (fileChooser.getSelectedFile());

				FileInputStream fileStream = new FileInputStream(file);
				ObjectInputStream objectStream = new ObjectInputStream(fileStream);

				model.getShapes().addAll((ArrayList<Shape>) objectStream.readObject());
				
				redoStack.clear();
				undoStack.clear();
				disableButtons();
				
				frame.getView().repaint();
			
				objectStream.close();
				fileStream.close();
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Error occured!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public void loadCmdByCmd() {
		String line;
		try {
			if ((line = bufferReader.readLine()) != null) { 
				readLine(line);
			} else {
				frame.getBtnCmdByCmd().setEnabled(false);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readLine(String line) {
		
		String[] lineElements = line.split(":"); 
		
		String valuesLine;
		
		if(lineElements[2] != null) {
			valuesLine = lineElements[2].replaceAll("[^0-9,.]", "");
		} else {
			valuesLine = lineElements[1].replaceAll("[^0-9,.]", "");
		}
																	
		String[] values = valuesLine.split(",");

		if (lineElements[0].equals("Add")) {
			if (lineElements[1].equals(" Point")) {
				Point p = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]),
						 new Color(Integer.parseInt("-"+(values[2]))));
				command = new AddShapeCmd(model, p);
				command.execute();
				frame.getTextArea().append(command.toString());
				undoStack.push(command);
				redoStack.clear();
			} else if (lineElements[1].equals(" Line")) {
				Point start = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
				Point end = new Point(Integer.parseInt(values[2]), Integer.parseInt(values[3]));

				Line l = new Line(start, end, new Color(Integer.parseInt("-"+(values[4]))));

				command = new AddShapeCmd(model, l);
				command.execute();
				frame.getTextArea().append(command.toString());
				undoStack.push(command);
				redoStack.clear();
			} else if (lineElements[1].equals(" Rectangle")) {
				Point upperLeft = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
				int height = Integer.parseInt(values[2]);
				int width = Integer.parseInt(values[3]);

				Rectangle r = new Rectangle(upperLeft, height, width, new Color(Integer.parseInt("-"+(values[5]))),
						new Color(Integer.parseInt("-"+(values[4]))));
				command = new AddShapeCmd(model, r);
				command.execute();
				frame.getTextArea().append(command.toString());
				undoStack.push(command);
				redoStack.clear();
			} else if (lineElements[1].equals(" Circle")) {

				Point center = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
				int radius = Integer.parseInt(values[2]);

				Circle c = new Circle(center, radius, new Color(Integer.parseInt("-"+(values[4]))), new Color(Integer.parseInt("-"+(values[3]))));
				command = new AddShapeCmd(model, c);
				command.execute();
				frame.getTextArea().append(command.toString());
				undoStack.push(command);
				redoStack.clear();
			} else if (lineElements[1].equals(" Donut")) {

				Point center = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
				int radius = Integer.parseInt(values[2]);
				int innerRadius = Integer.parseInt(values[3]);

				Donut d = new Donut(center, radius, innerRadius, new Color(Integer.parseInt("-"+(values[5]))), new Color(Integer.parseInt("-"+(values[4]))));
				command = new AddShapeCmd(model, d);
				command.execute();
				frame.getTextArea().append(command.toString());
				undoStack.push(command);
				redoStack.clear();
			} else if (lineElements[1].equals(" Hexagon")) {
				int x = Integer.parseInt(values[0]);
				int y = Integer.parseInt(values[1]);
				int r = Integer.parseInt(values[2]);
				Point p = new Point(x , y);
				HexagonAdapter h = new HexagonAdapter(p, r, new Color(Integer.parseInt("-"+(values[4]))), new Color(Integer.parseInt("-"+(values[3]))));
				command = new AddShapeCmd(model, h);
				command.execute();
				frame.getTextArea().append(command.toString());
				undoStack.push(command);
				redoStack.clear();
			}
		} else if(lineElements[0].equals("Select")){
			String shape= line.substring(8);
		
			for(Shape s : model.getShapes()) {
				if(s.toString().equals(shape)) {
					
					if(!s.isSelected()) {
						command = new SelectShapeCmd(this, s);
						command.execute();
						frame.getTextArea().append(command.toString());
						undoStack.push(command);
						redoStack.clear();
						disableButtons();
						frame.repaint();
						break;
					}
				}
			}
		} else if(lineElements[0].equals("Unselect")){
			String shape= line.substring(10);
			
			for(Shape s : model.getShapes()) {
				if(s.toString().equals(shape)) {
					
					if(s.isSelected()) {
						command = new UnselectShapeCmd(this, s);
						command.execute();
						frame.getTextArea().append(command.toString());
						undoStack.push(command);
						redoStack.clear();
						disableButtons();
						frame.repaint();
						break;
					}
				}
			}
		} else if (lineElements[0].equals("Modify")) {
			String newValuesLine = lineElements[3].replaceAll("[^0-9,.]", "");
			String[] newValues = newValuesLine.split(",");

			if (lineElements[1].equals(" Point")) {
				
				Point oldPoint = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]), true,
						new Color(Integer.parseInt("-"+(values[2]))));
				Point newPoint = new Point(Integer.parseInt(newValues[0]), Integer.parseInt(newValues[1]),
						new Color(Integer.parseInt("-"+(newValues[2]))));
				for (Shape s : model.getShapes()) {
					if (s.toString().equals(oldPoint.toString())) {
						command = new ModifyPointCmd((Point) s, newPoint);
						command.execute();
						frame.getTextArea().append(command.toString());
						undoStack.push(command);
						redoStack.clear();
						disableButtons();
						frame.repaint();
						break;
					}
				}
			} else if (lineElements[1].equals(" Line")) {

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
						undoStack.push(command);
						redoStack.clear();
						disableButtons();
						frame.repaint();
						break;
					}
				}
			} else if (lineElements[1].equals(" Rectangle")) {
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
						undoStack.push(command);
						redoStack.clear();
						disableButtons();
						frame.repaint();
						break;
					}
				}
			} else if (lineElements[1].equals(" Circle")) {

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
						undoStack.push(command);
						redoStack.clear();
						disableButtons();
						frame.repaint();
						break;
					}
				}
			} else if (lineElements[1].equals(" Donut")) {

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
						undoStack.push(command);
						redoStack.clear();
						disableButtons();
						frame.repaint();
						break;
					}
				}
			} else if (lineElements[1].equals(" Hexagon")) {
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
						undoStack.push(command);
						redoStack.clear();
						disableButtons();
						frame.repaint();
						break;
					}
				}
			}
		} else if (lineElements[0].equals("Delete")) {
			if (lineElements[1].equals(" Point")) {
				String shapeValues = lineElements[2].replaceAll("[^0-9,.]", "");
				String[] sValues = shapeValues.split(",");

				Point p = new Point(Integer.parseInt(sValues[0]), Integer.parseInt(sValues[1]), 
						true, new Color(Integer.parseInt("-"+(sValues[2]))));
				int position = Integer.parseInt(sValues[3]);
				
				for (Shape s : model.getShapes()) {
					if (s.toString().equals(p.toString())) {
						Shape temp = s;
						position = model.getShapes().indexOf(s);
						command = new RemoveShapeCmd(model, temp, position);
						command.execute();
						frame.getTextArea().append(command.toString());
						undoStack.push(command);
						selectedShapeList.remove(temp);
						disableButtons();
						frame.repaint();
						break;
					}
				}
			} else if (lineElements[1].equals(" Line")) {
				String shapeValues = lineElements[2].replaceAll("[^0-9,.]", "");
				String[] sValues = shapeValues.split(",");

				Point start = new Point(Integer.parseInt(sValues[0]), Integer.parseInt(sValues[1]));
				Point end = new Point(Integer.parseInt(sValues[2]), Integer.parseInt(sValues[3]));
				Color color = new Color(Integer.parseInt("-"+(sValues[4])));
				int position = Integer.parseInt(sValues[5]);
				
				Line l = new Line(start, end, color);
				for (Shape s : model.getShapes()) {
					if (s.toString().equals(l.toString())) {
						Shape temp = s;
						position = model.getShapes().indexOf(s);
						command = new RemoveShapeCmd(model, temp, position);
						command.execute();
						frame.getTextArea().append(command.toString());
						undoStack.push(command);
						selectedShapeList.remove(temp);
						disableButtons();
						frame.repaint();
						break;
					}
				}
			} else if (lineElements[1].equals(" Rectangle")) {
				String shapeValues = lineElements[2].replaceAll("[^0-9,.]", "");
				String[] sValues = shapeValues.split(",");

				Point start = new Point(Integer.parseInt(sValues[0]), Integer.parseInt(sValues[1]));
				int height = Integer.parseInt(sValues[2]);
				int width = Integer.parseInt(sValues[3]);
				Color color = new Color(Integer.parseInt("-"+(sValues[5])));
				Color innerColor = new Color(Integer.parseInt("-"+(sValues[4])));
				int position = Integer.parseInt(sValues[6]);
				
				Rectangle r = new Rectangle(start, height, width, color, innerColor);
				for (Shape s : model.getShapes()) {
					if (s.toString().equals(r.toString())) {
						Shape temp = s;
						position = model.getShapes().indexOf(s);
						command = new RemoveShapeCmd(model, temp, position);
						command.execute();
						frame.getTextArea().append(command.toString());
						undoStack.push(command);
						selectedShapeList.remove(temp);
						disableButtons();
						frame.repaint();
						break;
					}
				}
			} else if (lineElements[1].equals(" Circle")) {
				String shapeValues = lineElements[2].replaceAll("[^0-9,.]", "");
				String[] sValues = shapeValues.split(",");

				Point center = new Point(Integer.parseInt(sValues[0]), Integer.parseInt(sValues[1]));
				int radius = Integer.parseInt(sValues[2]);
				Color color = new Color(Integer.parseInt("-"+(sValues[4])));
				Color innerColor = new Color(Integer.parseInt("-"+(sValues[3])));
				int position = Integer.parseInt(sValues[5]);

				Circle c = new Circle(center, radius, color, innerColor);
				for (Shape s : model.getShapes()) {
					if (s.toString().equals(c.toString())) {
						Shape temp = s;
						position = model.getShapes().indexOf(s);
						command = new RemoveShapeCmd(model, temp, position);
						command.execute();
						frame.getTextArea().append(command.toString());
						undoStack.push(command);
						selectedShapeList.remove(temp);
						disableButtons();
						frame.repaint();
						break;
					}
				}
			} else if (lineElements[1].equals(" Donut")) {
				String shapeValues = lineElements[2].replaceAll("[^0-9,.]", "");
				String[] sValues = shapeValues.split(",");

				Point center = new Point(Integer.parseInt(sValues[0]), Integer.parseInt(sValues[1]));
				int radius = Integer.parseInt(sValues[2]);
				int innerRadius = Integer.parseInt(sValues[3]);
				Color color = new Color(Integer.parseInt("-"+(sValues[5])));
				Color innerColor = new Color(Integer.parseInt("-"+(sValues[4])));
				int position = Integer.parseInt(sValues[6]);

				Donut d = new Donut(center, radius, innerRadius, color, innerColor);
				for (Shape s : model.getShapes()) {
					if (s.toString().equals(d.toString())) {
						Shape temp = s;
						position = model.getShapes().indexOf(s);
						command = new RemoveShapeCmd(model, temp, position);
						command.execute();
						frame.getTextArea().append(command.toString());
						undoStack.push(command);
						selectedShapeList.remove(temp);
						disableButtons();
						frame.repaint();
						break;
					}
				}
			} else if (lineElements[1].equals(" Hexagon")) {
				String shapeValues = lineElements[2].replaceAll("[^0-9,.]", "");
				String[] sValues = shapeValues.split(",");

				Color color = new Color(Integer.parseInt("-"+(sValues[4])));
				Color innerColor = new Color(Integer.parseInt("-"+(sValues[3])));
				int position = Integer.parseInt(sValues[5]);

				Point p = new Point(Integer.parseInt(values[0]) , Integer.parseInt(values[1]));
				HexagonAdapter h = new HexagonAdapter(p , Integer.parseInt(sValues[2]), color, innerColor);
				for (Shape s : model.getShapes()) {
					if (s.toString().equals(h.toString())) {
						Shape temp = s;
						position = model.getShapes().indexOf(s);
						command = new RemoveShapeCmd(model, temp, position);
						command.execute();
						frame.getTextArea().append(command.toString());
						undoStack.push(command);
						selectedShapeList.remove(temp);
						disableButtons();
						frame.repaint();
						break;
					}
				}
			}
		} else if ((lineElements[0].equals("Move to back")) || (lineElements[0].equals("Move to front"))
				|| (lineElements[0].equals("Bring to back")) || (lineElements[0].equals("Bring to front"))) {

			if (lineElements[1].equals(" Point")) {
				Color color = new Color(Integer.parseInt("-"+(values[2])));
				Point p = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]), color);
				//int position = Integer.parseInt(values[3]);

				for (Shape s : model.getShapes()) {
					if (s.toString().equals(p.toString())) {
						int position = model.getShapes().indexOf(s);
						if (lineElements[0].equals("Move to back")) {
							command = new ToBackCmd(model, (Point) s, position);
							command.execute();
							frame.getTextArea().append(command.toString());
							undoStack.push(command);
							redoStack.clear();
						}
						else if (lineElements[0].equals("Move to front")){
							command = new ToFrontCmd(model, (Point) s, position);
							command.execute();
							frame.getTextArea().append(command.toString());
							undoStack.push(command);
							redoStack.clear();
						}
						else if (lineElements[0].equals("Bring to back")){
							command = new BringToBackCmd(model, (Point) s, position);
							command.execute();
							frame.getTextArea().append(command.toString());
							undoStack.push(command);
							redoStack.clear();
						}
						else if (lineElements[0].equals("Bring to front")){
							command = new BringToFrontCmd(model, (Point) s, position);
							command.execute();
							frame.getTextArea().append(command.toString());
							undoStack.push(command);
							redoStack.clear();
						}
						disableButtons();
						frame.repaint();
						break;
					}
				}
			} else if (lineElements[1].equals(" Line")) {

				Point start = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
				Point end = new Point(Integer.parseInt(values[2]), Integer.parseInt(values[3]));
				Color color = new Color(Integer.parseInt("-"+(values[4])));
				//int position = Integer.parseInt(values[5]);

				Line l = new Line(start, end, color);

				for (Shape s : model.getShapes()) {
					if (s.toString().equals(l.toString())) {
						int position = model.getShapes().indexOf(s);
						if (lineElements[0].equals("Move to back")) {
							command = new ToBackCmd(model, (Line) s, position);
							command.execute();
							frame.getTextArea().append(command.toString());
							undoStack.push(command);
							redoStack.clear();
						}
						else if (lineElements[0].equals("Move to front")){
							command = new ToFrontCmd(model, (Line) s, position);
							command.execute();
							frame.getTextArea().append(command.toString());
							undoStack.push(command);
							redoStack.clear();
						}
						else if (lineElements[0].equals("Bring to back")){
							command = new BringToBackCmd(model, (Line) s, position);
							command.execute();
							frame.getTextArea().append(command.toString());
							undoStack.push(command);
							redoStack.clear();
						}
						else if (lineElements[0].equals("Bring to front")){
							command = new BringToFrontCmd(model, (Line) s, position);
							command.execute();
							frame.getTextArea().append(command.toString());
							undoStack.push(command);
							redoStack.clear();
						}
						disableButtons();
						frame.repaint();
						break;
					}
				}
			} else if (lineElements[1].equals(" Rectangle")) {

				Point start = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
				int height = Integer.parseInt(values[2]);
				int width = Integer.parseInt(values[3]);
				Color color = new Color(Integer.parseInt("-"+(values[4])));
				Color innerColor = new Color(Integer.parseInt("-"+(values[5])));
				//int position = Integer.parseInt(values[6]);

				Rectangle r = new Rectangle(start, height, width, innerColor, color);
				for (Shape s : model.getShapes()) {
					if (s.toString().equals(r.toString())) {
						int position = model.getShapes().indexOf(s);
						if (lineElements[0].equals("Move to back")) {
							command = new ToBackCmd(model, (Rectangle) s, position);
							command.execute();
							frame.getTextArea().append(command.toString());
							undoStack.push(command);
							redoStack.clear();
						}
						else if (lineElements[0].equals("Move to front")){
							command = new ToFrontCmd(model, (Rectangle) s, position);
							command.execute();
							frame.getTextArea().append(command.toString());
							undoStack.push(command);
							redoStack.clear();
						}
						else if (lineElements[0].equals("Bring to back")){
							command = new BringToBackCmd(model, (Rectangle) s, position);
							command.execute();
							frame.getTextArea().append(command.toString());
							undoStack.push(command);
							redoStack.clear();
						}
						else if (lineElements[0].equals("Bring to front")){
							command = new BringToFrontCmd(model, (Rectangle) s, position);
							command.execute();
							frame.getTextArea().append(command.toString());
							undoStack.push(command);
							redoStack.clear();
						}
						disableButtons();
						frame.repaint();
						break;
					}
				}
			} else if (lineElements[1].equals(" Circle")) {

				Point center = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
				int radius = Integer.parseInt(values[2]);
				Color color = new Color(Integer.parseInt("-"+(values[4])));
				Color innerColor = new Color(Integer.parseInt("-"+(values[3])));
				//int position = Integer.parseInt(values[5]);

				Circle c = new Circle(center, radius, color, innerColor);

				for (Shape s : model.getShapes()) {
					if (s.toString().equals(c.toString())) {
						int position = model.getShapes().indexOf(s);
						if (lineElements[0].equals("Move to back")) {
							command = new ToBackCmd(model, (Circle) s, position);
							command.execute();
							frame.getTextArea().append(command.toString());
							undoStack.push(command);
							redoStack.clear();
						}
						else if (lineElements[0].equals("Move to front")){
							command = new ToFrontCmd(model, (Circle) s, position);
							command.execute();
							frame.getTextArea().append(command.toString());
							undoStack.push(command);
							redoStack.clear();
						}
						else if (lineElements[0].equals("Bring to back")){
							command = new BringToBackCmd(model, (Circle) s, position);
							command.execute();
							frame.getTextArea().append(command.toString());
							undoStack.push(command);
							redoStack.clear();
						}
						else if (lineElements[0].equals("Bring to front")){
							command = new BringToFrontCmd(model, (Circle) s, position);
							command.execute();
							frame.getTextArea().append(command.toString());
							undoStack.push(command);
							redoStack.clear();
						}
						disableButtons();
						frame.repaint();
						break;
					}
				}
			} else if (lineElements[1].equals(" Donut")) {

				Point center = new Point(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
				int radius = Integer.parseInt(values[2]);
				int innerRadius = Integer.parseInt(values[3]);
				Color color = new Color(Integer.parseInt("-"+(values[5])));
				Color innerColor = new Color(Integer.parseInt("-"+(values[4])));
				//int position = Integer.parseInt(values[6]);

				Donut d = new Donut(center, radius, innerRadius, color, innerColor);

				for (Shape s : model.getShapes()) {
					int position = model.getShapes().indexOf(s);
					if (s.toString().equals(d.toString())) {
						if (lineElements[0].equals("Move to back")) {
							command = new ToBackCmd(model, (Donut) s, position);
							command.execute();
							frame.getTextArea().append(command.toString());
							undoStack.push(command);
							redoStack.clear();
						}
						else if (lineElements[0].equals("Move to front")){
							command = new ToFrontCmd(model, (Donut) s, position);
							command.execute();
							frame.getTextArea().append(command.toString());
							undoStack.push(command);
							redoStack.clear();
						}
						else if (lineElements[0].equals("Bring to back")){
							command = new BringToBackCmd(model, (Donut) s, position);
							command.execute();
							frame.getTextArea().append(command.toString());
							undoStack.push(command);
							redoStack.clear();
						}
						else if (lineElements[0].equals("Bring to front")){
							command = new BringToFrontCmd(model, (Donut) s, position);
							command.execute();
							frame.getTextArea().append(command.toString());
							undoStack.push(command);
							redoStack.clear();
						}
						disableButtons();
						frame.repaint();
						break;
					}
				}
			} else if (lineElements[1].equals(" Hexagon")) {

				int x = Integer.parseInt(values[0]);
				int y = Integer.parseInt(values[1]);
				int r = Integer.parseInt(values[2]);
				Point p = new Point(x , y);
				HexagonAdapter h = new HexagonAdapter(p, r, new Color(Integer.parseInt("-"+(values[4]))), new Color(Integer.parseInt("-"+(values[3]))));
				//int position = Integer.parseInt(values[5]);
				
				for (Shape s : model.getShapes()) {
					if (s.toString().equals(h.toString())) {
						int position = model.getShapes().indexOf(s);
						if (lineElements[0].equals("Move to back")) {
							command = new ToBackCmd(model, (HexagonAdapter) s, position);
							command.execute();
							frame.getTextArea().append(command.toString());
							undoStack.push(command);
							redoStack.clear();
						}
						else if (lineElements[0].equals("Move to front")){
							command = new ToFrontCmd(model, (HexagonAdapter) s, position);
							command.execute();
							frame.getTextArea().append(command.toString());
							undoStack.push(command);
							redoStack.clear();
						}
						else if (lineElements[0].equals("Bring to back")){
							command = new BringToBackCmd(model, (HexagonAdapter) s, position);
							command.execute();
							frame.getTextArea().append(command.toString());
							undoStack.push(command);
							redoStack.clear();
						}
						else if (lineElements[0].equals("Bring to front")){
							command = new BringToFrontCmd(model, (HexagonAdapter) s, position);
							command.execute();
							frame.getTextArea().append(command.toString());
							undoStack.push(command);
							redoStack.clear();
						}
						disableButtons();
						frame.repaint();
						break;
					}
				}
			}
		} else if (lineElements[0].equals("Undo") || lineElements[0].equals("Redo")) {
			if (lineElements[0].equals("Undo")) {
				undo();
			}
			else if (lineElements[0].equals("Redo")) {
				redo();
			}
		}
		disableButtons();
		frame.getView().repaint();
		
	}

	public void toBack() {
		Shape shape = selectedShapeList.get(0);
		int position = model.getShapes().indexOf(shape);
		
		command = new ToBackCmd(model, shape, position);
		command.execute();
		frame.getTextArea().append(command.toString());
		undoStack.push(command);
		redoStack.clear();
		
		disableButtons();
		frame.getView().repaint();
	}
	
	public void toFront() {
		Shape shape = selectedShapeList.get(0);
		int position = model.getShapes().indexOf(shape);
		
		command = new ToFrontCmd(model, shape, position);
		command.execute();
		frame.getTextArea().append(command.toString());
		undoStack.push(command);
		redoStack.clear();
		
		disableButtons();
		frame.getView().repaint();
		}
	
	public void bringToBack() {
		Shape shape = selectedShapeList.get(0);
		int position = model.getShapes().indexOf(shape);
		
		command = new BringToBackCmd(model, shape, position);
		command.execute();
		frame.getTextArea().append(command.toString());
		undoStack.push(command);
		redoStack.clear();
		
		disableButtons();
		frame.getView().repaint();
	}
	
	public void bringToFront() {
		Shape shape = selectedShapeList.get(0);
		int position = model.getShapes().indexOf(shape);
		
		command = new BringToFrontCmd(model, shape, position);
		command.execute();
		frame.getTextArea().append(command.toString());
		undoStack.push(command);
		redoStack.clear();
		
		disableButtons();
		frame.getView().repaint();
	}
	
	public void disableButtons() {
		if (undoStack.isEmpty()) 
			frame.getBtnUndo().setEnabled(false);
		else 
			frame.getBtnUndo().setEnabled(true);

		if (redoStack.isEmpty())
			frame.getBtnRedo().setEnabled(false);
		else 
			frame.getBtnRedo().setEnabled(true);
		
		if (!selectedShapeList.isEmpty()) {
			if (selectedShapeList.size() == 1)
				observer.setBtnModify(true);
			else 
				observer.setBtnModify(false);
			observer.setBtnDelete(true);
		} else {
			observer.setBtnModify(false);
			observer.setBtnDelete(false);
		}
	}
	
	public void buttonSelectionClick(MouseEvent e) {
		Shape temp = null;
		selectedShape = null;

		Iterator<Shape> iterator = model.getShapes().iterator();
		while (iterator.hasNext()) {
			temp = iterator.next();
			if (temp.contains(e.getX(), e.getY())) {
				selectedShape = temp;
			}
		}

		if (selectedShape != null) {	
			Command command = selectedShape.isSelected() ? new UnselectShapeCmd(this, selectedShape) : new SelectShapeCmd(this, selectedShape);
			command.execute();
			frame.getTextArea().append(command.toString());
			undoStack.push(command);
			selectedShape = null;
		} else {
			UnselectShapes();
		}
		redoStack.clear();	
	}
	
	public void UnselectShapes() {
		Shape temp;
		while(selectedShapeList.size() > 0) {
			temp = selectedShapeList.get(0);
			command = new UnselectShapeCmd(this, temp);
			command.execute();
			frame.getTextArea().append(command.toString());
			undoStack.push(command);
			selectedShapeList.remove(temp);
		}
	}
	
	public void setDialogRectangle(DlgRectangle dialogRectangle) {
		this.dialogRectangle = dialogRectangle;
	}

	public void setDialogCircle(DlgCircle dialogCircle) {
		this.dialogCircle = dialogCircle;
	}

	public void setDialogDonut(DlgDonut dialogDonut) {
		this.dialogDonut = dialogDonut;
	}

	public void setDialogHexagon(DlgHexagon dialogHexagon) {
		this.dialogHexagon = dialogHexagon;
	}
	
	public void setDialogPoint(DlgPoint dialogPoint) {
		this.dialogPoint = dialogPoint;
	}

	public void setDialogLine(DlgLine dialogLine) {
		this.dialogLine = dialogLine;
	}

	public ArrayList<Shape> getSelectedShapeList() {
		return selectedShapeList;
	}
}
