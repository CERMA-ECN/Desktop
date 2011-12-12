package fr.ecn.ombre.desktop;

import fr.ecn.common.desktop.Application;
import fr.ecn.common.desktop.Controller;
import fr.ecn.common.desktop.MainController;

public class Main implements Application {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new MainController(new Main()).init();
	}

	@Override
	public Controller getFistController() {
		return new ImageInfosController();
	}

	@Override
	public String getTitle() {
		return "Ombre";
	}

}
