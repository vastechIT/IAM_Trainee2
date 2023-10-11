package com.VastechIT.UpdationData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class CompareCSV2 {
	public void compareFile() {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter a File Name Where you want to Change");
		String file = sc.nextLine();
		String file1 = "C:\\Users\\krbip\\Downloads\\Test.csv"; // Replace with your first CSV file name
		String file2 = "C:\\Users\\krbip\\Downloads\\" + file + ".csv"; // Replace with your second CSV file name

		try {
			List<String[]> data1 = readCSV(file1);
			List<String[]> data2 = readCSV(file2);

			int[] updates = compareAndMerge(data1, data2);

			writeCSV(data2, file2);

			System.out.println("Data comparison and update complete.");
			System.out.println(updates[0] + " records inserted.");
			System.out.println(updates[1] + " records updated.");
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

	public static int[] compareAndMerge(List<String[]> data1, List<String[]> data2) {
		int recordsInserted = 0;
		int recordsUpdated = 0;

		Set<String> uniqueKeys = new HashSet<>();
		for (String[] row : data2) {
			uniqueKeys.add(row[0]); // Assuming the first column is the unique identifier
		}

		for (String[] row : data1) {
			if (uniqueKeys.contains(row[0])) {
				for (int i = 0; i < data2.size(); i++) {
					if (data2.get(i)[0].equals(row[0])) {
						data2.set(i, row);
						recordsUpdated++;
						break;
					}
				}
			} else {
				data2.add(row);
				uniqueKeys.add(row[0]);
				recordsInserted++;
			}
		}

		return new int[] { recordsInserted, recordsUpdated };
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
