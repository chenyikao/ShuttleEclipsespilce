package org.eclipse.uml2.diagram.statemachine.edit.commands;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.diagram.statemachine.providers.UMLElementTypes;
import org.eclipse.uml2.uml.Pseudostate;
import org.eclipse.uml2.uml.Region;
import org.eclipse.uml2.uml.UMLFactory;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * @generated
 */
public class Pseudostate8CreateCommand extends CreateElementCommand {

	/**
	 * @generated
	 */
	public Pseudostate8CreateCommand(CreateElementRequest req) {
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
		Pseudostate newElement = UMLFactory.eINSTANCE.createPseudostate();

		Region owner = (Region) getElementToEdit();
		owner.getSubvertices().add(newElement);

		UMLElementTypes.init_Pseudostate_3011(newElement);
		return newElement;
	}
}
