package fr.ecn.common.desktop.actions;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

import javax.swing.AbstractAction;
import javax.swing.JFileChooser;

import fr.ecn.common.core.imageinfos.ImageInfos;
import fr.ecn.common.desktop.MainController;

public class OpenAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MainController mainController;

	/**
	 * @param mainWindow
	 */
	public OpenAction(MainController mainController) {
		super("Open image");
		this.mainController = mainController;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		//Create a file chooser
		final JFileChooser fc = new JFileChooser();
		
		//In response to a button click:
		int returnVal = fc.showOpenDialog(this.mainController.getMainWindow());
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
			
			ImageInfos imageInfos = new ImageInfos();
			imageInfos.setPath(file.getPath());
			
			try {
				this.mainController.setImageInfos(imageInfos);
				this.mainController.setController(this.mainController.getApplication().getFistController());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
