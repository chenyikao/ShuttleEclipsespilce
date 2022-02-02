package org.eclipse.uml2.diagram.statemachine.edit.policies;

import org.eclipse.gef.commands.Command;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.uml2.diagram.statemachine.edit.commands.Behavior2CreateCommand;
import org.eclipse.uml2.diagram.statemachine.edit.commands.Behavior3CreateCommand;
import org.eclipse.uml2.diagram.statemachine.edit.commands.BehaviorCreateCommand;
import org.eclipse.uml2.diagram.statemachine.providers.UMLElementTypes;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * @generated
 */
public class StateCompositeState_InternalActivitiesItemSemanticEditPolicy extends UMLBaseItemSemanticEditPolicy {

	/**
	 * @generated
	 */
	protected Command getCreateCommand(CreateElementRequest req) {
		if (UMLElementTypes.Behavior_3019 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(UMLPackage.eINSTANCE.getState_Entry());
			}
			return getGEFWrapper(new BehaviorCreateCommand(req));
		}
		if (UMLElementTypes.Behavior_3020 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(UMLPackage.eINSTANCE.getState_Exit());
			}
			return getGEFWrapper(new Behavior2CreateCommand(req));
		}
		if (UMLElementTypes.Behavior_3021 == req.getElementType()) {
			if (req.getContainmentFeature() == null) {
				req.setContainmentFeature(UMLPackage.eINSTANCE.getState_DoActivity());
			}
			return getGEFWrapper(new Behavior3CreateCommand(req));
		}
		return super.getCreateCommand(req);
	}

}
