package strategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import mvc.DrawingModel;

public class SaveDrawing implements Save {

	private DrawingModel model;
	
	public SaveDrawing(DrawingModel model) {
		this.model = model;
	}
	
	@Override
	public void save() {
		JFileChooser dlgSave = new JFileChooser(); 
		dlgSave.setDialogTitle("Save as");
		
		if (dlgSave.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) { 
			
			File file = new File(dlgSave.getSelectedFile().getAbsolutePath());

			if (file.exists()) { 
				JOptionPane.showMessageDialog(null, "File with the same name already exists!", "Error", JOptionPane.ERROR_MESSAGE);
			} else {
				FileOutputStream fileStream;
				ObjectOutputStream objectStream;
				
				try { 
					fileStream = new FileOutputStream(file);
					objectStream = new ObjectOutputStream(fileStream);

					objectStream.writeObject(model.getShapes());
					
					objectStream.close();
					fileStream.close();

				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}