package org.eclipse.uml2.diagram.statemachine.providers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.edit.ui.provider.AdapterFactoryLabelProvider;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.emf.type.core.ElementTypeRegistry;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.emf.ui.services.modelingassistant.ModelingAssistantProvider;
import org.eclipse.gmf.runtime.notation.Diagram;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.window.Window;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.dialogs.ElementListSelectionDialog;
import org.eclipse.uml2.diagram.statemachine.edit.parts.RegionSubvertices2EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.RegionSubverticesEditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.State2EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.State3EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.StateEditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.StateMachine2EditPart;
import org.eclipse.uml2.diagram.statemachine.edit.parts.StateMachineEditPart;
import org.eclipse.uml2.diagram.statemachine.part.Messages;
import org.eclipse.uml2.diagram.statemachine.part.UMLDiagramEditorPlugin;

/**
 * @generated
 */
public class UMLModelingAssistantProvider extends ModelingAssistantProvider {

	/**
	 * @generated NOT
	 * #227133 Exception on Behavior creation
	 */
	public List getTypesForPopupBar(IAdaptable host) {
		IGraphicalEditPart editPart = (IGraphicalEditPart) host.getAdapter(IGraphicalEditPart.class);
		if (editPart instanceof StateEditPart) {
			List types = new ArrayList();
			//			types.add(UMLElementTypes.Behavior_3019);
			//			types.add(UMLElementTypes.Behavior_3020);
			//			types.add(UMLElementTypes.Behavior_3021);
			return types;
		}
		if (editPart instanceof State2EditPart) {
			List types = new ArrayList();
			types.add(UMLElementTypes.Region_3002);
			//			types.add(UMLElementTypes.Behavior_3019);
			//			types.add(UMLElementTypes.Behavior_3020);
			//			types.add(UMLElementTypes.Behavior_3021);
			return types;
		}
		if (editPart instanceof State3EditPart) {
			List types = new ArrayList();
			types.add(UMLElementTypes.Region_3002);
			types.add(UMLElementTypes.ConnectionPointReference_3017);
			types.add(UMLElementTypes.ConnectionPointReference_3018);
			//			types.add(UMLElementTypes.Behavior_3019);
			//			types.add(UMLElementTypes.Behavior_3020);
			//			types.add(UMLElementTypes.Behavior_3021);
			return types;
		}
		return getTypesForPopupBarGen(host);
	}

