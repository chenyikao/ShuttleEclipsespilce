package org.eclipse.uml2.diagram.statemachine.edit.policies;

import java.util.Iterator;

import org.eclipse.emf.ecore.EAnnotation;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.DestroyElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.DestroyElementRequest;
import org.eclipse.gmf.runtime.notation.Node;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.diagram.statemachine.edit.parts.FinalStateEditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.Pseudostate2EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.Pseudostate3EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.Pseudostate4EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.Pseudostate5EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.Pseudostate6EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.Pseudostate7EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.Pseudostate8EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.PseudostateEditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.RegionSubverticesEditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.State2EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.State3EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.StateEditPart;
import org.eclipse.uml2.diagram.statemachine.part.UMLVisualIDRegistry;

/**
 * @generated
 */
public class RegionItemSemanticEditPolicy extends UMLBaseItemSemanticEditPolicy {

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
			case RegionSubverticesEditPart.VISUAL_ID:
				for (Iterator cit = node.getChildren().iterator(); cit.hasNext();) {
					Node cnode = (Node) cit.next();
					switch (UMLVisualIDRegistry.getVisualID(cnode)) {
					case StateEditPart.VISUAL_ID:
						cmd.add(getDestroyElementCommand(cnode));
						break;
					case State2EditPart.VISUAL_ID:
						cmd.add(getDestroyElementCommand(cnode));
						break;
					case State3EditPart.VISUAL_ID:
						cmd.add(getDestroyElementCommand(cnode));
						break;
					case FinalStateEditPart.VISUAL_ID:
						cmd.add(getDestroyElementCommand(cnode));
						break;
					case PseudostateEditPart.VISUAL_ID:
						cmd.add(getDestroyElementCommand(cnode));
						break;
					case Pseudostate2EditPart.VISUAL_ID:
						cmd.add(getDestroyElementCommand(cnode));
						break;
					case Pseudostate3EditPart.VISUAL_ID:
						cmd.add(getDestroyElementCommand(cnode));
						break;
					case Pseudostate4EditPart.VISUAL_ID:
						cmd.add(getDestroyElementCommand(cnode));
						break;
					case Pseudostate5EditPart.VISUAL_ID:
						cmd.add(getDestroyElementCommand(cnode));
						break;
					case Pseudostate6EditPart.VISUAL_ID:
						cmd.add(getDestroyElementCommand(cnode));
						break;
					case Pseudostate7EditPart.VISUAL_ID:
						cmd.add(getDestroyElementCommand(cnode));
						break;
					case Pseudostate8EditPart.VISUAL_ID:
						cmd.add(getDestroyElementCommand(cnode));
						break;
					}
				}
				break;
			}
		}
	}

}
