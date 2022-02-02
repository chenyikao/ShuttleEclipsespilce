/**
 * 
 */
package tw.edu.nccu.shuttle;

/**
 * A concept is a set of semantically related keywords.
 * 
 * @author Kao, Chen-yi
 * 
 */
public interface ConceptSyncSet<Concept> {
	
	/**
	 * @return the core concept in {@link Concept} type.
	 */
	public Concept getConcept();
	
	/**
	 * @return the name of {@link Concept}.
	 */
	public String getConceptName();

}
