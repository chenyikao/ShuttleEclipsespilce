/**
 * 
 */
package tw.edu.nccu.shuttle.sandbox;

import java.util.HashSet;

import org.eclipse.core.runtime.IAdaptable;

/**
 * This abstract class is already geared with some caching technology for saving 
 * from possible redundant hookability checkings and hookings.
 * 
 * TODO: patternlize caching? Cache pattern?
 * 
 * @author Kao, Chen-yi
 *
 */
public abstract class MPHook {

	// using Set for constant time containment checking
	private HashSet<IAdaptable> mpHookableMes;
	private HashSet<IAdaptable> mpNonhookableMes;
	private HashSet<IAdaptable> mpHookedMes;
	
	public final static UnsupportedOperationException NON_HOOKABLE_EXCEPTION =
		new UnsupportedOperationException();

	protected MPHook() {
		// TODO: needs to save memory initially (limit the initial capacity
		// reasonably)?
		mpHookableMes = new HashSet<IAdaptable>();
		mpNonhookableMes = new HashSet<IAdaptable>();
		mpHookedMes = new HashSet<IAdaptable>();
	}
	
	final public boolean isHookableInto(IAdaptable mpmeAdaptable) {
		// checking both positive and negative caches
		// @aspect NegativeCache
		if (mpHookableMes.contains(mpmeAdaptable)) return true;
		if (mpNonhookableMes.contains(mpmeAdaptable)) return false;

		// updating the caches
		if (isHookableIntoCacheSolved(mpmeAdaptable)) {
			mpHookableMes.add(mpmeAdaptable);
			return true;
		} else {
			mpNonhookableMes.add(mpmeAdaptable);
			return false;
		}
	}

	protected abstract boolean isHookableIntoCacheSolved(IAdaptable mpmeAdaptable);


	final public void hookInto(IAdaptable mpmeAdaptable)
	throws UnsupportedOperationException, IllegalArgumentException {
		if (isHookableInto(mpmeAdaptable)) {
			try {
				if (!mpHookedMes.contains(mpmeAdaptable)) {
					hookIntoCacheSolved(mpmeAdaptable);
					mpHookedMes.add(mpmeAdaptable);
				}
				return;
			} catch (UnsupportedOperationException e) {
				assert false; // contradiction if already confirmed to be hookable!
			}
		} else
			throw NON_HOOKABLE_EXCEPTION;
	}

	protected abstract void hookIntoCacheSolved(IAdaptable mpmeAdaptable)
			throws UnsupportedOperationException, IllegalArgumentException; 

//	TODO: final public void unhookFrom(IAdaptable mpmeAdaptable)
//	throws UnsupportedOperationException, IllegalArgumentException {...}
//	protected abstract void unhookFromCacheSolved(IAdaptable mpmeAdaptable)
//	throws UnsupportedOperationException, IllegalArgumentException; 
}
