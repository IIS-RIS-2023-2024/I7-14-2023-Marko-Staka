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
import services.ReadLogLineService;
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
	private ReadLogLineService readLogLineService;
	
	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
		observerModify = new ObserverChange(frame);
		observer.addPropertyChangeListener(observerModify);
		readLogLineService = new ReadLogLineService(this.model, this.frame, this);
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
				readLogLine(line);
			} else {
				frame.getBtnCmdByCmd().setEnabled(false);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void readLogLine(String line) {
		readLogLineService.readLogLine(line);
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

	public Stack<Command> getUndoStack() {
		return undoStack;
	}

	public Stack<Command> getRedoStack() {
		return redoStack;
	}

}
