/**
 * 
 */
package fr.ecn.ombre.desktop;

import java.util.LinkedList;
import java.util.List;

import fr.ecn.common.core.geometry.Point;
import fr.ecn.common.core.imageinfos.Face;
import fr.ecn.common.desktop.Controller;
import fr.ecn.common.desktop.MainWindow;

/**
 * @author jerome
 *
 */
public class FacesController extends Controller {
	
	protected List<Face> faces;
	protected Face face;
	protected Point currentPoint = null;

	protected int state = STATE_IDLE;
	
	public static final int STATE_IDLE = 0;
	public static final int STATE_EDIT = 1;
	public static final int STATE_CREATE = 2;
	
	private FacesOptionsPanel optionsPanel;

	@Override
	public void init() {
		List<Face> faces = this.getMainController().getImageInfos().getFaces();
		if (faces != null) {
			this.faces = faces;
		} else {
			this.faces = new LinkedList<Face>();
		}

		this.face = new Face();
		this.state = STATE_CREATE;
	}

	@Override
	public void initDisplay() {
		FacesImagePanel imagePanel = new FacesImagePanel(this.getMainController().getAwtImage(), this);
		optionsPanel = new FacesOptionsPanel(this, imagePanel);
		
		//Update next button state according to the face list
		if (this.faces.size() > 0) {
			this.optionsPanel.backNextPanel.setNextEnabled(true);
		} else {
			this.optionsPanel.backNextPanel.setNextEnabled(false);
		}
		
		MainWindow mainWindow = getMainController().getMainWindow();
		mainWindow.setImagePanel(imagePanel);
		mainWindow.setOptionsPanel(optionsPanel);
		
	}

	public void next() {
		if (this.faces.size() < 1) {
			return;
		}
		
		this.getMainController().getImageInfos().setFaces(this.faces);
		
		this.getMainController().setController(new OptionsController());
	}
	
	/**
	 * Set the state of the controller
	 * 
	 * @param state
	 */
	private void setState(int state) {
		this.state = state;
		
		switch (state) {
		case STATE_IDLE:
			break;
		case STATE_CREATE:
			this.optionsPanel.resetFace.setEnabled(false);
			this.optionsPanel.endFace.setEnabled(false);
			break;
		case STATE_EDIT:
			this.optionsPanel.endFace.setEnabled(true);
			break;
		}
	}
	
	public void startFace() {
		this.face = new Face();
		this.setState(STATE_CREATE);
	}

	public void addPoint(double x, double y) {
		if (this.state != STATE_CREATE) {
			return;
		}
		
		this.face.getPoints().add(new Point(x, y));
		
		this.optionsPanel.resetFace.setEnabled(true);
		
		if (this.face.getPoints().size() == 4) {
			this.setState(STATE_EDIT);
		}
	}

	public void selectPoint(double x, double y) {
		if (this.state != STATE_EDIT) {
			return;
		}
		
		for (Point point : this.face.getPoints()) {
			double delta = 25;
			if (point.getX() < x + delta && point.getX() > x - delta
					&& point.getY() < y + delta && point.getY() > y - delta) {
				this.currentPoint = point;
			}
		}
	}

	public void movePoint(double x, double y) {
		if (this.state != STATE_EDIT || this.currentPoint == null) {
			return;
		}
		
		this.currentPoint.setX(x);
		this.currentPoint.setY(y);
	}

	public void deselectPoint() {
		this.currentPoint = null;
	}
	
	public void endFace() {
		this.faces.add(this.face);
		this.face = null;

		this.optionsPanel.backNextPanel.setNextEnabled(true);
		
		//Then start a new face
		this.startFace();
	}

}
