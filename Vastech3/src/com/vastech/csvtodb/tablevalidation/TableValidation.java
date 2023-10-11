package com.vastech.csvtodb.tablevalidation;

import java.sql.Connection;
import com.vastech.csvtodb.constants.Constants;
import com.vastech.csvtodb.operations.DbOperations;
/*
 * 
 * 
 * 
 * @author Sakshi Avunuri
 * 
 * 
 */

public class TableValidation {

public static void main(String[] args) {
	  		try {
	  		  Connection conn = DbOperations.initDbConnection();
	  		  String filePath = Constants.csvFilePath;
			  DbOperations.initDbConnection();
	  		  System.out.println("Connection has been established!!!!!!");
		      DbOperations.insertionAndUpdation(conn,filePath);
		      DbOperations.closeConnection(conn);
			} catch (Exception e) {
				e.printStackTrace();
			}
	  		
}
}
