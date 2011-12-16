package fr.ecn.ombre.desktop;

import java.util.ArrayList;
import java.util.List;

import fr.ecn.common.core.geometry.Point;
import fr.ecn.common.core.geometry.Segment;
import fr.ecn.common.core.image.Image;
import fr.ecn.common.core.segmentdetection.EdgeDetection;
import fr.ecn.common.core.segmentdetection.Edgel;
import fr.ecn.common.core.segmentdetection.SegmentDetection;
import fr.ecn.common.desktop.Controller;
import fr.ecn.common.desktop.MainWindow;
import fr.irstv.dataModel.CircleKDistance;
import fr.irstv.dataModel.DataPoint;
import fr.irstv.kmeans.CleaningDataGroups;
import fr.irstv.kmeans.DataGroup;
import fr.irstv.kmeans.DataMk;
import fr.irstv.kmeans.RanSac;

public class VanishingPointsController extends Controller {
	
	/**
	 * The controller that called this controller
	 */
	protected HorizonController controller;
	
	protected DataGroup[] groups;
	protected boolean[] selectedGroups;

	/**
	 * @param controller
	 */
	public VanishingPointsController(HorizonController controller) {
		super();
		this.controller = controller;
	}

	@Override
	public void init() {
		Image image = this.getMainController().getCalcImage();
		
		List<Edgel> edgels = new EdgeDetection(image, 50f).getEdgels();
		List<Segment> segments = new SegmentDetection(edgels, image, 8).getSegments();
		
		CircleKDistance fd = new CircleKDistance();

		DataMk dataSet = new DataMk(segments);
		
		// here we launch the real job
		RanSac r = new RanSac(6,dataSet,fd);
		// param init
		r.setMaxSample(1000);
		r.setSigma(5d);
		r.setStopThreshold(0.1d);
		r.go();
		
		//Cleaning the groups
		groups = new CleaningDataGroups().clean(r.getGroups());
		
		//Create the selectedGroups array
		this.selectedGroups = new boolean[this.groups.length];
		for (int i=0; i< this.selectedGroups.length; i++) {
			this.selectedGroups[i] = true;
		}
	}

	@Override
	public void initDisplay() {
		VanishingPointsImagePanel imagePanel = new VanishingPointsImagePanel(this.getMainController().getAwtImage(), this);
		VanishingPointsOptionsPanel optionsPanel = new VanishingPointsOptionsPanel(this, imagePanel);
		
		MainWindow mainWindow = getMainController().getMainWindow();
		mainWindow.setImagePanel(imagePanel);
		mainWindow.setOptionsPanel(optionsPanel);
	}
	
	/**
	 * Save the result of this controller and switch to the next controller.
	 */
	public void next() {
		List<Point> vanishingPoints = new ArrayList<Point>();
		
		for (int i=0; i<this.groups.length; i++) {
			if (!this.getSelectedGroups()[i])
				continue;
			
			DataPoint vp = this.groups[i].computeCentroid();
			
			vanishingPoints.add(new Point(vp.get(0)*2, vp.get(1)*2));
		}
		
		this.getMainController().getImageInfos().setVanishingPoints(vanishingPoints);
		
		//Calculate Horizon
		double yHorizon = 0;
		for (Point p : vanishingPoints) {
			yHorizon += p.getY();
		}
		yHorizon /= vanishingPoints.size();
		
		controller.yHorizon = yHorizon;
		
		this.getMainController().back();
	}

	/**
	 * @return the groups
	 */
	public DataGroup[] getGroups() {
		return groups;
	}

	/**
	 * @return the selectedGroups
	 */
	public boolean[] getSelectedGroups() {
		return selectedGroups;
	}

}
