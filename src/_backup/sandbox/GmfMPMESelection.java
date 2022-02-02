/**
 * 
 */
package tw.edu.nccu.shuttle.sandbox;

import org.eclipse.gmf.runtime.emf.type.core.IElementType;

/**
 * An MP ME Selection is like a filter to select certain group
 * of MP model elements, which may be of one GMF Element Type
 * or other special constraining types deducted from SWRL 
 * ??clauses.
 * 
 * TODO: constraining types in which format? 
 * Special RDF? Ecore Feature? Mixed MP (Ecore/GMF/Java...) feature? 
 * Or just sub-string occurrence pattern for Shuttle Neutral Model? 
 * Adapting to IClientContext/IElementMatcher framework?
 * 
 * TODO: Active or passive selecting? 
 * select(Diagram)? isSelcted(View/Node/Edge)? 
 * Change Selection to Selector?
 * 
 * TODO: Selection SHOULD be hierarchical
 *
 * @author Kao, Chen-yi
 * 
 */
public class GmfMPMESelection implements MPMESelection {

	/**
	 * TODO: for temporary demo, 
	 * currently ALL selections are IElementType Selections. 
	 * But IElementTypeSelection SHOULD be separated from MPMESelection 
	 */
	private IElementType mEType;
	
	/**
	 * TODO: for temporary demo, 
	 * currently ALL selections are IElementType Selections. 
	 * But IElementTypeSelection SHOULD be separated from MPMESelection 
	 * 
	 * @param type
	 */
	public GmfMPMESelection(IElementType type) {
		mEType = type;
	}

	/* (non-Javadoc)
	 * @see tw.edu.nccu.shuttle.sandbox.MPMESelection#isSingleTypeSelection()
	 */
	public boolean isSingleTypeSelection() {
		//TODO: for temporary demo, not always true
		return true;
	}

	/* (non-Javadoc)
	 * @see tw.edu.nccu.shuttle.sandbox.MPMESelection#getType()
	 */
	public Class getType() {
		return mEType.getEClass().getInstanceClass();
		// TODO: throw No Such Type Exception 
		//	if this selection isn't a type selection.
	}

}
