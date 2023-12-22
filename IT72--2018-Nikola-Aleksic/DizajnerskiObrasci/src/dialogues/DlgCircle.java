package dialogues;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import geometry.Circle;
import geometry.Point;

public class DlgCircle extends JDialog {

	private final JPanel contentPanel = new JPanel();

	public boolean isOk;
	private JTextField txtX;
	private JTextField txtY;
	private JTextField txtRadius;
	private JColorChooser ccLineColor;
	private JColorChooser ccInnerColor;

	public static void main(String[] args) {
		try {
			DlgCircle dialog = new DlgCircle();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public DlgCircle() {
		setModal(true);
		setTitle("Enter/Modify Circle");
		setResizable(false);
		setBounds(100, 100, 650, 800);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		GridBagLayout gbl_contentPanel = new GridBagLayout();
		gbl_contentPanel.columnWidths = new int[] { 0, 0, 0, 0, 0, 0 };
		gbl_contentPanel.rowHeights = new int[] { 0, 0, 0, 0, 0, 0, 0 };
		gbl_contentPanel.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 1.0, Double.MIN_VALUE };
		gbl_contentPanel.rowWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE };
		contentPanel.setLayout(gbl_contentPanel);
		{
			JLabel lblX = new JLabel("X coordinate of center:");
			GridBagConstraints gbc_lblX = new GridBagConstraints();
			gbc_lblX.insets = new Insets(0, 0, 5, 5);
			gbc_lblX.gridx = 2;
			gbc_lblX.gridy = 1;
			contentPanel.add(lblX, gbc_lblX);
		}
		{
			txtX = new JTextField();
			GridBagConstraints gbc_txtX = new GridBagConstraints();
			gbc_txtX.insets = new Insets(0, 0, 5, 0);
			gbc_txtX.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtX.gridx = 4;
			gbc_txtX.gridy = 1;
			contentPanel.add(txtX, gbc_txtX);
			txtX.setColumns(10);
		}
		{
			JLabel lblY = new JLabel("Y coordinate of center:");
			GridBagConstraints gbc_lblY = new GridBagConstraints();
			gbc_lblY.insets = new Insets(0, 0, 5, 5);
			gbc_lblY.gridx = 2;
			gbc_lblY.gridy = 2;
			contentPanel.add(lblY, gbc_lblY);
		}
		{
			txtY = new JTextField();
			GridBagConstraints gbc_txtY = new GridBagConstraints();
			gbc_txtY.insets = new Insets(0, 0, 5, 0);
			gbc_txtY.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtY.gridx = 4;
			gbc_txtY.gridy = 2;
			contentPanel.add(txtY, gbc_txtY);
			txtY.setColumns(10);
		}
		{
			JLabel lblRadius = new JLabel("Radius:");
			GridBagConstraints gbc_lblRadius = new GridBagConstraints();
			gbc_lblRadius.insets = new Insets(0, 0, 5, 5);
			gbc_lblRadius.gridx = 2;
			gbc_lblRadius.gridy = 3;
			contentPanel.add(lblRadius, gbc_lblRadius);
		}
		{
			txtRadius = new JTextField();
			GridBagConstraints gbc_txtRadius = new GridBagConstraints();
			gbc_txtRadius.insets = new Insets(0, 0, 5, 0);
			gbc_txtRadius.fill = GridBagConstraints.HORIZONTAL;
			gbc_txtRadius.gridx = 4;
			gbc_txtRadius.gridy = 3;
			contentPanel.add(txtRadius, gbc_txtRadius);
			txtRadius.setColumns(10);
		}
		{
			JLabel lblLineColor = new JLabel("Line color:");
			GridBagConstraints gbc_lblLineColor = new GridBagConstraints();
			gbc_lblLineColor.insets = new Insets(0, 0, 5, 5);
			gbc_lblLineColor.gridx = 2;
			gbc_lblLineColor.gridy = 4;
			contentPanel.add(lblLineColor, gbc_lblLineColor);
		}
		{
			ccLineColor = new JColorChooser();
			GridBagConstraints gbc_ccLineColor = new GridBagConstraints();
			gbc_ccLineColor.insets = new Insets(0, 0, 5, 0);
			gbc_ccLineColor.gridx = 4;
			gbc_ccLineColor.gridy = 4;
			contentPanel.add(ccLineColor, gbc_ccLineColor);
		}
		{
			JLabel lblInnerColor = new JLabel("Inner color:");
			GridBagConstraints gbc_lblInnerColor = new GridBagConstraints();
			gbc_lblInnerColor.insets = new Insets(0, 0, 5, 5);
			gbc_lblInnerColor.gridx = 2;
			gbc_lblInnerColor.gridy = 5;
			contentPanel.add(lblInnerColor, gbc_lblInnerColor);
		}
		{
			ccInnerColor = new JColorChooser();
			GridBagConstraints gbc_ccInnerColor = new GridBagConstraints();
			gbc_ccInnerColor.insets = new Insets(0, 0, 5, 0);
			gbc_ccInnerColor.gridx = 4;
			gbc_ccInnerColor.gridy = 5;
			contentPanel.add(ccInnerColor, gbc_ccInnerColor);
		}

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						if (txtX.getText().trim().isEmpty() || txtY.getText().trim().isEmpty()
								|| txtRadius.getText().trim().isEmpty()) {
							isOk = false;
							dispose();
						} else {
							isOk = true;
							setVisible(false);
						}
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	public JTextField getTxtX() {
		return txtX;
	}

	public void setTxtX(JTextField txtX) {
		this.txtX = txtX;
	}

	public JTextField getTxtY() {
		return txtY;
	}

	public void setTxtY(JTextField txtY) {
		this.txtY = txtY;
	}

	public JTextField getTxtRadius() {
		return txtRadius;
	}

	public void setTxtRadius(JTextField txtRadius) {
		this.txtRadius = txtRadius;
	}

	public JColorChooser getCcLineColor() {
		return ccLineColor;
	}

	public void setCcLineColor(JColorChooser ccLineColor) {
		this.ccLineColor = ccLineColor;
	}

	public JColorChooser getCcInnerColor() {
		return ccInnerColor;
	}

	public void setCcInnerColor(JColorChooser ccInnerColor) {
		this.ccInnerColor = ccInnerColor;
	}

	public Circle getCircle() {
		Circle circle = new Circle(
				new Point(Integer.parseInt(this.getTxtX().getText().toString()),
						Integer.parseInt(this.getTxtY().getText().toString())),
				Integer.parseInt(this.getTxtRadius().getText().toString()), false, this.ccLineColor.getColor(),
				this.ccInnerColor.getColor());
		return circle;
	}

	public void setCircle(Circle temp) {

		this.getTxtX().setText(Integer.toString(temp.getCenter().getX()));
		this.getTxtY().setText(Integer.toString(temp.getCenter().getY()));
		this.getTxtRadius().setText(Integer.toString(temp.getRadius()));
		this.getCcLineColor().setColor(temp.getColor());
		this.getCcInnerColor().setColor(temp.getInnerColor());

		temp = new Circle(
				new Point(Integer.parseInt(this.getTxtX().getText().toString()),
						Integer.parseInt(this.getTxtY().getText().toString())),
				Integer.parseInt(this.getTxtRadius().getText().toString()), false, this.ccLineColor.getColor(),
				this.ccInnerColor.getColor());
	}

	public boolean isCommited() {
		return this.isOk;
	}

}
