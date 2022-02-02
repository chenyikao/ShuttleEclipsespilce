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

import java.util.Iterator;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.gmf.runtime.common.core.command.ICommand;
import org.eclipse.gmf.runtime.common.core.command.UnexecutableCommand;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParser;
import org.eclipse.gmf.runtime.common.ui.services.parser.IParserEditStatus;
import org.eclipse.gmf.runtime.common.ui.services.parser.ParserEditStatus;
import org.eclipse.jface.text.contentassist.IContentAssistProcessor;
import org.eclipse.uml2.uml.Behavior;
import org.eclipse.uml2.uml.Constraint;
import org.eclipse.uml2.uml.Event;
import org.eclipse.uml2.uml.Transition;
import org.eclipse.uml2.uml.Trigger;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.ValueSpecification;

public class TransitionParser implements IParser {
	public IContentAssistProcessor getCompletionProcessor(IAdaptable element) {
		return null;
	}

	public String getEditString(IAdaptable element, int flags) {
		return getPrintString(element, flags);
	}

	public ICommand getParseCommand(IAdaptable element, String newString, int flags) {
		return UnexecutableCommand.INSTANCE;
	}

	public String getPrintString(IAdaptable element, int flags) {
		EObject eObject = (EObject)element.getAdapter(EObject.class);
		if (eObject instanceof Transition) {
			Transition transition = (Transition) eObject;
			StringBuffer printStringBuffer = new StringBuffer(20);
			
			EList<Trigger> triggers = transition.getTriggers();
			for (Iterator<Trigger> triggersIterator = triggers.iterator(); triggersIterator.hasNext();) {
				Trigger trigger = triggersIterator.next();
				Event event = trigger.getEvent();
				if (event != null) {
					printStringBuffer.append(event.getLabel());
					if (triggersIterator.hasNext()) {
						printStringBuffer.append(',');
					}
				}
			}
			
			Constraint guard = transition.getGuard();
			if (guard != null) {
				ValueSpecification specification = guard.getSpecification();
				if (specification != null) {
					printStringBuffer.append('[');
					printStringBuffer.append(specification.stringValue());
					printStringBuffer.append(']');
				}
			}
			
			Behavior effect = transition.getEffect();
			if (effect != null) {
				printStringBuffer.append('/');
				printStringBuffer.append(effect.getLabel());
			}
			
			return printStringBuffer.toString();
		}
		return ""; //$NON-NLS-1$
	}

	public boolean isAffectingEvent(Object event, int flags) {
		if (event instanceof Notification) {
			Object feature = ((Notification) event).getFeature();
			return UMLPackage.eINSTANCE.getTransition_Trigger().equals(feature) ||
				UMLPackage.eINSTANCE.getTransition_Guard().equals(feature) ||
				UMLPackage.eINSTANCE.getTransition_Effect().equals(feature);
		}
		return false;
	}

	public IParserEditStatus isValidEditString(IAdaptable element, String editString) {
		return ParserEditStatus.UNEDITABLE_STATUS;
	}
}
