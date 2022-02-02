/**
 * 
 */
package tw.edu.nccu.shuttle.sandbox;

import java.util.HashSet;
import java.util.Hashtable;
import java.util.NoSuchElementException;
import java.util.Set;

/**
 * @author Kao, Chen-yi
 *
 */
public class MonitoredRuleTable<Key> {

	// static private class NoSuchAdapterException extends Exception {
	// }
	//	
	static final public NoSuchElementException NO_SUCH_RULE_EXCEPTION = 
		new NoSuchElementException();
	
	/**
	 * An efficient table for monitoring matched IDecoratorTargets and Rules (for
	 * accessing monitored ME's)
	 */
	final private Hashtable<Key, Set<Rule<?, ?>>> ruleTable = 
		new Hashtable<Key, Set<Rule<?, ?>>>();

	/**
	 * @param key
	 * @return monitored rules
	 * @throws NoSuchElementException
	 */
	public Iterable<Rule<?, ?>> getRules(Key key) throws NoSuchElementException {
		Iterable<Rule<?, ?>> monitoredKeyedRules = ruleTable.get(key);
		// if the rule table has the rule, then return it
		if (monitoredKeyedRules == null) throw NO_SUCH_RULE_EXCEPTION;
		else return monitoredKeyedRules;
	}
	
	public void monitorRule(Key key, Rule<?, ?> rule) {
		Set<Rule<?, ?>> monitoredKeyedRules = ruleTable.get(key);
		if (monitoredKeyedRules == null) {
			HashSet<Rule<?, ?>> newKeyedRules = new HashSet<Rule<?, ?>>();
			newKeyedRules.add(rule);
			ruleTable.put(key, newKeyedRules);
		} else {
			monitoredKeyedRules.add(rule);
		}
	}

	public void monitorRules(Key key, Set<Rule<?, ?>> rules) {
		ruleTable.put(key, rules);
	}

}
