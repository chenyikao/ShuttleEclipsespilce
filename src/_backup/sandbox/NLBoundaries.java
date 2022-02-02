/**
 * 
 */
package tw.edu.nccu.shuttle.sandbox;

import java.rmi.NoSuchObjectException;
import java.text.BreakIterator;
import java.util.EmptyStackException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Stack;


/**
 * Word break rules are copied and modified from BreakIteratorRules_en_US_TEST
 * since there's some '\' missing in it.
 * 
 * TODO: cooperate with all-language supporting
 * {@link com.ibm.icu.text.DictionaryBasedBreakIterator DictionaryBasedBreakIterator}
 * (currently supporting ONLY Thai. EN_US supporting via
 * {@link com.ibm.icu.dev.test.rbbi.BreakIteratorRules_en_US_TEST 
 * BreakIteratorRules_en_US_TEST}, title.brk, word.brk, word_ja.brk and 
 * word_POSIX.brk crashes and is NOT sufficient.)
 * 
 * TODO: cooperate with Eclipse JDT word rules?
 * For now we are finding NO default Rules (including one detecting upper-lower case-
 * switching) in the 
 * {@link org.eclipse.jdt.ui.text.JavaSourceViewerConfiguration#getStringScanner 
 * StringScanner} of
 * {@link org.eclipse.jdt.ui.text.JavaSourceViewerConfiguration 
 * JavaSourceViewerConfiguration}.
 * 
 * @author Kao, Chen-yi
 * 
 */
public class NLBoundaries extends Boundaries<Integer, String> {
	
	/**
	 * TODO: @aspect Internationalization
	 */
//	final static private BreakIteratorRules_en_US_TEST RULES_N_DICTIONARY =
//		new BreakIteratorRules_en_US_TEST();
	/**
	 * DictionaryBasedBreakIterator neither supports SentenceBreakRules nor overrides
	 * {@link BreakIterator#getSentenceInstance() getSentenceInstance()}.
	 */
	static private BreakIterator SENTENCE_ITERATOR = 
		BreakIterator.getSentenceInstance();
	static private CompoundWordBreakIterator WORD_ITERATOR = 
		new CompoundWordBreakIterator();
	
	
	
