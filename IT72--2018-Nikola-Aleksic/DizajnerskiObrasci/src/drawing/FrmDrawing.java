package drawing;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

import dialogues.DlgCircle;
import dialogues.DlgDonut;
import dialogues.DlgLine;
import dialogues.DlgPoint;
import dialogues.DlgRectangle;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;

public class FrmDrawing extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton btnModify;
	private JButton btnDelete;
	private JPanel pnlLower;
	private JPanel pnlUpper;
	private ButtonGroup group;
	private JRadioButton rdbtnPoint;
	private JRadioButton rdbtnSelection;
	private JRadioButton rdbtnLine;
	private JRadioButton rdbtnCircle;
	private JRadioButton rdbtnDonut;
	private JRadioButton rdbtnRectangle;
	private PnlDrawing drawingPanel = new PnlDrawing(this);

	public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmDrawing frame = new FrmDrawing();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	protected void modify() {
		Shape selected = drawingPanel.getSelected();
		if (selected != null) {
			if (selected instanceof Point) {
				Point point = (Point) selected;
				DlgPoint dlg = new DlgPoint();
				dlg.setPoint(point);
				dlg.setVisible(true);
				if (dlg.isOk) {
					point = dlg.getPoint();
					drawingPanel.getShapes().remove(selected);
					drawingPanel.getShapes().add(point);
				}
			} else if (selected instanceof Line) {
				Line line = (Line) selected;
				DlgLine dlg = new DlgLine();
				dlg.setLine(line);
				dlg.setVisible(true);
				if (dlg.isOk) {
					line = dlg.getLine();
					drawingPanel.getShapes().remove(selected);
					drawingPanel.getShapes().add(line);
				}
			} else if (selected instanceof Donut) {
				Donut donut = (Donut) selected;
				DlgDonut dlg = new DlgDonut();
				dlg.setDonut(donut);
				dlg.setVisible(true);
				if (dlg.isOk) {
					donut = dlg.getDonut();
					drawingPanel.getShapes().remove(selected);
					drawingPanel.getShapes().add(donut);
				}
			} else if (selected instanceof Circle) {
				Circle circle = (Circle) selected;
				DlgCircle dlg = new DlgCircle();
				dlg.setCircle(circle);
				dlg.setVisible(true);
				if (dlg.isOk) {
					circle = dlg.getCircle();
					drawingPanel.getShapes().remove(selected);
					drawingPanel.getShapes().add(circle);
				}
			} else if (selected instanceof Rectangle) {
				Rectangle rectangle = (Rectangle) selected;
				DlgRectangle dlg = new DlgRectangle();
				dlg.setRectangle(rectangle);
				dlg.setVisible(true);
				if (dlg.isOk) {
					rectangle = dlg.getRectangle();
					drawingPanel.getShapes().remove(selected);
					drawingPanel.getShapes().add(rectangle);
				}
			}
		}
		selected.setSelected(false);
		drawingPanel.repaint();

	}

	protected void delete() {
		Shape selected = drawingPanel.getSelected();
		if (selected != null) {
			int response = JOptionPane.showConfirmDialog(this, "Are you sure you want to delete this object?", "",
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
			if (response == JOptionPane.YES_OPTION) {
				drawingPanel.getShapes().remove(selected);
			}
		}
		drawingPanel.repaint();
	}

	public FrmDrawing() {

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 523, 300);
		setTitle("Aleksic Nikola IT72/2018");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		pnlLower = new JPanel();
		contentPane.add(pnlLower, BorderLayout.SOUTH);
		pnlUpper = new JPanel();
		contentPane.add(pnlUpper, BorderLayout.NORTH);

		rdbtnSelection = new JRadioButton("Selection");
		pnlUpper.add(rdbtnSelection);

		rdbtnPoint = new JRadioButton("Point");
		pnlUpper.add(rdbtnPoint);

		rdbtnLine = new JRadioButton("Line");
		pnlUpper.add(rdbtnLine);

		rdbtnCircle = new JRadioButton("Circle");
		pnlUpper.add(rdbtnCircle);

		rdbtnDonut = new JRadioButton("Donut");
		pnlUpper.add(rdbtnDonut);

		rdbtnRectangle = new JRadioButton("Rectangle");
		pnlUpper.add(rdbtnRectangle);

		group = new ButtonGroup();
		group.add(rdbtnCircle);
		group.add(rdbtnDonut);
		group.add(rdbtnLine);
		group.add(rdbtnPoint);
		group.add(rdbtnRectangle);
		group.add(rdbtnSelection);

		this.getContentPane().add(drawingPanel, BorderLayout.CENTER);

		btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				delete();
			}
		});
		pnlLower.add(btnDelete);

		btnModify = new JButton("Modify");
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				modify();
			}
		});
		pnlLower.add(btnModify);
	}

	public JRadioButton getRdbtnSelection() {
		return rdbtnSelection;
	}

	public JRadioButton getRdbtnPoint() {
		return rdbtnPoint;
	}

	public JRadioButton getRdbtnLine() {
		return rdbtnLine;
	}

	public JRadioButton getRdbtnCircle() {
		return rdbtnCircle;
	}

	public JRadioButton getRdbtnDonut() {
		return rdbtnDonut;
	}

	public JRadioButton getRdbtnRectangle() {
		return rdbtnRectangle;
	}
}