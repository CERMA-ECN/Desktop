package fr.ecn.ombre.desktop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import fr.ecn.common.desktop.utils.BackNextPanel;

public class HorizonOptionsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected HorizonController controller;
	
	protected BackNextPanel backNextPanel;

	/**
	 * @param controller
	 * @param imagePanel 
	 */
	public HorizonOptionsPanel(HorizonController controller) {
		super();
		this.controller = controller;
		
		this.build();
	}
	
	private void build() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		JButton resetFace = new JButton("Auto detect");
		resetFace.setAlignmentX(CENTER_ALIGNMENT);
		resetFace.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO
			}
		});
		this.add(resetFace);
		
		this.add(Box.createVerticalGlue());
		
		this.backNextPanel = new BackNextPanel(this.controller);
		this.add(this.backNextPanel);
	}
	
}
