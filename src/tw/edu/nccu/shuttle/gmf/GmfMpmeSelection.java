/**
 * 
 */
package tw.edu.nccu.shuttle.gmf;

import org.eclipse.gmf.runtime.emf.type.core.IElementType;

import tw.edu.nccu.shuttle.MpmeSelection;

/**
 * An MP ME Selection is like a filter to select certain group
 * of MP model elements, which may be of one GMF Element Type
 * or other special constraining types deducted from SWRL clauses
 * (Antecedents/Consequents).
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
public class GmfMpmeSelection implements MpmeSelection {

	/**
	 * TODO: for temporary demo, 
	 * currently ALL selections are IElementType Selections. 
	 * But IElementTypeSelection SHOULD be separated from MPMESelection 
	 */
//	private IElementType mEType;
	
	
	
	/**
	 * TODO: for temporary demo, 
	 * currently ALL selections are IElementType Selections. 
	 * But IElementTypeSelection SHOULD be separated from MPMESelection 
	 * 
	 * @param type
	 */
	public GmfMpmeSelection(IElementType type) {
//		mEType = type;
	}

	
	
	/**
	 * @see tw.edu.nccu.shuttle.MpmeSelection#isSingleTypeSelection()
	 */
	public boolean isSingleTypeSelection() {
		//TODO: for prototype demo, not always true
		return true;
	}

//	public Class getType() {
//		return mEType.getEClass().getInstanceClass();
//	}

}
