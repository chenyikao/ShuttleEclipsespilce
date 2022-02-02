/**
 * 
 */
package tw.edu.nccu.shuttle;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * Three major derivation types: {@link RuleTrigger Trigger}, {@link RuleRecommender Recommender}
 * and {@link Rule}.
 * 
 * This abstract class is already geared with some caching technology for saving 
 * from possible redundant hookability checkings and hookings.
 * 
 * TODO: Pattern-lize caching? Cache pattern?
 * 
 * @author Kao, Chen-yi
 * 
 */
public abstract class MPHook<ModelElement> {

	static public final UnsupportedOperationException NON_HOOKABLE_EXCEPTION =
			new UnsupportedOperationException();
	
	// using Set for constant time containment checking
	private Set<ModelElement> mpHookableMes;
	private Set<ModelElement> mpNonhookableMes;
	private Set<ModelElement> mpHookedMes;
	
	protected MPHook() {
		// TODO: needs to save memory initially (limit the initial capacity
		// reasonably)?
		mpHookableMes = Collections.synchronizedSet(new HashSet<ModelElement>());
		mpNonhookableMes = Collections.synchronizedSet(new HashSet<ModelElement>());
		mpHookedMes = Collections.synchronizedSet(new HashSet<ModelElement>());
	}
	
	
	
//	TODO: public Class<ModelElement> getElementType() {return ModelElement.class;}
	
	
	
	/**
	 * An efficient checking if this hook belongs to the three major derivation types, 
	 * without run-time type reflection and superclass traversal.
	 * @return 
	 * 	true - if it belongs to the type {@link RuleTrigger Trigger};<br/>
	 * 	false - otherwise.
	 */
	public boolean isTrigger() {return false;}
	
	/**
	 * An efficient checking if this hook belongs to the three major derivation types, 
	 * without run-time type reflection and superclass traversal.
	 * @return 
	 * 	true - if it belongs to the type {@link RuleRecommender Recommender};<br/>
	 * 	false - otherwise.
	 */
	public boolean isRecommender() {return false;}
	
	/**
	 * An efficient checking if this hook belongs to the three major derivation types, 
	 * without run-time type reflection and superclass traversal.
	 * @return 
	 * 	true - if it belongs to the type {@link Rule};<br/>
	 * 	false - otherwise.
	 */
	public boolean isRule() {return false;}
	
	
	
	final public boolean isHookableInto(ModelElement mpme) {
		// checking both positive and negative caches
		// @aspect NegativeCache
		if (mpHookableMes.contains(mpme)) return true;
		if (mpNonhookableMes.contains(mpme)) return false;

		// updating the caches
		if (isHookableIntoCacheSolved(mpme)) {
			mpHookableMes.add(mpme);
			return true;
		} else {
			mpNonhookableMes.add(mpme);
			return false;
		}
	}

	/**
	 * Checking if the given ME is hookable beyond just type checking.
	 * 
	 * @param mpme
	 * @return
	 */
	abstract protected boolean isHookableIntoCacheSolved(ModelElement mpme);


	final public void hookInto(ModelElement mpme)
	throws UnsupportedOperationException, IllegalArgumentException {
		if (isHookableInto(mpme)) {
			try {
				if (!mpHookedMes.contains(mpme)) {
					hookIntoCacheSolved(mpme);
					mpHookedMes.add(mpme);
				}
				return;
			} catch (UnsupportedOperationException e) {
				assert false; // contradiction if already confirmed to be hookable!
			}
		} else
			throw NON_HOOKABLE_EXCEPTION;
	}

	protected abstract void hookIntoCacheSolved(ModelElement mpme)
			throws UnsupportedOperationException, IllegalArgumentException; 

//	TODO: final public void unhookFrom(ModelElement mpme)
//	throws UnsupportedOperationException, IllegalArgumentException {...}

//	TODO: protected abstract void unhookFromCacheSolved(ModelElement mpme)
//	throws UnsupportedOperationException, IllegalArgumentException; 
}
