package fr.ecn.common.desktop.utils;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

/**
 * A generic ImagePanel that just draw an image
 * 
 * @author jerome
 *
 */
public class ImagePanel extends JPanel {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected Image image;

	/**
	 * @param image
	 */
	public ImagePanel(Image image) {
		super();
		this.image = image;
	}
	
	protected double getScale() {
		return Math.min((double)this.getWidth()/this.image.getWidth(null), (double)this.getHeight()/this.image.getHeight(null));
	}
	
	@Override
	public void paintComponent(Graphics g) {
		double scale = this.getScale();
		
		g.drawImage(this.image, 0, 0, (int) (this.image.getWidth(null) * scale), (int) (this.image.getHeight(null) * scale), this);
	}
}
