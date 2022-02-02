/**
 * 
 */
package tw.edu.nccu.shuttle.sandbox;

import org.eclipse.core.runtime.IAdaptable;

/**
 * @author Kao, Chen-yi
 *
 */
public abstract class GmfLabelSyncTrigger extends RuleTrigger {

	/* (non-Javadoc)
	 * @see tw.edu.nccu.shuttle.sandbox.MPHook#hookIntoCacheSolved(org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	protected void hookIntoCacheSolved(IAdaptable mpmeAdaptable)
			throws UnsupportedOperationException, IllegalArgumentException {
		// ??level up from GmfLabelWordNetSyncTrigger
		super.hookIntoCacheSolved(mpmeAdaptable);

	}

	/* (non-Javadoc)
	 * @see tw.edu.nccu.shuttle.sandbox.MPHook#isHookableIntoCacheSolved(org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	protected boolean isHookableIntoCacheSolved(IAdaptable mpmeAdaptable) {
		// ??level up from GmfLabelWordNetSyncTrigger
		return super.isHookableInto(mpmeAdaptable);
	}

}
