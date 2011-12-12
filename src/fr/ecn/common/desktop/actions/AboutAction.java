package fr.ecn.common.desktop.actions;

import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;

import fr.ecn.common.desktop.MainController;


public class AboutAction extends AbstractAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
//	private MainController mainController;

	/**
	 * @param mainWindow
	 */
	public AboutAction(MainController mainController) {
		super("About");
//		this.mainController = mainController;
	}

	@Override
	public void actionPerformed(ActionEvent event) {
		//TODO: Do something
	}

}
