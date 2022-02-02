/**
 * 
 */
package tw.edu.nccu.shuttle.rule;

import java.util.EventListener;

/**
 * @author Kao, Chen-yi
 *
 */
public interface RuleMatchedListener extends EventListener {

	/**
	 * Only event notification. Actions taken after rule matched depend on what implementers need.
	 * 
	 * @param e
	 */
	public void ruleMatched(RuleEvent e);
	
	/**
	 * Only event notification. Actions taken after rule unmatched depend on what implementers need.
	 * 
	 * @param e
	 */
	public void ruleUnmatched(RuleEvent e);

}
