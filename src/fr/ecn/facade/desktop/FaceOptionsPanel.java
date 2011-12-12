package fr.ecn.facade.desktop;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import fr.ecn.common.desktop.utils.BackNextPanel;

public class FaceOptionsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected FaceController controller;
	protected FaceImagePanel imagePanel;
	
	protected BackNextPanel backNextPanel;

	/**
	 * @param controller
	 * @param imagePanel 
	 */
	public FaceOptionsPanel(FaceController controller, FaceImagePanel imagePanel) {
		super();
		this.controller = controller;
		this.imagePanel = imagePanel;
		
		this.build();
	}
	
	private void build() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		JButton resetFace = new JButton("Reset face");
		resetFace.setAlignmentX(CENTER_ALIGNMENT);
		resetFace.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				controller.resetFace();
				imagePanel.repaint();
			}
		});
		this.add(resetFace);
		
		this.add(Box.createVerticalGlue());
		
		this.backNextPanel = new BackNextPanel(this.controller);
		this.add(this.backNextPanel);
	}
	
}
