/**
 * 
 */
package tw.edu.nccu.shuttle.rule;

/**
 * @author Kao, Chen-yi
 */
public interface RecommendationHint {

	/**
	 * while any Triggered MPME is hovered, show Recommendation (Consequent MPMEs)
	 * @param forConcept TODO
	 */
	void show(Rule<?, ?, ?> forConcept);

	/**
	 * @param forConcept
	 * @return true if the hint is truly hidden
	 */
	boolean hide(Rule<?, ?, ?> forConcept);

}
