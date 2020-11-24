package com.course;

import java.io.*;
import java.text.*;
import java.util.*;

import com.user.IO;
import com.course.Index;

public class IndexData {
	public static final String SEPARATOR = "|";
	
	public static ArrayList <Index> indexList = new ArrayList<Index>() ;
	
	/** Initialise the indexess before application starts
     * @param filename
     * @throws IOException
     * @throws ParseException 
     */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	
	
	public static ArrayList<Index> initIndex() throws IOException, ParseException {
		
		// read String from text file
		ArrayList<String> stringArray = (ArrayList) IO.read("data/Index.txt");
		
        for (int i = 0 ; i < stringArray.size() ; i++) {
        	
				String field = (String) stringArray.get(i);
				
				// get individual 'fields' of the string separated by SEPARATOR
				// pass in the string to the string tokenizer using delimiter "," 
				StringTokenizer tokenizer = new StringTokenizer(field, SEPARATOR);	
				
				//first to fifth tokens
				String  courseID = tokenizer.nextToken().trim();	
				int  indexID = Integer.parseInt(tokenizer.nextToken().trim());	
				String tutorialGroup = tokenizer.nextToken().trim();	
				int vacancies = Integer.parseInt(tokenizer.nextToken().trim());
				int waitingList = Integer.parseInt(tokenizer.nextToken().trim());
				
				// create Course object from file data
				Index index = new Index(courseID, indexID, tutorialGroup, vacancies, waitingList);
				// add to Courses list 
				indexList.add(index) ;
		}
		return indexList ;
	}

    /** Initialise the courses before application starts
     * @param filename
     * @throws IOException
     * @throws ParseException 
     */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static void searchVacancy(String CourseID,int indexID)throws IOException
	{
		ArrayList<String> stringArray = (ArrayList) IO.read("data/Index.txt");
		for (int i = 0 ; i < stringArray.size() ; i++) {
        	
			String field = (String) stringArray.get(i);
			
			// get individual 'fields' of the string separated by SEPARATOR
			// pass in the string to the string tokenizer using delimiter "," 
			StringTokenizer tokenizer = new StringTokenizer(field, SEPARATOR);	
			
			//first to fifth tokens
			String  courseID = tokenizer.nextToken().trim();	
			int  indexID1 = Integer.parseInt(tokenizer.nextToken().trim());	
			int vacancies = Integer.parseInt(tokenizer.nextToken().trim());
			
			if(courseID.equalsIgnoreCase(CourseID))
			{
				if(indexID == indexID1)
				System.out.println("Index Number: "+indexID1+" \t Vacancies: "+vacancies);
			}
		}
	}
    /** Initialise the courses before application starts
     * @param filename
     * @throws IOException
     * @throws ParseException 
     */
	@SuppressWarnings({ "rawtypes", "unchecked"})
		public static void showIndex(String CourseID)throws IOException
		{
			ArrayList<String> stringArray = (ArrayList) IO.read("data/Index.txt");
			int t=0;
			for (int i = 0 ; i < stringArray.size() ; i++) {
	        	
				String field = (String) stringArray.get(i);
				
				// get individual 'fields' of the string separated by SEPARATOR
				// pass in the string to the string tokenizer using delimiter "," 
				StringTokenizer tokenizer = new StringTokenizer(field, SEPARATOR);	
				
				//first to fifth tokens
				String  courseID = tokenizer.nextToken().trim();	
				String  indexID = tokenizer.nextToken().trim();
				
				if(courseID.equalsIgnoreCase(CourseID))
				{
					
					System.out.println(t+1+") Index Number: "+indexID);
					t++;
				}
			}
	
		}
		
		/** Save the courses that has been added during the session
		 * @param CourseToUpdate
		 * @throws IOException
		 */
		public static void saveIndexes(ArrayList<Index> IndexToUpdate) throws IOException {
			ArrayList <String> cl = new ArrayList<String>() ;// to store Courses data

	        for (int i = 0 ; i < IndexToUpdate.size() ; i++) {
					Index index = (Index) IndexToUpdate.get(i);
					StringBuilder stringBuild =  new StringBuilder() ;
					stringBuild.append(index.getCourseID().trim().toUpperCase());
					stringBuild.append(SEPARATOR);
					stringBuild.append(index.getIndexID());
					stringBuild.append(SEPARATOR);
					stringBuild.append(index.getTutorialGroup());
					stringBuild.append(SEPARATOR);
					stringBuild.append(index.getVacancy());
					stringBuild.append(SEPARATOR);
					stringBuild.append(index.getWaitingList());

					cl.add(stringBuild.toString()) ;
				}
				IO.write("data/Index.txt",cl);
		}
}