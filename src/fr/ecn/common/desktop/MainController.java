package fr.ecn.common.desktop;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Deque;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.SwingUtilities;

import fr.ecn.common.core.image.ColorImage;
import fr.ecn.common.core.imageinfos.ImageInfos;
import fr.ecn.common.desktop.utils.ImageUtils;

/**
 * Class that links View and controller
 * 
 * @author jerome
 *
 */
public class MainController {
	
	protected Application application;
	
	protected ImageInfos imageInfos;
	protected BufferedImage awtImage;
	protected ColorImage calcImage;
	
	protected MainWindow mainWindow;
	
	protected Controller controller = null;
	protected Deque<Controller> oldControllers = new LinkedList<Controller>();

	/**
	 * @param application
	 */
	public MainController(Application application) {
		super();
		this.application = application;
	}

	public void init() {
		SwingUtilities.invokeLater(new Runnable() {
			public void run(){
				mainWindow = new MainWindow(MainController.this);
				mainWindow.setVisible(true);
			}
		});
	}

	/**
	 * @param imageInfos the imageInfos to set
	 * @throws IOException if an error occurs during reading.
	 * 
	 * will set awtImage and calcImage according to the path in imageInfos
	 */
	public void setImageInfos(ImageInfos imageInfos) throws IOException {
		this.imageInfos = imageInfos;
		this.awtImage = ImageIO.read(new File(imageInfos.getPath()));
		this.calcImage = ImageUtils.fromAwt(this.awtImage);
	}
	
	/**
	 * @param controller the controller to set
	 */
	public void setController(Controller controller) {
		if (this.controller != null) {
			this.oldControllers.addFirst(this.controller);
		}
		
		this.controller = controller;
		this.controller.setMainController(this);
		
		this.initController();
	}
	
	/**
	 * Start the current controller initialization
	 */
	private void initController() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				MainController.this.controller.init();

				SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						MainController.this.controller.initDisplay();
					}
				});
			}
		}).start();
	}
	
	/**
	 * @return the application
	 */
	public Application getApplication() {
		return application;
	}

	public void back() {
		if (this.oldControllers.isEmpty()) {
			return;
		}
		
		this.controller = this.oldControllers.removeFirst();
		this.controller.initDisplay();
	}
	
	/**
	 * @return the imageInfos
	 */
	public ImageInfos getImageInfos() {
		return imageInfos;
	}


	/**
	 * @return the awtImage
	 */
	public BufferedImage getAwtImage() {
		return awtImage;
	}


	/**
	 * @return the calcImage
	 */
	public ColorImage getCalcImage() {
		return calcImage;
	}
	
	/**
	 * @return the mainWindow
	 */
	public MainWindow getMainWindow() {
		return mainWindow;
	}
	
}
