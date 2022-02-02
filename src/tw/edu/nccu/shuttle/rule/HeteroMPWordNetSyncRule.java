/**
 * @author Kao, Chen-yi
 * 
 */
package tw.edu.nccu.shuttle.rule;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import tw.edu.nccu.shuttle.IndexedCacheTable;
import tw.edu.nccu.shuttle.MPHook;
import tw.edu.nccu.shuttle.NLBoundaries;
import tw.edu.nccu.shuttle.NotYetAvailableException;
import tw.edu.nccu.shuttle.System;
import tw.edu.nccu.shuttle.WordNetSyncSet;

/**
 * @aspect HeterogeneousModelingPlatform
 * For orchestrating heterogeneous modeling platforms (including text and GMF).
 *  
 * @aspect TwoLevelTriggerRecommenders
 * TODO: Top level: One-document-one-trigger/recommender for scalability in mega-
 * team project collaboration. 
 * ME level: One-ME-type-one-trigger/recommender for simpler design. ME level 
 * triggers/recommenders are lazily loaded.
 * 
 */
public class HeteroMPWordNetSyncRule extends WordNetSyncRule<
Object, HeteroMPWordNetSyncRule.HeteroMPWordNetSyncTrigger, HeteroMPWordNetSyncRule.HeteroMPWordNetSyncRecommender> {



	/**
	 * @aspect HeterogeneousModelingPlatform
	 *
	 */
	final protected static class HeteroMPWordNetSyncHook extends MPHook<Object> {

		/**
		 * About real sub-MP ME trigger/recommender:
		 * <ul>
		 * <li>{@link #meHookDelegate}, {@link #meManagerHookDelegate} - concrete (manager) trigger/recommender
		 * 		ex. {@link TextWordNetSyncRule.TextWordNetSyncTrigger}
		 * <li>{@link #meHookMethod} - using reflection because of casting problem, 
		 * 		ex. {@link TextWordNetSyncRule.TextWordNetSyncTrigger#hookInto(DocumentedRegion)}
		 * </ul>
		 */
		private boolean isTOrR;
		private MPHook<?> meHookDelegate;
		private MPHook<?> meManagerHookDelegate;
		private Method meHookMethod;
		/**
		 * Ordered lists corresponding to sub-MP rule types
		 */
		private List<Class<? extends WordNetSyncRule<?,?,?>>> subMpRuleClasses;
		private Method HOOK_CHECKER;
		private List<MPHook<?>> managerHookDelegateList;
		/**
		 * ME type based mapping cache
		 */
		private IndexedCacheTable<Object, Boolean> meHookableTable;

		
		
		/**
		 * @param isTriggerOrRecommender 
		 * @param subMpRuleTypes
		 * @param isMeHookTrigger - Denoting that this hook is trigger or recommender (if false).
		 */
		public HeteroMPWordNetSyncHook(
				boolean isTriggerOrRecommender, List<Class<? extends WordNetSyncRule<?, ?, ?>>> subMpRuleTypes) {
			super();
			
			isTOrR = isTriggerOrRecommender;
//			if (subMpRules == null) return;
			subMpRuleClasses = subMpRuleTypes;
			managerHookDelegateList = new ArrayList<MPHook<?>>();
			meHookableTable = new IndexedCacheTable<Object, Boolean>();
			try {
				HOOK_CHECKER = MPHook.class.getMethod("isHookableInto", Object.class);
			} catch (NoSuchMethodException e) {			// Shall not happen!
			}
		}

		protected HeteroMPWordNetSyncHook(HeteroMPWordNetSyncHook recommenderToClone) {
			super();
			
			isTOrR = recommenderToClone.isTOrR;
			meHookDelegate = recommenderToClone.meHookDelegate;
			meManagerHookDelegate = recommenderToClone.meManagerHookDelegate;
			meHookMethod = recommenderToClone.meHookMethod;

			subMpRuleClasses = recommenderToClone.subMpRuleClasses;
			HOOK_CHECKER = recommenderToClone.HOOK_CHECKER;
			managerHookDelegateList = new ArrayList<MPHook<?>>(recommenderToClone.managerHookDelegateList);
			meHookableTable = new IndexedCacheTable<Object, Boolean>(recommenderToClone.meHookableTable);
		}



		public MPHook<?> getDelegate() {
			return meHookDelegate;
		}
		
		public Method getDelegateHookMethod() {
			return meHookMethod;
		}
		
		public MPHook<?> getManagerDelegate() {
			return meManagerHookDelegate;
		}
		
		public List<Class<? extends WordNetSyncRule<?,?,?>>> getSubMpRules() {
			return subMpRuleClasses;
		}
		
		
		
		/**
		 * Hook-common check with homomorphic delegating.
		 * Handling hook-common method checking, collecting and caching.
		 * 
		 * @see tw.edu.nccu.shuttle.MPHook#isHookableIntoCacheSolved(Object)
		 */
		@Override
		protected boolean isHookableIntoCacheSolved(Object mpme) {
			if (mpme == null) return false;

			try {
				
				// 1) try ME type based checker cache
				return meHookableTable.getCache(mpme);
				
			} catch (NoSuchElementException e) { try {
				
				// 2) not-cached means the ME type is unknown, then exhaustively testing the type of ME
				Class<? extends WordNetSyncRule<?,?,?>> subMpRuleClass;
				final int subMpRuleClassSize = subMpRuleClasses.size();
				int i;
				
				if (managerHookDelegateList.isEmpty()) {
					// 2.1) Caching manager trigger/recommender and hook checker
					for (i = 0; i < subMpRuleClassSize; i++) try {
						subMpRuleClass = subMpRuleClasses.get(i);
						
						managerHookDelegateList.add((MPHook<?>) (subMpRuleClass.getMethod(
								isTOrR ? "getManagerTrigger" : "getManagerRecommender").invoke(subMpRuleClass)));

					} catch (NoSuchMethodException e1) {			// Shall not happen!
					}
					// reset manager trigger/recommender to hetero-MP one
					HeteroMPWordNetSyncRule.getManagerTrigger();
					HeteroMPWordNetSyncRule.getManagerRecommender();
				}
					
				for (i = 0; i < subMpRuleClassSize; i++) {
					
					// 2.2) Getting manager trigger/recommender class by class
					subMpRuleClass = subMpRuleClasses.get(i);
					meManagerHookDelegate = managerHookDelegateList.get(i);
					
					// 2.3) Checking type of ME by invoking isHookableInto(mpme) of manager trigger/recommender
					// Direct meHookDelegate.isHookableInto(mpme); has ModelElement casting problem:
					//	ex., GmfLabelWOrdNetSyncRule.IDecoratorTarget wasn't able to be casted to HPHook.ModelElement
					boolean isHookableIntoMe = (Boolean) HOOK_CHECKER.invoke(meManagerHookDelegate, mpme);
					meHookableTable.indexAndCache(mpme, isHookableIntoMe);
					
					if (isHookableIntoMe) return true;
					
				}
				} catch (IllegalAccessException e1) {
					e1.printStackTrace();	// Shall not happen!
				} catch (IllegalArgumentException e1) {
					e1.printStackTrace();	// Shall not happen!
				} catch (InvocationTargetException e1) {
					e1.printStackTrace();	// Shall not happen!
				} catch (SecurityException e1) {
					e1.printStackTrace();	// Shall not happen!
				}
			} catch (IllegalArgumentException e) {
				e.printStackTrace();	// Shall not happen!
			}
			return false;
		}

		/**
		 * Homomorphic hook-common delegating. Handling hook-common method caching.
		 * 
		 * @see tw.edu.nccu.shuttle.MPHook#hookIntoCacheSolved(Object)
		 */
		@Override
		protected void hookIntoCacheSolved(Object mpme)	throws UnsupportedOperationException, IllegalArgumentException {

			try {
				if (meHookMethod == null) {
					
					meHookDelegate = isTOrR ? ((WordNetSyncTrigger<?>) meManagerHookDelegate).newTrigger() : 
							((WordNetSyncRecommender<?>) meManagerHookDelegate).newRecommender();
							
					// Direct meHookDelegate.hookInto(mpme); has ModelElement casting problem:
					//	ex., GmfLabelWOrdNetSyncRule.IDecoratorTarget wasn't able to be casted to HPHook.ModelElement
					meHookMethod = MPHook.class.getMethod("hookInto", Object.class);

				}
				meHookMethod.invoke(meHookDelegate, mpme);

			} catch (NoSuchMethodException e) {
				e.printStackTrace();	// Shall not happen!
			} catch (SecurityException e) {
				e.printStackTrace();	// Shall not happen!
			} catch (IllegalAccessException e) {
				e.printStackTrace();	// Shall not happen!
			} catch (InvocationTargetException e) {
				e.printStackTrace();	// Shall not happen!
			}
		}

		
		
		@SuppressWarnings("unchecked")
		public void addHost(@SuppressWarnings("rawtypes") Rule host) {
			if (isTOrR) ((WordNetSyncTrigger<?>) meHookDelegate).addHost(host);
			else ((WordNetSyncRecommender<?>) meHookDelegate).addHost(host);
		}
		
	}





	/**
	 * @aspect HeterogeneousModelingPlatform
	 * {@link #meTypeManagerTrigger} is {@link TextWordNetSyncTrigger} or {@link GmfWordNetSyncTrigger} inclusively.
	 * 
	 * @aspect ClosedTriggerRecommenderEnv - innering class to prevent the
	 *         addTrigger(...) with inherited or constructed classes of
	 *         out-side world
	 */
	final protected static class HeteroMPWordNetSyncTrigger extends WordNetSyncRule.WordNetSyncTrigger<Object> {

		private HeteroMPWordNetSyncHook delegateTrigger;

		/**
		 * Trigger-specific mapping cache
		 */
		final private IndexedCacheTable<WordNetSyncTrigger<?>, Method> tvbGetterTable =
				new IndexedCacheTable<WordNetSyncTrigger<?>, Method>();
		final private IndexedCacheTable<WordNetSyncTrigger<?>, Method> trsGetterTable =
				new IndexedCacheTable<WordNetSyncTrigger<?>, Method>();


		
		/**
		 * @aspect ClosedTriggerRecommenderEnv - a private constructor to prevent the
		 *         construction from out-side world
		 * @aspect DelayedHooking
		 */
		HeteroMPWordNetSyncTrigger(List<Class<? extends WordNetSyncRule<?,?,?>>> subMpRuleTypes) {
//			if (subMpRules == null) return;
			delegateTrigger = new HeteroMPWordNetSyncHook(true, subMpRuleTypes);
		}

		private HeteroMPWordNetSyncTrigger(HeteroMPWordNetSyncHook triggerToClone) {
			delegateTrigger = new HeteroMPWordNetSyncHook(triggerToClone);
		}
		
		@Override
		public HeteroMPWordNetSyncTrigger newTrigger() {
			return (HeteroMPWordNetSyncTrigger) this.clone();
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#clone()
		 */
		@Override
		public Object clone() {
			return new HeteroMPWordNetSyncTrigger(this.delegateTrigger);
		}

		
		
		/**
		 * @see tw.edu.nccu.shuttle.rule.WordNetSyncRule.WordNetSyncTrigger#getMpmeTextBoundaries()
		 */
		@Override
		public NLBoundaries getMpmeTextBoundaries() throws NotYetAvailableException {
			try {
				WordNetSyncTrigger<?> thisDelegate = (WordNetSyncRule.WordNetSyncTrigger<?>) delegateTrigger.getDelegate();
				return (NLBoundaries) tvbGetterTable.getCache(thisDelegate).invoke(thisDelegate);
			} catch (Exception e) {	// NoSuchElementException or NullPointerException
				throw new NotYetAvailableException("The model element");
			}
		}

		/**
		 * Considering possible overlapping of MEs for some MPs.
		 * 
		 * @see tw.edu.nccu.shuttle.rule.RuleTrigger#getTriggeredRecommenders()
		 */
		@SuppressWarnings("unchecked")
		@Override
		public Set<? extends RuleRecommender<Object>> getTriggeredRecommenders()
				throws NullPointerException, NotYetAvailableException {
			try {
				WordNetSyncTrigger<?> thisDelegate = (WordNetSyncRule.WordNetSyncTrigger<?>) delegateTrigger.getDelegate();
				return (Set<? extends RuleRecommender<Object>>) 
					trsGetterTable.getCache(thisDelegate).invoke(thisDelegate);
			} catch (Exception e) {	// NoSuchElementException or NullPointerException
				throw new NotYetAvailableException("The triggered recommenders");
			}
		}

		
		
		/**
		 * @aspect HeterogeneousModelingPlatform - Delegating trigger hook checking to the corresponding MPs.
		 * 
		 * @see tw.edu.nccu.shuttle.MPHook#isHookableIntoCacheSolved(Object)
		 */
		protected boolean isHookableIntoCacheSolved(Object mpme) {
			return delegateTrigger.isHookableIntoCacheSolved(mpme);
		}

		/**
		 * @aspect HeterogeneousModelingPlatform - Delegating trigger hooking to the corresponding MPs.
		 * @aspect DelayedHooking
		 * 
		 * @see tw.edu.nccu.shuttle.rule.RuleTrigger#hookIntoCacheSolved(Object)
		 */
		protected void hookIntoCacheSolved(Object mpme) throws IllegalArgumentException {
			super.hookIntoCacheSolved(mpme);

			// common sub-MP trigger hooking
			delegateTrigger.hookIntoCacheSolved(mpme);
			
			// handling trigger-specific method caching
			WordNetSyncRule.WordNetSyncTrigger<?> thisDelegate = 
					(WordNetSyncRule.WordNetSyncTrigger<?>) delegateTrigger.getDelegate();
			try {
				tvbGetterTable.indexAndCache(thisDelegate, WordNetSyncTrigger.class.getMethod("getMpmeTextBoundaries"));
				trsGetterTable.indexAndCache(thisDelegate, WordNetSyncTrigger.class.getMethod("getTriggeredRecommenders"));
			} catch (NoSuchMethodException e1) {
				e1.printStackTrace();	// Shall not happen!
			} catch (SecurityException e1) {
				e1.printStackTrace();	// Shall not happen!
			}
		}

		
		
		/**
		 * @see tw.edu.nccu.shuttle.rule.RuleTrigger#addHost(tw.edu.nccu.shuttle.rule.Rule)
		 */
		@Override
		public <Trigger extends RuleTrigger<Object>> void addHost(
				Rule<Object, Trigger, ? extends RuleRecommender<Object>> host) {
//			super.addHost(host);
			delegateTrigger.addHost(host);
		}

	}





	/**
	 * @aspect ClosedTriggerRecommenderEnv - this class is made inner to prevent the
	 *         addRecommender(...) with inherited or constructed classes of
	 *         out-side world
	 */
	final protected static class HeteroMPWordNetSyncRecommender 
	extends WordNetSyncRule.WordNetSyncRecommender<Object> implements Cloneable {

		private HeteroMPWordNetSyncHook delegateRecommender;
		
		

		/**
		 * @aspect ClosedTriggerRecommenderEnv - a private constructor to prevent the
		 *         construction from out-side world
		 * @aspect DelayedHooking
		 */
		HeteroMPWordNetSyncRecommender(List<Class<? extends WordNetSyncRule<?,?,?>>> subMpRuleTypes) {
//			if (subMpRules == null) return;
			delegateRecommender = new HeteroMPWordNetSyncHook(false, subMpRuleTypes);
		}
		
		private HeteroMPWordNetSyncRecommender(HeteroMPWordNetSyncHook recommenderToClone) {
			delegateRecommender = new HeteroMPWordNetSyncHook(recommenderToClone);
		}

		@Override
		public HeteroMPWordNetSyncRecommender newRecommender() {
			return (HeteroMPWordNetSyncRecommender) this.clone();
		}

		/* (non-Javadoc)
		 * @see java.lang.Object#clone()
		 */
		@Override
		public Object clone() {
			return new HeteroMPWordNetSyncRecommender(this.delegateRecommender);
		}

		
		
		/**
		 * @see tw.edu.nccu.shuttle.MPHook#isHookableIntoCacheSolved(java.lang.Object)
		 */
		protected boolean isHookableIntoCacheSolved(Object mpme) {
			return delegateRecommender.isHookableIntoCacheSolved(mpme);
		}
		
		/**
		 * @aspect DelayedHooking
		 * @see tw.edu.nccu.shuttle.rule.RuleRecommender#hookIntoCacheSolved(Object)
		 */
		protected void hookIntoCacheSolved(Object mpme) throws IllegalArgumentException {
			super.hookIntoCacheSolved(mpme);

			// common sub-MP recommender hooking
			delegateRecommender.hookIntoCacheSolved(mpme);
		}

		
		
		/**
		 * @see tw.edu.nccu.shuttle.rule.RuleRecommender#addHost(tw.edu.nccu.shuttle.rule.Rule)
		 */
		@Override
		public <Recommender extends RuleRecommender<Object>> void addHost(Rule<Object, ?, Recommender> host) {
//			super.addHost(host);
			delegateRecommender.addHost(host);
		}

	}

	
	
	
	
	private static HeteroMPWordNetSyncTrigger CACHED_MANAGER_TRIGGER;
	private static HeteroMPWordNetSyncRecommender CACHED_MANAGER_RECOMMENDER;

	
	
	/**
	 * @aspect ManagerWordNetSyncRule - Only performing a trigger/recommender constructor delegate
	 * 
	 * @param modelingPlatform
	 * @param subMpRuleTypes - null/empty list means supporting no platforms
	 * @throws NoSuchObjectException
	 */
	public HeteroMPWordNetSyncRule(System modelingPlatform, List<Class<? extends WordNetSyncRule<?,?,?>>> subMpRuleTypes) 
			throws NoSuchObjectException {
		super(
				modelingPlatform, 
				new HeteroMPWordNetSyncTrigger(subMpRuleTypes), 
				new HeteroMPWordNetSyncRecommender(subMpRuleTypes));
		CACHED_MANAGER_TRIGGER = (HeteroMPWordNetSyncTrigger) MANAGER_TRIGGER;
		CACHED_MANAGER_RECOMMENDER = (HeteroMPWordNetSyncRecommender) MANAGER_RECOMMENDER;
	}

	private HeteroMPWordNetSyncRule(WordNetSyncSet wnConcept) {
		super(wnConcept);
	}
	
	@Override
	public HeteroMPWordNetSyncRule newRule(WordNetSyncSet wnConcept) {
		return new HeteroMPWordNetSyncRule(wnConcept);
	}
	

	
	/**
	 * @return The manager trigger as specified in {@link WordNetSyncRule#getManagerTrigger()} without null.
	 */
	static public HeteroMPWordNetSyncTrigger getManagerTrigger() {
		return (HeteroMPWordNetSyncTrigger) (MANAGER_TRIGGER = CACHED_MANAGER_TRIGGER);
	}

	/**
	 * @return The manager recommender as specified in {@link WordNetSyncRule#getManagerRecommender()} without null.
	 */
	static public HeteroMPWordNetSyncRecommender getManagerRecommender() {
		return (HeteroMPWordNetSyncRecommender) (MANAGER_RECOMMENDER = CACHED_MANAGER_RECOMMENDER);
	}

}
