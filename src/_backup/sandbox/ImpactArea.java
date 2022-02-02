/**
 * 
 */
package tw.edu.nccu.shuttle.sandbox;

/**
 * Subject (text) Impact Segment is a 'set of boundaries' that constitute 
 * the difference b/w new and original host. 
 * 
 * Subject (text) Impact Area is a 'set of Impact Segments'. 
 * Hence the impact area includes the boundaries
 * belonged to either original or new host.
 * 
 * @author Kao, Chen-yi
 *
 */
public class ImpactArea<Subject, SubjectHost> 
	extends Boundaries<Editing<Subject>, SubjectHost> {

	public ImpactArea(SubjectHost host) {
		super(host);
		// TODO Auto-generated constructor stub
	}
	
	public void add(Editing.Operation operation, Subject subject, int position) 
		throws UnsupportedOperationException {
		switch (operation) {
		case CHANGING :
		case LIKELY_CHANGING :
		case UNCHANGING :
			boundaries.add(new Editing<Subject>(operation, subject, position));
			return;
		default :
			throw NOT_SUPPORTED_EXCEPTION;
		}
	}

}
