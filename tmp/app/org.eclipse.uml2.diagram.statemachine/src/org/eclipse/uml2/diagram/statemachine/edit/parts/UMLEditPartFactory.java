package org.eclipse.uml2.diagram.statemachine.edit.parts;

import org.eclipse.draw2d.FigureUtilities;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPartFactory;
import org.eclipse.gef.tools.CellEditorLocator;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrappingLabel;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Text;
import org.eclipse.uml2.diagram.statemachine.part.UMLVisualIDRegistry;

/**
 * @generated
 */
public class UMLEditPartFactory implements EditPartFactory {

	/**
	 * @generated
	 */
	public EditPart createEditPart(EditPart context, Object model) {
		if (model instanceof View) {
			View view = (View) model;
			switch (UMLVisualIDRegistry.getVisualID(view)) {

			case StateMachineEditPart.VISUAL_ID:
				return new StateMachineEditPart(view);

			case StateMachine2EditPart.VISUAL_ID:
				return new StateMachine2EditPart(view);

			case StateMachineNameEditPart.VISUAL_ID:
				return new StateMachineNameEditPart(view);

			case RegionEditPart.VISUAL_ID:
				return new RegionEditPart(view);

			case StateEditPart.VISUAL_ID:
				return new StateEditPart(view);

			case StateNameEditPart.VISUAL_ID:
				return new StateNameEditPart(view);

			case BehaviorEditPart.VISUAL_ID:
				return new BehaviorEditPart(view);

			case Behavior2EditPart.VISUAL_ID:
				return new Behavior2EditPart(view);

			case Behavior3EditPart.VISUAL_ID:
				return new Behavior3EditPart(view);

			case State2EditPart.VISUAL_ID:
				return new State2EditPart(view);

			case StateName3EditPart.VISUAL_ID:
				return new StateName3EditPart(view);

			case Region2EditPart.VISUAL_ID:
				return new Region2EditPart(view);

			case State3EditPart.VISUAL_ID:
				return new State3EditPart(view);

			case StateName2EditPart.VISUAL_ID:
				return new StateName2EditPart(view);

			case ConnectionPointReferenceEditPart.VISUAL_ID:
				return new ConnectionPointReferenceEditPart(view);

			case ConnectionPointReferenceNameEditPart.VISUAL_ID:
				return new ConnectionPointReferenceNameEditPart(view);

			case ConnectionPointReference2EditPart.VISUAL_ID:
				return new ConnectionPointReference2EditPart(view);

			case ConnectionPointReferenceName2EditPart.VISUAL_ID:
				return new ConnectionPointReferenceName2EditPart(view);

			case FinalStateEditPart.VISUAL_ID:
				return new FinalStateEditPart(view);

			case PseudostateEditPart.VISUAL_ID:
				return new PseudostateEditPart(view);

			case Pseudostate2EditPart.VISUAL_ID:
				return new Pseudostate2EditPart(view);

			case Pseudostate3EditPart.VISUAL_ID:
				return new Pseudostate3EditPart(view);

			case Pseudostate4EditPart.VISUAL_ID:
				return new Pseudostate4EditPart(view);

			case Pseudostate5EditPart.VISUAL_ID:
				return new Pseudostate5EditPart(view);

			case Pseudostate6EditPart.VISUAL_ID:
				return new Pseudostate6EditPart(view);

			case Pseudostate7EditPart.VISUAL_ID:
				return new Pseudostate7EditPart(view);

			case Pseudostate8EditPart.VISUAL_ID:
				return new Pseudostate8EditPart(view);

			case Pseudostate9EditPart.VISUAL_ID:
				return new Pseudostate9EditPart(view);

			case PseudostateNameEditPart.VISUAL_ID:
				return new PseudostateNameEditPart(view);

			case Pseudostate10EditPart.VISUAL_ID:
				return new Pseudostate10EditPart(view);

			case PseudostateName2EditPart.VISUAL_ID:
				return new PseudostateName2EditPart(view);

			case RegionSubverticesEditPart.VISUAL_ID:
				return new RegionSubverticesEditPart(view);

			case StateSimpleState_InternalActivitiesEditPart.VISUAL_ID:
				return new StateSimpleState_InternalActivitiesEditPart(view);

			case StateCompositeState_InternalActivitiesEditPart.VISUAL_ID:
				return new StateCompositeState_InternalActivitiesEditPart(view);

			case RegionSubvertices2EditPart.VISUAL_ID:
				return new RegionSubvertices2EditPart(view);

			case StateCompositeState_InternalActivities2EditPart.VISUAL_ID:
				return new StateCompositeState_InternalActivities2EditPart(view);

			case TransitionEditPart.VISUAL_ID:
				return new TransitionEditPart(view);

			case TransitionNameEditPart.VISUAL_ID:
				return new TransitionNameEditPart(view);
			}
		}
		return createUnrecognizedEditPart(context, model);
	}

