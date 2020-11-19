package uml;


/**
 * Each index represents a specific group under a course
 *
 */
public class Index {
	/**
	 * Unique identifier of a specific index
	 */
	private int indexID;

	/**
	 * Unique identifier of a course that the index belongs to
	 */
	private String  courseID;
	/**
	 * Tutorial Group
	 */
	private String tutorialGroup;
	/**
	 * Available slots of the index
	 */

	private int vacancy;
	/**
	 * List of students who are under the waiting list for the index
	 */
	private int waitingList;

	public Index(int indexID, String courseID, String tutorialGroup, int vacancy, int waitingList) {
		super();
		this.indexID = indexID;
		this.courseID = courseID;
		this.tutorialGroup = tutorialGroup;
		this.vacancy = vacancy;
		this.waitingList = waitingList;
	}

	
	public int getIndexID() {
		return indexID;
	}

	public void setIndexID(int indexID) {
		this.indexID = indexID;
	}
	
	public String getCourseID() {
		return courseID;
	}

	public void setCourseID(String courseID) {
		this.courseID = courseID;
	}

	public String getTutorialGroup() {
		return tutorialGroup;
	}

	public void setTutorialGroup(String tutorialGroup) {
		this.tutorialGroup = tutorialGroup;
	}
	
	public int getVacancy() {
		return vacancy;
	}

	public void setVacancy(int vacancy) {
		this.vacancy = vacancy;
	}

	public int getWaitingList() {
		return waitingList;
	}

	public void setWaitingList(int waitingList) {
		this.waitingList = waitingList;
	}
	
}