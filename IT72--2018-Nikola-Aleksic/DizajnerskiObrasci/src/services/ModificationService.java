package services;

import adapter.HexagonAdapter;
import command.Command;
import command.ModifyCircleCmd;
import command.ModifyDonutCmd;
import command.ModifyHexagonCmd;
import command.ModifyLineCmd;
import command.ModifyPointCmd;
import command.ModifyRectangleCmd;
import dialogues.DlgCircle;
import dialogues.DlgDonut;
import dialogues.DlgHexagon;
import dialogues.DlgLine;
import dialogues.DlgPoint;
import dialogues.DlgRectangle;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import mvc.DrawingController;

public class ModificationService {
	
	private DrawingController controller;
	private Command command;
	private DlgPoint dialogPoint;
	private DlgLine dialogLine;
	private DlgRectangle dialogRectangle;
	private DlgCircle dialogCircle;
	private DlgDonut dialogDonut;
	private DlgHexagon dialogHexagon;
	
	public ModificationService(DrawingController controller) {
		this.controller = controller;
	}
	
	public Command modify(Shape shapeForModify) {
		if (shapeForModify instanceof Point) 
			return modifyPoint();
		else if (shapeForModify instanceof Line) 
			return modifyLine();
		else if (shapeForModify instanceof Donut) 
			return modifyDonut();
		else if (shapeForModify instanceof Circle) 
			return modifyCircle();
		else if (shapeForModify instanceof Rectangle) 
			return modifyRectangle();
		else if (shapeForModify instanceof HexagonAdapter) 
			return modifyHexagon();	
		return command;
	}
		
	public Command modifyPoint() {
		Point oldPoint = (Point) controller.getSelectedShapeList().get(0);
	    if (dialogPoint == null) 
	    	dialogPoint = new DlgPoint();
	    dialogPoint.setModal(true);
	    dialogPoint.setPoint(oldPoint);
	    dialogPoint.setVisible(true);
		if (dialogPoint.isCommited()) 
			command = new ModifyPointCmd(oldPoint, dialogPoint.getPoint());
		return command;
	}
	
	public Command modifyLine() {
		Line oldLine = (Line) controller.getSelectedShapeList().get(0);
	    if (dialogLine == null) 
	    	dialogLine = new DlgLine();
	    dialogLine.setModal(true);
	    dialogLine.setLine(oldLine);
	    dialogLine.setVisible(true);
		if (dialogLine.isCommited()) 
			command = new ModifyLineCmd(oldLine, dialogLine.getLine());
		return command;
	}
	
	public Command modifyRectangle() {
		Rectangle oldRectangle = (Rectangle) controller.getSelectedShapeList().get(0);
	    if (dialogRectangle == null) 
	    	dialogRectangle = new DlgRectangle();
	    dialogRectangle.setModal(true);
	    dialogRectangle.setRectangle(oldRectangle);
	    dialogRectangle.setVisible(true);
		if (dialogRectangle.isCommited())
			command = new ModifyRectangleCmd(oldRectangle, dialogRectangle.getRectangle());
		return command;
	}
	
	public Command modifyCircle() {
		Circle oldCircle = (Circle) controller.getSelectedShapeList().get(0);
	    if (dialogCircle == null) 
	    	dialogCircle = new DlgCircle();
	    dialogCircle.setModal(true);
	    dialogCircle.setCircle(oldCircle);
	    dialogCircle.setVisible(true);
		if (dialogCircle.isCommited()) 
			command = new ModifyCircleCmd(oldCircle, dialogCircle.getCircle());
		return command;
	}
	
	public Command modifyDonut() {
		Donut oldDonut = (Donut) controller.getSelectedShapeList().get(0);
	    if (dialogDonut == null) 
	    	dialogDonut = new DlgDonut();
	    dialogDonut.setModal(true);
	    dialogDonut.setDonut(oldDonut);
	    dialogDonut.setVisible(true);
		if (dialogDonut.isCommited())
			command = new ModifyDonutCmd(oldDonut, dialogDonut.getDonut());
		return command;
	}
	
	public Command modifyHexagon() {
		HexagonAdapter oldHexagon = (HexagonAdapter) controller.getSelectedShapeList().get(0);
	    if (dialogHexagon == null) 
	    	dialogHexagon = new DlgHexagon();
	    dialogHexagon.setModal(true);
	    dialogHexagon.setHexagon(oldHexagon);
	    dialogHexagon.setVisible(true);
		if (dialogHexagon.isCommited())
			command = new ModifyHexagonCmd(oldHexagon, dialogHexagon.getHexagon());
		return command;
	}
	
	public void setDialogPoint(DlgPoint dialogPoint) {
		this.dialogPoint = dialogPoint;
	}
	
	public void setDialogLine(DlgLine dialogLine) {
		this.dialogLine = dialogLine;
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

}
