/**
 * 
 */
package tw.edu.nccu.shuttle.gmf;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gmf.runtime.diagram.ui.tools.CreationTool;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;

/**
 * @author Kao, Chen-yi
 *
 */
public class RecommendationCreationTool extends CreationTool {

	/**
	 * TODO: for temporary demo
	 * 
	 * @param mEType - model element type in 
	 * {@link org.eclipse.gmf.runtime.emf.type.core.IElementType}
	 */
	public RecommendationCreationTool(IElementType mEType) {
		super(mEType);
		super.createShapeAt(new Point(10, 10));
	}

	public void create() {
		// TODO: for temporary demo
		
	}

}
