package tw.edu.nccu.shuttle;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.NoSuchObjectException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;

import tw.edu.nccu.shuttle.gmf.GmfLabelWordNetSyncRule;
import tw.edu.nccu.shuttle.gmf.RecommendationHintDecoratorProvider;
import tw.edu.nccu.shuttle.rule.HeteroMPWordNetSyncRule;
import tw.edu.nccu.shuttle.rule.Rule;
import tw.edu.nccu.shuttle.rule.RuleRecommender;
import tw.edu.nccu.shuttle.rule.WordNetSyncRule;
import tw.edu.nccu.shuttle.texteditor.TextWordNetSyncRule;
import edu.mit.jwi.Dictionary;
import edu.mit.jwi.IDictionary;

/**
 * The activator class controls the plug-in life cycle.<br/>
 * 
 * Given example rules about Logic, which is the Modelling Platform (MP):
 * <pre>
 * 	TODO: Logic:LED -> Logic:LED + Logic:Wire + Logic:LED (in SWRL)?
 * 	TODO: Logic:* + Eclipse <- Logic_in_GMF:*?
 * </pre> 
 * 
 * 1. Presentation Decorator Providers - adding Recommendation Hints
 * 		- GMF Presentation Decorator Providers Extension Point 
 * 		- org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorProvider
 * 		- org.eclipse.gmf.runtime.diagram.ui.services.decorator.AbstractDecorator
 * 		(for retrieving the current edited model root) 
 * 		- org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor.getDiagramEditPart()
 * 		- Object org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart.getModel()
 * 
 * 		.1) register Shuttle Decorator Provider
 * 				1.1.1) use Eclipse/EMF/GEF/GMF Runtime to hook to the current editor
 * 				(may take advantage from Eclipse/EMF/GEF/GMF extension points)
 * 				(universal hooking for demostration, changed to metamodel-specific one in the future) 
 * 		.2) retrieve the demanded DecoratorTarget of MP (MP-DDT) accroding to the rule 
 * 		***ONLY invoke methods under Eclipse/EMF/GEF/GMF Runtime***
 * 		??via a 'static' method
 * 			2.1) retrieve the model elements
 * 				??) retrieve ALL current edited model elements?
 * 						- TopGraphicEditPart org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart.getTopGraphicEditPart()
 * 						- java.util.List org.eclipse.gmf.runtime.diagram.ui.editparts.TopGraphicEditPart.getResizableNotationViews() 
 * 						- java.lang.String org.eclipse.gmf.runtime.notation.View.getType()
 * 				2.1.3) when the decorator is activated ??and/or a rule is triggered, 
 * 				filter the demanded elements according to the rule
 * 						- ?? test if belonging to logic model by string matching
 * 						- org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget.getAdapter(...)
 * 			??) retrieve respect presentation elements (DecoratorTarget...)
 * 		3) append Shuttle Decoration
 * 		...
 * 		org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget.addShapeDecoration(Image image, IDecoratorTarget.Direction direction, int margin, boolean isVolatile) 
 * 		...
 * 
 * org.eclipse.gmf.runtime.diagram.ui.editpolicies.DecorationEditPolicy - 
 * 	The decorations are added to a different layer so that they have the option of 
 * 	being printed or not.
 * 
 * 2. Create Recommendation in MP (MP-R)
 * ***ONLY invoke methods under GMF Runtime***
 * MPM elements are supported -> triggering/consequent -> recommended -> applied
 * TODO: use private wrapper editor, like ShuttleDiagramEditor?
 *
 * TODO: The roundtrip way of locating: 
 * 	.1) Attach Rule Tracker (special Rule Listener?) to ALL potential 
 * 	Editors (by manual locating?):
 * 
 * 		Manual locating triggering/consequent part MP model elements (T-MPMEs/C-MPMEs)
 * 		.1) retrieve opened diagrams
 * 		<li>DiagramEditPart org.eclipse.ui.PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage().getActiveEditor()
 * 			(handle NullPointerException)<br/>
 * 			(TODO: for easy referring while modeling - 
 * 			DiagramEditPart org.eclipse.ui.PlatformUI.getWorkbench().getWorkbenchWindows().getPages().getEditorReferences())
 * 		</li>
 * 		.2) recursively check by Element Type
 * 		<li>GraphicalEditPart org.eclipse.gmf.runtime.diagram.ui.editparts.DiagramEditPart.getChildren()
 * 		<li>List<View> org.eclipse.gmf.runtime.diagram.ui.editparts.GraphicalEditPart.getModelChildren()
 * 		<li>EClass org.eclipse.gmf.runtime.notation.View.getElement().eClass()
 * 		<li>if EClass matches...
 * 
 * 	.2) Attach triggered Rule to S-MPMEs (by Recommendation Hint Decorator Provider?
 * 	since being decorated means appearing/triggering):
 * 	(see {@link RecommendationHintDecoratorProvider#createDecorators(org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget)})
 * 		??Rule triggered (subclass Rule listener to ShuttleDiagramEditor?)
 * 		??) teach to monitor T-MPMEs/C-MPMEs 
 * 		??decorate T-MPMEs & register mouse listener to them
 * 
 * 	.3) Editors fire the Rule Recommender to generate Recommendation: 
 * 		??Editor activated 
 * 		(register Rule listener as org.eclipse.ui.IPartListener2)
 * 			- org.eclipse.ui.IWorkbenchPage.addPartListener(IPartListener2)
 * 		??) register appeared T-MPMEs/C-MPMEs to the Recommender
 * 		??decorate T-MPMEs & register mouse listener to them
 * which can be integrated by subclassing Rule listener to ShuttleDiagramEditor?
 * 
 * 		.1) when a rule is supported, attach it to opened (viewable) Editors 
 * 		by {@link RecommendationHintDecoratorProvider} 
 * 		to monitor supported MP model elements (S-MPMEs) and register 
 * 		triggering(antecedent)/consequent part MP model elements (T-MPMEs/C-MPMEs) 
 * 		back to the {@link RuleRecommender} 
 * 		(see {@link Rule#trigger()}, {@link RuleRecommender#recommendMatch(Rule, Object)} and 
 * 		{@link RecommendationHintDecoratorProvider#createDecorators(org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget)});
 *
 * 		.2) TODO: when a rule is triggered/matched, register (or locate) 
 * 		T-MPMEs/C-MPMEs (see {@link Rule#match()} and
 * 		{@link RuleRecommender#recommendApplication(Object)});
 *
 * 		.3) ??generate the Recommendation, which is the composite of 'virtual' 
 * 		(non-existing in the MPM) MP model elements (V-MPMEs), Notations, 
 * 		Edit Parts and Figures for triggering/consequent part with respect to 
 * 		T-MPMEs/C-MPMEs (see {@link RuleRecommender#recommendApplication(Object)}); 
 *   
 * 		.4) TODO: locate the existing Figures for triggering MP model elements 
 * 		(T-MPMEs), as .1), including filter cached C-MPMEs
 *  
 * 		.5) register the Recommender as Mouse Motion Listener to the Figures
 * 		(see {@link RecommendationHintDecoratorProvider#createDecorators(org.eclipse.gmf.runtime.diagram.ui.services.decorator.IDecoratorTarget)}).
 * 
 * MPME role for the case here: 
 * ??Logic:LED (T-MPMEs) -> Logic:LED (C-MPMEs) + Logic:Wire (V-MPMEs) + Logic:LED (V-MPMEs)
 * 
 * 3. When mouse hovers, generate Recommendation in GMF from MP-R
 * ??Diagram Services Layer - How-to Guide > How to change the color of a shape dynamically
 * ??Element Type Registry
 * ??Edit Helper, Edit Helper Advice, Advice Bindings, Modeling Assistant Service
 * 		- org.eclipse.gmf.runtime.diagram.ui.parts.DiagramEditor.getDiagram()
 *		- org.eclipse.gmf.runtime.diagram.core.providers.IViewProvider.createEdge()
 * 	  	- org.eclipse.gmf.runtime.diagram.core.providers.IViewProvider.createNode()
 * 
 * @author Kao, Chen-yi
 *
 */
