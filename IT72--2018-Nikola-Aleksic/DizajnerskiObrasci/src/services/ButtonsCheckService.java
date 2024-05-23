package services;

import mvc.DrawingController;
import mvc.DrawingFrame;
import observer.Observer;
import observer.ObserverChange;

public class ButtonsCheckService {
	private DrawingFrame frame;
	private DrawingController controller;	
	private Observer observer = new Observer();
	private ObserverChange observerModify;
	
	public ButtonsCheckService(DrawingFrame frame, DrawingController controller) {
		this.frame = frame;
		this.controller = controller;		
		observerModify = new ObserverChange(frame);
		observer.addPropertyChangeListener(observerModify);
	}
	
	public void disableButtons() {
		checkUndoStackSize();
		checkRedoStackSize();
		checkSelectedShapeSize();
	}
	
	public void checkUndoStackSize() {
		if (controller.getUndoStack().isEmpty()) 
			frame.getBtnUndo().setEnabled(false);
		else 
			frame.getBtnUndo().setEnabled(true);
	}
	
	public void checkRedoStackSize() {
		if (controller.getRedoStack().isEmpty())
			frame.getBtnRedo().setEnabled(false);
		else 
			frame.getBtnRedo().setEnabled(true);
	}
	
	public void checkSelectedShapeSize()  {
		if (controller.getSelectedShapeList().isEmpty()) {
			buttonsSetDisable();
		} else {
			if (controller.getSelectedShapeList().size() == 1)
				buttonsSetEnable();
			else 
				observer.setBtnModify(false);
			observer.setBtnDelete(true);
		}
	}
	
	public void buttonsSetDisable() {
		observer.setBtnModify(false);
		observer.setBtnDelete(false);
		frame.getBtnBringToBack().setEnabled(false);
		frame.getBtnToBack().setEnabled(false);
		frame.getBtnBringToFront().setEnabled(false);
		frame.getBtnToBack().setEnabled(false);
	}
	
	public void buttonsSetEnable() {
		observer.setBtnModify(true);
		observer.setBtnDelete(true);
		frame.getBtnBringToBack().setEnabled(true);
		frame.getBtnToBack().setEnabled(true);
		frame.getBtnBringToFront().setEnabled(true);
		frame.getBtnToBack().setEnabled(true);
	}
	
}
