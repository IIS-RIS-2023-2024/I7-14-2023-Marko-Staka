package sort;

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
import stack.DlgRectangle;

public class FrmSort extends JFrame {

	private JPanel contentPane;
	DefaultListModel<Rectangle> l1 = new DefaultListModel<Rectangle>();

	JList list;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmSort frame = new FrmSort();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public FrmSort() {
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

					if (l1.isEmpty() == true) {
						l1.add(0, r1);
					} else {
						for (int i = 0; i < l1.size(); i++) {
							if (r1.area() <= l1.elementAt(i).area()) {
								l1.add(i, r1);
								return;
							}
						}
						l1.addElement(r1);
					}
				}
			}
		});
		pnlLower.add(btnAdd);

		JButton btnDelete = new JButton("Delete all");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				l1.clear();
			}
		});
		pnlLower.add(btnDelete);

		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);

		list = new JList();
		scrollPane.setViewportView(list);
		list.setModel(l1);
	}

}
