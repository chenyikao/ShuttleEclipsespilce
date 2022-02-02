package tw.edu.nccu.shuttle.sandbox;


public interface MPMESelection {

	public abstract boolean isSingleTypeSelection();

	/**
	 * Return {@link Class} of ALL model elements to select 
	 * if such type exists.
	 * 
	 * @return the Class Type of ME
	 */
	public abstract Class getType();

}