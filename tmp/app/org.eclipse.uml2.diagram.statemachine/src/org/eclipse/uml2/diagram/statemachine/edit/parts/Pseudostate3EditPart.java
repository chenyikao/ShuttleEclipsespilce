package org.eclipse.uml2.diagram.statemachine.edit.parts;

import org.eclipse.draw2d.Ellipse;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.GridData;
import org.eclipse.draw2d.GridLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.draw2d.RectangleFigure;
import org.eclipse.draw2d.Shape;
import org.eclipse.draw2d.StackLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.PointList;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.Request;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.LayoutEditPolicy;
import org.eclipse.gef.editpolicies.NonResizableEditPolicy;
import org.eclipse.gef.requests.CreateRequest;
import org.eclipse.gmf.runtime.diagram.ui.editparts.ShapeNodeEditPart;
import org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles;
import org.eclipse.gmf.runtime.gef.ui.figures.DefaultSizeNodeFigure;
import org.eclipse.gmf.runtime.gef.ui.figures.NodeFigure;
import org.eclipse.gmf.runtime.notation.View;
import org.eclipse.uml2.diagram.common.editparts.PrimaryShapeEditPart;
import org.eclipse.uml2.diagram.statemachine.edit.policies.Pseudostate3ItemSemanticEditPolicy;

/**
 * @generated
 */
public class Pseudostate3EditPart extends ShapeNodeEditPart implements PrimaryShapeEditPart {

	/**
	 * @generated
	 */
	public static final int VISUAL_ID = 3006;

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
	public Pseudostate3EditPart(View view) {
		super(view);
	}

	/**
	 * @generated
	 */
	protected void createDefaultEditPolicies() {
		super.createDefaultEditPolicies();
		installEditPolicy(EditPolicyRoles.SEMANTIC_ROLE, new Pseudostate3ItemSemanticEditPolicy());
		installEditPolicy(EditPolicy.LAYOUT_ROLE, createLayoutEditPolicy());
		// XXX need an SCR to runtime to have another abstract superclass that would let children add reasonable editpolicies
		// removeEditPolicy(org.eclipse.gmf.runtime.diagram.ui.editpolicies.EditPolicyRoles.CONNECTION_HANDLES_ROLE);
	}

	/**
	 * @generated
	 */
	protected LayoutEditPolicy createLayoutEditPolicy() {
		LayoutEditPolicy lep = new LayoutEditPolicy() {

			protected EditPolicy createChildEditPolicy(EditPart child) {
				EditPolicy result = child.getEditPolicy(EditPolicy.PRIMARY_DRAG_ROLE);
				if (result == null) {
					result = new NonResizableEditPolicy();
				}
				return result;
			}

			protected Command getMoveChildrenCommand(Request request) {
				return null;
			}

			protected Command getCreateCommand(CreateRequest request) {
				return null;
			}
		};
		return lep;
	}

	/**
	 * @generated
	 */
	protected IFigure createNodeShape() {
		DeepHistoryFigure figure = new DeepHistoryFigure();
		return primaryShape = figure;
	}

	/**
	 * @generated
	 */
	public DeepHistoryFigure getPrimaryShape() {
		return (DeepHistoryFigure) primaryShape;
	}

