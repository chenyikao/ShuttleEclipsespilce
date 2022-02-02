package tw.edu.nccu.shuttle;
import java.text.BreakIterator;
import java.text.CharacterIterator;
import java.util.NoSuchElementException;


/**
 * Compound-Word BreakIterator is a Word-break iterator wrapper that detects
 * upper-lower case-switching and underscore('_')-letter switching. Meeting
 * underscore-to-letter, letter-to-underscore, lower-to-upper, and
 * upper-to-lower (acronym end) trigger CompoundEnd.
 * 
 * TODO: This class is <i>TEMPORARY, FOR INTERNAL USE ONLY</i>, handling
 * forward boundary detection ONLY and hopefully replaced by ICU
 * Dictionary-Based Break Iterator in the future.
 *
 * TODO: caching boundaries for the performance under identical text settings,
 * if necessary.
 * 
 * @author Kao, Chen-yi
 * 
 */
class CompoundWordBreakIterator extends BreakIterator {

	final private BreakIterator WORD_BREAK_ITERATOR = BreakIterator.getWordInstance();
	
	
	
	private String subjectText = null;
	
	private int charPos;
	private int nextWordBoundary;
	/**
	 * For underscore-to-letter and letter-to-underscore detection.
	 */
	private boolean isCompoundSegment;
	/**
	 * For lower-to-upper and upper-to-lower detection.
	 */
	private boolean isSegmentUpperCase;
	
	
	
	@Override
	public int current() {
		return WORD_BREAK_ITERATOR.current();
	}

	/**
	 * Iteration initialization.
	 * 
	 * @see java.text.BreakIterator#first()
	 */
	@Override
	public int first() {
		int firstWordBoundary = WORD_BREAK_ITERATOR.first();
		charPos = firstWordBoundary;	// reset character iteration position

		// detect next internal word boundary in advance
		nextWordBoundary = WORD_BREAK_ITERATOR.next();
		isCompoundSegment = false;
		isSegmentUpperCase = false;
		
		try {
			int firstSegmentBoundary = nextSegment();
			return (firstSegmentBoundary > firstWordBoundary) ?	firstSegmentBoundary : firstWordBoundary ;
		} catch (NoSuchElementException e) {	// Shall not happen!
			e.printStackTrace();
			return -1;
		}
	}

	@Override
	public int following(int arg0) {
		return WORD_BREAK_ITERATOR.following(arg0);
	}

	@Override
	public CharacterIterator getText() {
		return WORD_BREAK_ITERATOR.getText();
	}

	@Override
	public int last() {
		return WORD_BREAK_ITERATOR.last();
	}

	/**
	 * Meeting underscore-to-letter, letter-to-underscore, lower-to-upper, and
	 * upper-to-lower (acronym end) trigger CompoundEnd.
	 * 
	 * @see java.text.BreakIterator#next()
	 */
	@Override
	public int next() {
//		if (subjectText == null) return BreakIterator.DONE;

		try {
			return nextSegment();
		} catch (NoSuchElementException e) {
			// time to shift the internal word boundary
			nextWordBoundary = WORD_BREAK_ITERATOR.next();
			isCompoundSegment = false;
			isSegmentUpperCase = false;
			return nextSegment();
		}
		
	}

	@Override
	public int next(int arg0) {
		return WORD_BREAK_ITERATOR.next(arg0);
	}

	private int nextSegment() throws NoSuchElementException {
		if (charPos == nextWordBoundary) throw new NoSuchElementException(
				"Segment iteration of ..." 
				+ subjectText.substring((nextWordBoundary < 10)?0:nextWordBoundary - 10, nextWordBoundary) 
				+ " ends.");
		
		for (; charPos < nextWordBoundary; charPos++) {
			char curChar = subjectText.charAt(charPos);
			// meeting lower-to-upper switching:
			// meeting upper-to-lower (acronym end) switching:
			if (Character.isUpperCase(curChar)) {
				isSegmentUpperCase = true;
				isCompoundSegment = true;
			}
			if (Character.isLowerCase(curChar) && isSegmentUpperCase) {
				isSegmentUpperCase = false;
				isCompoundSegment = true;
				// pass traversed position before return
				charPos++; return charPos - 2;	
			}
			// meeting letter-to-underscore switching:
			// meeting underscore-letter switching:
			if (curChar == '_') {
				if (isCompoundSegment) {	// for "...XXX__..." situation
					isCompoundSegment = false;
					charPos++; return charPos - 1;
				}
			} else {
				if (!isCompoundSegment) {	// for "__XXX..." situation
					isCompoundSegment = true;
					charPos++; return charPos - 1;
				}
			}
		}
		
		// if there's no more segments in this compound, truly break the word
		return nextWordBoundary;
	}

	@Override
	public int previous() {
		return WORD_BREAK_ITERATOR.previous();
	}

	/* (non-Javadoc)
	 * @see java.text.BreakIterator#setText(java.lang.String)
	 */
	@Override
	public void setText(String newText) {
//		super.setText(newText);
		
		// TODO: caching previous detected boundaries if the new text is the same - 
		//	if (newText != null && !newText.equals(subjectText)) 
		subjectText = new String(newText);

		// reset iterator if a new text comes
		WORD_BREAK_ITERATOR.setText(newText);
		first();
	}

	@Override
	public void setText(CharacterIterator arg0) {
		WORD_BREAK_ITERATOR.setText(arg0);
	}

}