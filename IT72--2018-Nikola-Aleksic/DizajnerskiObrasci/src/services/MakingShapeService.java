package services;

import java.awt.event.MouseEvent;

import javax.swing.JOptionPane;

import adapter.HexagonAdapter;
import dialogues.DlgCircle;
import dialogues.DlgDonut;
import dialogues.DlgHexagon;
import dialogues.DlgRectangle;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import mvc.DrawingFrame;

public class MakingShapeService {
	
	private DrawingFrame frame;
	private Point startPoint;
	private DlgRectangle dialogRectangle;
	private DlgCircle dialogCircle;
	private DlgDonut dialogDonut;
	private DlgHexagon dialogHexagon;
	
	public MakingShapeService(DrawingFrame frame) {
		this.frame = frame;
	}
	
	public Shape makeShape(MouseEvent e) {
		if (frame.gettglPoint().isSelected()) 
			return makePoint(e);
		else if (frame.gettglLine().isSelected()) 
			return makeLine(e);
		else if (frame.gettglRectangle().isSelected()) 
			return makeRectangle(e);
		else if (frame.gettglCircle().isSelected()) 
			return makeCircle(e);
		else if (frame.gettglDonut().isSelected()) 
			return makeDonut(e);
		else if (frame.gettglHexagon().isSelected()) 
			return makeHexagon(e);
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
