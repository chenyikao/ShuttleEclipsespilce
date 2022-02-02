package org.eclipse.uml2.diagram.statemachine.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.uml2.diagram.statemachine.edit.commands.FinalStateCreateCommand;
import org.eclipse.uml2.diagram.statemachine.edit.commands.Pseudostate2CreateCommand;
import org.eclipse.uml2.diagram.statemachine.edit.commands.Pseudostate3CreateCommand;
import org.eclipse.uml2.diagram.statemachine.edit.commands.Pseudostate4CreateCommand;
import org.eclipse.uml2.diagram.statemachine.edit.commands.Pseudostate5CreateCommand;
import org.eclipse.uml2.diagram.statemachine.edit.commands.Pseudostate6CreateCommand;
import org.eclipse.uml2.diagram.statemachine.edit.commands.Pseudostate7CreateCommand;
import org.eclipse.uml2.diagram.statemachine.edit.commands.Pseudostate8CreateCommand;
import org.eclipse.uml2.diagram.statemachine.edit.commands.PseudostateCreateCommand;
import org.eclipse.uml2.diagram.statemachine.edit.commands.State2CreateCommand;
import org.eclipse.uml2.diagram.statemachine.edit.commands.State3CreateCommand;
import org.eclipse.uml2.diagram.statemachine.edit.commands.StateCreateCommand;
import org.eclipse.uml2.diagram.statemachine.providers.UMLElementTypes;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * @generated
 */
public class RegionSubverticesItemSemanticEditPolicy extends UMLBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		if (UMLElementTypes.State_3001 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(UMLPackage.eINSTANCE.getRegion_Subvertex());
			}
			return getGEFWrapper(new StateCreateCommand(req));
		}
		if (UMLElementTypes.State_3012 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(UMLPackage.eINSTANCE.getRegion_Subvertex());
			}
			return getGEFWrapper(new State2CreateCommand(req));
		}
		if (UMLElementTypes.State_3016 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(UMLPackage.eINSTANCE.getRegion_Subvertex());
			}
			return getGEFWrapper(new State3CreateCommand(req));
		}
		if (UMLElementTypes.FinalState_3003 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(UMLPackage.eINSTANCE.getRegion_Subvertex());
			}
			return getGEFWrapper(new FinalStateCreateCommand(req));
		}
		if (UMLElementTypes.Pseudostate_3004 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(UMLPackage.eINSTANCE.getRegion_Subvertex());
			}
			return getGEFWrapper(new PseudostateCreateCommand(req));
		}
		if (UMLElementTypes.Pseudostate_3005 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(UMLPackage.eINSTANCE.getRegion_Subvertex());
			}
			return getGEFWrapper(new Pseudostate2CreateCommand(req));
		}
		if (UMLElementTypes.Pseudostate_3006 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(UMLPackage.eINSTANCE.getRegion_Subvertex());
			}
			return getGEFWrapper(new Pseudostate3CreateCommand(req));
		}
		if (UMLElementTypes.Pseudostate_3007 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(UMLPackage.eINSTANCE.getRegion_Subvertex());
			}
			return getGEFWrapper(new Pseudostate4CreateCommand(req));
		}
		if (UMLElementTypes.Pseudostate_3008 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(UMLPackage.eINSTANCE.getRegion_Subvertex());
			}
			return getGEFWrapper(new Pseudostate5CreateCommand(req));
		}
		if (UMLElementTypes.Pseudostate_3009 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(UMLPackage.eINSTANCE.getRegion_Subvertex());
			}
			return getGEFWrapper(new Pseudostate6CreateCommand(req));
		}
		if (UMLElementTypes.Pseudostate_3010 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(UMLPackage.eINSTANCE.getRegion_Subvertex());
			}
			return getGEFWrapper(new Pseudostate7CreateCommand(req));
		}
		if (UMLElementTypes.Pseudostate_3011 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(UMLPackage.eINSTANCE.getRegion_Subvertex());
			}
			return getGEFWrapper(new Pseudostate8CreateCommand(req));
		}
		return super.getCreateCommand(req);
	}

}