	public NLBoundaries(String text) {
		super(text);
		
		// TODO: delayed boundary detection?
		int sentenceStart, sentenceEnd, wordStart;
		// break sentences first!
		SENTENCE_ITERATOR.setText(host);
		sentenceStart = SENTENCE_ITERATOR.first();
		while (true) {
			sentenceEnd = SENTENCE_ITERATOR.next();
			if (sentenceEnd == BreakIterator.DONE) break;
			else {
		
				// then break (compound) words
				WORD_ITERATOR.setText(host.substring(sentenceStart, sentenceEnd));
				wordStart = WORD_ITERATOR.first();
				while (true) {
					boundaries.add(new Integer(wordStart));
					wordStart = WORD_ITERATOR.next();
					if (wordStart == BreakIterator.DONE) break;
				}
			
			}
			sentenceStart = sentenceEnd;
		}
	}
	
	
	/**
	 * <pre>
	 *   |------           Impact Segment           ------|
	 *   |-------------------------|----------------------|
	 *   LikelyChangingBoundary    LikelyChangingBoundary UnchangingBoundary
	 *   |                         |                      |
	 * --+-+--------------+--------+---+------------------+--------------
	 *   | leEditing      leEditing|   leEditing          |
	 *   i                         j                      k
	 * </pre>
	 * 
	 * From delta (set of differences) to impact segments/area. Host (text) from 
	 * null to full makes no impact to the legacy Boundary-Concepts system!
	 * (TODO: via Levenshtein Edit Distance Algorithm):
	 * (impact segment = current boundary) if...
	 * 	Case I) current boundary overlaps a difference by preceeding
	 * 		begin(current boundary) <= begin(difference) &&
	 * 		begin(difference) < begin(next boundary)
	 * 		(given there's next boundary) 
	 * 	Case II) current boundary overlaps a difference by following
	 * 		begin(current boundary) <= end(difference)
	 * 
	 * Here situation is simplified since every difference is character-wise.
	 * 
	 * @param newText
	 * @return
	 * @throws NoSuchObjectException - when there's no host text, it means there's
	 * 	no impact!
	 * @see tw.edu.nccu.shuttle.sandbox.Boundaries#getImpactAreaInHost(java.lang.Object)
	 */
	public ImpactArea<Integer, String> getImpactAreaInHost(String newText) 
		throws NoSuchObjectException {
		// check host text first
		try {
			super.getImpactAreaInHost(newText);
		} catch (UnsupportedOperationException e) {
			// Now I support Impact Area calculation!
		}
		
		// check other requirements
		//	throw NoSuchObjectException for boundaries
		Iterator<Integer> boundaryIterator = boundaries.iterator();
		if (!boundaryIterator.hasNext()) throw NO_IMPACTAREA_EXCEPTION;

		//	throw NoSuchObjectException for alignments
		Iterator<Editing<Character>> leAlignment =
			new LevenshteinCalculator().align(host, newText).iterator();
		if (!leAlignment.hasNext()) throw NO_IMPACTAREA_EXCEPTION;
		
		// Now we can do the business! 
		ImpactArea<Integer, String> impactArea = 
			new ImpactArea<Integer, String>(host);
		// Since all the iterators are checked to have next, we can advance them first
		int currentBoundary = boundaryIterator.next(); 
		int nextBoundary = boundaryIterator.next();
		Editing<Character> leEditing = leAlignment.next();

		// iterate boundaries
		boolean preBoundaryIsLikelyChanging = false;
		while (true) {
			boolean boundaryIsLikelyChanging = false;
			int leEditingPos = leEditing.getPosInX();
		
			// followingly iterate alignments	@aspect BoundaryIteration
			for (; currentBoundary <= leEditingPos && leEditingPos < nextBoundary; ) {
				Editing.Operation leOperation = leEditing.getOperation();
				/**
				 * From character difference to impact segments/area, prediction and 
				 * finally recommendation(s), where predictions are relationships b/w
				 * MPME modifications and Recommendations:
				 * 
				 * Regard a Boundary-Concepts pair as an Editing unit, then
				 * for an... 
				 * I) Substitution Edit (in the impact area):
				 * Change prediction (TODO: Substitution prediction); 
				 * II) Insertion Edit (in the impact area): 
				 * Change prediction (TODO: ?); 
				 * III) Deletion Edit (in the impact area): 
				 * Change prediction (TODO: Deletion prediction); 
				 * IV) unchanged Edit (outside the impact area): 
				 * Legacy (no) prediction (TODO: ?).
				 * 
				 * TODO: 
				 * setPrediction(Boundary-Concepts pair, RecommendationPrediction);
				 */
				if (!boundaryIsLikelyChanging)
					switch (leOperation) {
					case SUBSTITUTION :
					case INSERTION : 
					case DELETION :
						boundaryIsLikelyChanging = true;
					}

				if (leAlignment.hasNext()) {
					leEditing = leAlignment.next();
					leEditingPos = leEditing.getPosInX();
				} else break;	// no more alignments, but may be boundaries
			}
			
			// Summarize boundary character changes...
			if (boundaryIsLikelyChanging) {
				impactArea.add(
						Editing.Operation.LIKELY_CHANGING, 
						currentBoundary, currentBoundary);
				preBoundaryIsLikelyChanging = true;
			}
			//	or close an Impact Segment by a boundary operation
			else if (preBoundaryIsLikelyChanging) {
				impactArea.add(
						Editing.Operation.UNCHANGING, 
						currentBoundary, currentBoundary);
				preBoundaryIsLikelyChanging = false;
			}

			// @aspect BoundaryIteration
			if (boundaryIterator.hasNext()) {
				currentBoundary = nextBoundary;
				nextBoundary = boundaryIterator.next();
			}
			else {
				//	close an Impact Segment by a boundary operation
				if (preBoundaryIsLikelyChanging) impactArea.add(
							Editing.Operation.UNCHANGING, nextBoundary, nextBoundary);
				break;
			}
		}
		
		try {
			if (impactArea.iterator().hasNext()) return impactArea;
			else throw NO_IMPACTAREA_EXCEPTION;
		} catch (UnsupportedOperationException e) {
			throw NO_IMPACTAREA_EXCEPTION;
		}
		
	}

		    
	
