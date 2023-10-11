package com.VastechIT.FileCreations;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

//Create a File for Insert the Data from CSV File
public class FileCreation {
	public static String fileName = "";

	public void fileCreation() {
		Scanner scanner = new Scanner(System.in);
		// Ask file name to User
		System.out.print("Enter the file name: ");
		fileName = scanner.next();

		// Check the File Existence
		while (checkFileExistence(fileName)) {
			System.out.println("File already exists. Please enter a different file name.");
			System.out.print("Enter the Different file name: ");
			fileName = scanner.next();
		}

		// Create the file
		try {
			File file = new File("C:\\Users\\krbip\\Downloads\\" + fileName + ".csv");
			file.createNewFile();
			System.out.println("File created successfully.");
		} catch (IOException e) {
			System.out.println("An error occurred while creating the file.");
			e.printStackTrace();
		}
		scanner.close();
	}

//Check the File Existence
	public boolean checkFileExistence(String fileName) {
		File file = new File("C:\\Users\\krbip\\Downloads\\" + fileName + ".csv");
		if (file.exists()) {
			return true;
		} else {
			return false;
		}
	}
}
