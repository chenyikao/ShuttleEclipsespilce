/**
 * 
 */
package tw.edu.nccu.shuttle.rule;

import java.rmi.NoSuchObjectException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Set;

import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;

import tw.edu.nccu.shuttle.Editing;
import tw.edu.nccu.shuttle.ImpactArea;
import tw.edu.nccu.shuttle.IndexedCacheTable;
import tw.edu.nccu.shuttle.NLBoundaries;
import tw.edu.nccu.shuttle.NotYetAvailableException;
import tw.edu.nccu.shuttle.System;
import tw.edu.nccu.shuttle.WordNetSyncSet;
import tw.edu.nccu.shuttle.rule.WordNetSyncRule.WordNetSyncRecommender;
import tw.edu.nccu.shuttle.rule.WordNetSyncRule.WordNetSyncTrigger;
import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.IIndexWord;
import edu.mit.jwi.item.ISynset;
import edu.mit.jwi.item.ISynsetID;
import edu.mit.jwi.item.IWord;
import edu.mit.jwi.item.IWordID;
import edu.mit.jwi.item.POS;

/**
 * Representatives of the end points of Sub-bounded-string-Concepts Links.
 * 
 * WordNet SyncRule is responsible to manage the life cycle of
 * {@link WordNetSyncTrigger WordNet SyncTrigger}: born,
 * Concepts ({@link Rule}s) linked, Concepts re-linked (Trigger/Recommender linked to
 * new Rules: new/switched/removed links), dead and so-on.
 * 
 * Two splitting points: {@link #hookIntoCacheSolved(Object mpme)}
 * and {@link #getRecommenders(ImpactArea, WrappingLabel)}
 * 
 * {@link MANAGER_RULE}, {@link MANAGER_TRIGGER} and {@link MANAGER_RECOMMENDER} ALL are supposed to be write-once.
 *  
 * @author Kao, Chen-yi
 *
 * @param <Trigger>
 * @param <Recommender>
 */
