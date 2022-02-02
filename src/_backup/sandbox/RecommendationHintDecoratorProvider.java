package tw.edu.nccu.shuttle.sandbox;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.concurrent.ArrayBlockingQueue;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.IJobChangeEvent;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.core.runtime.jobs.JobChangeAdapter;
import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.CreateDecoratorsOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget;
import org.eclipse.jface.operation.IRunnableWithProgress;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.progress.WorkbenchJob;

/**
 * <p>
 * Currently the provider probably is in singleton pattern - one provider in
 * whole Shuttle running. This centralized pattern may provide efficiency.
 * </p>
 *
 * TODO: Maybe adapt to the pattern of one provider per RuleRecommender or even
 * one provider per Rule (merging provider and RuleRecommender) with one
 * self-maintaining Recommendation Hint mechanism under GMF
 * Service-Provider-ProviderChangeListener framework?
 *
 * @author Kao, Chen-yi
 *
 */
public class RecommendationHintDecoratorProvider implements IDecoratorProvider {

	static private class SupportedRuleChecker {
		/**
		 * An efficient table for monitoring matched IDecoratorTargets and Rules (for
		 * accessing monitored ME's)
		 */
		final private MonitoredRuleTable<IDecoratorTarget>
			DECORATORTARGET_RULE_TABLE = new MonitoredRuleTable<IDecoratorTarget>();

		/**
		 * TODO: Triggering- and consequent-supporting MP ME Selections SHOULD
		 * be distinguished.
		 *
		 * @param decoratorTarget -
		 *            Decorator Target
		 * @return IElementType ME Adapter For the Decorator Target
		 */
		Iterable<Rule<?, ?>> getRules(IDecoratorTarget decoratorTarget)
				throws NoSuchElementException {
			try {

				// if DECORATORTARGET_RULE_TABLE has the rule, then return it
				// TODO: monitor negative target-rules at the same time for
				//	negative matching performance!
				// 	@aspect NegativeCache
				return DECORATORTARGET_RULE_TABLE.getRules(decoratorTarget);

			} catch (NoSuchElementException e) {

				// if not monitored, check all rules' Triggers, for
				// supportedMPMESelection, then Recommenders, for
				// antecedentMPMESelection/consequentMPMESelection
				HashSet<Rule<?, ?>> newDTRules = new HashSet<Rule<?, ?>>();
				for (Rule<?, ?> rule : System.getSupportedRules())
					if (rule.isHookableInto(decoratorTarget)) newDTRules.add(rule);
				if (newDTRules.isEmpty()) throw e;
				else {
					DECORATORTARGET_RULE_TABLE.monitorRules(
							decoratorTarget, newDTRules);
					return newDTRules;
				}

			}
		}

	}

	/**
	 * Referring http://help.eclipse.org/help32/topic/org.eclipse.platform.doc.isv/guide/runtime_jobs_progress.htm
	 * @author Kao, Chen-yi
	 *
	 */
	private class CreateDecoratorsJob extends Job {
		public CreateDecoratorsJob() {
			super("Shuttle initialization");
			this.setUser(true);
			this.setPriority(Job.LONG);
//			this.addJobChangeListener(new JobChangeAdapter() {
//				public void done(IJobChangeEvent event) {
//					if (!dtsToBeHooked.isEmpty()) event.getJob().schedule();
					// BUG!! if dtsToBeHooked.isEmpty(), all dtsToBeHooked puts hereafter would miss.
//				}
//			});
//			// The job is created definitely after a successful dtsToBeHooked put.
//			this.schedule();
		}
        
		/**
		 * This overriding is to ensure that each run is set definitely when dtsToBeHooked is NOT empty.
		 * @see org.eclipse.core.runtime.jobs.Job#shouldRun()
		 */
		public boolean shouldRun() {
			return !dtsToBeHooked.isEmpty();
		}
		
		@Override
		public IStatus run(IProgressMonitor monitor) {
			IDecoratorTarget decoratorTarget;
			
			monitor.beginTask("Constructing concept rules", dtsToBeHooked.size());
			do {	// Each run is set definitely when dtsToBeHooked is NOT empty.
				decoratorTarget = dtsToBeHooked.poll();
				for (Rule<?, ?> rule : supportedRuleChecker.getRules(decoratorTarget)) {
					// register Trigger, for supportedMPMESelection,
					//	then Recommender, for Antecedent/Consequent MPME Selection,
					// 	then complete the main mission after all:
					//	installing the decorator!
					rule.hookInto(decoratorTarget);
				}
				
				monitor.worked(1);
			} while (!dtsToBeHooked.isEmpty());
			monitor.done();
		    
			return Status.OK_STATUS;
        }
		
	}

	final private SupportedRuleChecker supportedRuleChecker = new SupportedRuleChecker();
	final private ArrayBlockingQueue<IDecoratorTarget> dtsToBeHooked = 
		new ArrayBlockingQueue<IDecoratorTarget>(100);	//100 is a guess number!
	final private CreateDecoratorsJob dtsHookingJob = new CreateDecoratorsJob();

	/**
	 * <p>
	 * Attaching triggered Rule to opened (viewable) GMF Editors by the way.
	 * Including deploying Rule Triggering Listener (another face of MP
	 * Sub-inference engine). See "roundtrip way of locating T-MPMEs" in
	 * {@link System}.
	 * </p>
	 *
	 * <p>
	 * Creating Decorator (hooking Rule) does NOT mean triggering the Rule. It just
	 * hooks, or installs, the Rule Triggers/Recommenders.
	 * </p>
	 *
	 * @see org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorProvider#createDecorators(org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget)
	 */
	public void createDecorators(IDecoratorTarget decoratorTarget) {
		try {
			dtsToBeHooked.put(decoratorTarget);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//Job-lized operation for initialization performance (response time) improvement
		//	using single-job-many-tasks model (w/ queuing decoratorTargets)
		//	for predicting progress
		//	PlatformUI.getWorkbench().getProgressService().run(...) does NOT run in background
		dtsHookingJob.schedule();
//		if (dtsHookingJob == null) dtsHookingJob = new CreateDecoratorsJob();
	}

	public void addProviderChangeListener(IProviderChangeListener listener) {
		// TODO Auto-generated method stub

	}

	public boolean provides(IOperation operation) {
		if (!(operation instanceof CreateDecoratorsOperation)) return false;

		try {
			supportedRuleChecker.getRules(
					((CreateDecoratorsOperation) operation).getDecoratorTarget());
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

	public void removeProviderChangeListener(IProviderChangeListener listener) {
		// TODO Auto-generated method stub

	}

}
