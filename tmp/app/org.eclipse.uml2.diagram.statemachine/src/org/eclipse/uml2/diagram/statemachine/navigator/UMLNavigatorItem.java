package org.eclipse.uml2.diagram.statemachine.navigator;

import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.core.runtime.Platform;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.gmf.runtime.common.core.util.IAdaptableSelection;
import org.eclipse.gmf.runtime.notation.View;

/**
 * @generated
 */
public class UMLNavigatorItem extends UMLAbstractNavigatorItem implements IAdaptableSelection {

	/**
	 * @generated
	 */
	static {
		final Class[] supportedTypes = new Class[] { View.class, EObject.class };
		Platform.getAdapterManager().registerAdapters(new IAdapterFactory() {

			public Object getAdapter(Object adaptableObject, Class adapterType) {
				if (adaptableObject instanceof org.eclipse.uml2.diagram.statemachine.navigator.UMLNavigatorItem && (adapterType == View.class || adapterType == EObject.class)) {
					return ((org.eclipse.uml2.diagram.statemachine.navigator.UMLNavigatorItem) adaptableObject).getView();
				}
				return null;
			}

			public Class[] getAdapterList() {
				return supportedTypes;
			}
		}, org.eclipse.uml2.diagram.statemachine.navigator.UMLNavigatorItem.class);
	}

	/**
	 * @generated
	 */
	private View myView;

	/**
	 * @generated
	 */
	private boolean myLeaf = false;

	/**
	 * @generated
	 */
	public UMLNavigatorItem(View view, Object parent, boolean isLeaf) {
		super(parent);
		myView = view;
		myLeaf = isLeaf;
	}

	/**
	 * @generated
	 */
	public View getView() {
		return myView;
	}

	/**
	 * @generated
	 */
	public boolean isLeaf() {
		return myLeaf;
	}

	/**
	 * @generated
	 */
	public boolean equals(Object obj) {
		if (obj instanceof org.eclipse.uml2.diagram.statemachine.navigator.UMLNavigatorItem) {
			return EcoreUtil.getURI(getView()).equals(EcoreUtil.getURI(((org.eclipse.uml2.diagram.statemachine.navigator.UMLNavigatorItem) obj).getView()));
		}
		return super.equals(obj);
	}

	/**
	 * @generated
	 */
	public int hashCode() {
		return EcoreUtil.getURI(getView()).hashCode();
	}

}
