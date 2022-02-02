package org.eclipse.uml2.diagram.statemachine.preferences;

import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IEditorReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.uml2.diagram.common.preferences.IconStylePreferencePage;
import org.eclipse.uml2.diagram.statemachine.part.UMLDiagramEditor;
import org.eclipse.uml2.diagram.statemachine.part.UMLDiagramEditorPlugin;
import org.eclipse.uml2.diagram.statemachine.providers.UMLElementTypes;

/**
 * @generated
 */
public class DiagramIconStylePreferencePage extends IconStylePreferencePage {

	/**
	 * @generated
	 */
	private IWorkbench myWorkbench;

	/**
	 * @generated
	 */
	public DiagramIconStylePreferencePage() {
		setPreferenceStore(UMLDiagramEditorPlugin.getInstance().getPreferenceStore());
	}

	/**
	 * @generated
	 */
	@Override
	public void init(IWorkbench workbench) {
		super.init(workbench);
		myWorkbench = workbench;
	}

	/**
	 * @generated
	 */
	public boolean performOk() {
		super.performOk();
		UMLElementTypes.refreshImageRegistry();
		refreshDiagramEditors();
		return true;
	}

	/**
	 * @generated
	 */
	private void refreshDiagramEditors() {
		IEditorReference[] editors = myWorkbench.getActiveWorkbenchWindow().getActivePage().getEditorReferences();
		for (int i = 0; i < editors.length; i++) {
			IEditorPart editor = editors[i].getEditor(false);
			if (editor != null && editor instanceof UMLDiagramEditor) {
				((UMLDiagramEditor) editor).refresh();
			}
		}
	}

	/**
	 * @generated
	 */
	@Override
	protected void initHelp() {
		// TODO Auto-generated method stub

	};

}
