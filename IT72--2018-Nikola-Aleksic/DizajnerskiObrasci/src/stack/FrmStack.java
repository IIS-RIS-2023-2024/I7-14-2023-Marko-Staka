package stack;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import geometry.Point;
import geometry.Rectangle;

public class FrmStack extends JFrame {

	private JPanel contentPane;
	DefaultListModel<Rectangle> stack = new DefaultListModel<Rectangle>();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmStack frame = new FrmStack();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public FrmStack() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		setTitle("Aleksic Nikola IT72/2018");
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		JPanel pnlLower = new JPanel();
		contentPane.add(pnlLower, BorderLayout.SOUTH);

		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DlgRectangle dlg = new DlgRectangle();
				dlg.setVisible(true);

				if (dlg.isOk) {
					Rectangle r1 = new Rectangle(
							new Point(Integer.parseInt(dlg.getTxtX().getText().toString()),
									Integer.parseInt(dlg.getTxtY().getText().toString())),
							Integer.parseInt(dlg.getTxtHeight().getText().toString()),
							Integer.parseInt(dlg.getTxtWidth().getText().toString()));
					stack.add(0, r1);
				}
			}
		});
		pnlLower.add(btnAdd);

		JButton btnDelete = new JButton("Delete");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Rectangle temp = stack.firstElement();

				DlgRectangle dlg = new DlgRectangle();

				dlg.getTxtX().setText(Integer.toString(temp.getUpperLeftPoint().getX()));
				dlg.getTxtY().setText(Integer.toString(temp.getUpperLeftPoint().getY()));
				dlg.getTxtHeight().setText(Integer.toString(temp.getHeight()));
				dlg.getTxtWidth().setText(Integer.toString(temp.getWidth()));

				dlg.getTxtX().setEditable(false);
				dlg.getTxtY().setEditable(false);
				dlg.getTxtHeight().setEditable(false);
				dlg.getTxtWidth().setEditable(false);

				dlg.setVisible(true);

				if (dlg.isOk) {
					stack.remove(0);
				}
			}
		});
		pnlLower.add(btnDelete);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		JList list = new JList();
		scrollPane.setViewportView(list);
		list.setModel(stack);

	}

}
