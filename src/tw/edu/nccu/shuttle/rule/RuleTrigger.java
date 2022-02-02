/**
 * 
 */
package tw.edu.nccu.shuttle.rule;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import tw.edu.nccu.shuttle.MPHook;
import tw.edu.nccu.shuttle.NotYetAvailableException;

/**
 * One trigger per model element per rule.
 * 
 * @author Kao, Chen-yi
 *
 */
public abstract class RuleTrigger<ModelElement> extends MPHook<ModelElement> {

	protected Set<Rule<ModelElement, RuleTrigger<ModelElement>, RuleRecommender<ModelElement>>> hosts;
	/**
	 * Using thread-safe {@link Vector}s in a public listener access environment.
	 */
	protected List<RuleTriggeredListener> rtListeners = new Vector<RuleTriggeredListener>();
	protected List<RuleMatchedListener> rmListeners = new Vector<RuleMatchedListener>();
	
	protected ModelElement hookedMpme = null;
	
	
	
	protected RuleTrigger() {
		super();
		hosts = new HashSet<Rule<ModelElement, RuleTrigger<ModelElement>, RuleRecommender<ModelElement>>>();
	}
	
	public boolean isTrigger() {return true;}

	
	
	@SuppressWarnings("unchecked")
	public <Trigger extends RuleTrigger<ModelElement>> void addHost(
			Rule<ModelElement, Trigger, ? extends RuleRecommender<ModelElement>> host) {

		// @aspect AvoidingCycle - no adding trigger again if {@link #hosts} already contains {@link #host}
		if (hosts.add((Rule<ModelElement, RuleTrigger<ModelElement>, RuleRecommender<ModelElement>>) host)) {
			host.addTrigger((Trigger) this);
			/**
			 * @aspect AddHostAddListenerDualInterfaceFlexibility
			 * A {@link RuleTriggerListener} is NOT necessarily a host rule.
			 */
			addListener(host);
		}
	}

	@SuppressWarnings("unchecked")
	public <Trigger extends RuleTrigger<ModelElement>> void removeHost(Rule<ModelElement, Trigger, ?> host) {
		if (hosts.remove(host)) {	// @aspect AvoidingCycle
			host.removeTrigger((Trigger) this);
			// @aspect AddHostAddListenerDualInterfaceFlexibility
			removeListener(host);
		}
	}

	
	
	public void addListener(RuleTriggerListener listener) {
		rtListeners.add(listener);
		rmListeners.add(listener);
	}
	
	public void removeListener(RuleTriggerListener listener) {
		rtListeners.remove(listener);
		rmListeners.remove(listener);
	}
	
	public void addListener(RuleTriggeredListener listener) {
		rtListeners.add(listener);
	}
	
	public void removeListener(RuleTriggeredListener listener) {
		rtListeners.remove(listener);
	}
	
	public void addListener(RuleMatchedListener listener) {
		rmListeners.add(listener);
	}
	
	public void removeListener(RuleMatchedListener listener) {
		rmListeners.remove(listener);
	}
	

	
	/**
	 * @return the hookedMpme
	 * @throws NotYetAvailableException When the hooked MPME is not yet available.
	 */
	public ModelElement getHookedMpme() throws NotYetAvailableException {
		if (hookedMpme == null) throw new NotYetAvailableException("MPME");
		return hookedMpme;
	}

	/**
	 * @return {@link Recommender}s triggered by ALL {@link Rule host}s' match.
	 * @throws NullPointerException
	 * @throws NotYetAvailableException
	 * @see Rule#getTriggeredRecommenders()
	 */
	public Set<? extends RuleRecommender<ModelElement>> getTriggeredRecommenders()
			throws NullPointerException, NotYetAvailableException {
		
		Set<RuleRecommender<ModelElement>> triggeredRecomms = new HashSet<RuleRecommender<ModelElement>>();
		
		for (Rule<ModelElement, RuleTrigger<ModelElement>, RuleRecommender<ModelElement>> host : 
			hosts) triggeredRecomms.addAll(host.getTriggeredRecommenders(this));
		
		return Collections.synchronizedSet(triggeredRecomms);
		
	}

	
	
	/**
	 * @see tw.edu.nccu.shuttle.MPHook#hookIntoCacheSolved(Object)
	 */
	@Override
	protected void hookIntoCacheSolved(ModelElement mpme)
			throws UnsupportedOperationException, IllegalArgumentException {
		hookedMpme = mpme;
	}
	
}
