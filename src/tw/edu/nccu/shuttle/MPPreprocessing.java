/**
 * 
 */
package tw.edu.nccu.shuttle;

import java.util.AbstractQueue;
import java.util.Collections;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentLinkedQueue;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import tw.edu.nccu.shuttle.rule.Rule;
import tw.edu.nccu.shuttle.rule.RuleRecommender;

/**
 * A concurrency part of whole modeling platform handling interested model 
 * element hooking efficiently using Eclipse Jobs concurrency mechanism.
 * The hooking is kind of pre-processing hence this class is for temporary
 * queueing storage.
 * 
 * @author Kao, Chen-yi
 *
 */
public class MPPreprocessing<ModelElement> {

	/**
	 * Referring http://help.eclipse.org/help32/topic/org.eclipse.platform.doc.isv/guide/runtime_jobs_progress.htm
	 *
	 */
	private class HookIntoModelElementJob extends Job {
		public HookIntoModelElementJob(String meType) {
			super("Shuttle initialization - hooking into " + meType);
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
		 * This overriding is to ensure that each run is set definitely when 
		 * {@link mesToBeHooked} is NOT empty.
		 * 
		 * @see org.eclipse.core.runtime.jobs.Job#shouldRun()
		 */
		public boolean shouldRun() {
			return !mesToBeHooked.isEmpty();
		}
		
		@Override
		public IStatus run(IProgressMonitor monitor) {
			ModelElement me;
			
			monitor.beginTask("Constructing concept rules", mesToBeHooked.size());
			do {	// Each run is set definitely when mesToBeHooked is NOT empty.
				me = mesToBeHooked.poll();
				for (Rule<Object, ?, ?> rule : mp.getRules(me)) {
					// register Trigger, for supportedMPMESelection,
					//	then Recommender, for Antecedent/Consequent MPME Selection,
					// 	then complete the main mission after all:
					//	installing the decorator!
					rule.hookInto(me);
				}
				
				monitor.worked(1);
			} while (!mesToBeHooked.isEmpty());
			monitor.done();
		    
			return Status.OK_STATUS;
        }
		
	}


	
	private HookIntoModelElementJob meHookingJob;
	private AbstractQueue<ModelElement> mesToBeHooked;
	
	protected System mp;

	
	
	public MPPreprocessing(System modelingPlatform, String meType) {
		if (modelingPlatform == null) throw new IllegalArgumentException();
		mp = modelingPlatform;
		meHookingJob = new HookIntoModelElementJob(meType);
		mesToBeHooked = new ConcurrentLinkedQueue<ModelElement>();
	}

	
	
	/**
	 * Job-lized operation for initialization performance (response time) improvement
	 * using single-job-many-tasks model (w/ queuing decoratorTargets)
	 * for predicting progress
	 * 
	 * PlatformUI.getWorkbench().getProgressService().run(...) does NOT run in background
	 * 
	 * No cache checking here since 
	 * (1) {@link mesToBeHooked} is a linearly traversed linked list;
	 * (2) there's already underlying cache checking in {@link Rule#hookInto(Object)}.
	 * 
	 */
	public void add(ModelElement me) {
		if (isMEHookable(me)) mesToBeHooked.offer(me);
//		if (dtsHookingJob == null) dtsHookingJob = new CreateDecoratorsJob();
	}

	public void schedule() {
		meHookingJob.schedule();
	}

	
	
	/**
	 * @aspect OneRecommenderOneMeType
	 * @param me
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public RuleRecommender<ModelElement> getCoupledRecommender(ModelElement me) throws NotYetAvailableException {
		try {
			return (RuleRecommender<ModelElement>) mp.getRules(me).iterator().next().getCoupledRecommender(me);
		} catch (NoSuchElementException e) {
			throw new NotYetAvailableException("Supported rules");
		}
	}

	/**
	 * @return
	 * @throws NotYetAvailableException
	 */
	@SuppressWarnings("unchecked")
	public Set<RuleRecommender<ModelElement>> getTriggeredRecommenders(ModelElement me)	throws NotYetAvailableException {
		try {
			Set<RuleRecommender<ModelElement>> recomms = 
					Collections.synchronizedSet(new HashSet<RuleRecommender<ModelElement>>());
			for (Rule<Object, ?, ?> supportingRule : mp.getRules(me))
				recomms.addAll((Set<RuleRecommender<ModelElement>>)supportingRule.getTriggeredRecommenders(me));
			return recomms;

		} catch (NoSuchElementException e) {
			throw new NotYetAvailableException("Supporting rules");
		}
	}

	
	
	public boolean isMEHookable(ModelElement me) {
		try {
			mp.getRules(me);
			return true;
		} catch (NoSuchElementException e) {
			return false;
		}
	}

}
