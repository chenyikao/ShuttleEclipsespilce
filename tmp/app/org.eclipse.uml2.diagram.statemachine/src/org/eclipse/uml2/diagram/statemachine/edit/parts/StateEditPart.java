package org.eclipse.uml2.diagram.statemachine.edit.parts;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.RoundedRectangle;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.ToolbarLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.core.edithelpers.CreateElementRequestAdapter;
import org.eclipse.gmf.runtime.diagram.ui.editparts.IGraphicalEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ITextAwareEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.ConstrainedToolbarLayoutEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.CreationEditPolicy;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.diagram.ui.requests.CreateViewAndElementRequest;
import org.eclipse.gmf.runtime.draw2d.ui.figures.ConstrainedToolbarLayout;
import org.eclipse.gmf.runtime.draw2d.ui.figures.WrapLabel;
import org.eclipse.gmf.runtime.emf.type.core.IElementType;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.diagram.common.editparts.PrimaryShapeEditPart;
import org.eclipse.uml2.diagram.statemachine.edit.policies.StateItemSemanticEditPolicy;
import org.eclipse.uml2.diagram.statemachine.edit.policies.UMLTextSelectionEditPolicy;
import org.eclipse.uml2.diagram.statemachine.part.UMLVisualIDRegistry;
import org.eclipse.uml2.diagram.statemachine.providers.UMLElementTypes;

/**
 * @generated
 */
