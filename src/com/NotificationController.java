package com;

import java.io.IOException;
import java.text.ParseException;
import java.util.*;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.MimeMessage;

import com.course.*;
import com.user.Student;
import com.lesson.Lesson;

public class NotificationController {
	
	public static ArrayList<Course> courseList = CourseData.courseList;
	public static ArrayList<Index> indexList = IndexData.indexList;
	public static ArrayList<StudentCourse> studentCourseList = StudentCourseData.studentCourseList;
	public static ArrayList<Lesson> lessonList = LessonData.lessonList;
	public static ArrayList<Student> studentList = StudentData.studentList;
	
	//Student
	public static ArrayList<Student> getStudents(){ return studentList; }
	
	// Lesson
	public static ArrayList<Lesson> getLesson(){ return lessonList; }
	
	// Course
	public static ArrayList<Course> getCourse(){ return courseList; }
		
	// Index
	public static ArrayList<Index> getIndex(){ return indexList; }
	
	//StudentCourse
	public static ArrayList<StudentCourse> getStudentCourses(){ return studentCourseList; }
	
	public static void writeObject(Object newObj) throws IOException{
		if (newObj instanceof Course){
			courseList.add((Course) newObj);
			CourseData.saveCourses(courseList);
		}
		else if (newObj instanceof Index){
			indexList.add((Index) newObj);
			IndexData.saveIndexes(indexList);
		}
		else if (newObj instanceof StudentCourse){
			studentCourseList.add((StudentCourse) newObj);
			StudentCourseData.saveStudentCourses(studentCourseList);
		}
	}
	
	public static void main (String[] args)
	{
		sendEmail(null, null);
	}

	/**
	 * Send both email and SMS for wait list notification
	 */
	public static void sendBoth (Student studentToNotify, String courseID)
	{
		sendEmail(studentToNotify, courseID);
	    sendSMS(studentToNotify);
	}
	
	/**
	 * Send email for wait list notification
	 */
	public static void sendEmail(Student studentToNotify, String courseID)
	{
		//String email = studentToNotify.getEmail();
		String email = "bliu008@e.ntu.edu.sg";
		
		Properties props = new Properties();
	    props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");
		
		Authenticator auth = new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				//email and password of smtp server
				return new PasswordAuthentication("cz2002.fsp2.group5@gmail.com","cz2002@fsp2group5");
			}
		};

	    Session session = Session.getDefaultInstance(props, auth);
	    
	    try {
	        MimeMessage msg = new MimeMessage(session);
	        msg.setFrom("cz2002.fsp2.group5@gmail.com");
	        msg.setRecipients(Message.RecipientType.TO,email);
	        msg.setSubject("Waitlist Notification");
	        //msg.setText("You have been registered to " + courseCode);
	        msg.setText("You have been registered to 777");
	        Transport.send(msg);
	    } catch (MessagingException mex) {
	        System.out.println("send failed, exception: " + mex);
	    }
	}
	
	/**
	 * Send SMS for wait list notification
	 * @param studentToNotify
	 */
	public static void sendSMS(Student studentToNotify)
	{
		String mobileNo = studentToNotify.getPhone_number();
		System.out.println("A SMS is sent to " + mobileNo);
	}
	
	public static void sendAlertWaitlist(int indexNumber) throws IOException, ParseException{
		ArrayList<Index> indexList = getIndex();
		ArrayList<StudentCourse> studentCourseList = getStudentCourses();
		ArrayList<Student> studentList = getStudents();
		
		for(Index i: indexList){
			if (i.getVacancy() > 0 && i.getWaitingList() > 0){
				for(StudentCourse sc : studentCourseList){
					if ((sc.getIndexID() == indexNumber) && sc.getRegisterStatus().equals("On Waiting List")){
						for (Student s : studentList){
							if (s.getUsername().equals(sc.getUsername())){
								int vacancy = i.getVacancy();
								int waitingList = i.getWaitingList();
								
								if (vacancy > 0){
									// save to studentCourses.txt
									studentCourseList.remove(sc);
									StudentCourse newSc = new StudentCourse(sc.getUserid(),sc.getUsername(), sc.getCourseID(), indexNumber, "Registered");
									writeObject(newSc);
									
									// decrement vacancy and waitinglist by 1
									vacancy--;
									waitingList--;
									
									// save to indexes.txt
									indexList.remove(i);
									Index newIndex = new Index(indexNumber, i.getCourseID(), i.getTutorialGroup(), vacancy, waitingList);
									writeObject(newIndex);

									// Set new updated vacancy and waitinglist
									i.setVacancy(vacancy);
									i.setWaitingList(waitingList);

									// Sending Fake SMS
									// Sending Actual Email
									switch (s.getNotiMode()) {
										case "SMS" -> sendSMS(s);
										case "Email" -> sendEmail(s, i.getCourseID());
										case "Both" -> sendBoth(s, i.getCourseID());
									}
								}
								else{
									return;
								}
							}
						}
					}
				}
			}
		}
	}
}