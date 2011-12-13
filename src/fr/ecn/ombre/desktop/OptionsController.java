package fr.ecn.ombre.desktop;

import java.text.ParseException;

import javax.swing.JOptionPane;

import fr.ecn.common.desktop.Controller;
import fr.ecn.common.desktop.MainWindow;
import fr.ecn.common.desktop.utils.ImagePanel;
import fr.ecn.ombre.core.utils.ExifReader;

public class OptionsController extends Controller {
	
	protected OptionsOptionsPanel optionsPanel;

	@Override
	public void init() {
		ExifReader.readExif(this.getMainController().getImageInfos());
	}

	@Override
	public void initDisplay() {
		ImagePanel imagePanel = new ImagePanel(this.getMainController().getAwtImage());
		optionsPanel = new OptionsOptionsPanel(this);
		
		MainWindow mainWindow = getMainController().getMainWindow();
		mainWindow.setImagePanel(imagePanel);
		mainWindow.setOptionsPanel(optionsPanel);
	}

	@Override
	public void next() {
		try {
			this.optionsPanel.valid();
			
			this.getMainController().setController(
					new ResultController(this.optionsPanel.getDate(), this.optionsPanel
							.getShadowsOnWalls(), this.optionsPanel.getExpendToStreet()));
		} catch (ParseException e) {
			JOptionPane.showMessageDialog(this.getMainController().getMainWindow(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
		}
	}

}
