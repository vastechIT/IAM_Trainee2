package com.VastechIT.DataInsertion;

import java.io.BufferedWriter;
//import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.util.Scanner;

public class InsertData {
	public void dataInsert(String fileName) {
		try {
//			The File from where we read the data
			File file1 = new File("C:\\Users\\krbip\\Downloads\\Test.csv");

			// Insert the Data from First File to Second File
			FileWriter writer = new FileWriter("C:\\Users\\krbip\\Downloads\\" + fileName + ".csv");
			BufferedWriter bw = new BufferedWriter(writer);
			Scanner t = new Scanner(file1);
			int count = 0;
			if (file1.canRead()) {
				while (t.hasNextLine()) {
					System.out.println(++count + "th Row Data Insertion Start");
					bw.write(t.nextLine());
					bw.newLine();
					System.out.println("Data Insertion Completed");
				}
				bw.close();
				System.out.println("File Writing Data Sucessfull");
			} else {
				System.out.println("File is Not Readable");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