	private enum TextOrientation {FROM_X_TO_Y, FROM_Y_TO_X};
	private enum EditingOrientation {
		DEL_INS_SUB, DEL_SUB_INS, INS_DEL_SUB, INS_SUB_DEL, SUB_DEL_INS, SUB_INS_DEL
	};
//	static final private IndexOutOfBoundsException OUT_OF_TABLE_EXCEPTION =
//		new IndexOutOfBoundsException();
	/**
	 * Originally copied from 
	 * http://www-128.ibm.com/developerworks/java/library/j-jazzy/
	 * 
	 * For more economical Levenshtein table, assume there're no more than 
	 * 32,767 editings b/w texts or 32,767 characters in any single string
	 * (TODO: verifying the theory b/w text characters and editings).
	 * 
	 * TODO: handle the case of consecutive invocations with the same text X and Y
	 * 
	 * TODO: consider using 
	 * org.eclipse.ui.internal.texteditor.quickdiff.compare.rangedifferencer.Levenshtein
	 * instead in the future
	 * 
	 * @author Kao, Chen-yi
	 *
	 */
	static private class LevenshteinCalculator {

		/**
		 * The semantics of an Alignment is which of an {@link Editing}. Since the
		 * Iterator of {@link java.util.Stack Stack} does NOT support stack operations
		 * (executing {@link java.util.Iterator#next() next()} as 
		 * {@link java.util.Stack#pop() pop()}) we have to write our own stack-like
		 * Iterator!
		 * TODO: re-usable one-time generated stack with performance -> 
		 * 	backup (by clone) original lightestEditings, then pop it, while make sure
		 * 	that levenshteinTable NOT dirty during stack life
		 *  
		 * With default orientation of 'x->y' and 'deletion-first'.
		 * TODO: NOT yet ready to handle other orientation combinations (partly
		 * 	for table tracing performance).
		 * 	ONLY FOR PRIVATE USE NOW!!
		 * 
		 * @author Kao, Chen-yi
		 *
		 */
		private class AlignmentIterator 
		implements Iterable<Editing<Character>>, Iterator<Editing<Character>> {

			private TextOrientation textOrientation;
			private EditingOrientation editingOrientation;

			private Stack<Editing<Character>> lightestEditings;
			private HashSet<TableCell> nonLECells;	// cells of non lightest editing
			
			AlignmentIterator() {
				this(TextOrientation.FROM_X_TO_Y, EditingOrientation.DEL_INS_SUB);
			}
			private AlignmentIterator(
					TextOrientation textOrientation, 
					EditingOrientation editingOrientation) {
				this.textOrientation = textOrientation;
				this.editingOrientation = editingOrientation;
				lightestEditings = new Stack<Editing<Character>>();
			}
			
//			public void setOrientation(Orientation orientation) {
//				this.orientation = orientation;
//				// TODO: the editing operations SHOULD be switched as well as 
//				//	the orientation if necessary
//			}
			
			private void tracingEditing() {
				nonLECells = new HashSet<TableCell>();
				try {
					isLightestEditing((short) 0, (short) 0, levenshteinTable[0][0]);
				} catch (IndexOutOfBoundsException e) {}
			}
			/**
			 * Finding the 'lightest' editing path w/ following priorities:
			 * 	0) excluding all-time non-lightest editing and leaving chances for
			 * 		relatively non-lightest editing;
			 * 	1) choosing 0-cost substitution;
			 * 	2) applying insertion/deletion/1-cost substitution orientation.
			 * 	3) choosing monotonical editing path;
			 * 
			 * @param xTrace - trace index for string x
			 * @param yTrace - trace index for string y
			 * @param traceCellCache - cache for arithmetic performance, 
			 * 	computed in advance
			 * @return that levenshteinTable[xTracking][yTracking] is 
			 * 	Lightest Editing or not
			 * @throws UnsupportedOperationException - TODO: NOT yet ready to 
			 * 	handle other orientation combinations 
			 */
			private boolean isLightestEditing(
					short xTrace, short yTrace, TableCell traceCellCache) 
				throws UnsupportedOperationException {
				
				if (xTrace == tableHeight - 1 && yTrace == tableWidth -1)
					return true;
				// now xTrace != tableHeight || yTrace != tableWidth
				
				if (nonLECells.contains(traceCellCache)) return false;
				
				// TODO: NOT yet ready to handle other orientation combinations.
				//	The following procedure is ALL for DEL_INS_SUB Orientation!
				if (editingOrientation != EditingOrientation.DEL_INS_SUB) 
					throw new UnsupportedOperationException();

				final short nextXTrace = (short) (xTrace + 1),
					nextYTrace = (short) (yTrace + 1),
					validChangeDistance = (short) (traceCellCache.distance + 1);
				TableCell nextCell, diagonalCell = null;
				short nextDistance, diagonalDistance = 0;
				boolean diagonalIsTraceable = false;
				
				// (Priority 1)
				// possibly out-of-table
				if (nextXTrace < tableHeight && nextYTrace < tableWidth) {	
					diagonalCell = levenshteinTable[nextXTrace][nextYTrace];
					diagonalDistance = diagonalCell.distance;
					if (diagonalDistance == traceCellCache.distance)
						if (isLightestEditing(nextXTrace, nextYTrace, diagonalCell)) {
							lightestEditings.push(new Editing<Character>(
									xTrace, yTrace, textX.charAt(xTrace)));
							return true;
						}
					diagonalIsTraceable = true;
				}
				
				// (Priority 2)
				// For each editing direction (neighbor cell), 
				//	glimpse relatively non lightest editing,
				// 	then deeply tracing lightest editing:
				
				//		for deletion direction -
				if (nextXTrace < tableHeight) {
					nextCell = levenshteinTable[nextXTrace][yTrace];
					nextDistance = nextCell.distance;
					if (nextDistance == validChangeDistance)						
						if (isLightestEditing(nextXTrace, yTrace, nextCell)) {
							lightestEditings.push(new Editing<Character>(
									textX.charAt(xTrace), xTrace, yTrace));
							return true;
						}
				}
				
				//		for insertion direction -
				if (nextYTrace < tableWidth) {
					nextCell = levenshteinTable[xTrace][nextYTrace];
					nextDistance = nextCell.distance;
					if (nextDistance == validChangeDistance)
						if (isLightestEditing(xTrace, nextYTrace, nextCell)) {
							lightestEditings.push(new Editing<Character>(
									xTrace, textY.charAt(yTrace), yTrace));
							return true;
						}
				}

				//		for 1-cost substitution direction -
				if (diagonalIsTraceable && diagonalDistance == validChangeDistance)
					// even the diagonal cell is traceable, 
					//	if diagonalDistance == distance of traceCellCache + 1
					//	the cell is still NOT traced 
					if (isLightestEditing(nextXTrace, nextYTrace, diagonalCell)) {
						lightestEditings.push(new Editing<Character>(
								textX.charAt(xTrace), xTrace, 
								textY.charAt(yTrace), yTrace));
						return true;
					}
				
				// now the current cell represents all-time non-lightest editing!
				nonLECells.add(traceCellCache);
				return false;
				
			}
			
			public Iterator<Editing<Character>> iterator() {
				// @aspect ProxyPattern
				//	check editing tracking first!
				//	TODO: replace lightestEditings.isEmpty() by isTraced to avoid 
				//	TRULY EMPTY EDITINGS
				if (lightestEditings.isEmpty()) tracingEditing();
				return this;
			}
			public boolean hasNext() {
				// TODO: check editing tracking first!	@aspect ProxyPattern
				// return truly LE resolving after tracing
				return !lightestEditings.isEmpty();
			}
			public Editing<Character> next() {
				// TODO: check editing tracking first!	@aspect ProxyPattern
				try {
					return lightestEditings.pop();
				} catch (EmptyStackException e) {
					throw System.NO_SUCH_ELEMENT_EXCEPTION;
				}
			}
			public void remove() throws UnsupportedOperationException {
				throw new UnsupportedOperationException();
			}

		}

