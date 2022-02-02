/**
 * 
 */
package tw.edu.nccu.shuttle.rule;

/**
 * Another face (polymorphism) of MP inference engine. For whom interested in 
 * listening the {@link Rule} events:
 * <ol>
 * <li>{@link #ruleTriggered()} - TODO ??Both {@link Rule} and {@link RuleRecommender} 
 * are also interested in it to recommend match;
 * <li>{@link #ruleMatched()} - TODO ??Both {@link Rule} and {@link RuleRecommender} 
 * are also interested in it to recommend application;
 * <li>{@link #ruleRecommended()} - TODO Issued after each recommendation visualization
 * <li>{@link #ruleApplied()} - TODO
 * </ol>
 * 
 * The canonical event-listener model for {@link Rule}s:
 * <ol>
 * <li>{@link RuleTrigger}s/{@link RuleRecommender}s are deployed by {@link Rule} ->
 * <li>the hosting {@link Rule} and {@link RuleRecommender}s ({@link RuleTriggeredListener}s) are 
 * notified by {@link #ruleTriggered()} or {@link #ruleMatched()} events ->
 * <li>other {@link Rule}s or {@link RuleTrigger}s ({@link RuleRecommenderListener}s) may be 
 * notified by {@link #ruleRecommended()} or {@link #ruleApplied()} events
 * </ol>
 * 
 * @author Kao, Chen-yi
 *
 */
public interface RuleListener extends RuleTriggerListener, RuleRecommenderListener {

}
