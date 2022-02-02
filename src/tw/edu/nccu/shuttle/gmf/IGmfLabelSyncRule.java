/**
 * 
 */
package tw.edu.nccu.shuttle.gmf;



/**
 * A utility interface for GMF Label synchronization rules.
 * 
 * @author Kao, Chen-yi
 *
 */
public interface IGmfLabelSyncRule extends IGmfSyncRule {

	/**
	 * @aspect Internationalization
	 * TODO: for temporary demo. The exception message SHOULD be internationalized!
	 */
	final static public IllegalArgumentException IMHOOKABLE_EXCEPTION =
		new IllegalArgumentException(
			"The MPME Adaptable is neither AbstractGraphicalEditPart " +
			"adaptable nor WrapLabel host!");
	
}
