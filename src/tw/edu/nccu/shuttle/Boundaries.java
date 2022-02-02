/**
 * 
 */
package tw.edu.nccu.shuttle;

import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author Kao, Chen-yi
 * 
 */
abstract public class Boundaries<Subject, SubjectHost> implements Iterable<Subject> {
	
	final static public NoSuchObjectException NO_IMPACTAREA_EXCEPTION = 
		new NoSuchObjectException(null);
	final static public UnsupportedOperationException NOT_SUPPORTED_EXCEPTION = 
		new UnsupportedOperationException();
	
	
	
	protected SubjectHost host = null;
	protected ArrayList<Subject> boundaries;
	
	private Boolean hostIsEmpty;
	private Boolean newHostIsEmpty;
	
	
	
	/**
	 * @aspect NullAllowing - Allowing empty host (text) for future Impact Area
	 *         calculation.
	 * @param host
	 */
	protected Boundaries(SubjectHost host) {
		this.host = host;
		boundaries = new ArrayList<Subject>();
		
		hostIsEmpty = 
			(host == null || host.toString().trim().length() == 0)?true:false;
		newHostIsEmpty = null;
	}

	
	
	/**
	 * @return the host
	 */
	public SubjectHost getHost() {
		return host;
	}

	/**
	 * @aspect NullAllowing - Allowing empty host (text) for future Impact Area
	 *         calculation.
	 * @param newHost
	 * @return ALWAYS NO RETURN (with UnsupportedOperationException) - child
	 *         classes have to override it if they are concrete Boundaries'!
	 * @throws NoSuchObjectException -
	 *             if there is no host (text) or nothing changed
	 * @throws UnsupportedOperationException -
	 *             always
	 */
	public ImpactArea<Subject, SubjectHost> getImpactArea(SubjectHost newHost) 
		throws NoSuchObjectException, UnsupportedOperationException {
		// checking for equivalence
		if (host != null && host.equals(newHost)) throw NO_IMPACTAREA_EXCEPTION;
		
		// @aspect NullAllowing - checking for equivalence of emptiness
		if (newHostIsEmpty == null) newHostIsEmpty = 
			(newHost == null || newHost.toString().trim().length() == 0)?true:false;
		if (hostIsEmpty && newHostIsEmpty) throw NO_IMPACTAREA_EXCEPTION;
		
		throw NOT_SUPPORTED_EXCEPTION;
	}
	
	public ImpactArea<Subject, SubjectHost> getImpactAreaInHost(SubjectHost newHost) 
		throws NoSuchObjectException, UnsupportedOperationException {
		// @aspect NullAllowing - checking for equivalence of emptiness
		newHostIsEmpty = 
			(newHost == null || newHost.toString().trim().length() == 0)?true:false;
		if (hostIsEmpty) throw NO_IMPACTAREA_EXCEPTION;

		return getImpactArea(newHost);
	}
	
	public ImpactArea<Subject, SubjectHost> getImpactAreaInNew(SubjectHost newHost) 
		throws NoSuchObjectException, UnsupportedOperationException {
		// @aspect NullAllowing - checking for equivalence of emptiness
		newHostIsEmpty = 
			(newHost == null || newHost.toString().trim().length() == 0)?true:false;
		if (newHostIsEmpty) throw NO_IMPACTAREA_EXCEPTION;
		
		return getImpactArea(newHost);
	}

	
	
	public Iterator<Subject> iterator() throws UnsupportedOperationException {
		return boundaries.iterator();
	}
		    
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object anotherBoundaries) {
		try {
			return host.equals(((Boundaries<?, ?>) anotherBoundaries).getHost());
		} catch (ClassCastException e) {return false;}
	}

	public int size() {
		return boundaries.size();
	}
	
}
