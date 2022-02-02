/**
 * 
 */
package tw.edu.nccu.shuttle.texteditor;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.Region;

/**
 * @author Kao, Chen-yi
 *
 */
public class DocumentedRegion extends Region {

	private IDocument doc;

	/**
	 * @param doc
	 * @param offset
	 * @param length
	 */
	public DocumentedRegion(IDocument document, int offset, int length) {
		super(offset, length);
		if (document == null) throw tw.edu.nccu.shuttle.System.NULL_ARGUMENT_EXCEPTION;
		
		doc = document;
	}

	public DocumentedRegion(IDocument document, IRegion region) {
		this(document, region.getOffset(), region.getLength());
	}



	public IDocument getDocument() {
		return doc;
	}

	/**
	 * @see org.eclipse.jface.text.Region#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object o) {
		try {
			return doc.equals(((DocumentedRegion) o).getDocument()) && super.equals(o);
		} catch (ClassCastException e) {
			return false;
		}
	}

	/**
	 * @see org.eclipse.jface.text.Region#toString()
	 */
	@Override
	public String toString() {
		try {
			return doc.get(getOffset(), getLength());
		} catch (BadLocationException e) {
			return "Bad location - [" + super.toString() + "]";
		}
	}

}
