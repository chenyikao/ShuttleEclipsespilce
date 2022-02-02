/*
 * Created on 2005/2/20
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

/**
 * @author Timmy
 * 
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */

public interface ModelElement {

	/**
	 *  
	 * @uml.property name="model"
	 * @uml.associationEnd inverse="modelElement:Model" multiplicity="(1 1)"
	 */
	Model getModel();

	/**
	 *  
	 * @uml.property name="model"
	 */
	void setModel(Model model);

}
