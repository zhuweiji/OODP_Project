package uml;

/**
 * Each course represents a specific subject under the school
 *
 */
public class Course {

	/**
	 * Full name of the course
	 */
	private String courseName;
	/**
	 * School of the course
	 */
	private String school;
	/**
	 * Unique identifier of the course
	 */
	private String courseID;
	/**
	 * Total capacity of the course
	 */
	private int totalCapacity;
	/**
	 * Academic units of the course
	 */
	private int acadUnits;
	/**
	 * A list of index under the course
	 */

	public Course(String courseName, String school, String courseID, int totalCapacity, int acadUnits) {
		super();
		this.courseName = courseName;
		this.school = school;
		this.courseID = courseID;
		this.totalCapacity = totalCapacity;
		this.acadUnits = acadUnits;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	
	public String getSchool() {
		return school;
	}

	public void setSchool(String school) {
		this.school = school;
	}

	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}
	
	public int getTotalCapacity() {
		return totalCapacity;
	}

	public void setTotalCapacity(int totalCapacity) {
		this.totalCapacity = totalCapacity;
	}
	
	public int getAcadUnits() {
		return acadUnits;
	}

	public void setAcadUnits(int acadUnits) {
		this.acadUnits = acadUnits;
	}


	

}