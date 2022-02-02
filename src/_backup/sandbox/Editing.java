/**
 * 
 */
package tw.edu.nccu.shuttle.sandbox;

/**
 * The semantics of an Editing: 
 * <pre>
 * subjectInX.subsequence(0, posInX) + operation = subjectInY.subsequence(0, posInY)
 * </pre>
 * 
 * TODO: cooperate with Eclipse RangeDifference/IComparator
 * TODO: limit the editable stream length to {@link java.lang.Short Short}
 * 	rather than {@likn java.lang.Integer Integer}-capable?
 * 
 * @author Kao, Chen-yi
 *
 * @param <Subject> the type of editing subject
 */
public class Editing<Subject> {

	/**
	 * TODO: CHANGING == SUBSTITUTION | DELETION | INSERTION
	 * 
	 * Currently there's NO enforced semantic connection b/w CHANGE,
	 * SUBSTITUTION, DELETION and INSERTION operations.
	 * 
	 * @author Kao, Chen-yi
	 * 
	 */
	public enum Operation {
		CHANGING, LIKELY_CHANGING, UNCHANGING, 
		SUBSTITUTION, ZERO_SUBSTITUTION, DELETION, INSERTION
	}

	private Operation operation;
	private Subject subjectInX; 
	private int posInX; 
	private Subject subjectInY;
	private int posInY; 
	static final private UnsupportedOperationException NO_INFORMATION_EXCEPTION = 
		new UnsupportedOperationException(); 
	/**
	 * @param operation
	 * @param subjectInX
	 * @param posInX
	 * @param subjectInY
	 * @param posInY
	 */
	private Editing(
			Operation operation, Subject subjectInX, int posInX, 
			Subject subjectInY, int posInY) {
		super();
		this.subjectInX = subjectInX;
		this.posInX = posInX;
		this.subjectInY = subjectInY;
		this.posInY = posInY;
		this.operation = operation;
	}
	public Editing(Subject subjectInX, int posInX, Subject subjectInY, int posInY) {
		this(Operation.SUBSTITUTION, subjectInX, posInX, subjectInY, posInY);
	}
	public Editing(Subject subjectInX, int posInX, int posInY) {
		this(Operation.DELETION, subjectInX, posInX, null, posInY);
	}
	public Editing(int posInX, Subject subjectInY, int posInY) {
		this(Operation.INSERTION, null, posInX, subjectInY, posInY);
	}
	public Editing(int posInX, int posInY, Subject subjectInXY) {
		this(Operation.ZERO_SUBSTITUTION, subjectInXY, posInX, subjectInXY, posInY);
	}
	/**
	 * Representing a source-information-revealing only editing.
	 * 
	 * @param operation
	 * @param subjectInX - subject (character) in X editing host (text)
	 * @param posInX - the corresponding position of subject
	 */
	public Editing(Operation operation, Subject subjectInX, int posInX) {
		this(operation, subjectInX, posInX, null, -1);
	}
	/**
	 * @return the subjectInX
	 */
	public Subject getSubjectInX() throws UnsupportedOperationException {
		if (subjectInX == null) throw NO_INFORMATION_EXCEPTION;
		return subjectInX;
	}
	public int getPosInX() throws UnsupportedOperationException {
		if (posInX <= -1) throw NO_INFORMATION_EXCEPTION;
		return posInX;
	}
	/**
	 * @return the subjectInY
	 */
	public Subject getSubjectInY() throws UnsupportedOperationException {
		if (subjectInY == null) throw NO_INFORMATION_EXCEPTION;
		return subjectInY;
	}
	public int getPosInY() throws UnsupportedOperationException {
		if (posInY <= -1) throw NO_INFORMATION_EXCEPTION;
		return posInY;
	}
	/**
	 * @return the operation
	 */
	public Operation getOperation() {
		return operation;
	}
}

