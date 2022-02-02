/**
 * 
 */
package tw.edu.nccu.shuttle.sandbox;


/**
 * @author Kao, Chen-yi
 *
 */
public abstract class GmfLabelSyncRule
	<Trigger extends GmfLabelSyncTrigger, Recommender extends GmfLabelSyncRecommender> 
	extends Rule<Trigger, Recommender> {

	/**
	 * TODO: for temporary demo. The exception message SHOULD be internationalized!
	 * @aspect Internationalization
	 */
	final static public IllegalArgumentException IMHOOKABLE_EXCEPTION =
		new IllegalArgumentException(
			"The MPME Adaptable is neither AbstractGraphicalEditPart " +
			"adaptable nor WrapLabel host!");
	
}
