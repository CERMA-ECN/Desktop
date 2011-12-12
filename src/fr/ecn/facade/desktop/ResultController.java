package fr.ecn.facade.desktop;

import java.awt.image.BufferedImage;
import java.util.List;

import fr.ecn.common.core.geometry.Point;
import fr.ecn.common.core.image.ColorImage;
import fr.ecn.common.core.imageinfos.Face;
import fr.ecn.common.desktop.Controller;
import fr.ecn.common.desktop.MainWindow;
import fr.ecn.common.desktop.utils.ImagePanel;
import fr.ecn.common.desktop.utils.ImageUtils;
import fr.ecn.facade.core.straightening.StraighteningFunction;

public class ResultController extends Controller {
	
	protected BufferedImage resultImage;

	@Override
	public void init() {
		//Get the first (and only) face
		Face face = this.getMainController().getImageInfos().getFaces().get(0);
		
		List<Point> edgesPoints =  face.getPoints();
		
		//Get a vanishing point
		//TODO find the vanishing point corresponding to this face
		Point vanishingPoint = this.getMainController().getImageInfos().getVanishingPoints().get(0);
		
		ColorImage resultImage = new StraighteningFunction(edgesPoints, this.getMainController().getCalcImage(), 40, vanishingPoint, 10).getResult();
//		ColorImage resultImage = new StraighteningFunction(edgesPoints, this.getMainController().getCalcImage(), 40, 2, 10).getResult();
		
		this.resultImage = ImageUtils.toAwt(resultImage);
	}

	@Override
	public void initDisplay() {
		ImagePanel imagePanel = new ImagePanel(this.resultImage);
		
		MainWindow mainWindow = getMainController().getMainWindow();
		mainWindow.setImagePanel(imagePanel);
	}

	@Override
	public void next() {}

}
