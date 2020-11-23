package com.course;

import java.io.*;
import java.text.*;
import java.util.*;


public class IndexData {
	
	@SuppressWarnings("rawtypes")
	public static void write(String fileName, List data) throws IOException {
		PrintWriter out = new PrintWriter(new FileWriter(fileName));

		try {
			for (int i = 0; i < data.size(); i++) {
				out.println((String) data.get(i));
			}
		} finally {
			out.close();
		}
	}

	/** Read the contents of the given file.
	 * 

	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List read(String fileName) throws IOException {
		List data = new ArrayList();
		Scanner scanner = new Scanner(new FileInputStream(fileName));
		try {
			while (scanner.hasNextLine()) {
				data.add(scanner.nextLine());
			}
		} finally {
			scanner.close();
		}
		return data;
	}
	
	public static final String SEPARATOR = "|";
	
	public static ArrayList <Index> indexList = new ArrayList<Index>() ;
	
	/** Initialise the indexes before application starts
     */
@SuppressWarnings({ "rawtypes", "unchecked"})
	
	
	public static ArrayList<Index> initIndex() throws IOException, ParseException {
		
		// read String from text file
		ArrayList<String> stringArray = (ArrayList) read("src/data/Index.txt");
		
        for (int i = 0 ; i < stringArray.size() ; i++) {
        	
				String field = (String) stringArray.get(i);
				
				// get individual 'fields' of the string separated by SEPARATOR
				// pass in the string to the string tokenizer using delimiter "," 
				StringTokenizer tokenizer = new StringTokenizer(field, SEPARATOR);	
				
				//first to fifth tokens	
				int  indexID = Integer.parseInt(tokenizer.nextToken().trim());	
				String  courseID = tokenizer.nextToken().trim();
				String tutorialGroup = tokenizer.nextToken().trim();	
				int vacancy = Integer.parseInt(tokenizer.nextToken().trim());
				int waitingList = Integer.parseInt(tokenizer.nextToken().trim());
				
				// create Course object from file data
				Index index = new Index(indexID, courseID, tutorialGroup, vacancy, waitingList);
				// add to Courses list 
				indexList.add(index) ;
		}
		return indexList ;
	}

    /** Initialise the courses before application starts
     * @throws IOException
     * @throws ParseException 
     */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static void searchVacancy(String CourseID,int indexID)throws IOException
	{
		ArrayList<String> stringArray = (ArrayList) read("src/db/index.txt");
		for (int i = 0 ; i < stringArray.size() ; i++) {
        	
			String field = (String) stringArray.get(i);
			
			// get individual 'fields' of the string separated by SEPARATOR
			// pass in the string to the string tokenizer using delimiter "," 
			StringTokenizer tokenizer = new StringTokenizer(field, SEPARATOR);	
			
			//first to fifth tokens
			String  courseID = tokenizer.nextToken().trim();	
			int  indexID1 = Integer.parseInt(tokenizer.nextToken().trim());	
			int vacancy = Integer.parseInt(tokenizer.nextToken().trim());
			
			if(courseID.equalsIgnoreCase(CourseID))
			{
				if(indexID == indexID1)
				System.out.println("Index ID: "+indexID1+" \t Vacancies: "+vacancy);
			}
		}
	}
    /** Initialise the courses before application starts
     */
	@SuppressWarnings({ "rawtypes", "unchecked"})
		public static void showIndex(String CourseID)throws IOException
		{
			ArrayList<String> stringArray = (ArrayList) read("src/com.course/Index.txt");
			int t=0;
			for (int i = 0 ; i < stringArray.size() ; i++) {
	        	
				String field = (String) stringArray.get(i);
				
				// get individual 'fields' of the string separated by SEPARATOR
				// pass in the string to the string tokenizer using delimiter "," 
				StringTokenizer tokenizer = new StringTokenizer(field, SEPARATOR);	
				
				//first to fifth tokens
				String  indexID = tokenizer.nextToken().trim();
				String  courseID = tokenizer.nextToken().trim();	
				
				if(courseID.equalsIgnoreCase(CourseID))
				{
					
					System.out.println(t+1+") Index ID: "+indexID);
					t++;
				}
			}
	
		}
		
		/** Save the courses that has been added during the session
		 */
		public static void saveIndex(ArrayList<Index> IndexToUpdate) throws IOException {
			ArrayList <String> cl = new ArrayList<String>() ;// to store Courses data

			for (Index value : IndexToUpdate) {
				Index index = (Index) value;
				StringBuilder stringBuild = new StringBuilder();
				stringBuild.append(index.getIndexID());
				stringBuild.append(SEPARATOR);
				stringBuild.append(index.getCourseID().trim().toUpperCase());
				stringBuild.append(SEPARATOR);
				stringBuild.append(index.getTutorialGroup().trim().toUpperCase());
				stringBuild.append(SEPARATOR);
				stringBuild.append(index.getVacancy());
				stringBuild.append(SEPARATOR);
				stringBuild.append(index.getWaitingList());

				cl.add(stringBuild.toString());
			}
				write("src/com.course/Index.txt",cl);
		}
}
