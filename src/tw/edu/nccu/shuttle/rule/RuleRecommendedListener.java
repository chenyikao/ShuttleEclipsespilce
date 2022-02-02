/**
 * 
 */
package tw.edu.nccu.shuttle.rule;

import java.util.EventListener;

/**
 * @author Kao, Chen-yi
 *
 */
public interface RuleRecommendedListener extends EventListener {

	public void ruleRecommended(RuleEvent e);

}
