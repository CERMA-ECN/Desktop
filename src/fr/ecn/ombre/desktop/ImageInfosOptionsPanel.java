package fr.ecn.ombre.desktop;

import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import fr.ecn.common.core.imageinfos.Coordinate;
import fr.ecn.common.core.imageinfos.ImageInfos;
import fr.ecn.common.desktop.utils.BackNextPanel;

public class ImageInfosOptionsPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected ImageInfosController controller;
	
	protected BackNextPanel backNextPanel;

	protected JTextField latitude;
	protected JTextField longitude;
	protected JTextField orientation;
	
	/**
	 * @param controller
	 * @param imagePanel 
	 */
	public ImageInfosOptionsPanel(ImageInfosController controller) {
		super();
		this.controller = controller;
		
		this.build();
	}
	
	private void build() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		JPanel form = new JPanel();
		form.setLayout(new GridLayout(3, 2, 5, 5));
		
		this.latitude = new JTextField();
		this.longitude = new JTextField();
		this.orientation = new JTextField();

		ImageInfos imageInfos = this.controller.getMainController().getImageInfos();
		if (imageInfos.getLatitude() != null) {
			this.latitude.setText(imageInfos.getLatitude().toString());
		}
		if (imageInfos.getLongitude() != null) {
			this.longitude.setText(imageInfos.getLongitude().toString());
		}
		if (imageInfos.getOrientation() != null) {
			this.orientation.setText(imageInfos.getOrientation().toString());
		}
		
		form.add(new JLabel("Latitude"));
		form.add(this.latitude);
		form.add(new JLabel("Longitude"));
		form.add(this.longitude);
		form.add(new JLabel("Orientation"));
		form.add(this.orientation);
		
		this.add(form);
		
		this.add(Box.createVerticalGlue());
		
		this.backNextPanel = new BackNextPanel(this.controller);
		this.backNextPanel.setBackEnabled(false);
		this.add(this.backNextPanel);
	}
	
	public void valid() {
		ImageInfos imageInfos = this.controller.getMainController().getImageInfos();
		
		Coordinate latitude = Coordinate.fromString(this.latitude.getText());
		Coordinate longitude = Coordinate.fromString(this.longitude.getText());
		Double orientation = Double.parseDouble(this.orientation.getText());
		
		imageInfos.setLatitude(latitude);
		imageInfos.setLongitude(longitude);
		imageInfos.setOrientation(orientation);
	}
	
}
