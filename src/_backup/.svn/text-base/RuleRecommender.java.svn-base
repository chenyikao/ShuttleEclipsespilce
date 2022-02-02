package edu.nccu.shuttle.sandbox;

import java.util.ArrayList;
import java.util.Collection;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseMotionListener;
import org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.notation.View;

/**
 * <p>Rule Recommender connects abstract {@link Rule} and concrete (visual) MP and 
 * handles every legacy MP ME associated to a {@link Recommendation}, like 
 * triggering and consequent MP MEs, etc.</p>
 * 
 * <p>One {@link Rule} many Recommenders (also Mouse Motion Listeners). 
 * And one Recommender handles ONLY one {@link Recommendation}.</P>
 * 
 * TODO: MP-generalization? Separate GMFRuleRecommender from RuleRecommender?
 * TODO: Decorator and Decorator Provider manipulation SHOULD be integrated here.
 * 
 * @author Kao, Chen-yi
 *
 */
public class RuleRecommender extends MouseMotionListener.Stub {

//	TODO:
//	SHOULD be updated via Rule.trigger()/match().
//	private ArrayList<??> triggeredMPMEs = new ArrayList();

	// TODO: performance issue: Collection SHOULD be replaced by hash-structure
	static private ArrayList<GmfMPMESelection> SupportedMPMESelections = 
		new ArrayList<GmfMPMESelection>();
	
	private Rule host;

	/**
	 * <p>Antecedent and Consequent Anchors are treated the same currently.
	 * But an Anchor is NOT meant to have any V-MPMEs attached.</p>
	 *  
	 * TODO: SHOULD be updated by the Creation Listener of model element?
	 */
//	private ArrayList<AnchorMPME> antecedentAnchors;
	private ArrayList<GmfMPME> anchorMPMEs = new ArrayList<GmfMPME>();
	
	private Recommendation recommendation;

	/**
	 * TODO: RuleRecommender(DecoratorProvider), ex.
	 * RuleRecommender(RecommendationHintDecoratorProvider),
	 * RuleRecommender(DeleteRecommendationHintDecoratorProvider)...
	 */
	public RuleRecommender(Rule host) {
		super();
		this.host = host;
	}

	/**
	 * This method SHOULD be invoked while ANY part of {@link Antecedent} is
	 * triggered.
	 */
	public void recommendMatch() {
		// TODO: getTriggeringMPMESelections()
	}

	/**
	 * This method SHOULD be invoked while the WHOLE {@link Antecedent} is triggered.
	 */
	public void recommendApplication() {
		// TODO: getConsequentMPMESelections(), ...
		// TODO: build application Recommendation, which SHOULD be different from
		//	matching Recommendation -> Recommendation(anchorMPMEs)
		recommendation = new Recommendation(anchorMPMEs);
	}

	/**
	 * TODO ??multi-level MPs
	 * Ecore-MP: .ecore declaration, referred by rule
	 * GMF-MP: plug.xml that extends the extension point 
	 * org.eclipse.gmf.runtime.emf.type.core.elementTypes, 
	 * referred by rule
	 * 
	 * TODO ??triggered MPME types SHOULD be categorized into
	 * meta-model vs. model level, according to if a triggered
	 * ontological element can be FULLY mapped to one MPME type.
	 * TODO ??Therefore a mapping alignment figure is needed.   
	 * TODO ??to retrieve the instant triggered types anytime, 
	 * TriggeredMPMETypes SHOULD be multi-thread synchronized.   
	 * 
	 * TODO ??The Ecore reflective API: 
	 * - static EcoreFactory org.eclipse.emf.ecore.impl.EcoreFactoryImpl.init() 
	 * - EClass org.eclipse.emf.ecore.EcoreFactory.createEClass() 
	 * 		or EDataType .createEDataType() 
	 * - org.eclipse.emf.ecore.EClassifier.setInstanceClassName(java.lang.String value)
	 * 
	 * @return
	 */
	public Collection<IElementType> getTriggeredMPMESelections() {
		return null;
	}

	/**
	 * <p>Supported MP ME Selections mean MP MEs referred by (or appearing in) 
	 * Rules.</p>
	 * 
	 * TODO: performance issue: Collection SHOULD be replaced by hash-structure
	 * 
	 * @return
	 */
	public static Collection<GmfMPMESelection> getSupportedMPMESelections() {
		/**
		 * TODO: for temporary demo. 
		 * In the future the de-/registration of Supported MPME Selections 
		 * SHOULD be done via Rule.dispose()/Rule() 
		 * (the deprecation/creation of a Rule).
		 */
		if (SupportedMPMESelections.isEmpty()) {
			SupportedMPMESelections.add(0, new GmfMPMESelection(ElementTypeRegistry
					.getInstance().getType("logic.led")));
		}
		return SupportedMPMESelections;
		// TODO: throw No Selection Exception 
		//	if there's no MP MEs that trigger any rule.
	}

	/**
	 * <p>Overrides an empty implementation.
	 * Recommendation is showed up only if NO Buttons are pressed during 
	 * mouse moving.</p> 
	 * 
	 * @see org.eclipse.draw2d.MouseMotionListener.Stub#mouseEntered(org.eclipse.draw2d.MouseEvent)
	 */
	@Override
	public void mouseEntered(MouseEvent me) {
		if (me.button < 1 || me.button > 3) {
			// TODO: Recommendation.visible = true -> View.visible = true
			recommendation.show();
		}
	}

	/**
	 * <p>Overrides an empty implementation.</P>
	 * 
	 * @see org.eclipse.draw2d.MouseMotionListener.Stub#mouseExited(org.eclipse.draw2d.MouseEvent)
	 */
	@Override
	public void mouseExited(MouseEvent me) {
		// TODO: performance issue SHOULD be abstract - guarded by Recommendation
		//	itself - modifier proxy 
//		Recommendation.visible = false
		// if (this.visible == true) ... 
	}

	/**
	 * <p>The matching of a Rule is determined here.</p>
	 * 
	 * TODO: Matching Completeness checking?
	 * 
	 * @param triggeringMPME - The Triggering MP ME for host {@link Rule}
	 */
	public void addTriggeringMPME(GmfMPME triggeringMPME) {
		addAnchorMPME(triggeringMPME);
		// TODO: for temporary demo. One-step trigger.
		//	Before match the MP ME Anchor SHOULD be handled and
		//	triggering Completeness SHOULD be checked.
		host.match(this);
	}

	/**
	 * <p>The automatic application of a Rule is determined here.</p>
	 * 
	 * TODO: Matching/Consequent Completeness checking
	 * 
	 * @param consequentMPME - The Consequent MP ME for host {@link Rule} 
	 */
	public void addConsequentMPME(GmfMPME consequentMPME) {
		addAnchorMPME(consequentMPME);
//		??host.apply();
	}

	/**
	 * <p>Antecedent and Consequent Anchors are treated the same currently.
	 * They both are Mouse Motion listerner too for providing a full Rule 
	 * application hint.</p>
	 * 
	 * @param anchorMPME - The Anchor MP ME for host {@link Rule}
	 */
	private void addAnchorMPME(GmfMPME anchorMPME) {
		if (anchorMPMEs.add(anchorMPME) == true) {
			anchorMPME.getFigure().addMouseMotionListener(this);
		}
	}

}