		/**
		 * Objectization for table cell management, while {@link java.lang.Short 
		 * Short}'s {@link java.lang.Short#equals(Object) equals(...)} can't
		 * distinguish cells with identical distances.
		 * 
		 * @author Kao, Chen-yi
		 *
		 */
		private class TableCell {
			short distance;
//			boolean isEditingTraced = false;
			// using Stack instead of table of booleans
//			boolean isLightestEditing = false;

			/**
			 * @param distance
			 */
			protected TableCell(short distance) {
				super();
				this.distance = distance;
			}
			
			public String toString() {return new Short(distance).toString();}
		}

		private String textX;
		private String textY;
		
		private TableCell[][] levenshteinTable;
//		private Short[][] levenshteinTable;
		private short tableHeight;
		private short tableWidth;
		
		/**
		 * @param x
		 * @param y
		 * @throws UnsupportedOperationException - if there's no differences b/w
		 * 	2 strings. Though Levenshtein Algorithm doesn't exclude such situations,
		 * 	for performance we do.
		 */
		private final void initializeTable(String x, String y) 
			throws UnsupportedOperationException {
			// fast-look for equal text calculation
			if (x.equals(y)) throw new UnsupportedOperationException();

			textX = x;
			textY = y;
			tableHeight = (short) (x.length() + 1);
			tableWidth = (short) (y.length() + 1);
			levenshteinTable = new TableCell[tableHeight][tableWidth];
		}

