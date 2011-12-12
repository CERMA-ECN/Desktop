package fr.ecn.ombre.desktop;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import fr.ecn.common.core.geometry.Point;
import fr.ecn.common.core.imageinfos.Face;
import fr.ecn.common.desktop.utils.ImagePanel;

public class FacesImagePanel extends ImagePanel implements MouseListener, MouseMotionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	protected FacesController controller;

	public FacesImagePanel(Image image, FacesController controller) {
		super(image);

		this.controller = controller;
		
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		double scale = this.getScale();
		
		g.drawImage(this.image, 0, 0, (int) (this.image.getWidth(null) * scale), (int) (this.image.getHeight(null) * scale), this);

		//Draw the faces
		g.setColor(Color.YELLOW);
		
		for (Face face : this.controller.faces) {
			Point p1 = face.getPoints().get(3);
			
			for (Point p2 : face.getPoints()) {
				g.drawLine((int)(p1.getX() * scale), (int)(p1.getY() * scale), (int)(p2.getX() * scale), (int)(p2.getY() * scale));
				
				p1 = p2;
			}
		}
		
		//Draw the current face
		if (this.controller.face != null) {
			g.setColor(Color.RED);
			
			Point p1 = null;
			if (this.controller.face.getPoints().size() == 1 ) {
				p1 = this.controller.face.getPoints().get(0);
			}
			if (this.controller.face.getPoints().size() == 4) {
				p1 = this.controller.face.getPoints().get(3);
			}
			for (Point p2 : this.controller.face.getPoints()) {
				if (p1 != null) {
					g.drawLine((int)(p1.getX() * scale), (int)(p1.getY() * scale), (int)(p2.getX() * scale), (int)(p2.getY() * scale));
				}
				
				p1 = p2;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		if (event.getButton() != MouseEvent.BUTTON1) {
			return;
		}
		
		double scale = this.getScale();
		
		double x = event.getPoint().getX() / scale;
		double y = event.getPoint().getY() / scale;
		
		this.controller.addPoint(x, y);
		this.repaint();
	}

	@Override
	public void mouseEntered(MouseEvent event) {
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
	}

	@Override
	public void mousePressed(MouseEvent event) {
//		System.out.println("Pressed : " + event);
		
		if (event.getButton() != MouseEvent.BUTTON1) {
			return;
		}
		
		double scale = this.getScale();
		
		double x = event.getPoint().getX() / scale;
		double y = event.getPoint().getY() / scale;
		
		this.controller.selectPoint(x, y);
		this.repaint();
		
	}

	@Override
	public void mouseReleased(MouseEvent event) {
//		System.out.println("Released : " + event);
		
		if (event.getButton() != MouseEvent.BUTTON1) {
			return;
		}
		
		this.controller.deselectPoint();
		
	}

	@Override
	public void mouseDragged(MouseEvent event) {
//		System.out.println("Draged : " + event);
		
//		if (event.getButton() != MouseEvent.BUTTON1) {
//			return;
//		}
		
		if (this.controller.currentPoint == null) {
			return;
		}
		
		double scale = this.getScale();
		
		double x = event.getPoint().getX() / scale;
		double y = event.getPoint().getY() / scale;
		
		this.controller.movePoint(x, y);
		this.repaint();
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	}

}
