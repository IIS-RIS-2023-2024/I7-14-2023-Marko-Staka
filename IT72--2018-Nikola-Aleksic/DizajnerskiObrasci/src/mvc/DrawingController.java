package mvc;

import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Stack;

import javax.swing.JTextArea;

import command.AddShapeCmd;
import command.BringToBackCmd;
import command.BringToFrontCmd;
import command.UnselectShapeCmd;
import command.RemoveShapeCmd;
import command.SelectShapeCmd;
import command.ToBackCmd;
import command.ToFrontCmd;
import command.Command;

import geometry.Shape;
import services.ButtonsCheckService;
import services.LoadFileService;
import services.MakingShapeService;
import services.ModificationService;
import services.ReadLogLineService;
import strategy.SaveDrawing;
import strategy.SaveLog;
import strategy.SaveManager;

public class DrawingController {
	
	private DrawingModel model;
	private DrawingFrame frame;

	private Shape selectedShape;
	
	public ArrayList<Shape> selectedShapeList = new ArrayList<Shape>();
	public ArrayList<String> logList;

	public Stack<Command> undoStack = new Stack<Command>();
	public Stack<Command> redoStack = new Stack<Command>();
	
	public Command command;
	
	private ReadLogLineService readLogLineService;
	private ModificationService modificationService;
	private MakingShapeService makingShapeService;
	private ButtonsCheckService buttonsCheckService;
	private LoadFileService loadFileService;
	
	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
		readLogLineService = new ReadLogLineService(this.model, this.frame, this);
		modificationService = new ModificationService(this);
		makingShapeService = new MakingShapeService(this.frame);
		buttonsCheckService = new ButtonsCheckService(this.frame, this);
		loadFileService = new LoadFileService(this.model, this.frame, this);
	}

	
	public void mouseClicked(MouseEvent e) {
		if (frame.gettglSelection().isSelected())
			buttonSelectionClick(e);
		else 
			drawShape(e);
	}
	
	public void drawShape(MouseEvent e) {
		Shape newShape = makingShapeService.makeShape(e);
		if(newShape != null) {
			command = new AddShapeCmd(model, newShape);
			commandExecute();
		}
	}

	public void modify() {
		Shape shapeForModify = selectedShapeList.get(0);
		command = modificationService.modify(shapeForModify);
		commandExecute();
	}

	public void delete() {
		Shape temp;
		int position;
		while(selectedShapeList.size() > 0) {
			temp = selectedShapeList.get(0);
			position = model.getShapes().indexOf(temp);
			command = new RemoveShapeCmd(model, temp, position);
			commandExecute();
			selectedShapeList.remove(temp);
		}
	}
	
	public void undo() {
		command = undoStack.peek();
		command.unexecute();
		frame.getTextArea().append("Undo: [ " + undoStack.peek().toString().replace("\n","") + " ]\n");
		frame.repaint();
		undoStack.pop();
		redoStack.push(command);
		changeButtonsAvailability();
	}
	
	public void redo() {
		command = redoStack.peek();
		command.execute();
		frame.getTextArea().append("Redo: [ " + redoStack.peek().toString().replace("\n","") + " ]\n");
		frame.repaint();
		redoStack.pop();
		undoStack.push(command);
		changeButtonsAvailability();
	}
	
	public void toBack() {
		Shape shape = selectedShapeList.get(0);
		int position = model.getShapes().indexOf(shape);
		
		command = new ToBackCmd(model, shape, position);
		commandExecute();
	}
	
	public void toFront() {
		Shape shape = selectedShapeList.get(0);
		int position = model.getShapes().indexOf(shape);
		
		command = new ToFrontCmd(model, shape, position);
		commandExecute();
		}
	
	public void bringToBack() {
		Shape shape = selectedShapeList.get(0);
		int position = model.getShapes().indexOf(shape);
		
		command = new BringToBackCmd(model, shape, position);
		commandExecute();
	}
	
	public void bringToFront() {
		Shape shape = selectedShapeList.get(0);
		int position = model.getShapes().indexOf(shape);
		
		command = new BringToFrontCmd(model, shape, position);
		commandExecute();
	}
	
	public void buttonSelectionClick(MouseEvent e) {
		Shape temp = null;
		selectedShape = null;
		Iterator<Shape> iterator = model.getShapes().iterator();
		while (iterator.hasNext()) {
			temp = iterator.next();
			if (temp.contains(e.getX(), e.getY()))
				selectedShape = temp;
		}
		if (selectedShape != null)	
			selectShapes();
		else
			unselectShapes();
	}
	
	public void selectShapes() {
		command = selectedShape.isSelected() ? new UnselectShapeCmd(this, selectedShape) : new SelectShapeCmd(this, selectedShape);
		commandExecute();
		selectedShape = null;
	}
	
	public void unselectShapes() {
		Shape temp;
		while(selectedShapeList.size() > 0) {
			temp = selectedShapeList.get(0);
			command = new UnselectShapeCmd(this, temp);
			commandExecute();
		}
	}
	
	public void commandExecute() {
		command.execute();
		frame.getTextArea().append(command.toString());
		undoStack.push(command);
		redoStack.clear();
		changeButtonsAvailability();
		frame.getView().repaint();
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
		loadFileService.loadLog();
	}
	
	public void loadDrawing() {
		loadFileService.loadDrawing();
	}
	
	public void loadCmdByCmd() {
		loadFileService.loadCmdByCmd();
	}
	
	public void readLogLine(String logLine) {
		readLogLineService.readLogLine(logLine);
	}
	
	public void changeButtonsAvailability() {
		buttonsCheckService.disableButtons();
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

	public ModificationService getModificationService() {
		return modificationService;
	}

	public MakingShapeService getMakingShapeService() {
		return makingShapeService;
	}
	
}