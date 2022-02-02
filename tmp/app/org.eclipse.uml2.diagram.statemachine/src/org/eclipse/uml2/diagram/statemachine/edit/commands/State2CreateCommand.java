package org.eclipse.uml2.diagram.statemachine.edit.commands;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.diagram.statemachine.providers.UMLElementTypes;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * @generated
 */
public class State2CreateCommand extends CreateElementCommand {

	/**
	 * @generated
	 */
	public State2CreateCommand(CreateElementRequest req) {
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
		return UMLPackage.eINSTANCE.getRegion();
	}

	/**
	 * @generated
	 */
	protected EObject doDefaultElementCreation() {
		State newElement = UMLFactory.eINSTANCE.createState();

		Region owner = (Region) getElementToEdit();
		owner.getSubvertices().add(newElement);

		UMLElementTypes.init_State_3012(newElement);
		return newElement;
	}
}
