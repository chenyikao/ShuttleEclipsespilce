/**
 * 
 */
package tw.edu.nccu.shuttle;

/**
 * @author Kao, Chen-yi
 *
 */
public interface ModelingPlatform {

	public static final NotYetAvailableException MPME_NOT_YET_AVAILABLE_EXCEPTION = 
			new NotYetAvailableException("MPME");
	public static final NotYetAvailableException MPME_MOD_NOT_YET_AVAILABLE_EXCEPTION = 
			new NotYetAvailableException("MPME modification");

}
