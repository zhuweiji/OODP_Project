package com.course;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import com.Main;
import com.user.Admin;
import com.user.UserAcc;

public class AccountData {
	public static final String SEPARATOR = ",";
	
	public static ArrayList<UserAcc> accountList = new ArrayList<UserAcc>();
	public static ArrayList<Admin> adminList = new ArrayList<>();


    /** Initialise the courses before application starts
     *
     * @throws IOException
     * @throws ParseException 
     */
	@SuppressWarnings({ "rawtypes", "unchecked"})
	public static ArrayList<UserAcc> initAccounts() throws IOException {
		// read String from text file
		ArrayList<String> stringArray = (ArrayList) IO.read(Main.usercredpath.toString());
		
		
		for (int i = 0; i < stringArray.size(); i++) {
			String st = (String) stringArray.get(i);
			// get individual 'fields' of the string separated by SEPARATOR
			// pass in the string to the string tokenizer using delimiter "|"
			StringTokenizer star = new StringTokenizer(st, SEPARATOR);

			String userid = star.nextToken().trim(); // first token
			String username = star.nextToken().trim();
			String salt = star.nextToken().trim(); // fourth token
			String hashed_pw = star.nextToken().trim(); // second token
			String hashed_permissions = star.nextToken().trim();
			// create Account object from file data
			UserAcc acc = new UserAcc(userid,username, salt, hashed_pw, hashed_permissions);
			// add to Account list
			accountList.add(acc);
		}
		return accountList;
	}

	// save new entry 
	public static void saveAccounts(ArrayList<UserAcc> al) throws IOException {
		ArrayList <String> alw = new ArrayList<String>() ;

		for (int i = 0; i < al.size(); i++) {
			UserAcc acc = (UserAcc) al.get(i);
			StringBuilder st = new StringBuilder();
			st.append(acc.getUser_id().trim());
			st.append(SEPARATOR);
			st.append(acc.getUsername().trim());
			st.append(SEPARATOR);
			st.append(acc.getSalt().trim());
			st.append(SEPARATOR);
			st.append(acc.getPassword().trim());
			st.append(SEPARATOR);
			st.append(acc.getHashedPermissions().trim());
			alw.add(st.toString());
		}
		IO.write(Main.usercredpath.toString(), alw);
	}

	public static ArrayList<Admin> initAdmins() throws IOException {
		// read String from text file
		ArrayList<String> stringArray = (ArrayList) IO.read(Main.admininfopath.toString());
		adminList.clear();

		for (int i = 0; i < stringArray.size(); i++) {
			String st = (String) stringArray.get(i);
			// get individual 'fields' of the string separated by SEPARATOR
			// pass in the string to the string tokenizer using delimiter "|"
			StringTokenizer star = new StringTokenizer(st, ",");

			String userid = star.nextToken().trim(); // first token
			String adminid = star.nextToken().trim(); // first token
			String name = star.nextToken().trim(); // fourth token
			String email = star.nextToken().trim(); // second token
			// create Account object from file data
			Admin admin = new Admin(userid, adminid, name, email);
			// add to Account list
			adminList.add(admin);
		}
		return adminList;
	}
	
}