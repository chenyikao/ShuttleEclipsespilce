package tw.edu.nccu.shuttle;


/**
 * @author Kao, Chen-yi
 *
 */
public interface MpmeSelection {

	public abstract boolean isSingleTypeSelection();

	/**
	 * Return {@link Class} of ALL model elements to select 
	 * if such type exists.
	 * 
	 * TODO: throw No Such Type Exception if this selection isn't a type selection.
	 * 
	 * @return the Class Type of ME
	 * 
	 */
//	public abstract Class getType();

}