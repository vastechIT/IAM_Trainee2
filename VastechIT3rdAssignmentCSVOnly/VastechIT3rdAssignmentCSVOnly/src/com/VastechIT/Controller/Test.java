package com.VastechIT.Controller;

import java.util.Scanner;

import com.VastechIT.DataInsertion.InsertData;
import com.VastechIT.FileCreations.FileCreation;
import com.VastechIT.UpdationData.CompareCSV2;

//This is Main File for Data Transfer
public class Test {

	public static void main(String[] args) {
		System.out.println("Main Class Started");

//		File Creation 
		System.out.println("Choose a Option");
		System.out.println("Enter 1 for Create a new File and Insert the All Data to New File");
		System.out.println("Enter 2 for Insert and Update the Existing Data in Existing Table");
		Scanner t = new Scanner(System.in);
		int num = t.nextInt();
		switch (num) {
		case 1: {
//			Create a New File
			System.out.println("Start File Creation");
			FileCreation create = new FileCreation();
			create.fileCreation();
			System.out.println("Completed File Creation Stage");

//			Start Insertion from one file to Another File
			System.out.println("Start Data Insertion");
			InsertData dataInsertion = new InsertData();
			String fileName = FileCreation.fileName;
			dataInsertion.dataInsert(fileName);
			System.out.println("Completed Data Insertion");
		}
			break;
		case 2: {
			System.out.println("Main Class Ended");
			System.out.println("Start File Updation");
			CompareCSV2 compare = new CompareCSV2();
			compare.compareFile();
			System.out.println("Updation Successfully");
			System.out.println("Main Method Ended");
		}
			break;
		default: {
			System.out.println("Sorry!...You Need to Choose Right Data");
			System.out.println("Please Run Again and Try Again");
		}
		}
	}
}
