/**
 * 
 */
package fr.ecn.ombre.desktop;

import fr.ecn.common.desktop.Controller;
import fr.ecn.common.desktop.MainWindow;
import fr.ecn.common.desktop.utils.ImagePanel;

/**
 * @author jerome
 *
 */
public class HorizonController extends Controller {
	
	protected double yHorizon;

	/* (non-Javadoc)
	 * @see fr.ecn.common.desktop.Controller#init()
	 */
	@Override
	public void init() {
		if (this.getMainController().getImageInfos().getYHorizon() != null) {
			this.yHorizon = this.getMainController().getImageInfos().getYHorizon();
		} else {
			this.yHorizon = this.getMainController().getCalcImage().getHeight()/2;
		}
	}

	/* (non-Javadoc)
	 * @see fr.ecn.common.desktop.Controller#initDisplay()
	 */
	@Override
	public void initDisplay() {
		ImagePanel imagePanel = new HorizonImagePanel(this.getMainController().getAwtImage(), this);
		
		MainWindow mainWindow = getMainController().getMainWindow();
		mainWindow.setImagePanel(imagePanel);
		mainWindow.setOptionsPanel(new HorizonOptionsPanel(this));
	}

	/* (non-Javadoc)
	 * @see fr.ecn.common.desktop.Controller#next()
	 */
	@Override
	public void next() {
		this.getMainController().getImageInfos().setYHorizon(this.yHorizon);
		
		//TODO: Next controller
	}

	/**
	 * @return the yHorizon
	 */
	public double getyHorizon() {
		return yHorizon;
	}

	/**
	 * @param yHorizon the yHorizon to set
	 */
	public void setyHorizon(double yHorizon) {
		this.yHorizon = yHorizon;
	}

}
