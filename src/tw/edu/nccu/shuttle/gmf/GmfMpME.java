/**
 * 
 */
package tw.edu.nccu.shuttle.gmf;

import org.eclipse.draw2d.IFigure;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @author Kao, Chen-yi
 *
 */
public class GmfMpME extends GmfMpmeSelection {

	private IGraphicalEditPart mEEditPart;
	
	
	
	/**
	 * @param editPart - the View of Anchor MP ME
	 * @param type TODO
	 */
	public GmfMpME(IGraphicalEditPart editPart, IElementType type) {
		super(type);
		// check if the Semantic Element Type is related to the Edit Part 
		//	TODO: throw Exception if NOT related
		if (editPart.getAdapter(type.getEClass().getInstanceClass()) != null) {
			mEEditPart = editPart;
		}
	}

	
	
	/**
	 * @return the Graphical Edit Part
	 */
	public IGraphicalEditPart getGraphicalEditPart() {
		return mEEditPart;
	}

	/**
	 * @return the {@link org.eclipse.gmf.runtime.notation.View} of Anchor MP ME
	 */
	public View getView() {
		return mEEditPart.getNotationView();
	}

	public IFigure getFigure() {
		return mEEditPart.getFigure();
	}

}
