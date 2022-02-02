package tw.edu.nccu.shuttle.rule;

import java.util.Collections;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;

import tw.edu.nccu.shuttle.IndexedCacheTable;
import tw.edu.nccu.shuttle.MPHook;
import tw.edu.nccu.shuttle.NotYetAvailableException;
import tw.edu.nccu.shuttle.System;




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
public abstract class Rule<
	ModelElement, Trigger extends RuleTrigger<ModelElement>, Recommender extends RuleRecommender<ModelElement>> 
	extends MPHook<ModelElement> implements RuleListener {

	protected static final IndexedCacheTable<Object, RuleTrigger<?>> TRIGGER_CACHE = 
			new IndexedCacheTable<Object, RuleTrigger<?>>();
	protected static final IndexedCacheTable<Object, RuleRecommender<?>> RECOMMENDER_CACHE = 
			new IndexedCacheTable<Object, RuleRecommender<?>>();

	
	
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
		triggers = Collections.synchronizedSet(new HashSet<Trigger>());
		recommenders = Collections.synchronizedSet(new HashSet<Recommender>());
	}
	
	public boolean isRule() {return true;}

	
	
	/**
	 * A {@link Rule} is triggered to recommend match.
	 * 
	 * TODO: {@link Rule} should be triggered by supported and existing MPMEs, 
	 * which are deployed while being "decorated".
	 * 
	 * TODO: Triggering flow:
	 * One-step trigger/match?
	 * trigger(triggeredMPME)?
	 * ...
	 * Check the triggering coverage in Antecedent
	 * -> if Antecedent is fully covered by triggering
	 * -> Rule is matched (i.e. {@link #match()}).
	 * 
	 * @see tw.edu.nccu.shuttle.rule.RuleTriggeredListener#ruleTriggered(tw.edu.nccu.shuttle.rule.RuleEvent)
	 * @generated
	 */
	@SuppressWarnings("unchecked")
	public void ruleTriggered(RuleEvent event) {
		Trigger t = (Trigger) event.getTrigger();
		if (t == null) return;	// do nothing if no any Trigger given
		
		try {
			for (Recommender recommender : getTriggeredRecommenders(t)) {
				// SHOULD NOT be duplicated recommenders here (everyone is newly created!):
				//	assert(recommenders.add(recommender) == true);
				recommender.ruleTriggered(new RuleEvent(this, event.getTrigger(), recommender));
			}
		} catch (NotYetAvailableException e) {
			return;				// do nothing if no Recommenders available neither
		}
	}

	/**
	 * A Rule is matched to recommend application.
	 * 
	 * @param e
	 * @see tw.edu.nccu.shuttle.rule.RuleMatchedListener#ruleMatched(tw.edu.nccu.shuttle.rule.RuleEvent)
	 * @generated
	 */
	public void ruleMatched(RuleEvent e) {
		// TODO: Currently just plays a notification to {@link Rule}.
		//	recommender.recommendApplication(triggeredMPMESelections, consequentMPMESelections)?
		e.getRecommender().ruleMatched(e);
	}


	
	/**
	 * TODO: handle priority changes
	 * -> recommendation approval/disapproval (to Recommender)
	 * -> if there's an approval -> apply
	 * -> if there's a disapproval -> froze the rule (set the rule
	 * state as "frozen" and downgrade the rule priority to the 
	 * lowest)
	 * 
	 * @see tw.edu.nccu.shuttle.rule.RuleAppliedListener#ruleApplied(tw.edu.nccu.shuttle.rule.RuleEvent)
	 * @generated
	 */
	public void ruleApplied(RuleEvent e) {
	}



	/**
	 * This implementation is a delegation to Triggers and Recommenders. 
	 * 
	 * @see tw.edu.nccu.shuttle.MPHook#hookIntoCacheSolved(Object)
	 */
	protected void hookIntoCacheSolved(ModelElement mpme)
			throws UnsupportedOperationException, IllegalArgumentException {
		for (Trigger trigger : triggers)
			trigger.hookInto(mpme);
		for (Recommender recommender : recommenders)
			recommender.hookInto(mpme);
	}

	/**
	 * This implementation is a caching and delegation to Triggers
	 * and Recommenders. Don't override this until you're sure to use your own
	 * caching technologies or want no caching at all!
	 * 
	 * @see tw.edu.nccu.shuttle.MPHook#isHookableIntoCacheSolved(Object)
	 */
	protected boolean isHookableIntoCacheSolved(ModelElement mpme) {
		for (Trigger trigger : triggers)
			if (trigger.isHookableInto(mpme))
				return true;
		for (Recommender recommender : recommenders)
			if (recommender.isHookableInto(mpme))
				return true;

		return false;
	}

