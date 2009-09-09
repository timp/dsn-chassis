/**
 * 
 */
package org.cggh.chassis.generic.atom.submission.client.protocol;

import java.util.HashSet;
import java.util.Set;

/**
 * @author aliman
 *
 */
public class SubmissionQuery {

	public enum Field { AUTHOR_EMAIL, STUDY_URL };
	
	private Set<Clause> clauses = new HashSet<Clause>();
	
	private class Clause {
		private String match;
		private Field field;
		private Clause(Field field, String match) {
			this.field = field; this.match = match;
		}
	}
	
	public void addClause(Field field, String match) {
		this.clauses.add(new Clause(field, match));
	}
	
}
