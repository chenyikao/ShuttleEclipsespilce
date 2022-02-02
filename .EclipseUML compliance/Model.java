import java.util.Collection;

import java.util.Iterator;
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

public interface Model {

	/**
	 * 
	 * @uml.property name="modelElement"
	 */
	void setModelElement(java.util.Collection value);

	/**
	 * 
	 * @uml.property name="modelElement"
	 */
	Iterator modelElementIterator();

	/**
	 * 
	 * @uml.property name="modelElement"
	 * @uml.associationEnd javaType="java.util.Collection" aggregation="composite" inverse=
	 * "model:ModelElement" multiplicity="(1 -1)"
	 */
	boolean addModelElement(ModelElement element);

	/**
	 * 
	 * @uml.property name="modelElement"
	 */
	boolean removeModelElement(ModelElement element);

	/**
	 * 
	 * @uml.property name="modelElement"
	 */
	boolean isModelElementEmpty();

	/**
	 * 
	 * @uml.property name="modelElement"
	 */
	void clearModelElement();

	/**
	 * 
	 * @uml.property name="modelElement"
	 */
	boolean containsModelElement(ModelElement element);

	/**
	 * 
	 * @uml.property name="modelElement"
	 */
	boolean containsAllModelElement(Collection elements);

	/**
	 * 
	 * @uml.property name="modelElement"
	 */
	int modelElementSize();

	/**
	 * 
	 * @uml.property name="modelElement"
	 */
	ModelElement[] modelElementToArray();

}
