package org.eclipse.uml2.diagram.statemachine.providers;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.diagram.core.providers.AbstractViewProvider;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.type.core.IHintedType;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.diagram.statemachine.edit.parts.Behavior2EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.Behavior3EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.BehaviorEditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.ConnectionPointReference2EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.ConnectionPointReferenceEditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.ConnectionPointReferenceName2EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.ConnectionPointReferenceNameEditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.FinalStateEditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.Pseudostate10EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.Pseudostate2EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.Pseudostate3EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.Pseudostate4EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.Pseudostate5EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.Pseudostate6EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.Pseudostate7EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.Pseudostate8EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.Pseudostate9EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.PseudostateEditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.PseudostateName2EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.PseudostateNameEditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.Region2EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.RegionEditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.RegionSubvertices2EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.RegionSubverticesEditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.State2EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.State3EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.StateCompositeState_InternalActivities2EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.StateCompositeState_InternalActivitiesEditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.StateEditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.StateMachine2EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.StateMachineEditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.StateMachineNameEditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.StateName2EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.StateName3EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.StateNameEditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.StateSimpleState_InternalActivitiesEditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.TransitionEditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.TransitionNameEditPart;
import org.eclipse.uml2.diagram.statemachine.part.UMLVisualIDRegistry;
import org.eclipse.uml2.diagram.statemachine.view.factories.Behavior2ViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.Behavior3ViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.BehaviorViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.ConnectionPointReference2ViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.ConnectionPointReferenceName2ViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.ConnectionPointReferenceNameViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.ConnectionPointReferenceViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.FinalStateViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.Pseudostate10ViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.Pseudostate2ViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.Pseudostate3ViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.Pseudostate4ViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.Pseudostate5ViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.Pseudostate6ViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.Pseudostate7ViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.Pseudostate8ViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.Pseudostate9ViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.PseudostateName2ViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.PseudostateNameViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.PseudostateViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.Region2ViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.RegionSubvertices2ViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.RegionSubverticesViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.RegionViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.State2ViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.State3ViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.StateCompositeState_InternalActivities2ViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.StateCompositeState_InternalActivitiesViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.StateMachine2ViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.StateMachineNameViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.StateMachineViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.StateName2ViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.StateName3ViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.StateNameViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.StateSimpleState_InternalActivitiesViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.StateViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.TransitionNameViewFactory;
import org.eclipse.uml2.diagram.statemachine.view.factories.TransitionViewFactory;

/**
 * @generated
 */
public class UMLViewProvider extends AbstractViewProvider {

