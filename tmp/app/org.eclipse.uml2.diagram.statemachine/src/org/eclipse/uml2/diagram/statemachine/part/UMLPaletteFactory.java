package org.eclipse.uml2.diagram.statemachine.part;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.gef.Tool;
import org.eclipse.gef.palette.PaletteContainer;
import org.eclipse.gef.palette.PaletteDrawer;
import org.eclipse.gef.palette.PaletteGroup;
import org.eclipse.gef.palette.PaletteRoot;
import org.eclipse.gef.palette.PaletteStack;
import org.eclipse.gef.palette.ToolEntry;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeConnectionTool;
import org.eclipse.gmf.runtime.diagram.ui.tools.UnspecifiedTypeCreationTool;
import org.eclipse.uml2.diagram.statemachine.providers.UMLElementTypes;

/**
 * @generated
 */
public class UMLPaletteFactory {

	/**
	 * @generated
	 */
	public void fillPalette(PaletteRoot paletteRoot) {
		paletteRoot.add(createNodes1Group());
		paletteRoot.add(createLinks2Group());
	}

	/**
	 * Creates "Nodes" palette tool group
	 * @generated
	 */
	private PaletteContainer createNodes1Group() {
		PaletteGroup paletteContainer = new PaletteGroup(Messages.Nodes1Group_title);
		paletteContainer.add(createStateMachine1CreationTool());
		paletteContainer.add(createState2Group());
		paletteContainer.add(createRegion3CreationTool());
		paletteContainer.add(createFinalState4CreationTool());
		paletteContainer.add(createInitial5CreationTool());
		paletteContainer.add(createShallowHistory6CreationTool());
		paletteContainer.add(createDeepHistory7CreationTool());
		paletteContainer.add(createFork8CreationTool());
		paletteContainer.add(createJoin9CreationTool());
		paletteContainer.add(createJunction10CreationTool());
		paletteContainer.add(createChoice11CreationTool());
		paletteContainer.add(createTerminate12CreationTool());
		paletteContainer.add(createEntryPoint13CreationTool());
		paletteContainer.add(createExitPoint14CreationTool());
		return paletteContainer;
	}

	/**
	 * Creates "Links" palette tool group
	 * @generated
	 */
	private PaletteContainer createLinks2Group() {
		PaletteGroup paletteContainer = new PaletteGroup(Messages.Links2Group_title);
		paletteContainer.add(createTransition1CreationTool());
		return paletteContainer;
	}

	/**
	 * Creates "State" palette tool group
	 * @generated
	 */
	private PaletteContainer createState2Group() {
		PaletteStack paletteContainer = new PaletteStack(Messages.State2Group_title, null, null);
		paletteContainer.setDescription(Messages.State2Group_desc);
		paletteContainer.add(createSimpleState1CreationTool());
		paletteContainer.add(createCompositeState2CreationTool());
		paletteContainer.add(createSubmachineState3CreationTool());
		return paletteContainer;
	}