public class StateEditPart extends ShapeNodeEditPart implements PrimaryShapeEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 3001;

	/**
	 * @generated
	 */
	protected IFigure contentPane;

	/**
	 * @generated
	 */
	protected IFigure primaryShape;

	/**
	 * @generated
	 */
	public StateEditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		installEditPolicy(EditPolicyRoles.CREATION_ROLE, new CreationEditPolicy() {

			public Command getCommand(Request request) {
				if (understandsRequest(request)) {
					if (request instanceof CreateViewAndElementRequest) {
						CreateElementRequestAdapter adapter = ((CreateViewAndElementRequest) request).getViewAndElementDescriptor().getCreateElementRequestAdapter();
						IElementType type = (IElementType) adapter.getAdapter(IElementType.class);
						if (type == UMLElementTypes.Behavior_3019) {
							EditPart compartmentEditPart = getChildBySemanticHint(UMLVisualIDRegistry.getType(StateSimpleState_InternalActivitiesEditPart.VISUAL_ID));
							return compartmentEditPart == null ? null : compartmentEditPart.getCommand(request);
						}
						if (type == UMLElementTypes.Behavior_3020) {
							EditPart compartmentEditPart = getChildBySemanticHint(UMLVisualIDRegistry.getType(StateSimpleState_InternalActivitiesEditPart.VISUAL_ID));
							return compartmentEditPart == null ? null : compartmentEditPart.getCommand(request);
						}
						if (type == UMLElementTypes.Behavior_3021) {
							EditPart compartmentEditPart = getChildBySemanticHint(UMLVisualIDRegistry.getType(StateSimpleState_InternalActivitiesEditPart.VISUAL_ID));
							return compartmentEditPart == null ? null : compartmentEditPart.getCommand(request);
						}
					}
					return super.getCommand(request);
				}
				return null;
			}
		});
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new StateItemSemanticEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
		// XXX need an SCR to runtime to have another abstract superclass that would let children add reasonable editpolicies
		// removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);
	}

	/**
	 * @generated
	 */
	protected LayoutEditPolicy createLayoutEditPolicy() {

		ConstrainedToolbarLayoutEditPolicy lep = new ConstrainedToolbarLayoutEditPolicy() {

			protected EditPolicy createChildEditPolicy(EditPart child) {
				if (child.getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE) == null) {
					if (child instanceof ITextAwareEditPart) {
						return new UMLTextSelectionEditPolicy();
					}
				}
				return super.createChildEditPolicy(child);
			}
		};
		return lep;
	}

	/**
	 * @generated
	 */
	protected IFigure createNodeShape() {
		SimpleStateFigure figure = new SimpleStateFigure();
		return primaryShape = figure;
	}

	/**
	 * @generated
	 */
	public SimpleStateFigure getPrimaryShape() {
		return (SimpleStateFigure) primaryShape;
	}

	/**
	 * @generated
	 */
	protected boolean addFixedChild(EditPart childEditPart) {
		if (childEditPart instanceof StateNameEditPart) {
			((StateNameEditPart) childEditPart).setLabel(getPrimaryShape().getFigureSimpleStateFigure_name());
			return true;
		}
		if (childEditPart instanceof StateSimpleState_InternalActivitiesEditPart) {
			IFigure pane = getPrimaryShape().getFigureSimpleStateFigure_InternalActivitiesCompartment();
			setupContentPane(pane); // FIXME each comparment should handle his content pane in his own way 
			pane.add(((StateSimpleState_InternalActivitiesEditPart) childEditPart).getFigure());
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected boolean removeFixedChild(EditPart childEditPart) {

		if (childEditPart instanceof StateSimpleState_InternalActivitiesEditPart) {
			IFigure pane = getPrimaryShape().getFigureSimpleStateFigure_InternalActivitiesCompartment();
			pane.remove(((StateSimpleState_InternalActivitiesEditPart) childEditPart).getFigure());
			return true;
		}
		return false;
	}

	/**
	 * @generated
	 */
	protected void addChildVisual(EditPart childEditPart, int index) {
		if (addFixedChild(childEditPart)) {
			return;
		}
		super.addChildVisual(childEditPart, -1);
	}

	/**
	 * @generated
	 */
	protected void removeChildVisual(EditPart childEditPart) {
		if (removeFixedChild(childEditPart)) {
			return;
		}
		super.removeChildVisual(childEditPart);
	}

	/**
	 * @generated
	 */
	protected IFigure getContentPaneFor(IGraphicalEditPart editPart) {

		if (editPart instanceof StateSimpleState_InternalActivitiesEditPart) {
			return getPrimaryShape().getFigureSimpleStateFigure_InternalActivitiesCompartment();
		}
		return super.getContentPaneFor(editPart);
	}

	/**
	 * @generated
	 */
	protected NodeFigure createNodePlate() {
		DefaultSizeNodeFigure result = new DefaultSizeNodeFigure(getMapMode().DPtoLP(40), getMapMode().DPtoLP(40));
		return result;
	}

	/**
	 * Creates figure for this edit part.
	 * 
	 * Body of this method does not depend on settings in generation model
	 * so you may safely remove <i>generated</i> tag and modify it.
	 * 
	 * @generated
	 */
	protected NodeFigure createNodeFigure() {
		NodeFigure figure = createNodePlate();
		figure.setLayoutManager(new StackLayout());
		IFigure shape = createNodeShape();
		figure.add(shape);
		contentPane = setupContentPane(shape);
		return figure;
	}

	/**
	 * Default implementation treats passed figure as content pane.
	 * Respects layout one may have set for generated figure.
	 * @param nodeShape instance of generated figure class
	 * @generated
	 */
	protected IFigure setupContentPane(IFigure nodeShape) {
		if (nodeShape.getLayoutManager() == null) {
			ConstrainedToolbarLayout layout = new ConstrainedToolbarLayout();
			layout.setSpacing(getMapMode().DPtoLP(5));
			nodeShape.setLayoutManager(layout);
		}
		return nodeShape; // use nodeShape itself as contentPane
	}

	/**
	 * @generated
	 */
	public IFigure getContentPane() {
		if (contentPane != null) {
			return contentPane;
		}
		return super.getContentPane();
	}

	/**
	 * @generated
	 */
	public EditPart getPrimaryChildEditPart() {
		return getChildBySemanticHint(UMLVisualIDRegistry.getType(StateNameEditPart.VISUAL_ID));
	}

	/**
	 * @generated
	 */
	public class SimpleStateFigure extends RoundedRectangle {

		/**
		 * @generated
		 */
		private Label fFigureSimpleStateFigure_name;

		/**
		 * @generated
		 */
		private RectangleFigure fFigureSimpleStateFigure_InternalActivitiesCompartment;

		/**
		 * @generated
		 */
		public SimpleStateFigure() {

			ToolbarLayout layoutThis = new ToolbarLayout();
			layoutThis.setStretchMinorAxis(true);
			layoutThis.setMinorAlignment(ToolbarLayout.ALIGN_CENTER);

			layoutThis.setSpacing(5);
			layoutThis.setVertical(true);

			this.setLayoutManager(layoutThis);

			this.setCornerDimensions(new Dimension(getMapMode().DPtoLP(18), getMapMode().DPtoLP(18)));
			this.setBorder(new MarginBorder(getMapMode().DPtoLP(4), getMapMode().DPtoLP(4), getMapMode().DPtoLP(4), getMapMode().DPtoLP(4)));
			createContents();
		}

		/**
		 * @generated
		 */
		private void createContents() {

			fFigureSimpleStateFigure_name = new Label();
			fFigureSimpleStateFigure_name.setText("");

			this.add(fFigureSimpleStateFigure_name);

			fFigureSimpleStateFigure_InternalActivitiesCompartment = new RectangleFigure();
			fFigureSimpleStateFigure_InternalActivitiesCompartment.setOutline(false);

			this.add(fFigureSimpleStateFigure_InternalActivitiesCompartment);

			fFigureSimpleStateFigure_InternalActivitiesCompartment.setLayoutManager(new StackLayout());

		}

		/**
		 * @generated
		 */
		private boolean myUseLocalCoordinates = false;

		/**
		 * @generated
		 */
		protected boolean useLocalCoordinates() {
			return myUseLocalCoordinates;
		}

		/**
		 * @generated
		 */
		protected void setUseLocalCoordinates(boolean useLocalCoordinates) {
			myUseLocalCoordinates = useLocalCoordinates;
		}

		/**
		 * @generated
		 */
		public Label getFigureSimpleStateFigure_name() {
			return fFigureSimpleStateFigure_name;
		}

		/**
		 * @generated
		 */
		public RectangleFigure getFigureSimpleStateFigure_InternalActivitiesCompartment() {
			return fFigureSimpleStateFigure_InternalActivitiesCompartment;
		}

	}

}
