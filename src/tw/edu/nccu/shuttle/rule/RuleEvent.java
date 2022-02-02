/**
 * 
 */
package tw.edu.nccu.shuttle.rule;

import java.util.EventObject;

import tw.edu.nccu.shuttle.rule.WordNetSyncRule.WordNetSyncRecommender;
import tw.edu.nccu.shuttle.rule.WordNetSyncRule.WordNetSyncTrigger;

/**
 * @author Kao, Chen-yi
 *
 */
public class RuleEvent extends EventObject {

	private static final long serialVersionUID = 1L;
	
	
	
	private Rule<?, ?, ?> source;
	/**
	 * {@link #rteTrigger} - The {@link RuleTrigger} event trigger.
	 * {@link #rreRecommender} - The {@link RuleRecommender} event recommender.
	 */
	private RuleTrigger<?> rteTrigger;
	private RuleRecommender<?> rreRecommender;

	
	
	/**
	 * The {@link #sourceRule}, {@link #trigger} and {@link #recommender} don't have to
	 * belong to co-declared types, like {@link WordNetSyncRule}, {@link WordNetSyncTrigger}
	 * and {@link WordNetSyncRecommender}.
	 * 
	 * @param sourceRule
	 * 
	 * @param trigger - The {@link RuleTrigger} event trigger. Not applicable/necessary (null) for 
	 * {@link RuleRecommenderListener} when {@link RuleRecommendedListener#ruleRecommended()} 
	 * or {@link RuleAppliedListener#ruleApplied()} are invoked.
	 * 
	 * @param recommender - The {@link RuleRecommender} event recommender. Not applicable/necessary (null) 
	 * for {@link RuleTriggerListener} when {@link RuleTriggeredListener#ruleTriggered()} 
	 * or {@link RuleMatchedListener#ruleMatched()} are invoked.
	 */
	public RuleEvent(
			Rule<?, ?, ?> sourceRule, RuleTrigger<?> trigger, RuleRecommender<?> recommender) {
		super(sourceRule);
		source = sourceRule;
		rteTrigger = trigger;
		rreRecommender = recommender;
	}

	/**
	 * @return the source {@link Rule}
	 */
	public Rule<?, ?, ?> getSource() {
		return source;
	}

	public RuleTrigger<?> getTrigger() {
		return rteTrigger;
	}

	public RuleRecommender<?> getRecommender() {
		return rreRecommender;
	}
	
}