	/**
	 * @generated
	 */
	protected Class getDiagramViewClass(IAdaptable semanticAdapter, String diagramKind) {
		EObject semanticElement = getSemanticElement(semanticAdapter);
		if (StateMachineEditPart.MODEL_ID.equals(diagramKind) && UMLVisualIDRegistry.getDiagramVisualID(semanticElement) != -1) {
			return StateMachineViewFactory.class;
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected Class getNodeViewClass(IAdaptable semanticAdapter, View containerView, String semanticHint) {
		if (containerView == null) {
			return null;
		}
		IElementType elementType = getSemanticElementType(semanticAdapter);
		EObject domainElement = getSemanticElement(semanticAdapter);
		int visualID;
		if (semanticHint == null) {
			// Semantic hint is not specified. Can be a result of call from CanonicalEditPolicy.
			// In this situation there should be NO elementType, visualID will be determined
			// by VisualIDRegistry.getNodeVisualID() for domainElement.
			if (elementType != null || domainElement == null) {
				return null;
			}
			visualID = UMLVisualIDRegistry.getNodeVisualID(containerView, domainElement);
		} else {
			visualID = UMLVisualIDRegistry.getVisualID(semanticHint);
			if (elementType != null) {
				// Semantic hint is specified together with element type.
				// Both parameters should describe exactly the same diagram element.
				// In addition we check that visualID returned by VisualIDRegistry.getNodeVisualID() for
				// domainElement (if specified) is the same as in element type.
				if (!UMLElementTypes.isKnownElementType(elementType) || (!(elementType instanceof IHintedType))) {
					return null; // foreign element type
				}
				String elementTypeHint = ((IHintedType) elementType).getSemanticHint();
				if (!semanticHint.equals(elementTypeHint)) {
					return null; // if semantic hint is specified it should be the same as in element type
				}
				if (domainElement != null && visualID != UMLVisualIDRegistry.getNodeVisualID(containerView, domainElement)) {
					return null; // visual id for node EClass should match visual id from element type
				}
			} else {
				// Element type is not specified. Domain element should be present.
				// This method is called with EObjectAdapter as parameter from:
				//   - ViewService.createNode(View container, EObject eObject, String type, PreferencesHint preferencesHint) 
				//   - generated ViewFactory.decorateView() for parent element
				if (!StateMachineEditPart.MODEL_ID.equals(UMLVisualIDRegistry.getModelID(containerView))) {
					return null; // foreign diagram
				}
				switch (visualID) {
				case StateMachine2EditPart.VISUAL_ID:
				case RegionEditPart.VISUAL_ID:
				case StateEditPart.VISUAL_ID:
				case BehaviorEditPart.VISUAL_ID:
				case Behavior2EditPart.VISUAL_ID:
				case Behavior3EditPart.VISUAL_ID:
				case State2EditPart.VISUAL_ID:
				case Region2EditPart.VISUAL_ID:
				case State3EditPart.VISUAL_ID:
				case ConnectionPointReferenceEditPart.VISUAL_ID:
				case ConnectionPointReference2EditPart.VISUAL_ID:
				case FinalStateEditPart.VISUAL_ID:
				case PseudostateEditPart.VISUAL_ID:
				case Pseudostate2EditPart.VISUAL_ID:
				case Pseudostate3EditPart.VISUAL_ID:
				case Pseudostate4EditPart.VISUAL_ID:
				case Pseudostate5EditPart.VISUAL_ID:
				case Pseudostate6EditPart.VISUAL_ID:
				case Pseudostate7EditPart.VISUAL_ID:
				case Pseudostate8EditPart.VISUAL_ID:
				case Pseudostate9EditPart.VISUAL_ID:
				case Pseudostate10EditPart.VISUAL_ID:
					if (domainElement == null || visualID != UMLVisualIDRegistry.getNodeVisualID(containerView, domainElement)) {
						return null; // visual id in semantic hint should match visual id for domain element
					}
					break;
				case StateMachineNameEditPart.VISUAL_ID:
					if (StateMachine2EditPart.VISUAL_ID != UMLVisualIDRegistry.getVisualID(containerView) || containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case RegionSubverticesEditPart.VISUAL_ID:
					if (RegionEditPart.VISUAL_ID != UMLVisualIDRegistry.getVisualID(containerView) || containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case StateNameEditPart.VISUAL_ID:
				case StateSimpleState_InternalActivitiesEditPart.VISUAL_ID:
					if (StateEditPart.VISUAL_ID != UMLVisualIDRegistry.getVisualID(containerView) || containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case StateName3EditPart.VISUAL_ID:
				case StateCompositeState_InternalActivitiesEditPart.VISUAL_ID:
					if (State2EditPart.VISUAL_ID != UMLVisualIDRegistry.getVisualID(containerView) || containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case RegionSubvertices2EditPart.VISUAL_ID:
					if (Region2EditPart.VISUAL_ID != UMLVisualIDRegistry.getVisualID(containerView) || containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case StateName2EditPart.VISUAL_ID:
				case StateCompositeState_InternalActivities2EditPart.VISUAL_ID:
					if (State3EditPart.VISUAL_ID != UMLVisualIDRegistry.getVisualID(containerView) || containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case ConnectionPointReferenceNameEditPart.VISUAL_ID:
					if (ConnectionPointReferenceEditPart.VISUAL_ID != UMLVisualIDRegistry.getVisualID(containerView) || containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case ConnectionPointReferenceName2EditPart.VISUAL_ID:
					if (ConnectionPointReference2EditPart.VISUAL_ID != UMLVisualIDRegistry.getVisualID(containerView) || containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case PseudostateNameEditPart.VISUAL_ID:
					if (Pseudostate9EditPart.VISUAL_ID != UMLVisualIDRegistry.getVisualID(containerView) || containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case PseudostateName2EditPart.VISUAL_ID:
					if (Pseudostate10EditPart.VISUAL_ID != UMLVisualIDRegistry.getVisualID(containerView) || containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				case TransitionNameEditPart.VISUAL_ID:
					if (TransitionEditPart.VISUAL_ID != UMLVisualIDRegistry.getVisualID(containerView) || containerView.getElement() != domainElement) {
						return null; // wrong container
					}
					break;
				default:
					return null;
				}
			}
		}
		return getNodeViewClass(containerView, visualID);
	}

	/**
	 * @generated
	 */
	protected Class getNodeViewClass(View containerView, int visualID) {
		if (containerView == null || !UMLVisualIDRegistry.canCreateNode(containerView, visualID)) {
			return null;
		}
		switch (visualID) {
		case StateMachine2EditPart.VISUAL_ID:
			return StateMachine2ViewFactory.class;
		case StateMachineNameEditPart.VISUAL_ID:
			return StateMachineNameViewFactory.class;
		case RegionEditPart.VISUAL_ID:
			return RegionViewFactory.class;
		case StateEditPart.VISUAL_ID:
			return StateViewFactory.class;
		case StateNameEditPart.VISUAL_ID:
			return StateNameViewFactory.class;
		case BehaviorEditPart.VISUAL_ID:
			return BehaviorViewFactory.class;
		case Behavior2EditPart.VISUAL_ID:
			return Behavior2ViewFactory.class;
		case Behavior3EditPart.VISUAL_ID:
			return Behavior3ViewFactory.class;
		case State2EditPart.VISUAL_ID:
			return State2ViewFactory.class;
		case StateName3EditPart.VISUAL_ID:
			return StateName3ViewFactory.class;
		case Region2EditPart.VISUAL_ID:
			return Region2ViewFactory.class;
		case State3EditPart.VISUAL_ID:
			return State3ViewFactory.class;
		case StateName2EditPart.VISUAL_ID:
			return StateName2ViewFactory.class;
		case ConnectionPointReferenceEditPart.VISUAL_ID:
			return ConnectionPointReferenceViewFactory.class;
		case ConnectionPointReferenceNameEditPart.VISUAL_ID:
			return ConnectionPointReferenceNameViewFactory.class;
		case ConnectionPointReference2EditPart.VISUAL_ID:
			return ConnectionPointReference2ViewFactory.class;
		case ConnectionPointReferenceName2EditPart.VISUAL_ID:
			return ConnectionPointReferenceName2ViewFactory.class;
		case FinalStateEditPart.VISUAL_ID:
			return FinalStateViewFactory.class;
		case PseudostateEditPart.VISUAL_ID:
			return PseudostateViewFactory.class;
		case Pseudostate2EditPart.VISUAL_ID:
			return Pseudostate2ViewFactory.class;
		case Pseudostate3EditPart.VISUAL_ID:
			return Pseudostate3ViewFactory.class;
		case Pseudostate4EditPart.VISUAL_ID:
			return Pseudostate4ViewFactory.class;
		case Pseudostate5EditPart.VISUAL_ID:
			return Pseudostate5ViewFactory.class;
		case Pseudostate6EditPart.VISUAL_ID:
			return Pseudostate6ViewFactory.class;
		case Pseudostate7EditPart.VISUAL_ID:
			return Pseudostate7ViewFactory.class;
		case Pseudostate8EditPart.VISUAL_ID:
			return Pseudostate8ViewFactory.class;
		case Pseudostate9EditPart.VISUAL_ID:
			return Pseudostate9ViewFactory.class;
		case PseudostateNameEditPart.VISUAL_ID:
			return PseudostateNameViewFactory.class;
		case Pseudostate10EditPart.VISUAL_ID:
			return Pseudostate10ViewFactory.class;
		case PseudostateName2EditPart.VISUAL_ID:
			return PseudostateName2ViewFactory.class;
		case RegionSubverticesEditPart.VISUAL_ID:
			return RegionSubverticesViewFactory.class;
		case StateSimpleState_InternalActivitiesEditPart.VISUAL_ID:
			return StateSimpleState_InternalActivitiesViewFactory.class;
		case StateCompositeState_InternalActivitiesEditPart.VISUAL_ID:
			return StateCompositeState_InternalActivitiesViewFactory.class;
		case RegionSubvertices2EditPart.VISUAL_ID:
			return RegionSubvertices2ViewFactory.class;
		case StateCompositeState_InternalActivities2EditPart.VISUAL_ID:
			return StateCompositeState_InternalActivities2ViewFactory.class;
		case TransitionNameEditPart.VISUAL_ID:
			return TransitionNameViewFactory.class;
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected Class getEdgeViewClass(IAdaptable semanticAdapter, View containerView, String semanticHint) {
		IElementType elementType = getSemanticElementType(semanticAdapter);
		if (!UMLElementTypes.isKnownElementType(elementType) || (!(elementType instanceof IHintedType))) {
			return null; // foreign element type
		}
		String elementTypeHint = ((IHintedType) elementType).getSemanticHint();
		if (elementTypeHint == null) {
			return null; // our hint is visual id and must be specified
		}
		if (semanticHint != null && !semanticHint.equals(elementTypeHint)) {
			return null; // if semantic hint is specified it should be the same as in element type
		}
		int visualID = UMLVisualIDRegistry.getVisualID(elementTypeHint);
		EObject domainElement = getSemanticElement(semanticAdapter);
		if (domainElement != null && visualID != UMLVisualIDRegistry.getLinkWithClassVisualID(domainElement)) {
			return null; // visual id for link EClass should match visual id from element type
		}
		return getEdgeViewClass(visualID);
	}

	/**
	 * @generated
	 */
	protected Class getEdgeViewClass(int visualID) {
		switch (visualID) {
		case TransitionEditPart.VISUAL_ID:
			return TransitionViewFactory.class;
		}
		return null;
	}

	/**
	 * @generated
	 */
	private IElementType getSemanticElementType(IAdaptable semanticAdapter) {
		if (semanticAdapter == null) {
			return null;
		}
		return (IElementType) semanticAdapter.getAdapter(IElementType.class);
	}

}
