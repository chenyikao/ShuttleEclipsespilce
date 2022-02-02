/**
 * 
 */
package tw.edu.nccu.shuttle.gmf;

import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecorator;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget;

import tw.edu.nccu.shuttle.rule.WordNetSyncRule;


/**
 * @author Kao, Chen-yi
 *
 */
abstract public class GmfLabelSyncRecommender extends WordNetSyncRule.WordNetSyncRecommender<IDecoratorTarget> {

//	public GmfLabelSyncRecommender(GmfLabelSyncRule<?, ?> host) {
//		super((Rule<?, RuleRecommender>) host);
//	}

	
	/**
	 * @see tw.edu.nccu.shuttle.rule.RuleRecommender#hookIntoCacheSolved(java.lang.Object)
	 */
	protected void hookIntoCacheSolved(IDecoratorTarget mpme) throws UnsupportedOperationException {
		// Hook Recommendation Hint. TODO: recommendationHint is MPHook?
		recommendationHint = new RecommendationHintDecorator(mpme);
		// TODO: temporary system key
		mpme.installDecorator(
				"Shuttle", (IDecorator) recommendationHint);
	}

}
