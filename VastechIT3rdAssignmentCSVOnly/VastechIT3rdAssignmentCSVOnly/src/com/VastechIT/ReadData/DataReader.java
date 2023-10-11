package com.VastechIT.ReadData;

//Reading the Data of CSv File
import java.io.File;
import java.util.Scanner;

public class DataReader {
	public void dataReader() {
		try {
			File f = new File("C:\\Users\\krbip\\Downloads\\Test.csv");
			if (f.exists()) {
				Scanner sc = new Scanner(f);
				sc.nextLine();
				while (sc.hasNextLine()) {
					System.out.println(sc.nextLine());
					Thread.sleep(500);
				}
			} else {
				System.out.println(f + " This File is Not Existed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
