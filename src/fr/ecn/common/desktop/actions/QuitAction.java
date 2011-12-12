package fr.ecn.common.desktop.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import fr.ecn.common.desktop.MainController;


public class QuitAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private MainController mainController;

	/**
	 * @param mainWindow
	 */
	public QuitAction(MainController mainController) {
		super("Quit");
		this.mainController = mainController;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		this.mainController.getMainWindow().dispose();
	}

}
