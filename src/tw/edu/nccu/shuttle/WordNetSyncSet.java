package tw.edu.nccu.shuttle;

import edu.mit.jwi.IDictionary;
import edu.mit.jwi.item.ISynsetID;

/**
 * Based on WordNet Synsets (implemented MIT JWI ISynsetID).
 * 
 * TODO: more Object-oriented, process both basic and advanced concept search here.
 * 
 * @author Kao, Chen-yi
 *
 */
public class WordNetSyncSet implements ConceptSyncSet<ISynsetID> {

	static final protected String CONCEPT_RESOLVER_NOT_AVAILABLE = 
			"WordNet Synset Resolver NOT available!";
	
	static protected IDictionary ConceptResolver = null;

	
	
	protected ISynsetID concept;
	protected int rel;
	
	
	
	/**
	 * @param wnConcept - Given concept in WordNet Synset.
	 * @param relativity - <ul>
	 * 	<li><code>0</code>: Exactly the ME text content; 
	 * 	<li><code>1+</code>: a WordNet distance between the ME text content and given concept.
	 * 	</ul> 
	 * @param synsetResolver
	 * @throws NullPointerException
	 * 	When {@link #wnConcept} is null.
	 */
	public WordNetSyncSet(ISynsetID wnConcept, int relativity, IDictionary synsetResolver) throws NullPointerException {
		if (wnConcept == null) throw System.NULL_ARGUMENT_EXCEPTION;
		concept = wnConcept;
		rel = relativity;
		if (ConceptResolver != synsetResolver) ConceptResolver = synsetResolver;
	}
	
	
	
	/**
	 * @see tw.edu.nccu.shuttle.ConceptSyncSet#getConcept()
	 */
	public String getConceptName() {
		if (ConceptResolver == null) return CONCEPT_RESOLVER_NOT_AVAILABLE;
		return ConceptResolver.getSynset(concept).toString();
	}

	/**
	 * @return the concept in WordNet Synset.
	 * @see tw.edu.nccu.shuttle.ConceptSyncSet#getConcept()
	 */
	public ISynsetID getConcept() {
		return concept;
	}

	/**
	 * @return the relativity.
	 */
	public int getRelativity() {
		return rel;
	}



	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		try {
			WordNetSyncSet target = (WordNetSyncSet)obj;
			return target.getConcept().equals(concept) && target.getRelativity() == rel;
		} catch (ClassCastException e) {
			return false;
		}
	}

}
