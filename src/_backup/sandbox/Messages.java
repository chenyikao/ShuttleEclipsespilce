package tw.edu.nccu.shuttle.sandbox;

import org.eclipse.osgi.util.NLS;

public class Messages extends NLS {
	private static final String BUNDLE_NAME = "tw.edu.nccu.shuttle.sandbox.messages"; //$NON-NLS-1$
	public static String System_PluginID;
	public static String System_ProjectName;
	public static String System_WNDictFolderName;
	static {
		// initialize resource bundle
		NLS.initializeMessages(BUNDLE_NAME, Messages.class);
	}

	private Messages() {
	}
}
