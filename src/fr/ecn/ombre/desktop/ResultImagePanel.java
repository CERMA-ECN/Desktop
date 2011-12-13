package fr.ecn.ombre.desktop;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

import fr.ecn.common.core.geometry.Point;
import fr.ecn.common.desktop.utils.ImagePanel;
import fr.ecn.ombre.core.shadows.ShadowDrawingFace;

public class ResultImagePanel extends ImagePanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected ResultController controller;
	
	/**
	 * @param awtImage
	 * @param resultController
	 */
	public ResultImagePanel(BufferedImage awtImage, ResultController controller) {
		super(awtImage);
		this.controller = controller;
	}
	
	public void paintComponent(Graphics g) {
		double scale = this.getScale();
		
		g.drawImage(this.image, 0, 0, (int) (this.image.getWidth(null) * scale), (int) (this.image.getHeight(null) * scale), this);

		g.setColor(new Color(0, 0, 255, 50));
		
		for (ShadowDrawingFace fOmbre : this.controller.shadows) {
			this.drawShadow(fOmbre, g, scale);
		}
	}

	private void drawShadow(ShadowDrawingFace face, Graphics g, double scale) {
		Point[] points = face.getPoints();
		
		Polygon poly = new Polygon();

		for (int i = 0; i < points.length; i++) {
			Point p = points[i];
			
			poly.addPoint((int) (p.getX()*scale), (int) (p.getY()*scale));
		}
		
		g.fillPolygon(poly);
	}
	
}
