/*
 * Copyright (c) 2007 Borland Software Corporation
 * 
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Sergey Gribovsky (Borland) - initial API and implementation
 */

package org.eclipse.uml2.diagram.statemachine.parser;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.transaction.TransactionalEditingDomain;
import org.eclipse.emf.transaction.util.TransactionUtil;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.gmf.runtime.emf.commands.core.command.CompositeTransactionalCommand;
import org.eclipse.gmf.runtime.emf.type.core.commands.SetValueCommand;
import org.eclipse.gmf.runtime.emf.type.core.requests.SetRequest;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.uml2.uml.NamedElement;
import org.eclipse.uml2.uml.State;
import org.eclipse.uml2.uml.StateMachine;
import org.eclipse.uml2.uml.UMLPackage;

public class SubmachineStateParser implements IParser {
	public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
		return null;
	}

	public String getEditString(IAdaptable element, int flags) {
		EObject eObject = (EObject)element.getAdapter(EObject.class);
		if (eObject instanceof NamedElement) {
			return ((NamedElement) eObject).getName();
		}
		return ""; //$NON-NLS-1$
	}

	public String getPrintString(IAdaptable element, int flags) {
		EObject eObject = (EObject)element.getAdapter(EObject.class);
		if (eObject instanceof State) {
			StringBuffer printStringBuffer = new StringBuffer(20);
			State state = (State) eObject;
			printStringBuffer.append(state.getName());
			StateMachine submachine = state.getSubmachine();
			if (submachine != null) {
				String submachineName = submachine.getName();
				if (submachineName != null && submachineName.length() > 0) {
					printStringBuffer.append(':');
					printStringBuffer.append(submachineName);
				}
			}
			return printStringBuffer.toString();
		}
		return ""; //$NON-NLS-1$
	}

	public boolean isAffectingEvent(Object event, int flags) {
		if (event instanceof Notification) {
			Object feature = ((Notification) event).getFeature();
			return UMLPackage.eINSTANCE.getNamedElement_Name().equals(feature) ||
				UMLPackage.eINSTANCE.getState_Submachine().equals(feature);
		}
		return false;
	}

	public IParserEditStatus isValidEditString(IAdaptable element, String editString) {
		return ParserEditStatus.EDITABLE_STATUS;
	}

	public ICommand getParseCommand(IAdaptable adapter, String newString, int flags) {
		EObject element = (EObject) adapter.getAdapter(EObject.class);
		TransactionalEditingDomain editingDomain = TransactionUtil.getEditingDomain(element);
		if (editingDomain == null) {
			return UnexecutableCommand.INSTANCE;
		}
		CompositeTransactionalCommand command = new CompositeTransactionalCommand(editingDomain, "Set Values"); //$NON-NLS-1$
		command.compose(getSetNameCommand(element, newString));
		return command;
	}

	private ICommand getSetNameCommand(EObject element, String name) {
		SetRequest request = new SetRequest(element, UMLPackage.eINSTANCE.getNamedElement_Name(), name);
		return new SetValueCommand(request);
	}
}
