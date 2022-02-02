/**
 * 
 */
package tw.edu.nccu.shuttle.rule;

import java.util.EventListener;

/**
 * @author Kao, Chen-yi
 *
 */
public interface RuleAppliedListener extends EventListener {

	public void ruleApplied(RuleEvent e);

}
