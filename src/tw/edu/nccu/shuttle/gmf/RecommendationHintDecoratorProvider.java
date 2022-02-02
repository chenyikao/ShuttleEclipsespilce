package tw.edu.nccu.shuttle.gmf;

import org.eclipse.gmf.runtime.common.core.service.IOperation;
import org.eclipse.gmf.runtime.common.core.service.IProviderChangeListener;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.CreateDecoratorsOperation;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorProvider;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget;

import tw.edu.nccu.shuttle.MPPreprocessing;
import tw.edu.nccu.shuttle.System;

/**
 * <p>
 * Currently the provider probably is in singleton pattern - one provider in
 * whole Shuttle running. This centralized pattern may provide efficiency.
 * </p>
 *
 * TODO: Maybe adapt to the pattern of one provider per RuleRecommender or even
 * one provider per Rule (merging provider and RuleRecommender) with one
 * self-maintaining Recommendation Hint mechanism under GMF
 * Service-Provider-ProviderChangeListener framework?
 *
 * @author Kao, Chen-yi
 *
 */
public class RecommendationHintDecoratorProvider implements IDecoratorProvider {

	final private MPPreprocessing<IDecoratorTarget> prepOfDTs = 
		new MPPreprocessing<IDecoratorTarget>(System.getDefault(), "GMF Labels");
	
	
	
	/**
	 * <p>
	 * Attaching triggered Rule to opened (viewable) GMF Editors by the way.
	 * Including deploying Rule Triggering Listener (another face of MP
	 * Sub-inference engine). See "roundtrip way of locating T-MPMEs" in
	 * {@link System}.
	 * </p>
	 *
	 * <p>
	 * Creating Decorator (hooking Rule) does NOT mean triggering the Rule. It just
	 * hooks, or installs, the Rule Triggers/Recommenders.
	 * </p>
	 *
	 * @see org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorProvider#createDecorators(org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget)
	 */
	public void createDecorators(IDecoratorTarget decoratorTarget) {
		prepOfDTs.add(decoratorTarget);
	}

	
	public boolean provides(IOperation operation) {
		if (!(operation instanceof CreateDecoratorsOperation)) return false;
		return prepOfDTs.isMEHookable(
				((CreateDecoratorsOperation) operation).getDecoratorTarget());
	}

	
	public void addProviderChangeListener(IProviderChangeListener listener) {
		// TODO Auto-generated method stub
		
	}
	
	
	public void removeProviderChangeListener(IProviderChangeListener listener) {
		// TODO Auto-generated method stub

	}

}
