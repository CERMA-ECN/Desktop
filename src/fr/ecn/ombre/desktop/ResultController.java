package fr.ecn.ombre.desktop;

import java.text.ParseException;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import fr.ecn.common.core.image.Image;
import fr.ecn.common.core.imageinfos.Face;
import fr.ecn.common.core.imageinfos.ImageInfos;
import fr.ecn.common.desktop.Controller;
import fr.ecn.ombre.core.shadows.Couple;
import fr.ecn.ombre.core.shadows.ShadowDrawing;
import fr.ecn.ombre.core.shadows.ShadowDrawingException;
import fr.ecn.ombre.core.shadows.ShadowDrawingFace;
import fr.ecn.ombre.core.shadows.ShadowDrawingFactory;

public class ResultController extends Controller {
	
	protected List<ShadowDrawingFace> shadows = null;;
	
	protected ResultOptionsPanel optionsPanel;
	protected ResultImagePanel imagePanel;
	
	/**
	 * @param date
	 * @param shadowsOnWalls
	 * @param expendToStreet
	 */
	public ResultController() {
		super();
	}

	@Override
	public void init() {
	}

	@Override
	public void initDisplay() {
		optionsPanel = new ResultOptionsPanel(this);
		imagePanel = new ResultImagePanel(this.getMainController().getAwtImage(), this);
		
		this.getMainController().getMainWindow().setOptionsPanel(optionsPanel);
		this.getMainController().getMainWindow().setImagePanel(imagePanel);
	}

	@Override
	public void next() {}
	
	public void compute() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					optionsPanel.valid();
					
					Calendar date = optionsPanel.date;
					boolean shadowsOnWalls = optionsPanel.getShadowsOnWalls();
					boolean expendToStreet = optionsPanel.getExpendToStreet();
					
					computeShadows(date, shadowsOnWalls, expendToStreet);

					SwingUtilities.invokeLater(new Runnable() {
						public void run() {
							imagePanel.repaint();
						}
					});
				} catch (ShadowDrawingException e) {
					JOptionPane.showMessageDialog(getMainController().getMainWindow(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				} catch (ParseException e) {
					JOptionPane.showMessageDialog(getMainController().getMainWindow(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		}).start();
	}
	
	private void computeShadows(Calendar date, boolean shadowsOnWalls, boolean expendToStreet) throws ShadowDrawingException {
		//Initialize the shadows List
		this.shadows = new LinkedList<ShadowDrawingFace>();
		
		ImageInfos imageInfos = this.getMainController().getImageInfos();
		
		// test si la geometrie n'est pas vide:
		if (imageInfos.getFaces().isEmpty()) {
			throw new ShadowDrawingException("Vous devez rentrer au moins une face");
		}
		
		//Creating the list of ShadowDrawingFace
		List<ShadowDrawingFace> faces = new LinkedList<ShadowDrawingFace>();
		for (Face f : imageInfos.getFaces()) {
			faces.add(new ShadowDrawingFace(f.getRealFace()));
		}

		Image image = this.getMainController().getCalcImage();
		
		ShadowDrawingFactory sdf = new ShadowDrawingFactory(image, imageInfos, faces, date);
		ShadowDrawing shadowDrawing = sdf.getShadowDrawing();
		
		// =============================================================//
		// CALCUL DE L'OMBRE //
		// =============================================================//
		
		// calcul proprement dit:
		for (ShadowDrawingFace f : faces) {

			// premier calcul de l'ombre au sol, avec test si l'inclinaison du
			// rayon n'est pas bonne.. ( pour cas limite ou pente rayon<pente
			// fuyante==>bug!)
			ShadowDrawingFace faceOmbre = shadowDrawing.drawShadow(f);
			// si l'ombre n'est pas dans le bon sens, on s'arrete la.
			if (!faceOmbre.isOutside()) {
				continue;
			}
			
			this.shadows.add(f);
			
			/**
			 * On ne fait la suite que si la case "wall" est cochée, autrement
			 * dit, que si on veut voir l'ombre sur les murs
			 * 
			 * Si l'on a une seul face le calcul est innutile
			 */
			if (shadowsOnWalls && imageInfos.getFaces().size() > 1) {
				// on boucle sur les faces autres que f :
				for (ShadowDrawingFace f2 : faces) {
					if (f2 != f) {
						// On calcule les points qui de l'ombre qui se
						// retrouvent sur la face f2 de la géométrie, et
						// on les met dans vectPointOmbreF2
						Couple[] vectPointOmbreF2 = shadowDrawing.calculOmbreMur(f, faceOmbre, f2);
						List<ShadowDrawingFace> ombre = shadowDrawing.determinationOmbreMur(f, faceOmbre, f2, vectPointOmbreF2,
								sdf.getCoupleSoleil());
						this.shadows.addAll(ombre);
					}
				} // fin boucle sur autres faces que f
			} else { // si pas de "wall", on rajoute juste l'ombre si elle est
				this.shadows.add(faceOmbre);
			} // fin if "wall"
		} // fin boucle de départ sur les faces.
		
		//Expend shadows to street if requested
		if (expendToStreet) {
			List<ShadowDrawingFace> expendedFaces = new LinkedList<ShadowDrawingFace>();
			for (ShadowDrawingFace face : this.shadows) {
				expendedFaces.add(face.expandToStreet(image));
			}
			this.shadows.addAll(expendedFaces);
		}
	}

}
