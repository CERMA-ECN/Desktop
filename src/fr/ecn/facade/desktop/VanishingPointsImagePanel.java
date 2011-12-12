package fr.ecn.facade.desktop;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import fr.ecn.common.desktop.utils.ImagePanel;
import fr.irstv.dataModel.DataPoint;
import fr.irstv.dataModel.MkDataPoint;
import fr.irstv.kmeans.DataGroup;

public class VanishingPointsImagePanel extends ImagePanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public static final Color[] colorMap = {
		Color.RED,
		Color.BLUE,
		Color.GREEN,
		new Color(255, 200, 0),
		Color.YELLOW,
		Color.MAGENTA,
		Color.CYAN,
		Color.WHITE,
	};
	
	protected VanishingPointsController controller;

	public VanishingPointsImagePanel(Image image, VanishingPointsController controller) {
		super(image);

		this.controller = controller;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		double scale = this.getScale();
		
		g.drawImage(this.image, 0, 0, (int) (this.image.getWidth(null) * scale), (int) (this.image.getHeight(null) * scale), this);
		
		DataGroup[] groups = this.controller.getGroups();
		for (int i=0; i<groups.length; i++) {
			if (!this.controller.getSelectedGroups()[i])
				continue;
			
			DataGroup group = groups[i];

			g.setColor(colorMap[i]);
			
			for (MkDataPoint dp : group.getComponents()) {
				DataPoint beginPoint = dp.getSeg().getBeginPoint();
				DataPoint endPoint = dp.getSeg().getEndPoint();
				
				g.drawLine((int)(scale * beginPoint.get(0)), (int)(scale * beginPoint.get(1)),
						(int)(scale * endPoint.get(0)), (int)(scale * endPoint.get(1)));
			}
			
			DataPoint vp = group.computeCentroid();
			
			g.fillOval((int)(scale * vp.get(0)*2), (int)(scale * vp.get(1)*2), 10, 10);
		}
	}

}
