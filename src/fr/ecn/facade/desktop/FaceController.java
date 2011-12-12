/**
 * 
 */
package fr.ecn.facade.desktop;

import java.util.ArrayList;
import java.util.List;

import fr.ecn.common.core.geometry.Point;
import fr.ecn.common.core.imageinfos.Face;
import fr.ecn.common.desktop.Controller;
import fr.ecn.common.desktop.MainWindow;

/**
 * @author jerome
 *
 */
public class FaceController extends Controller {
	
	protected Face face;
	protected Point currentPoint = null;

	protected int state = STATE_IDLE;
	
	public static final int STATE_IDLE = 0;
	public static final int STATE_EDIT = 1;
	public static final int STATE_CREATE = 2;
	
	private FaceOptionsPanel optionsPanel;

	@Override
	public void init() {
		List<Face> faces = this.getMainController().getImageInfos().getFaces();
		if (faces != null && faces.size() > 0) {
			this.face = faces.get(0);
		} else {
			this.face = new Face();
		}
		
		if (this.face.getPoints().size() == 4) {
			this.state = STATE_EDIT;
		} else {
			this.state = STATE_CREATE;
		}
	}

	@Override
	public void initDisplay() {
		FaceImagePanel imagePanel = new FaceImagePanel(this.getMainController().getAwtImage(), this);
		optionsPanel = new FaceOptionsPanel(this, imagePanel);
		
		//Update next button state according to the controler state;
		this.setState(this.state);
		
		MainWindow mainWindow = getMainController().getMainWindow();
		mainWindow.setImagePanel(imagePanel);
		mainWindow.setOptionsPanel(optionsPanel);
		
	}

	public void next() {
		if (this.state == STATE_CREATE) {
			return;
		}
		
		List<Face> faces = new ArrayList<Face>(1);
		faces.add(this.face);
		
		this.getMainController().getImageInfos().setFaces(faces);
		this.getMainController().setController(new ResultController());
	}
	
	/**
	 * Set the state of the controller
	 * 
	 * @param state
	 */
	private void setState(int state) {
		this.state = state;
		
		switch (state) {
		case STATE_CREATE:
			this.optionsPanel.backNextPanel.setNextEnabled(false);
			break;
		case STATE_EDIT:
			this.optionsPanel.backNextPanel.setNextEnabled(true);
			break;
		}
	}
	
	public void resetFace() {
		this.face = new Face();
		this.setState(STATE_CREATE);
	}

	public void addPoint(double x, double y) {
		if (this.state != STATE_CREATE) {
			return;
		}
		
		this.face.getPoints().add(new Point(x, y));
		
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

}
