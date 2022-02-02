/**
 */
package tw.edu.nccu.shuttle.gmf;

import java.rmi.NoSuchObjectException;
import java.util.NoSuchElementException;
import java.util.Set;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartListener;
import org.eclipse.gef.editparts.AbstractGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;

import tw.edu.nccu.shuttle.ImpactArea;
import tw.edu.nccu.shuttle.NLBoundaries;
import tw.edu.nccu.shuttle.NotYetAvailableException;
import tw.edu.nccu.shuttle.WordNetSyncSet;
import tw.edu.nccu.shuttle.rule.Rule;
import tw.edu.nccu.shuttle.rule.RuleEvent;
import tw.edu.nccu.shuttle.rule.RuleTrigger;
import tw.edu.nccu.shuttle.rule.WordNetSyncRule;

/**
 * @aspect OneTriggerRecommenderPerModelElementType
 * TODO: change to GmfLabelSyncRule<GmfLabelSyncTrigger,
 * GmfLabelSyncRecommender> to accommodate 'one Trigger/Recommender per model
 * element (type)' principle
 * 
 * @author Kao, Chen-yi
 * 
 */
public class GmfLabelWordNetSyncRule extends WordNetSyncRule<
	IDecoratorTarget, 
	GmfLabelWordNetSyncRule.GmfLabelWordNetSyncTrigger, 
	GmfLabelWordNetSyncRule.GmfLabelWordNetSyncRecommender>
	implements IGmfLabelSyncRule {

	/**
	 * @aspect ClosedTriggerRecommenderEnv - innerized to prevent the
	 *         addTrigger(...) with inherited or constructed classes of
	 *         out-side world
	 */
	public static class GmfLabelWordNetSyncTrigger 
		extends GmfLabelSyncTrigger implements EditPartListener {

		/**
		 * Label text change is NOT going to trigger bean property change event 
		 * currently. So we have to monitor the change on our own.
		 * 
		 * TODO: WrappingLabel SHOULD implement "full" bean property change firing 
		 * TODO: for temporary demo. To be integrated with SingleMPME/GmfLabelSelection?
		 */
		private AbstractGraphicalEditPart hookedEditPart = null;
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
				

		
//		protected GmfLabelWordNetSyncTrigger() {
//			super();
//		}
		/**
		 * @aspect ClosedTriggerRecommenderEnv - a private constructor to prevent the
		 *         construction from out-side world
		 * @aspect DelayedHooking
		 * @see tw.edu.nccu.shuttle.rule.WordNetSyncRule.WordNetSyncTrigger#newTrigger()
		 */
		@Override
		public GmfLabelWordNetSyncTrigger newTrigger() {
			return new GmfLabelWordNetSyncTrigger();
		}
		
		
		
		/**
		 * @return The GMF Label value
		 * @throws NotYetAvailableException When GMF Label value is not yet available.
		 */
		public String getLabelValue() throws NotYetAvailableException {
			if (labelValue == null) 
				throw new NotYetAvailableException("GMF Label value");
			return labelValue;
		}

		/**
		 * @return The last value of GMF Label
		 * @throws NotYetAvailableException When the last value of GMF Label is not yet available.
		 */
		public String getLastLabelValue() throws NotYetAvailableException {
			if (lastLabelValue == null) 
				throw new NotYetAvailableException("The last value of GMF Label");
			return lastLabelValue;
		}

		/**
		 * @return The boundaries of GMF Label value 
		 * @throws NotYetAvailableException When the boundaries of GMF Label value are not yet available.
		 * @see tw.edu.nccu.shuttle.rule.WordNetSyncRule.WordNetSyncTrigger#getMpmeTextBoundaries()
		 */
		public NLBoundaries getLabelValueBoundaries() throws NotYetAvailableException {
			if (labelValueBoundaries == null) 
				throw new NotYetAvailableException("The boundaries of GMF Label value");
			return labelValueBoundaries;
		}
		/**
		 * @see #getLabelValueBoundaries()
		 * @see tw.edu.nccu.shuttle.rule.WordNetSyncRule.WordNetSyncTrigger#getMpmeTextBoundaries()
		 */
		@Override
		public NLBoundaries getMpmeTextBoundaries() throws NotYetAvailableException {
			return getLabelValueBoundaries();
		}
		
		/**
		 * @return The impact area in the last value
		 * @throws NotYetAvailableException When the impact area in the last value is not yet available.
		 */
		public ImpactArea<Integer, String> getImpactAreaInLast() 
			throws NotYetAvailableException {
			if (impactAreaInLast == null) 
				throw new NotYetAvailableException("The impact area in the last value");
			return impactAreaInLast;
		}

		
		
		/**
		 * GMF Label is controlled by WrappingLabel. So we have to
		 * recognize (return true for) every WrappingLabel of
		 * AbstractGraphicalEditParts.
		 * 
		 * @see tw.edu.nccu.shuttle.MPHook#isHookableIntoCacheSolved(Object)
		 */
		protected boolean isHookableIntoCacheSolved(IDecoratorTarget mpme) {
			if (hookedGmfLabel != null) return true;
			
			try {
				hookedEditPart = (AbstractGraphicalEditPart) 
						mpme.getAdapter(AbstractGraphicalEditPart.class);
				hookedGmfLabel = (WrappingLabel) hookedEditPart.getFigure();
				return true;
			} catch (ClassCastException e) {
				// supports the host of AbstractGraphicalEditPart ONLY!
				return false;
			}
		}

		/**
		 * @aspect DelayedHooking
		 * @see tw.edu.nccu.shuttle.rule.RuleTrigger#hookIntoCacheSolved(Object)
		 */
		protected void hookIntoCacheSolved(IDecoratorTarget mpme) 
		throws IllegalArgumentException {
			if (hookedGmfLabel != null) return;

			super.hookIntoCacheSolved(mpme);
			try {
				hookedEditPart = (AbstractGraphicalEditPart) 
						((IDecoratorTarget)mpme).getAdapter(AbstractGraphicalEditPart.class);
				hookedGmfLabel = (WrappingLabel) hookedEditPart.getFigure();
			} catch (NullPointerException e) {
				throw IGmfLabelSyncRule.IMHOOKABLE_EXCEPTION;
			} catch (ClassCastException e) {
				throw IGmfLabelSyncRule.IMHOOKABLE_EXCEPTION;
			}
			
			// label text change is NOT going to trigger bean property change
			// event currently
			hookedEditPart.addEditPartListener(this);
//			hookedGmfLabel.getUpdateManager().addUpdateListener(this);
//			hookedGmfLabel.addKeyListener(this);
			
			labelValue = hookedGmfLabel.getText();
			// @aspect ManagerWordNetSyncRule
			//	after the following label has boundary detected!
			labelValueBoundaries = new NLBoundaries(labelValue);
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
					// TODO: history of Impact Area
					// Focusing on the latest Impact Segment is enough for
					// today's recommendation!
					impactAreaInLast = 
						labelValueBoundaries.getImpactAreaInHost(labelValue);
					
					// for Triggered MPMEs - Rules (Concepts) invloved in
					// ImpactArea are triggered! (excluding Recommenders on
					// Triggering MPMEs AGAIN!)
					for (WordNetSyncRule<?,?,?> likelyChangingConcept 
							: GmfLabelWordNetSyncRule.getRules(impactAreaInLast))
						likelyChangingConcept.ruleTriggered(new RuleEvent(likelyChangingConcept, this, null));
					
				} catch (NoSuchObjectException e) {
					// if there's NO Impact Area, then do nothing (including 
					//	triggering changing concepts) for now!
				} catch (NoSuchElementException e) {
					// Do nothing (no recommendations) if there's no rule triggered/matched
				}
//				triggerToUnrecommendHistory(this);
				
				try {
					NLBoundaries newBoundaries = new NLBoundaries(labelValue);
					
					// for Triggering MPMEs - check deleted Concepts to de-link
					//	Triggers/Recommenders and un-recommend application
					//
					// @aspect Bound2ObjectThanWildcard
					Set<GmfLabelWordNetSyncRule> newConcepts = GmfLabelWordNetSyncRule.getRules(newBoundaries);
					for (WordNetSyncRule<?, ?, ?> lastConcept : GmfLabelWordNetSyncRule.getRules(labelValueBoundaries))
						if (!newConcepts.contains(lastConcept)) 
							lastConcept.ruleUnmatched(new RuleEvent(lastConcept, this, null));
					
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
					 * ManagerWordNetSyncRule}) to instruct the 
					 * following Trigger/Recommender hooking.
					 */
					for (GmfLabelWordNetSyncRule newConcept : newConcepts) {
						newConcept.hookInto(hookedMpme);
						break;
					}
					
				} catch (Exception e) {
					// Should NOT be here for UnsupportedOperationException and IllegalArgumentException!
					// Do nothing (no recommendations) if there's no rule triggered/matched.
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
	public static class GmfLabelWordNetSyncRecommender extends GmfLabelSyncRecommender {

		private WrappingLabel hookedGmfLabel;
		private IFigure lastRecommendation;

		
		
//		protected GmfLabelWordNetSyncRecommender() {
//			super();
//		}
		/**
		 * @aspect ClosedTriggerRecommenderEnv - a private constructor to prevent the
		 *         construction from out-side world
		 * @aspect DelayedHooking
		 * @see tw.edu.nccu.shuttle.rule.WordNetSyncRule.WordNetSyncRecommender#newRecommender()
		 */
		@Override
		public GmfLabelWordNetSyncRecommender newRecommender() {
			return new GmfLabelWordNetSyncRecommender();
		}


		
		/**
		 * @aspect DelayedHooking
		 * @see tw.edu.nccu.shuttle.gmf.GmfLabelSyncRecommender#hookIntoCacheSolved(Object)
		 */
		protected void hookIntoCacheSolved(IDecoratorTarget mpme) throws IllegalArgumentException {
			if (hookedGmfLabel != null) return;
			
			super.hookIntoCacheSolved(mpme);
			
			try {
				hookedGmfLabel = (WrappingLabel) ((AbstractGraphicalEditPart) 
						mpme.getAdapter(AbstractGraphicalEditPart.class)).getFigure();
			} catch (NullPointerException e) {
				throw IGmfLabelSyncRule.IMHOOKABLE_EXCEPTION;
			} catch (ClassCastException e) {
				throw IGmfLabelSyncRule.IMHOOKABLE_EXCEPTION;
			}
		}

		protected boolean isHookableIntoCacheSolved(IDecoratorTarget mpme) {
			if (hookedGmfLabel != null) return true;
			
			try {
				hookedGmfLabel = (WrappingLabel) ((AbstractGraphicalEditPart) 
						mpme.getAdapter(AbstractGraphicalEditPart.class)).getFigure();
				return true;
			} catch (ClassCastException e) {
				// supports the host of AbstractGraphicalEditPart ONLY!
				return false;
			}
		}



		/**
		 * @aspect ShowingHidingHint
		 * Directly going matched to recommend application, therefore fresh overriding!
		 * Since I'm a very specific Recommender, my hosts SHOULD be very specific too.
		 * 
		 * @see RecommendationHintDecoratorProvider
		 * @see tw.edu.nccu.shuttle.rule.RuleRecommender#ruleTriggered(tw.edu.nccu.shuttle.rule.RuleEvent)
		 */
		@Override
		public void ruleTriggered(RuleEvent e) {
			Rule<?,?,?> triggerHost = e.getSource();
			// Guarded direct recommending - ONLY for {@link GmfLabelWordNetSyncRule}
			if (triggerHost instanceof GmfLabelWordNetSyncRule) 
				triggerHost.ruleMatched(new RuleEvent(triggerHost, e.getTrigger(), this));
		}

		
		
		/**
		 * @see tw.edu.nccu.shuttle.rule.RuleRecommender#ruleMatched(tw.edu.nccu.shuttle.rule.RuleEvent)
		 */
		@Override
		public void ruleMatched(RuleEvent re) {
			Rule<?,?, ?> triggeredHost = re.getSource();
			RuleTrigger<?> trigger = re.getTrigger();
			// Guarded recommending - ONLY for {@link GmfLabelWordNetSyncRule}
			if (!(triggeredHost instanceof GmfLabelWordNetSyncRule)) return;
			if (!(trigger instanceof GmfLabelWordNetSyncTrigger)) return;
			super.ruleMatched(re);
			
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


		
		/**
		 * @param rule - The unrecommending Concept
		 * @see tw.edu.nccu.shuttle.rule.RuleRecommender#ruleUnmatched(tw.edu.nccu.shuttle.rule.RuleEvent)
		 */
		@Override
		public void ruleUnmatched(RuleEvent e) {
			// hide Hint	@aspect ShowingHidingHint, RecommProcess
			assert recommendationHint != null;
			if (recommendationHint.hide(e.getSource()))
				// TODO: de-set Application Recommendation	@aspect RecommProcess
				hookedGmfLabel.setToolTip(lastRecommendation);
		}

	}
	
	
	
	/**
	 * @aspect ManagerWordNetSyncRule
	 * @param sys
	 * @throws NoSuchObjectException
	 */
	public GmfLabelWordNetSyncRule(tw.edu.nccu.shuttle.System sys) throws NoSuchObjectException {
		super(sys, new GmfLabelWordNetSyncTrigger(), new GmfLabelWordNetSyncRecommender());
	}
	
	private GmfLabelWordNetSyncRule(WordNetSyncSet wnConcept) {
		super(wnConcept);
	}

	@Override
	public GmfLabelWordNetSyncRule newRule(WordNetSyncSet wnConcept) {
		return new GmfLabelWordNetSyncRule(wnConcept);
	}

	

//	public static final GmfLabelWordNetSyncRule	getManagerRule(System sys) throws NoSuchObjectException {
//		try {
//			return (GmfLabelWordNetSyncRule) getManagerRule();
//		} catch (NotYetAvailableException e) {
//			return new GmfLabelWordNetSyncRule(sys);
//		}
//	}

	/**
	 * @return The manager trigger as specified in {@link WordNetSyncRule#getManagerTrigger()} without null.
	 */
	static public GmfLabelWordNetSyncTrigger getManagerTrigger() {
		if (MANAGER_TRIGGER == null || !(MANAGER_TRIGGER instanceof GmfLabelWordNetSyncTrigger)) {
			MANAGER_TRIGGER = new GmfLabelWordNetSyncTrigger();
		}
		return (GmfLabelWordNetSyncTrigger) MANAGER_TRIGGER;
	}

	/**
	 * @return The manager recommender as specified in {@link WordNetSyncRule#getManagerRecommender()} without null.
	 */
	static public GmfLabelWordNetSyncRecommender getManagerRecommender() {
		if (MANAGER_RECOMMENDER == null || !(MANAGER_RECOMMENDER instanceof GmfLabelWordNetSyncRecommender)) {
			MANAGER_RECOMMENDER = new GmfLabelWordNetSyncRecommender();
		}
		return (GmfLabelWordNetSyncRecommender) MANAGER_RECOMMENDER;
	}

}
