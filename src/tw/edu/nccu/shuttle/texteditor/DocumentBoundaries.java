/**
 * 
 */
package tw.edu.nccu.shuttle.texteditor;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;

import tw.edu.nccu.shuttle.NLBoundaries;

/**
 * Supporting partial region boundary detection of a {@link IDocument document}.
 * 
 * @author Kao, Chen-yi
 *
 */
public class DocumentBoundaries extends NLBoundaries {

	public DocumentBoundaries(IDocument doc) {
		super(doc.get());
	}

	public DocumentBoundaries(DocumentedRegion dr) throws BadLocationException {
		super(dr.toString());
	}

}
