package com.vastech.Utils;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.vastech.Constants.Dbconstants;

/*this class is responsible to update the data*/

public class Update {

	public String empid;
	public String ename;
	public String emailid;
	public String line = "";
	{
		try {
			BufferedReader br = new BufferedReader(new FileReader(Dbconstants.csvfile));
			while ((line = br.readLine()) != null) {
				String[] data = line.split(",");
				if (data.length != 3) {
					System.out.println(" CSV data: " + line);
					continue;
				}

				String empid = data[0];

				String ename = data[1];
				String emailid = data[2];

			}
		}

		catch (Exception e) {

			e.printStackTrace();
		}
	}

	/* this method will checks the if employee exists in the database or not */
	public static boolean employeeExists(Connection connect, String empid) throws SQLException {

		Utils u = new Utils();
		String tablename1 = "";
		tablename1 = u.Tablename;

		String sql = "SELECT empid FROM " + tablename1 + " WHERE empid = ?";
		try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
			preparedStatement.setString(1, empid);
			try (ResultSet resultSet = preparedStatement.executeQuery()) {
				return resultSet.next();
			}
		}
	}

	/* this method will update the employee details */
	public static void updateEmployee(Connection connect, String empid, String ename, String emailid)
			throws SQLException {

		Utils u = new Utils();
		String tablename1 = "";
		tablename1 = u.Tablename;

		String sql = "UPDATE " + tablename1 + " SET ename = ?, emailid = ?, empid = ? WHERE empid = ?";
		try (PreparedStatement preparedStatement = connect.prepareStatement(sql)) {
			preparedStatement.setString(1, ename);
			preparedStatement.setString(2, emailid);
			preparedStatement.setString(3, empid);
			preparedStatement.setString(4, empid);
			preparedStatement.executeUpdate();
			// System.out.println("Updated employee: " );
		}
		System.out.println("updated successfully..");
	}

}
