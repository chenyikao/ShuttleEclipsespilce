/**
 */
package tw.edu.nccu.shuttle.texteditor;

import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentListener;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;

import tw.edu.nccu.shuttle.ImpactArea;
import tw.edu.nccu.shuttle.ModelingPlatform;
import tw.edu.nccu.shuttle.NLBoundaries;
import tw.edu.nccu.shuttle.NotYetAvailableException;
import tw.edu.nccu.shuttle.WordNetSyncSet;
import tw.edu.nccu.shuttle.rule.Recommendation;
import tw.edu.nccu.shuttle.rule.Rule;
import tw.edu.nccu.shuttle.rule.RuleEvent;
import tw.edu.nccu.shuttle.rule.RuleRecommender;
import tw.edu.nccu.shuttle.rule.RuleTrigger;
import tw.edu.nccu.shuttle.rule.WordNetSyncRule;

/**
 * @aspect OneTriggerRecommenderPerModelElementType
 *  
 * @aspect TwoLevelTriggerRecommenders
 * TODO: Top level: One-document-one-trigger/recommender for scalability in mega-
 * team project collaboration. 
 * ME level: One-ME-type-one-trigger/recommender for simpler design. ME level 
 * triggers/recommenders are lazily loaded.
 * 
 * @author Kao, Chen-yi
 * 
 */
