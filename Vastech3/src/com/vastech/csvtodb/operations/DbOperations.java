package com.vastech.csvtodb.operations;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.vastech.csvtodb.constants.Constants;

public class DbOperations {

	//Connection started
	public static Connection initDbConnection() throws ClassNotFoundException {
		Connection con = null;
	try {
	Class.forName(Constants.driver);
	con = DriverManager.getConnection(Constants.url,Constants.username,Constants.password);
	}
	catch(Exception e) {
	e.printStackTrace();
	}
		return con;
     }
	
	//If table not exists,then table is created by reading csv headers and csvfile data is inserted....
	public static void insertionAndUpdation(Connection connection,String csvFilePath)  {
		try {
		File f = new File(csvFilePath);
		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);
		 String header = br.readLine();
//         System.out.println(header);
         String[] str=new String[header.length()] ;
         String[] str1=new String[header.length()] ;
			if (header != null) {
         	
             String[] columns = header.split(",");
             for(int i=0;i<columns.length;i++) {
            //Reading csvFile headers 
             str[i]=columns[i]+" varchar(45)";
             str1[i]=columns[i];
             }
         }
			    //platform creation
			    Statement statement = connection.createStatement();
                // Check if the table already exists
			    Scanner sc = new Scanner(System.in);
				System.out.println("Enter table name : ");
				String tableName = sc.nextLine();
				 boolean tableExists = false;
 	            try {
 	                statement.execute("Select * from " + tableName + " LIMIT 1;");
 	                tableExists = true;
 	            } catch (SQLException e) {
 	            	System.out.println("Table Not exists");
 	            }
			  if(!tableExists)
			    {
				 String createTable = "create table "+tableName+" ("+str[0]+","+str[1]+","+str[2]+","+str[3]+");";
	             statement.execute(createTable);
	             System.out.println("Table has been created.");
	             String insertQuery = "insert into "+tableName+" ("+str1[0]+","+str1[1]+","+str1[2]+","+str1[3]+") values (?, ?, ?, ?);";
	             PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);
                 String line="";
                 int count = 0;
		            while ((line = br.readLine()) != null) {
		                // Split the CSV line into fields
		                String[] fields = line.split(",");
		                // Set the values for the prepared statement
		                preparedStatement.setString(1, fields[0]);
		                preparedStatement.setString(2, fields[1]);
		                preparedStatement.setString(3, fields[2]);
		                preparedStatement.setString(4, fields[3]);
		                // Execute the INSERT statement
		                preparedStatement.executeUpdate();
		               count++;
		               // System.out.print(count);
		                
		            }
		            System.out.println(count+" records have been inserted");
			}
			//If table exists,then existing table updation is done....
			else {
				System.out.println("Table already exists!!!!");
				boolean isFirstRow = true; // Flag to track the first row (header)
				List<String> updatedNames = new ArrayList<>(); // List to store updated names
				List<String> insertedNames = new ArrayList<>(); // List to store inserted names

				try {
				    BufferedReader br1 = new BufferedReader(new FileReader(csvFilePath));
				    String line = "";
				    while ((line = br1.readLine()) != null) {
				        String[] csvValues = line.split(","); // Assuming CSV values are comma-separated

				        if (csvValues.length >= 4) { // Assuming at least 4 columns in CSV
				            String empId = csvValues[0].trim();
				            String empName = csvValues[1].trim();
				            String empEmail = csvValues[2].trim();
				            String empPhone = csvValues[3].trim();

				            if (!isFirstRow) { // Skip the first row (header)
				                // Check if the record already exists in the database based on EmpId
				                String checkQuery = "SELECT * FROM " + tableName + " WHERE EmpId = ?";
				                PreparedStatement checkStatement = connection.prepareStatement(checkQuery);
				                checkStatement.setString(1, empId);
				                ResultSet resultSet = checkStatement.executeQuery();

				                if (resultSet.next()) {
				                    // Record already exists, update it
				                    String updateQuery = "UPDATE " + tableName + " SET EmpName=?, EmpPhone=?, EmpAddress=?  WHERE EmpId=?";
				                    PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
				                    updateStatement.setString(1, empName);
				                    updateStatement.setString(2, empEmail);
				                    updateStatement.setString(3, empPhone);
				                    updateStatement.setString(4, empId);
				                    updateStatement.executeUpdate();
				                    updateStatement.close();
				                    updatedNames.add(empName); // Add the updated name to the list
				                } else {
				                    // Record does not exist, insert it
				                    String insertQuery = "INSERT INTO " + tableName + " (EmpId, EmpName, EmpPhone, EmpAddress) VALUES (?, ?, ?, ?)";
				                    PreparedStatement insertStatement = connection.prepareStatement(insertQuery);
				                    insertStatement.setString(1, empId);
				                    insertStatement.setString(2, empName);
				                    insertStatement.setString(3, empPhone);
				                    insertStatement.setString(4, empEmail);
				                    insertStatement.executeUpdate();
				                    insertStatement.close();
				                    insertedNames.add(empName); // Add the inserted name to the list
				                }
				            } else {
				                isFirstRow = false; // Set the flag to false after processing the header row
				            }
				        }
				    }

				    // Print updated names as a comma-separated list
				    System.out.println("Updated rows: " + String.join(", ", updatedNames));

				    // Print inserted names as a comma-separated list
				    System.out.println("Inserted rows: " + String.join(", ", insertedNames));

				} catch (SQLException | IOException e) {
				    e.printStackTrace();
				} 
			}
			  fr.close();
			  br.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
}
	//connection closed
	public static void closeConnection(Connection connection) {
		if(connection!=null)
		{
			try {
				connection.close();
				System.out.println("Database connection closed!!!!");
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}

