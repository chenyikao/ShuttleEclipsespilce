/**
 * 
 */
package tw.edu.nccu.shuttle.rule;

import java.util.EventListener;

/**
 * @author Kao, Chen-yi
 *
 */
public interface RuleTriggeredListener extends EventListener {
	
	/**
	 * @param e
	 */
	public void ruleTriggered(RuleEvent e);

}