	/**
	 * @generated
	 */
	protected NodeFigure createNodePlate() {
		DefaultSizeNodeFigure result = new DefaultSizeNodeFigure(getMapMode().DPtoLP(25), getMapMode().DPtoLP(25));
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
	public class DeepHistoryFigure extends Ellipse {

		/**
		 * @generated
		 */
		public DeepHistoryFigure() {

			GridLayout layoutThis = new GridLayout();
			layoutThis.numColumns = 2;
			layoutThis.makeColumnsEqualWidth = true;
			layoutThis.horizontalSpacing = 0;
			layoutThis.verticalSpacing = 0;
			layoutThis.marginWidth = 0;
			layoutThis.marginHeight = 0;
			this.setLayoutManager(layoutThis);

			this.setPreferredSize(new Dimension(getMapMode().DPtoLP(25), getMapMode().DPtoLP(25)));
			this.setMaximumSize(new Dimension(getMapMode().DPtoLP(25), getMapMode().DPtoLP(25)));
			this.setMinimumSize(new Dimension(getMapMode().DPtoLP(25), getMapMode().DPtoLP(25)));
			this.setBorder(new MarginBorder(getMapMode().DPtoLP(6), getMapMode().DPtoLP(6), getMapMode().DPtoLP(6), getMapMode().DPtoLP(3)));
			createContents();
		}

		/**
		 * @generated
		 */
		private void createContents() {

			class DeepHistoryFigure_Letter0Class extends Shape {

				/**
				 * @generated
				 */
				private final PointList myTemplate = new PointList();

				/**
				 * @generated
				 */
				private Rectangle myTemplateBounds;

				/**
				 * @generated
				 */
				public void addPoint(Point point) {
					myTemplate.addPoint(point);
					myTemplateBounds = null;
				}

				/**
				 * @generated
				 */
				protected void fillShape(Graphics graphics) {
					Rectangle bounds = getBounds();
					graphics.pushState();
					graphics.translate(bounds.x, bounds.y);
					graphics.fillPolygon(scalePointList());
					graphics.popState();
				}

				/**
				 * @generated
				 */
				protected void outlineShape(Graphics graphics) {
					Rectangle bounds = getBounds();
					graphics.pushState();
					graphics.translate(bounds.x, bounds.y);
					graphics.drawPolygon(scalePointList());
					graphics.popState();
				}

				/**
				 * @generated
				 */
				private Rectangle getTemplateBounds() {
					if (myTemplateBounds == null) {
						myTemplateBounds = myTemplate.getBounds().getCopy().union(0, 0);
						//just safety -- we are going to use this as divider 
						if (myTemplateBounds.width < 1) {
							myTemplateBounds.width = 1;
						}
						if (myTemplateBounds.height < 1) {
							myTemplateBounds.height = 1;
						}
					}
					return myTemplateBounds;
				}

				/**
				 * @generated
				 */
				private int[] scalePointList() {
					Rectangle pointsBounds = getTemplateBounds();
					Rectangle actualBounds = getBounds();

					float xScale = ((float) actualBounds.width) / pointsBounds.width;
					float yScale = ((float) actualBounds.height) / pointsBounds.height;

					if (xScale == 1 && yScale == 1) {
						return myTemplate.toIntArray();
					}
					int[] scaled = (int[]) myTemplate.toIntArray().clone();
					for (int i = 0; i < scaled.length; i += 2) {
						scaled[i] = (int) Math.floor(scaled[i] * xScale);
						scaled[i + 1] = (int) Math.floor(scaled[i + 1] * yScale);
					}
					return scaled;
				}
			}
			;
			DeepHistoryFigure_Letter0Class deepHistoryFigure_Letter0 = new DeepHistoryFigure_Letter0Class();

			deepHistoryFigure_Letter0.addPoint(new Point(getMapMode().DPtoLP(0), getMapMode().DPtoLP(40)));
			deepHistoryFigure_Letter0.addPoint(new Point(getMapMode().DPtoLP(0), getMapMode().DPtoLP(20)));
			deepHistoryFigure_Letter0.addPoint(new Point(getMapMode().DPtoLP(40), getMapMode().DPtoLP(20)));
			deepHistoryFigure_Letter0.addPoint(new Point(getMapMode().DPtoLP(40), getMapMode().DPtoLP(40)));
			deepHistoryFigure_Letter0.addPoint(new Point(getMapMode().DPtoLP(40), getMapMode().DPtoLP(0)));
			deepHistoryFigure_Letter0.addPoint(new Point(getMapMode().DPtoLP(40), getMapMode().DPtoLP(20)));
			deepHistoryFigure_Letter0.addPoint(new Point(getMapMode().DPtoLP(0), getMapMode().DPtoLP(20)));
			deepHistoryFigure_Letter0.addPoint(new Point(getMapMode().DPtoLP(0), getMapMode().DPtoLP(0)));
			deepHistoryFigure_Letter0.setPreferredSize(new Dimension(getMapMode().DPtoLP(15), getMapMode().DPtoLP(15)));
			deepHistoryFigure_Letter0.setMaximumSize(new Dimension(getMapMode().DPtoLP(15), getMapMode().DPtoLP(15)));

			GridData constraintDeepHistoryFigure_Letter0 = new GridData();
			constraintDeepHistoryFigure_Letter0.verticalAlignment = GridData.FILL;
			constraintDeepHistoryFigure_Letter0.horizontalAlignment = GridData.FILL;
			constraintDeepHistoryFigure_Letter0.horizontalIndent = 0;
			constraintDeepHistoryFigure_Letter0.horizontalSpan = 1;
			constraintDeepHistoryFigure_Letter0.verticalSpan = 1;
			constraintDeepHistoryFigure_Letter0.grabExcessHorizontalSpace = true;
			constraintDeepHistoryFigure_Letter0.grabExcessVerticalSpace = true;
			this.add(deepHistoryFigure_Letter0, constraintDeepHistoryFigure_Letter0);

			RectangleFigure deepHistoryFigure_AsteriskContainer0 = new RectangleFigure();
			deepHistoryFigure_AsteriskContainer0.setFill(false);
			deepHistoryFigure_AsteriskContainer0.setOutline(false);
			deepHistoryFigure_AsteriskContainer0.setPreferredSize(new Dimension(getMapMode().DPtoLP(15), getMapMode().DPtoLP(15)));
			deepHistoryFigure_AsteriskContainer0.setMinimumSize(new Dimension(getMapMode().DPtoLP(15), getMapMode().DPtoLP(15)));
			deepHistoryFigure_AsteriskContainer0.setBorder(new MarginBorder(getMapMode().DPtoLP(0), getMapMode().DPtoLP(2), getMapMode().DPtoLP(5), getMapMode().DPtoLP(0)));

			GridData constraintDeepHistoryFigure_AsteriskContainer0 = new GridData();
			constraintDeepHistoryFigure_AsteriskContainer0.verticalAlignment = GridData.FILL;
			constraintDeepHistoryFigure_AsteriskContainer0.horizontalAlignment = GridData.FILL;
			constraintDeepHistoryFigure_AsteriskContainer0.horizontalIndent = 0;
			constraintDeepHistoryFigure_AsteriskContainer0.horizontalSpan = 1;
			constraintDeepHistoryFigure_AsteriskContainer0.verticalSpan = 1;
			constraintDeepHistoryFigure_AsteriskContainer0.grabExcessHorizontalSpace = true;
			constraintDeepHistoryFigure_AsteriskContainer0.grabExcessVerticalSpace = true;
			this.add(deepHistoryFigure_AsteriskContainer0, constraintDeepHistoryFigure_AsteriskContainer0);

			deepHistoryFigure_AsteriskContainer0.setLayoutManager(new StackLayout());

			class DeepHistoryFigure_Asterisk1Class extends Shape {

				/**
				 * @generated
				 */
				private final PointList myTemplate = new PointList();

				/**
				 * @generated
				 */
				private Rectangle myTemplateBounds;

				/**
				 * @generated
				 */
				public void addPoint(Point point) {
					myTemplate.addPoint(point);
					myTemplateBounds = null;
				}

				/**
				 * @generated
				 */
				protected void fillShape(Graphics graphics) {
					Rectangle bounds = getBounds();
					graphics.pushState();
					graphics.translate(bounds.x, bounds.y);
					graphics.fillPolygon(scalePointList());
					graphics.popState();
				}

				/**
				 * @generated
				 */
				protected void outlineShape(Graphics graphics) {
					Rectangle bounds = getBounds();
					graphics.pushState();
					graphics.translate(bounds.x, bounds.y);
					graphics.drawPolygon(scalePointList());
					graphics.popState();
				}

				/**
				 * @generated
				 */
				private Rectangle getTemplateBounds() {
					if (myTemplateBounds == null) {
						myTemplateBounds = myTemplate.getBounds().getCopy().union(0, 0);
						//just safety -- we are going to use this as divider 
						if (myTemplateBounds.width < 1) {
							myTemplateBounds.width = 1;
						}
						if (myTemplateBounds.height < 1) {
							myTemplateBounds.height = 1;
						}
					}
					return myTemplateBounds;
				}

				/**
				 * @generated
				 */
				private int[] scalePointList() {
					Rectangle pointsBounds = getTemplateBounds();
					Rectangle actualBounds = getBounds();

					float xScale = ((float) actualBounds.width) / pointsBounds.width;
					float yScale = ((float) actualBounds.height) / pointsBounds.height;

					if (xScale == 1 && yScale == 1) {
						return myTemplate.toIntArray();
					}
					int[] scaled = (int[]) myTemplate.toIntArray().clone();
					for (int i = 0; i < scaled.length; i += 2) {
						scaled[i] = (int) Math.floor(scaled[i] * xScale);
						scaled[i + 1] = (int) Math.floor(scaled[i + 1] * yScale);
					}
					return scaled;
				}
			}
			;
			DeepHistoryFigure_Asterisk1Class deepHistoryFigure_Asterisk1 = new DeepHistoryFigure_Asterisk1Class();

			deepHistoryFigure_Asterisk1.addPoint(new Point(getMapMode().DPtoLP(10), getMapMode().DPtoLP(7)));
			deepHistoryFigure_Asterisk1.addPoint(new Point(getMapMode().DPtoLP(30), getMapMode().DPtoLP(33)));
			deepHistoryFigure_Asterisk1.addPoint(new Point(getMapMode().DPtoLP(20), getMapMode().DPtoLP(20)));
			deepHistoryFigure_Asterisk1.addPoint(new Point(getMapMode().DPtoLP(30), getMapMode().DPtoLP(7)));
			deepHistoryFigure_Asterisk1.addPoint(new Point(getMapMode().DPtoLP(10), getMapMode().DPtoLP(33)));
			deepHistoryFigure_Asterisk1.addPoint(new Point(getMapMode().DPtoLP(20), getMapMode().DPtoLP(20)));
			deepHistoryFigure_Asterisk1.addPoint(new Point(getMapMode().DPtoLP(40), getMapMode().DPtoLP(20)));
			deepHistoryFigure_Asterisk1.addPoint(new Point(getMapMode().DPtoLP(0), getMapMode().DPtoLP(20)));
			deepHistoryFigure_Asterisk1.addPoint(new Point(getMapMode().DPtoLP(20), getMapMode().DPtoLP(20)));

			deepHistoryFigure_AsteriskContainer0.add(deepHistoryFigure_Asterisk1);

		}

		/**
		 * @generated
		 */
		private boolean myUseLocalCoordinates = true;

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

	}

}
