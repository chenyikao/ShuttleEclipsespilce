package tw.edu.nccu.shuttle;

import org.eclipse.osgi.util.NLS;

/**
 * @author Kao, Chen-yi
 * 
 */
public class Messages extends NLS {
	
	static private final String BUNDLE_NAME = "tw.edu.nccu.shuttle.messages"; //$NON-NLS-1$
	static public String System_PluginID;
	static public String System_ProjectName;
	static public String System_WNDictFolderName;
	
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