	/**
	 * @generated
	 */
	private ToolEntry createStateMachine1CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(UMLElementTypes.StateMachine_2004);
		NodeToolEntry entry = new NodeToolEntry(Messages.StateMachine1CreationTool_title, Messages.StateMachine1CreationTool_desc, types);
		entry.setSmallIcon(UMLElementTypes.getImageDescriptor(UMLElementTypes.StateMachine_2004));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createRegion3CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(2);
		types.add(UMLElementTypes.Region_3013);
		types.add(UMLElementTypes.Region_3002);
		NodeToolEntry entry = new NodeToolEntry(Messages.Region3CreationTool_title, Messages.Region3CreationTool_desc, types);
		entry.setSmallIcon(UMLElementTypes.getImageDescriptor(UMLElementTypes.Region_3013));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createFinalState4CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(UMLElementTypes.FinalState_3003);
		NodeToolEntry entry = new NodeToolEntry(Messages.FinalState4CreationTool_title, Messages.FinalState4CreationTool_desc, types);
		entry.setSmallIcon(UMLElementTypes.getImageDescriptor(UMLElementTypes.FinalState_3003));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createInitial5CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(UMLElementTypes.Pseudostate_3004);
		NodeToolEntry entry = new NodeToolEntry(Messages.Initial5CreationTool_title, Messages.Initial5CreationTool_desc, types);
		entry.setSmallIcon(UMLElementTypes.getImageDescriptor(UMLElementTypes.Pseudostate_3004));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createShallowHistory6CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(UMLElementTypes.Pseudostate_3005);
		NodeToolEntry entry = new NodeToolEntry(Messages.ShallowHistory6CreationTool_title, Messages.ShallowHistory6CreationTool_desc, types);
		entry.setSmallIcon(UMLElementTypes.getImageDescriptor(UMLElementTypes.Pseudostate_3005));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createDeepHistory7CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(UMLElementTypes.Pseudostate_3006);
		NodeToolEntry entry = new NodeToolEntry(Messages.DeepHistory7CreationTool_title, Messages.DeepHistory7CreationTool_desc, types);
		entry.setSmallIcon(UMLElementTypes.getImageDescriptor(UMLElementTypes.Pseudostate_3006));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createFork8CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(UMLElementTypes.Pseudostate_3007);
		NodeToolEntry entry = new NodeToolEntry(Messages.Fork8CreationTool_title, Messages.Fork8CreationTool_desc, types);
		entry.setSmallIcon(UMLElementTypes.getImageDescriptor(UMLElementTypes.Pseudostate_3007));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createJoin9CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(UMLElementTypes.Pseudostate_3008);
		NodeToolEntry entry = new NodeToolEntry(Messages.Join9CreationTool_title, Messages.Join9CreationTool_desc, types);
		entry.setSmallIcon(UMLElementTypes.getImageDescriptor(UMLElementTypes.Pseudostate_3008));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createJunction10CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(UMLElementTypes.Pseudostate_3009);
		NodeToolEntry entry = new NodeToolEntry(Messages.Junction10CreationTool_title, Messages.Junction10CreationTool_desc, types);
		entry.setSmallIcon(UMLElementTypes.getImageDescriptor(UMLElementTypes.Pseudostate_3009));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createChoice11CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(UMLElementTypes.Pseudostate_3010);
		NodeToolEntry entry = new NodeToolEntry(Messages.Choice11CreationTool_title, Messages.Choice11CreationTool_desc, types);
		entry.setSmallIcon(UMLElementTypes.getImageDescriptor(UMLElementTypes.Pseudostate_3010));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createTerminate12CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(UMLElementTypes.Pseudostate_3011);
		NodeToolEntry entry = new NodeToolEntry(Messages.Terminate12CreationTool_title, Messages.Terminate12CreationTool_desc, types);
		entry.setSmallIcon(UMLElementTypes.getImageDescriptor(UMLElementTypes.Pseudostate_3011));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createEntryPoint13CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(2);
		types.add(UMLElementTypes.ConnectionPointReference_3017);
		types.add(UMLElementTypes.Pseudostate_3014);
		NodeToolEntry entry = new NodeToolEntry(Messages.EntryPoint13CreationTool_title, Messages.EntryPoint13CreationTool_desc, types);
		entry.setSmallIcon(UMLDiagramEditorPlugin.findImageDescriptor("/org.eclipse.uml2.uml.edit/icons/full/obj16/Pseudostate_entryPoint.gif")); //$NON-NLS-1$
		entry.setLargeIcon(UMLDiagramEditorPlugin.findImageDescriptor("/org.eclipse.uml2.uml.edit/icons/full/obj16/Pseudostate_entryPoint.gif")); //$NON-NLS-1$
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createExitPoint14CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(2);
		types.add(UMLElementTypes.ConnectionPointReference_3018);
		types.add(UMLElementTypes.Pseudostate_3015);
		NodeToolEntry entry = new NodeToolEntry(Messages.ExitPoint14CreationTool_title, Messages.ExitPoint14CreationTool_desc, types);
		entry.setSmallIcon(UMLDiagramEditorPlugin.findImageDescriptor("/org.eclipse.uml2.uml.edit/icons/full/obj16/Pseudostate_exitPoint.gif")); //$NON-NLS-1$
		entry.setLargeIcon(UMLDiagramEditorPlugin.findImageDescriptor("/org.eclipse.uml2.uml.edit/icons/full/obj16/Pseudostate_exitPoint.gif")); //$NON-NLS-1$
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createTransition1CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(UMLElementTypes.Transition_4001);
		LinkToolEntry entry = new LinkToolEntry(Messages.Transition1CreationTool_title, Messages.Transition1CreationTool_desc, types);
		entry.setSmallIcon(UMLElementTypes.getImageDescriptor(UMLElementTypes.Transition_4001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createSimpleState1CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(UMLElementTypes.State_3001);
		NodeToolEntry entry = new NodeToolEntry(Messages.SimpleState1CreationTool_title, Messages.SimpleState1CreationTool_desc, types);
		entry.setSmallIcon(UMLElementTypes.getImageDescriptor(UMLElementTypes.State_3001));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createCompositeState2CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(UMLElementTypes.State_3012);
		NodeToolEntry entry = new NodeToolEntry(Messages.CompositeState2CreationTool_title, Messages.CompositeState2CreationTool_desc, types);
		entry.setSmallIcon(UMLElementTypes.getImageDescriptor(UMLElementTypes.State_3012));
		entry.setLargeIcon(entry.getSmallIcon());
		return entry;
	}

	/**
	 * @generated
	 */
	private ToolEntry createSubmachineState3CreationTool() {
		List/*<IElementType>*/types = new ArrayList/*<IElementType>*/(1);
		types.add(UMLElementTypes.State_3016);
		NodeToolEntry entry = new NodeToolEntry(Messages.SubmachineState3CreationTool_title, Messages.SubmachineState3CreationTool_desc, types);
		entry.setSmallIcon(UMLElementTypes.getImageDescriptor(UMLElementTypes.State_3016));
		entry.setLargeIcon(entry.getSmallIcon());
		entry.setToolClass(CreateSubmachineStateTool.class);
		return entry;
	}

	/**
	 * @generated
	 */
	private static class NodeToolEntry extends ToolEntry {

		/**
		 * @generated
		 */
		private final List elementTypes;

		/**
		 * @generated
		 */
		private NodeToolEntry(String title, String description, List elementTypes) {
			super(title, description, null, null);
			this.elementTypes = elementTypes;
		}

		/**
		 * @generated
		 */
		public Tool createToolGen() {
			Tool tool = new UnspecifiedTypeCreationTool(elementTypes);
			tool.setProperties(getToolProperties());
			return tool;
		}

		/**
		 * @generated NOT
		 */
		public Tool createTool() {
			Tool tool = super.createTool();
			if (tool != null) {
				return tool;
			}
			return createToolGen();
		}
	}

	/**
	 * @generated
	 */
	private static class LinkToolEntry extends ToolEntry {

		/**
		 * @generated
		 */
		private final List relationshipTypes;

		/**
		 * @generated
		 */
		private LinkToolEntry(String title, String description, List relationshipTypes) {
			super(title, description, null, null);
			this.relationshipTypes = relationshipTypes;
		}

		/**
		 * @generated
		 */
		public Tool createTool() {
			Tool tool = new UnspecifiedTypeConnectionTool(relationshipTypes);
			tool.setProperties(getToolProperties());
			return tool;
		}
	}
}
