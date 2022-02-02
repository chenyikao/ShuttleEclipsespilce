/*******************************************************************************
 * Copyright (c) 2000, 2005 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     IBM Corporation - initial API and implementation
 * 
 * Portions of this file were contributed by Dan Breslau (dbreslau a t alumni dot uchicago dot edu)
 * These portions are released into the public domain. No rights reserved.
 * Use at your own risk.
 *******************************************************************************/
package tw.edu.nccu.shuttle.texteditor;


import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.IInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.ITextHover;
import org.eclipse.jface.text.ITextHoverExtension;
import org.eclipse.jface.text.ITextHoverExtension2;
import org.eclipse.jface.text.ITextViewer;
import org.eclipse.jface.text.Region;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseTrackListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Shell;

/**
 * Example implementation for an <code>ITextHover</code> which hovers over Java code.
 *
 * @author modified by Kao, Chen-yi
 * 
 */
public class TextHover implements 
ITextHover, ITextHoverExtension, ITextHoverExtension2, IInformationControlCreator, MouseTrackListener {

	static final String TITLE = "Synchronizable with";
	
	
	
	private Point infoCtrlLocation;

	private List<SyncSetElementHyperlink> recomm;

    
	
	/**
	 * Method declared and deprecated in ITextHover. (Since we need to implement
	 * ITextHover, we need to implement this method.)
     * @param textViewer 
	 * @param hoverRegion 
	 * @return 
	 * @deprecated
     */
	public String getHoverInfo(ITextViewer textViewer, IRegion hoverRegion) {
		if (hoverRegion != null) {
			try {
				if (hoverRegion.getLength() > -1)
					return textViewer.getDocument().get(hoverRegion.getOffset(), hoverRegion.getLength());
			} catch (BadLocationException x) {
			}
		}
//		return JavaEditorMessages.getString("JavaTextHover.emptySelection"); //$NON-NLS-1$
		return "";
	}

	public Object getHoverInfo2(ITextViewer textViewer, IRegion hoverRegion) {

	    // Start with the string returned by the older getHoverInfo()
	    final String selection = getHoverInfo(textViewer, hoverRegion);

	    // If text is selected in the editor window, it's returned as the
	    // hover string. If no text is selected, then the returned hover is
	    // a URL pointing to www.outofwhatbox.com/blog.
	    return new TextHoverInformationControl.IHTMLHoverInfo() {
//	        public boolean isURL() {return selection.length() == 0;}
	        public String getHTMLString() {
//	            if (isURL()){
//	                return "http://www.outofwhatbox.com/blog";             //$NON-NLS-1$
//	            }
	        	//TODO: put constants into TextHoverInformationControl.IHTMLHoverInfo
	            final Color SYSTEM_BACKGROUND = TextHoverInformationControl.SystemBackgroundColor;
	            
	            String recommItemStream = new String();
				for (SyncSetElementHyperlink recommItem : recomm) {

					recommItemStream += "\n<tr><td><a href=\"about:blank\">" + recommItem.get1stElementLocation() + "</a>";
					String secEleLoc = recommItem.get2ndElementLocation();
					if (secEleLoc != null) 
						recommItemStream += "<small> ( and <a href=\"about:blank\">" + secEleLoc + "</a>...)</small>";
					recommItemStream += "</td><td/><td><img src=\"confidence_2.ico\"></td><td>" 
						+ (int)(recommItem.getConfidenceLevel()*100) + "%</td></tr>";
				
				}
	            
					//	            try {
					return "<html style=\"background-color: rgb("
					+ SYSTEM_BACKGROUND.getRed() + ", " 
					+ SYSTEM_BACKGROUND.getGreen() + ", " 
					+ SYSTEM_BACKGROUND.getBlue() + ")\">"
					+ "<head><style type=\"text/css\">td {padding-right:10px}</style>"
					+ "<base href=\"" + tw.edu.nccu.shuttle.System.getDefault().getStateLocation() + "/icons/\"/>"
//					+ new URL(tw.edu.nccu.shuttle.System.getDefault().getDescriptor().getInstallURL(), "icons/confidence_2.gif")
//					+ "file://D\\Shuttle\\1.0 (Eclipsespilce)\\icons\\confidence_2.gif"
//					+ "http://www.outofwhatbox.com/images/RichTextRedux/StandardPoorHover.jpg"
					+ "</head>"

					+ "<body><h3>" + TITLE + "&nbsp;<code>" + selection + "</code>:</h3>"
					+ "<table border=\"0\" rules=\"none\" >"
					+ recommItemStream
					
					// TODO: For elements to-be-edited/synchronized
					+ "<tr><td><a href=\"about:blank\">Unit1</a>"
					+ "<small> ( and <a href=\"about:blank\">Unit2 in File 1</a>...)</small></td>"
					+ "<td/>"
					+ "<td><img src=\"confidence_2.ico\"></td><td>30%</td></tr>"
					
					// TODO: For elements edited
					+ "<tr><td><strike style=\"color:gray\" >Unit3</strike> in <a href=\"about:blank\">File 1</a>"
					+ "<small> ( and <a href=\"about:blank\">Unit4 in File 2</a>...)</small></td>"
					+ "<td>&#x25B7;&emsp;<a href=\"about:blank\">Unit5</a></td>"
					+ "<td><img src=\"confidence_2.ico\"></td><td>30%</td></tr>"
				
					+ "</table></body></html>";
					
//				} catch (MalformedURLException e) {
//					e.printStackTrace();
//					return null;
//				}
	        }
	    };
	}

	
	/* (non-Javadoc)
	 * Method declared on ITextHover
	 */
	public IRegion getHoverRegion(ITextViewer textViewer, int offset) {
		Point selection= textViewer.getSelectedRange();
		if (selection.x <= offset && offset < selection.x + selection.y)
			return new Region(selection.x, selection.y);
		return new Region(offset, 0);
	}

	
	public Point getHoverLocation() {
		return infoCtrlLocation;
	}

	
	/**
	 * @return
	 */
	public IInformationControlCreator getHoverControlCreator() {
		return this;
	}



	public void setRecommendation(List<SyncSetElementHyperlink> recommendation) {
		this.recomm = recommendation;
	}
	

	
	public IInformationControl createInformationControl(Shell parent) {
		return new TextHoverInformationControl(this, parent);
//					return new DefaultInformationControl(parent);
	}



	public void mouseHover(MouseEvent e) {
    	infoCtrlLocation = e.display.getCursorLocation();
    }
    
    
	public void mouseEnter(MouseEvent e) {
		// Only interested in hover event
	}


	public void mouseExit(MouseEvent e) {
		// Only interested in hover event
	}

}