		public final AlignmentIterator align(String x, String y)
				throws NoSuchObjectException {
			// fill the table with distances first!
			if (distance(x, y) == 0) throw new NoSuchObjectException(null);
			return new AlignmentIterator();
		}

		/**
		 * Calculates the distance between Strings x and y and builds up the table
		 * using the <b>Dynamic Programming</b> algorithm.
		 */
		public final short distance(String x, String y) {
			// build table first!
			try {initializeTable(x, y);} 
			catch (UnsupportedOperationException e) {return 0;}

			levenshteinTable[0][0] = new TableCell((short) 0);
			for (short j = 0; j < tableWidth - 1; j++) {
				levenshteinTable[0][j + 1] = new TableCell(
						(short) (levenshteinTable[0][j].distance + ins(y, j)));
			}
			for (short i = 0; i < tableHeight - 1; i++) {
				levenshteinTable[i + 1][0] = new TableCell(
						(short) (levenshteinTable[i][0].distance + del(x, i)));
				for (short j = 0; j < tableWidth - 1; j++) {
					levenshteinTable[i + 1][j + 1] = new TableCell(min(
						(short) (levenshteinTable[i][j].distance + sub(x, i, y, j)),
						(short) (levenshteinTable[i][j + 1].distance + del(x, i)),
						(short) (levenshteinTable[i + 1][j].distance + ins(y, j))));
				}
			}

			return levenshteinTable[tableHeight - 1][tableWidth - 1].distance;
		}

		private short sub(String x, short xi, String y, short yi) {
			return (short) (x.charAt(xi) == y.charAt(yi) ? 0 : 1);
		}

		private short ins(String x, short xi) {
			return (short) 1;
		}

		private short del(String x, short xi) {
			return (short) 1;
		}

		private short min(short a, short b, short c) {
			return (short) Math.min(Math.min(a, b), c);
		}
	}
	
}
