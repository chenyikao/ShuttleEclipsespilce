package org.eclipse.uml2.diagram.statemachine.part;

import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.diagram.statemachine.edit.parts.Behavior2EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.Behavior3EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.BehaviorEditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.ConnectionPointReference2EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.ConnectionPointReferenceEditPart;
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
import org.eclipse.uml2.diagram.statemachine.edit.parts.StateSimpleState_InternalActivitiesEditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.TransitionEditPart;
import org.eclipse.uml2.diagram.statemachine.providers.UMLElementTypes;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.ConnectionPointReference;
import org.eclipse.uml2.uml.FinalState;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.Vertex;

/**
 * @generated
 */
@SuppressWarnings("unchecked")
public class UMLDiagramUpdater {

	/**
	 * @generated
	 */
	public static List getSemanticChildren(View view) {
		switch (UMLVisualIDRegistry.getVisualID(view)) {
		case StateMachine2EditPart.VISUAL_ID:
			return getStateMachine_2004SemanticChildren(view);
		case State2EditPart.VISUAL_ID:
			return getState_3012SemanticChildren(view);
		case State3EditPart.VISUAL_ID:
			return getState_3016SemanticChildren(view);
		case RegionSubverticesEditPart.VISUAL_ID:
			return getRegionSubvertices_7004SemanticChildren(view);
		case StateSimpleState_InternalActivitiesEditPart.VISUAL_ID:
			return getStateSimpleState_InternalActivities_7005SemanticChildren(view);
		case StateCompositeState_InternalActivitiesEditPart.VISUAL_ID:
			return getStateCompositeState_InternalActivities_7006SemanticChildren(view);
		case RegionSubvertices2EditPart.VISUAL_ID:
			return getRegionSubvertices_7003SemanticChildren(view);
		case StateCompositeState_InternalActivities2EditPart.VISUAL_ID:
			return getStateCompositeState_InternalActivities_7007SemanticChildren(view);
		case StateMachineEditPart.VISUAL_ID: {
			//We have "dummy" TopLevelNode (with vid = org.eclipse.uml2.diagram.statemachine.edit.parts.StateMachine2EditPart.VISUAL_ID). 
			//The only purpose for this node is to be a container for children (imports, etc)
			//of the "main" diagram figure (that one shown as Canvas).
			//Also we have modified the VisualIDRegistry#getNodeVisualID() to return
			//VID = org.eclipse.uml2.diagram.statemachine.edit.parts.StateMachine2EditPart.VISUAL_ID, 
			//for the case when top-level view is created for the same semantic element as the canvas view.

			List resultAndHeader = new LinkedList();
			resultAndHeader.add(new UMLNodeDescriptor(view.getElement(), StateMachine2EditPart.VISUAL_ID));
			resultAndHeader.addAll(getStateMachine_1000SemanticChildren(view));
			return resultAndHeader;
		}
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getStateMachine_2004SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		StateMachine modelElement = (StateMachine) view.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getRegions().iterator(); it.hasNext();) {
			Region childElement = (Region) it.next();
			int visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == RegionEditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator it = modelElement.getConnectionPoints().iterator(); it.hasNext();) {
			Pseudostate childElement = (Pseudostate) it.next();
			int visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == Pseudostate9EditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == Pseudostate10EditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getState_3012SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		State modelElement = (State) view.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getRegions().iterator(); it.hasNext();) {
			Region childElement = (Region) it.next();
			int visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == Region2EditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getState_3016SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		State modelElement = (State) view.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getRegions().iterator(); it.hasNext();) {
			Region childElement = (Region) it.next();
			int visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == Region2EditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		for (Iterator it = modelElement.getConnections().iterator(); it.hasNext();) {
			ConnectionPointReference childElement = (ConnectionPointReference) it.next();
			int visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == ConnectionPointReferenceEditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == ConnectionPointReference2EditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getRegionSubvertices_7004SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.EMPTY_LIST;
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		Region modelElement = (Region) containerView.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getSubvertices().iterator(); it.hasNext();) {
			Vertex childElement = (Vertex) it.next();
			int visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == StateEditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == State2EditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == State3EditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == FinalStateEditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == PseudostateEditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == Pseudostate2EditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == Pseudostate3EditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == Pseudostate4EditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == Pseudostate5EditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == Pseudostate6EditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == Pseudostate7EditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == Pseudostate8EditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getStateSimpleState_InternalActivities_7005SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.EMPTY_LIST;
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		State modelElement = (State) containerView.getElement();
		List result = new LinkedList();
		{
			Behavior childElement = modelElement.getEntry();
			int visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == BehaviorEditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		{
			Behavior childElement = modelElement.getExit();
			int visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == Behavior2EditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		{
			Behavior childElement = modelElement.getDoActivity();
			int visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == Behavior3EditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getStateCompositeState_InternalActivities_7006SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.EMPTY_LIST;
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		State modelElement = (State) containerView.getElement();
		List result = new LinkedList();
		{
			Behavior childElement = modelElement.getEntry();
			int visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == BehaviorEditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		{
			Behavior childElement = modelElement.getExit();
			int visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == Behavior2EditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		{
			Behavior childElement = modelElement.getDoActivity();
			int visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == Behavior3EditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getRegionSubvertices_7003SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.EMPTY_LIST;
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		Region modelElement = (Region) containerView.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getSubvertices().iterator(); it.hasNext();) {
			Vertex childElement = (Vertex) it.next();
			int visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == StateEditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == State2EditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == State3EditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == FinalStateEditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == PseudostateEditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == Pseudostate2EditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == Pseudostate3EditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == Pseudostate4EditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == Pseudostate5EditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == Pseudostate6EditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == Pseudostate7EditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
			if (visualID == Pseudostate8EditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
				continue;
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getStateCompositeState_InternalActivities_7007SemanticChildren(View view) {
		if (false == view.eContainer() instanceof View) {
			return Collections.EMPTY_LIST;
		}
		View containerView = (View) view.eContainer();
		if (!containerView.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		State modelElement = (State) containerView.getElement();
		List result = new LinkedList();
		{
			Behavior childElement = modelElement.getEntry();
			int visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == BehaviorEditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		{
			Behavior childElement = modelElement.getExit();
			int visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == Behavior2EditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		{
			Behavior childElement = modelElement.getDoActivity();
			int visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
			if (visualID == Behavior3EditPart.VISUAL_ID) {
				result.add(new UMLNodeDescriptor(childElement, visualID));
			}
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getStateMachine_1000SemanticChildren(View view) {
		if (!view.isSetElement()) {
			return Collections.EMPTY_LIST;
		}
		StateMachine modelElement = (StateMachine) view.getElement();
		List result = new LinkedList();
		for (Iterator it = modelElement.getNestedClassifiers().iterator(); it.hasNext();) {
			Classifier childElement = (Classifier) it.next();
			int visualID = UMLVisualIDRegistry.getNodeVisualID(view, childElement);
		}
		return result;
	}

	/**
	 * @generated
	 */
	public static List getContainedLinks(View view) {
		switch (UMLVisualIDRegistry.getVisualID(view)) {
		case StateMachineEditPart.VISUAL_ID:
			return getStateMachine_1000ContainedLinks(view);
		case StateMachine2EditPart.VISUAL_ID:
			return getStateMachine_2004ContainedLinks(view);
		case RegionEditPart.VISUAL_ID:
			return getRegion_3013ContainedLinks(view);
		case StateEditPart.VISUAL_ID:
			return getState_3001ContainedLinks(view);
		case BehaviorEditPart.VISUAL_ID:
			return getBehavior_3019ContainedLinks(view);
		case Behavior2EditPart.VISUAL_ID:
			return getBehavior_3020ContainedLinks(view);
		case Behavior3EditPart.VISUAL_ID:
			return getBehavior_3021ContainedLinks(view);
		case State2EditPart.VISUAL_ID:
			return getState_3012ContainedLinks(view);
		case Region2EditPart.VISUAL_ID:
			return getRegion_3002ContainedLinks(view);
		case State3EditPart.VISUAL_ID:
			return getState_3016ContainedLinks(view);
		case ConnectionPointReferenceEditPart.VISUAL_ID:
			return getConnectionPointReference_3017ContainedLinks(view);
		case ConnectionPointReference2EditPart.VISUAL_ID:
			return getConnectionPointReference_3018ContainedLinks(view);
		case FinalStateEditPart.VISUAL_ID:
			return getFinalState_3003ContainedLinks(view);
		case PseudostateEditPart.VISUAL_ID:
			return getPseudostate_3004ContainedLinks(view);
		case Pseudostate2EditPart.VISUAL_ID:
			return getPseudostate_3005ContainedLinks(view);
		case Pseudostate3EditPart.VISUAL_ID:
			return getPseudostate_3006ContainedLinks(view);
		case Pseudostate4EditPart.VISUAL_ID:
			return getPseudostate_3007ContainedLinks(view);
		case Pseudostate5EditPart.VISUAL_ID:
			return getPseudostate_3008ContainedLinks(view);
		case Pseudostate6EditPart.VISUAL_ID:
			return getPseudostate_3009ContainedLinks(view);
		case Pseudostate7EditPart.VISUAL_ID:
			return getPseudostate_3010ContainedLinks(view);
		case Pseudostate8EditPart.VISUAL_ID:
			return getPseudostate_3011ContainedLinks(view);
		case Pseudostate9EditPart.VISUAL_ID:
			return getPseudostate_3014ContainedLinks(view);
		case Pseudostate10EditPart.VISUAL_ID:
			return getPseudostate_3015ContainedLinks(view);
		case TransitionEditPart.VISUAL_ID:
			return getTransition_4001ContainedLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getIncomingLinks(View view) {
		switch (UMLVisualIDRegistry.getVisualID(view)) {
		case StateMachine2EditPart.VISUAL_ID:
			return getStateMachine_2004IncomingLinks(view);
		case RegionEditPart.VISUAL_ID:
			return getRegion_3013IncomingLinks(view);
		case StateEditPart.VISUAL_ID:
			return getState_3001IncomingLinks(view);
		case BehaviorEditPart.VISUAL_ID:
			return getBehavior_3019IncomingLinks(view);
		case Behavior2EditPart.VISUAL_ID:
			return getBehavior_3020IncomingLinks(view);
		case Behavior3EditPart.VISUAL_ID:
			return getBehavior_3021IncomingLinks(view);
		case State2EditPart.VISUAL_ID:
			return getState_3012IncomingLinks(view);
		case Region2EditPart.VISUAL_ID:
			return getRegion_3002IncomingLinks(view);
		case State3EditPart.VISUAL_ID:
			return getState_3016IncomingLinks(view);
		case ConnectionPointReferenceEditPart.VISUAL_ID:
			return getConnectionPointReference_3017IncomingLinks(view);
		case ConnectionPointReference2EditPart.VISUAL_ID:
			return getConnectionPointReference_3018IncomingLinks(view);
		case FinalStateEditPart.VISUAL_ID:
			return getFinalState_3003IncomingLinks(view);
		case PseudostateEditPart.VISUAL_ID:
			return getPseudostate_3004IncomingLinks(view);
		case Pseudostate2EditPart.VISUAL_ID:
			return getPseudostate_3005IncomingLinks(view);
		case Pseudostate3EditPart.VISUAL_ID:
			return getPseudostate_3006IncomingLinks(view);
		case Pseudostate4EditPart.VISUAL_ID:
			return getPseudostate_3007IncomingLinks(view);
		case Pseudostate5EditPart.VISUAL_ID:
			return getPseudostate_3008IncomingLinks(view);
		case Pseudostate6EditPart.VISUAL_ID:
			return getPseudostate_3009IncomingLinks(view);
		case Pseudostate7EditPart.VISUAL_ID:
			return getPseudostate_3010IncomingLinks(view);
		case Pseudostate8EditPart.VISUAL_ID:
			return getPseudostate_3011IncomingLinks(view);
		case Pseudostate9EditPart.VISUAL_ID:
			return getPseudostate_3014IncomingLinks(view);
		case Pseudostate10EditPart.VISUAL_ID:
			return getPseudostate_3015IncomingLinks(view);
		case TransitionEditPart.VISUAL_ID:
			return getTransition_4001IncomingLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getOutgoingLinks(View view) {
		switch (UMLVisualIDRegistry.getVisualID(view)) {
		case StateMachine2EditPart.VISUAL_ID:
			return getStateMachine_2004OutgoingLinks(view);
		case RegionEditPart.VISUAL_ID:
			return getRegion_3013OutgoingLinks(view);
		case StateEditPart.VISUAL_ID:
			return getState_3001OutgoingLinks(view);
		case BehaviorEditPart.VISUAL_ID:
			return getBehavior_3019OutgoingLinks(view);
		case Behavior2EditPart.VISUAL_ID:
			return getBehavior_3020OutgoingLinks(view);
		case Behavior3EditPart.VISUAL_ID:
			return getBehavior_3021OutgoingLinks(view);
		case State2EditPart.VISUAL_ID:
			return getState_3012OutgoingLinks(view);
		case Region2EditPart.VISUAL_ID:
			return getRegion_3002OutgoingLinks(view);
		case State3EditPart.VISUAL_ID:
			return getState_3016OutgoingLinks(view);
		case ConnectionPointReferenceEditPart.VISUAL_ID:
			return getConnectionPointReference_3017OutgoingLinks(view);
		case ConnectionPointReference2EditPart.VISUAL_ID:
			return getConnectionPointReference_3018OutgoingLinks(view);
		case FinalStateEditPart.VISUAL_ID:
			return getFinalState_3003OutgoingLinks(view);
		case PseudostateEditPart.VISUAL_ID:
			return getPseudostate_3004OutgoingLinks(view);
		case Pseudostate2EditPart.VISUAL_ID:
			return getPseudostate_3005OutgoingLinks(view);
		case Pseudostate3EditPart.VISUAL_ID:
			return getPseudostate_3006OutgoingLinks(view);
		case Pseudostate4EditPart.VISUAL_ID:
			return getPseudostate_3007OutgoingLinks(view);
		case Pseudostate5EditPart.VISUAL_ID:
			return getPseudostate_3008OutgoingLinks(view);
		case Pseudostate6EditPart.VISUAL_ID:
			return getPseudostate_3009OutgoingLinks(view);
		case Pseudostate7EditPart.VISUAL_ID:
			return getPseudostate_3010OutgoingLinks(view);
		case Pseudostate8EditPart.VISUAL_ID:
			return getPseudostate_3011OutgoingLinks(view);
		case Pseudostate9EditPart.VISUAL_ID:
			return getPseudostate_3014OutgoingLinks(view);
		case Pseudostate10EditPart.VISUAL_ID:
			return getPseudostate_3015OutgoingLinks(view);
		case TransitionEditPart.VISUAL_ID:
			return getTransition_4001OutgoingLinks(view);
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getStateMachine_1000ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getStateMachine_2004ContainedLinks(View view) {
		//no links to, from and inside the diagram header
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getRegion_3013ContainedLinks(View view) {
		Region modelElement = (Region) view.getElement();
		List result = new LinkedList();
		result.addAll(getContainedTypeModelFacetLinks_Transition_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getState_3001ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getBehavior_3019ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getBehavior_3020ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getBehavior_3021ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getState_3012ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getRegion_3002ContainedLinks(View view) {
		Region modelElement = (Region) view.getElement();
		List result = new LinkedList();
		result.addAll(getContainedTypeModelFacetLinks_Transition_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getState_3016ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getConnectionPointReference_3017ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getConnectionPointReference_3018ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getFinalState_3003ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPseudostate_3004ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPseudostate_3005ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPseudostate_3006ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPseudostate_3007ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPseudostate_3008ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPseudostate_3009ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPseudostate_3010ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPseudostate_3011ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPseudostate_3014ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getPseudostate_3015ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getTransition_4001ContainedLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getStateMachine_2004IncomingLinks(View view) {
		//no links to, from and inside the diagram header
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getRegion_3013IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getState_3001IncomingLinks(View view) {
		State modelElement = (State) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Transition_4001(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getBehavior_3019IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getBehavior_3020IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getBehavior_3021IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getState_3012IncomingLinks(View view) {
		State modelElement = (State) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Transition_4001(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getRegion_3002IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getState_3016IncomingLinks(View view) {
		State modelElement = (State) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Transition_4001(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getConnectionPointReference_3017IncomingLinks(View view) {
		ConnectionPointReference modelElement = (ConnectionPointReference) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Transition_4001(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getConnectionPointReference_3018IncomingLinks(View view) {
		ConnectionPointReference modelElement = (ConnectionPointReference) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Transition_4001(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getFinalState_3003IncomingLinks(View view) {
		FinalState modelElement = (FinalState) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Transition_4001(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPseudostate_3004IncomingLinks(View view) {
		Pseudostate modelElement = (Pseudostate) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Transition_4001(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPseudostate_3005IncomingLinks(View view) {
		Pseudostate modelElement = (Pseudostate) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Transition_4001(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPseudostate_3006IncomingLinks(View view) {
		Pseudostate modelElement = (Pseudostate) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Transition_4001(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPseudostate_3007IncomingLinks(View view) {
		Pseudostate modelElement = (Pseudostate) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Transition_4001(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPseudostate_3008IncomingLinks(View view) {
		Pseudostate modelElement = (Pseudostate) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Transition_4001(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPseudostate_3009IncomingLinks(View view) {
		Pseudostate modelElement = (Pseudostate) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Transition_4001(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPseudostate_3010IncomingLinks(View view) {
		Pseudostate modelElement = (Pseudostate) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Transition_4001(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPseudostate_3011IncomingLinks(View view) {
		Pseudostate modelElement = (Pseudostate) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Transition_4001(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPseudostate_3014IncomingLinks(View view) {
		Pseudostate modelElement = (Pseudostate) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Transition_4001(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPseudostate_3015IncomingLinks(View view) {
		Pseudostate modelElement = (Pseudostate) view.getElement();
		Map crossReferences = EcoreUtil.CrossReferencer.find(view.eResource().getResourceSet().getResources());
		List result = new LinkedList();
		result.addAll(getIncomingTypeModelFacetLinks_Transition_4001(modelElement, crossReferences));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getTransition_4001IncomingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getStateMachine_2004OutgoingLinks(View view) {
		//no links to, from and inside the diagram header
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getRegion_3013OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getState_3001OutgoingLinks(View view) {
		State modelElement = (State) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_Transition_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getBehavior_3019OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getBehavior_3020OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getBehavior_3021OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getState_3012OutgoingLinks(View view) {
		State modelElement = (State) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_Transition_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getRegion_3002OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public static List getState_3016OutgoingLinks(View view) {
		State modelElement = (State) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_Transition_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getConnectionPointReference_3017OutgoingLinks(View view) {
		ConnectionPointReference modelElement = (ConnectionPointReference) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_Transition_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getConnectionPointReference_3018OutgoingLinks(View view) {
		ConnectionPointReference modelElement = (ConnectionPointReference) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_Transition_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getFinalState_3003OutgoingLinks(View view) {
		FinalState modelElement = (FinalState) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_Transition_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPseudostate_3004OutgoingLinks(View view) {
		Pseudostate modelElement = (Pseudostate) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_Transition_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPseudostate_3005OutgoingLinks(View view) {
		Pseudostate modelElement = (Pseudostate) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_Transition_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPseudostate_3006OutgoingLinks(View view) {
		Pseudostate modelElement = (Pseudostate) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_Transition_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPseudostate_3007OutgoingLinks(View view) {
		Pseudostate modelElement = (Pseudostate) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_Transition_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPseudostate_3008OutgoingLinks(View view) {
		Pseudostate modelElement = (Pseudostate) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_Transition_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPseudostate_3009OutgoingLinks(View view) {
		Pseudostate modelElement = (Pseudostate) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_Transition_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPseudostate_3010OutgoingLinks(View view) {
		Pseudostate modelElement = (Pseudostate) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_Transition_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPseudostate_3011OutgoingLinks(View view) {
		Pseudostate modelElement = (Pseudostate) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_Transition_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPseudostate_3014OutgoingLinks(View view) {
		Pseudostate modelElement = (Pseudostate) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_Transition_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getPseudostate_3015OutgoingLinks(View view) {
		Pseudostate modelElement = (Pseudostate) view.getElement();
		List result = new LinkedList();
		result.addAll(getOutgoingTypeModelFacetLinks_Transition_4001(modelElement));
		return result;
	}

	/**
	 * @generated
	 */
	public static List getTransition_4001OutgoingLinks(View view) {
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	private static Collection getContainedTypeModelFacetLinks_Transition_4001(Region container) {
		Collection result = new LinkedList();
		for (Iterator links = container.getTransitions().iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Transition) {
				continue;
			}
			Transition link = (Transition) linkObject;
			if (TransitionEditPart.VISUAL_ID != UMLVisualIDRegistry.getLinkWithClassVisualID(link)) {
				continue;
			}
			Vertex dst = link.getTarget();
			Vertex src = link.getSource();
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Transition_4001, TransitionEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getIncomingTypeModelFacetLinks_Transition_4001(Vertex target, Map crossReferences) {
		Collection result = new LinkedList();
		Collection settings = (Collection) crossReferences.get(target);
		for (Iterator it = settings.iterator(); it.hasNext();) {
			EStructuralFeature.Setting setting = (EStructuralFeature.Setting) it.next();
			if (setting.getEStructuralFeature() != UMLPackage.eINSTANCE.getTransition_Target() || false == setting.getEObject() instanceof Transition) {
				continue;
			}
			Transition link = (Transition) setting.getEObject();
			if (TransitionEditPart.VISUAL_ID != UMLVisualIDRegistry.getLinkWithClassVisualID(link)) {
				continue;
			}
			Vertex src = link.getSource();
			result.add(new UMLLinkDescriptor(src, target, link, UMLElementTypes.Transition_4001, TransitionEditPart.VISUAL_ID));
		}
		return result;
	}

	/**
	 * @generated
	 */
	private static Collection getOutgoingTypeModelFacetLinks_Transition_4001(Vertex source) {
		Region container = null;
		// Find container element for the link.
		// Climb up by containment hierarchy starting from the source
		// and return the first element that is instance of the container class.
		for (EObject element = source; element != null && container == null; element = element.eContainer()) {
			if (element instanceof Region) {
				container = (Region) element;
			}
		}
		if (container == null) {
			return Collections.EMPTY_LIST;
		}
		Collection result = new LinkedList();
		for (Iterator links = container.getTransitions().iterator(); links.hasNext();) {
			EObject linkObject = (EObject) links.next();
			if (false == linkObject instanceof Transition) {
				continue;
			}
			Transition link = (Transition) linkObject;
			if (TransitionEditPart.VISUAL_ID != UMLVisualIDRegistry.getLinkWithClassVisualID(link)) {
				continue;
			}
			Vertex dst = link.getTarget();
			Vertex src = link.getSource();
			if (src != source) {
				continue;
			}
			result.add(new UMLLinkDescriptor(src, dst, link, UMLElementTypes.Transition_4001, TransitionEditPart.VISUAL_ID));
		}
		return result;
	}

}
