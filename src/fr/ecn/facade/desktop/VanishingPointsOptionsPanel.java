package fr.ecn.facade.desktop;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JPanel;

import fr.ecn.common.desktop.utils.BackNextPanel;
import fr.irstv.kmeans.DataGroup;

public class VanishingPointsOptionsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected VanishingPointsController controller;
	protected VanishingPointsImagePanel imagePanel;
	
	protected BackNextPanel backNextPanel;

	/**
	 * @param controller
	 * @param imagePanel 
	 */
	public VanishingPointsOptionsPanel(VanishingPointsController controller, VanishingPointsImagePanel imagePanel) {
		super();
		this.controller = controller;
		this.imagePanel = imagePanel;
		
		this.build();
	}
	
	private void build() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		final DataGroup[] groups = this.controller.getGroups();
		final boolean[] selectedGroups = this.controller.getSelectedGroups();
		
		for (int i=0; i < groups.length; i++) {
			final int id = i;
			
			final JCheckBox checkBox = new JCheckBox("Groupe " + i);
			checkBox.setSelected(selectedGroups[i]);
			checkBox.setForeground(VanishingPointsImagePanel.colorMap[i]);
			checkBox.setAlignmentX(CENTER_ALIGNMENT);
			checkBox.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent event) {
					selectedGroups[id] = checkBox.isSelected();
					imagePanel.repaint();
				}
			});
			this.add(checkBox);
		}
		
		this.add(Box.createVerticalGlue());
		
		this.backNextPanel = new BackNextPanel(this.controller);
		this.backNextPanel.setBackEnabled(false);
		this.add(this.backNextPanel);
	}
	
}
