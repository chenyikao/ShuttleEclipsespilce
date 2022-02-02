package tw.edu.nccu.shuttle.gmf;

import java.util.ArrayList;

import org.eclipse.gmf.runtime.diagram.ui.services.decorator.AbstractDecorator;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoration;
import org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget;
import org.eclipse.swt.graphics.Image;

import tw.edu.nccu.shuttle.System;
import tw.edu.nccu.shuttle.rule.RecommendationHint;
import tw.edu.nccu.shuttle.rule.Rule;

/**
 * The Decorator itself is a Hint (self-decoration).
 * 
 * @author Kao, Chen-yi
 *
 */
public class RecommendationHintDecorator extends AbstractDecorator implements RecommendationHint {

	static final private String HINT_ID = "tw.edu.nccu.shuttle.GMFIntegrationDemo2.RecommendationHint";
	static final private Image HINT_IMAGE = System.imageDescriptorFromPlugin(
			// Currently the image is directly copied from
			//	/icons/full/ovr16 of plug-in org.eclipse.jdt.ui
			// TODO:
			//	1) migrate/centralize the image resource to System and
			//	2) reuse the image in plug-in org.eclipse.jdt.ui if detected
			System.PLUGIN_ID, "icons/warning_co.gif").createImage();

	static private boolean IsHintImageRegistered = false;
	
	
	
	private IDecoration hint;
	/**
	 * Using ArrayList for that a decorator target (MPME) may link to duplicate
	 * Concepts via any Sub-Bounded-String.
	 */
	private ArrayList<Rule<?,?,?>> hintForRules = new ArrayList<Rule<?,?,?>>();
	
	

	/**
	 * @param decoratorTarget
	 */
	public RecommendationHintDecorator(IDecoratorTarget decoratorTarget) {
		super(decoratorTarget);

		/**
		 * When an icon is used frequently to display items in a particular
		 * viewer, it can be shared among similar items in the viewer using a
		 * Label Provider.
		 * 
		 * Hence to take the advantage of Label Provider: register Decorator to
		 * Label Provider -> register Label Provider to Diagram Editor
		 * 
		 * The GMF Presentation Decorator Providers Extension Point should have
		 * already done this for us! So we just care for utilizing the plug-in
		 * Image Registry.
		 */
		synchronized (HINT_IMAGE) {
			if (!IsHintImageRegistered) {
				System.getDefault().getImageRegistry().put(HINT_ID, HINT_IMAGE);
				IsHintImageRegistered = true;
			}
		}
	}

	
	
	public void activate() {
		// initially (?) hide!
	}

	public void refresh() {
		// show() or hide()
		if (hintForRules.isEmpty()) hide();
		else show();
	}


	
	/**
	 * Show anyway!
	 */
	private void show() {
		// TODO: the shape decoration volatility can be left as a preference
		hint = getDecoratorTarget().addShapeDecoration(
				HINT_IMAGE, IDecoratorTarget.Direction.SOUTH_EAST, -1, true);
		setDecoration(hint);
	}

	/**
	 * Hide anyway!
	 */
	private void hide() {
//		getDecoratorTarget().removeDecoration(hint);	// failed!
//		removeDecoration();								// failed!
		deactivate();									// failed!
	}

	/**
	 * Allowing show for nothing (show anyway)!
	 * 
	 * @see tw.edu.nccu.shuttle.rule.RecommendationHint#show(tw.edu.nccu.shuttle.rule.Rule)
	 */
	public void show(Rule<?,?,?> forRule) {
		if (forRule != null) hintForRules.add(forRule);
		if (hintForRules.size() == 1) show();	// show once for performance
	}

	/**
	 * Allowing hide for nothing (hide anyway)!
	 * 
	 * @see tw.edu.nccu.shuttle.rule.RecommendationHint#hide(tw.edu.nccu.shuttle.rule.Rule)
	 */
	public boolean hide(Rule<?,?,?> forRule) {
		// Rule to hide for may be NOT triggered before because NOT all deleted
		// concepts (e.g. "application") are triggered ones (e.g. "apply") and
		// (TODO:) GmfLabelWordNetSyncRecommender currently does NOT filter the
		// untriggered ones.
		if (hintForRules.isEmpty()) return false;
		// probably never shown before, so remove(...) may return false
		if (forRule != null) hintForRules.remove(forRule);
		if (forRule == null || hintForRules.isEmpty()) {
			hide();
			return true;
		} else return false;
	}

}
