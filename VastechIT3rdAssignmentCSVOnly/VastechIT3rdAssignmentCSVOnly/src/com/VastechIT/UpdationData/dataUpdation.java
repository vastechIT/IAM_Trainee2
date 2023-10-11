package com.VastechIT.UpdationData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//import com.VastechIT.FileCreations.FileCreation;
//This File Just a Rough File 
//It used for Testing the Updation Data
public class dataUpdation {
	public static void main(String[] args) {
//		String file2 = FileCreation.fileName;
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter a File Name whatever You Created Successfully");
		String file = sc.nextLine();
		String sourceFile = "C:\\Users\\krbip\\Downloads\\Test.csv"; // Replace with your source CSV file name
		String destinationFile = "C:\\Users\\krbip\\Downloads\\" + file + ".csv"; // Replace with your destination CSV
																					// file name

		try {
			List<String[]> sourceData = readCSV(sourceFile);
			List<String[]> destinationData = readCSV(destinationFile);

			List<String[]> updatedData = compareAndMerge(sourceData, destinationData);

			writeCSV(updatedData, destinationFile);

			System.out.println("Data comparison and update complete.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static List<String[]> readCSV(String filename) throws IOException {
		List<String[]> data = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
			String line;
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				data.add(values);
			}
		}
		return data;
	}

	public static List<String[]> compareAndMerge(List<String[]> sourceData, List<String[]> destinationData) {
		List<String[]> updatedData = new ArrayList<>();

		for (String[] sourceRow : sourceData) {
			boolean foundMatch = false;

			for (String[] destinationRow : destinationData) {
				if (sourceRow[0].equals(destinationRow[0])) { // Assuming the first column is the unique identifier
					destinationRow = sourceRow.clone(); // Update the data
					foundMatch = true;
					break;
				}
			}

			if (!foundMatch) {
				updatedData.add(sourceRow);
			}
		}

		// Append remaining destination rows (if any)
		for (String[] destinationRow : destinationData) {
			boolean foundMatch = false;

			for (String[] sourceRow : sourceData) {
				if (destinationRow[0].equals(sourceRow[0])) {
					foundMatch = true;
					break;
				}
			}

			if (!foundMatch) {
				updatedData.add(destinationRow);
			}
		}

		return updatedData;
	}

	public static void writeCSV(List<String[]> data, String filename) throws IOException {
		try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
			for (String[] row : data) {
				StringBuilder sb = new StringBuilder();
				for (String value : row) {
					sb.append(value).append(",");
				}
				writer.write(sb.deleteCharAt(sb.length() - 1).toString());
				writer.newLine();
			}
		}
	}
}
