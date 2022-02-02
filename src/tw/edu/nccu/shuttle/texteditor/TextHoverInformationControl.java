// Author: Dan Breslau (dbreslau a t alumni dot uchicago dot edu)
// This file is released into the public domain. No rights reserved.
// Use at your own risk.

package tw.edu.nccu.shuttle.texteditor;

import org.eclipse.jface.text.AbstractInformationControl;
import org.eclipse.jface.text.IInformationControlCreator;
import org.eclipse.jface.text.IInformationControlExtension2;
import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;



/**
 * Browser-based implementation of {@link org.eclipse.jface.text.IInformationControl}.
 * <p>
 * Displays HTML in a {@link org.eclipse.swt.browser.Browser} widget.
 *
 * @author modified by Kao, Chen-yi
 */
public class TextHoverInformationControl extends AbstractInformationControl 
    implements IInformationControlExtension2    {
    
    /**
     * A wrapper used to deliver content to the hover control, either 
     * as marked-up text or as a URL.
     */
    public interface IHTMLHoverInfo {
//        /**
//         * @return true if the String returned by getHTMLString() represents a URL; 
//         * false if the String contains marked-up text.
//         */
//        public boolean isURL();

       /**
        * @return The input string to be displayed in the Browser widget
        * (either as marked-up text, or as a URL.)
        */
        public String getHTMLString();
    }

    
    
    static Color SystemBackgroundColor;

    private Browser fBrowser;
    private TextHover fHover;
//    boolean fIsURL;

    /**
     * Creates a JavaHoverInformationControl with the given shell as parent.
     *
     * @param hover		the hover model/controller
     * @param parent	the parent shell
     */
    public TextHoverInformationControl(TextHover hover, Shell parent) {
//        super(parent, (String) null);
//        super(parent, true);
        super(parent, TextHover.TITLE + "... ");
        SystemBackgroundColor = parent.getDisplay().getSystemColor(SWT.COLOR_INFO_BACKGROUND);
        fHover = hover;
        
        create();
    }


    /*
     * @see org.eclipse.jface.text.AbstractInformationControl#createContent(org.eclipse.swt.widgets.Composite)
     */
    protected void createContent(Composite parent) {

        try {
//        	fBrowser = new Browser(getShell(), SWT.NONE);
            fBrowser = new Browser(parent, SWT.NONE);
        } 
        catch (SWTError e) {
            MessageBox messageBox = new MessageBox(getShell(), SWT.ICON_ERROR | SWT.OK);
            messageBox.setMessage("Browser cannot be initialized."); //$NON-NLS-1$
            messageBox.setText("Error");                             //$NON-NLS-1$
            messageBox.open();
        }
    }
    
    /*
     * @see IInformationControl#setInformation(String)
     */
    public void setInformation(String content) {
//    	fBrowser.setBounds(getShell().getClientArea());
//        if (fIsURL) {
//            fBrowser.setUrl(content);
//        }
//        else {
            fBrowser.setText(content);
//        }
        setVisible(true);
    }


    /*
     * @see IInformationControl#computeSizeHint()
     */
    public Point computeSizeHint() {
        // see: https://bugs.eclipse.org/bugs/show_bug.cgi?id=117602

        // Note: I set the widthHint to 350 in order to get
        // better screen shots. SWT.DEFAULT works too, but
        // produces a wider hover. -- Dan Breslau
//        final int widthHint= 350;
//        return getShell().computeSize(widthHint, SWT.DEFAULT, true);

        final int DEFAULT_WIDTH = 550, DEFAULT_HEIGHT = 200;
        return getShell().computeSize(DEFAULT_WIDTH, DEFAULT_HEIGHT, true);
    }
    

    /* (non-Javadoc)
	 * @see org.eclipse.jface.text.AbstractInformationControl#setSize(int, int)
	 */
	@Override
	public void setSize(int width, int height) {
//		final Rectangle bounds = getBounds();
//		fBrowser.setBounds(bounds.x, bounds.y, width, height);
		fBrowser.setSize(width, height);
		super.setSize(width, height);
	}


	/* (non-Javadoc)
	 * @see org.eclipse.jface.text.AbstractInformationControl#setVisible(boolean)
	 */
	@Override
	public void setVisible(boolean visible) {
		if (visible) {
			Point hoverLoc = fHover.getHoverLocation();
			setLocation(new Point(hoverLoc.x + 3, hoverLoc.y + 3));
			
//	        setSizeConstraints(DEFAULT_WIDTH, DEFAULT_HEIGHT);
	        final Point size = computeSizeHint();
	        setSize(size.x, size.y);
		}
		super.setVisible(visible);
	}


	/*
     * @see IInformationControlExtension#hasContents()
     */
    public boolean hasContents() {
        return fBrowser.getText().length() > 0;
    }


    /*
     * @see org.eclipse.jface.text.IInformationControlExtension5#getInformationPresenterControlCreator()
     * @since 3.4
     */
    public IInformationControlCreator getInformationPresenterControlCreator() {
//        return new IInformationControlCreator() {
//            /*
//             * @see org.eclipse.jface.text.IInformationControlCreator#createInformationControl(org.eclipse.swt.widgets.Shell)
//             */
//            public IInformationControl createInformationControl(Shell parent) {
//                return new TextHoverInformationControl(parent);
//            }
//        };
    	return fHover;
    }


    /*
     * @see org.eclipse.jface.text#setInput()
     * The input object may be a String, an instance of IHTMLHoverInfo, or any
     * object that returns a displayable String from its toString() implementation.
     * 
     * @since 3.4
     */    
    public void setInput(Object input) {
        // Assume that the input is marked-up text, not a URL
//        fIsURL = false;
        final String inputString;

        if (input instanceof IHTMLHoverInfo) {
            // Get the input string, then see whether it's a URL
            IHTMLHoverInfo inputInfo = (IHTMLHoverInfo) input;
            inputString = inputInfo.getHTMLString();
//            fIsURL= inputInfo.isURL();
        }
        else if (input instanceof String) {
            // Treat the String as marked-up text to be displayed.
            inputString = (String) input;
        }
        else {
            // For any other kind of object, just use its string 
            // representation as text to be displayed.
            inputString = input.toString();
        }
        setInformation(inputString);
    }


}
