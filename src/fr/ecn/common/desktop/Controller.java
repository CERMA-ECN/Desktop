package fr.ecn.common.desktop;


public abstract class Controller {

	private MainController mainController;
	
	/**
	 * @return the mainController
	 */
	public MainController getMainController() {
		return mainController;
	}

	/**
	 * @param mainController the mainController to set
	 */
	void setMainController(MainController mainController) {
		this.mainController = mainController;
	}

	abstract public void init();

	abstract public void initDisplay();
	
	/**
	 * Action when the back button is hit.
	 * Default action is to call mainController.back();
	 */
	public void back() {
		this.mainController.back();
	}

	abstract public void next();

}