public class System extends AbstractUIPlugin {

	static final public NullPointerException NULL_ARGUMENT_EXCEPTION =
		new NullPointerException();

	// TODO: The plug-in ID, which is NOT automatically synchronized to
	// plugin.xml (MANIFEST.MF)
	public static final String PLUGIN_ID = Messages.System_PluginID;

	// The shared instance
	private static System plugin;

	
	
	/**
	 * TODO: The default GMF Label Synchronization Rule based on acronyms.
	 */
	final public static Rule<?, ?, ?> DEFAULT_GMFLABEL_ACRONYM_SYNCRULE = null;
//	final static public Rule demoRule = new Rule();
	

	
	/**
	 * @aspect Bound2ObjectThanWildcard -
	 * Using <code>Rule&lt;Object, ?, ?></code> instead of <code>Rule&lt;?, ?, ?></code>
	 * to bind ALL kinds of {@link ModelElement}s.
	 * 
	 * TODO: migrate to a proxy rule collection (some rules may reside in remote
	 * 	POMDAKs).
	 * 
	 * @aspect SynchronizedSet -
	 *	Could be modified by {@link HeteroMPWordNetSyncTrigger}s, so better made to
	 *	be a synchronized {@link java.util.Set Set}.
	 */
	final private Set<Rule<Object, ?, ?>> rules = Collections.synchronizedSet(new HashSet<Rule<Object, ?, ?>>());
	/**
	 * An efficient table for monitoring matched ModelElements and Rules (for
	 * accessing monitored ME's)
	 */
	final private IndexedCacheTable<Object, Rule<Object, ?, ?>> meHookableRules = 
			new IndexedCacheTable<Object, Rule<Object, ?, ?>>();

