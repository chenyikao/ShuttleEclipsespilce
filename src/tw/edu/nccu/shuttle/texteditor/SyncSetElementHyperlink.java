/**
 * 
 */
package tw.edu.nccu.shuttle.texteditor;

import org.eclipse.jface.text.IRegion;
import org.eclipse.jface.text.hyperlink.IHyperlink;

/**
 * @author Kao, Chen-yi
 *
 */
public class SyncSetElementHyperlink implements IHyperlink {

	final static String CONFIDENCE_LEVEL_UNIT = "▌";
//	final static String LowConfidenceLevelUnit = "     ||";
//	final static String MidConfidenceLevelUnit = "▌▌";
//	final static String HighConfidenceLevelUnit = "██";
	final static int TOP_CONFIDENCE_LEVEL = 3;
	protected static int LongestLocationLength = 0;
	
	
	
	protected IRegion region;
	protected String eleLocation = "";
	protected String secondEleLocation = "";
	protected int locationLength;
	protected float confidenceLevel = 0;
	
	
	
	/**
	 * @param region
	 * @param eleLocation - the first {@link SyncSet} element location
	 * @param secEleLocation - the second {@link SyncSet} element location
	 * @param confLevel
	 */
	public SyncSetElementHyperlink(IRegion region, String eleLocation, String secEleLocation, float confLevel) {
		this.region = region;
		this.eleLocation = eleLocation;
		this.secondEleLocation = secEleLocation;
		this.confidenceLevel = confLevel;
		
		locationLength = eleLocation.length() + ((secEleLocation != null)?secEleLocation.length():0);
		if (locationLength > LongestLocationLength) LongestLocationLength = locationLength;
	}

	
	
	/**
	 * @return the first ME location
	 */
	public String get1stElementLocation() {
		return eleLocation;
	}

	/**
	 * @return the second ME location
	 */
	public String get2ndElementLocation() {
		return secondEleLocation;
	}

	/**
	 * @return the confidenceLevel
	 */
	public float getConfidenceLevel() {
		return confidenceLevel;
	}

	/**
	 * @see org.eclipse.jface.text.hyperlink.IHyperlink#getHyperlinkRegion()
	 */
	public IRegion getHyperlinkRegion() {
		return region;
	}

	/**
	 * @see org.eclipse.jface.text.hyperlink.IHyperlink#getTypeLabel()
	 */
	public String getTypeLabel() {
		return getHyperlinkText();
	}

	/**
	 * @see org.eclipse.jface.text.hyperlink.IHyperlink#getHyperlinkText()
	 */
	public String getHyperlinkText() {
		String text = eleLocation;
//		String cLevel = "";
		int concreteCLevel = (int)(confidenceLevel*TOP_CONFIDENCE_LEVEL);
//		switch (concreteCLevel) {		
//			case 3: cLevel = HighConfidenceLevelUnit;
//			case 2: cLevel = cLevel.concat(MidConfidenceLevelUnit);
//			case 1: cLevel = cLevel.concat(LowConfidenceLevelUnit);
//			default: text = text.concat(cLevel);
//		}
		if (concreteCLevel > 0) {
			if (secondEleLocation != null) text = text.concat(" (and " + secondEleLocation + " ...)");
			else for (int i = (LongestLocationLength + 11) - text.length(); i > 0; i--) text = text.concat(" ");
			text = text.concat("     in ");
			do {
				text = text.concat(CONFIDENCE_LEVEL_UNIT);
				concreteCLevel--; 
			} while (concreteCLevel > 0);
			text = text.concat((int)(confidenceLevel*100) + "%");
		}
		return text;
	}
	
	
	
	public static SyncSetElementHyperlink getTitleLink(IRegion detectedRegion) {
		return new SyncSetElementHyperlink(
				detectedRegion, " - " + tw.edu.nccu.shuttle.texteditor.TextHover.TITLE + " - ", null, 0);
	}

	
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		try {
			SyncSetElementHyperlink objLink = (SyncSetElementHyperlink) obj;
			return eleLocation.equals(objLink.get1stElementLocation()) 
					&& secondEleLocation.equals(objLink.get2ndElementLocation())
					&& confidenceLevel == objLink.getConfidenceLevel();

		} catch (ClassCastException e) {
			return false;
		}
	}



	/**
	 * @see org.eclipse.jface.text.hyperlink.IHyperlink#open()
	 */
	public void open() {
		// TODO Auto-generated method stub
	}

}