	/**
	 * @generated
	 */
	public List getTypesForPopupBarGen(IAdaptable host) {
		IGraphicalEditPart editPart = (IGraphicalEditPart) host.getAdapter(IGraphicalEditPart.class);
		if (editPart instanceof StateMachine2EditPart) {
			List types = new ArrayList();
			types.add(UMLElementTypes.Region_3013);
			types.add(UMLElementTypes.Pseudostate_3014);
			types.add(UMLElementTypes.Pseudostate_3015);
			return types;
		}
		if (editPart instanceof StateEditPart) {
			List types = new ArrayList();
			types.add(UMLElementTypes.Behavior_3019);
			types.add(UMLElementTypes.Behavior_3020);
			types.add(UMLElementTypes.Behavior_3021);
			return types;
		}
		if (editPart instanceof State2EditPart) {
			List types = new ArrayList();
			types.add(UMLElementTypes.Region_3002);
			types.add(UMLElementTypes.Behavior_3019);
			types.add(UMLElementTypes.Behavior_3020);
			types.add(UMLElementTypes.Behavior_3021);
			return types;
		}
		if (editPart instanceof State3EditPart) {
			List types = new ArrayList();
			types.add(UMLElementTypes.Region_3002);
			types.add(UMLElementTypes.ConnectionPointReference_3017);
			types.add(UMLElementTypes.ConnectionPointReference_3018);
			types.add(UMLElementTypes.Behavior_3019);
			types.add(UMLElementTypes.Behavior_3020);
			types.add(UMLElementTypes.Behavior_3021);
			return types;
		}
		if (editPart instanceof RegionSubverticesEditPart) {
			List types = new ArrayList();
			types.add(UMLElementTypes.State_3001);
			types.add(UMLElementTypes.State_3012);
			types.add(UMLElementTypes.State_3016);
			types.add(UMLElementTypes.FinalState_3003);
			types.add(UMLElementTypes.Pseudostate_3004);
			types.add(UMLElementTypes.Pseudostate_3005);
			types.add(UMLElementTypes.Pseudostate_3006);
			types.add(UMLElementTypes.Pseudostate_3007);
			types.add(UMLElementTypes.Pseudostate_3008);
			types.add(UMLElementTypes.Pseudostate_3009);
			types.add(UMLElementTypes.Pseudostate_3010);
			types.add(UMLElementTypes.Pseudostate_3011);
			return types;
		}
		if (editPart instanceof RegionSubvertices2EditPart) {
			List types = new ArrayList();
			types.add(UMLElementTypes.State_3001);
			types.add(UMLElementTypes.State_3012);
			types.add(UMLElementTypes.State_3016);
			types.add(UMLElementTypes.FinalState_3003);
			types.add(UMLElementTypes.Pseudostate_3004);
			types.add(UMLElementTypes.Pseudostate_3005);
			types.add(UMLElementTypes.Pseudostate_3006);
			types.add(UMLElementTypes.Pseudostate_3007);
			types.add(UMLElementTypes.Pseudostate_3008);
			types.add(UMLElementTypes.Pseudostate_3009);
			types.add(UMLElementTypes.Pseudostate_3010);
			types.add(UMLElementTypes.Pseudostate_3011);
			return types;
		}
		if (editPart instanceof StateMachineEditPart) {
			List types = new ArrayList();
			types.add(UMLElementTypes.StateMachine_2004);
			return types;
		}
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public List getRelTypesOnSource(IAdaptable source) {
		IGraphicalEditPart sourceEditPart = (IGraphicalEditPart) source.getAdapter(IGraphicalEditPart.class);
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public List getRelTypesOnTarget(IAdaptable target) {
		IGraphicalEditPart targetEditPart = (IGraphicalEditPart) target.getAdapter(IGraphicalEditPart.class);
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public List getRelTypesOnSourceAndTarget(IAdaptable source, IAdaptable target) {
		IGraphicalEditPart sourceEditPart = (IGraphicalEditPart) source.getAdapter(IGraphicalEditPart.class);
		IGraphicalEditPart targetEditPart = (IGraphicalEditPart) target.getAdapter(IGraphicalEditPart.class);
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public List getTypesForSource(IAdaptable target, IElementType relationshipType) {
		IGraphicalEditPart targetEditPart = (IGraphicalEditPart) target.getAdapter(IGraphicalEditPart.class);
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public List getTypesForTarget(IAdaptable source, IElementType relationshipType) {
		IGraphicalEditPart sourceEditPart = (IGraphicalEditPart) source.getAdapter(IGraphicalEditPart.class);
		return Collections.EMPTY_LIST;
	}

	/**
	 * @generated
	 */
	public EObject selectExistingElementForSource(IAdaptable target, IElementType relationshipType) {
		return selectExistingElement(target, getTypesForSource(target, relationshipType));
	}

	/**
	 * @generated
	 */
	public EObject selectExistingElementForTarget(IAdaptable source, IElementType relationshipType) {
		return selectExistingElement(source, getTypesForTarget(source, relationshipType));
	}

	/**
	 * @generated
	 */
	protected EObject selectExistingElement(IAdaptable host, Collection types) {
		if (types.isEmpty()) {
			return null;
		}
		IGraphicalEditPart editPart = (IGraphicalEditPart) host.getAdapter(IGraphicalEditPart.class);
		if (editPart == null) {
			return null;
		}
		Diagram diagram = (Diagram) editPart.getRoot().getContents().getModel();
		Collection elements = new HashSet();
		for (Iterator it = diagram.getElement().eAllContents(); it.hasNext();) {
			EObject element = (EObject) it.next();
			if (isApplicableElement(element, types)) {
				elements.add(element);
			}
		}
		if (elements.isEmpty()) {
			return null;
		}
		return selectElement((EObject[]) elements.toArray(new EObject[elements.size()]));
	}

	/**
	 * @generated
	 */
	protected boolean isApplicableElement(EObject element, Collection types) {
		IElementType type = ElementTypeRegistry.getInstance().getElementType(element);
		return types.contains(type);
	}

	/**
	 * @generated
	 */
	protected EObject selectElement(EObject[] elements) {
		Shell shell = Display.getCurrent().getActiveShell();
		ILabelProvider labelProvider = new AdapterFactoryLabelProvider(UMLDiagramEditorPlugin.getInstance().getItemProvidersAdapterFactory());
		ElementListSelectionDialog dialog = new ElementListSelectionDialog(shell, labelProvider);
		dialog.setMessage(Messages.UMLModelingAssistantProviderMessage);
		dialog.setTitle(Messages.UMLModelingAssistantProviderTitle);
		dialog.setMultipleSelection(false);
		dialog.setElements(elements);
		EObject selected = null;
		if (dialog.open() == Window.OK) {
			selected = (EObject) dialog.getFirstResult();
		}
		return selected;
	}
}
