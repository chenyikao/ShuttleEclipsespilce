/**
 */
package tw.edu.nccu.shuttle.sandbox;

import java.rmi.NoSuchObjectException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartListener;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;

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
 * GMF Label WordNet SyncRule is responsible to manage the life cycle of
 * {@link GmfLabelWordNetSyncTrigger GMF Label WordNet SyncTrigger}: born,
 * Concepts (Rules) linked, Concepts re-linked (Trigger/Recommender linked to
 * new Rules: new/switched/removed links), dead and so-on.
 * 
 * Two splitting points: {@link #hookIntoCacheSolved(IAdaptable mpmeAdaptable)}
 * and {@link #getRecommenders(ImpactArea, WrappingLabel)}
 * 
 * TODO: change to GmfLabelSyncRule<GmfLabelSyncTrigger,
 * GmfLabelSyncRecommender> to accommodate 'one Trigger/Recommender per model
 * element (type)' principle
 * 
 * @aspect OneTriggerRecommenderPerModelElementType
 * 
 * @author Kao, Chen-yi
 * 
 */
public class GmfLabelWordNetSyncRule 
extends GmfLabelSyncRule<
GmfLabelWordNetSyncRule.GmfLabelWordNetSyncTrigger, 
GmfLabelWordNetSyncRule.GmfLabelWordNetSyncRecommender> {

	/**
	 * @aspect ClosedTriggerRecommenderEnv - innerized to prevent the
	 *         addTrigger(...) with inherited or constructed classes of
	 *         out-side world
	 */
	static private class GmfLabelWordNetSyncTrigger 
		extends GmfLabelSyncTrigger implements EditPartListener {

		/**
		 * Label text change is NOT going to trigger bean property change event 
		 * currently. So we have to monitor the change on our own.
		 * 
		 * TODO: WrappingLabel SHOULD implement "full" bean property change firing 
		 * TODO: for temporary demo. To be integrated with SingleMPME/GmfLabelSelection?
		 */
		private WrappingLabel hookedGmfLabel = null;
		private String labelValue = null;
		private String lastLabelValue = null;
//		private Set<String> impactHistory = null;
		private NLBoundaries labelValueBoundaries = null;

		private ImpactArea<Integer, String> impactAreaInLast = null;
		
		// TODO: for temporary demo. 
		// SHOULD be leveled up to MPHook and guarded by methods ('cause the exception is
		// initially null)
//		private static UnsupportedOperationException NOT_HOOKABLE_EXCEPTION;
				

		
		/**
		 * @aspect ClosedTriggerRecommenderEnv - a private constructor to prevent the
		 *         construction from out-side world
		 * @aspect DelayedHooking
		 */
//		protected GmfLabelWordNetSyncTrigger() {
//			super();
//		}

		/**
		 * @return the labelValue
		 */
		public String getLabelValue() throws NoSuchObjectException {
			if (labelValue == null) 
				throw tw.edu.nccu.shuttle.sandbox.System.NOT_YET_EXIST_EXCEPTION;
			return labelValue;
		}

		/**
		 * @return the lastLabelValue
		 */
		public String getLastLabelValue() throws NoSuchObjectException {
			if (lastLabelValue == null) 
				throw tw.edu.nccu.shuttle.sandbox.System.NOT_YET_EXIST_EXCEPTION;
			return lastLabelValue;
		}

		public NLBoundaries getLabelValueBoundaries() throws NoSuchObjectException {
			if (labelValueBoundaries == null) 
				throw tw.edu.nccu.shuttle.sandbox.System.NOT_YET_EXIST_EXCEPTION;
			return labelValueBoundaries;
		}
		
		/**
		 * @return the impactAreaInLast
		 */
		public ImpactArea<Integer, String> getImpactAreaInLast() 
			throws NoSuchObjectException {
			if (impactAreaInLast == null) 
				throw tw.edu.nccu.shuttle.sandbox.System.NOT_YET_EXIST_EXCEPTION;
			return impactAreaInLast;
		}

		/**
		 * @aspect DelayedHooking
		 * @see tw.edu.nccu.shuttle.sandbox.GmfLabelSyncTrigger#hookIntoCacheSolved(org.eclipse.core.runtime.IAdaptable)
		 */
		protected void hookIntoCacheSolved(IAdaptable mpmeAdaptable) 
		throws IllegalArgumentException {
			super.hookIntoCacheSolved(mpmeAdaptable);
			
			AbstractGraphicalEditPart mpmeEditPart; 
			try {
				mpmeEditPart = (AbstractGraphicalEditPart) mpmeAdaptable
						.getAdapter(AbstractGraphicalEditPart.class);
				hookedGmfLabel = (WrappingLabel) mpmeEditPart.getFigure();
			} catch (NullPointerException e) {
				throw GmfLabelSyncRule.IMHOOKABLE_EXCEPTION;
			} catch (ClassCastException e) {
				throw GmfLabelSyncRule.IMHOOKABLE_EXCEPTION;
			}
			
			// label text change is NOT going to trigger bean property change
			// event currently
			mpmeEditPart.addEditPartListener(this);
//			hookedGmfLabel.getUpdateManager().addUpdateListener(this);
//			hookedGmfLabel.addKeyListener(this);
			
			labelValue = hookedGmfLabel.getText();
			// @aspect ManagerGmfLabelWordNetSyncRule
			//	after the following label has boundary detected!
			labelValueBoundaries = new NLBoundaries(labelValue);
		}

		/**
		 * GMF Label is controlled by WrappingLabel. So we have to
		 * recognize (return true for) every WrappingLabel of
		 * AbstractGraphicalEditParts.
		 * 
		 * @see tw.edu.nccu.shuttle.sandbox.MPHook#isHookableIntoCacheSolved(org.eclipse.core.runtime.IAdaptable)
		 */
		protected boolean isHookableIntoCacheSolved(IAdaptable mpmeAdaptable) {
			try {
				return (
						((AbstractGraphicalEditPart) 
						mpmeAdaptable.getAdapter(AbstractGraphicalEditPart.class))
						.getFigure() instanceof WrappingLabel)?true:false;
			} catch (ClassCastException e) {
				// supports the host of AbstractGraphicalEditPart ONLY!
				return false;
			}
		}

//		/**
//		 * NOT interested in painting and this is ONLY an emtpty implementation for
//		 * contract. See why in {@link #notifyValidating()}.
//		 * 
//		 * @see org.eclipse.draw2d.UpdateListener#notifyPainting(org.eclipse.draw2d.geometry.Rectangle,
//		 *      java.util.Map)
//		 */
//		@SuppressWarnings("unchecked")
//		public void notifyPainting(Rectangle damage, Map dirtyRegions) {
//		}
	//
//		private LabelTextChecking checkingLabelText = new LabelTextChecking(); 
//		private Timer labelTextCheckingTimer = new Timer(); 
//		/**
//		 * WrappingLabel.setText() occurs too often for each character key-in and
//		 * therefore does notifyValidating() and notifyPainting(...). So we choose
//		 * to handle notifyValidating() ONLY. And notifyValidating() handling is
//		 * deferred heuristically for 3 sec. and the "Enter" KeyEvent would be
//		 * better monitored.
//		 * 
//		 * @see org.eclipse.draw2d.UpdateListener#notifyValidating()
//		 */
//		public void notifyValidating() {
		// deferring and checking text change (TODO: or filtering key event and
		// deferring?) -> trigger the host rule...
		
//			// do checking after 3 sec. elapses
//			try {
//				labelTextCheckingTimer.schedule(checkingLabelText, 3000);
//				checkingLabelText.setOldHookedMPMELabelValue(hookedGmfLabel.getText());
//			} catch (IllegalStateException e) {
//				// do nothing if already deferred
//				return;
//			}
//		}
//		private class LabelTextChecking extends TimerTask {
	//
////			private synchronized boolean checkingShouldBeDeferred = false; 
//			private String oldHookedMPMELabelValue;
//			private String newHookedMPMELabelValue;
	//
//			public void setOldHookedMPMELabelValue(String labelValue) {
//				oldHookedMPMELabelValue = labelValue;
//			}
//			
//			@Override
//			public void run() {
	//
//			}
	//
//		}
		
//		public void keyPressed(KeyEvent ke) {
//			// TODO filtering "Enter", "Esc" (or more non-valid-character key) and
//			// other event
	//
//		}
	//
//		public void keyReleased(KeyEvent ke) {
//			// NOT interested in
//		}
		public void childAdded(EditPart child, int index) {
			// NOT interested in
		}

		public void partActivated(EditPart editpart) {
			// NOT interested in
		}

		public void partDeactivated(EditPart editpart) {
			// TODO Auto-generated method stub
			
		}

		public void removingChild(EditPart child, int index) {
			// TODO: host.removeTrigger(child->mpmeAdaptable);
		}

		public void selectedStateChanged(EditPart editpart) {
			String newLabelValue = hookedGmfLabel.getText();
			if (!newLabelValue.equals(labelValue)) {
//				if (impactHistory == null) 
//					impactHistory = new HashSet<String>();
//				
//				impactHistory.add(labelValue);
				// update first for later Recommenders' accessing
				lastLabelValue = labelValue;
				labelValue = newLabelValue;

				/**
				 * -> updating boundaries and rules,
				 * boundary-Concept (Rule) dislinking/linking/generation
				 * (TODO: dislink: Concepts = Concepts - [Concepts - new Concepts] -> 
				 * link: Concepts = Concepts + [new Concepts - Concepts], 
				 * shrink then grow the set size?)
				 * 
				 * @aspect ShowingHidingHint
				 * -> Arrange Hint showing/hiding - HintForConcepts
				 * Show/hide the Recommendation Hint in corresponding MPME if it's 
				 * shown according to Sub-Bounded-String (SBS) editing and Concepts 
				 * changing.
				 * 
				 * 1) for Triggering MPMEs 
				 * (hints here are shown, or 'en-set', while being Triggered MPMEs):
				 * 1.1) for deleted Concepts: de-set HintForConcepts.
				 * 	match->unrecommendApplication(...) and delete Trigger/Recommender 
				 * 	links
				 * 1.2) for added Concepts: not the target to be triggered therefore 
				 * 	not interested
				 * 1.3) for unchanged Concepts: of course no changes for hints
				 * 
				 * 2) for Triggered MPMEs:
				 * 2.1) for 'likely changing' SBS: en-set HintForConcepts
				 * 	Operation points - match->recommendApplication(...)
				 * 2.2) TODO - for 'back' SBS: like the situation of 
				 * 	"apply"->"..."->"apply". When "apply" is back, 
				 * 	we need Impact History roll-back 
				 * 	mechanism, which hints that "Sync. source value is undo..." on 
				 * 	dirty MPMEs and triggerToUnrecommendHistory(...) for clean MPMEs.
				 * 
				 * 3) then if HintForConcept is NOT empty, show Hint, otherwise 
				 * hide it
				 * 
				 */

				// trigger the host rule (need NOT to recommend match) -> re-iterate
				// breaks for new boundaries
				try {
					// TODO: focusing on old (last) Impact Segment is enough for
					// today's recommendation!
					impactAreaInLast = 
						labelValueBoundaries.getImpactAreaInHost(labelValue);
					
					// for Triggered MPMEs - Rules (Concepts) invloved in
					// ImpactArea are triggered! (excluding Recommenders on
					// Triggering MPMEs AGAIN!)
					for (GmfLabelWordNetSyncRule likelyChangingConcept 
							: GmfLabelWordNetSyncRule.getRules(impactAreaInLast))
						likelyChangingConcept.trigger(this);
					
				} catch (NoSuchObjectException e) {
					// if there's NO Impact Area, then do nothing (including 
					//	triggering changing concepts) for now!
				}
//				triggerToUnrecommendHistory(this);
				
				NLBoundaries newBoundaries = new NLBoundaries(labelValue);

				// for Triggering MPMEs - check deleted Concepts to de-link
				//	Triggers/Recommenders and un-recommend application
				Collection<GmfLabelWordNetSyncRule> newConcepts = 
					GmfLabelWordNetSyncRule.getRules(newBoundaries);
				for (GmfLabelWordNetSyncRule lastConcept : 
					GmfLabelWordNetSyncRule.getRules(labelValueBoundaries))
					if (!newConcepts.contains(lastConcept)) 
						lastConcept.triggerToUnrecommend(this);
				
				/*
				 * Because label value changes are preserved as Recommendations,
				 * change history is no longer needed for labelValueBoundaries and
				 * labelValueBoundaries can be completely overwrote.
				 */
				labelValueBoundaries = newBoundaries;

				/**
				 * TODO: Manager Rule's job?
				 * 
				 * @aspect HookingRuleIntoMPME, WordNetSyncRuleLifeCycle
				 * 
				 * Hook these possible new Rule and Trigger/Recommender pair if
				 * not yet hooked. The hooking order is that <b>Rule hooking >
				 * Trigger/Recommender hooking</b>, which means a Rule hooking
				 * always causes Trigger/Recommender hooking while inversely
				 * don't. So we just select any new Rule (while Manager Rule has NO
				 * Triggers/Recommenders assigned {@aspect 
				 * ManagerGmfLabelWordNetSyncRule}) to instruct the 
				 * following Trigger/Recommender hooking.
				 */
				try {
					for (GmfLabelWordNetSyncRule newConcept : newConcepts) {
						newConcept.hookInto(hookedMpme);
						break;
					}
				} catch (Exception e) {
					// Should NOT be here for NoSuchElementException, 
					//	UnsupportedOperationException and IllegalArgumentException!
					assert false;
				}
				
				
				/*
				 * TODO: another way making recommendation(s) from impact 
				 * segments/area?
				 * Situation I: both old and new boundaries link to the same 
				 * Concepts
				 * (just assumption that 'Concepts' and 'Concept' link to the same 
				 * Concepts) 
				 * e.x., addConcepts -> addConcept
				 *       ^^^########    ^^^#######
				 *                 @
				 *       (@: value difference; #: impact area)
				 *       
				 * Situation II: some old or new boundaries link to different
				 * Concepts (just assumption that 'Apply' and 'Exec' link to different 
				 * Concepts) 
				 * e.x., recommendApply -> recommendExec
				 *       ^^^^^^^^^#####	   ^^^^^^^^^####
				 *                                  @@@@
				 */ 
//				if (newBoundary.overlap(labelValueImpactArea)) ;

				/*
				 * -> if there's ANY Final Application Recommendation [TODO:
				 * new linking (boundary-Concept linking)?], 
				 * match the host rule (by Trigger) -> recommend application
				 */  
				/*
				 * TODO: Assume ONLY possible kinds of old-new impact-segment bindings 
				 * (to be proved later):
				 * Case I) num(old impact segments) == num(new impact segments) 
				 * I.i) begin(old value) + ?
				 * Case II) num(old impact segments) =/= num(new impact segments)...
				 * ->
				 * Two kinds of Application Recommendations:
				 * 1) Predictable Recommendations - one-one impact-segment/boundary?
				 * 2) Impredictable Recommendations - ...
				 */
				
//				try {
//					/*
//					 * (TODO: if Impact Area overlapped new label value, partially update
//					 * Sub-bounded-string-Concepts links, via Levenshtein sequence 
//					 * alignment again? for saving boundary detection time?)
//					 */
//					Boundaries impactAreaInNew = 
//						labelValueBoundaries.getImpactAreaInNew();
//				} catch (NoSuchObjectException e) {
//					// if there's NO Impact Area for now, then do nothing!
//				}

			}

		}

	}

	
	
	
	
	/**
	 * @aspect ClosedTriggerRecommenderEnv - this class is made inner to prevent the
	 *         addRecommender(...) with inherited or constructed classes of
	 *         out-side world
	 */
	static private class GmfLabelWordNetSyncRecommender 
	extends GmfLabelSyncRecommender {

		private WrappingLabel hookedGmfLabel;
		private IFigure lastRecommendation;

		
		
		/**
		 * @aspect ClosedTriggerRecommenderEnv - a private constructor to prevent the
		 *         construction from out-side world
		 * @aspect DelayedHooking
		 */
//		protected GmfLabelWordNetSyncRecommender() {
//			super();
//		}

		/**
		 * @aspect DelayedHooking
		 * @see tw.edu.nccu.shuttle.sandbox.RuleRecommender#hookIntoCacheSolved(org.eclipse.core.runtime.IAdaptable)
		 */
		protected void hookIntoCacheSolved(IAdaptable mpmeAdaptable) throws IllegalArgumentException {
			super.hookIntoCacheSolved(mpmeAdaptable);
			
			try {
				hookedGmfLabel = (WrappingLabel) 
				((AbstractGraphicalEditPart) mpmeAdaptable
				.getAdapter(AbstractGraphicalEditPart.class))
				.getFigure();
			} catch (NullPointerException e) {
				throw GmfLabelSyncRule.IMHOOKABLE_EXCEPTION;
			} catch (ClassCastException e) {
				throw GmfLabelSyncRule.IMHOOKABLE_EXCEPTION;
			}
		}

		protected boolean isHookableIntoCacheSolved(IAdaptable mpmeAdaptable) {
			try {
				return (
						((AbstractGraphicalEditPart) 
						mpmeAdaptable.getAdapter(AbstractGraphicalEditPart.class))
						.getFigure() instanceof WrappingLabel)?true:false;
			} catch (ClassCastException e) {
				// supports the host of AbstractGraphicalEditPart ONLY!
				return false;
			}
		}

		/**
		 * @aspect ShowingHidingHint
		 * Directly going matched - recommend application!
		 * 
		 * @see RecommendationHintDecoratorProvider
		 * @see tw.edu.nccu.shuttle.sandbox.RuleRecommender#recommendMatch(Rule, RuleTrigger)
		 */
		@SuppressWarnings("unchecked")
		@Override
		public void recommendMatch(Rule triggeredHost, RuleTrigger trigger) {
			// Directly going matched, therefore fresh overriding!
			// since I'm a very specific Recommender, my hosts SHOULD be very specific too
//			assert (trigger instanceof GmfLabelWordNetSyncTrigger);
			triggeredHost.match(trigger, this);
		}

		/* (non-Javadoc)
		 * @see tw.edu.nccu.shuttle.sandbox.RuleRecommender#recommendApplication()
		 */
		@Override
		public <Trigger extends RuleTrigger, Recommender extends RuleRecommender> 
		void recommendApplication(
				Rule<Trigger, Recommender> triggeredHost, Trigger trigger) {
			assert triggeredHost instanceof GmfLabelWordNetSyncRule;
			assert trigger instanceof GmfLabelWordNetSyncTrigger;
			super.recommendApplication(triggeredHost, trigger);
			
//				java.lang.System.out.println(lastHookedMPMELabelValue + " -> "
//					+ newHookedMPMELabelValue);
			
			// TODO: wrapped by Recommendation
			// Tooltip:
			String lastLabelValue;
			try {
				lastLabelValue = 
					((GmfLabelWordNetSyncTrigger) trigger).getLastLabelValue();
			} catch (NoSuchObjectException e) {
				lastLabelValue = " (nothing)";
			}
			try {
				Label tooltip = new Label(" Changes elsewhere: \n "
						+ lastLabelValue
						+ " -> "
						+ ((GmfLabelWordNetSyncTrigger) trigger).getLabelValue() 
						+ " ");
				
				// Panel tooltip = new Panel();
				// tooltip.add(new Label(lastHookedMPMELabelValue));
				// tooltip.add(new Button("Deprecate this rule"));
				// tooltip.setLocation(hookedMPMELabel.getLocation()); //failed!
//					??tooltip.setLocation(new Point()); // failed!
				// tooltip.setFocusTraversable(true); //failed!
				lastRecommendation = hookedGmfLabel.getToolTip();
				hookedGmfLabel.setToolTip(tooltip);
				
				// IFigure child adding: failed!
				// Label tooltip = new Label("new value...");
				// tooltip.setLocation(new Point());
				// tooltip.setVisible(true);
				// hookedMPMELabel.add(tooltip);

			} catch (NoSuchObjectException e) {
				assert false;	// label value should NOT be nothing!
			}
		}

		public void unrecommendApplication(
				GmfLabelWordNetSyncRule unrecommendConcept) {
			// hide Hint	@aspect ShowingHidingHint, RecommProcess
			assert recommendationHint != null;
			if (recommendationHint.hide(unrecommendConcept))
				// TODO: de-set Application Recommendation	@aspect RecommProcess
				hookedGmfLabel.setToolTip(lastRecommendation);
		}

	}

	
	
	
	
	private static System System = null;

	/**
	 * @aspect JWIInitialization
	 */
	static private IDictionary WordNet;

	private static GmfLabelWordNetSyncRule MANAGER_RULE = null;
	protected static Iterable<GmfLabelWordNetSyncRule> EMPTY_RULES = 
		new HashSet<GmfLabelWordNetSyncRule>();
	
	private WordNetSyncSet concept;

	static private Hashtable<ISynsetID, GmfLabelWordNetSyncRule> ConceptRuleCache = 
		new Hashtable<ISynsetID, GmfLabelWordNetSyncRule>();
	/**
	 * Caching Boundary-Concepts, Sub-bounded-string-(Index-Word)-Concepts and
	 * basic-extended-Concepts links for saving time of navigating WordNet.
	 * Shuttle does concept search based on the maximum set of Index Words, no
	 * matter what their part-of-speech is.
	 * 
	 * TODO: Sub-Bounded-String (SBS) -> Bounded-Substring (BS)?
	 * 
	 * @aspect Set - the rule sets allow NO duplicate rules
	 */
	static private Hashtable<NLBoundaries, Set<GmfLabelWordNetSyncRule>> 
	BoundaryConceptsLinkCache =
		new Hashtable<NLBoundaries, Set<GmfLabelWordNetSyncRule>>();
	static private Hashtable<IIndexWord, Set<GmfLabelWordNetSyncRule>> 
	WordConceptsLinkCache = 
		new Hashtable<IIndexWord, Set<GmfLabelWordNetSyncRule>>();
	
	/**
	 * Java local final variables in static methods are NOT static (value-
	 * memorized) actually!
	 */
	static final private GmfLabelWordNetSyncTrigger MANAGER_TRIGGER = 
		new GmfLabelWordNetSyncTrigger();
	static final private Hashtable<IAdaptable, GmfLabelWordNetSyncTrigger> 
	triggerCache = new Hashtable<IAdaptable, GmfLabelWordNetSyncTrigger>();
	static final private GmfLabelWordNetSyncRecommender MANAGER_RECOMMENDER = 
		new GmfLabelWordNetSyncRecommender();
	static final private Hashtable<IAdaptable, GmfLabelWordNetSyncRecommender> 
	recommenderCache = new Hashtable<IAdaptable, GmfLabelWordNetSyncRecommender>();


	
	/**
	 * Constructor for supporting {@link System} manager rule.
	 * 
	 * @aspect ManagerGmfLabelWordNetSyncRule
	 * @param sys
	 * @throws NoSuchObjectException 
	 */
	private GmfLabelWordNetSyncRule(System sys) throws NoSuchObjectException {
		super();
		if (System == null) {
			// @aspect ManagerGmfLabelWordNetSyncRule 
			//	mark as an 'pure' manager. ALL Rules are managers - 'pure' or 'non pure'.
			concept = null;
			System = sys;
			WordNet = sys.getWordNet();
			WordNet.open();
		}
	}

	public GmfLabelWordNetSyncRule(WordNetSyncSet wnConcept) 
	throws IllegalArgumentException {	// @aspect IllegalArgumentException
		super();
		if (wnConcept == null) throw new IllegalArgumentException();
		concept = wnConcept;
	}

	

	final public static GmfLabelWordNetSyncRule getManagerRule(System sys) 
	throws NoSuchObjectException {
		if (MANAGER_RULE == null) MANAGER_RULE = new GmfLabelWordNetSyncRule(sys);
		return MANAGER_RULE;
	}

	/* (non-Javadoc)
	 * @see tw.edu.nccu.shuttle.sandbox.Rule#isHookableIntoCacheSolved(org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	protected boolean isHookableIntoCacheSolved(IAdaptable mpmeAdaptable) {
		try {
			// @aspect ClosedTriggerRecommenderEnv
			getCachedTrigger(mpmeAdaptable);
			return true;	// any Trigger manufacturable causes hookable
		} catch (NoSuchObjectException e) {/* do nothing at first! */}
		try {
			// @aspect ClosedTriggerRecommenderEnv
			getCachedRecommender(mpmeAdaptable);
			return true;	// any Recommender manufacturable causes hookable
		} catch (NoSuchObjectException e) {
			return false;
		}
	}

	/**
	 * <i>Complete</i> overriding since unique rule splitting mechanism 
	 * {@aspect ManagerGmfLabelWordNetSyncRule} and Trigger/Recommender scheme.
	 * TODO: merge this with general scheme (redesign general Trigger/Recommender 
	 * scheme?)
	 * 
	 * Hooking of GMF Label WordNet SyncRule into a model element is done by the
	 * hooking of Triggers/Recommenders (where each Recommender is coupled with
	 * one Trigger) into that and connecting of Rule and Triggers/Recommenders.
	 * 
	 * Here the Rule manages (solves cache and one Trigger/Recommender per model
	 * element per Rule) and splits with new WordNet Concept specific Rules.
	 * 
	 * @aspect HookingRuleIntoMPME, ManagerGmfLabelWordNetSyncRule
	 * @see tw.edu.nccu.shuttle.sandbox.Rule#hookIntoCacheSolved(org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	protected void hookIntoCacheSolved(IAdaptable mpmeAdaptable)
			throws UnsupportedOperationException, IllegalArgumentException {
		if (!isHookableInto(mpmeAdaptable)) throw NON_HOOKABLE_EXCEPTION;
		
		// generate/hook Triggers/Recommenders into model element -
		// 	one trigger per model element per rule, so retrieve
		//	Triggers/Recommenders cached or generate ones, 
		GmfLabelWordNetSyncTrigger mpmeTrigger = null;
		GmfLabelWordNetSyncRecommender mpmeRecommender = null;
		try {mpmeTrigger = getCachedTrigger(mpmeAdaptable);} 
		catch (NoSuchObjectException e) {
			assert false;	// should NOT happen since is checked to be hookable!
		}
		try {mpmeRecommender = getCachedRecommender(mpmeAdaptable);} 
		catch (NoSuchObjectException e) {
			assert false;	// should NOT happen since is checked to be hookable!
		}
		//	attach them to model-elements
		mpmeTrigger.hookInto(mpmeAdaptable);
		mpmeRecommender.hookInto(mpmeAdaptable);
		
		// connect Rule and Triggers/Recommenders - 
		// @aspect ManagerGmfLabelWordNetSyncRule
		//	For performance Manager Rule has NO Triggers/Recommenders assigned!
		// @aspect ManagerGmfLabelWordNetSyncRule, SplittingGmfLabelSyncRule
		//	Initial (basis) splitting point - label construction. 
		//	split rules if it's necessary 
		try {
			for (GmfLabelWordNetSyncRule concept : getRules(
					mpmeTrigger.getLabelValueBoundaries())) {
				//	and attach them to rules 
				//	w/ avoiding duplicates right by {@link java.util.Set Set} 
				//	operations.
				concept.addTrigger(mpmeTrigger);
				concept.addRecommender(mpmeRecommender);
			}
		} catch (NoSuchObjectException e) {
			// do nothing since there's NO label value boundaries at this time!
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
	 * @param trigger
	 * @throws NullPointerException - when {@code trigger} is null.
	 */
	void triggerToUnrecommend(GmfLabelWordNetSyncTrigger trigger) 
	throws NullPointerException {
		if (trigger == null) 
			throw tw.edu.nccu.shuttle.sandbox.System.NULL_ARGUMENT_EXCEPTION;
		
		try {
			GmfLabelWordNetSyncRecommender coupledRecommender = 
				getCoupledRecommender(trigger);
			// Unrecommend the application
			coupledRecommender.unrecommendApplication(this);
			// remove Recommender links
			removeRecommender(coupledRecommender);
		} catch (NoSuchObjectException e) {
			// do nothing but remove ONLY Trigger links later
		}

		// remove Trigger links
		removeTrigger(trigger);
		
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
	void triggerToUnrecommendHistory(GmfLabelWordNetSyncTrigger trigger) 
	throws NullPointerException {
		if (trigger == null) 
			throw tw.edu.nccu.shuttle.sandbox.System.NULL_ARGUMENT_EXCEPTION;
		
		for (GmfLabelWordNetSyncRecommender triggeredRecommender : 
			getTriggeredRecommenders(trigger))
			triggeredRecommender.unrecommendApplication(this);

	}
	
	/**
	 * @see tw.edu.nccu.shuttle.sandbox.Rule#apply()
	 */
	@Override
	public void apply() {
		//??
		// -> recommendation approval/disapproval (to Recommender)
		// -> if there's an approval -> apply
		// -> if there's a disapproval -> froze the rule (set the rule
		// state as "frozen" and downgrade the rule priority to the
		// lowest)

		super.apply();
	}

	

	/**
	 * Traverse the Boundary-Concepts links to retrieve ALL related Rules
	 * (Concepts) (for notify recommending application, for example). Now ONLY
	 * traversing the Impact Area in Last Label Value.
	 * 
	 * @aspect Caching - Assume that each Impact Area is sent once and NO
	 *         caching needed.
	 * 
	 * @aspect ManagerGmfLabelWordNetSyncRule, SplittingGmfLabelSyncRule -
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
	 *            currently. (@aspect ManagerGmfLabelWordNetSyncRule,
	 *            SplittingGmfLabelSyncRule)
	 * @return
	 * @throws JWNLException -
	 *             generated through preparing splitting rules
	 */
	public static Iterable<GmfLabelWordNetSyncRule> getRules(
			ImpactArea<Integer, String> impactAreaInLast) {
		if (impactAreaInLast == null) 
			throw tw.edu.nccu.shuttle.sandbox.System.NULL_ARGUMENT_EXCEPTION;
		
		// NO duplicate rules allowed	@aspect Set
		HashSet<GmfLabelWordNetSyncRule> impactConcepts =
			new HashSet<GmfLabelWordNetSyncRule>();
		
		// @aspect SubBoundedStringIteration
		//	TODO: generic Boundary Iterator - Iterator<Integer> boundaryIterator() 
		Iterator<Editing<Integer>> impactAreaIterator = impactAreaInLast.iterator();
		if (impactAreaIterator.hasNext()) {
			String mpmeLabelValue = impactAreaInLast.getHost();
			
			// previous and current boundaries
			int preBoundary, curBoundary = impactAreaIterator.next().getSubjectInX();
			while (impactAreaIterator.hasNext()) {
				preBoundary = curBoundary;
				curBoundary = impactAreaIterator.next().getSubjectInX();
				// @aspect ManagerGmfLabelWordNetSyncRule, SplittingGmfLabelSyncRule
				//	split rules if it's necessary 
				for (GmfLabelWordNetSyncRule concept : getRules(
						mpmeLabelValue.substring(preBoundary, curBoundary)))
					// @aspect AvoidingDuplicates
					//	avoiding duplicates right by {@link java.util.Set Set}
					//	operations.
					impactConcepts.add(concept);
			}
		}

		return impactConcepts;
	}

	/**
	 * @param nlBoundaries
	 * @return
	 * @throws JWNLException - generated through preparing splitting rules
	 */
	public static Collection<GmfLabelWordNetSyncRule> getRules(
			NLBoundaries nlBoundaries) {
		if (nlBoundaries == null) 
			throw tw.edu.nccu.shuttle.sandbox.System.NULL_ARGUMENT_EXCEPTION;
		
		// @aspect Caching
		Set<GmfLabelWordNetSyncRule> linkedRules = 
			BoundaryConceptsLinkCache.get(nlBoundaries);
		if (linkedRules == null) {
			linkedRules = new HashSet<GmfLabelWordNetSyncRule>();
			
			// @aspect SubBoundedStringIteration
			Iterator<Integer> nlBoundaryIterator = nlBoundaries.iterator();
			if (nlBoundaryIterator.hasNext()) {
				String host = nlBoundaries.getHost();
				
				// previous and current boundaries
				int preBoundary, curBoundary = nlBoundaryIterator.next();
				while (nlBoundaryIterator.hasNext()) {
					preBoundary = curBoundary;
					curBoundary = nlBoundaryIterator.next();
					// @aspect ManagerGmfLabelWordNetSyncRule
					// @aspect SplittingGmfLabelSyncRule
					//	split rules if it's necessary 
					for (GmfLabelWordNetSyncRule concept : getRules(
							host.substring(preBoundary, curBoundary)))
						// @aspect AvoidingDuplicates
						//	avoiding duplicates right by {@link java.util.Set Set}
						//	operations.
						linkedRules.add(concept);
				}
			}
			
			// @aspect Caching
			BoundaryConceptsLinkCache.put(nlBoundaries, linkedRules);
			
		}
		return linkedRules;
		
	}
	
	/**
	 * TODO: cache negative matches
	 * 
	 * @param subBoundedString
	 * @return
	 * @throws JWNLException - generated through preparing splitting rules
	 */
	static private Iterable<GmfLabelWordNetSyncRule> getRules(
			String subBoundedString) {
		assert System != null;	// @aspect ManagerGmfLabelWordNetSyncRule
		
		if (subBoundedString == null) return EMPTY_RULES;
		String canonicalSBS = subBoundedString.trim();
		if (canonicalSBS.length() == 0) return EMPTY_RULES;
		
		Set<GmfLabelWordNetSyncRule> rules = null;
		boolean isGotNewRules = false;
		final POS[] allPos = POS.values();
		IIndexWord[] allIndexWords = new IIndexWord[allPos.length];
		
		// Concept search, one concept one rule -> searching ConceptRuleCache
		//	and merging duplicate rules (via {@link java.util.Set Set} operation)
		//	@aspect OneConceptOneRule, MergingDuplicateRules
		// 1: WordNet Dictionary Lookup
		for (int i = 0; i < allPos.length; i++) {
			allIndexWords[i] = WordNet.getIndexWord(canonicalSBS, allPos[i]);

			// 2: concept search	@aspect ConceptSearch
			//	??more OO, process concept search in WordNetSyncSet
			if (allIndexWords[i] != null) {
				if (!isGotNewRules) {
					// no matter what POS is, ALL share the same rules! -
					//	Find a representative IndexWord for a group, because any
					//	allIndexWords[i] may be null
					rules = WordConceptsLinkCache.get(allIndexWords[i]);
					if (rules != null) return rules;
					rules = new HashSet<GmfLabelWordNetSyncRule>();
					isGotNewRules = true;
				}
				
				for (IWordID indexWord : allIndexWords[i].getWordIDs()) {
					// 2.1) basic concept search	@aspect ConceptSearch
					ISynsetID basicConceptID = indexWord.getSynsetID(); 
					ISynset basicConcept = WordNet.getSynset(basicConceptID); 
					rules.add(getRule(basicConceptID));
					
					// @aspect ConceptSearch
					//	??rename extendedConceptXXX to advancedConceptXXX
					// 2.2) 1-hop extended concept search - 1-hop-further related 
					// word search. Extended concepts include 1-hop-further
					// related words' Synsets.
					for (IWord synsetWord : basicConcept.getWords())
						for (IWordID relatedWord : synsetWord.getRelatedWords())
							rules.add(getRule(relatedWord.getSynsetID()));
					
					// @aspect ConceptSearch
					// 2.2) 1-hop extended concept search - 1-hop-further Synset 
					//	search. 
					for (ISynsetID extendedConceptID : 
						basicConcept.getRelatedSynsets())
						rules.add(getRule(extendedConceptID));
				}
			}
		}
		// final: direct concept link	@aspect ConceptSearch
		if (isGotNewRules) {
			for (IIndexWord indexWord : allIndexWords) 
				if (indexWord != null) WordConceptsLinkCache.put(indexWord, rules);
		} else return EMPTY_RULES;
	
		return rules;
	}

	static private GmfLabelWordNetSyncRule getRule(ISynsetID wnConceptID) {
		// @aspect Caching, OneConceptOneRule
		GmfLabelWordNetSyncRule rule = ConceptRuleCache.get(wnConceptID);

		if (rule == null) {
			rule = 
				new GmfLabelWordNetSyncRule(new WordNetSyncSet(wnConceptID, WordNet));
			// @aspect HookingRuleIntoMPME
			//	TODO: integrate or keep separate the hooking process? -
			//	1) new Triggers/Recommenders if necessary;
			//	2) new Rules if necessary;
			//	3) for all new Rules - 
			//	3.1) rule.hookInto(mpmeAdaptable);
			//		Rule hooking is invloked by DecoratorProvider ONLY for performance
			//	3.2) rule.addTrigger(new Trigger);
			//	3.3) rule.addRecommender(new Recommender).
			
			// update {@link System} supported rules
			System.addSupportedRule(rule);
			// update the cache
			ConceptRuleCache.put(wnConceptID, rule);
		}

		return rule;
	}
			
	/**
	 * TODO: generalize this factory method and put it to {@link RuleTrigger}
	 * TODO: parameterized as getTriggerOrRecommender<TriggerOrRecommender>(...)?
	 * @param mpmeAdaptable
	 * @return
	 * @throws NoSuchObjectException
	 */
	static private GmfLabelWordNetSyncTrigger getCachedTrigger(IAdaptable mpmeAdaptable) 
	throws NoSuchObjectException {
		if (mpmeAdaptable == null) return MANAGER_TRIGGER;
		
		GmfLabelWordNetSyncTrigger trigger = triggerCache.get(mpmeAdaptable);
		if (trigger == null) {
			// @aspect InstancesReducingPerformance
			if (MANAGER_TRIGGER.isHookableInto(mpmeAdaptable)) {
				// @aspect ClosedTriggerRecommenderEnv - a private construction
				trigger = new GmfLabelWordNetSyncTrigger();
				triggerCache.put(mpmeAdaptable, trigger);

			} else 
				// @aspect NegativeMatching
				throw tw.edu.nccu.shuttle.sandbox.System.NOT_YET_EXIST_EXCEPTION;
		}
		
		return trigger;
	}
	
	/**
	 * TODO: generalize this factory method and put it to {@link RuleRecommender}
	 * TODO: parameterized as getTriggerOrRecommender<TriggerOrRecommender>(...)?
	 * @param mpmeAdaptable
	 * @return
	 * @throws NoSuchObjectException
	 */
	static private GmfLabelWordNetSyncRecommender getCachedRecommender(
			IAdaptable mpmeAdaptable) throws NoSuchObjectException {
		if (mpmeAdaptable == null) return MANAGER_RECOMMENDER;
		
		GmfLabelWordNetSyncRecommender recommender = 
			recommenderCache.get(mpmeAdaptable);
		if (recommender == null) {
			// @aspect InstancesReducingPerformance
			if (MANAGER_RECOMMENDER.isHookableInto(mpmeAdaptable)) {
				// @aspect ClosedTriggerRecommenderEnv - a private construction
				recommender = new GmfLabelWordNetSyncRecommender();
				recommenderCache.put(mpmeAdaptable, recommender);

			} else 
				// @aspect NegativeMatching
				throw tw.edu.nccu.shuttle.sandbox.System.NOT_YET_EXIST_EXCEPTION;
		}

		return recommender;
	}
	
	@Override
	public GmfLabelWordNetSyncRecommender getCoupledRecommender(
			GmfLabelWordNetSyncTrigger trigger) 
	throws NullPointerException, NoSuchObjectException {
		if (trigger == null) 
			throw tw.edu.nccu.shuttle.sandbox.System.NULL_ARGUMENT_EXCEPTION;
		IAdaptable mpmeForTrigger = trigger.getHookedMpme();
		assert mpmeForTrigger != null;
		return getCachedRecommender(mpmeForTrigger);
	}

	/**
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
	 * @return
	 * @see tw.edu.nccu.shuttle.sandbox.Rule#getTriggeredRecommenders(tw.edu.nccu.shuttle.sandbox.RuleTrigger)
	 */
	@Override
	public Iterable<GmfLabelWordNetSyncRecommender> getTriggeredRecommenders(
			GmfLabelWordNetSyncTrigger trigger) throws NullPointerException {
		if (trigger == null) 
			throw tw.edu.nccu.shuttle.sandbox.System.NULL_ARGUMENT_EXCEPTION;
		
		// @aspect ManagerGmfLabelWordNetSyncRule
		//	??if I'm a manager rule, do no work
		// 	else return ALL Recommenders EXCEPT the one coupled with source 
		//	Trigger itself
		
		GmfLabelWordNetSyncRecommender coupledRecomm;
		try {
			coupledRecomm = getCoupledRecommender(trigger);
		} catch (NoSuchObjectException e) {
			// should NOT happen! Trigger and Recommender are meant to be coupled!
			assert false;
			coupledRecomm = null;
		}
		HashSet<GmfLabelWordNetSyncRecommender> impactRecommenders =
			new HashSet<GmfLabelWordNetSyncRecommender>();
		
		// @aspect ManagerGmfLabelWordNetSyncRule, SplittingGmfLabelSyncRule
		// If there's any rule split (a new rule has NO Triggers/Recommenders),
		// it MUST be caused by the current MPME
		// ONLY - connect new Rules and current MPME's Triggers/Recommenders.
		Iterable<GmfLabelWordNetSyncRecommender> conceptRecomms = getRecommenders(); 
		if (!conceptRecomms.iterator().hasNext()) {
			addTrigger(trigger);
			addRecommender(coupledRecomm);
		}
		// merging duplicate Recommenders (via {@link java.util.Set Set} 
		//	operation) and excluding one coupled with source Trigger
		//	@aspect OneRecommenderOneMeType, MergingDuplicateRecommenders
		else
			for (GmfLabelWordNetSyncRecommender mpmeRecommender 
					: conceptRecomms) 
				// EXCLUDING Recommenders on Triggering MPMEs
				if (mpmeRecommender != coupledRecomm) 
					impactRecommenders.add(mpmeRecommender);

		return impactRecommenders;

	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return concept.toString();
	}

}
