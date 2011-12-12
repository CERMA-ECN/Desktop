package fr.ecn.common.desktop.utils;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import fr.ecn.common.desktop.Controller;

/**
 * @author jerome
 *
 * back/next buttons for side panels
 *
 */
public class BackNextPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Controller controller;

	protected JButton backButton;
	protected JButton nextButton;
	
	/**
	 * 
	 */
	public BackNextPanel(Controller controller) {
		super();
		
		this.controller = controller;
		
		this.build();
	}

	private void build() {
		backButton = new JButton("< Back");
		backButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.back();
			}
		});
		
		nextButton = new JButton("Next >");
		nextButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.next();
			}
		});
		
		this.setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
		this.add(backButton);
		this.add(Box.createRigidArea(new Dimension(5, 0)));
		this.add(nextButton);
	}
	
	public void setBackEnabled(boolean enabled) {
		this.backButton.setEnabled(enabled);
	}
	
	public void setNextEnabled(boolean enabled) {
		this.nextButton.setEnabled(enabled);
	}

}
