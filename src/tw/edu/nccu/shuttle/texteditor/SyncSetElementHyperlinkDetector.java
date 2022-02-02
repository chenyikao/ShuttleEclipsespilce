/**
 * 
 */
package tw.edu.nccu.shuttle.texteditor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.NavigableSet;
import java.util.Set;
import java.util.TreeSet;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.TextViewer;
import org.eclipse.jface.text.hyperlink.AbstractHyperlinkDetector;
import org.eclipse.jface.text.hyperlink.IHyperlink;

import tw.edu.nccu.shuttle.MPPreprocessing;
import tw.edu.nccu.shuttle.NotYetAvailableException;
import tw.edu.nccu.shuttle.System;
import tw.edu.nccu.shuttle.rule.RuleRecommender;
import tw.edu.nccu.shuttle.texteditor.TextWordNetSyncRule.TextWordNetSyncRecommender;

/**
 * @author Kao, Chen-yi
 */
public class SyncSetElementHyperlinkDetector extends AbstractHyperlinkDetector {

	final static public int LINK_SIZE_LIMIT = 5;

	final static private Set<IDocument> ProcessedDocs = Collections.synchronizedSet(new HashSet<IDocument>());
	final static private Set<DocumentedRegion> ProcessedDrs = Collections.synchronizedSet(new HashSet<DocumentedRegion>());
	final static protected TextHover TextHover = new TextHover();
	
	
	
	final private MPPreprocessing<DocumentedRegion> prepOfText = 
			new MPPreprocessing<DocumentedRegion>(System.getDefault(), "text");
	
	
	
	/**
	 * @see org.eclipse.jface.text.hyperlink.IHyperlinkDetector#detectHyperlinks(org.eclipse.jface.text.ITextViewer, org.eclipse.jface.text.IRegion, boolean)
	 */
	public IHyperlink[] detectHyperlinks(ITextViewer textViewer, IRegion region, boolean canShowMultipleHyperlinks) {
		
		// TODO Hover setting and hyperlink recommendation should be Recommender's responsibility
		setTextHover(textViewer, region);
	
		final IRegion selection = (region.getLength() == 0) ? TextHover.getHoverRegion(textViewer, region.getOffset()) : region;
		final IDocument doc = textViewer.getDocument();
		final DocumentedRegion currentDr = new DocumentedRegion(doc, selection);
		
//		/**
//		 * To split whole workspace traversal and make this method invocation lightweight!
//		 */
//		new Job("Shuttle initialization - hooking into text files") {
//			{
//				this.setUser(true);
//				this.setPriority(Job.LONG);
//			}
//
//			@Override
//			public IStatus run(IProgressMonitor monitor) {
				
				/**
				 * At first processing the current region under detection.
				 */ 
				if (ProcessedDrs.add(currentDr)) {
					prepOfText.add(currentDr);
//					prepOfText.schedule();
				}

				/**
				 * Secondly extracting the {@link IDocument} of initial {@link ITextViewer}.
				 * 
				 * Using {@link Boundaries<Integer, IDocument>} since neither 
				 * {@link IRegion} nor {@link Position} is document-reference-able.
				 */
				if (ProcessedDocs.add(doc)) {
					DocumentBoundaries db = new DocumentBoundaries(doc);
					
//					monitor.beginTask("Constructing concept rules for the current document", db.size());
					int lastBoundary = -1, bLength;
					for (int regionBoundary : db) try {
//						monitor.worked(1);
						// in the first iteration we haven't got any region offset yet
						if (lastBoundary == -1) {lastBoundary = regionBoundary; continue;}
						if ((bLength = regionBoundary - lastBoundary) > 0 && !doc.get(lastBoundary, bLength).trim().isEmpty()) 
							prepOfText.add(new DocumentedRegion(doc, lastBoundary, bLength));
						lastBoundary = regionBoundary;
						
					} catch (BadLocationException e) {
						e.printStackTrace();	// Shall not happen!
					}
//					monitor.done();
//					prepOfText.schedule();
				}

				prepOfText.schedule();
				
				// TODO Secondly all other Workspace text/plain files
				// TODO Then all other Workspace .java files
				// TODO Then all other Workspace .htm/.html/.css files
				// TODO Then all other Workspace .c/.cpp files
				
//				return Status.OK_STATUS;
//	        }
//		}.schedule();

		try {
			NavigableSet<SyncSetElementHyperlink> links = new TreeSet<SyncSetElementHyperlink>(
					new Comparator<SyncSetElementHyperlink>() {

						/**
						 * The larger the confidence, the higher the position of recommendation item.
						 * 
						 * @param link1
						 * @param link2
						 * @return
						 * @see TextWordNetSyncRecommender#recommendationForDemo
						 */
						@Override
						public int compare(SyncSetElementHyperlink link1, SyncSetElementHyperlink link2) {
							return (int)((link1.getConfidenceLevel() - link2.getConfidenceLevel())*100);
						}
						
					});

			for (RuleRecommender<DocumentedRegion> tr : prepOfText.getTriggeredRecommenders(currentDr))
				// sort recommendations from ALL triggered recommenders;
				if (tr instanceof TextWordNetSyncRecommender)
					links.addAll(((TextWordNetSyncRecommender) tr).getRecommendation());

			List<SyncSetElementHyperlink> recommLinks = new ArrayList<SyncSetElementHyperlink>(links.descendingSet());
			recommLinks.add(0, SyncSetElementHyperlink.getTitleLink(currentDr));
			int finalRecommSize = recommLinks.size();
			TextHover.setRecommendation(recommLinks.subList(1, finalRecommSize-1));

			return recommLinks.toArray(new IHyperlink[finalRecommSize]);

		} catch (NotYetAvailableException e) {
		}
		return null;
	}



	/**
	 * @param textViewer
	 * @param region
	 */
	private void setTextHover(ITextViewer textViewer, IRegion region) {
	//		if (textViewer instanceof ISourceViewer) {
	//			ISourceViewer viewer = (ISourceViewer) textViewer;
	//			viewer.configure(new TextHoverSourceViewerConfiguration());
	//		}
		if (textViewer instanceof TextViewer) {
			TextViewer viewer = (TextViewer) textViewer;
			try {
				viewer.setTextHover(TextHover, viewer.getDocument().getContentType(region.getOffset()));
				textViewer.getTextWidget().addMouseTrackListener(TextHover);
			} catch (BadLocationException e) {
				// region's offset should be within the document
			}
	//			viewer.setHoverControlCreator(TextHoverIInformationControlCreator);
			viewer.setHoverEnrichMode(null);
		}
	}

}
