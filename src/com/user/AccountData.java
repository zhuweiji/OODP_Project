package com.user;

import java.io.*;
import java.text.ParseException;
import java.util.*;


public class AccountData {
	
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
	 * @param fileName
	 * @return
	 * @throws IOException
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
	
	public static ArrayList<UserAcc> accountList = new ArrayList<UserAcc>();

    /** Initialise the courses before application starts
     * @param filename
     * @throws IOException
     * @throws ParseException 
     */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static ArrayList<UserAcc> initAccounts() throws IOException {
		// read String from text file
		ArrayList<String> stringArray = (ArrayList) read("src/data/user_cred.txt");
		
		
		for (int i = 0; i < stringArray.size(); i++) {
			String st = (String) stringArray.get(i);
			// get individual 'fields' of the string separated by SEPARATOR
			// pass in the string to the string tokenizer using delimiter "|"
			StringTokenizer star = new StringTokenizer(st, SEPARATOR);

			String userid = star.nextToken().trim(); // first token
			String username = star.nextToken().trim(); // first token
			String salt = star.nextToken().trim(); // fourth token
			String hashed_pw = star.nextToken().trim(); // second token
			String hashed_permissions = star.nextToken().trim(); // second token
			// create Account object from file data
			UserAcc acc = new UserAcc(userid, username, salt, hashed_pw, hashed_permissions);
			// add to Account list
			accountList.add(acc);
		}
		return accountList;
	}
}