	private IDictionary wordNet;


	
	/**
	 * The constructor
	 */
	public System() {
		plugin = this;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		// @aspect JWIInitialization
		// 	construct the URL to the wordnet dictionary directory
		// TODO: System_WNDictFolderName SHOULD be detected automatically during
		// install and run time
		try { 
			URL url = new URL("file", null, Messages.System_WNDictFolderName); 
			wordNet = new Dictionary(url);
		} catch(MalformedURLException e){
			// TODO: throw WordNetNotFoundException - 
			//	SHOULD NOT happen if using FileChooser
			e.printStackTrace(); 
		}
		
		/**
		 * Registering initially supported (registered) synchronization rules.
		 * 
		 * @aspect HeterogeneousModelingPlatform
		 * @aspect ManagerWordNetSyncRule
		 * 
		 * Adding manager heterogeneous MP WordNet SyncRule, which initially has an
		 * empty Synset (concept) and <i>intentionally hooks to any supported MP</i>. 
		 * But when it's activated via
		 * {@link HeteroMPWordNetSyncRule#hookInto(Object)}
		 * later on, it manages and splits new Concept-specific one, i.e., can
		 * be associated to ANY single Synset, hooks to model-synchronizable ME's
		 * ONLY, and initiates the generation of other ME-specific WordNet
		 * SyncRules.
		 * 
		 * TODO supportedRules.add(DEFAULT_GMFLABEL_ACRONYM_SYNCRULE);
		 */
		List<Class<? extends WordNetSyncRule<?,?,?>>> supportedSubMpRules = 
				new ArrayList<Class<? extends WordNetSyncRule<?,?,?>>>();
		supportedSubMpRules.add(TextWordNetSyncRule.class);
		supportedSubMpRules.add(GmfLabelWordNetSyncRule.class);
		rules.add(new HeteroMPWordNetSyncRule(this, supportedSubMpRules));

//		java.lang.System.out.println("shuttle start!!");	// System.out works!

		super.start(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 * 
	 * @return the shared instance
	 */
	public static System getDefault() {
		return plugin;
	}

	/**
	 * @return the wordNet
	 * @throws NoSuchObjectException TODO
	 */
	public IDictionary getWordNet() throws NotYetAvailableException {
		if (wordNet == null) throw new NotYetAvailableException("WordNet");
		return wordNet;
	}

	
	
	/**
	 * To make a (intentionally new) rule supported by {@link System}.
	 * 
	 * @param rule
	 * @throws NullPointerException
	 */
	public void addRule(Rule<Object,?,?> rule) throws NullPointerException {
		if (rule == null) throw NULL_ARGUMENT_EXCEPTION;
		rules.add(rule);
	}
	
	/**
	 * TODO: Triggering- and consequent-supporting MP ME Selections SHOULD
	 * be distinguished.
	 *
	 * @param modelElement
	 * @return TODO:IElementType ME Adapter
	 */
	public Iterable<Rule<Object, ?, ?>> getRules(Object modelElement) throws NoSuchElementException {
		try {
	
			// if me_HOOKABLE_RULES has the rule, then return it
			// TODO: monitor negative target-rules at the same time for
			//	negative matching performance!
			// 	@aspect NegativeCache
			return meHookableRules.getCacheSet(modelElement);
	
		} catch (NoSuchElementException e) {
	
			// if not monitored, check all rules' Triggers, for
			// supportedMPMESelection, then Recommenders, for
			// antecedentMPMESelection/consequentMPMESelection
			Set<Rule<Object,?,?>> newMeRules = new HashSet<Rule<Object,?,?>>();
			for (Rule<Object,?,?> rule : rules)
				if (rule.isHookableInto(modelElement)) newMeRules.add(rule);
			if (newMeRules.isEmpty()) throw e;
			else {
				meHookableRules.indexAndCacheSet(modelElement, newMeRules);
				return newMeRules;
			}
	
		}
	}

}
