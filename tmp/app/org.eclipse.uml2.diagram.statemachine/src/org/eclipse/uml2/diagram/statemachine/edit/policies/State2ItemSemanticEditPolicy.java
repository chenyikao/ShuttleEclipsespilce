package org.eclipse.uml2.diagram.statemachine.edit.policies;

import java.util.Iterator;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateRelationshipRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.emf.type.core.requests.ReorientRelationshipRequest;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.diagram.statemachine.edit.commands.Region2CreateCommand;
import org.eclipse.uml2.diagram.statemachine.edit.commands.TransitionCreateCommand;
import org.eclipse.uml2.diagram.statemachine.edit.commands.TransitionReorientCommand;
import org.eclipse.uml2.diagram.statemachine.edit.parts.Behavior2EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.Behavior3EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.BehaviorEditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.Region2EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.StateCompositeState_InternalActivitiesEditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.TransitionEditPart;
import org.eclipse.uml2.diagram.statemachine.part.UMLVisualIDRegistry;
import org.eclipse.uml2.diagram.statemachine.providers.UMLElementTypes;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * @generated
 */
public class State2ItemSemanticEditPolicy extends UMLBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		if (UMLElementTypes.Region_3002 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(UMLPackage.eINSTANCE.getState_Region());
			}
			return getGEFWrapper(new Region2CreateCommand(req));
		}
		return super.getCreateCommand(req);
	}

	/**
	 * @generated
	 */
	protected Command getDestroyElementCommand(DestroyElementRequest req) {
		CompoundCommand cc = getDestroyEdgesCommand();
		addDestroyChildNodesCommand(cc);
		addDestroyShortcutsCommand(cc);
		cc.add(getGEFWrapper(new DestroyElementCommand(req)));
		return cc.unwrap();
	}

	/**
	 * @generated
	 */
	protected void addDestroyChildNodesCommand(CompoundCommand cmd) {
		View view = (View) getHost().getModel();
		EAnnotation annotation = view.getEAnnotation("Shortcut"); //$NON-NLS-1$
		if (annotation != null) {
			return;
		}
		for (Iterator it = view.getChildren().iterator(); it.hasNext();) {
			Node node = (Node) it.next();
			switch (UMLVisualIDRegistry.getVisualID(node)) {
			case Region2EditPart.VISUAL_ID:
				cmd.add(getDestroyElementCommand(node));
				break;
			case StateCompositeState_InternalActivitiesEditPart.VISUAL_ID:
				for (Iterator cit = node.getChildren().iterator(); cit.hasNext();) {
					Node cnode = (Node) cit.next();
					switch (UMLVisualIDRegistry.getVisualID(cnode)) {
					case BehaviorEditPart.VISUAL_ID:
						cmd.add(getDestroyElementCommand(cnode));
						break;
					case Behavior2EditPart.VISUAL_ID:
						cmd.add(getDestroyElementCommand(cnode));
						break;
					case Behavior3EditPart.VISUAL_ID:
						cmd.add(getDestroyElementCommand(cnode));
						break;
					}
				}
				break;
			}
		}
	}

	/**
	 * @generated
	 */
	protected Command getCreateRelationshipCommand(CreateRelationshipRequest req) {
		Command command = req.getTarget() == null ? getStartCreateRelationshipCommand(req) : getCompleteCreateRelationshipCommand(req);
		return command != null ? command : super.getCreateRelationshipCommand(req);
	}

	/**
	 * @generated
	 */
	protected Command getStartCreateRelationshipCommand(CreateRelationshipRequest req) {
		if (UMLElementTypes.Transition_4001 == req.getElementType()) {
			return getGEFWrapper(new TransitionCreateCommand(req, req.getSource(), req.getTarget()));
		}
		return null;
	}

	/**
	 * @generated
	 */
	protected Command getCompleteCreateRelationshipCommand(CreateRelationshipRequest req) {
		if (UMLElementTypes.Transition_4001 == req.getElementType()) {
			return getGEFWrapper(new TransitionCreateCommand(req, req.getSource(), req.getTarget()));
		}
		return null;
	}

	/**
	 * Returns command to reorient EClass based link. New link target or source
	 * should be the domain model element associated with this node.
	 * 
	 * @generated
	 */
	protected Command getReorientRelationshipCommand(ReorientRelationshipRequest req) {
		switch (getVisualID(req)) {
		case TransitionEditPart.VISUAL_ID:
			return getGEFWrapper(new TransitionReorientCommand(req));
		}
		return super.getReorientRelationshipCommand(req);
	}

}
