package tw.edu.nccu.shuttle.sandbox;
import org.eclipse.jdt.ui.JavaUI;
import org.eclipse.jdt.ui.text.JavaSourceViewerConfiguration;
import org.eclipse.jface.preference.JFacePreferences;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.RuleBasedScanner;
//import org.eclipse.ui.editors.text.EditorsUI;


class ShuttleWordConf extends JavaSourceViewerConfiguration {
	
	public ShuttleWordConf() {
		super(
				JavaUI.getColorManager(), 
				JFacePreferences.getPreferenceStore(), 
//				EditorsUI.getPreferenceStore(), 
				null, null);
	}

	
	
	/* (non-Javadoc)
	 * @see org.eclipse.jdt.ui.text.JavaSourceViewerConfiguration#getStringScanner()
	 */
	@Override
	public RuleBasedScanner getStringScanner() {
		return super.getStringScanner();
	}

}