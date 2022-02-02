/**
 * 
 */
package tw.edu.nccu.shuttle.gmf;

import tw.edu.nccu.shuttle.MpmeSelection;


/**
 * @author Kao, Chen-yi
 *
 */
public class GmfLabelSelection implements MpmeSelection {

	/* (non-Javadoc)
	 * @see tw.edu.nccu.shuttle.sandbox.MPMESelection#getType()
	 */
//	public Class getType() {
//		return TextCompartmentEditPart.class;
//	}

	/* (non-Javadoc)
	 * @see tw.edu.nccu.shuttle.sandbox.MPMESelection#isSingleTypeSelection()
	 */
	public boolean isSingleTypeSelection() {
		return true;
	}

}
