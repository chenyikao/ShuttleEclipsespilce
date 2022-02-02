package org.eclipse.uml2.diagram.statemachine.edit.commands;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.emf.type.core.commands.CreateElementCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.CreateElementRequest;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.UMLPackage;

/**
 * @generated
 */
public class Behavior2CreateCommand extends CreateElementCommand {

	/**
	 * @generated
	 */
	public Behavior2CreateCommand(CreateElementRequest req) {
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
	public boolean canExecute() {
		State container = (State) getElementToEdit();
		if (container.getExit() != null) {
			return false;
		}
		return true;
	}

	/**
	 * @generated
	 */
	protected EClass getEClassToEdit() {
		return UMLPackage.eINSTANCE.getState();
	}

}
