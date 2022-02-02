package tw.edu.nccu.shuttle.sandbox;

import java.rmi.NoSuchObjectException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;



/**
 * Concept(Rule)-related information is handled by the host rule.
 * 
 * TODO: Hierarchical MP inference - Rules and inferences can be multi-leveled.
 * Rules of higher levels ask ones of lower levels for concrete visualization of
 * triggering, matching and recommendation.<br/>
 * 
 * Use Visitor or Containment pattern? Or leave it to MP Sub-inference-engine?<br/>
 * 
 * ONLY care for the hierarchies involving MPs. Leave other hierarchical
 * inferences to KAON2.
 * 
 * <pre>
 *  Only in MP-level Rule should know MPMEs 
 *  -&gt; MPRule SHOULD be separated from Rule
 * </pre>
 * 
 * This abstract class is already geared with some caching technology for saving 
 * from possible redundant hookability checkings in Triggers and Recommenders.
 * 
 * @author Kao, Chen-yi
 * @model
 * 
 */
public abstract class Rule
	<Trigger extends RuleTrigger, Recommender extends RuleRecommender> 
	extends MPHook {

	protected Set<Trigger> triggers;
	
	/**
	 * TODO: Recommender should be imported and shared from {@link System}?
	 * 
	 * @model
	 */
	protected Set<Recommender> recommenders;
	
	public Rule() {
		super();
		// TODO: needs to save memory initially (limit the initial capacity
		// reasonably)?
		triggers = new HashSet<Trigger>();
		recommenders = new HashSet<Recommender>();
	}

//	public void setMostHookable(boolean mostHookable) {
//		if (mostHookable) 
//	}
	
	/**
	 * A Rule is triggered to recommend match.
	 * 
	 * TODO: Rule should be triggered by supported and existing MPMEs, 
	 * which are deployed
	 * with Rule Triggering Listener (another face of MP Sub-inference engine)
	 * while being "decorated".
	 * 
	 * TODO: trigger(triggeredMPME)?
	 * 
	 * TODO: triggering flow
	 * ...
	 * Check the triggering coverage in Antecedent
	 * -> if Antecedent is fully covered by triggering
	 * -> Rule is matched (i.e. {@link #match()}).
	 * 
	 * @generated
	 */
	public void trigger(Trigger trigger) {
		// TODO: for temporary demo. One-step trigger/match
		for (Recommender recommender : getTriggeredRecommenders(trigger)) {
			recommender.recommendMatch(this, trigger);
		}
		// SHOULD NOT be duplicated recommenders here (everyone is newly created!)
//		assert(recommenders.add(newRecommender) == true);
//		return newRecommender;
	}
	
	/**
	 * A Rule is matched to recommend application.
	 * 
	 * @param trigger TODO
	 * @param recommender
	 * @generated
	 */
	public void match(Trigger trigger, Recommender recommender) {
		// TODO: Currently just plays a notification to {@link Rule}.
		//	recommender.recommendApplication(triggeredMPMESelections, consequentMPMESelections)?
		recommender.recommendApplication(this, trigger);
	}

	/**
	 * @generated
	 */
	public void apply() {
		// ??handle priority changes
	}

	/**
	 * This implementation is a delegation to Triggers and Recommenders. 
	 * 
	 * @see tw.edu.nccu.shuttle.sandbox.MPHook#hookIntoCacheSolved(org.eclipse.core.runtime.IAdaptable)
	 */
	protected void hookIntoCacheSolved(IAdaptable mpmeAdaptable)
			throws UnsupportedOperationException, IllegalArgumentException {
		for (Trigger trigger : triggers)
			trigger.hookInto(mpmeAdaptable);
		for (Recommender recommender : recommenders)
			recommender.hookInto(mpmeAdaptable);
	}

	/**
	 * This implementation is a caching and delegation to Triggers
	 * and Recommenders. Don't override this until you're sure to use your own
	 * caching technologies or want no caching at all!
	 * 
	 * @see tw.edu.nccu.shuttle.sandbox.MPHook#isHookableTo(org.eclipse.core.runtime.IAdaptable)
	 */
	protected boolean isHookableIntoCacheSolved(IAdaptable mpmeAdaptable) {
		for (Trigger trigger : triggers)
			if (trigger.isHookableInto(mpmeAdaptable))
				return true;
		for (Recommender recommender : recommenders)
			if (recommender.isHookableInto(mpmeAdaptable))
				return true;

		return false;
	}

	/**
	 * Read-only getter.
	 * @return the triggers
	 */
	public Iterable<Trigger> getTriggers() {
		return triggers;
	}

	public void addTrigger(Trigger trigger) {
		// @aspect AvoidingCycle
		if (!triggers.contains(trigger)) {
			triggers.add(trigger);
			trigger.addHost(this);
		}
	}

	public void removeTrigger(Trigger trigger) {
		// @aspect AvoidingCycle
		if (triggers.contains(trigger)) {
			triggers.remove(trigger);
			trigger.removeHost(this);
		}
	}

	/**
	 * Read-only getter.
	 * @return the recommenders
	 */
	public Iterable<Recommender> getRecommenders() {
		return recommenders;
	}

	abstract public Recommender getCoupledRecommender(Trigger trigger) 
	throws NullPointerException, NoSuchObjectException; 
	
	abstract public Iterable<Recommender> getTriggeredRecommenders(Trigger trigger) 
	throws NullPointerException;

	public void addRecommender(Recommender recommender) {
		// @aspect AvoidingCycle
		if (!recommenders.contains(recommender)) {
			recommenders.add(recommender);
			recommender.addHost(this);
		}
	}

	public void removeRecommender(Recommender recommender) {
		// @aspect AvoidingCycle
		if (recommenders.contains(recommender)) {
			recommenders.remove(recommender);
			recommender.removeHost(this);
		}
	}

}