public class TextWordNetSyncRule extends WordNetSyncRule<
DocumentedRegion, TextWordNetSyncRule.TextWordNetSyncTrigger, TextWordNetSyncRule.TextWordNetSyncRecommender> {

	/**
	 * @aspect ClosedTriggerRecommenderEnv - innering class to prevent the
	 *         addTrigger(...) with inherited or constructed classes of
	 *         out-side world
	 *         
	 * @field {@link #hookedMpme} - 
	 * a {@link DocumentedRegion text region} with its hosting {@link IDocument document} on the modeling platform.
	 * 
	 */
	static protected class TextWordNetSyncTrigger 
		extends WordNetSyncRule.WordNetSyncTrigger<DocumentedRegion> implements IDocumentListener {

		private String mpmeText = null;
		private String lastMpmeText = null;
		private DocumentBoundaries mpmeTextBoundary = null;
		
//		private Set<String> impactHistory = null;
		private ImpactArea<Integer, String> impactAreaInLast = null;
		

		
		@Override
		public TextWordNetSyncTrigger newTrigger() {
			return new TextWordNetSyncTrigger();
		}
		

		
		@Override
		public NLBoundaries getMpmeTextBoundaries() throws NotYetAvailableException {
			if (mpmeTextBoundary == null) throw ModelingPlatform.MPME_NOT_YET_AVAILABLE_EXCEPTION;
			return mpmeTextBoundary;
		}
		
		/**
		 * @return the MPME text
		 * @throws NotYetAvailableException TODO
		 */
		public String getMpmeText() throws NotYetAvailableException {
			if (mpmeText == null) throw ModelingPlatform.MPME_NOT_YET_AVAILABLE_EXCEPTION;
			return mpmeText;
		}

		/**
		 * @return last MPME text
		 */
		public String getLastMpmeText() throws NotYetAvailableException {
			if (lastMpmeText == null) throw ModelingPlatform.MPME_MOD_NOT_YET_AVAILABLE_EXCEPTION;
			return lastMpmeText;
		}

		/**
		 * @return the impactAreaInLast
		 */
		public ImpactArea<Integer, String> getImpactAreaInLast() throws NotYetAvailableException {
			if (impactAreaInLast == null) throw ModelingPlatform.MPME_MOD_NOT_YET_AVAILABLE_EXCEPTION;
			return impactAreaInLast;
		}

		/**
		 * Collecting MPMEs that overlapping the given region.
		 * 
		 * @see tw.edu.nccu.shuttle.rule.RuleTrigger#getTriggeredRecommenders()
		 */
		@SuppressWarnings("unchecked")
		@Override
		public Set<? extends RuleRecommender<DocumentedRegion>> getTriggeredRecommenders()
				throws NullPointerException, NotYetAvailableException {
//			int regionOffset = hookedMpme.getOffset(); 
//			int regionEndOffset = regionOffset + hookedMpme.getLength();
			
			Set<TextWordNetSyncRecommender> allRecomms = new HashSet<TextWordNetSyncRecommender>();
			for (Rule<DocumentedRegion, ? extends RuleTrigger<DocumentedRegion>, ? extends RuleRecommender<DocumentedRegion>> 
				rule : hosts) allRecomms.addAll((Set<TextWordNetSyncRecommender>) rule.getRecommenders());
			return allRecomms;
			
//			Set<TextWordNetSyncRecommender> trs = Collections.synchronizedSet(new HashSet<TextWordNetSyncRecommender>());
//			Iterator<TextWordNetSyncRecommender> IteRecomms = allRecomms.iterator();
//			while (IteRecomms.hasNext()) try {
//				TextWordNetSyncRecommender recomm = IteRecomms.next();
//				DocumentedRegion recommRegion = recomm.getHookedMpme();
//				int rrOffset = recommRegion.getOffset(); 
//				int rrEndOffset = rrOffset + recommRegion.getLength();
//				if ((rrEndOffset >= regionOffset && rrEndOffset <= regionEndOffset) 
//						|| (rrOffset >= regionOffset && rrOffset <= regionEndOffset)) trs.add(recomm);
//			
//			} catch (ClassCastException e) {continue;}	// For heterogeneous MP
//			return trs;
		}



		/**
		 * @see tw.edu.nccu.shuttle.MPHook#isHookableIntoCacheSolved(Object)
		 */
		protected boolean isHookableIntoCacheSolved(DocumentedRegion mpme) {
			return true;
		}
		
		/**
		 * @aspect DelayedHooking
		 * @see tw.edu.nccu.shuttle.rule.RuleTrigger#hookIntoCacheSolved(Object)
		 */
		protected void hookIntoCacheSolved(DocumentedRegion mpme) throws IllegalArgumentException {
			super.hookIntoCacheSolved(mpme);
			
			DocumentedRegion dr = (DocumentedRegion) mpme;
			dr.getDocument().addDocumentListener(this);
			
			// @aspect ManagerWordNetSyncRule
			//	after the following label has boundary detected!
			mpmeText = dr.toString();
			try {
				mpmeTextBoundary = new DocumentBoundaries(dr);
			} catch (BadLocationException e) {	// Shall not happen!
			}
		}

		
		
		public void documentAboutToBeChanged(DocumentEvent event) {
			// TODO Auto-generated method stub
			
		}

		public void documentChanged(DocumentEvent event) {
//			String newLabelValue = hookedMpme.getText();
//			if (!newLabelValue.equals(mpmeText)) {
////				if (impactHistory == null) 
////					impactHistory = new HashSet<String>();
////				
////				impactHistory.add(labelValue);
//				// update first for later Recommenders' accessing
//				lastMpmeText = mpmeText;
//				mpmeText = newLabelValue;
//
//				/**
//				 * -> updating boundaries and rules,
//				 * boundary-Concept (Rule) dislinking/linking/generation
//				 * (TODO: dislink: Concepts = Concepts - [Concepts - new Concepts] -> 
//				 * link: Concepts = Concepts + [new Concepts - Concepts], 
//				 * shrink then grow the set size?)
//				 * 
//				 * @aspect ShowingHidingHint
//				 * -> Arrange Hint showing/hiding - HintForConcepts
//				 * Show/hide the Recommendation Hint in corresponding MPME if it's 
//				 * shown according to Sub-Bounded-String (SBS) editing and Concepts 
//				 * changing.
//				 * 
//				 * 1) for Triggering MPMEs 
//				 * (hints here are shown, or 'en-set', while being Triggered MPMEs):
//				 * 1.1) for deleted Concepts: de-set HintForConcepts.
//				 * 	match->unrecommendApplication(...) and delete Trigger/Recommender 
//				 * 	links
//				 * 1.2) for added Concepts: not the target to be triggered therefore 
//				 * 	not interested
//				 * 1.3) for unchanged Concepts: of course no changes for hints
//				 * 
//				 * 2) for Triggered MPMEs:
//				 * 2.1) for 'likely changing' SBS: en-set HintForConcepts
//				 * 	Operation points - match->recommendApplication(...)
//				 * 2.2) TODO - for 'back' SBS: like the situation of 
//				 * 	"apply"->"..."->"apply". When "apply" is back, 
//				 * 	we need Impact History roll-back 
//				 * 	mechanism, which hints that "Sync. source value is undo..." on 
//				 * 	dirty MPMEs and triggerToUnrecommendHistory(...) for clean MPMEs.
//				 * 
//				 * 3) then if HintForConcept is NOT empty, show Hint, otherwise 
//				 * hide it
//				 * 
//				 */
//
//				// trigger the host rule (need NOT to recommend match) -> re-iterate
//				// breaks for new boundaries
//				try {
//					// TODO: focusing on old (last) Impact Segment is enough for
//					// today's recommendation!
//					impactAreaInLast = 
//						mpmeTextBoundary.getImpactAreaInHost(mpmeText);
//					
//					// for Triggered MPMEs - Rules (Concepts) invloved in
//					// ImpactArea are triggered! (excluding Recommenders on
//					// Triggering MPMEs AGAIN!)
//					for (TextWordNetSyncRule likelyChangingConcept 
//							: TextWordNetSyncRule.getRules(impactAreaInLast))
//						likelyChangingConcept.trigger(this);
//					
//				} catch (NoSuchObjectException e) {
//					// if there's NO Impact Area, then do nothing (including 
//					//	triggering changing concepts) for now!
//				}
////				triggerToUnrecommendHistory(this);
//				
//				NLBoundaries newBoundaries = new NLBoundaries(mpmeText);
//
//				// for Triggering MPMEs - check deleted Concepts to de-link
//				//	Triggers/Recommenders and un-recommend application
//				Collection<TextWordNetSyncRule> newConcepts = 
//					TextWordNetSyncRule.getRules(newBoundaries);
//				for (TextWordNetSyncRule lastConcept : 
//					TextWordNetSyncRule.getRules(mpmeTextBoundary))
//					if (!newConcepts.contains(lastConcept)) 
//						lastConcept.triggerToUnrecommend(this);
//				
//				/*
//				 * Because label value changes are preserved as Recommendations,
//				 * change history is no longer needed for labelValueBoundaries and
//				 * labelValueBoundaries can be completely overwrote.
//				 */
//				mpmeTextBoundary = newBoundaries;
//
//				/**
//				 * TODO: Manager Rule's job?
//				 * 
//				 * @aspect HookingRuleIntoMPME, WordNetSyncRuleLifeCycle
//				 * 
//				 * Hook these possible new Rule and Trigger/Recommender pair if
//				 * not yet hooked. The hooking order is that <b>Rule hooking >
//				 * Trigger/Recommender hooking</b>, which means a Rule hooking
//				 * always causes Trigger/Recommender hooking while inversely
//				 * don't. So we just select any new Rule (while Manager Rule has NO
//				 * Triggers/Recommenders assigned {@aspect 
//				 * ManagerWordNetSyncRule}) to instruct the 
//				 * following Trigger/Recommender hooking.
//				 */
//				try {
//					for (TextWordNetSyncRule newConcept : newConcepts) {
//						newConcept.hookInto(hookedMpme);
//						break;
//					}
//				} catch (Exception e) {
//					// Should NOT be here for NoSuchElementException, 
//					//	UnsupportedOperationException and IllegalArgumentException!
//					assert false;
//				}
//			}
//
		}

	}

	
	
	
	
	/**
	 * @aspect ClosedTriggerRecommenderEnv - this class is made inner to prevent the
	 *         addRecommender(...) with inherited or constructed classes of
	 *         out-side world
	 */
	static protected class TextWordNetSyncRecommender 
	extends WordNetSyncRule.WordNetSyncRecommender<DocumentedRegion> {

		/**
		 * TODO: Use generic {@link Recommendation} interface.
		 */
		final private NavigableSet<Object[]> recommendationForDemo = new TreeSet<Object[]>(
				new Comparator<Object[]>() {

					/**
					 * The larger the relativity, the lower the confidence of recommendation item.
					 * 
					 * @param recommItem1
					 * @param recommItem2
					 * @return
					 * @see #ruleMatched(RuleEvent)
					 */
					public int compare(Object[] recommItem1, Object[] recommItem2) {
						return -(((Integer)recommItem1[1]).intValue()-((Integer)recommItem2[1]).intValue());
					}
					
				});

		
		
		/**
		 * @aspect ClosedTriggerRecommenderEnv - a private constructor to prevent the
		 *         construction from out-side world
		 * @aspect DelayedHooking
		 */
//		protected GmfLabelWordNetSyncRecommender() {
//			super();
//		}

		@Override
		public TextWordNetSyncRecommender newRecommender() {
			return new TextWordNetSyncRecommender();
		}
		


		/**
		 * {@link IHyperlink} visualization of recommend application, which is generated and cached by 
		 * {@link #ruleMatched(tw.edu.nccu.shuttle.rule.RuleEvent)}.
		 * 
		 * @param region
		 * @return
		 */
		public List<SyncSetElementHyperlink> getRecommendation() {
			if (recommendationForDemo.isEmpty()) return null;

			IRegion meRegion = (IRegion) hookedMpme;
			List<SyncSetElementHyperlink> links = new ArrayList<SyncSetElementHyperlink>();
			
			Iterator<Object[]> recIterator = recommendationForDemo.descendingIterator();
			int limit = SyncSetElementHyperlinkDetector.LINK_SIZE_LIMIT;
			while (recIterator.hasNext() && limit > 0) {
				Object[] recItem = recIterator.next();

				// @aspect HeterogeneousModelingPlatform 
				@SuppressWarnings("unchecked")
				Iterator<WordNetSyncRecommender<DocumentedRegion>> recommenders = 
					((Iterable<WordNetSyncRecommender<DocumentedRegion>>) recItem[2]).iterator();
//	links example:	new IHyperlink[] {
//						new SyncSetElementHyperlink(meRegion, "Unit 1 - File 1", "Unit 4 - File 1", (float)3/(float)3), 
//						new SyncSetElementHyperlink(meRegion, "Unit 2 - File 2", "Unit 1 - File 1", (float)2/(float)3), 
//						new SyncSetElementHyperlink(meRegion, "Unit 3 - File 3", "Unit 5 - File 5", (float)1/(float)3),
//				};
				
				links.add(new SyncSetElementHyperlink(
						meRegion, 
						recommenders.hasNext()?recommenders.next().getHookedMpme().toString():null, 
						recommenders.hasNext()?recommenders.next().getHookedMpme().toString():null, 
						(float)(.5 - (Integer)recItem[1]*.1)));
				
				limit--;
			}
			return links;
		}


		
		/**
		 * @see tw.edu.nccu.shuttle.MPHook#isHookableIntoCacheSolved(java.lang.Object)
		 */
		protected boolean isHookableIntoCacheSolved(DocumentedRegion mpme) {
			return true;
		}

		/**
		 * @aspect DelayedHooking
		 * @see tw.edu.nccu.shuttle.rule.RuleRecommender#hookIntoCacheSolved(Object)
		 */
		protected void hookIntoCacheSolved(DocumentedRegion mpme) throws IllegalArgumentException {
			super.hookIntoCacheSolved(mpme);
		}


		
		/**
		 * @aspect ShowingHidingHint
		 * Directly going matched - recommend application!
		 * 
		 * @see tw.edu.nccu.shuttle.rule.RuleRecommender#ruleTriggered(tw.edu.nccu.shuttle.rule.RuleEvent)
		 * @see tw.edu.nccu.shuttle.rule.RuleRecommender#recommendMatch(Rule, Object)
		 */
		@Override
		public void ruleTriggered(RuleEvent e) {
			super.ruleTriggered(e);
			// Directly going matched, therefore fresh overriding!
			// since I'm a very specific Recommender, my hosts SHOULD be very specific too
//			assert trigger instanceof DocWordNetSyncTrigger;
			e.getSource().ruleMatched(e);
		}

		/**
		 * Generating and caching recommend application. Its visualization is left in {@link #getRecommendation(IRegion)}.
		 * 
		 * TODO: application wrapped by Recommendation
		 * 
		 * @see tw.edu.nccu.shuttle.rule.RuleRecommender#ruleMatched(tw.edu.nccu.shuttle.rule.RuleEvent)
		 */
		@Override
		public void ruleMatched(RuleEvent e) {
			super.ruleMatched(e);
			
			// @aspect HeterogeneousModelingPlatform 
			@SuppressWarnings("unchecked")
			WordNetSyncRule<DocumentedRegion, WordNetSyncTrigger<DocumentedRegion>, WordNetSyncRecommender<DocumentedRegion>> 
				concept = (WordNetSyncRule<
						DocumentedRegion, WordNetSyncTrigger<DocumentedRegion>, WordNetSyncRecommender<DocumentedRegion>>) 
						e.getSource();
			@SuppressWarnings("unchecked")
			WordNetSyncTrigger<DocumentedRegion> trigger = (WordNetSyncTrigger<DocumentedRegion>) e.getTrigger();
			
			// sorting by confidence
			Set<WordNetSyncRecommender<DocumentedRegion>> triggeredRecomms;
			try {
				triggeredRecomms = (Set<WordNetSyncRecommender<DocumentedRegion>>) concept.getTriggeredRecommenders(trigger);
			} catch (NotYetAvailableException e1) {
				triggeredRecomms = null;
			}
			recommendationForDemo.add(new Object[] {
					concept, 
					new Integer(concept.getRelativity()), 
					triggeredRecomms});
		}

		/* (non-Javadoc)
		 * @see tw.edu.nccu.shuttle.rule.RuleRecommender#ruleUnmatched(tw.edu.nccu.shuttle.rule.RuleEvent)
		 */
		@Override
		public void ruleUnmatched(RuleEvent e) {
			super.ruleUnmatched(e);
//			// hide Hint	@aspect ShowingHidingHint, RecommProcess
//			assert recommendationHint != null;
//			if (recommendationHint.hide(e.getSource()))
//				// TODO: de-set Application Recommendation	@aspect RecommProcess
//				hookedGmfLabel.setToolTip(lastRecommendation);
		}

	}

	
	
	
	
	/**
	 * @aspect ManagerWordNetSyncRule
	 * @param sys
	 * @throws NoSuchObjectException
	 */
	public TextWordNetSyncRule(tw.edu.nccu.shuttle.System sys) throws NoSuchObjectException {
		super(sys, new TextWordNetSyncTrigger(), new TextWordNetSyncRecommender());
	}

	private TextWordNetSyncRule(WordNetSyncSet wnConcept) {
		super(wnConcept);
	}

	@Override
	public TextWordNetSyncRule newRule(WordNetSyncSet wnConcept) {
		return new TextWordNetSyncRule(wnConcept);
	}



//	final public static TextWordNetSyncRule getManagerRule(System sys) throws NoSuchObjectException {
//		try {
//			return (TextWordNetSyncRule) getManagerRule();
//		} catch (NotYetAvailableException e) {
//			return new TextWordNetSyncRule(sys);
//		}
//	}

	/**
	 * @return The manager trigger as specified in {@link WordNetSyncRule#getManagerTrigger()} without null.
	 */
	static public TextWordNetSyncTrigger getManagerTrigger() {
		if (MANAGER_TRIGGER == null || !(MANAGER_TRIGGER instanceof TextWordNetSyncTrigger)) {
			MANAGER_TRIGGER = new TextWordNetSyncTrigger();
		}
		return (TextWordNetSyncTrigger) MANAGER_TRIGGER;
	}

	/**
	 * @return The manager recommender as specified in {@link WordNetSyncRule#getManagerRecommender()} without null.
	 */
	static public TextWordNetSyncRecommender getManagerRecommender() {
		if (MANAGER_RECOMMENDER == null || !(MANAGER_RECOMMENDER instanceof TextWordNetSyncRecommender)) {
			MANAGER_RECOMMENDER = new TextWordNetSyncRecommender();
		}
		return (TextWordNetSyncRecommender) MANAGER_RECOMMENDER;
	}

}
