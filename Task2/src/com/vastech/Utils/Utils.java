package com.vastech.Utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.vastech.Constants.Dbconstants;
/* In this class we perform the connection operation,create table operation
 * and insert the data into the created table.
 * if table exists it will update the table.
 * 
 */

public class Utils {

	public static String Tablename = "";

	/* this method is responsible for establishing the connection */
	public static Connection initConnection() throws ClassNotFoundException {
		// System.out.println("connection method started");

		Connection connect = null;
		try {
			Class.forName(Dbconstants.JDBC_DRIVER);
			connect = DriverManager.getConnection(Dbconstants.url, Dbconstants.username, Dbconstants.password);

		} catch (SQLException e) {

			e.printStackTrace();

		}
		// System.out.println("connection method ended..");

		return connect;

	}

	/* this method is responsible for closing the connection */

	/* this method is responsible for creating the table if not exists */
	public static void createTable(Connection connect, String csvfile)
			throws IOException, ClassNotFoundException, SQLException {

		Scanner scan = new Scanner(System.in);
		System.out.println("enter the table name?");

		try {
			Tablename = scan.next();
		} catch (Exception e) {

			e.printStackTrace();
		}
		// Utils u = new Utils();

		// Connection connect1 = u.initConnection();
		Statement statement = null;
		try {
			statement = connect.createStatement();
		} catch (SQLException e) {

			e.printStackTrace();
		}

		// Check if the table already exists
		boolean tableExists = false;
		try {
			statement.execute("Select * from " + Tablename + " LIMIT 1;");
			tableExists = true;
		} catch (SQLException e) {

			System.out.println("Table Not exists");
		}

		// Reading the only headers from csv file

		FileReader fr = null;
		try {
			fr = new FileReader(csvfile);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}

		BufferedReader br = new BufferedReader(fr);
		String header = br.readLine();
		// System.out.println(header);

		String[] columns = header.split(",");
		String[] a = new String[header.length()];
		for (int i = 0; i < columns.length; i++) {
			a[i] = (columns[i] + " varchar(45)");
		}

		String sql = "create table " + Tablename + " (" + a[0] + "," + a[1] + "," + a[2] + ");";
		// System.out.println(sql);

		// Creating the table

		if (!tableExists) {
			try {
				statement.execute(sql);
				System.out.println("Table created.");

				//Utils.insertEmployee(Utils.initConnection(), Dbconstants.csvfile);
				//System.out.println("data inserted..");

			} catch (SQLException e) {

				e.printStackTrace();
			}
			// System.out.println("Table created.");
		} else {
			System.out.println("Table already exists");
			// Update u1=new Update();
			// Update.updateEmployee(connect,u1.empid, u1.ename, u1.emailid);
			// Update u1=new Update();
			// if (Update.employeeExists(connect, u1.empid)) {

			// Update.updateEmployee(connect, u1.empid, u1.ename, u1.emailid);
			// System.out.println("updated..");
		}

	}

	/* this method is responsible for inserting the data into the table */

	public static void insertEmployee(Connection connect, String csvfile) throws IOException {

		int num = 0;
		FileReader fr = null;
		try {
			fr = new FileReader(csvfile);
		} catch (FileNotFoundException e) {

			e.printStackTrace();
		}
		/*
		 * BufferedReader br = new BufferedReader(fr);
		 * 
		 * String line;
		 * 
		 * while ((line = br.readLine()) != null) { Utils u = new Utils();
		 * 
		 * try { // connect = u.initConnection(); String[] value = line.split(",");
		 * String empid = value[0]; String ename = value[1]; String email = value[2];
		 */
		try {
			BufferedReader br = new BufferedReader(new FileReader(Dbconstants.csvfile));
			String line;
			Utils u = new Utils();
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");
				if (data.length != 3) {
					System.out.println(" CSV data: " + line);
					continue;
				}

				String empid = data[0];

				String ename = data[1];
				String email = data[2];

				String sql = "insert into " + u.Tablename + " values(?,?,?)";
				PreparedStatement ps = connect.prepareStatement(sql);
				ps.setString(1, empid);
				ps.setString(2, ename);
				ps.setString(3, email);
				num = ps.executeUpdate();

			}
		} catch (Exception e) {

			e.printStackTrace();
		}

	}

}
