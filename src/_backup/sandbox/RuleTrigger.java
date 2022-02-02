/**
 * 
 */
package tw.edu.nccu.shuttle.sandbox;

import java.rmi.NoSuchObjectException;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.runtime.IAdaptable;

/**
 * One trigger per model element per rule.
 * 
 * @author Kao, Chen-yi
 *
 */
public abstract class RuleTrigger extends MPHook {

	protected Set<Rule<? extends RuleTrigger, ?>> hosts;
	
	protected IAdaptable hookedMpme = null;
	
	protected RuleTrigger() {
		super();
		hosts = new HashSet<Rule<? extends RuleTrigger, ?>>();
	}
	
	@SuppressWarnings("unchecked")
	public <Trigger extends RuleTrigger> void addHost(Rule<Trigger, ?> host) {
		// @aspect AvoidingCycle
		if (!hosts.contains(host)) {
			hosts.add(host);
			host.addTrigger((Trigger) this);
		}
	}

	@SuppressWarnings("unchecked")
	public <Trigger extends RuleTrigger> void removeHost(Rule<Trigger, ?> host) {
		// @aspect AvoidingCycle
		if (hosts.contains(host)) {
			hosts.remove(host);
			host.removeTrigger((Trigger) this);
		}
	}

	/**
	 * @return the hookedMpme
	 */
	public IAdaptable getHookedMpme() throws NoSuchObjectException {
		if (hookedMpme == null) throw System.NOT_YET_EXIST_EXCEPTION;
		return hookedMpme;
	}

	/* (non-Javadoc)
	 * @see tw.edu.nccu.shuttle.sandbox.MPHook#hookIntoCacheSolved(org.eclipse.core.runtime.IAdaptable)
	 */
	@Override
	protected void hookIntoCacheSolved(IAdaptable mpmeAdaptable)
			throws UnsupportedOperationException, IllegalArgumentException {
		hookedMpme = mpmeAdaptable;
	}
	
}
