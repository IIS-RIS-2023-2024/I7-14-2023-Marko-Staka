package mvc;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DrawingFrame extends JFrame {
	
	private DrawingView view = new DrawingView();
	private DrawingController controller;
	
	private JPanel contentPane;
	
	private JButton btnModify;
	private JButton btnDelete;
	
	private JButton btnUndo;
	private JButton btnRedo;
	
	private JButton btnSaveDrawing;
	private JButton btnSaveLog;
	private JButton btnLoadDrawing;
	private JButton btnLoadLog;
	private JButton btnCmdByCmd;
	
	private JButton btnToBack;
	private JButton btnToFront;
	private JButton btnBringToBack;
	private JButton btnBringToFront;
	
	private JButton btnColor;
	private JButton btnInnerColor;
	
	private JPanel pnlLower;
	private JPanel pnlUpper;
	private JPanel pnlLog;
	private JPanel pnlMiddle;
	
	private JPanel pnlLowerRight;
	private JPanel pnlLowerRightUp;
	private JPanel pnlLowerRightMiddle;
	private JPanel pnlLowerRightDown;
	
	private ButtonGroup group;
	private JToggleButton tglPoint;
	private JToggleButton tglSelection;
	private JToggleButton tglLine;
	private JToggleButton tglCircle;
	private JToggleButton tglDonut;
	private JToggleButton tglRectangle;
	private JToggleButton tglHexagon;
	
	private JScrollPane scrollPane;
	private JTextArea txaLog;
	
	private Color color = Color.BLACK;
	private Color innerColor = Color.WHITE;

	public DrawingFrame() {

		
		view.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.mouseClicked(e);
			}
		});
		getContentPane().add(view, BorderLayout.CENTER);
		
		
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 523, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		pnlUpper = new JPanel();
		contentPane.add(pnlUpper, BorderLayout.NORTH);
		pnlMiddle = new JPanel();
		contentPane.add(pnlMiddle, BorderLayout.SOUTH);
		pnlLower = new JPanel();
		pnlMiddle.add(pnlLower, BorderLayout.NORTH);
		pnlLog = new JPanel();
		pnlLog.setBackground(Color.WHITE);
		pnlMiddle.add(pnlLog, BorderLayout.SOUTH);
		
		btnSaveDrawing = new JButton();
		btnSaveDrawing.setPreferredSize(new Dimension(34, 34));
		btnSaveDrawing.setToolTipText("Save Drawing");
		btnSaveDrawing.setIcon(new ImageIcon(DrawingFrame.class.getResource("/icons/diskette.png")));
		btnSaveDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.saveDrawing();
			}
		});
		pnlUpper.add(btnSaveDrawing);
		
		btnLoadDrawing = new JButton();
		btnLoadDrawing.setPreferredSize(new Dimension(34, 34));
		btnLoadDrawing.setToolTipText("Load drawing");
		btnLoadDrawing.setIcon(new ImageIcon(DrawingFrame.class.getResource("/icons/download.png")));
		btnLoadDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.loadDrawing();
			}
		});
		pnlUpper.add(btnLoadDrawing);

		pnlUpper.add(new JSeparator(SwingConstants.VERTICAL));
		pnlUpper.add(new JSeparator(SwingConstants.VERTICAL));
		pnlUpper.add(new JSeparator(SwingConstants.VERTICAL));
		
		btnUndo = new JButton();
		btnUndo.setPreferredSize(new Dimension(34, 34));
		btnUndo.setToolTipText("Undo");
		btnUndo.setIcon(new ImageIcon(DrawingFrame.class.getResource("/icons/undo-circular-arrow.png")));
		btnUndo.setEnabled(false);
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.undo();
			}
		});
		pnlUpper.add(btnUndo);

		btnRedo = new JButton();
		btnRedo.setPreferredSize(new Dimension(34, 34));
		btnRedo.setToolTipText("Redo");
		btnRedo.setIcon(new ImageIcon(DrawingFrame.class.getResource("/icons/redo-arrow-symbol.png")));
		btnRedo.setEnabled(false);
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.redo();
			}
		});
		pnlUpper.add(btnRedo);
		
		pnlUpper.add(new JSeparator(SwingConstants.VERTICAL));
		pnlUpper.add(new JSeparator(SwingConstants.VERTICAL));
		pnlUpper.add(new JSeparator(SwingConstants.VERTICAL));
		
		btnDelete = new JButton();
		btnDelete.setPreferredSize(new Dimension(34, 34));
		btnDelete.setToolTipText("Delete");
		btnDelete.setIcon(new ImageIcon(DrawingFrame.class.getResource("/icons/delete.png")));
		btnDelete.setEnabled(false);
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.delete();
			}
		});
		pnlUpper.add(btnDelete);

		btnModify = new JButton();
		btnModify.setPreferredSize(new Dimension(34, 34));
		btnModify.setToolTipText("Modify");
		btnModify.setIcon(new ImageIcon(DrawingFrame.class.getResource("/icons/draw.png")));
		btnModify.setEnabled(false);
		btnModify.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.modify();
			}
		});
		pnlUpper.add(btnModify);
		
		pnlUpper.add(new JSeparator(SwingConstants.VERTICAL));
		
		btnToBack = new JButton();
		btnToBack.setPreferredSize(new Dimension(34, 34));
		btnToBack.setToolTipText("To back");
		btnToBack.setIcon(new ImageIcon(DrawingFrame.class.getResource("/icons/send-to-back (1).png")));
		btnToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.toBack();
			}
		});
		pnlUpper.add(btnToBack);

		btnToFront = new JButton();
		btnToFront.setPreferredSize(new Dimension(34, 34));
		btnToFront.setToolTipText("To front");
		btnToFront.setIcon(new ImageIcon(DrawingFrame.class.getResource("/icons/bring.png")));
		btnToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.toFront();
			}
		});
		pnlUpper.add(btnToFront);
		
		btnBringToBack = new JButton();
		btnBringToBack.setPreferredSize(new Dimension(34, 34));
		btnBringToBack.setToolTipText("Bring to back");
		btnBringToBack.setIcon(new ImageIcon(DrawingFrame.class.getResource("/icons/send-to-back.png")));
		btnBringToBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.bringToBack();
			}
		});
		pnlUpper.add(btnBringToBack);

		btnBringToFront = new JButton();
		btnBringToFront.setPreferredSize(new Dimension(34, 34));
		btnBringToFront.setToolTipText("Bring to front");
		btnBringToFront.setIcon(new ImageIcon(DrawingFrame.class.getResource("/icons/bring-to-front.png")));
		btnBringToFront.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.bringToFront();
			}
		});
		pnlUpper.add(btnBringToFront);
		
		pnlUpper.add(new JSeparator(SwingConstants.VERTICAL));
		pnlUpper.add(new JSeparator(SwingConstants.VERTICAL));
		pnlUpper.add(new JSeparator(SwingConstants.VERTICAL));
		
		tglSelection = new JToggleButton();
		tglSelection.setPreferredSize(new Dimension(34, 34));
		tglSelection.setToolTipText("Select");
		tglSelection.setIcon(new ImageIcon(DrawingFrame.class.getResource("/icons/choose.png")));
		pnlUpper.add(tglSelection);

		tglPoint = new JToggleButton();
		tglPoint.setPreferredSize(new Dimension(34, 34));
		tglPoint.setToolTipText("Point");
		tglPoint.setIcon(new ImageIcon(DrawingFrame.class.getResource("/icons/add.png")));
		pnlUpper.add(tglPoint);

		tglLine = new JToggleButton();
		tglLine.setPreferredSize(new Dimension(34, 34));
		tglLine.setToolTipText("Line");
		tglLine.setIcon(new ImageIcon(DrawingFrame.class.getResource("/icons/divide-mathematical-sign.png")));
		pnlUpper.add(tglLine);

		tglCircle = new JToggleButton();
		tglCircle.setPreferredSize(new Dimension(34, 34));
		tglCircle.setToolTipText("Circle");
		tglCircle.setIcon(new ImageIcon(DrawingFrame.class.getResource("/icons/rec.png")));
		pnlUpper.add(tglCircle);

		tglDonut = new JToggleButton();
		tglDonut.setPreferredSize(new Dimension(34, 34));
		tglDonut.setToolTipText("Donut");
		tglDonut.setIcon(new ImageIcon(DrawingFrame.class.getResource("/icons/circle.png")));
		pnlUpper.add(tglDonut);

		tglRectangle = new JToggleButton();
		tglRectangle.setPreferredSize(new Dimension(34, 34));
		tglRectangle.setToolTipText("Rectangle");
		tglRectangle.setIcon(new ImageIcon(DrawingFrame.class.getResource("/icons/square.png")));
		pnlUpper.add(tglRectangle);
		
		tglHexagon = new JToggleButton();
		tglHexagon.setPreferredSize(new Dimension(34, 34));
		tglHexagon.setToolTipText("Hexagon");
		tglHexagon.setIcon(new ImageIcon(DrawingFrame.class.getResource("/icons/hexagon.png")));
		pnlUpper.add(tglHexagon);
		
		pnlUpper.add(new JSeparator(SwingConstants.VERTICAL));
		
		btnColor = new JButton();
		btnColor.setPreferredSize(new Dimension(34, 34));
		btnColor.setToolTipText("Border color");
		btnColor.setBackground(Color.BLACK);
		btnColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color temp = JColorChooser.showDialog(null, "Choose color", color);
				if (temp != null) {
					color = temp;
					btnColor.setBackground(color);
				}
			}
		});
		pnlUpper.add(btnColor);
		
		btnInnerColor = new JButton();
		btnInnerColor.setPreferredSize(new Dimension(34, 34));
		btnInnerColor.setToolTipText("Inner color");
		btnInnerColor.setBackground(Color.WHITE);
		btnInnerColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Color temp = JColorChooser.showDialog(null, "Choose color", innerColor);
				if (temp != null) {
					innerColor = temp;
					btnInnerColor.setBackground(innerColor);
				}
			}
		});
		pnlUpper.add(btnInnerColor);

		group = new ButtonGroup();
		group.add(tglCircle);
		group.add(tglDonut);
		group.add(tglLine);
		group.add(tglPoint);
		group.add(tglRectangle);
		group.add(tglSelection);
		group.add(tglHexagon);
		
		pnlUpper.add(new JSeparator(SwingConstants.VERTICAL));

		this.getContentPane().add(view, BorderLayout.CENTER);
		
		scrollPane = new JScrollPane();

		GroupLayout gl_pnlLog = new GroupLayout(pnlLog);
		gl_pnlLog.setHorizontalGroup(gl_pnlLog.createParallelGroup(Alignment.LEADING)
				.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 750, Short.MAX_VALUE));
		gl_pnlLog.setVerticalGroup(gl_pnlLog.createParallelGroup(Alignment.TRAILING)
				.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE));

		txaLog = new JTextArea();
		txaLog.setEditable(false);
		scrollPane.setViewportView(txaLog);
		pnlLog.setLayout(gl_pnlLog);
		pnlLower.add(pnlLog);
		
		pnlLower.add(new JSeparator(SwingConstants.VERTICAL));
		pnlLower.add(new JSeparator(SwingConstants.VERTICAL));
		
		pnlLowerRight = new JPanel(new GridLayout(3, 1));
		pnlLower.add(pnlLowerRight, BorderLayout.EAST);
		pnlLowerRightUp = new JPanel();
		pnlLowerRight.add(pnlLowerRightUp, BorderLayout.NORTH);
		pnlLowerRightMiddle = new JPanel();
		pnlLowerRight.add(pnlLowerRightMiddle, BorderLayout.CENTER);
		pnlLowerRightDown = new JPanel();
		pnlLowerRight.add(pnlLowerRightDown, BorderLayout.SOUTH);

		btnSaveLog = new JButton();
		btnSaveLog.setPreferredSize(new Dimension(34, 34));
		btnSaveLog.setToolTipText("Save log");
		btnSaveLog.setIcon(new ImageIcon(DrawingFrame.class.getResource("/icons/save.png")));
		btnSaveLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.saveLog();
			}
		});
		pnlLowerRightUp.add(btnSaveLog);
		
		btnLoadLog = new JButton();
		btnLoadLog.setPreferredSize(new Dimension(34, 34));
		btnLoadLog.setToolTipText("Load log");
		btnLoadLog.setIcon(new ImageIcon(DrawingFrame.class.getResource("/icons/file.png")));
		btnLoadLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.loadLog();
			}
		});
		pnlLowerRightMiddle.add(btnLoadLog);
		
		btnCmdByCmd = new JButton();
		btnCmdByCmd.setPreferredSize(new Dimension(34, 34));
		btnCmdByCmd.setToolTipText("Load command");
		btnCmdByCmd.setIcon(new ImageIcon(DrawingFrame.class.getResource("/icons/pencil.png")));
		btnCmdByCmd.setEnabled(false);
		btnCmdByCmd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.loadCmdByCmd();
			}
		});
		pnlLowerRightDown.add(btnCmdByCmd);
	}
	
	public JButton getBtnColor() {
		return btnColor;
	}

	public void setBtnColor(JButton btnColor) {
		this.btnColor = btnColor;
	}

	public JButton getBtnInnerColor() {
		return btnInnerColor;
	}

	public void setBtnInnerColor(JButton btnInnerColor) {
		this.btnInnerColor = btnInnerColor;
	}

	public DrawingView getView() {
		return view;
	}

	public void setController(DrawingController controller) {
		this.controller = controller;
	}

	public JToggleButton gettglSelection() {
		return tglSelection;
	}

	public JToggleButton gettglPoint() {
		return tglPoint;
	}

	public JToggleButton gettglLine() {
		return tglLine;
	}

	public JToggleButton gettglCircle() {
		return tglCircle;
	}

	public JToggleButton gettglDonut() {
		return tglDonut;
	}

	public JToggleButton gettglRectangle() {
		return tglRectangle;
	}
	
	public JToggleButton gettglHexagon() {
		return tglHexagon;
	}
	
	public JButton getBtnModify() {
		return btnModify;
	}

	public JButton getBtnDelete() {
		return btnDelete;
	}
	
	public JButton getBtnUndo() {
		return btnUndo;
	}

	public JButton getBtnRedo() {
		return btnRedo;
	}

	public JTextArea getTextArea() {
		return txaLog;
	}
	
	public JButton getBtnSaveDrawing() {
		return btnSaveDrawing;
	}

	public JButton getBtnSaveLog() {
		return btnSaveLog;
	}
	
	public JButton getBtnLoadDrawing() {
		return btnLoadDrawing;
	}

	public JButton getBtnLoadLog() {
		return btnLoadLog;
	}
	
	public JButton getBtnCmdByCmd() {
		return btnCmdByCmd;
	}
	
	public JButton getBtnToBack() {
		return btnToBack;
	}

	public JButton getBtnToFront() {
		return btnToFront;
	}
	
	public JButton getBtnBringToBack() {
		return btnBringToBack;
	}

	public JButton getBtnBringToFront() {
		return btnBringToFront;
	}
	
}
