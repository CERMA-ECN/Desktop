package fr.ecn.ombre.desktop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import fr.ecn.common.desktop.utils.BackNextPanel;

public class FacesOptionsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected FacesController controller;
	protected FacesImagePanel imagePanel;
	
	protected JButton resetFace;
	protected JButton endFace;
	protected BackNextPanel backNextPanel;

	/**
	 * @param controller
	 * @param imagePanel 
	 */
	public FacesOptionsPanel(FacesController controller, FacesImagePanel imagePanel) {
		super();
		this.controller = controller;
		this.imagePanel = imagePanel;
		
		this.build();
	}
	
	private void build() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		resetFace = new JButton("Reset face");
		resetFace.setAlignmentX(CENTER_ALIGNMENT);
		resetFace.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.startFace();
				imagePanel.repaint();
			}
		});
		this.add(resetFace);
		
		endFace = new JButton("End face");
		endFace.setAlignmentX(CENTER_ALIGNMENT);
		endFace.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.endFace();
				imagePanel.repaint();
			}
		});
		endFace.setEnabled(false);
		this.add(endFace);
		
		this.add(Box.createVerticalGlue());
		
		this.backNextPanel = new BackNextPanel(this.controller);
		this.add(this.backNextPanel);
	}
	
}
