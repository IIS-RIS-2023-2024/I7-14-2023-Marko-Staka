package strategy;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class SaveLog implements Save{
	
	private ArrayList<String> log;

	public SaveLog(ArrayList<String> log) {
		this.log = log;
	}

	@Override
	public void save() {
		JFileChooser dlgSave = new JFileChooser();
		dlgSave.setDialogTitle("Save as");
		
		if (dlgSave.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
			File file = new File(dlgSave.getSelectedFile().getAbsolutePath() + ".txt");

			if (file.exists()) {
				JOptionPane.showMessageDialog(null, "File with the same name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				try {
					PrintWriter writer = new PrintWriter(file);
					
					for (int i = 0; i < log.size(); i++) {
						writer.println(log.get(i));
					}
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}