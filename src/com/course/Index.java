package com.course;


public class Index {
	
	private int indexID;
	private String courseID;
	private String tutorialGroup;
	private int vacancy;
	private int waitingList;
	
	/**
	 * Boolean attributes to indicate whether or not
	 * the index has tutorial/ lecture/ lab
	 */
	
	public Index (String courseID, int indexID, String tutorialGroup, int vacancy, int waitingList){
		this.courseID = courseID;
		this.indexID = indexID;
		this.tutorialGroup = tutorialGroup;
		this.vacancy = vacancy;
		this.waitingList = waitingList;
	}
	//Course Code
	public String getCourseID()
	{
		return courseID;
	}
	public void setCourseID(String courseID)
	{
		this.courseID = courseID;
	}
	//Index Number
	public int getIndexID()
	{
		return indexID;
	}
	public void setIndexID(int indexID)
	{
		this.indexID = indexID;
	}
	// Tutorial Group
	public String getTutorialGroup()
	{
		return tutorialGroup;
	}
	public void setTutorialGroup(String tutorialGroup)
	{
		this.tutorialGroup = tutorialGroup;
	}
	//Vacancy
	public int getVacancy()
	{
		return vacancy;
	}
	public void setVacancy(int vacancy)
	{
		this.vacancy = vacancy;
	}
	//Waiting List
	public int getWaitingList()
	{
		return waitingList;
	}
	public void setWaitingList(int waitingList)
	{
		this.waitingList = waitingList;
	}
	
	public boolean equals(int o) {
		return getIndexID() == o;
	}
	public String[] getAllDetails() {
		return new String[] {
				courseID,
				Integer.toString(indexID),
				tutorialGroup,
				Integer.toString(vacancy),
				Integer.toString(waitingList) };
	}
}