package fr.ecn.ombre.desktop;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import fr.ecn.common.desktop.utils.ImagePanel;

public class HorizonImagePanel extends ImagePanel implements MouseListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected HorizonController controller;

	public HorizonImagePanel(Image image, HorizonController horizonController) {
		super(image);

		this.controller = horizonController;
		
		this.addMouseListener(this);
	}
	
	public void paintComponent(Graphics g) {
		double scale = this.getScale();
		
		g.drawImage(this.image, 0, 0, (int) (this.image.getWidth(null) * scale), (int) (this.image.getHeight(null) * scale), this);
	
		double yHorizon = this.controller.getyHorizon();
		
		g.setColor(Color.RED);
		
		g.drawLine(0, (int) (yHorizon * scale), (int) (this.image.getWidth(null) * scale), (int) (yHorizon * scale));
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		if (event.getButton() != MouseEvent.BUTTON1) {
			return;
		}
		
		double scale = this.getScale();

		double y = event.getPoint().getY() / scale;
		
		this.controller.setyHorizon(y);
		this.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent arg0) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	}

}