//	public void setMostHookable(boolean mostHookable) {
//	if (mostHookable) 
//}

	
	
	@SuppressWarnings("unchecked")
	protected Trigger getCachedTrigger(ModelElement mpme) throws NullPointerException, NotYetAvailableException {
		if (mpme == null) throw tw.edu.nccu.shuttle.System.NULL_ARGUMENT_EXCEPTION;

		Trigger trigger = null;
		try {
			trigger = (Trigger) TRIGGER_CACHE.getCache(mpme);
		} catch (NoSuchElementException e) {
			for (Trigger t : triggers)
				if (t.hookedMpme == mpme) {
					trigger = t;
					TRIGGER_CACHE.indexAndCache(mpme, t);
				}
		}
		if (trigger == null) throw new NotYetAvailableException("Triggers");
		return trigger;
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
		if (triggers.add(trigger)) trigger.addHost(this);
	}

	public void removeTrigger(Trigger trigger) {
		// @aspect AvoidingCycle
		if (triggers.remove(trigger)) trigger.removeHost(this);
	}

	
	
	@SuppressWarnings("unchecked")
	protected Recommender getCachedRecommender(ModelElement mpme) throws NullPointerException, NotYetAvailableException {
		if (mpme == null) throw tw.edu.nccu.shuttle.System.NULL_ARGUMENT_EXCEPTION;
		
		Recommender recomm = null;
		try {
			recomm = (Recommender) RECOMMENDER_CACHE.getCache(mpme);
		} catch (NoSuchElementException e) {
			for (Recommender r : recommenders)
				if (r.hookedMpme == mpme) {
					recomm = r;
					RECOMMENDER_CACHE.indexAndCache(mpme, r);
				}
		}
		if (recomm == null) throw new NotYetAvailableException("Recommenders");
		return recomm;
	}

	/**
	 * Read-only getter.
	 * @return the recommenders
	 */
	public Set<Recommender> getRecommenders() {
		return recommenders;
	}

	abstract public Recommender getCoupledRecommender(Trigger trigger)
			throws NullPointerException, NotYetAvailableException; 
	
	@SuppressWarnings("unchecked")
	public Recommender getCoupledRecommender(ModelElement mpme) throws NullPointerException, NotYetAvailableException {
		Recommender recomm = null;
		try {
			recomm = (Recommender) RECOMMENDER_CACHE.getCache(mpme);
		} catch (NoSuchElementException e) {
			recomm = getCoupledRecommender(getCachedTrigger(mpme));
			RECOMMENDER_CACHE.indexAndCache(mpme, recomm);
		}
		return recomm;
	}
	
	/**
	 * Not like {@link Trigger#getTriggeredRecommenders()}, this method returns {@link Recommender}s
	 * triggered by only this {@link Rule}'s match.
	 * 
	 * Currently we ignore Recommendations occured on Triggering MPMEs
	 * themselves (e.g. 'recommendApplyApply' -> 'recommendApplyExec' should
	 * cause the Recommendation on ALL 'apply's). Therefore the Triggered
	 * Recommenders we consider EXCLUDE ones on Triggering MPMEs. 
	 * TODO: consider the Recommendations on Triggering MPMEs
	 * 
	 * TODO: A Final Application Recommendation is the combination of all
	 * possible predictions (Substitution, NO, Deletion and Legacy [unchanged]
	 * predictions)(via Jazzy or Levenshtein Algorithm?).
	 * 
	 * @param trigger
	 * @return
	 * @throws NullPointerException
	 * @throws NotYetAvailableException
	 * 
	 * @see Trigger#getTriggeredRecommenders()
	 */
	public Set<Recommender> getTriggeredRecommenders(Trigger trigger) throws NullPointerException, NotYetAvailableException {
		if (trigger == null) throw tw.edu.nccu.shuttle.System.NULL_ARGUMENT_EXCEPTION;

		Recommender coupledRecomm = null;
		try {
			coupledRecomm = getCoupledRecommender(trigger);
		} catch (NotYetAvailableException e) {
			// should NOT happen! Trigger and Recommender are meant to be coupled!
			assert false;
		}
		
		Iterable<Recommender> thisRuleRecomms = getRecommenders(); 
		if (!thisRuleRecomms.iterator().hasNext()) throw new NotYetAvailableException("Recommenders");
		
		// merging duplicate Recommenders (via {@link java.util.Set Set} 
		//	operation) and excluding one coupled with source Trigger
		//	@aspect OneRecommenderOneMeType, MergingDuplicateRecommenders
		Set<Recommender> triggeredRecomms = new HashSet<Recommender>();
		for (Recommender mpmeRecommender : thisRuleRecomms) 
			// EXCLUDING Recommenders on Triggering MPMEs
			if (mpmeRecommender != coupledRecomm) 
				triggeredRecomms.add(mpmeRecommender);
		
		return Collections.synchronizedSet(triggeredRecomms);
		
	}

	/**
	 * There are two possible ways to get triggered {@link Recommender}s:
	 * <ol>
	 * <li> <code>getCachedTrigger(mpme).getTriggeredRecommenders()</code>;
	 * <li> <code>getTriggeredRecommenders((Trigger) getCachedTrigger(mpme))</code>.
	 * </ol>
	 * Both shall return consistent results, which means
	 * <pre><code>assert getCachedTrigger(mpme).getTriggeredRecommenders().equals(
	 * getTriggeredRecommenders((Trigger) getCachedTrigger(mpme)));</code></pre>
	 * 
	 * @param mpme
	 * @return
	 * @throws NullPointerException
	 * @throws NotYetAvailableException
	 */
	@SuppressWarnings("unchecked")
	public Set<Recommender> getTriggeredRecommenders(ModelElement mpme) throws NullPointerException, NotYetAvailableException {
		return (Set<Recommender>) getCachedTrigger(mpme).getTriggeredRecommenders();
	}

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
