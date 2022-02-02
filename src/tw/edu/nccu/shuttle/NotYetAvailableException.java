/**
 * 
 */
package tw.edu.nccu.shuttle;

import java.rmi.NoSuchObjectException;

/**
 * @author Kao, Chen-yi
 *
 */
public class NotYetAvailableException extends NoSuchObjectException {

	/**
	 * TODO: Finalize instances of the same {@link roleType}.
	 * 
	 * TODO: Hope to recognize the singular/plural form of {@link #roleType}
	 * automatically.
	 * 
	 * @param roleType
	 */
	public NotYetAvailableException(String roleType) {
		super(roleType + " is/are not yet available.");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
