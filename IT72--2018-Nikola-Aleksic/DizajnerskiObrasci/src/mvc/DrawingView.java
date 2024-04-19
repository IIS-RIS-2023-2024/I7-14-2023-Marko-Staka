package mvc;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Iterator;

import javax.swing.JPanel;

import geometry.Shape;

public class DrawingView extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private DrawingModel model = new DrawingModel();

	public void setModel(DrawingModel model) {
		this.model = model;
	}

	public DrawingModel getModel() {
		return model;
	}

	@Override
	public void paint(Graphics g) {
		this.setBackground(Color.WHITE);
		super.paint(g);
		if (model != null) {
			Iterator<Shape> it = model.getShapes().iterator();
			while (it.hasNext()) {
				it.next().draw(g);
			}
		}
	}
	
}