abstract public class WordNetSyncRule
	<ModelElement, Trigger extends WordNetSyncTrigger<ModelElement>, Recommender extends WordNetSyncRecommender<ModelElement>> 
	extends Rule<ModelElement, Trigger, Recommender> {

	abstract public static class WordNetSyncTrigger<ModelElement> extends RuleTrigger<ModelElement> {
		abstract public NLBoundaries getMpmeTextBoundaries() throws NotYetAvailableException;
		abstract public WordNetSyncTrigger<ModelElement> newTrigger();
	};

	abstract public static class WordNetSyncRecommender<ModelElement> extends RuleRecommender<ModelElement> {
		abstract public WordNetSyncRecommender<ModelElement> newRecommender();
	};
	
	
	
	
	
	final public static NoSuchElementException NO_RULES_EXCEPTION = new NoSuchElementException();
	
	
	
	/**
	 * @aspect LazyJWIInitialization
	 */
	private static System Mp;
	private static IDictionary WordNet;
	
	/**
	 * Java local final variables in static methods are NOT static (value-
	 * memorized) actually!
	 */
	protected static WordNetSyncRule<?, WordNetSyncTrigger<?>, WordNetSyncRecommender<?>> MANAGER_RULE = null;
	protected static WordNetSyncTrigger<?> MANAGER_TRIGGER;
	protected static WordNetSyncRecommender<?> MANAGER_RECOMMENDER;
	
	
	
	protected WordNetSyncSet concept;
	
	private static IndexedCacheTable<
	ISynsetID, WordNetSyncRule<?, WordNetSyncTrigger<?>, WordNetSyncRecommender<?>>> 
		ConceptRuleCache = new IndexedCacheTable<
		ISynsetID, WordNetSyncRule<?, WordNetSyncTrigger<?>, WordNetSyncRecommender<?>>>();
	/**
	 * Caching Boundary-Concepts, Sub-bounded-string-(Index-Word)-Concepts and
	 * basic-extended-Concepts links for saving time of navigating WordNet.
	 * Shuttle does concept search based on the maximum set of Index Words, no
	 * matter what their part-of-speech is.
	 * 
	 * TODO: Sub-Bounded-String (SBS) -> Bounded-Substring (BS)?
	 * 
	 * @aspect Set - the rule sets allow NO duplicate rules
	 * @aspect Bound2ObjectThanWildcard
	 * 
	 */
	private static IndexedCacheTable<
	NLBoundaries, WordNetSyncRule<?, WordNetSyncTrigger<?>, WordNetSyncRecommender<?>>> 
		BoundaryConceptsLinkCache = new IndexedCacheTable<
		NLBoundaries, WordNetSyncRule<?, WordNetSyncTrigger<?>, WordNetSyncRecommender<?>>>();
	private static IndexedCacheTable<
	IIndexWord, WordNetSyncRule<?, WordNetSyncTrigger<?>, WordNetSyncRecommender<?>>>	
		WordConceptsLinkCache =	new IndexedCacheTable<
		IIndexWord, WordNetSyncRule<?, WordNetSyncTrigger<?>, WordNetSyncRecommender<?>>>();

	
	
	/**
	 * Constructor for supporting {@link System} manager rule.
	 * 
	 * @aspect LazyJWIInitialization
	 * @aspect ManagerWordNetSyncRule
	 * @param modelingPlatform The modeling platform place-holder
	 * @param managerTrigger
	 * @param managerRecommender
	 * @throws NoSuchObjectException When there's no WordNet engine found.
	 */
	@SuppressWarnings("unchecked")
	protected WordNetSyncRule(
			System modelingPlatform, 
			WordNetSyncTrigger<ModelElement> managerTrigger, 
			WordNetSyncRecommender<ModelElement> managerRecommender) throws NoSuchObjectException {
		
		if ((modelingPlatform == null) || (managerTrigger == null) || (managerRecommender == null))
			throw new IllegalArgumentException();
		concept = null;
		Mp = modelingPlatform;
		WordNet = modelingPlatform.getWordNet();
		WordNet.open();

		MANAGER_TRIGGER = (WordNetSyncTrigger<Object>) managerTrigger;
		MANAGER_RECOMMENDER = (WordNetSyncRecommender<Object>) managerRecommender;
		//	mark this instance as an 'pure' manager. ALL Rules are managers - 'pure' or 'non pure'.
		MANAGER_RULE = (WordNetSyncRule<?, WordNetSyncTrigger<?>, WordNetSyncRecommender<?>>) this;
	}
	
	/**
	 * @aspect IllegalArgumentException
	 * @param wnConcept
	 * @throws IllegalArgumentException
	 */
	protected WordNetSyncRule(WordNetSyncSet wnConcept) throws IllegalArgumentException {
		super();
		if (wnConcept == null) throw new IllegalArgumentException();
		concept = wnConcept;
	}


	
	abstract public WordNetSyncRule<ModelElement, Trigger, Recommender>	newRule(WordNetSyncSet wnConcept);
	
	@SuppressWarnings("unchecked")
	public Trigger newTrigger() {
		return (Trigger) MANAGER_TRIGGER.newTrigger();
	}
	
	@SuppressWarnings("unchecked")
	public Recommender newRecommender() {
		return (Recommender) MANAGER_RECOMMENDER.newRecommender();
	}

	
	
	/**
	 * @return the system
	 */
	public static System getSystem() {
		return Mp;
	}

	/**
	 * TODO: Relative to what?
	 * @return
	 */
	public int getRelativity() {
		return concept.getRelativity();
	}

	/**
	 * @aspect ManagerWordNetSyncRule
	 * @return
	 * @throws NotYetAvailableException
	 * 	When the manager rule is not available yet.
	 */
	public static final WordNetSyncRule<?,?,?> getManagerRule() throws NotYetAvailableException {
		if (MANAGER_RULE == null) throw new NotYetAvailableException("Manager rule"); 
		return MANAGER_RULE;
	}

	/**
	 * @return the manager trigger
	 * @throws NotYetAvailableException
	 * 	When the manager trigger is not available yet.
	 */
	public static WordNetSyncTrigger<?> getManagerTrigger() throws NotYetAvailableException {
		if (MANAGER_TRIGGER == null) throw new NotYetAvailableException("Manager trigger"); 
		return MANAGER_TRIGGER;
	}

	/**
	 * @return the manager recommender
	 * @throws NotYetAvailableException
	 * 	When the manager recommender is not available yet.
	 */
	public static WordNetSyncRecommender<?> getManagerRecommender() throws NotYetAvailableException {
		if (MANAGER_RECOMMENDER == null) throw new NotYetAvailableException("Manager recommender"); 
		return MANAGER_RECOMMENDER;
	}

	/**
	 * Traverse the Boundary-Concepts links to retrieve ALL related Rules
	 * (Concepts) (for notify recommending application, for example). Now ONLY
	 * traversing the Impact Area in Last Label Value.
	 * 
	 * @aspect Caching - Assume that each Impact Area is sent once and NO
	 *         caching needed.
	 * 
	 * @aspect ManagerWordNetSyncRule, SplittingGmfLabelSyncRule -
	 *         Possibly splitting new rule w/ empty Triggers/Recommenders. If
	 *         there's any rule split, it MUST be caused by the current MPME
	 *         ONLY - remember to connect new Rules and current MPME's
	 *         Triggers/Recommenders later.
	 * 
	 * @aspect SplittingGmfLabelSyncRule - Closure (inductive step) splitting
	 *         point - label editing.
	 * @param impactAreaInLast -
	 *            definitely non-null. Since this is a 'private' method, for
	 *            performance we don't check it. Because it involves the 'last'
	 *            label value ONLY, making there're NO splitting rules
	 *            currently. (@aspect ManagerWordNetSyncRule,
	 *            SplittingGmfLabelSyncRule)
	 * @return A {@link Set} of {@link WordNetSyncRule} related to the given {@link ImpactArea} - 
	 * For possible modification outside this method it had been synchronized.
	 * 
	 * @throws JWNLException -
	 *             generated through preparing splitting rules
	 */
	protected static Set<WordNetSyncRule<?,?,?>> getRules(ImpactArea<Integer, String> impactAreaInLast) 
	throws NoSuchElementException {
		
		if (impactAreaInLast == null) 
			throw tw.edu.nccu.shuttle.System.NULL_ARGUMENT_EXCEPTION;
		
		// NO duplicate rules allowed	@aspect Set
		Set<WordNetSyncRule<?,?,?>> impactConcepts = new HashSet<WordNetSyncRule<?,?,?>>();
		
		// @aspect SubBoundedStringIteration
		//	TODO: generic Boundary Iterator - Iterator<Integer> boundaryIterator() 
		Iterator<Editing<Integer>> impactAreaIterator = impactAreaInLast.iterator();
		if (impactAreaIterator.hasNext()) {
			String mpmeTextValue = impactAreaInLast.getHost();
			
			// previous and current boundaries
			int preBoundary, curBoundary = impactAreaIterator.next().getSubjectInX();
			while (impactAreaIterator.hasNext()) try {
				preBoundary = curBoundary;
				curBoundary = impactAreaIterator.next().getSubjectInX();
				// @aspect ManagerWordNetSyncRule, SplittingGmfLabelSyncRule
				//	split rules if it's necessary 
				for (WordNetSyncRule<?,?,?> concept : getRules(mpmeTextValue.substring(preBoundary, curBoundary)))
					// @aspect AvoidingDuplicates
					//	avoiding duplicates right by {@link java.util.Set Set}
					//	operations.
					impactConcepts.add(concept);
			} catch (NoSuchElementException noRulesException) {
				throw noRulesException;
			}
		}
	
		return Collections.synchronizedSet(impactConcepts);
	}

	/**
	 * @aspect Bound2ObjectThanWildcard -
	 * 
	 * TODO: @throws JWNLException - generated through preparing splitting rules
	 * 
	 * @param nlBoundaries
	 * @return A {@link Set} of {@link WordNetSyncRule} related to the given {@link NLBoundaries} - 
	 * For possible modification outside this method it had been synchronized.
	 */
	@SuppressWarnings("unchecked")
	protected static <GenericWnSyncRule extends WordNetSyncRule<?, ?, ?>>
	Set<GenericWnSyncRule> getRules(NLBoundaries nlBoundaries) throws NoSuchElementException {
		
		if (nlBoundaries == null) throw tw.edu.nccu.shuttle.System.NULL_ARGUMENT_EXCEPTION;
		
		try {
			
			// @aspect Caching
			return (Set<GenericWnSyncRule>) BoundaryConceptsLinkCache.getCacheSet(nlBoundaries);
			
		} catch (NoSuchElementException e) {	// when no cached rules
			
			Set<GenericWnSyncRule> linkedRules = new HashSet<GenericWnSyncRule>();
			
			// @aspect SubBoundedStringIteration
			Iterator<Integer> nlBoundaryIterator = nlBoundaries.iterator();
			if (nlBoundaryIterator.hasNext()) {
				String host = nlBoundaries.getHost();
				
				// previous and current boundaries
				int preBoundary, curBoundary = nlBoundaryIterator.next();
				while (nlBoundaryIterator.hasNext()) try {
					preBoundary = curBoundary;
					curBoundary = nlBoundaryIterator.next();
					// @aspect ManagerWordNetSyncRule
					// @aspect SplittingGmfLabelSyncRule
					//	split rules if it's necessary 
					for (WordNetSyncRule<?, WordNetSyncTrigger<?>, WordNetSyncRecommender<?>> concept :
						getRules(host.substring(preBoundary, curBoundary)))
						// @aspect AvoidingDuplicates
						//	avoiding duplicates right by {@link java.util.Set Set}
						//	operations.
						linkedRules.add((GenericWnSyncRule) concept);
				} catch (NoSuchElementException noRulesException) {
					throw noRulesException;
				}
			}
			
			// @aspect Caching and synchronized
			BoundaryConceptsLinkCache.indexAndCacheSet(
					nlBoundaries, 
					(Set<WordNetSyncRule<?, WordNetSyncTrigger<?>, WordNetSyncRecommender<?>>>) linkedRules);
			
			return linkedRules;
		}
		
	}

	/**
	 * @aspect Bound2ObjectThanWildcard -
	 * 
	 * TODO: cache negative matches
	 * TODO: @throws JWNLException - generated through preparing splitting rules
	 * 
	 * @param subBoundedString
	 * @return A {@link Set} of {@link WordNetSyncRule} related to the given string - 
	 * For possible modification outside this method it had been synchronized.
	 */
	@SuppressWarnings("unchecked")
	protected static 
	<GenericWnSyncRule extends WordNetSyncRule<?, WordNetSyncTrigger<?>, WordNetSyncRecommender<?>>> 
	Set<GenericWnSyncRule> getRules(String subBoundedString) throws NoSuchElementException {
		
		assert WordNet != null;	// @aspect ManagerWordNetSyncRule
		
		// filtering empty strings
		if (subBoundedString == null) throw NO_RULES_EXCEPTION;
		String canonicalSBS = subBoundedString.trim();
		if (canonicalSBS.length() == 0) throw NO_RULES_EXCEPTION;
		
		final POS[] all_POS = POS.values();		assert all_POS.length > 0;
		IIndexWord[] allPosWords = new IIndexWord[all_POS.length];
		boolean hasGotNewRules = false;
		Set<GenericWnSyncRule> rules = null;
		
		// Concept search, one concept one rule -> searching ConceptRuleCache
		//	and merging duplicate rules (via {@link java.util.Set Set} operation)
		//	@aspect OneConceptOneRule, MergingDuplicateRules
		// 1: WordNet Dictionary Lookup
		for (int i = 0; i < all_POS.length; i++) {
			allPosWords[i] = WordNet.getIndexWord(canonicalSBS, all_POS[i]);
			if (allPosWords[i] == null) continue;
			if (!hasGotNewRules) {
				try {
					// no matter what POS is, ALL share the same rules! -
					//	Find a representative IndexWord for a group, because any
					//	allIndexWords[i] may be null
					return (Set<GenericWnSyncRule>) WordConceptsLinkCache.getCacheSet(allPosWords[i]);
				} catch (NoSuchElementException e) {
					rules = new HashSet<GenericWnSyncRule>();
					hasGotNewRules = true;
				}
			}
			
			// 2: concept search	@aspect ConceptSearch
			// TODO: more OO - process concept search in WordNetSyncSet
			for (IWordID posWord : allPosWords[i].getWordIDs()) {
				// 2.1) basic concept search	@aspect ConceptSearch
				ISynsetID posConceptID = posWord.getSynsetID();					// relativity 1 
				rules.add((GenericWnSyncRule) getRule(posConceptID, 1));
				
				// @aspect ConceptSearch
				// TODO: rename extendedConceptXXX to advancedConceptXXX
				// 2.2.1) 1.5-hop extended concept search - 1-hop-further related 
				// (lexical) word search. Extended concepts include 1-hop-further
				// related words' Synsets.
				ISynset posConcept = WordNet.getSynset(posConceptID); 
				for (IWord posRelIndex : posConcept.getWords())					// relativity 2
					for (IWordID posRel2Lex : posRelIndex.getRelatedWords())	// relativity 3
						rules.add((GenericWnSyncRule) getRule(posRel2Lex.getSynsetID(), 3));
				
				// @aspect ConceptSearch
				// 2.2.2) 1-hop extended concept search - 1-hop-further (semantical) Synset 
				//	search. 
				for (ISynsetID posRelSemantic : posConcept.getRelatedSynsets())	// relativity 2
					rules.add((GenericWnSyncRule) getRule(posRelSemantic, 2));
			}
		}
		// final: direct concept link	@aspect ConceptSearch
		if (hasGotNewRules) {
			for (IIndexWord indexWord : allPosWords) 
				// rules to return are synchronized in {@link IndexedRuleTable#indexRules()}
				if (indexWord != null) WordConceptsLinkCache.indexAndCacheSet(
						indexWord, 
						(Set<WordNetSyncRule<?, WordNetSyncTrigger<?>, WordNetSyncRecommender<?>>>) rules);
		} else throw NO_RULES_EXCEPTION;
	
		return rules;
	}

	/**
	 * To protect the private constructor {@link #WordNetSyncRule(WordNetSyncSet)} from uncontrolled outside invocation,
	 * the chain of getRule(...) must be protected too.
	 * 
	 * @aspect Bound2ObjectThanWildcard
	 * 
	 * @param wnConceptID
	 * @param relativity 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected static 
	<GenericWnSyncRule extends WordNetSyncRule<?, WordNetSyncTrigger<?>, WordNetSyncRecommender<?>>> 
	GenericWnSyncRule getRule(ISynsetID wnConceptID, int relativity) {
		
		try {
			
			// @aspect Caching, OneConceptOneRule
			return (GenericWnSyncRule) ConceptRuleCache.getCache(wnConceptID);
			
		} catch (NoSuchElementException e) {
	
			GenericWnSyncRule rule = 
					(GenericWnSyncRule) MANAGER_RULE.newRule(new WordNetSyncSet(wnConceptID, relativity, WordNet));
	
			// @aspect HookingRuleIntoMPME
			//	TODO: integrate or keep separate the hooking process? -
			//	1) new Triggers/Recommenders if necessary;
			//	2) new Rules if necessary;
			//	3) for all new Rules - 
			//	3.1) rule.hookInto(mpmeAdaptable);
			//		Rule hooking is invoked by DecoratorProvider ONLY for performance
			//	3.2) rule.addTrigger(new Trigger);
			//	3.3) rule.addRecommender(new Recommender).
			
			// update {@link System} supported rules
			Mp.addRule((Rule<Object, ?, ?>) rule);
			// update the cache
			ConceptRuleCache.indexAndCache(wnConceptID, rule);
			
			return rule;
		}
	}

	
	
	/**
	 * TODO: generalize this factory method and put it to {@link RuleTrigger}
	 * TODO: parameterized as getTriggerOrRecommender<TriggerOrRecommender>(...)?
	 * 
	 * @param mpme
	 * @return
	 * @throws NotYetAvailableException When no cached triggers exist.
	 */
	@SuppressWarnings("unchecked")
	protected Trigger getCachedTrigger(ModelElement mpme) throws NotYetAvailableException {
		if (mpme == null) return (Trigger) MANAGER_TRIGGER;
		
		try {
			return super.getCachedTrigger(mpme);
		} catch (NotYetAvailableException e) {
			
			// @aspect InstancesReducingPerformance
			if (((WordNetSyncTrigger<ModelElement>)MANAGER_TRIGGER).isHookableInto(mpme)) {
				// @aspect ClosedTriggerRecommenderEnv - a private construction
				Trigger newTrigger = (Trigger) this.newTrigger();
				TRIGGER_CACHE.indexAndCache(mpme, newTrigger);
				return newTrigger;
				
			} else 
				// @aspect NegativeMatching
				throw new NotYetAvailableException("Cached trigger");
			
		}
	}

	/**
	 * TODO: generalize this factory method and put it to {@link RuleRecommender}
	 * TODO: parameterized as getTriggerOrRecommender<TriggerOrRecommender>(...)?
	 * @param mpme
	 * @return
	 * @throws NotYetAvailableException When there's no cached recommenders.
	 */
	@SuppressWarnings("unchecked")
	protected Recommender getCachedRecommender(ModelElement mpme)
			throws NotYetAvailableException {
		if (mpme == null) return (Recommender) MANAGER_RECOMMENDER;
		
		try {
			return super.getCachedRecommender(mpme);
		} catch (NotYetAvailableException e) {
			
			// @aspect InstancesReducingPerformance
			if (((WordNetSyncRecommender<ModelElement>)MANAGER_RECOMMENDER).isHookableInto(mpme)) {
				// @aspect ClosedTriggerRecommenderEnv - a private construction
				Recommender newRecommender = (Recommender) this.newRecommender();
				RECOMMENDER_CACHE.indexAndCache(mpme, newRecommender);
				return newRecommender;
				
			} else 
				// @aspect NegativeMatching
				throw new NotYetAvailableException("Cached recommenders");
			
		}
	}

	@Override
	public Recommender getCoupledRecommender(Trigger trigger) throws NullPointerException, NotYetAvailableException {
		if (trigger == null) throw tw.edu.nccu.shuttle.System.NULL_ARGUMENT_EXCEPTION;
		ModelElement mpmeForTrigger = trigger.getHookedMpme();
		assert mpmeForTrigger != null;
		return (Recommender) getCachedRecommender(mpmeForTrigger);
	}

	/**
	 * @see tw.edu.nccu.shuttle.rule.Rule#getTriggeredRecommenders(tw.edu.nccu.shuttle.rule.RuleTrigger)
	 */
	@Override
	public Set<Recommender> getTriggeredRecommenders(Trigger trigger) throws NullPointerException, NotYetAvailableException {
		
		// @aspect ManagerWordNetSyncRule
		//	TODO: if I'm a manager rule, do no work	else return ALL Recommenders EXCEPT the one coupled with source 
		//	Trigger itself
		
		try {
			return super.getTriggeredRecommenders(trigger);
		} catch (NotYetAvailableException e) {
			
			// @aspect ManagerWordNetSyncRule, SplittingGmfLabelSyncRule
			// If there's any rule split (a new rule has NO Triggers/Recommenders),
			// it MUST be caused by the current MPME ONLY - connect new Rules and 
			// current MPME's Triggers/Recommenders.
			addTrigger(trigger);
			addRecommender(getCoupledRecommender(trigger));
			throw new NotYetAvailableException("Triggered recommenders");

		}
	}

	
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		try {
			return ((WordNetSyncRule<?,?,?>) obj).equals(concept);
		} catch (ClassCastException e) {
			return false;
		}
	}

	
	
	/**
	 * @aspect ClosedTriggerRecommenderEnv 
	 * Closed and manufacturer-controlled trigger/recommender environment makes
	 * manufacturable trigggers/recommenders hookable.
	 * 
	 * @see tw.edu.nccu.shuttle.rule.Rule#isHookableIntoCacheSolved(Object)
	 */
	@Override
	protected boolean isHookableIntoCacheSolved(ModelElement mpme) {
		try {
			// @aspect ClosedTriggerRecommenderEnv
			getCachedTrigger(mpme);
			return true;
		} catch (NoSuchObjectException e) {/* do nothing at first! */}
		try {
			// @aspect ClosedTriggerRecommenderEnv
			getCachedRecommender(mpme);
			return true;
		} catch (NoSuchObjectException e) {
			return false;
		}
	}

	/**
	 * <i>Complete</i> overriding due to unique rule splitting mechanism 
	 * {@aspect ManagerWordNetSyncRule} and Trigger/Recommender scheme.
	 * TODO: merge this with general scheme (redesign general Trigger/Recommender 
	 * scheme?)
	 * 
	 * Hooking of WordNet SyncRule into a model element is done by the
	 * hooking of Triggers/Recommenders (where each Recommender is coupled with
	 * one Trigger) into that and connecting of Rule and Triggers/Recommenders.
	 * 
	 * Here the Rule manages (solves cache and one Trigger/Recommender per model
	 * element per Rule) and splits with new WordNet Concept specific Rules.
	 * 
	 * @aspect HookingRuleIntoMPME, ManagerWordNetSyncRule
	 * @see tw.edu.nccu.shuttle.rule.Rule#hookIntoCacheSolved(Object)
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void hookIntoCacheSolved(ModelElement mpme) throws UnsupportedOperationException, IllegalArgumentException {
		
		// generate/hook Triggers/Recommenders into model element -
		// 	one trigger per model element per rule, so retrieve
		//	Triggers/Recommenders cached or generate ones, 
		Trigger mpmeTrigger = null;
		Recommender mpmeRecommender = null;
		try {mpmeTrigger = getCachedTrigger(mpme);} 
		catch (NoSuchObjectException e) {
			assert false;	// should NOT happen since is checked to be hookable!
		}
		try {mpmeRecommender = getCachedRecommender(mpme);} 
		catch (NoSuchObjectException e) {
			assert false;	// should NOT happen since is checked to be hookable!
		}
		//	attach them to the model-element before adding hosts
		mpmeTrigger.hookInto(mpme);
		mpmeRecommender.hookInto(mpme);
		
		// connect Rule and Triggers/Recommenders - 
		// @aspect ManagerWordNetSyncRule
		//	For performance Manager Rule has NO Triggers/Recommenders assigned!
		// @aspect ManagerWordNetSyncRule, SplittingGmfLabelSyncRule
		//	Initial (basis) splitting point - label construction. 
		//	split rules if it's necessary 
		try {
			for (@SuppressWarnings("rawtypes") WordNetSyncRule concept : getRules(mpmeTrigger.getMpmeTextBoundaries())) {
				//	and attach them to rules w/ avoiding duplicates right by {@link java.util.Set Set} operations.
				concept.addTrigger(mpmeTrigger);
				concept.addRecommender(mpmeRecommender);
				// WordNet concept (rule) triggered right after trigger/recommender hooked and linked
				concept.ruleTriggered(new RuleEvent(concept, mpmeTrigger, null));
			}
		} catch (NotYetAvailableException e) {
			// Do nothing since there's NO label value boundaries at this time!
		} catch (NoSuchElementException e) {
			// Do nothing if there're no concepts (rules) detected.
		}
		
	}
	
	
	
	/**
	 * Going matched to 'unrecommend' application and un-hooking. This is a
	 * package-level parallel near-trigger(...) polymorphism, so we immediately
	 * re-direct this to unrecommendApplication(...) w/o invoking match(...) or
	 * matchToUnrecommend(...).
	 * 
	 * TODO: Group removeTrigger(trigger) and removeRecommender(coupledRecommender)
	 * as unhookFrom(mpmeAdaptable).
	 * 
	 * @aspect ShowingHidingHint
	 * @see tw.edu.nccu.shuttle.rule.RuleMatchedListener#ruleUnmatched(tw.edu.nccu.shuttle.rule.RuleEvent)
	 */
	@SuppressWarnings("unchecked")
	public void ruleUnmatched(RuleEvent re) {
		Trigger trigger = (Trigger) re.getTrigger();
		if (trigger == null) throw tw.edu.nccu.shuttle.System.NULL_ARGUMENT_EXCEPTION;
		
		try {
			Recommender coupledRecommender = 
				getCoupledRecommender(trigger);
			// Unrecommend the application
			coupledRecommender.ruleUnmatched(new RuleEvent(this, trigger, coupledRecommender));
			// remove Recommender links
			removeRecommender(coupledRecommender);
		} catch (NoSuchObjectException e) {
			// do nothing but remove ONLY Trigger links later
		}
		
		// remove Trigger links
		removeTrigger(trigger);
		
	}

	/**
	 * @see tw.edu.nccu.shuttle.rule.RuleRecommendedListener#ruleRecommended(tw.edu.nccu.shuttle.rule.RuleEvent)
	 */
	public void ruleRecommended(RuleEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Going matched to 'unrecommend' application for Triggered MPMEs (WITHOUT 
	 * removeTrigger(trigger) and removeRecommender(coupledRecommender)). This is a
	 * package-level parallel near-trigger(...) polymorphism, so we immediately
	 * re-direct this to unrecommendApplication(...) w/o invoking match(...) or
	 * matchToUnrecommend(...).
	 * 
	 * @aspect ShowingHidingHint
	 * @param trigger
	 * @throws NullPointerException - when {@code trigger} is null.
	 */
//	void triggerToUnrecommendHistory(Trigger trigger)
//			throws NullPointerException {
//				if (trigger == null) 
//					throw tw.edu.nccu.shuttle.System.NULL_ARGUMENT_EXCEPTION;
//				
//				for (Recommender triggeredRecommender : 
//					getTriggeredRecommenders(trigger))
//					triggeredRecommender.ruleUnmatched(new RuleEvent(this, trigger, triggeredRecommender));
//			
//			}

	
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return concept.toString();
	}

}
