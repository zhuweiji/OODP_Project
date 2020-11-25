package com.user;

import java.io.*;
import java.util.*;

public class IO {
	/** Write fixed content to the given file.
	 * @param fileName
	 * @param data
	 * @throws IOException
	 */
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
		int count = 0;
		try (Scanner scanner = new Scanner(new FileInputStream(fileName))) {
			while (scanner.hasNextLine()) {
				data.add(scanner.nextLine());
				count++;
			}
		}
		if (count == 0){
			return null;
		}
		return data;
	}
}
