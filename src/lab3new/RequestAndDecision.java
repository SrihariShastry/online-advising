//Name: Srihari Shasty
//UTA ID: 1001662267
package lab3new;

import java.io.Serializable;

public class RequestAndDecision implements Serializable{

	/**
	 * A structure for Requests
	 */
	private static final long serialVersionUID = 1L;
	private String studentName;			// Name of the student requesting 
	private String course;				// Name of the course Student is requesting 
	private String decision;			// Advisor's decision for the said request
	
	
	public RequestAndDecision() {
		this.studentName = "";
		this.course ="";
		this.decision = "";
	}
	
	public void setRequest(String studentName,String course) {
		this.studentName = studentName;
		this.course = course;
	}
	
	public void setDecision(String decision) {
		this.decision = decision;
	}

	public String getStudentName() {
		return studentName;
	}

	public String getCourse() {
		return course;
	}

	public String getDecision() {
		return decision;
	}
}
