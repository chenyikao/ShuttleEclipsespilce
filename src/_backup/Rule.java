package edu.nccu.eclipsespilce.sandbox;

import java.util.ArrayList;



/**
 * TODO: Hierarchical MP inference - Rules and inferences can be multi-leveled.
 * Rules of higher levels ask ones of lower levels for concrete
 * visualization of triggering, matching and recommendation.<br/> 
 * 
 * Use Visitor or Containment pattern? Or leave it to MP Sub-inference-engine?<br/> 
 * 
 * ONLY care for the hierarchies involving MPs. Leave other hierarchical 
 * inferences to KAON2.
 * 
 * <pre>
 * Only in MP-level Rule should know MPMEs 
 * -> MPRule SHOULD be separated from Rule
 * </pre> 
 *
 * @author Kao, Chen-yi
 * 
 */
public class Rule {

	// TODO: for temporary demo. 
	//	Recommender should be imported and shared from the system ({@link Activator}).
	private ArrayList<RuleRecommender> recommenders = 
		new ArrayList<RuleRecommender>();
	
	/**
	 * TODO: Rule should be triggered by supported and existing MPMEs, 
	 * which are deployed
	 * with Rule Triggering Listener (another face of MP Sub-inference engine)
	 * while being "decorated".
	 * 
	 * TODO: trigger(triggeredMPME)?
	 * 
	 * TODO: triggering flow
	 * ...
	 * Check the triggering coverage in Antecedent
	 * -> if Antecedent is fully covered by triggering
	 * -> Rule is matched (i.e. {@link #match()}).
	 * 
	 * @see RecommendationHintDecoratorProvider
	 */
	public RuleRecommender trigger() {
		// TODO: Recommend the match
		// recommender.recommendMatch(...);
		// DecoratorProvider.createDecorators(...);
		// TODO: for temporary demo. One-step trigger/match
		RuleRecommender newRecommender = new RuleRecommender(this);
		// SHOULD NOT be duplicated recommenders here (everyone is newly created!)
		assert(recommenders.add(newRecommender) == true);
		return newRecommender;
	}
	
	public void match(RuleRecommender recommender) {
		// TODO: Currently just plays a notification to {@link Rule}.
		//	recommender.recommendApplication(triggeredMPMESelections, consequentMPMESelections)?
		recommender.recommendApplication();
	}

	public void apply() {
		// TODO ??
	}

}