	/**
	 * @generated
	 */
	private EditPart createUnrecognizedEditPart(EditPart context, Object model) {
		// Handle creation of unrecognized child node EditParts here
		return null;
	}

	/**
	 * @generated
	 */
	public static CellEditorLocator getTextCellEditorLocator(ITextAwareEditPart source) {
		if (source.getFigure() instanceof WrappingLabel)
			return new TextCellEditorLocator((WrappingLabel) source.getFigure());
		else {
			return new LabelCellEditorLocator((Label) source.getFigure());
		}
	}

	/**
	 * @generated
	 */
	static private class TextCellEditorLocator implements CellEditorLocator {

		/**
		 * @generated
		 */
		private WrappingLabel wrapLabel;

		/**
		 * @generated
		 */
		public TextCellEditorLocator(WrappingLabel wrapLabel) {
			this.wrapLabel = wrapLabel;
		}

		/**
		 * @generated
		 */
		public WrappingLabel getWrapLabel() {
			return wrapLabel;
		}

		/**
		 * @generated
		 */
		public void relocate(CellEditor celleditor) {
			Text text = (Text) celleditor.getControl();
			Rectangle rect = getWrapLabel().getTextBounds().getCopy();
			getWrapLabel().translateToAbsolute(rect);
			if (getWrapLabel().isTextWrapOn() && getWrapLabel().getText().length() > 0) {
				rect.setSize(new Dimension(text.computeSize(rect.width, SWT.DEFAULT)));
			} else {
				int avr = FigureUtilities.getFontMetrics(text.getFont()).getAverageCharWidth();
				rect.setSize(new Dimension(text.computeSize(SWT.DEFAULT, SWT.DEFAULT)).expand(avr * 2, 0));
			}
			if (!rect.equals(new Rectangle(text.getBounds()))) {
				text.setBounds(rect.x, rect.y, rect.width, rect.height);
			}
		}

	}

	/**
	 * @generated
	 */
	private static class LabelCellEditorLocator implements CellEditorLocator {

		/**
		 * @generated
		 */
		private Label label;

		/**
		 * @generated
		 */
		public LabelCellEditorLocator(Label label) {
			this.label = label;
		}

		/**
		 * @generated
		 */
		public Label getLabel() {
			return label;
		}

		/**
		 * @generated
		 */
		public void relocate(CellEditor celleditor) {
			Text text = (Text) celleditor.getControl();
			Rectangle rect = getLabel().getTextBounds().getCopy();
			getLabel().translateToAbsolute(rect);
			int avr = FigureUtilities.getFontMetrics(text.getFont()).getAverageCharWidth();
			rect.setSize(new Dimension(text.computeSize(SWT.DEFAULT, SWT.DEFAULT)).expand(avr * 2, 0));
			if (!rect.equals(new Rectangle(text.getBounds()))) {
				text.setBounds(rect.x, rect.y, rect.width, rect.height);
			}
		}
	}
}
