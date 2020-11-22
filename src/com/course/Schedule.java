package com.course;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * Each schedule represents a class schedule for an index
 *
 */
public class Schedule implements Serializable {

	/**
	 * Venue for different type of lesson
	 */
	private String venuelec, venuetut, venuelab;
	private String indexid;
	private Date startlab;
	private Date startlect;
	private Date starttut;
	private Date endlab;
	private Date endlec;
	private Date endtut;
	/**
	 * Each schedule contain 3 type of lesson, namely Lab, Tutorial, and Lecture
	 */
	private Lesson Lab,Tutorial,Lecture;
	/**
	 * Day of the week of the lesson
	 */
	private int daylab,daylect,daytut;
	
	private static int venuetutcounter = 1;
	private static int venuelabcounter = 2;
	private static int venuelectcounter = 3;
	
	public Date getStartlab() {
		return startlab;
	}
	public Date getStartlect() {
		return startlect;
	}
	public Date getStarttut() {
		return starttut;
	}
	/**
	 * Constructor to add a schedule to an index
	 * @param daylab
	 * @param daylect
	 * @param daytut
	 * @param _lab
	 * @param _lect
	 * @param _tut
	 * @param indexid
	 */
	public Schedule(int daylab, int daylect, int daytut, Date _lab, Date _lect, Date _tut, String indexid)
	{
		this.daylab = daylab;
		this.daylect = daylect;
		this.daytut = daytut;
		this.startlab = _lab;
		this.startlect =_lect;
		this.starttut = _tut;
		
		endlab = new Date(startlab.getTime() + 7200000);
		endlec = new Date(startlect.getTime() + 3600000);
		endtut = new Date(starttut.getTime() + 3600000);
		
		venuetut = "TR" + venuetutcounter++ + " ";

		venuelab = "LAB" + venuelabcounter++;

		venuelec = "LT" + venuelectcounter++ +" ";
		
		Lab = new Lesson(daylab,startlab,endlab, venuelab,indexid);
		Tutorial = new Lesson(daytut,starttut,endtut, venuetut,indexid);
		Lecture = new Lesson(daylect,startlect,endlec, venuelec,indexid); 

	}
	
	public Lesson getLab() {
		return lab;
	}
	
	public void setLab(Lesson lab) {
		this.lab = lab;
	}
	
	public Lesson getTut() {
		return tut;
	}
	
	public void setTut(Lesson tut) {
		this.tut = tut;
	}
	
	public Lesson getLect() {
		return lect;
	}
	
	public void setLect(Lesson lect) {
		this.lect = lect;
	}
	
	public String getIndexID()
	{
		return indexid;
	}
	
	public String getVennueTut()
	{
		return this.venuetut;
	}
	
	public String getVennueLect()
	{
		return this.venuelec;
	}
	
	public String getVennueLab()
	{
		return this.venuelab;
	}
	/**
	 * check timing conflict between different type of lesson
	 * @param sch
	 * @return
	 */
	public boolean checkConflict(Schedule sch){
		
		if(IsConflict(sch.daylab, sch.startlab, sch.endlab , startlab, endlab)){
			System.out.println("Lab crash");
			return true;
		}

		if(IsConflict(sch.daylab, sch.startlab, sch.endlab, starttut, endtut)){
			System.out.println("Lab crash");
			return true;
		}

		if(IsConflict(sch.daylab, sch.startlab, sch.endlab, startlect ,endlec)){
			System.out.println("Lab crash");
			return true;
		}

		if(IsConflict(sch.daylect, sch.startlect , sch.endlec , startlab, endlab)){
			System.out.println("Lect crash");
			return true;
		}
		if(IsConflict(sch.daylect, sch.startlect,sch.endlec,starttut,endtut)){
			System.out.println("Lect crash");
			return true;
		}
		if(IsConflict(sch.daylect, sch.startlect,sch.endlec,startlect,endlec)){
			System.out.println("Lect crash");
			return true;
		}
		if(IsConflict(sch.daytut, sch.starttut,sch.endtut,startlab,endlab)){
			System.out.println("Lect tut");
			return true;
		}
		if(IsConflict(sch.daytut, sch.starttut,sch.endtut,starttut,endtut)){
			System.out.println("Lect tut");
			return true;
		}
		if(IsConflict(sch.daytut, sch.starttut,sch.endtut,startlab,endlab)){
			System.out.println("Lect tut");
			return true;
		}
		return false;
		
	}
	
	/**
	 * check if two dates are in conflict
	 * @param daycheck
	 * @param s1
	 * @param e1
	 * @param s2
	 * @param e2
	 * @return
	 */
	public boolean IsConflict(int daycheck,Date s1, Date e1, Date s2, Date e2) // true if conflict
	{
		
		if(daylab!=daycheck &&daylect!=daycheck &&daytut!=daycheck){
			return false;
		}
		
		
		
		if (s1.getTime() == s2.getTime()) {
			return true;
		}

		if (s2.getTime() > s1.getTime()) {
			if (s2.getTime() < e1.getTime()) {
				return true;
			}
		}

		if (s2.getTime() < s1.getTime()) {
			if (e2.getTime() < e1.getTime()) {
				return true;
			}
		}
		return false;
	}
	
	
	public String printInfo() {
		
		DateFormat df = new SimpleDateFormat("HH:mm");
		String slabday ="";
		String stutday ="";
		String slectday ="";
		switch (daylab) {
		case 1:
			slabday = "MON";
			break;
		case 2:
			slabday = "TUE";
			break;
		case 3:
			slabday = "WED";
			break;
		case 4:
			slabday = "THU";
			break;
		case 5:
			slabday = "FRI";
			break;
		default:
			break;
		}
		
		switch (daytut) {
		case 1:
			stutday = "MON";
			break;
		case 2:
			stutday = "TUE";
			break;
		case 3:
			stutday = "WED";
			break;
		case 4:
			stutday = "THU";
			break;
		case 5:
			stutday = "FRI";
			break;
		default:
			break;
		}
		
		switch (daylect) {
		case 1:
			slectday = "MON";
			break;
		case 2:
			slectday = "TUE";
			break;
		case 3:
			slectday = "WED";
			break;
		case 4:
			slectday = "THU";
			break;
		case 5:
			slectday = "FRI";
			break;
		default:
			break;
		}
		
		return "Lab: " + slabday + " " + df.format(startlab) + "-" + df.format(endlab) + " " + 
				"Lec: " + slectday + " " + df.format(startlect) + "-" + df.format(endlec) + " " + 
				"Tut: " + stutday + " " + df.format(starttut) + "-" + df.format(endtut);
	}
	
	
}
