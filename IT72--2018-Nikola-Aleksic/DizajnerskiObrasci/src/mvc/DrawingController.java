package mvc;

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
import java.util.Stack;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.filechooser.FileNameExtensionFilter;

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
	
	private FileReader fileReader;
	private BufferedReader bufferReader;
	private ReadLogLineService readLogLineService;
	private ModificationService modificationService;
	private MakingShapeService makingShapeService;
	private ButtonsCheckService buttonsCheckService;
	
	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
		readLogLineService = new ReadLogLineService(this.model, this.frame, this);
		modificationService = new ModificationService(this);
		makingShapeService = new MakingShapeService(this.frame);
		buttonsCheckService = new ButtonsCheckService(this.frame, this);
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
				changeButtonsAvailability();

				fileReader = new FileReader(file);
				bufferReader = new BufferedReader(fileReader);

				JOptionPane.showMessageDialog(null, "Log is loaded! Use load command button to draw!");
				frame.getBtnCmdByCmd().setEnabled(true);
				
			}
		} catch (Exception ex) {
			JOptionPane.showMessageDialog(null, "Error occured!", "Error", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	@SuppressWarnings("unchecked")
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
				changeButtonsAvailability();
				
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
		System.out.println("eeeee");
		readLogLineService.readLogLine(line);
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
			unselectShapes();
		}
		redoStack.clear();	
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
