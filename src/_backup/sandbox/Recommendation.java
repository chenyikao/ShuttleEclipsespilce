/**
 * 
 */
package tw.edu.nccu.shuttle.sandbox;

import java.util.Collection;

/**
 * @author Kao, Chen-yi
 *
 */
public class Recommendation {

//	private RecommendationCreationTool vMPMECreator;

	/**
	 * TODO: use Creation Tool (*creat*tool), 
	 * Creation Policy (*creat*edit*polic*), 
	 * View Provider, 
	 * Create-element Request, 
	 * Creation Command, 
	 * org.eclipse.gef.editparts.AbstractEditPart.createChild(), 
	 * org.eclipse.gmf.runtime.diagram.core.providers.IViewProvider.createEdge/createNode or
	 * trace the source code corresponding to the Sequence diagram?
	 * .1) generate corresponding GMF semantic model (SM) to the V-MPMEs for
	 * possible application (yes! one approved to apply) in the future;
	 * (to avoid possible temporary SM constraint violation, generate 
	 * an INDEPENDENT sub-model of SM)
	 * 	- use clone(): what's to be cloned if in an empty diagram?
	 * .2) generate corresponding EditParts to the V-MPMEs;
	 * .3) generate corresponding Notations Views to the EditParts.
	 * 	- use Create-element Request: CreateElementRequest -> View? 
	 * 	- use Creation Command: Command -> View? 
	 * 
	 * TODO: let ShuttleDiagramEditor subclass CreationTool and handle mouseHover() 
	 * - can it handle hover-in and hover-out events?
	 * TODO: V-MPMEs SHOULD NOT be saved.
	 * By setting model elements to transcient? manipulating IFigure ONLY? 
	 * Or intercepting SaveOperation? 
	 * 
	 * @param Anchor MP ME Selections
	 */
	public Recommendation(Collection<GmfMPME> anchorMPMEs) {
		// TODO: for temporary demo. Mono-type Creation Tool and V-MPMEs assumption.
		// copied from {@link CreationTool#performCreation(int button)}
//		EditPartViewer viewer = getCurrentViewer();
//		Command c = getCurrentCommand();
//		executeCurrentCommand();
//
//		// copied from {@link CreationTool#selectAddedObject(EditPartViewer viewer, Collection objects)}
//		final List editparts = new ArrayList();
//		for (Iterator i = objects.iterator(); i.hasNext();) {
//			Object object = i.next();
//			if (object instanceof IAdaptable) {
//				Object editPart =
//					viewer.getEditPartRegistry().get(
//						((IAdaptable)object).getAdapter(View.class));
//				if (editPart != null)
//					editparts.add(editPart);
//			}
//		}

//		GmfMPME recommME = anchorMPMEs.iterator().next();
//		IGraphicalEditPart recommMEGEP = recommME.getGraphicalEditPart();
//		CreationEditPolicy recommCEP = new CreationEditPolicy();
//		recommCEP.setHost(recommMEGEP.getParent());
//		Command recommCC = recommCEP.getCommand(new CreateViewRequest(
//				new CreateViewRequest.ViewDescriptor(
//						recommMEGEP,
//						recommME.getType().getEClass().getInstanceClass(),
//						false,
//						recommMEGEP.getDiagramPreferencesHint())));
//		recommCC.execute();

		//		vMPMECreator = new RecommendationCreationTool();
	}

	public void show() {
//		vMPMECreator.create();
	}

}
