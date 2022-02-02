package org.eclipse.uml2.diagram.statemachine.edit.commands;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.diagram.statemachine.providers.UMLElementTypes;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * @generated
 */
public class Pseudostate10CreateCommand extends CreateElementCommand {

	/**
	 * @generated
	 */
	public Pseudostate10CreateCommand(CreateElementRequest req) {
		super(req);
	}

	/**
	 * @generated
	 */
	protected EObject getElementToEdit() {
		EObject container = ((CreateElementRequest) getRequest()).getContainer();
		if (container instanceof View) {
			container = ((View) container).getElement();
		}
		return container;
	}

	/**
	 * @generated
	 */
	protected EClass getEClassToEdit() {
		return UMLPackage.eINSTANCE.getStateMachine();
	}

	/**
	 * @generated
	 */
	protected EObject doDefaultElementCreation() {
		Pseudostate newElement = UMLFactory.eINSTANCE.createPseudostate();

		StateMachine owner = (StateMachine) getElementToEdit();
		owner.getConnectionPoints().add(newElement);

		UMLElementTypes.init_Pseudostate_3015(newElement);
		return newElement;
	}
}
