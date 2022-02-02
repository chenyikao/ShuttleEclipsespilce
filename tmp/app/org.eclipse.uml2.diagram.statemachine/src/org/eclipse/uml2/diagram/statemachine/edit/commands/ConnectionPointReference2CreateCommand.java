package org.eclipse.uml2.diagram.statemachine.edit.commands;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.diagram.statemachine.providers.UMLElementTypes;
import org.eclipse.uml2.uml.ConnectionPointReference;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * @generated
 */
public class ConnectionPointReference2CreateCommand extends CreateElementCommand {

	/**
	 * @generated
	 */
	public ConnectionPointReference2CreateCommand(CreateElementRequest req) {
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
		return UMLPackage.eINSTANCE.getState();
	}

	/**
	 * @generated
	 */
	protected EObject doDefaultElementCreation() {
		ConnectionPointReference newElement = UMLFactory.eINSTANCE.createConnectionPointReference();

		State owner = (State) getElementToEdit();
		owner.getConnections().add(newElement);

		UMLElementTypes.init_ConnectionPointReference_3018(newElement);
		return newElement;
	}
}
