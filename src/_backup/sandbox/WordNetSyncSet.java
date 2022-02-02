/**
 * 
 */
package tw.edu.nccu.shuttle.sandbox;

import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.ISynsetID;

/**
 * Based on WordNet Synsets (implemented MIT JWI ISynsetID).
 * 
 * ??more OO, process both basic and advanced concept search here.
 * 
 * @author Kao, Chen-yi
 *
 */
public class WordNetSyncSet implements ConceptSyncSet {

	static protected IDictionary ConceptResolver = null;
	final static protected String CONCEPT_RESOLVER_NOT_AVAILABLE = 
		"WordNet Synset Resolver NOT available!";
	
	protected ISynsetID concept;
	
	public WordNetSyncSet(ISynsetID wnConcept, IDictionary synsetResolver) 
	throws NullPointerException {
		if (wnConcept == null) throw new NullPointerException();
		concept = wnConcept;
		if (synsetResolver != ConceptResolver) ConceptResolver = synsetResolver;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		if (ConceptResolver == null) return CONCEPT_RESOLVER_NOT_AVAILABLE;
		return ConceptResolver.getSynset(concept).toString();
	}

}
